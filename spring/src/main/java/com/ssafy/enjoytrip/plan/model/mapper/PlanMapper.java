package com.ssafy.enjoytrip.plan.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.enjoytrip.plan.model.dto.Plan;
import com.ssafy.enjoytrip.plan.model.dto.PlanDto;
import com.ssafy.enjoytrip.plan.model.dto.PlanSearchCondition;
import com.ssafy.enjoytrip.plan.model.dto.PlanSearchResult;

@Mapper
public interface PlanMapper {

	Optional<Plan> findById(Integer planId) throws SQLException;

	List<Plan> findByAttractionId(@Param("attractionId") Integer attractionId) throws SQLException;

	Integer getTotalCountWithCondition(@Param("condition") PlanSearchCondition condition) throws SQLException;
	
	List<Plan> findByCondition(@Param("condition") PlanSearchCondition condition, @Param("pageSize") Integer pageSize, @Param("offset") Integer offset)
			throws SQLException;

	void insertPlan(PlanDto planDto) throws SQLException;

	void insertAttractionPlan(@Param("planId") Integer planId, @Param("attractionIds") List<Integer> attractionIds)
			throws SQLException;

	void updatePlan(PlanDto planDto) throws SQLException;

	void deletePlan(Integer planId) throws SQLException;

	void deleteAttractionPlan(Integer planId) throws SQLException;
	
	default PlanSearchResult findByConditionWithPaging(PlanSearchCondition condition, int pageSize) throws SQLException {
		int totalCount = getTotalCountWithCondition(condition);
		int page = condition.getPage() != null && condition.getPage() > 0 ? condition.getPage() : 1;
		int totalPage = totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1);
		int offset = condition.getPage() != null ? (condition.getPage() - 1) * pageSize : 0;
		List<Plan> plans = findByCondition(condition, pageSize, offset);
		PlanSearchResult result = new PlanSearchResult(plans, plans.size(), page, pageSize, totalCount, totalPage);
		
		return result;
	}

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
