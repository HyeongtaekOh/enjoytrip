package com.ssafy.enjoytrip.attraction.mapper;

import com.ssafy.enjoytrip.attraction.model.dto.AttractionInfo;
import com.ssafy.enjoytrip.attraction.model.mapper.AttractionMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AttractionMapperTest {
	
	@Autowired
	AttractionMapper mapper;
	
	@Test
	void getMapper() {
		assertNotNull(mapper);
	}
	
	@Test
	void findById() {
		int id = 125266;
		try {
			AttractionInfo a = mapper.findById(id).get();
			assertEquals("25261", a.getZipcode());
			assertEquals("20030300000000", a.getDetail().getCreatedTime());
			log.debug("attraction : {}", a);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Test
//	void findByCondition() {
//
//		int pageSize = 15;
//
//		try {
//			AttractionSearchCondition condition = new AttractionSearchCondition(0, 0, 0, "휴양림");
//			List<AttractionInfo> attrs = mapper.findByCondition(condition, pageSize, 15);
//			assertNotNull(attrs);
//			log.info("count : {}", attrs.size());
//			assertEquals(125420, attrs.get(0).getContentId());
//			assertEquals(15, attrs.size());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
}
