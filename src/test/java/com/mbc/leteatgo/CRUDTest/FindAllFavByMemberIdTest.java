package com.mbc.leteatgo.CRUDTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mbc.leteatgo.domain.FavVO;
import com.mbc.leteatgo.repository.FavRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class FindAllFavByMemberIdTest {
	
	@Autowired
	FavRepository favRepo;

	@Test
	void test() {
		
		String memberId = "TestTest1";
		
		List<FavVO> favList = new ArrayList<>();
		
		favList = favRepo.findAllByMemberId(memberId);
		
		System.out.println("길이: " + favList.size());
		
		favList.forEach(System.out::println);
		
		
		
	}

}
