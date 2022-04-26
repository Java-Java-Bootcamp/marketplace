package ru.teamtwo.telegrambot.service.context;

import ru.teamtwo.telegrambot.service.context.ProcessingContext;

public interface ContextHandler {
    boolean shouldRun(ProcessingContext context);

    void execute(ProcessingContext context);
}
