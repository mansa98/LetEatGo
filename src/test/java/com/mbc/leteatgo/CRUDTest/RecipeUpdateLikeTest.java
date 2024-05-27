package com.mbc.leteatgo.CRUDTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mbc.leteatgo.domain.RecipeVO;
import com.mbc.leteatgo.repository.RecipeRepository;
import com.mbc.leteatgo.service.RecipeService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class RecipeUpdateLikeTest {

	@Autowired
	RecipeService recipeService;
	
	@Autowired
	RecipeRepository recipeRepo;
	
	@Test
	void test() {
		
		RecipeVO recipeVO = recipeService.selectRecipe(1);
		
		
		//System.out.println("☆ 좋아요 업데이트가 반영되기 전: \t" + recipeVO.getRecipeLike());
		
		//recipeVO.setRecipeLike(recipeVO.getRecipeLike() + 1);
		
		//RecipeVO newRecipeVO = recipeService.updateLikeCount(recipeVO);
		//recipeRepo.updateLike(recipeVO.getRecipeLike(), recipeVO.getRecipeNum());
		//boolean result = recipeService.updateLike(recipeVO);
		
		//log.info("왜 나만 버그 나 ㅅㅂ: {}", result);
		
		// System.out.println("★ 좋아요 업데이트가 반영된 이후: \t" + newRecipeVO.getRecipeLike());
		
	}

}
