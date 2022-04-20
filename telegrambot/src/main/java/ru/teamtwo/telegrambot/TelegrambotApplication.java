package ru.teamtwo.telegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "ru.teamtwo.*" })
@EntityScan("ru.teamtwo.*")
@EnableFeignClients
public class TelegrambotApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegrambotApplication.class, args);
    }
}
