package com.ureca.ufit.common.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.ureca.ufit.common.config.TestMongoAuditingConfig;

@DataMongoTest
@Import(TestMongoAuditingConfig.class)
public abstract class DataMongoSupport extends TestContainerSupport {

	@Autowired
	protected MongoTemplate mongoTemplate;
}
