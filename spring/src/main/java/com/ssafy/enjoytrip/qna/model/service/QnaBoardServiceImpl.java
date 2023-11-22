package com.ssafy.enjoytrip.qna.model.service;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.exception.QnaBoardException;
import com.ssafy.enjoytrip.qna.model.dto.QnaBoardDto;
import com.ssafy.enjoytrip.qna.model.dto.QnaBoardSearchCondition;
import com.ssafy.enjoytrip.qna.model.dto.QnaBoardSearchResult;
import com.ssafy.enjoytrip.qna.model.mapper.QnaBoardMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class QnaBoardServiceImpl implements QnaBoardService {

	private final QnaBoardMapper mapper;
	private final int PAGE_SIZE = 9;
	
	public Optional<QnaBoardDto> getArticleById(Integer articleId) {
		log.info("get article by id : {}", articleId);
		try {
			return mapper.findById(articleId);
		} catch (SQLException e) {
			// TODO Auto-generated catch 
			e.printStackTrace();
			throw new QnaBoardException("id로 검색 중 오류 발생");
		}
	}

	@Override
	public QnaBoardSearchResult getArticlesByCondition(QnaBoardSearchCondition condition) {
		log.info("get articles by condition : {}", condition);
		try {
			int pageSize = condition.getPageSize() != null && condition.getPageSize() > 0 ? condition.getPageSize()
					: PAGE_SIZE;
			return mapper.findByConditionWithPage(condition, pageSize);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new QnaBoardException("상세 검색 중 오류 발생");
		}
	}

	@Override
	public void insertQnaArticle(QnaBoardDto qnaBoardDto) {
		log.info("insert QnA : {}", qnaBoardDto);
		try {
			mapper.insertQnaArticle(qnaBoardDto);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new QnaBoardException("QnA 등록 중 오류 발생");
		}
	}

	@Override
	public void updateQnaArticle(QnaBoardDto qnaBoardDto) {
		log.info("update QnA : {}", qnaBoardDto);
		try {
			mapper.updateQnaArticle(qnaBoardDto);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new QnaBoardException("QnA 수정 중 오류 발생");
		}
	}

	@Override
	public void deleteQnaArticle(Integer articleId) {
		log.info("delete QnA : {}", articleId);
		try {
			mapper.deleteQnaArticle(articleId);;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new QnaBoardException("QnA 삭제 중 오류 발생");
		}
	}

}
