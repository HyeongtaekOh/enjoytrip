package com.ssafy.enjoytrip.member.model.mapper;

import java.sql.SQLException;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.member.model.dto.MemberDto;

@Mapper
public interface MemberMapper {
	
	Integer duplicateUsernameCheck(String username) throws SQLException;

	Optional<MemberDto> findById(Integer userId) throws SQLException;

	Optional<MemberDto> findByUsername(String username) throws SQLException;

	void insertMember(MemberDto memberDto) throws SQLException;
	
	void updateMember(MemberDto memberDto) throws SQLException;
	
	void deleteMember(Integer userId) throws SQLException;
}
