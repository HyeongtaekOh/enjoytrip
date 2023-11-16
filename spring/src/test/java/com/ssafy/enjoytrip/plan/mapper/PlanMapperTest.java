package com.ssafy.enjoytrip.plan.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import com.ssafy.enjoytrip.plan.model.dto.Plan;
import com.ssafy.enjoytrip.plan.model.dto.PlanSearchCondition;
import com.ssafy.enjoytrip.plan.model.dto.PlanDto;
import com.ssafy.enjoytrip.plan.model.dto.PlanSimple;
import com.ssafy.enjoytrip.plan.model.mapper.PlanMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PlanMapperTest {
	
	@Autowired
	private PlanMapper mapper;
	
	
	@Test
	void getMapper() {
		assertNotNull(mapper);
	}
	
	@Test
	void findById() {
		
		log.debug("find plan by id");
		int id = 1;
		
		try {
			Plan plan = mapper.findById(id).get();
			log.debug("plan name : {}", plan.getName());
			log.debug("plan attractions : {}", plan.getAttractions());	
			log.debug("plan : {}", plan);
			assertNotNull(plan);
			assertEquals("plan1", plan.getName());
			assertEquals(10, plan.getAttractions().size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void findByName() {
		log.debug("find plans by plan name");
		try {
			List<PlanSimple> plans = mapper.findByName("plan");
			assertNotNull(plans);
			assertEquals(1, plans.size());
			assertEquals("plan1", plans.get(0).getName());
			log.debug("plans : {}", plans);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	void findByAttractionId() {
		log.debug("find plans by attraction_id test");
		try {
			List<Plan> plans = mapper.findByAttractionId(125406);
			assertNotNull(plans);
			log.debug("plans : {}", plans);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void findByCondition() {
		
		int pageSize = 15;
		log.debug("find plans by condition test");
		List<Integer> aids = new ArrayList<Integer>();
		PlanSearchCondition condition = new PlanSearchCondition(1, "plan", "Trip", aids);
		
		try {
			List<Plan> plans = mapper.findByCondition(condition, pageSize, 0);
			assertNotNull(plans);
			log.debug("plans : {}", plans);
			assertEquals(1, plans.size());
			condition.getAttractionIds().add(125406);
			condition.getAttractionIds().add(125405);
			plans = mapper.findByCondition(condition, pageSize, 0);
			assertNotNull(plans);
			log.debug("plans : {}", plans);
			assertEquals(0, plans.size());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	void insertPlanWithAttractions() {
		
		log.debug("insert plan test");
		
		PlanDto planDto = new PlanDto();
		planDto.setUserId(1);
		planDto.setName("plan2");
		planDto.setDescription("두번째 여행 계획");
		planDto.setTheme("For Fun");
		
		List<Integer> aids = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			aids.add(125410 + i);
		}
		
		planDto.setDeparturesId(125410);
		planDto.setDeparturesName("국립 청옥산자연휴양림");
		planDto.setArrivalsId(125419);
		planDto.setArrivalsName("국립 천관산자연휴양림");
		planDto.setAttractionIds(aids);
		
		try {
			mapper.insertPlanWithAttractions(planDto);
			log.debug("insert 성공");
			Plan plan = mapper.findById(planDto.getPlanId()).get();
			assertNotNull(plan);
			log.debug("plan : {}", plan);
			assertEquals(10, plan.getAttractions().size());
			assertEquals(125419, plan.getAttractions().get(9).getContentId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Test
	void updatePlan() {
		log.debug("update test");
		PlanDto planDto = new PlanDto();
		planDto.setPlanId(1);
		planDto.setName("updated plan");
		planDto.setDescription("계획 수정!");
		planDto.setTheme("For Fun");
		
		List<Integer> attractionIds = new ArrayList<Integer>();
		
		for (int i = 0; i < 7; i++) {
			attractionIds.add(125406 + i);
		}
		
		planDto.setAttractionIds(attractionIds);
		
		try {
			Plan plan = mapper.findById(planDto.getPlanId()).get();
			assertNotNull(plan);
			log.debug("old plan : {}", plan);
			mapper.updatePlanWithAttractions(planDto);
			plan = mapper.findById(planDto.getPlanId()).get();
			assertNotNull(plan);
			log.debug("new plan : {}", plan);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void deletePlanById() {
		log.debug("delete test");
		int id = 1;
		try {
			Plan plan = mapper.findById(id).get();
			assertNotNull(plan);
			log.debug("");
			mapper.deletePlanWithAttractionsById(id);
			assertFalse(mapper.findById(id).isPresent());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
