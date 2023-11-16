package com.ssafy.enjoytrip.plan.model.service;

import java.util.List;
import java.util.Optional;

import com.ssafy.enjoytrip.plan.model.dto.Plan;
import com.ssafy.enjoytrip.plan.model.dto.PlanSearchCondition;
import com.ssafy.enjoytrip.plan.model.dto.PlanDto;
import com.ssafy.enjoytrip.plan.model.dto.PlanSimple;

public interface PlanService {

	Optional<Plan> findById(Integer planId);

	List<PlanSimple> findByName(String name);

	List<Plan> findByAttractionId(Integer attractionId);
	
	List<Plan> findByCondition(PlanSearchCondition condition, Integer page);

	void insertPlanWithAttractions(PlanDto planDto);
	
	void updatePlanWithAttractions(PlanDto planDto);
	
	void deletePlanWithAttractionsById(Integer planId);
}
