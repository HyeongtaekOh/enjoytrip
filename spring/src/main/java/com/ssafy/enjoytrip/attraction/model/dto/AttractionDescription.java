package com.ssafy.enjoytrip.attraction.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class AttractionDescription {

	private int contentId;
	private String homepage;
	private String overview;
	private String telname;
}
