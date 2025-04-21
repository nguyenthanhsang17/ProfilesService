package com.a2m.profileservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Spring Boot API", version = "1.0", description = "API Documentation"))
@SecurityScheme(
        name = "bearerAuth",              // Tên scheme, bạn có thể đặt tùy ý
        type = SecuritySchemeType.HTTP,   // Loại xác thực là HTTP
        scheme = "bearer",                // Sử dụng Bearer Token
        bearerFormat = "JWT"              // Định dạng token (thường là JWT)
)
public class OpenAPIConfig {
}
