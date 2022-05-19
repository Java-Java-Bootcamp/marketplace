package ru.teamtwo.telegrambot.service.impl.stages;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.teamtwo.telegrambot.model.customer.CustomerState;

import java.util.Map;

public record StageContext (
   Update update,
   String message,
   String chatId,
   User user,
   CustomerState customerState,
   String dataCallbackType,
   Map<String, String> dataCallbackArgs
){}
