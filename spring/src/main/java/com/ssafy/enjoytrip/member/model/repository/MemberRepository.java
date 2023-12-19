package com.ssafy.enjoytrip.member.model.repository;

import com.ssafy.enjoytrip.member.model.dto.MemberDto;

import java.sql.SQLException;
import java.util.Optional;

public interface MemberRepository {

    Integer duplicateUsernameCheck(String username) throws SQLException;

    Optional<MemberDto> findById(Integer userId) throws SQLException;

    Optional<MemberDto> findByUsername(String username) throws SQLException;

    void insertMember(MemberDto memberDto) throws SQLException;

    void updateMember(MemberDto memberDto) throws SQLException;

    void deleteMember(Integer userId) throws SQLException;
}
