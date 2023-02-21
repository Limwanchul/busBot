package com.example.busBot.busSearch.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BusArrival {

	private String stationId = "";		// 정류소 아이디
	private String routeId = "";		// 노선 아이디
	private String locationNo1 = "";	// 첫 번째 차량 위치 정보
	private String predictTime1 = "";	// 첫 번째 차량 도착 예상 시간
	private int lowPlate1;				// 첫 번째 차량 저상 버스 여부 (0: 일반버스, 1: 저상버스)
	private String plateNo1 = "";		// 첫 번째 차량 차량 번호
	private int remainSeatCnt1 = -1;	// 첫 번째 차량 빈자리 수 (-1: 정보없음)
	private String locationNo2 = "";	// 두 번째 차량 위치 정보
	private String predictTime2 = "";	// 두 번째 차량 도착 예상 시간
	private int lowPlate2;				// 두 번째 차량 저상버스 여부 (0: 일반버스, 1: 저상버스)
	private String plateNo2 = "";		// 두 번째 차량 차량번호
	private int remainSeatCnt2 = -1;	// 두 번째 차량 빈자리 수 (-1: 정보 없음)
	private String staOrder = "";		// 정류소 순번
	private String flag = "";			// 상태 구분 (RUN: 운행중, PASS: 운행중, STOP: 운행종료, WAIT: 회차대기)
	private String[] text;

}
