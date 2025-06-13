package com.ureca.ufit.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@OpenAPIDefinition(
	info = @io.swagger.v3.oas.annotations.info.Info(
		title = "UFit API API",
		description = "UFit API 명세서",
		version = "v.1.0"),
	servers = {
		@Server(url = "https://be.u-fit.site", description = "Deploy Server URL"),
		@Server(url = "http://localhost:8080", description = "Local Host URL")}
)
@Configuration
public class SwaggerConfig {

	private static final String ACCESS_TOKEN_KEY = "Access Token (Bearer)";

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.components(createComponents())
			.addSecurityItem(createSecurityRequirement())
			.info(createApiInfo());
	}

	private Components createComponents() {
		return new Components()
			.addSecuritySchemes(ACCESS_TOKEN_KEY, createAccessTokenSecurityScheme());
	}

	private SecurityRequirement createSecurityRequirement() {
		return new SecurityRequirement()
			.addList(ACCESS_TOKEN_KEY);
	}

	private SecurityScheme createAccessTokenSecurityScheme() {
		return new SecurityScheme()
			.type(SecurityScheme.Type.HTTP)
			.scheme("bearer")
			.bearerFormat("Authorization")
			.in(SecurityScheme.In.HEADER)
			.name(HttpHeaders.AUTHORIZATION);
	}

	private Info createApiInfo() {
		return new Info()
			.title("UFit API")
			.description("UFit API 명세서")
			.version("1.0.0");
	}
}
