package com.example.busBot.telegram;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import lombok.extern.log4j.Log4j2;

@SuppressWarnings("deprecation")
@Log4j2
public class TelegramMessage extends TelegramLongPollingBot {

	private String telegramApiKey;
	public TelegramMessage(String telegramApiKey) {
		this.telegramApiKey = telegramApiKey;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onUpdateReceived(Update update) {

		String[] text = update.getMessage().getText().split("/");
		String busNum = text[0];
		String busReg = text[1];
		String busStn = text[2];

		log.info("Text : " + update.getMessage().getText());
		log.info("chatId : " + update.getMessage().getChatId().toString());
		
		Map<String, Object> result = new HashMap<>();
	    if (update.hasMessage() && update.getMessage().hasText()) {
	    	
	    	WebClient webClient = WebClient.builder()
                    .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(-1)) 
                    .baseUrl("127.0.0.1/busArrival?busNum=" + busNum + "&busReg=" + busReg + "&busStn=" + busStn)
                    .build();
	    	
	    	result = (Map<String, Object>) webClient
	    			.mutate()
	    			.build()
	    			.get()
	    			.accept(MediaType.ALL)
	    			.acceptCharset(Charsets.UTF_8)
	    			.retrieve()
                   .bodyToMono(Map.class)
                   .block();
	    	
	        try {
	        	List<Map<String, Object>> busMap = (List<Map<String, Object>>) result.get("busInfo");
	        	StringBuilder msg = new StringBuilder();
	        	
	        	if (!CollectionUtils.isEmpty(busMap)) {
	        		String predicTime1 = String.valueOf(busMap.get(0).get("predictTime1"));
		        	String predicTime2 = String.valueOf(busMap.get(0).get("predictTime2"));
		        	
		        	if (StringUtils.isNoneBlank(predicTime1)) {
		        		msg.append("첫번째 버스는 " + predicTime1 + "분 후에 도착합니다.");
		        	} else if (StringUtils.isNoneBlank(predicTime2)) {
		        		msg.append("\r\n");
			        	msg.append("두번째 버스는 " + predicTime2 + "분 후에 도착합니다.");
		        	} else if (StringUtils.isNoneBlank(predicTime1) && StringUtils.isNoneBlank(predicTime2)) {
		        		msg.append("버스 운행이 종료되었 습니다.");
		        	}
	        	} else {
	        		msg.append("존재하지 않는 버스노선 입니다.");
	        	}
	        	
	        	SendMessage message = new SendMessage();
		        message.setChatId(update.getMessage().getChatId().toString());
		        message.setText(msg.toString());
		        
	            execute(message);
	        } catch (TelegramApiException e) {
	            e.printStackTrace();
	        }
	        
	    }
	}
	
	@Override
	public String getBotUsername() {

		return "busStop";
	}
	
	@Override
	public String getBotToken() {

		return telegramApiKey;
	}

}
