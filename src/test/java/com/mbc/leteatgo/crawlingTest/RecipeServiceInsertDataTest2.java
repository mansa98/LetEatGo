package com.mbc.leteatgo.crawlingTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mbc.leteatgo.domain.RecipeVO;
import com.mbc.leteatgo.service.RecipeService;

import lombok.extern.slf4j.Slf4j;


@SpringBootTest
@Slf4j
class RecipeServiceInsertDataTest2 {

	@Autowired
	RecipeService recipeService;
	
//	@Autowired
//	RecipeRepository recipeRepo;
	
	@Test
	void test() throws IOException {
		
		log.info("검색용");
		
		List<RecipeVO> foodList = new ArrayList<>();
		
		foodList = recipeService.getDatas(1, "소고기");
		
//		for (RecipeVO recipe: foodList) {
//			log.info(recipe.toString() + "\n");
//		}
		
		log.info("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		
		RecipeVO testVO = recipeService.saveData(foodList.get(3));
		
		log.info("testVO: {}", testVO);
		
		log.info("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		
	}

}
