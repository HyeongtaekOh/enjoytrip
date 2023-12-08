package com.ssafy.enjoytrip.member.model.repository;

import com.ssafy.enjoytrip.member.model.dto.MemberDto;

import java.sql.SQLException;
import java.util.Optional;

public interface MemberRepository {

    Integer duplicateUsernameCheck(String username);

    Optional<MemberDto> findById(Integer userId);

    Optional<MemberDto> findByUsername(String username);

    void insertMember(MemberDto memberDto);

    void updateMember(MemberDto memberDto);

    void deleteMember(Integer userId);
}
