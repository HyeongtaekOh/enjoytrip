package com.ssafy.enjoytrip.attraction.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.enjoytrip.attraction.model.dto.AttractionSearchCondition;
import com.ssafy.enjoytrip.attraction.model.dto.AttractionInfo;

@Mapper
public interface AttractionMapper {

	Optional<AttractionInfo> findById(int id) throws SQLException;
	List<AttractionInfo> findByCondition(@Param("condition") AttractionSearchCondition condition, @Param("pageSize") int pageSize, @Param("offset") int offset) throws SQLException;
}
