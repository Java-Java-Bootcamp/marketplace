package ru.teamtwo.telegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class TelegrambotApplication {

    public static void main(String[] args) {
//        SpringApplication.run(TelegrambotApplication.class, args);
        try {
            // Create the TelegramBotsApi object to register your bots
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

            // Register your newly created AbilityBot
            botsApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
