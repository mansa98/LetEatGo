package com.mbc.leteatgo.crawlingTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mbc.leteatgo.domain.RecipeVO;
import com.mbc.leteatgo.repository.RecipeRepository;

@SpringBootTest
class RecipeDatasSelectTest {

	@Autowired
	RecipeRepository recipeRepo;
	
	@Test
	void test() {
		
		List<RecipeVO> foodList = new ArrayList<>();
		
		foodList = (List<RecipeVO>)recipeRepo.findAll();
		
		System.out.println("foodList의 길이: " + foodList.size());
		
		foodList.forEach(System.out::println);
	}

}
