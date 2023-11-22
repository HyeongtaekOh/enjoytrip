package com.ssafy.enjoytrip.plan.model.service;

import java.util.List;
import java.util.Optional;

import com.ssafy.enjoytrip.plan.model.dto.Plan;
import com.ssafy.enjoytrip.plan.model.dto.PlanDto;
import com.ssafy.enjoytrip.plan.model.dto.PlanSearchCondition;

public interface PlanService {

	Optional<Plan> findById(Integer planId);

	List<Plan> findByAttractionId(Integer attractionId);
	
	List<Plan> findByCondition(PlanSearchCondition condition);

	void insertPlanWithAttractions(PlanDto planDto);
	
	void updatePlanWithAttractions(PlanDto planDto);
	
	void deletePlanWithAttractionsById(Integer planId);
}
