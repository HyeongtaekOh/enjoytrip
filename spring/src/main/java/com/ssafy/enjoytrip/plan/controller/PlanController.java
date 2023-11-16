package com.ssafy.enjoytrip.plan.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.plan.model.dto.Plan;
import com.ssafy.enjoytrip.plan.model.dto.PlanDto;
import com.ssafy.enjoytrip.plan.model.dto.PlanRegistDto;
import com.ssafy.enjoytrip.plan.model.dto.PlanSearchCondition;
import com.ssafy.enjoytrip.plan.model.service.PlanService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/plan")
@RequiredArgsConstructor
@Api(value = "여행 계획 API")
//@CrossOrigin(origins = {"*"})
public class PlanController {

	private final PlanService service;

	@ApiOperation(value = "여행 계획 ID 검색", notes = "고유번호(id)에 해당하는 여행 계획 리스트 조회\n여행 계획 기본 정보 + 여행 코스에 포함된 관광지 정보 조회")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK", response = Plan.class),
			@ApiResponse(code = 204, message = "No Content"),
			@ApiResponse(code = 400, message = "Bad Request")
	})
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

	@ApiOperation(value = "특정 관광지를 코스에 포함한 여행 계획 검색", 
				  notes = "관광지 고유번호(attractionId)를 받아 해당 관광지를 여행 코스에 포함하고 있는 여행 계획 리스트 조회\n"
				        + "여행 계획 기본 정보 + 여행 코스에 포함된 관광지 정보 조회")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK", response = Plan.class, responseContainer = "List"),
			@ApiResponse(code = 204, message = "No Content"),
			@ApiResponse(code = 400, message = "Bad Request")
	})
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

	@ApiOperation(value = "여행 계획 조건 검색", 
				  notes = "검색 조건에 해당하는 여행 계획 정보 조회\n"
				  		+ "검색 조건 : 게시자 ID(userId), 검색어(keyword, 제목 or 내용 or 출발지 이름 or 도착지 이름), 테마 (theme), 코스 내 관광지 ID 리스트(attractionIds)\n"
						+ "여행 계획 기본 정보 + 여행 코스에 포함된 관광지 정보 조회")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK", response = Plan.class, responseContainer = "List"),
			@ApiResponse(code = 204, message = "No Content"),
			@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping
	public ResponseEntity<?> getPlansByCondition(PlanSearchCondition condition, Integer page) {

		log.debug("검색 조건에 맞는 plan 조회");
		log.debug("condition : {}", condition);
		List<Plan> plans = service.findByCondition(condition, page);

		if (plans != null && plans.size() > 0) {
			log.debug("plans : {}", plans);
			return new ResponseEntity<List<Plan>>(plans, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}

	@ApiOperation(value = "신규 여행 계획 등록", notes = "여행 계획 기본 정보 + 여행 코스(관광지 ID 리스트)를 입력받아 신규 계획 정보 등록")
	@PostMapping
	public ResponseEntity<Map<String, Integer>> registPlan(@RequestBody PlanRegistDto registDto) {

		log.debug("plan 등록");
		log.debug("regist planRegistDto : {}", registDto);
		
		PlanDto planDto = PlanDto.of(registDto);

		service.insertPlanWithAttractions(planDto);
		return new ResponseEntity<Map<String, Integer>>(Collections.singletonMap("planId", planDto.getPlanId()), HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "여행 계획 수정", notes = "여행 계획 기본 정보 + 여행 코스(관광지 ID 리스트)를 입력받아 계획 정보 수정")
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Integer>> updatePlan(@PathVariable Integer id, @RequestBody PlanDto planDto) {
		log.debug("plan update");
		log.debug("update plan id : {}", id);
		log.debug("update dto : {}", planDto);
		
		planDto.setPlanId(id);
		service.updatePlanWithAttractions(planDto);
		return new ResponseEntity<Map<String, Integer>>(Collections.singletonMap("planId", planDto.getPlanId()), HttpStatus.OK);
	}
	
	@ApiOperation(value = "여행 계획 삭제", notes = "고유 번호(ID)에 해당하는 여행 계획 삭제")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePlan(@PathVariable Integer id) {
		
		log.debug("plan 삭제");
		log.debug("delete planId : {}", id);
		
		service.deletePlanWithAttractionsById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
