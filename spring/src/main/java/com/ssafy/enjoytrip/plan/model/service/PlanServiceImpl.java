package com.ssafy.enjoytrip.plan.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.exception.PlanException;
import com.ssafy.enjoytrip.plan.model.dto.Plan;
import com.ssafy.enjoytrip.plan.model.dto.PlanDto;
import com.ssafy.enjoytrip.plan.model.dto.PlanSearchCondition;
import com.ssafy.enjoytrip.plan.model.mapper.PlanMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

	private final PlanMapper mapper;

	private final int PAGE_SIZE = 6;

	@Override
	@Transactional(readOnly = true)
	public Optional<Plan> findById(Integer planId) {
		try {
			return mapper.findById(planId);
		} catch (SQLException e) {
			throw new PlanException("ID로 조회 중 오류 발생 : " + e.getMessage());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Plan> findByAttractionId(Integer attractionId) {
		try {
			return mapper.findByAttractionId(attractionId);
		} catch (SQLException e) {
			throw new PlanException("관광지 ID로 조회 중 오류 발생 : " + e.getMessage());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Plan> findByCondition(PlanSearchCondition condition) {
		try {
			int pageSize = condition.getPageSize() != null && condition.getPageSize() > 0 ? condition.getPageSize()
					: PAGE_SIZE;
			return mapper.findByCondition(condition, pageSize,
					condition.getPage() != null ? (condition.getPage() - 1) * pageSize : 0);
		} catch (SQLException e) {
			throw new PlanException("검색 조건으로 조회 중 오류 발생 : " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public void insertPlanWithAttractions(PlanDto planDto) {
		try {
			mapper.insertPlanWithAttractions(planDto);
		} catch (SQLException e) {
			throw new PlanException("여행 계획 추가 중 오류 발생 : " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public void updatePlanWithAttractions(PlanDto planDto) {
		try {
			mapper.updatePlanWithAttractions(planDto);
		} catch (SQLException e) {
			throw new PlanException("여행 계획 수정 중 오류 발생 : " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public void deletePlanWithAttractionsById(Integer planId) {
		try {
			mapper.deletePlanWithAttractionsById(planId);
		} catch (SQLException e) {
			throw new PlanException("여행 계획 삭제 중 오류 발생 : " + e.getMessage());
		}
	}

}
