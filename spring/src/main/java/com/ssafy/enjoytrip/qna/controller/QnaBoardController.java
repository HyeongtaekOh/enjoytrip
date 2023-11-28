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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QnaBoardController {

	private final QnaBoardService service;

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
	
	@PostMapping()
	public ResponseEntity<QnaBoardDto> registQnaArticle(@RequestBody QnaBoardDto qnaBoardDto) {
		log.info("controller | regist article : {}", qnaBoardDto);
		service.insertQnaArticle(qnaBoardDto);
		
		return new ResponseEntity<QnaBoardDto>(qnaBoardDto, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateQnaArticle(@PathVariable Integer id, @RequestBody QnaBoardDto qnaBoardDto) {
		log.info("controller | update article : {}", qnaBoardDto);
		service.updateQnaArticle(qnaBoardDto);
		
		return ResponseEntity.ok("OK");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> removeQnaArticle(@PathVariable Integer id) {
		log.info("controller | delete articleId : {}", id);
		service.deleteQnaArticle(id);
		
		return ResponseEntity.ok("OK");
	}
}
