package com.zhamila.taskgamer_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import settings.CommandBot;

@SpringBootApplication
public class TaskgamerBotApplication {

	public static void main(String[] args) throws TelegramApiException {
		SpringApplication.run(TaskgamerBotApplication.class, args);
		CommandBot bot = new CommandBot();
		TelegramBotsApi telegramBot = new TelegramBotsApi(DefaultBotSession.class);
		telegramBot.registerBot(bot);
	}

}
