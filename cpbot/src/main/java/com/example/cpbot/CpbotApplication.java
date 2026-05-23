package com.example.cpbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class CpbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(CpbotApplication.class, args);
	}
    @Bean
    public TelegramBotsApi telegramBotsApi(BotController cpBot) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(cpBot);
        System.out.println("✅ Bot successfully registered with Telegram!");
        return botsApi;
    }
}
