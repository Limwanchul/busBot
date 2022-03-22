package com.example.busBot.dto;

public class BusArrival {
	/*
	 * 정류소 아이디
	 */
	private String stationId = "";

	/*
	 * 노선 아이디
	 */
	private String routeId = "";

	/*
	 * 첫 번째 차량 위치 정보
	 */
	private String locationNo1 = "";

	/*
	 * 첫 번째 차량 도착 예상 시간
	 */
	private String predictTime1 = "";

	/*
	 * 첫 번째 차량 저상 버스 여부
	 * 0: 일반버스, 1: 저상버스
	 */
	private int lowPlate1;

	/*
	 * 첫 번째 차량 차량 번호
	 */
	private String plateNo1 = "";

	/*
	 * 첫 번째 차량 빈자리 수
	 * -1: 정보없음
	 */
	private int remainSeatCnt1 = -1;

	/*
	 * 두 번째 차량 위치 정보
	 */
	private String locationNo2 = "";

	/*
	 * 두 번째 차량 도착 예상 시간
	 */
	private String predictTime2 = "";

	/*
	 * 두 번째 차량 저상버스 여부
	 * 0: 일반버스, 1: 저상버스
	 */
	private int lowPlate2;

	/*
	 * 두 번째 차량 차량번호
	 */
	private String plateNo2 = "";

	/*
	 * 두 번째 차량 빈자리 수
	 * -1: 정보 없음
	 */
	private int remainSeatCnt2 = -1;

	/*
	 * 정류소 순번
	 */
	private String staOrder = "";

	/*
	 * 상태 구분
	 * RUN: 운행중, PASS: 운행중, STOP: 운행종료, WAIT: 회차대기
	 */
	private String flag = "";

	private String[] text;

	public String getStationId() {

		return stationId;
	}

	public void setStationId(String stationId) {

		this.stationId = stationId;
	}

	public String getRouteId() {

		return routeId;
	}

	public void setRouteId(String routeId) {

		this.routeId = routeId;
	}

	public String getLocationNo1() {

		return locationNo1;
	}

	public void setLocationNo1(String locationNo1) {

		this.locationNo1 = locationNo1;
	}

	public String getPredictTime1() {

		return predictTime1;
	}

	public void setPredictTime1(String predictTime1) {

		this.predictTime1 = predictTime1;
	}

	public int getLowPlate1() {

		return lowPlate1;
	}

	public void setLowPlate1(int lowPlate1) {

		this.lowPlate1 = lowPlate1;
	}

	public String getPlateNo1() {

		return plateNo1;
	}

	public void setPlateNo1(String plateNo1) {

		this.plateNo1 = plateNo1;
	}

	public int getRemainSeatCnt1() {

		return remainSeatCnt1;
	}

	public void setRemainSeatCnt1(int remainSeatCnt1) {

		this.remainSeatCnt1 = remainSeatCnt1;
	}

	public String getLocationNo2() {

		return locationNo2;
	}

	public void setLocationNo2(String locationNo2) {

		this.locationNo2 = locationNo2;
	}

	public String getPredictTime2() {

		return predictTime2;
	}

	public void setPredictTime2(String predictTime2) {

		this.predictTime2 = predictTime2;
	}

	public int getLowPlate2() {

		return lowPlate2;
	}

	public void setLowPlate2(int lowPlate2) {

		this.lowPlate2 = lowPlate2;
	}

	public String getPlateNo2() {

		return plateNo2;
	}

	public void setPlateNo2(String plateNo2) {

		this.plateNo2 = plateNo2;
	}

	public int getRemainSeatCnt2() {

		return remainSeatCnt2;
	}

	public void setRemainSeatCnt2(int remainSeatCnt2) {

		this.remainSeatCnt2 = remainSeatCnt2;
	}

	public String getStaOrder() {

		return staOrder;
	}

	public void setStaOrder(String staOrder) {

		this.staOrder = staOrder;
	}

	public String getFlag() {

		return flag;
	}

	public void setFlag(String flag) {

		this.flag = flag;
	}

	public String[] getText() {

		return text;
	}

	public void setText(String[] text) {

		this.text = text;
	}
}
