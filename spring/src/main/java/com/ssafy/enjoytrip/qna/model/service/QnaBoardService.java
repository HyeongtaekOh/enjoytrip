package com.ssafy.enjoytrip.qna.model.service;

import java.util.List;
import java.util.Optional;

import com.ssafy.enjoytrip.qna.model.dto.QnaBoardDto;
import com.ssafy.enjoytrip.qna.model.dto.QnaBoardSearchCondition;

public interface QnaBoardService {

	Optional<QnaBoardDto> getArticleById(Integer articleId);
	List<QnaBoardDto> getArticlesByCondition(QnaBoardSearchCondition condition);
	void insertQnaArticle(QnaBoardDto qnaBoardDto);
	void updateQnaArticle(QnaBoardDto qnaBoardDto);
	void deleteQnaArticle(Integer articleId);
}
