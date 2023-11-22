package com.ssafy.enjoytrip.attraction.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.enjoytrip.attraction.model.dto.AttractionSearchCondition;
import com.ssafy.enjoytrip.attraction.model.dto.AttractionSearchResult;
import com.ssafy.enjoytrip.attraction.model.dto.AttractionInfo;

@Mapper
public interface AttractionMapper {

	Optional<AttractionInfo> findById(int id) throws SQLException;
	
	List<AttractionInfo> findByIds(@Param("ids") List<Integer> ids) throws SQLException;
	
	Integer getTotalCountWithCondition(@Param("condition") AttractionSearchCondition condition) throws SQLException;
	
	List<AttractionInfo> findByCondition(@Param("condition") AttractionSearchCondition condition, @Param("pageSize") int pageSize, @Param("offset") int offset) throws SQLException;
	
	default AttractionSearchResult findByConditionWithPaging(AttractionSearchCondition condition, int pageSize) throws SQLException {
		int totalCount = getTotalCountWithCondition(condition);
		int page = condition.getPage() != null && condition.getPage() > 0 ? condition.getPage() : 1;
		int totalPage = totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1);
		int offset = condition.getPage() != null ? (condition.getPage() - 1) * pageSize : 0;
		List<AttractionInfo> attractions = findByCondition(condition, pageSize, offset);
		AttractionSearchResult result = new AttractionSearchResult(attractions, attractions.size(), page, pageSize, totalCount, totalPage);
		return result;
	}
}
