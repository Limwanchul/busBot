package com.example.busBot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.busBot.dto.BusArrival;
import com.example.busBot.dto.BusRoute;
import com.example.busBot.dto.BusStation;

@Service
public interface PublicDataAcessService {

	/**
	 * 버스 노선 조회>노선번호목록조회
	 * 
	 * @param busNumber
	 * @param busRegion
	 * @return
	 */
	List<BusRoute> getBusRouteList (String busNumber, String busRegion);

	

	/**
	 * 버스 노선 조회 >경유정류소목록조회
	 * 
	 * @param routeId
	 * @return
	 */
	List<BusStation> getBusRouteStationList (int routeId, String busStationName);

	/**
	 * 버스 도착 정보 조회>버스도착정보목록조회
	 * 
	 * @param stationId
	 * @return
	 */
	List<BusArrival> getBusArrivalList (String stationId);

	
	/**
	 * 공공데이터 connect 및 데이터 return
	 * callFlag 0: 노선번호목록조회 1: 경유정류소목록조회 2: 버스도착정보목록조회
	 * 
	 * @param urlBuilder
	 * @return
	 */
	String getBusRouteListCon (StringBuilder urlBuilder, int callFlag);

}
