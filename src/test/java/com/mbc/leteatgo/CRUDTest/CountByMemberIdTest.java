package com.mbc.leteatgo.CRUDTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mbc.leteatgo.repository.FavRepository;

@SpringBootTest
class CountByMemberIdTest {

	@Autowired
	FavRepository favRepo;
	
	
	@Test
	void test() {
		
		int count = 0;
		
		String memberId = "TestTest1";
		
		count = favRepo.countByMemberId(memberId);
		
		System.out.println("결과:" + count);
		
	}

}
