package com.ssafy.enjoytrip.plan.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.enjoytrip.plan.model.dto.Plan;
import com.ssafy.enjoytrip.plan.model.dto.PlanSearchCondition;
import com.ssafy.enjoytrip.plan.model.dto.PlanDto;
import com.ssafy.enjoytrip.plan.model.dto.PlanSimple;

@Mapper
public interface PlanMapper {

	Optional<Plan> findById(Integer planId) throws SQLException;

	List<PlanSimple> findByName(String name) throws SQLException;

	List<Plan> findByAttractionId(Integer attractionId) throws SQLException;

	List<Plan> findByCondition(@Param("condition") PlanSearchCondition condition, @Param("pageSize") Integer pageSize, @Param("offset") Integer offset)
			throws SQLException;

	void insertPlan(PlanDto planDto) throws SQLException;

	void insertAttractionPlan(@Param("planId") Integer planId, @Param("attractionIds") List<Integer> attractionIds)
			throws SQLException;

	void updatePlan(PlanDto planDto) throws SQLException;

	void deletePlan(Integer planId) throws SQLException;

	void deleteAttractionPlan(Integer planId) throws SQLException;

	default void insertPlanWithAttractions(PlanDto planDto) throws SQLException {
		insertPlan(planDto);
		Integer planId = planDto.getPlanId();
		insertAttractionPlan(planId, planDto.getAttractionIds());
	}

	default void updatePlanWithAttractions(PlanDto planDto) throws SQLException {
		deleteAttractionPlan(planDto.getPlanId());
		updatePlan(planDto);
		insertAttractionPlan(planDto.getPlanId(), planDto.getAttractionIds());
	}

	default void deletePlanWithAttractionsById(Integer planId) throws SQLException {
		deleteAttractionPlan(planId);
		deletePlan(planId);
	}
}
