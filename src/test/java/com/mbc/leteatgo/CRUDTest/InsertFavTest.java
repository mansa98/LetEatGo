package com.mbc.leteatgo.CRUDTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mbc.leteatgo.domain.FavVO;
import com.mbc.leteatgo.service.FavService;

@SpringBootTest
class InsertFavTest {
	
	@Autowired
	FavService favService;

	@Test
	void test() {
		
		FavVO favVO = FavVO.builder()
							.favNum(1)
							.memberId("Test1")
							.recipeNum(900)
							.build();
		
		favService.insertFav(favVO);
		
	}

}
