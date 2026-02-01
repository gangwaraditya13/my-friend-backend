package com.adish.myfriend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    SecurityScheme basicAuthScheme = new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("basic");

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MyFriend API")
                        .version("1.0.0")
                        .description(
                                "Backend APIs for MyFriend application"
                        )
                        .contact(new Contact()
                                .name("Aditya Kumar")
                                .email("gangwaraditya13@gmail.com")
                                .url("https://github.com/gangwaraditya13"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0"))
                )
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Server"),
                        new Server().url("https://my-friend-backend.onrender.com")
                                .description("Production Server")
                ));
    }
}
