package ru.teamtwo.telegrambot.service.api.context;

import ru.teamtwo.telegrambot.service.impl.stages.StageContext;

public interface StageHandler {

    boolean shouldRun(StageContext context);

    void execute(StageContext context);
}
