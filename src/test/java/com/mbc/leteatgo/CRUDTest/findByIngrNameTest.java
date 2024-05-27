package com.mbc.leteatgo.CRUDTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mbc.leteatgo.domain.RecipeVO;
import com.mbc.leteatgo.repository.RecipeRepository;

@SpringBootTest
class findByIngrNameTest {

	@Autowired
	RecipeRepository recipeRepo;
	
	@Test
	void test() {
		
		List<RecipeVO> testList = null;
		List<String> testStrList = null;
		
		//testList = recipeRepo.findByIngrName("%소고기%");
		testStrList = recipeRepo.findByIngrName("%소고기%");
		
		
		System.out.println("▲크기: " + testStrList.size());
		
		testStrList.forEach(System.out::println);
	}

}
