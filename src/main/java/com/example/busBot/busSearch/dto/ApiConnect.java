package com.example.busBot.busSearch.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiConnect {

	@Value("${custom.apiKey}")
	private String apiKey;

	public String getApiKey() {

		return apiKey;
	}

}
