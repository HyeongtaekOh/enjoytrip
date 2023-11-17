package com.ssafy.enjoytrip.qna.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class QnaBoardDto {

	private Integer articleId;
	private String userId;
	private String subject;
	private String content;
	private Integer hits;
	private String createdTime;
	private String modifiedTime;

	private String username;
}
