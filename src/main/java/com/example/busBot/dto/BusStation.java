package com.example.busBot.dto;

public class BusStation {

	/*
	 * 정류소 아이디
	 */
	private String stationId = "";

	/*
	 * 정류소 순번
	 */
	private String stationSeq = "";

	/*
	 * 정류소명
	 */
	private String stationName = "";

	/*
	 * 정류소 번호
	 */
	private String mobileNo = "";

	/*
	 * 지역명
	 */
	private String regionName = "";

	/*
	 * 관할지역
	 */
	private String districtCd = "";

	/*
	 * 중앙차로 여부
	 */
	private String centerYn = "";

	/*
	 * 회차여부
	 */
	private String turnYn = "";

	/*
	 * X 좌표
	 */
	private String x = "";

	/*
	 * Y 좌표
	 */
	private String y = "";

	public String getStationId() {

		return stationId;
	}

	public void setStationId(String stationId) {

		this.stationId = stationId;
	}

	public String getStationSeq() {

		return stationSeq;
	}

	public void setStationSeq(String stationSeq) {

		this.stationSeq = stationSeq;
	}

	public String getStationName() {

		return stationName;
	}

	public void setStationName(String stationName) {

		this.stationName = stationName;
	}

	public String getMobileNo() {

		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {

		this.mobileNo = mobileNo;
	}

	public String getRegionName() {

		return regionName;
	}

	public void setRegionName(String regionName) {

		this.regionName = regionName;
	}

	public String getDistrictCd() {

		return districtCd;
	}

	public void setDistrictCd(String districtCd) {

		this.districtCd = districtCd;
	}

	public String getCenterYn() {

		return centerYn;
	}

	public void setCenterYn(String centerYn) {

		this.centerYn = centerYn;
	}

	public String getTurnYn() {

		return turnYn;
	}

	public void setTurnYn(String turnYn) {

		this.turnYn = turnYn;
	}

	public String getX() {

		return x;
	}

	public void setX(String x) {

		this.x = x;
	}

	public String getY() {

		return y;
	}

	public void setY(String y) {

		this.y = y;
	}

}
