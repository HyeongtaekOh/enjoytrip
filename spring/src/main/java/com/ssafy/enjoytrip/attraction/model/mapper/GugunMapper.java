package com.ssafy.enjoytrip.attraction.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.enjoytrip.attraction.model.dto.GugunCode;

@Mapper
public interface GugunMapper {
	List<GugunCode> findGugunCode(@Param("sidoCode") int sidoCode) throws SQLException;
}
