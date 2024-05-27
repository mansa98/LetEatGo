package com.mbc.leteatgo.recipeRecommendationTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class JustAJavaCollectionTest {

	@Test
	void test() {
		
		Map<String, Integer> likeMap = new HashMap<>();
		
		likeMap.put("맛있는 소고기찜", 3);
		likeMap.put("맛있는 돼지고기찜", 3);
		likeMap.put("맛있는 닭고기찜", 3);
		likeMap.put("맛있는 오리고기찜", 3);
		likeMap.put("맛있는 꿩고기찜", 2);
		likeMap.put("맛있는 양고기찜", 2);
		
		// Set<String> testSet = likeMap.entrySet();
		
		List<Map.Entry<String, Integer>> entryList = new LinkedList<>(likeMap.entrySet());
		
		//String title = entryList.get(0).getKey();
		
		List<String> titleList = new ArrayList<>();
		
		
		int limit = (int)(entryList.size() * 0.1f);
		
		entryList.stream().limit(5).forEach(x -> { 
			
//			for (int i = 0; i < entryList.size(); i++) {
//				
//				titleList.add(x.getKey());
//			}
			
			titleList.add(x.getKey());
			
		});
		
		//List<String> newEntryList = entryList.stream().limit(2);
		
		//log.info("어케 생긴겨 {}", entryList.get(0).getKey());
		
		
//		for (int i = 0; i < entryList.size(); i++) {
//			
//			titleList.add(entryList.get(i).getKey());
//			
//		}
		
		//log.info("타이틀: {}", title);
		log.info("타이틀: {}", titleList);
	
	}

}
