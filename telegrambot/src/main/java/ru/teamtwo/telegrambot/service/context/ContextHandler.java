package ru.teamtwo.telegrambot.service.context;

public interface ContextHandler {
    boolean shouldRun(ProcessingContext context);

    void execute(ProcessingContext context);
}
