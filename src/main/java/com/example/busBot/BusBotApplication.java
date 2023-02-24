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
			Init initBean = context.getBean(Init.class);

			String telegramApiKey = initBean.getTelegramApiKey();
			String botId = initBean.getBotId();
	        
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
			botsApi.registerBot(new TelegramMessage(telegramApiKey, botId));
		} catch (Exception e) {
			log.error(e);
		}
	}

	@Component
    class Init {

		@Value("${telegram.apiKey}")
	    private String telegramApiKey;

		@Value("${telegram.botId}")
		private String botId;
	
	    public String getTelegramApiKey() {
	        return telegramApiKey;
	    }

		public String getBotId() { return botId; }
    }
	
}
