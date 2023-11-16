package com.ssafy.enjoytrip.qna.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.enjoytrip.qna.model.dto.QnaBoardDto;

@Mapper
public interface QnaBoardMapper {

	Optional<QnaBoardDto> findById(Integer articleId) throws SQLException;

	List<QnaBoardDto> findByCondition(@Param("userId") String userId, @Param("keyword") String keyword,
			@Param("pageSize") Integer pageSize, @Param("offset") Integer offset) throws SQLException;

	void insertQnaArticle(QnaBoardDto qnaBoardDto) throws SQLException;

	void updateQnaArticle(QnaBoardDto qnaBoardDto) throws SQLException;

	void deleteQnaArticle(Integer articleId) throws SQLException;
}
