package com.example.busBot.busSearch.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.websocket.server.PathParam;

import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.busBot.busSearch.dto.BusArrival;
import com.example.busBot.busSearch.dto.BusRoute;
import com.example.busBot.busSearch.dto.BusStation;
import com.example.busBot.busSearch.service.BusSearchServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@RestController
@RequiredArgsConstructor
@Log4j2
public class BusSearchController {

	private final BusSearchServiceImp pDataAcess;

	/*
	 * 공공데이터포털 APIKEY
	 */
	@GetMapping("/busArrival")
	public HashMap<String, Object> busArrival (@PathParam(value = "busNum") String busNum, @PathParam(value = "busReg") String busReg
			, @PathParam(value = "busStn") String busStn) {
		// 노선번호목록 list
		List<BusRoute> busRouteList = new ArrayList<BusRoute>();
		// 경유정류소목록 list
		List<BusStation> busStationRouteList = new ArrayList<BusStation>();
		// 버스도착정보목록 list
		List<BusArrival> busArrivalList = new ArrayList<BusArrival>();

		// 경유정류소목록 json 데이터
		String busRouteStationListJson = "";
		
		HashMap<String, Object> resultMap = new HashMap<>();
		try {
			/*
			 * 버스 도착예정시간 FLOW
			 * 1. 버스 노선 조회>노선번호목록조회에서 routeId(노선ID)값을 구한다.
			 * 2. 버스 노선 조회 >경유정류소목록조회에서 routeId값을 파라미터로 넘겨 stationId(정류소ID)값을 구한다.
			 * 3. 버스 도착 정보 조회>버스도착정보목록조회에서 stationId값을 파라미터로 넘겨 버스 도착 정보를 조회
			 * 4. 1번에서 구한 routeId값으로 3번에서 조회한 버스도착정보목록에서 검색하여 predictTime1(도착시간(분단위))을 구한다.
			 */

			// 노선번호목록조회 (버스 번호, 지역)
			busRouteList = pDataAcess.getBusRouteList(busNum, busReg);

			if (CollectionUtils.isEmpty(busRouteList)) {
				resultMap.put("result", false);
				resultMap.put("msg", busReg + " 지역에" + busNum + "번 버스가 없습니다.");
				
				return resultMap;
			}

			// 경유정류소목록조회
			busStationRouteList = pDataAcess.getBusRouteStationList(busRouteList.get(0).getRouteId(), busStn);

			if (CollectionUtils.isEmpty(busStationRouteList)) {
				resultMap.put("result", false);
				resultMap.put("msg", busNum + "(" + busReg + ")" + "번 버스가 " + busStn + " 정류소를 지나가지 않습니다.");
				
				return resultMap;
			}
			
			busArrivalList = pDataAcess.getBusArrivalList(busStationRouteList.get(0).getStationId());
			ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
			busRouteStationListJson = mapper.writeValueAsString(busArrivalList);
			Gson gson = new Gson();
			BusArrival[] busArrivalArray = gson.fromJson(busRouteStationListJson, BusArrival[].class);
			busArrivalList = Arrays.asList(busArrivalArray);

			resultMap.put("result", true);
			resultMap.put("busInfo", busArrivalList);
			
		} catch (Exception e) {
			log.error(e);
		}
		return resultMap;

	}
}
