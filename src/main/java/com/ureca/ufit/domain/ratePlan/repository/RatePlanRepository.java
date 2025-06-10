package com.ureca.ufit.domain.ratePlan.repository;

import org.springframework.data.domain.Pageable;
import com.ureca.ufit.entity.RatePlan;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RatePlanRepository extends MongoRepository<RatePlan, String> {
    Page<RatePlan> findByEnabledTrueAndDeletedFalse(Pageable pageable);
}