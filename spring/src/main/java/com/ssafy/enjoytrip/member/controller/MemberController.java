package com.ssafy.enjoytrip.member.controller;

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

import com.ssafy.enjoytrip.exception.DuplicatedMemberException;
import com.ssafy.enjoytrip.exception.MemberException;
import com.ssafy.enjoytrip.member.model.dto.MemberDto;
import com.ssafy.enjoytrip.member.model.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/duplicate-check")
	public ResponseEntity<String> duplicateCheck(@RequestBody String username) throws Exception {
		log.debug("duplicateCheck username : {}", username);
		if (!memberService.duplicateCheck(username)) {
			return new ResponseEntity<String>("OK", HttpStatus.OK);
		} else {
			throw new DuplicatedMemberException("이미 존재하는 아이디입니다.");
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

	@PutMapping("/{userId}")
	public ResponseEntity<?> updateMember(@PathVariable Integer userId, @RequestBody MemberDto member) {
		log.debug("update : {}", member);
		member.setUserId(userId);
		memberService.updateMember(member);
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteMember(@PathVariable Integer userId) {
		log.debug("delete : {}", userId);
		memberService.deleteMember(userId);
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}

}
