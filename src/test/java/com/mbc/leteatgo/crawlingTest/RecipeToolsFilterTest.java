package com.mbc.leteatgo.crawlingTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mbc.leteatgo.domain.RecipeVO;
import com.mbc.leteatgo.service.RecipeService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class RecipeToolsFilterTest {
	
	@Autowired
	RecipeService recipeService;

	@Test
	void test() {
		
		List<String> recipeTools = new ArrayList<>();
		
		String[] recipeToolsArray = {"볼", "냄비", "도마", "조리용나이프", "채반", "요리스푼", "접시", "국자", "대접", "믹싱볼", "채반", "프라이팬", "조리용스푼", "완성그릇", "주방가위", "키친타올", "요리집게", "위생장갑", "볶음팬", "면기"};
		
		recipeTools.addAll(Arrays.asList(recipeToolsArray));
		
		for (String tool : recipeTools) {
			
			log.info("◆ 아주 빡치게 하는 조리도구들: " + tool + "\n");
			
		}
		
		List<RecipeVO> recipeList = new ArrayList<>();
		List<String> seasoningList = new ArrayList<>();
		
		recipeList = recipeService.getDatasFromDB();
		
		for (int i = 0; i < recipeList.size(); i++) {
			
			seasoningList.add(recipeList.get(i).getRecipeIngrSeasoning());
			
		}
		
		for (String seasoning : seasoningList) {
			
			log.info("▶ 양념재료(아마도): " + seasoning);
			
		}
		
		
		for (int i = 0; i < seasoningList.size(); i++) {
			
			
			for (int j = 0; j < recipeTools.size(); j++) {
				
				if (seasoningList.get(i).contains(recipeTools.get(j))) {
					
					seasoningList.set(i, "");
				}
			}
			
		}
		
		
		for (String seasoning : seasoningList) {
			
			log.info("♣ 최종 양념재료 제발!!" + seasoning);
		}
		
		
	}

}
