package com.ssafy.enjoytrip.plan.model.dto;

import java.util.List;

import com.ssafy.enjoytrip.attraction.model.dto.AttractionInfoSimple;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Plan {
	
	private Integer planId;
	private Integer userId;
	private String name;
	private String description;
	private String theme;
	private String createdTime;
	private String modifiedTime;
	private Integer departuresId;
	private String departuresName;
	private Integer arrivalsId;
	private String arrivalsName;
	private Integer hit;

	private String username;
	private List<AttractionInfoSimple> attractions;
	
}
