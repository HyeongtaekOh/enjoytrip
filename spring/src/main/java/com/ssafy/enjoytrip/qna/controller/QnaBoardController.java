package com.ssafy.enjoytrip.qna.controller;

import java.util.List;
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

import com.ssafy.enjoytrip.exception.QnaBoardException;
import com.ssafy.enjoytrip.qna.model.dto.QnaBoardDto;
import com.ssafy.enjoytrip.qna.model.dto.QnaBoardSearchCondition;
import com.ssafy.enjoytrip.qna.model.dto.QnaBoardSearchResult;
import com.ssafy.enjoytrip.qna.model.service.QnaBoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/qna")
@RequiredArgsConstructor
@Api(value = "Q&A 게시판 API")
//@CrossOrigin(origins = {"*"})
public class QnaBoardController {

	private final QnaBoardService service;
	
	@ApiOperation(value = "ID 검색", notes = "고유번호(id)에 해당하는 Q&A 게시물 조회")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK", response = QnaBoardDto.class),
			@ApiResponse(code = 204, message = "No Content"),
			@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping("/{id}")
	public ResponseEntity<?> getArticleById(@PathVariable Integer id) {
		
		log.info("controller | get article by id : {}", id);
		Optional<QnaBoardDto> q = service.getArticleById(id);
		
		if (q.isPresent()) {
			QnaBoardDto article = q.get();
			log.info("article = {}", article);
			return ResponseEntity.ok(article);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiOperation(value = "상세 검색", notes = "검색 조건에 해당하는 Q&A 게시물 조회\n"
			+ "검색 조건 : 게시자 ID (userId), 검색어 (keyword, 제목 or 내용), 페이지 번호 (page), 페이지 크기 (pageSize)")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK", response = QnaBoardSearchResult.class),
			@ApiResponse(code = 204, message = "No Content"),
			@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping()
	public ResponseEntity<?> getArticlesByCondition(QnaBoardSearchCondition condition) {
		log.info("controller | get articles by condition : {}", condition);
		QnaBoardSearchResult result = service.getArticlesByCondition(condition);
		
		if (result == null) {
			throw new QnaBoardException("qna 조건 검색 결과 오류 발생");
		}
		
		List<QnaBoardDto> qnas = result.getQnas();
		
		if (qnas != null && qnas.size() > 0) {
			log.info("controller | list = {}", qnas);
			return ResponseEntity.ok(result);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiOperation(value = "게시물 등록", notes = "신규 Q&A 게시물 등록")
	@PostMapping()
	public ResponseEntity<QnaBoardDto> registQnaArticle(@RequestBody QnaBoardDto qnaBoardDto) {
		log.info("controller | regist article : {}", qnaBoardDto);
		service.insertQnaArticle(qnaBoardDto);
		
		return new ResponseEntity<QnaBoardDto>(qnaBoardDto, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "게시물 수정", notes = "고유 번호(ID)에 해당하는 Q&A 게시물 정보 (제목 / 내용) 수정")
	@PutMapping("/{id}")
	public ResponseEntity<String> updateQnaArticle(@PathVariable Integer id, @RequestBody QnaBoardDto qnaBoardDto) {
		log.info("controller | update article : {}", qnaBoardDto);
		service.updateQnaArticle(qnaBoardDto);
		
		return ResponseEntity.ok("OK");
	}
	
	@ApiOperation(value = "Q&A 게시물 삭제", notes = "고유 번호(ID)에 해당하는 Q&A 게시물 삭제")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> removeQnaArticle(@PathVariable Integer id) {
		log.info("controller | delete articleId : {}", id);
		service.deleteQnaArticle(id);
		
		return ResponseEntity.ok("OK");
	}
}
