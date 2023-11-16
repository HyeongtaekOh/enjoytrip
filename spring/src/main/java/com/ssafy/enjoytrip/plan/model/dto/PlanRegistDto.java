package com.ssafy.enjoytrip.plan.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PlanRegistDto {

	private Integer userId;
	private String name;
	private String description;
	private String theme;
	private Integer departuresId;
	private String departuresName;
	private Integer arrivalsId;
	private String arrivalsName;
	
	private List<Integer> attractionIds;
}
