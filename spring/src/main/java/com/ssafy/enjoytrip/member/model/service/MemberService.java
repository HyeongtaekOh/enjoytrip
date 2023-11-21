package com.ssafy.enjoytrip.member.model.service;

import java.util.Optional;

import com.ssafy.enjoytrip.member.model.dto.LoginVo;
import com.ssafy.enjoytrip.member.model.dto.MemberDto;

public interface MemberService {

	boolean duplicateCheck(String username);
	
	Optional<MemberDto> getMemberById(Integer userId);

	Optional<MemberDto> getMemberByUsername(String username);

	void registMember(MemberDto memberDto);
	
	void updateMember(MemberDto memberDto);
	
	void deleteMember(Integer userId);

	MemberDto loginMember(LoginVo loginVo);
}