package com.ssafy.enjoytrip.attraction.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.ssafy.enjoytrip.attraction.model.service.AttractionService;

import lombok.extern.slf4j.Slf4j;

@AutoConfigureMockMvc
@SpringBootTest(
	properties = {
			"spring.config.location=classpath:application.properties"
	},
	classes = {
			AttractionController.class
	}
)
@Slf4j
@ComponentScan(basePackages = "com.ssafy")
class AttractionControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@DisplayName("#### 관광지 id 검색 테스트 ####")
	void testGetAttractionById() throws Exception {
		int id = 125266;
		mockMvc.perform(get("/attraction/" + id))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andDo(print());
		log.debug("#### 관광지 id 검색 테스트 종료 ####");
	}

	@Test
	@Disabled
	void testAttractionController() {
		fail("Not yet implemented");
	}

}
