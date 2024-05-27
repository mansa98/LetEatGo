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
class RecipeServiceCrawlingTest {

	@Autowired
	RecipeService recipeService;
	
	//
	// 소고기, 돼지고기, 오리고기, 닭고기, 고등어, 오징어, 미역, 갈치, 쭈꾸미, 두부, 콩나물, 낙지, 홍합, 연어
	//
	@Test
	void test() throws IOException {
		
		List<RecipeVO> foodList = new ArrayList<>();
		
		foodList = recipeService.getDatas(1, "");
		
		for (RecipeVO recipe : foodList) {
			
			log.info("★★★★★★ recipeVO: {}", recipe.toString());
			
		}
		
	}

}
