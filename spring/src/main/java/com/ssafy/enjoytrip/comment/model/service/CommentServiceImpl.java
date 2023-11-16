package com.ssafy.enjoytrip.comment.model.service;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.comment.model.dto.CommentDto;
import com.ssafy.enjoytrip.comment.model.mapper.CommentMapper;
import com.ssafy.enjoytrip.exception.CommentException;

@Service
public class CommentServiceImpl implements CommentService {

	private CommentMapper mapper;

	@Autowired
	public CommentServiceImpl(CommentMapper mapper) {
		super();
		this.mapper = mapper;
	}

	@Override
	public void regist(CommentDto comment) {
		try {
			mapper.regist(comment);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CommentException("댓글 등록 오류 발생");
		}

	}

	@Override
	public void update(CommentDto comment) {
		try {
			mapper.update(comment);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CommentException("댓글 수정 오류 발생");
		}
	}

	@Override
	public void delete(int commentId) {
		try {
			mapper.delete(commentId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CommentException("댓글 삭제 오류 발생");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public CommentDto getComment(int id) {
		try {
			return mapper.getComment(id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CommentException("댓글 불러오는 중 오류 발생");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<CommentDto> getComments(int contentId, String type) {
		try {
			return mapper.getComments(contentId, type);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CommentException("댓글 불러오는 중 오류 발생");
		}
	}

	@Override
	public boolean checkComment(int id, Principal principal) {
		try {
			CommentDto comment = mapper.getComment(id);
			String username = principal.getName();
			if (comment.getUserName().equals(username)) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CommentException("댓글 불러오는 중 오류 발생");
		}
	}
}
