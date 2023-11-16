package com.ssafy.enjoytrip.member.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.exception.MemberException;
import com.ssafy.enjoytrip.member.model.dto.MemberDto;
import com.ssafy.enjoytrip.model.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Api(value = "회원 API", description = "회원 정보")
//@CrossOrigin(origins = { "*" })
public class MemberController {

	private final MemberService memberService;

	@ApiOperation(value = "중복 username 체크", notes = "username이 존재하는지 확인")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "BAD REQUEST") })
	@PostMapping("/duplicate-check")
	public ResponseEntity<String> duplicateCheck(@RequestBody String username) throws Exception {
		log.debug("duplicateCheck username : {}", username);
		if (!memberService.duplicateCheck(username)) {
			return new ResponseEntity<String>("OK", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("NO", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/{userId}")
	public ResponseEntity<MemberDto> getMemberByUserId(@PathVariable Integer userId) {
		
		Optional<MemberDto> m = memberService.getMemberById(userId);
		
		if (!m.isPresent()) {
			throw new MemberException("존재하지 않는 사용자 고유번호입니다.");
		}
		
		MemberDto member = m.get();
		member.setPassword(null);
		
		return ResponseEntity.ok(member);
	}

//	@ApiOperation(value = "회원 정보 업데이트", notes = "회원 아이디 기반으로 비밀번호 업데이트")
//	@ApiResponses(value = {
//			@ApiResponse(code = 200, message = "OK"),
//			@ApiResponse(code = 400, message = "Bad Request")
//	})
//	@PutMapping("/{userId}")
//	public ResponseEntity<?> updateMember(@PathVariable String userId, @RequestBody MemberDto member) {
//		log.debug("update : {}", member);
//		if (!member.getPassword().equals(member.getPasswordCheck()))
//			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
//		memberService.updateMember(member);
//		return new ResponseEntity<String>("OK", HttpStatus.OK);
//	}
//
//	@ApiOperation(value = "비밀번호 변경", notes = "회원 아이디와 전화번호를 기반으로 비밀번호 변경")
//	@ApiResponses(value = {
//			@ApiResponse(code = 200, message = "OK"),
//			@ApiResponse(code = 400, message = "Bad Request")
//	})
//	@PatchMapping("/{userId}")
//	public ResponseEntity<?> findPassword(@PathVariable String userId, @RequestBody MemberDto member) {
//		log.debug("find : {}", member);
//		if (!member.getPassword().equals(member.getPasswordCheck()))
//			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
//		memberService.findPassword(member);
//		return new ResponseEntity<String>("OK", HttpStatus.OK);
//	}
//	
//	@ApiOperation(value = "회원 삭제", notes = "회원 아이디를 기반으로 회원 정보 삭제")
//	@ApiResponses(value = {
//			@ApiResponse(code = 200, message = "OK"),
//			@ApiResponse(code = 400, message = "Bad Request")
//	})
//	@DeleteMapping("/{userId}")
//	public ResponseEntity<String> deleteMember(@PathVariable String userId) {
//		log.debug("delete : {}", userId);
//		memberService.deleteMember(userId);
//		return new ResponseEntity<String>("OK", HttpStatus.OK);
//	}

}
