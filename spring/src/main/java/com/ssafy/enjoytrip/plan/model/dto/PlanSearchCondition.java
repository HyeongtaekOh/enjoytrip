package com.ssafy.enjoytrip.plan.model.dto;

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
public class PlanSearchCondition {

	private Integer userId;
	private String keyword;
	private String theme;
	private List<Integer> attractionIds;
	
}
