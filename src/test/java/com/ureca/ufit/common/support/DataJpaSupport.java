package com.ureca.ufit.common.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.repository.Repository;

import com.ureca.ufit.common.config.TestJpaAuditingConfig;
import com.ureca.ufit.global.config.QueryDslConfig;

import jakarta.persistence.EntityManager;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestJpaAuditingConfig.class, QueryDslConfig.class})
public abstract class DataJpaSupport extends TestContainerSupport {

	@Autowired
	protected EntityManager em;
}
