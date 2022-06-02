package ru.teamtwo.api.controller;

import org.springframework.test.web.servlet.MockMvc;
import ru.teamtwo.api.models.BaseEntity;

public record ControllerTestUtilsParams(
        MockMvc mockMvc,
        String mapping,
        BaseEntity entity,
        Record dto) {
}
