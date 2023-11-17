package com.ssafy.enjoytrip.attraction.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.attraction.model.dto.SidoCode;
import com.ssafy.enjoytrip.attraction.model.mapper.SidoMapper;
import com.ssafy.enjoytrip.exception.AttractionException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class SidoServiceImpl implements SidoService {
	private final SidoMapper sidoMapper;

	@Override
	@Transactional(readOnly = true)
	public List<SidoCode> findSidoCode() {
		try {
			return sidoMapper.findSidoCode();
		} catch (SQLException e) {
			log.error("시도 코드 검색 중 오류 발생");
			throw new AttractionException("시도 코드를 불러 오는 중 오류 발생 : " + e.getMessage());
		}
	}

}
