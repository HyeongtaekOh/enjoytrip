package com.ssafy.enjoytrip.attraction.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.attraction.model.dto.GugunCode;
import com.ssafy.enjoytrip.attraction.model.mapper.GugunMapper;
import com.ssafy.enjoytrip.attraction.model.mapper.SidoMapper;
import com.ssafy.enjoytrip.exception.AttractionException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class GugunServiceImpl implements GugunService{
	private final GugunMapper gugunMapper;
	
	@Override
	@Transactional(readOnly = true)
	public List<GugunCode> findGugunCode(int sidoCode) {
		try {
			return gugunMapper.findGugunCode(sidoCode);
		} catch (SQLException e) {
			log.error("구군 코드 검색 중 오류 발생");
			throw new AttractionException("구군 코드를 불러 오는 중 오류 발생 : " + e.getMessage());
		}
	}
	

}
