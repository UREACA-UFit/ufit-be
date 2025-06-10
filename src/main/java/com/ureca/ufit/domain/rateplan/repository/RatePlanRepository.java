package com.ureca.ufit.domain.ratePlan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ureca.ufit.entity.RatePlan;

@Repository
public interface RatePlanRepository extends MongoRepository<RatePlan, String>, RatePlanQueryRepository {
    Page<RatePlan> findByEnabledTrueAndDeletedFalse(Pageable pageable);
}
