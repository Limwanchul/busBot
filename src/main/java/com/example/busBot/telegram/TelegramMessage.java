package com.example.busBot.telegram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TelegramMessage extends TelegramLongPollingBot {

	private String telegramApiKey;

	private String botId;

	private final int BUTTON_MAX = 5;

	public TelegramMessage(String telegramApiKey, String botId) {
		this.telegramApiKey = telegramApiKey;
		this.botId = botId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onUpdateReceived(Update update) {

		SendMessage message = new SendMessage();
		StringBuilder msg = new StringBuilder();

		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
		List<InlineKeyboardButton> rowInline = new ArrayList<>();
		InlineKeyboardButton inlineKeyboardButton = null;

		if (update.hasCallbackQuery()) {
			log.info("button : " + update.getCallbackQuery().getData());
			String rs = String.valueOf(update.getCallbackQuery().getData());
			String butFlg = rs.substring(rs.indexOf("/") + 1, rs.length());

			if ("1".equals(butFlg)) {	// 지역을 선택 했을 때
				Map<String, Object> busList = webClient("127.0.0.1/tellegramBusArrival", rs);
				List<Map<String, Object>> busMapList = (List<Map<String, Object>>) busList.get("busInfo");
				if (ObjectUtils.isNotEmpty(busMapList)) {

					// 버튼 최대 갯수는 BUTTON_MAX개로 BUTTON_MAX개가 넘어가면 /구분자로 입력하라는 문구 전달
					for(int i = 0; i < BUTTON_MAX; i++) {
						inlineKeyboardButton = new InlineKeyboardButton();
						inlineKeyboardButton.setText(String.valueOf(busMapList.get(i).get("stationName")));
						inlineKeyboardButton.setCallbackData(String.valueOf(busMapList.get(i).get("stationId")) + "/2");
						rowInline.add(inlineKeyboardButton);
					}

					// Set the keyboard to the markup
					rowsInline.add(rowInline);
					// Add it to the message
					markupInline.setKeyboard(rowsInline);
					message.setReplyMarkup(markupInline);

					message.setChatId(botId);
					msg.append("정류장을 선택해 주세요.");
					if (busMapList.size() > BUTTON_MAX) {
						msg.append("\n원하는 정류장이 없으면 [번호/지역/정류장]으로 다시 검색해 주세요.");
					}
					message.setText(msg.toString());

					try {
						execute(message);
					} catch (TelegramApiException e) {
						throw new RuntimeException(e);
					}
				}
			} else if("2".equals(butFlg)) {    // 정류장 선택 했을 때
				Map<String, Object> busStopList = webClient("127.0.0.1/tellegramBusArrival", rs);
				System.out.println(busStopList);

				try {
					List<Map<String, Object>> busMap = (List<Map<String, Object>>) busStopList.get("busInfo");

					if (!CollectionUtils.isEmpty(busMap)) {
						String predicTime1 = "";
						String predicTime2 = "";

						for (Map<String, Object>  bus : busMap) {
							// TODO 노선Id(routeId)로 버스 번호 찾는 API호출 하여 처음에 입력한 값과 비교 하여 Return
							// http://openapi.gbis.go.kr/ws/rest/busrouteservice/info?serviceKey=1234567890&routeId=200000085

							predicTime1 = String.valueOf(bus.get("predictTime1"));
							predicTime2 = String.valueOf(bus.get("predictTime2"));
							if (StringUtils.isNoneBlank(predicTime1)) {
								msg.append("첫번째 버스는 " + predicTime1 + "분 후에 도착합니다.");
							} else if (StringUtils.isNoneBlank(predicTime2)) {
								msg.append("\r\n");
								msg.append("두번째 버스는 " + predicTime2 + "분 후에 도착합니다.");
							} else if (StringUtils.isNoneBlank(predicTime1) && StringUtils.isNoneBlank(predicTime2)) {
								msg.append("버스 운행이 종료되었 습니다.");
								break;
							}
						}
					} else {
						msg.append("존재하지 않는 버스노선 입니다.");
					}

					message.setChatId(update.getMessage().getChatId().toString());
					message.setText(msg.toString());

					execute(message);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}
		}

	    if (update.hasMessage() && update.getMessage().hasText()) {
			String busNum = update.getMessage().getText();

			log.info("Text : " + update.getMessage().getText());
			log.info("chatId : " + botId);

			// 버스 노선 조회>노선번호목록조회에서 routeId(노선ID)값을 구한다.
			Map<String, Object> busList = webClient("127.0.0.1/tellegramBusArrival", busNum);
			List<Map<String, Object>> busMapList = (List<Map<String, Object>>) busList.get("busInfo");

			if (ObjectUtils.isNotEmpty(busList)) {

				// 버튼 최대 갯수는 BUTTON_MAX개로 BUTTON_MAX개가 넘어가면 /구분자로 입력하라는 문구 전달
				for(int i = 0; i < BUTTON_MAX; i++) {
					inlineKeyboardButton = new InlineKeyboardButton();
					inlineKeyboardButton.setText(String.valueOf(busMapList.get(i).get("regionName")));
					inlineKeyboardButton.setCallbackData(String.valueOf(busMapList.get(i).get("routeId")) + "/1");
					rowInline.add(inlineKeyboardButton);
				}

				// Set the keyboard to the markup
				rowsInline.add(rowInline);
				// Add it to the message
				markupInline.setKeyboard(rowsInline);
				message.setReplyMarkup(markupInline);

				message.setChatId(botId);
				msg.append("지역을 선택해 주세요.");
				if (busMapList.size() > BUTTON_MAX) {
					msg.append("\n원하는 지역이 없으면 [번호/지역]으로 다시 검색해 주세요.");
				}
				message.setText(msg.toString());

				try {
					execute(message);
				} catch (TelegramApiException e) {
					throw new RuntimeException(e);
				}
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

	public Map<String, Object> webClient(String baseUrl, String data) {
		Map<String, Object> result = new HashMap<>();

		WebClient webClient = WebClient.builder()
				.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(-1))
				.baseUrl(baseUrl)
//				.baseUrl("127.0.0.1/" + url ?busNum=" + busNum + "&butNum=")
				.build();

		result = (Map<String, Object>) webClient
				.mutate()
				.build()
				.get()
				.uri(uri ->uri.queryParam("data", data).build())
				.accept(MediaType.ALL)
				.acceptCharset(Charsets.UTF_8)
				.retrieve()
				.bodyToMono(Map.class)
				.block();

		return result;
	}

}
