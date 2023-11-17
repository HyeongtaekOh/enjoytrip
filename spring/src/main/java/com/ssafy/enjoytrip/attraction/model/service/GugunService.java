package com.ssafy.enjoytrip.attraction.model.service;

import java.util.List;

import com.ssafy.enjoytrip.attraction.model.dto.GugunCode;

public interface GugunService {
	List<GugunCode> findGugunCode(int sidoCode);
}
