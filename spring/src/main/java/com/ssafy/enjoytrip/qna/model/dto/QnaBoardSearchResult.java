package com.ssafy.enjoytrip.qna.model.dto;

import java.util.List;


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
public class QnaBoardSearchResult {

	private List<QnaBoardDto> qnas;
	private Integer count;
	private Integer page;
	private Integer pageSize;
	private Integer totalCount;
	private Integer totalPage;
}
