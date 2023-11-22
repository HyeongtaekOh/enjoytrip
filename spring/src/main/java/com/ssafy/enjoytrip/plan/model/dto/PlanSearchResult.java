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
public class PlanSearchResult {
	
	private List<Plan> plans;
	private Integer count;
	private Integer page;
	private Integer pageSize;
	private Integer totalCount;
	private Integer totalPage;
}
