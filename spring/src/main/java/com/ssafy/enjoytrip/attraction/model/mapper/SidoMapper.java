package com.ssafy.enjoytrip.attraction.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.attraction.model.dto.SidoCode;

@Mapper
public interface SidoMapper {
	List<SidoCode> findSidoCode() throws SQLException;

}
