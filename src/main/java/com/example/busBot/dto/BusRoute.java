package com.example.busBot.dto;

public class BusRoute {

	/*
	 * 노선 아이디
	 */
	private int routeId;
	/*
	 * 노선번호
	 */
	private String routeName;

	/*
	 * 노선유형
	 */
	private int routeTypeCd;

	/*
	 * 노선유형명
	 */
	private String routeTypeName;

	/*
	 * 지역명
	 */
	private String regionName;

	/*
	 * 관할지역
	 */
	private int districtCd;

	public String getRouteName() {

		return routeName;
	}

	public int getRouteId() {

		return routeId;
	}

	public void setRouteId(int routeId) {

		this.routeId = routeId;
	}

	public void setRouteName(String routeName) {

		this.routeName = routeName;
	}

	public int getRouteTypeCd() {

		return routeTypeCd;
	}

	public void setRouteTypeCd(int routeTypeCd) {

		this.routeTypeCd = routeTypeCd;
	}

	public String getRouteTypeName() {

		return routeTypeName;
	}

	public void setRouteTypeName(String routeTypeName) {

		this.routeTypeName = routeTypeName;
	}

	public String getRegionName() {

		return regionName;
	}

	public void setRegionName(String regionName) {

		this.regionName = regionName;
	}

	public int getDistrictCd() {

		return districtCd;
	}

	public void setDistrictCd(int districtCd) {

		this.districtCd = districtCd;
	}
}
