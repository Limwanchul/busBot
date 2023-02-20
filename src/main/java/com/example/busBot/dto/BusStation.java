package com.example.busBot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BusStation {

	private String stationId = "";		// 정류소 아이디
	private String stationSeq = "";		// 정류소 순번
	private String stationName = "";	// 정류소명
	private String mobileNo = "";		// 정류소 번호
	private String regionName = "";		// 지역명
	private String districtCd = "";		// 관할지역
	private String centerYn = "";		// 중앙차로 여부
	private String turnYn = "";			// 회차여부
	private String x = "";				// X 좌표
	private String y = "";				// Y 좌표

}
