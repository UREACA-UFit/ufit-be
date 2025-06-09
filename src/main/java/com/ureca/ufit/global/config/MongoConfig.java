package com.ureca.ufit.global.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import com.ureca.ufit.global.util.DateToLocalDateTimeKstConverter;
import com.ureca.ufit.global.util.LocalDateTimeToDateKstConverter;

@Configuration
@EnableMongoAuditing
public class MongoConfig {

	@Bean
	public MongoCustomConversions customConversions(
		LocalDateTimeToDateKstConverter localDateTimeToDateKstConverter,
		DateToLocalDateTimeKstConverter dateToLocalDateTimeKstConverter) {

		return new MongoCustomConversions(Arrays.asList(
			localDateTimeToDateKstConverter,
			dateToLocalDateTimeKstConverter
		));
	}
}
