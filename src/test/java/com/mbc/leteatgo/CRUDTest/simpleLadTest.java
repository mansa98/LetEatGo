package com.mbc.leteatgo.CRUDTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mbc.leteatgo.service.LadService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class simpleLadTest {
	
	@Autowired
	LadService ladService;

	@Test
	void test() {
		
		List<String> ladLikeList = null;
		List<String> ladDislikeList = null;
		
		String ladLike = ladService.getLadByMemberId("TestTest1").getLadLike().replace("[", "").replace("]", "");
		String ladDislike = ladService.getLadByMemberId("TestTest1").getLadDislike().replace("[", "").replace("]", "");
		
		List<String> ladLike2 = Arrays.asList("양파", "부추", "팽이버섯", "소고기", "돼지고기", "닭고기");
		
		log.info("ladLike: {}", ladLike);
		log.info("ladDislike: {}", ladDislike);
		
		String[] ladLikeSplit = ladLike.split(", ");
		
//		for (String str : ladLikeSplit) {
//			
//			str.trim();
//		}
		
		ladLikeList = Arrays.asList(ladLikeSplit);
		
		log.info("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		ladLikeList.forEach(System.out::print);
		log.info("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		ladLike2.forEach(System.out::print);
		
	}

}
