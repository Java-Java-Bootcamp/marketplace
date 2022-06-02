package ru.teamtwo.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.teamtwo.api.TestUtils;
import ru.teamtwo.api.models.BaseEntity;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Set;

@TestComponent
@RequiredArgsConstructor
public class ControllerTestUtils {

    public static ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.findAndRegisterModules();
    }

    public static void baseGetTest(ControllerTestUtilsParams controllerTestUtilsParams) throws Exception {
        MvcResult result = controllerTestUtilsParams.mockMvc().perform(ControllerTestUtils.get(controllerTestUtilsParams.mapping(), controllerTestUtilsParams.entity())).andReturn();
        ControllerTestUtils.assertContentAndEntityEqual(result.getResponse(), controllerTestUtilsParams.dto().getClass(), controllerTestUtilsParams.entity());
    }

    public static void baseSaveTest(ControllerTestUtilsParams controllerTestUtilsParams) throws Exception {
        MvcResult result = controllerTestUtilsParams.mockMvc().perform(ControllerTestUtils.post(controllerTestUtilsParams.mapping(), controllerTestUtilsParams.dto())).andReturn();
        ControllerTestUtils.assertContentAndEntityIdsEqual(result.getResponse(), controllerTestUtilsParams.entity());
    }

    public static MockHttpServletRequestBuilder get(String controllerRequestMapping, String methodMapping, BaseEntity entity){
        return MockMvcRequestBuilders.get(uriWithContext(controllerRequestMapping, methodMapping, entity.getId().toString()));
    }
    public static MockHttpServletRequestBuilder get(String controllerRequestMapping, BaseEntity entity){
        return MockMvcRequestBuilders.get(uriWithContext(controllerRequestMapping, "", entity.getId().toString()));
    }
    public static MockHttpServletRequestBuilder get(String controllerRequestMapping, String methodMapping, String args){
        return MockMvcRequestBuilders.get(uriWithContext(controllerRequestMapping, methodMapping, args));
    }
    public static MockHttpServletRequestBuilder post(String controllerRequestMapping, Record dto) throws JsonProcessingException {
        return post(controllerRequestMapping, "", Collections.singleton(dto));
    }
    public static MockHttpServletRequestBuilder post(String controllerRequestMapping, String methodMapping, Set dto) throws JsonProcessingException {
        return MockMvcRequestBuilders.post(uriWithContext(controllerRequestMapping, methodMapping)).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto));
    }

    public static String uriWithContext(String controllerRequestMapping, String methodMapping){
        return uriWithContext(controllerRequestMapping, methodMapping, "");
    }

    public static String uriWithContext(String controllerRequestMapping, String methodMapping, String args){
        if(!controllerRequestMapping.startsWith("/"))
            controllerRequestMapping = "/" + controllerRequestMapping;
        if(!methodMapping.startsWith("/") && !controllerRequestMapping.endsWith("/") && !methodMapping.isEmpty())
            methodMapping = "/" + methodMapping;
        if(!args.startsWith("/") && !methodMapping.endsWith("/") && !args.isEmpty())
            args = "/" + args;
        return controllerRequestMapping + methodMapping + args;
    }

    public static void assertContentAndEntityIdsEqual(MockHttpServletResponse response, BaseEntity entity) throws UnsupportedEncodingException, JsonProcessingException {
        String content = response.getContentAsString();
        Assertions.assertThat(content).isNotBlank();
        Set<Integer> set = objectMapper.readValue(content, Set.class);
        Long id = Long.valueOf(set.stream().toList().get(0));
        Assertions.assertThat(id).isEqualTo(entity.getId());
    }

    public static void assertContentAndEntityEqual(MockHttpServletResponse response, Class<? extends Record> recordClass, BaseEntity entity) throws UnsupportedEncodingException, JsonProcessingException {
        String content = response.getContentAsString();
        Assertions.assertThat(content).isNotBlank();
        TestUtils.assertDtoAndEntityEqual(objectMapper.readValue(content, recordClass), entity);
    }
}
