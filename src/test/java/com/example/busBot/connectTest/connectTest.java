package com.example.busBot.connectTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.json.XML;

import com.example.busBot.dto.BusRoute;
import com.google.gson.Gson;

public class ConnectTest {

	/*
	 * 공공데이터포털 APIKEY
	 */
	private static String apiKey = "HaR876tHIxakSxmNpO2sHGfkhJHyFPPMJXLuhDsGmv3rthi/VIZPquD2wtM4rsrjWkPDStsa1LAxph5jtpUO4w==";
	public static void main(String[] args) {
		/*
		 * 호출 API flag(테스트 코드)
		 * 0: 버스 노선 조회 1: 버스 정류소 조회 2: 버스 도착 정보 조회
		 */
		int apiFlag = 0;
		StringBuilder urlBuilder = new StringBuilder(); /*URL*/

		try {
			/*
			 * 버스 도착예정시간 FLOW
			 * 1. 버스 노선 조회>노선번호목록조회에서 routeId(노선ID)값을 구한다.
			 * 2. 버스 노선 조회 >경유정류소목록조회에서 routeId값을 파라미터로 넘겨 stationId(정류소ID)값을 구한다.
			 * 3. 버스 도착 정보 조회>버스도착정보목록조회에서 stationId값을 파라미터로 넘겨 버스 도착 정보를 조회
			 * 4. 1번에서 구한 routeId값으로 3번에서 조회한 버스도착정보목록에서 검색하여 predictTime1(도착시간(분단위))을 구한다.
			 */
			if (apiFlag == 0) {
				// 버스 노선 조회 > 노선번호목록조회
				urlBuilder.append("http://apis.data.go.kr/6410000/busrouteservice/getBusRouteList");
				urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + apiKey); /*Service Key*/
				urlBuilder.append("&" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode(apiKey, "UTF-8")); /*인증키(공공데이포털 발급)*/
				urlBuilder.append("&" + URLEncoder.encode("keyword","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*버스 번호*/
			} else if (apiFlag == 1) {
				// 버스 노선 조회 > 경유정류소목록조회 
				urlBuilder.append("http://apis.data.go.kr/6410000/busrouteservice/getBusRouteStationList");
				urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + apiKey); /*Service Key*/
				urlBuilder.append("&" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode(apiKey, "UTF-8")); /*인증키(공공데이포털 발급)*/
				urlBuilder.append("&" + URLEncoder.encode("routeId","UTF-8") + "=" + URLEncoder.encode("228000002", "UTF-8")); /*정류소 번호*/
			} else if (apiFlag == 2) {
				// 버스 도착 정보 조회 > 버스도착정보목록조회
				urlBuilder.append("http://apis.data.go.kr/6410000/busarrivalservice/getBusArrivalList");
				urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + apiKey); /*Service Key*/
				urlBuilder.append("&" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode(apiKey, "UTF-8")); /*인증키(공공데이포털 발급)*/
				urlBuilder.append("&" + URLEncoder.encode("stationId","UTF-8") + "=" + URLEncoder.encode("203000158", "UTF-8")); /*정류소ID*/
			}

			/*
			 * 공공포탈 API 데이터 GET
			 */
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
//			System.out.println("Response code: " + conn.getResponseCode());
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
			String jsonObjectBusRouteList = jsonObject_Body.getJSONArray("busRouteList").toString();
			System.out.println(jsonObjectBusRouteList);

			// 버스 노선 번호 목록 vo 객체에 set
			Gson gson = new Gson();
			BusRoute[] busRouteArray = gson.fromJson(jsonObjectBusRouteList, BusRoute[].class);
			List<BusRoute> busRouteList = Arrays.asList(busRouteArray);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
