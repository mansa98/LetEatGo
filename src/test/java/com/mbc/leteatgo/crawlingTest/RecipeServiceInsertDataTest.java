package com.mbc.leteatgo.crawlingTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mbc.leteatgo.domain.RecipeVO;
import com.mbc.leteatgo.repository.RecipeRepository;
import com.mbc.leteatgo.service.RecipeService;

import lombok.extern.slf4j.Slf4j;


@SpringBootTest
@Slf4j
class RecipeServiceInsertDataTest {

	@Autowired
	RecipeRepository recipeRepo;
	
	@Autowired
	RecipeService recipeService;
	
//	@Autowired
//	RecipeRepository recipeRepo;
	
	/** 인서트 하기 전 데이터를 깨끗하게 지워야 할때 실행합니다 */
//	 @Test
	void delete() throws IOException {
		
		recipeRepo.deleteAll();
		
		log.info("★ 삭제가 완료된 후 남아있는 데이터 카운트는? {}개", recipeRepo.count());
		
	}
	////									 next:741 next:781
	//
////// 소고기(5), 돼지고기(5), 오리고기(4), 닭고기(4), 고등어(1), 오징어(1), 미역(1), 갈치(1), 쭈꾸미(1), 두부(1), 콩나물(1), 낙지(1), 홍합(1), 연어(1)
	//
	////							3~6페이지				2페이지	 버림
	 @Test
	void test() throws IOException {
		
		log.info("검색용");
		
		List<RecipeVO> foodList = new ArrayList<>();
		
		//List<String> testStrList = null;
		
//		testStrList = recipeRepo.findByIngrName("%소고기%");
		
		foodList = recipeService.getDatas(2, "연어");
		
			
		Iterator<RecipeVO> foodListIt = foodList.iterator();
		
		//필터1
		while(foodListIt.hasNext()) {
			
			if (foodListIt.next().getRecipeTitle().matches(".*[소고기].*")) {
				
				foodListIt.remove();
			}
			
		}
		//필터2
		while(foodListIt.hasNext()) {
			
			if (foodListIt.next().getRecipeTitle().matches(".*[돼지고기].*")) {
				
				foodListIt.remove();
			}
			
		}
		//필터3
		while(foodListIt.hasNext()) {
			
			if (foodListIt.next().getRecipeTitle().matches(".*[오리고기].*")) {
				
				foodListIt.remove();
			}
			
		}
		
		/////////////////////////////////////////////////////////////////////////
		
		
		foodListIt.forEachRemaining(foodList :: add);
		
		log.info("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆ 남은 데이터 크기: {}", foodList.size());
		
		
//		for (RecipeVO recipe: foodList) {
//			log.info(recipe.toString() + "\n");
//		}
		
		log.info("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		
		List<RecipeVO> testList = recipeService.saveDatas(foodList);
		
		log.info("♬♬♬♬♬ testList의 길이: {}", testList.size());
		
		// testList.forEach(System.out::println);
		
//		for (int i = 0; i < foodList.size(); i++) {
//			
//			log.info("＆＆＆ 제목만: {}", foodList.get(i).getRecipeTitle());
//			
//		}
		
		
		
		log.info("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		
	}

}
