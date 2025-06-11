package com.ureca.ufit.common.support;

import static lombok.AccessLevel.*;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
public abstract class TestContainerSupport {

	private static final String POSTGRESQL_IMAGE = "postgres:16";
	private static final String MONGO_IMAGE = "mongo:8.0.5";
	private static final String REDIS_IMAGE = "redis:7.2.5";
	private static final int POSTGRESQL_PORT = 5432;
	private static final int MONGO_PORT = 27017;
	private static final int REDIS_PORT = 6379;

	private static final PostgreSQLContainer<?> POSTGRESQL;
	private static final MongoDBContainer MONGO;
	private static final GenericContainer<?> REDIS;

	static {
		POSTGRESQL = new PostgreSQLContainer<>(DockerImageName.parse(POSTGRESQL_IMAGE))
			.withExposedPorts(POSTGRESQL_PORT)
			.withReuse(true);
		MONGO = new MongoDBContainer(DockerImageName.parse(MONGO_IMAGE))
			.withExposedPorts(MONGO_PORT)
			.withReuse(true);
		REDIS = new GenericContainer<>(DockerImageName.parse(REDIS_IMAGE))
			.withExposedPorts(REDIS_PORT)
			.withReuse(true);

		POSTGRESQL.start();
		MONGO.start();
		REDIS.start();
	}

	@DynamicPropertySource
	public static void setUp(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.driver-class-name", POSTGRESQL::getDriverClassName);
		registry.add("spring.datasource.url", POSTGRESQL::getJdbcUrl);
		registry.add("spring.datasource.username", POSTGRESQL::getUsername);
		registry.add("spring.datasource.password", POSTGRESQL::getPassword);

		registry.add("spring.data.mongodb.host", MONGO::getHost);
		registry.add("spring.data.mongodb.port", () -> String.valueOf(MONGO.getMappedPort(MONGO_PORT)));

		registry.add("spring.data.redis.host", REDIS::getHost);
		registry.add("spring.data.redis.port", () -> String.valueOf(REDIS.getMappedPort(REDIS_PORT)));

	}
}
