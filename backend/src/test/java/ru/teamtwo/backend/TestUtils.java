package ru.teamtwo.backend;

import org.assertj.core.api.Assertions;
import ru.teamtwo.backend.models.BaseEntity;

import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.time.Instant;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class TestUtils {
    public static final Long EMPTY_ID = 99999L;
    public static final Long UNIMPORTANT_ID = 1L;
    public static final Long SPECIFIC_ID = 12345L;
    public static final String UNIMPORTANT_STRING = "abc";
    public static final Integer UNIMPORTANT_NUMBER = 1;
    public static final Integer NEW_NUMBER = 2;
    public static final Integer ANOTHER_NEW_NUMBER = 3;
    public static final Instant UNIMPORTANT_INSTANT = Instant.now();

    public static void assertDtoAndEntityEqual(Record dto, BaseEntity entity) {
        Set<RecordComponent> dtoFields = Arrays.stream(dto.getClass().getRecordComponents())
                .collect(Collectors.toSet());
        Set<Method> entityMethods = Arrays.stream(entity.getClass().getMethods())
                .filter(method -> method.getName().startsWith("get")
                        && !method.getName().equals("getClass")
                        && !method.getName().equals("getHibernateLazyInitializer")
                )
                .collect(Collectors.toSet());

        Assertions.assertThat(dtoFields.size()).withFailMessage(() -> {
            StringJoiner stringJoinerDtoFields = new StringJoiner(",");
            StringJoiner stringJoinerEntityMethods = new StringJoiner(",");
            dtoFields.stream().map(RecordComponent::getName).forEach(stringJoinerDtoFields::add);
            entityMethods.stream().map(Method::getName).forEach(stringJoinerEntityMethods::add);
            return "Different set sizes: " + dtoFields.size() + " : " + stringJoinerDtoFields + ";\n" + entityMethods.size() + " : " + stringJoinerEntityMethods;
        }).isEqualTo(entityMethods.size());

        entityMethods.forEach(method -> {
            try {
                String methodName = method.getName().substring(3).toLowerCase(Locale.ROOT);
                Object methodValue = method.invoke(entity);
                boolean isMethodValueBaseEntity = methodValue instanceof BaseEntity;
                RecordComponent fieldWithSameName = dtoFields.stream()
                        .filter((recordComponent1) -> {
                            String fieldName = recordComponent1.getName().toLowerCase(Locale.ROOT);
                            if (isMethodValueBaseEntity) {
                                fieldName = fieldName.substring(0, fieldName.length() - 2);
                            }
                            return fieldName.equals(methodName);
                        }).findFirst().orElseThrow(() -> new Exception(methodName));

                Object fieldValue = fieldWithSameName.getAccessor().invoke(dto);
                if (isMethodValueBaseEntity) {
                    Assertions.assertThat(fieldValue).isEqualTo(((BaseEntity) methodValue).getId());
                } else {
                    Assertions.assertThat(fieldValue).isEqualTo(methodValue);
                }
            } catch (Exception e) {
                Assertions.fail(e.getMessage());
            }
        });
    }
}
