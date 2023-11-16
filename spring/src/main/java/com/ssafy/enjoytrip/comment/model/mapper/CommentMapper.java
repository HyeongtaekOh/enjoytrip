package com.ssafy.enjoytrip.comment.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.enjoytrip.comment.model.dto.CommentDto;

@Mapper
public interface CommentMapper {
	void regist(CommentDto comment) throws SQLException;

	int update(CommentDto comment) throws SQLException;

	int delete(int commentId) throws SQLException;

	CommentDto getComment(int id) throws SQLException;

	List<CommentDto> getComments(@Param("contentId") int contentId, @Param("type") String type) throws SQLException;
}
