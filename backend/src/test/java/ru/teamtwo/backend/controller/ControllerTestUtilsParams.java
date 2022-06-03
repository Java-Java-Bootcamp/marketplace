package ru.teamtwo.backend.controller;

import org.springframework.test.web.servlet.MockMvc;
import ru.teamtwo.backend.models.BaseEntity;

public record ControllerTestUtilsParams(
        MockMvc mockMvc,
        String mapping,
        BaseEntity entity,
        Record dto) {
}
