package com.ssafy.enjoytrip.qna.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.enjoytrip.qna.model.dto.QnaBoardDto;
import com.ssafy.enjoytrip.qna.model.dto.QnaBoardSearchCondition;
import com.ssafy.enjoytrip.qna.model.dto.QnaBoardSearchResult;

@Mapper
public interface QnaBoardMapper {

	Optional<QnaBoardDto> findById(Integer articleId) throws SQLException;

	Integer getTotalCountWithCondition(@Param("condition") QnaBoardSearchCondition condition) throws SQLException;
	
	List<QnaBoardDto> findByCondition(@Param("condition") QnaBoardSearchCondition condition,
			@Param("pageSize") Integer pageSize, @Param("offset") Integer offset) throws SQLException;

	void insertQnaArticle(QnaBoardDto qnaBoardDto) throws SQLException;

	void updateQnaArticle(QnaBoardDto qnaBoardDto) throws SQLException;

	void deleteQnaArticle(Integer articleId) throws SQLException;
	
	default QnaBoardSearchResult findByConditionWithPaging(QnaBoardSearchCondition condition, int pageSize) throws SQLException {
		int totalCount = getTotalCountWithCondition(condition);
		int page = condition.getPage() != null && condition.getPage() > 0 ? condition.getPage() : 1;
		int totalPage = totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1);
		int offset = condition.getPage() != null ? (condition.getPage() - 1) * pageSize : 0;
		List<QnaBoardDto> qnas = findByCondition(condition, pageSize, offset);
		QnaBoardSearchResult result = new QnaBoardSearchResult(qnas, qnas.size(), page, pageSize, totalCount, totalPage);
		
		return result;
	}
}
