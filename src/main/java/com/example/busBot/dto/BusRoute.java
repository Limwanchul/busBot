package com.example.busBot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BusRoute {

	private int routeId;			// 노선 아이디
	private String routeName;		// 노선번호
	private int routeTypeCd;		// 노선유형
	private String routeTypeName;	// 노선유형명
	private String regionName;		// 지역명
	private int districtCd;			// 관할지역

}
