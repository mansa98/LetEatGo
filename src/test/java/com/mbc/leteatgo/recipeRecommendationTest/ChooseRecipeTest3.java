package com.mbc.leteatgo.recipeRecommendationTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mbc.leteatgo.domain.RecipeVO;
import com.mbc.leteatgo.service.LadService;
import com.mbc.leteatgo.service.RecipeService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class ChooseRecipeTest3 {
	
	@Autowired
	RecipeService recipeService;
	
	@Autowired
	LadService ladService;
	
	@Test
	public void test() {

//	실제 member와 lad 데이터 반영 
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		
		// 현재 member_tbl의 실제 멤버
		//String memberId = "TestTest1";
		String memberId = "Test1";
		
		// lad 테이블에서 데이터를 가져옴 (쓸데없는 [] 는 제거)
		String ladLike = ladService.getLadByMemberId(memberId).getLadLike().replace("[", "").replace("]", "");
		String ladDislike = ladService.getLadByMemberId(memberId).getLadDislike().replace("[", "").replace("]", "");
		
		// lad 테이블에서 가져온 데이터를 ,와 공백을 제거하면서 배열에 담음
		String[] ladLikeSplit = ladLike.split(", ");
		String[] ladDislikeSplit = ladDislike.split(", ");
		
		// 배열 => 리스트
		List<String> ladLikeList = Arrays.asList(ladLikeSplit);
		List<String> ladDislikeList = Arrays.asList(ladDislikeSplit);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		
		// 임의의 회원 선호/비선호 재료들 : 임의 선정
		//List<String> ladLike = Arrays.asList("양파", "부추", "팽이버섯", "소고기", "돼지고기", "닭고기");
		//List<String> ladDislike = Arrays.asList("고추", "마늘", "고수", "당근", "양고기", "케일");
		
		// 음식별 선호도 카운트
		// 맵 구조 => 음식명 : 카운트(수)
		Map<String, Integer> likeMap = new HashMap<>();
				
		// 선호도 판정
		for (RecipeVO recipeVO : recipeService.getDatasFromDB()) {
			
			List<String> recipeIngrList = Arrays.asList(recipeVO.getRecipeIngrCombined().split(", ")); // 재료간 띄워쓰기 배제

			// 선호 재료 포함 수량
			int likeCount = (int)recipeIngrList.stream().filter(x -> ladLikeList.contains(x)).count();
			
			// 비선호 재료 포함 수량
			int disLikeCount = (int)recipeIngrList.stream().filter(x -> ladDislikeList.contains(x)).count();
			
			// 포함 조건 : 선호하는 비선호 재료가 하나도 없으면서, 선호재료가 한개 이상 포함된 음식들 분류 
			if (disLikeCount == 0 && likeCount > 0) {
				likeMap.put(recipeVO.getRecipeTitle(), likeCount);
			} //
		
		} // for
		
		System.out.println("----------------------------------------------------------------------------");
		
		// 선호하는 재료가 많은 순서대로 나열(추천)
		List<Map.Entry<String, Integer>> entryList = new LinkedList<>(likeMap.entrySet());
		// entryList.sort(((o1, o2) -> likeMap.get(o1.getKey()) - likeMap.get(o2.getKey()))); // 오름차순 정렬
		entryList.sort(((o1, o2) -> likeMap.get(o2.getKey()) - likeMap.get(o1.getKey()))); // 내림차순 정렬
		
		// 전체 나열(내림차순)
//		entryList.forEach(t -> {
//			System.out.println(t.getKey() + " : " + t.getValue());
//		});
		
		log.info("entryList size : " + entryList.size());
		
		// 상위 10%만 추천한다면...
		int limit = (int)(entryList.size() * 0.1f);
		
		log.info("상위 10% 음식 수량 : " + limit);
		
		entryList.stream().limit(limit).forEach(x -> { log.info("선호 음식(들) : " + x); });
	} //	

}