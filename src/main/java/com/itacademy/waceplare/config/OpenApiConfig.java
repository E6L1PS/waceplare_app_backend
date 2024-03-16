package com.itacademy.waceplare.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "Waceplare OpenAPI Specification",
                version = "1.0",
                description = "The server part of the application for the system for selling private advertisements." +
                        " This is a project under the Digital Department program.",
                contact = @Contact(
                        name = "Pesternikov Danil",
                        url = "https://t.me/gnitfihsnwod",
                        email = "danils003@mail.ru"
                ),
                license = @License(
                        name = "",
                        url = ""
                ),
                termsOfService = ""
        ),
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT token",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
