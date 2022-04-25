package ru.teamtwo.telegrambot.service;

public interface ContextHandler {
    boolean shouldRun(ProcessingContext context);

    void execute(ProcessingContext context);
}
