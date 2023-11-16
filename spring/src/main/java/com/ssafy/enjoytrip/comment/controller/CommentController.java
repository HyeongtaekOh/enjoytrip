package com.ssafy.enjoytrip.comment.controller;

import java.security.Principal;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.comment.model.dto.CommentDto;
import com.ssafy.enjoytrip.comment.model.service.CommentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@Api(value = "Comment API")
//@CrossOrigin(origins = { "*" })
public class CommentController {
	private final CommentService service;

	@ApiOperation(value = "댓글 등록", notes = "새로운 댓글 등록")
	@PostMapping()
	public ResponseEntity<?> registComment(@RequestBody CommentDto commentDto){
		log.info("controller | regist comment : {}", commentDto);
		service.regist(commentDto);
		return new ResponseEntity<CommentDto>(commentDto, HttpStatus.CREATED);
	}

	@ApiOperation(value = "댓글 수정", notes = "고유 번호(ID)에 해당하는 댓글 내용 수정")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateComment(@PathVariable Integer id, @RequestBody CommentDto commentDto, Principal principal){
		log.info("controller | update comment : {}", commentDto);
		if(!service.checkComment(id, principal))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		service.update(commentDto);
		return ResponseEntity.ok("OK");
	}
	
	@ApiOperation(value = "댓글 삭제", notes = "고유 번호(ID)에 해당하는 댓글 내용 삭제")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteComment(@PathVariable Integer id, Principal principal){
		log.info("controller | delete comment : {}", id);
		if(!service.checkComment(id, principal))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		service.delete(id);
		return ResponseEntity.ok("OK");
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCommentById(@PathVariable Integer id){
		log.info("controller | get comment");
		return new ResponseEntity<CommentDto>(service.getComment(id), HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<?> getCommentsWithContentId(@RequestParam Integer contentId, @RequestParam String type){
		log.info("controller | get comments");
		List<CommentDto> list = service.getComments(contentId, type);
		if(list != null && list.size() > 0) {
			log.info("controller | list = {}", list);
			return ResponseEntity.ok(list);
		}else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
}
