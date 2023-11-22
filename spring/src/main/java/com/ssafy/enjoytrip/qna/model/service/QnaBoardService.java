package com.ssafy.enjoytrip.qna.model.service;

import java.util.Optional;

import com.ssafy.enjoytrip.qna.model.dto.QnaBoardDto;
import com.ssafy.enjoytrip.qna.model.dto.QnaBoardSearchCondition;
import com.ssafy.enjoytrip.qna.model.dto.QnaBoardSearchResult;

public interface QnaBoardService {

	Optional<QnaBoardDto> getArticleById(Integer articleId);
	QnaBoardSearchResult getArticlesByCondition(QnaBoardSearchCondition condition);
	void insertQnaArticle(QnaBoardDto qnaBoardDto);
	void updateQnaArticle(QnaBoardDto qnaBoardDto);
	void deleteQnaArticle(Integer articleId);
}
