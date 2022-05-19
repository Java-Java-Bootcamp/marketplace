package ru.teamtwo.telegrambot.service.api.stage;

import ru.teamtwo.telegrambot.service.impl.stages.StageContext;

public interface StageHandler {
    boolean shouldRun(StageContext context);
    void execute(StageContext context);
}
