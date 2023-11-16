package com.ssafy.enjoytrip.comment.model.service;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

import com.ssafy.enjoytrip.comment.model.dto.CommentDto;

public interface CommentService {
	void regist(CommentDto comment);

	void update(CommentDto comment);

	void delete(int commentId);
	
	CommentDto getComment(int id);

	List<CommentDto> getComments(int contentId, String type);

	boolean checkComment(int id, Principal principal);

}
