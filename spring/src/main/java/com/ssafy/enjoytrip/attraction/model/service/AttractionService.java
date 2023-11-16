package com.ssafy.enjoytrip.attraction.model.service;

import java.util.List;
import java.util.Optional;

import com.ssafy.enjoytrip.attraction.model.dto.AttractionSearchCondition;
import com.ssafy.enjoytrip.attraction.model.dto.AttractionInfo;

public interface AttractionService {

	Optional<AttractionInfo> findById(int id);
	List<AttractionInfo> findByCondition(AttractionSearchCondition condition, int page);
}
