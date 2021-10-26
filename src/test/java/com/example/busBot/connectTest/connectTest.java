package com.example.busBot.connectTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;
import org.json.XML;

public class connectTest {
	public static void main(String[] args) throws Exception {
	 	StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6410000/busarrivalservice/getBusArrivalList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=HaR876tHIxakSxmNpO2sHGfkhJHyFPPMJXLuhDsGmv3rthi/VIZPquD2wtM4rsrjWkPDStsa1LAxph5jtpUO4w=="); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode("HaR876tHIxakSxmNpO2sHGfkhJHyFPPMJXLuhDsGmv3rthi/VIZPquD2wtM4rsrjWkPDStsa1LAxph5jtpUO4w==", "UTF-8")); /*인증키(공공데이포털 발급)*/
        urlBuilder.append("&" + URLEncoder.encode("stationId","UTF-8") + "=" + URLEncoder.encode("122000181", "UTF-8")); /*정류소ID*/
        
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        
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
        
        JSONObject xmlJSONObj = XML.toJSONObject(sb.toString());
        String jsonPrettyPrintString = xmlJSONObj.toString(4);
        System.out.println(jsonPrettyPrintString);

	}
}
