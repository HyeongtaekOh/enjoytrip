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
public class PlanDto {

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

	private List<Integer> attractionIds;
	
	public static PlanDto of(PlanRegistDto registDto) {
		PlanDto planDto = new PlanDto();
		planDto.setUserId(registDto.getUserId());
		planDto.setName(registDto.getName());
		planDto.setDescription(registDto.getDescription());
		planDto.setTheme(registDto.getTheme());
		planDto.setDeparturesId(registDto.getDeparturesId());
		planDto.setDeparturesName(registDto.getDeparturesName());
		planDto.setArrivalsId(registDto.getArrivalsId());
		planDto.setArrivalsName(registDto.getArrivalsName());
		planDto.setAttractionIds(registDto.getAttractionIds());
		
		return planDto;
	}
}
