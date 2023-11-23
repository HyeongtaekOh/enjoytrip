package com.ssafy.enjoytrip.qna.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QnaBoardSearchCondition {

	private String userId;
	private String username;
	private String keyword;
	private Integer page;
	private Integer pageSize; 
}
