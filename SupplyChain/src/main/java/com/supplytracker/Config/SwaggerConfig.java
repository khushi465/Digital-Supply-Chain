package com.supplytracker.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                new Info().title("Digital Supply Chain ").description("By Team")
        ).tags(Arrays.asList(
                new Tag().name("User Auth").description("Register, Login and Logout users"),
                new Tag().name("User").description("get all users logged in and update user role"),
                new Tag().name("Item").description("add and get items"),
                new Tag().name("Shipment").description("assign items for shipment")

        ));
    }
}
