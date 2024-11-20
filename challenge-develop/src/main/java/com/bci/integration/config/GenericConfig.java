package com.bci.integration.config;

import com.bci.integration.util.PasswordValidator;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenericConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${app.password.regex}")
    private String passwordRegex;
    @Value("${app.password.error.message}")
    private String errorMessage;

    @Bean
    public PasswordValidator getPasswordValidator(){
        return new PasswordValidator(passwordRegex,errorMessage);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(
                        new Components().addSecuritySchemes(BEARER_KEY_SECURITY_SCHEME,
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info().title(applicationName));
    }

    public static final String BEARER_KEY_SECURITY_SCHEME = "bearer-key";
}
