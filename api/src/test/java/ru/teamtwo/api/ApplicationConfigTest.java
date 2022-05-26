package ru.teamtwo.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
public class ApplicationConfigTest {

    @Value("${spring.main.banner-mode}")
    private String valueTestString;

    @Test
    void test(){
        Assertions.assertThat(valueTestString).isEqualTo("false");
    }
}
