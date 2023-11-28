package com.ssafy.enjoytrip.plan.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.exception.PlanException;
import com.ssafy.enjoytrip.plan.model.dto.Plan;
import com.ssafy.enjoytrip.plan.model.dto.PlanDto;
import com.ssafy.enjoytrip.plan.model.dto.PlanRegistDto;
import com.ssafy.enjoytrip.plan.model.dto.PlanSearchCondition;
import com.ssafy.enjoytrip.plan.model.dto.PlanSearchResult;
import com.ssafy.enjoytrip.plan.model.service.PlanService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/plan")
@RequiredArgsConstructor
public class PlanController {

	private final PlanService service;

	@GetMapping("/{id}")
	public ResponseEntity<?> getPlanById(@PathVariable Integer id) {
		log.debug("id로 plan 조회");
		Optional<Plan> p = service.findById(id);

		if (p.isPresent()) {
			Plan plan = p.get();
			log.debug("plan : {}", plan);
			return new ResponseEntity<Plan>(plan, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/course")
	public ResponseEntity<?> getPlansWithAttraction(Integer attractionId) {
		log.debug("관광지 id로 plan 조회");
		List<Plan> plans = service.findByAttractionId(attractionId);
		
		if (plans != null && plans.size() > 0) {
			log.debug("plans : {}", plans);
			return new ResponseEntity<List<Plan>>(plans, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping
	public ResponseEntity<?> getPlansByCondition(PlanSearchCondition condition) {

		log.debug("검색 조건에 맞는 plan 조회");
		log.debug("condition : {}", condition);
		PlanSearchResult result = service.findByCondition(condition);

		if (result == null) {
			throw new PlanException("plan 조건 검색 결과 오류 발생");
		}
		
		List<Plan> plans = result.getPlans();
		
		if (plans != null && plans.size() > 0) {
			log.debug("plan search by condition result : {}", result);
			return new ResponseEntity<PlanSearchResult>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}

	@PostMapping
	public ResponseEntity<Map<String, Integer>> registPlan(@RequestBody PlanRegistDto registDto) {

		log.debug("plan 등록");
		log.debug("regist planRegistDto : {}", registDto);
		
		PlanDto planDto = PlanDto.of(registDto);

		service.insertPlanWithAttractions(planDto);
		return new ResponseEntity<Map<String, Integer>>(Collections.singletonMap("planId", planDto.getPlanId()), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Integer>> updatePlan(@PathVariable Integer id, @RequestBody PlanDto planDto) {
		log.debug("plan update");
		log.debug("update plan id : {}", id);
		log.debug("update dto : {}", planDto);
		
		planDto.setPlanId(id);
		service.updatePlanWithAttractions(planDto);
		return new ResponseEntity<Map<String, Integer>>(Collections.singletonMap("planId", planDto.getPlanId()), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePlan(@PathVariable Integer id) {
		
		log.debug("plan 삭제");
		log.debug("delete planId : {}", id);
		
		service.deletePlanWithAttractionsById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
