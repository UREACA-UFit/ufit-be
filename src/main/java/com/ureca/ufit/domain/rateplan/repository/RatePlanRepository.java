package com.ureca.ufit.domain.rateplan.repository;

import com.ureca.ufit.entity.RatePlan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatePlanRepository extends MongoRepository<RatePlan, String> {

}
