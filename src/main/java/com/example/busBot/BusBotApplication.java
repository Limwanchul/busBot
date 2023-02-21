package com.example.busBot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.example.busBot.telegram.TelegramMessage;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@Log4j2
public class BusBotApplication {

	public static void main(String[] args) {
		
		try {
			ApplicationContext context = SpringApplication.run(BusBotApplication.class, args);
			TelegramApiKey telegramApiKeyBean = context.getBean(TelegramApiKey.class);

	        String telegramApiKey = telegramApiKeyBean.getTelegramApiKey();
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
			botsApi.registerBot(new TelegramMessage(telegramApiKey));
		} catch (Exception e) {
			log.error(e);
		}
	}

	@Component
    class TelegramApiKey {

		@Value("${telegram.apiKey}")
	    private String telegramApiKey;
	
	    public String getTelegramApiKey() {
	        return telegramApiKey;
	    }
    }
	
}
