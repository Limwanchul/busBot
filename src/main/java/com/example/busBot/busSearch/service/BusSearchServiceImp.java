package com.example.busBot.busSearch.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.context.annotation.Configuration;

import com.example.busBot.busSearch.dto.ApiConnect;
import com.example.busBot.busSearch.dto.BusArrival;
import com.example.busBot.busSearch.dto.BusRoute;
import com.example.busBot.busSearch.dto.BusStation;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BusSearchServiceImp implements BusSearchService{

	private final ApiConnect api;

	@Override
	public List<BusRoute> getBusRouteList(String busNumber, String busRegion) {
		StringBuilder urlBuilder = new StringBuilder(); /*URL*/
		List<BusRoute> busRouteDoFilterList = new ArrayList<BusRoute>();

		try {
			// 버스 노선 조회 > 노선번호목록조회
			urlBuilder.append("http://apis.data.go.kr/6410000/busrouteservice/getBusRouteList");
			urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + api.getApiKey()); /*Service Key*/
			urlBuilder.append("&" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode(api.getApiKey(), "UTF-8")); /*인증키(공공데이포털 발급)*/
			urlBuilder.append("&" + URLEncoder.encode("keyword","UTF-8") + "=" + URLEncoder.encode(busNumber, "UTF-8")); /*버스 번호*/

			String busRouteListCon = getBusRouteListCon(urlBuilder, 1);
			// 버스 노선 번호 목록 vo 객체에 set
			Gson gson = new Gson();
			BusRoute[] busRouteArray = gson.fromJson(busRouteListCon, BusRoute[].class);
			List<BusRoute>busRouteList = Arrays.asList(busRouteArray);

			// 텔레그램에서 조회 시 버스번호만 넘겨서 지역 필터링 하지 않음
			if (StringUtils.isEmpty(busRegion)) {
				return busRouteList;
			}

			// 지역에 대한 필터링 regionName
			for (BusRoute busRoute : busRouteList) {
				if (busRoute.getRegionName().indexOf(busRegion) > -1 && busNumber.equals(busRoute.getRouteName())) {
//				if (busNumber.equals(busRoute.getRouteName())) {
					busRouteDoFilterList.add(busRoute);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return busRouteDoFilterList;
	}

	@Override
	public List<BusStation> getBusRouteStationList(int routeId, String busStationName) {
		StringBuilder urlBuilder = new StringBuilder(); /*URL*/
		List<BusStation> busRouteStationDoFilterList = new ArrayList<BusStation>();

		try {
			// 버스 노선 조회 > 경유정류소목록조회
			urlBuilder.append("http://apis.data.go.kr/6410000/busrouteservice/getBusRouteStationList");
			urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + api.getApiKey()); /*Service Key*/
			urlBuilder.append("&" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode(api.getApiKey(), "UTF-8")); /*인증키(공공데이포털 발급)*/
			urlBuilder.append("&" + URLEncoder.encode("routeId","UTF-8") + "=" + URLEncoder.encode(String.valueOf(routeId), "UTF-8")); /*정류소 번호*/

			String busRouteStationListCon = getBusRouteListCon(urlBuilder, 2);
			// 버스 노선 번호 목록 vo 객체에 set
			Gson gson = new Gson();
			BusStation[] busRouteStationArray = gson.fromJson(busRouteStationListCon, BusStation[].class);
			List<BusStation>busRouteStationList = Arrays.asList(busRouteStationArray);

			// 텔레그램 버튼을 통하여 조회
			if (StringUtils.isEmpty(busStationName)) {
				return busRouteStationList;
			}

			// 버스 정류장에 대한 필터링 StationName
			for (BusStation busRouteStation : busRouteStationList) {
				if (busRouteStation.getStationName().indexOf(busStationName) > -1) {
					busRouteStationDoFilterList.add(busRouteStation);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return busRouteStationDoFilterList;
	}

	@Override
	public List<BusArrival> getBusArrivalList(String stationId) {
		StringBuilder urlBuilder = new StringBuilder(); /*URL*/
		List<BusArrival> busRouteDoFilterList = new ArrayList<BusArrival>();

		try {
			// 버스 도착 정보 조회 > 버스도착정보목록조회
			urlBuilder.append("http://apis.data.go.kr/6410000/busarrivalservice/getBusArrivalList");
			urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + api.getApiKey()); /*Service Key*/
			urlBuilder.append("&" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode(api.getApiKey(), "UTF-8")); /*인증키(공공데이포털 발급)*/
			urlBuilder.append("&" + URLEncoder.encode("stationId","UTF-8") + "=" + URLEncoder.encode(stationId, "UTF-8")); /*정류소ID*/

			String busArrivalListCon = getBusRouteListCon(urlBuilder, 3);
			// 버스 노선 번호 목록 vo 객체에 set
			Gson gson = new Gson();
			BusArrival[] busArrivalArray = gson.fromJson(busArrivalListCon, BusArrival[].class);
			List<BusArrival>busArrivalList = Arrays.asList(busArrivalArray);

			// 버스 정류장에 대한 필터링 regionName
			for (BusArrival busarrival : busArrivalList) {
				busRouteDoFilterList.add(busarrival);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return busRouteDoFilterList;
	}

	@Override
	public String getBusRouteListCon(StringBuilder urlBuilder, int callFlag) {
		String jsonObjectBusRouteList = "";
		try {
			/*
			 * 공공포탈 API 데이터 GET
			 */
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
//		System.out.println("Response code: " + conn.getResponseCode());
			BufferedReader rd;
			if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}

			rd.close();
			conn.disconnect();

			/*
			 * XML -> JSON convert
			 * 원하는 데이터가 3Level에 존재하여 JSON데이터 3번 parsing 필요
			 */
			JSONObject jsonObject_Response = (JSONObject) XML.toJSONObject(sb.toString()).get("response");
			JSONObject jsonObject_Body = (JSONObject) jsonObject_Response.get("msgBody");
		
			if (callFlag == 1) { /*노선번호목록조회*/
				jsonObjectBusRouteList = jsonObject_Body.getJSONArray("busRouteList").toString();
			} else if (callFlag == 2) {/*경유정류소목록조회*/
				jsonObjectBusRouteList = jsonObject_Body.getJSONArray("busRouteStationList").toString();
			} else if (callFlag == 3) {
				jsonObjectBusRouteList = jsonObject_Body.getJSONArray("busArrivalList").toString();
			}
			//System.out.println(jsonObjectBusRouteList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObjectBusRouteList;
	}

}
