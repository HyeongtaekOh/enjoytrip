package com.ssafy.enjoytrip.attraction.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.attraction.model.dto.AttractionSearchCondition;
import com.ssafy.enjoytrip.attraction.model.dto.AttractionInfo;
import com.ssafy.enjoytrip.attraction.model.service.AttractionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/attraction")
@Api(value = "관광지 API", description = "관광지 정보")
//@CrossOrigin(origins = {"*"})
public class AttractionController {
	
	private final AttractionService attractionService;
	
	@ApiOperation(value = "관광지 ID 조회", notes = "고유번호(id)에 해당하는 관광지 정보 조회")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK", response = AttractionInfo.class),
			@ApiResponse(code = 204, message = "No Content"),
			@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping("/{id}")
	public ResponseEntity<?> getAttractionById(@PathVariable int id) {
		
		log.debug("id : {}", id);
		Optional<AttractionInfo> a = attractionService.findById(id);
		if (a.isPresent()) {
			AttractionInfo attraction = a.get();
			log.debug("attraction : {}", attraction);
			return new ResponseEntity<AttractionInfo>(attraction, HttpStatus.OK);			
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@ApiOperation(value = "관광지 조건 검색", notes = "검색 조건(시/도, 구/군, 관광지 유형) 기반으로 검색")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK", response = AttractionInfo.class, responseContainer = "List"),
			@ApiResponse(code = 204, message = "No Content"),
			@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping("")
	public ResponseEntity<?> getAttractionsByCondition(@RequestParam(defaultValue = "0") int sidoCode,
			@RequestParam(defaultValue = "0") int gugunCode, 
			@RequestParam(defaultValue = "0") int type, 
			@RequestParam(defaultValue = "") String keyword, 
			@RequestParam(defaultValue = "1") int page) {
		
		log.debug("sidoCode = {}, gugunCode = {}, type = {}, keyword = {}, page = {}", sidoCode, gugunCode, type, keyword, page);
		AttractionSearchCondition condition = new AttractionSearchCondition(sidoCode, gugunCode, type, keyword);
		List<AttractionInfo> list = attractionService.findByCondition(condition, page);
		log.debug("list : {}", list);
		
		if (list != null && list.size() > 0) {
			return new ResponseEntity<List<AttractionInfo>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
}
