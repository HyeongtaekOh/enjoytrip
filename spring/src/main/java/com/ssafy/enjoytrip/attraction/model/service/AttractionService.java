package com.ssafy.enjoytrip.attraction.model.service;

import java.util.List;
import java.util.Optional;

import com.ssafy.enjoytrip.attraction.model.dto.AttractionDescription;
import com.ssafy.enjoytrip.attraction.model.dto.AttractionSearchCondition;
import com.ssafy.enjoytrip.attraction.model.dto.AttractionSearchResult;
import com.ssafy.enjoytrip.attraction.model.dto.AttractionInfo;

public interface AttractionService {

	Optional<AttractionInfo> findById(int id);
	List<AttractionInfo> findByIds(List<Integer> ids);
	AttractionSearchResult findByCondition(AttractionSearchCondition condition, int page);

	Optional<AttractionDescription> findDescriptionById(int id);
}
