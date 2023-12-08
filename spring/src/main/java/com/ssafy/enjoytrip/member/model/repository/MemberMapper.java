package com.ssafy.enjoytrip.member.model.repository;

import java.sql.SQLException;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.member.model.dto.MemberDto;

@Mapper
public interface MemberMapper extends MemberRepository {

}
