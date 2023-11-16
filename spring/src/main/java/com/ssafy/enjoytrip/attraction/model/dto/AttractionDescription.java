package com.ssafy.enjoytrip.attraction.model.dto;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class AttractionDescription {

	@ApiParam(value = "관광지 고유 번호")
	private int contentId;
	@ApiParam(value = "홈페이지 주소")
	private String homepage;
	@ApiParam(value = "관광지 설명")
	private String overview;
	private String telname;
}
