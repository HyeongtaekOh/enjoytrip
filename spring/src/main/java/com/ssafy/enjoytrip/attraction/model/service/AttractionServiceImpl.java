package com.ssafy.enjoytrip.attraction.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.attraction.model.dto.AttractionSearchCondition;
import com.ssafy.enjoytrip.attraction.model.dto.AttractionInfo;
import com.ssafy.enjoytrip.attraction.model.mapper.AttractionMapper;
import com.ssafy.enjoytrip.exception.AttractionException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AttractionServiceImpl implements AttractionService {

	private final AttractionMapper attractionMapper;
	
	private final int PAGE_SIZE = 15;

	@Override
	@Transactional(readOnly = true)
	public Optional<AttractionInfo> findById(int id) {
		try {
			return attractionMapper.findById(id);
		} catch (SQLException e) {
			log.error("ID로 검색 중 오류 발생");
			throw new AttractionException("ID로 검색 중 오류 발생 : " + e.getMessage());
		}
	}

	@Override
	public List<AttractionInfo> findByIds(List<Integer> ids) {
		try {
			return attractionMapper.findByIds(ids);
		} catch (SQLException e) {
			log.error("ID 리스트로 검색 중 오류 발생");
			throw new AttractionException("ID 리스트로 검색 중 오류 발생 : " + e.getMessage());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<AttractionInfo> findByCondition(AttractionSearchCondition condition, int page) {
		try {
			return attractionMapper.findByCondition(condition, PAGE_SIZE, (page - 1) * PAGE_SIZE);
		} catch (SQLException e) {
			log.error("검색 조건으로 검색 중 오류 발생");
			throw new AttractionException("검색 조건으로 검색 중 오류 발생 : " + e.getMessage());
		}		
	}
	
}
