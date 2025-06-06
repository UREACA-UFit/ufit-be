package com.ureca.ufit.common.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@TestConfiguration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class TestJpaAuditingConfig {

	@Bean
	public AuditorAware<String> auditorProvider() {
		return new TestAuditAwareImpl();
	}
}
