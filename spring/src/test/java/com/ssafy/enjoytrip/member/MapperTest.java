package com.ssafy.enjoytrip.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MapperTest {
	
//	@Autowired
//	MemberMapper mapper;
//	
//	@Test
//	void getMapper() {
//		assertNotNull(mapper);
//	}
//	
//	@Test
//	void idCheckTest() throws Exception {
//		String userId = "aa";
//		int cnt = mapper.idCheck(userId);
//		log.debug("cnt : {}", cnt);
//		assertEquals(1, cnt);
//	}
//	
//	@Test
//	void updateMemberTest() throws Exception{
//		String userId = "aa";
//		MemberDto mem = mapper.getMember(userId);
//		mem.setPassword("bb");
//		mapper.updateMember(mem);
//		log.debug("member : {}", mem);
//		assertEquals("bb", mem.getPassword());
//	}
//	
//	@Test
//	void joinTest() throws Exception{
//		MemberDto mem = new MemberDto();
//		mem.setUserId("bb");
//		mem.setPassword("bb");
//		mem.setPhoneNumber("bb");
//		mapper.join(mem);
//		assertNotNull(mapper.getMember("bb"));
//	}
//	
}
