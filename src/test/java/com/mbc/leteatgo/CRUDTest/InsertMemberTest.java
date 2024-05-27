package com.mbc.leteatgo.CRUDTest;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.mbc.leteatgo.repository.MemberDAO;
import com.mbc.leteatgo.domain.MemberDTO;
import com.mbc.leteatgo.domain.Role;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class InsertMemberTest {
	
	@Autowired
	MemberDAO memberDAO;
	
	@Autowired
	MemberDTO memberDTO;
	
//	// @Test
//	void insertAndCountTest() throws ParseException {
//			
//		log.info("insertAndCountTest 실행상황 확인");
//		
//		memberDTO = MemberDTO.builder()
//							  .num(5)
//							  .id("Test5")
//							  .name("테스트이름5")
//							  .pw("@Test5")
//							  .nick("테스트5")
//							  .gender("m")
//							  .birthday(new SimpleDateFormat("yyyy-MM-dd").parse("001-11-30"))
//							  .email("test15@gmail.com")
//							  .mobile("010-8773-7778")
//							  .zip("7775")
//							  .roadAddress("경기도 수원시 영통구 광교호수공원로 10")
//							  .jibunAddress("경기도 수원시 영통구 하동 10")
//							  .detailAddress("호수 안의 103호")
//							  .build();
//		
//		log.info("memberDTO 주입 상황 확인: {}", memberDTO);
//		
//		memberDAO.insertMember(memberDTO);
//		
////		log.info("현재 회원수: {}", memberDAO.selectMembersCount());
//	
//	}// insertAndCountTest()
	
	
//	@Test
//	void insertManyMembersTest() throws Exception {
//		
//		// List<MemberDTO> memberList = new ArrayList<>();
//		
//		MemberDTO memberDTO = new MemberDTO();
//		
//			memberDTO = MemberDTO.builder()
//								 .num()
//								 .id("Test0")
//								 .name("테스트")
//								 .pw("@Test")
//								 .nick("테스트")
//								 .mobile("010-7777-7777")
//								 .email("test@gmail.com")
//								 .zip("777")
//								 .roadAddress("경기도 수원시 영통구 광교호수공원로 10")
//								 .jibunAddress("경기도 수원시 영통구 하동 10")
//								 .detailAddress("호수 안")
//								 .joinDate(new SimpleDateFormat("yyyy-MM-dd").parse("000-01-1"))
//								 .build();
//			
//			log.info("for문 속의 memberDTO 현황: {}", memberDTO.toString());
//			
//			memberDAO.insertMember(memberDTO);
//       } insertManyMembersTest
	
	@Test
	@Transactional
	@Rollback(false)
	// @Rollback(true)
	public void test() throws ParseException {
//		memberDTO = memberDAO.selectMember("test1");
		
		memberDTO = MemberDTO.builder()
				  .memberNum(1)
				  .memberId("Test1")
				  .memberName("테스트이름1")
				  .memberPw("@Test1")
				  .memberNick("테스트1")
				  .memberGender("m")
				  .memberBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("001-11-0"))
				  .memberEmail("test1@gmail.com")
				  .memberMobile("010-871-7777")
				  .memberZip("771")
				  .memberRoadAddress("경기도 수원시 영통구 광교호수공원로 10")
				  .memberJibunAddress("경기도 수원시 영통구 하동 10")
				  .memberDetailAddress("호수 안의 11호")
				  .build();
		
		log.info("memberDTO : {}", memberDTO.toString());
		boolean result = false;
		
		try {
			memberDAO.insertMember(memberDTO);
			result = true;
		} catch (Exception e) {
			log.error("InsertMemberTest.insertMember : {}", e);
			e.printStackTrace();
		}
		
		assertTrue(result);
		
		result = false;
		
		try {
			memberDAO.insertRole(new Role(memberDTO.getMemberId(), "ROLE_USER"));
			result = true;
		} catch (Exception e) {
			log.error("InsertMemberTest.insertRole : {}", e);
			e.printStackTrace();
		}
		
	}
	
} // InsertMemeberTest

 


