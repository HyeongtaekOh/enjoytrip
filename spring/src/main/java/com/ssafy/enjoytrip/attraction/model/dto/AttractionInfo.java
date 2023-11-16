package com.ssafy.enjoytrip.attraction.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiParam;
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
public class AttractionInfo {

	@ApiParam(value = "관광지 고유 번호")
	private int contentId;
	@ApiParam(value = "관광지 유형 번호")
	private int contentTypeId;
	@ApiParam(value = "이름")
	private String title;
	@ApiParam(value = "주소 1")
	private String addr1;
	@ApiParam(value = "주소 2")
	private String addr2;
	@ApiParam(value = "우편 번호")
	private String zipcode;
	@ApiParam(value = "전화 번호")
	private String tel;
	@ApiParam(value = "이미지 주소 1")
	private String firstImage;
	@ApiParam(value = "이미지 주소 2")
	private String firstImage2;
	@ApiParam(value = "조회수")
	private int readcount;
	@ApiParam(value = "시/도 코드")
	private int sidoCode;
	@ApiParam(value = "시/군/구 코드")
	private int gugunCode;
	@ApiParam(value = "위도")
	private double latitude;
	@ApiParam(value = "경도")
	private double longitude;
	@ApiParam(value = "Map Level")
	private String mlevel;
	
	@JsonIgnore
	private AttractionDetail detail;
	@JsonIgnore
	private AttractionDescription description;
	
}
