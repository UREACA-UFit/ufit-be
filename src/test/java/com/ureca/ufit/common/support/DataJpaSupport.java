package com.ureca.ufit.common.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.repository.Repository;

import com.dangsim.common.config.QueryDslConfig;
import com.dangsim.common.config.TestJpaAuditingConfig;

import jakarta.persistence.EntityManager;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestJpaAuditingConfig.class, QueryDslConfig.class})
public abstract class DataJpaSupport {

	@Autowired
	protected EntityManager em;
}
