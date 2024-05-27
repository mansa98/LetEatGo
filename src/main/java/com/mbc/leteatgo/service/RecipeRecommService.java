package com.mbc.leteatgo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mbc.leteatgo.domain.RecipeVO;
import com.mbc.leteatgo.repository.IngrMainRepository;
import com.mbc.leteatgo.repository.IngrSubRepository;
import com.mbc.leteatgo.repository.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RecipeRecommService {
	
	@Autowired
	RecipeService recipeService;
	
	@Autowired
	RecipeRepository recipeRepository;
	
	@Autowired
	IngrMainRepository ingrMainRepository; 
	
	@Autowired
	IngrSubRepository ingrSubRepository;
	
	@Autowired
	LadService ladService;
	

	public List<RecipeVO> getRecipesRecommendation(String memberId) {
		
		long beginTime = System.currentTimeMillis();
		
		// 최종 리턴할, 추천 레시피들을 담은 리스트!
		List<RecipeVO> recipeRecommList = new ArrayList<>();
		
//		실제 member와 lad 데이터 반영 
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		// 현재 member_tbl의 실제 멤버
		//String memberId = "TestTest1";
		
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
		//List<String> ladLike = Arrays.asList("양파", "부추", "두부", "소고기", "돼지고기", "닭고기");
		//List<String> ladDislike = Arrays.asList("고추", "고수", "당근", "양고기", "호박");
		
		// 주재료에 따른 아류 부재료 확보
		// ex) 돼지고기 => 돼지고기, 다진 돼지고기, 대패 삼겹살, 대패삼겹살, ...
				
		
		// 음식별 선호도 카운트
		// 맵 구조 => 음식명 : 카운트(수)
		// Map<String, Integer> likeMap = new HashMap<>();
		// 맵 구조 => 음식번호 : 카운트(수)
		Map<Integer, Integer> likeMap = new HashMap<>();
				
		// 선호도 판정
		for (RecipeVO recipeVO : recipeService.getDatasFromDB()) {
			
			List<String> recipeIngrList = Arrays.asList(recipeVO.getRecipeIngrCombined().split(", ")); // 재료간 띄워쓰기 배제
	
			// 선호 재료 포함 수량
			List<String> ladSubLike = new ArrayList<>();
	
			for (String str : ladLikeList) {
				
				if (ingrMainRepository.findByIngrMainName(str) != null) {
					String [] strArr = ingrMainRepository.findByIngrMainName(str).split(", ");
					ladSubLike.addAll(Arrays.asList(strArr));
				}
				
			} //
			
			log.info("########### new");
			
			log.info("new ladSubLike : {}", ladSubLike);
			
			int likeCount = (int)recipeIngrList.stream().filter(x -> ladSubLike.contains(x)).count();
			
			log.info("new likeCount : " + likeCount);
			
			
			// 비선호 재료 포함 수량
			List<String> ladSubDislike = new ArrayList<>();
	
			for (String str : ladDislikeList) {
				
				if (ingrMainRepository.findByIngrMainName(str) != null) {
					String [] strArr = ingrMainRepository.findByIngrMainName(str).split(", ");
					ladSubDislike.addAll(Arrays.asList(strArr));
				}
				
			} //
			
			log.info("ladSubDislike : {}", ladSubDislike);
			
			int disLikeCount = (int)recipeIngrList.stream().filter(x -> ladSubDislike.contains(x)).count();
			
			log.info("disLikeCount : " + disLikeCount);
			
			// 포함 조건 : 선호하는 비선호 재료가 하나도 없으면서, 선호재료가 한개 이상 포함된 음식들 분류 
			if (disLikeCount == 0 && likeCount > 0) {
				likeMap.put(recipeVO.getRecipeNum(), likeCount);
			} //
		
		} // for
		
		log.info("new ***************************");
		
		// 선호하는 재료가 많은 순서대로 나열(추천)
		List<Map.Entry<Integer, Integer>> entryList = new LinkedList<>(likeMap.entrySet());
		// entryList.sort(((o1, o2) -> likeMap.get(o1.getKey()) - likeMap.get(o2.getKey()))); // 오름차순 정렬
		entryList.sort(((o1, o2) -> likeMap.get(o2.getKey()) - likeMap.get(o1.getKey()))); // 내림차순 정렬
		
		// 전체 나열(내림차순)
	//		entryList.forEach(t -> {
	//			log.info(t.getKey() + " : " + t.getValue());
	//		});
		
		log.info("new entryList size : " + entryList.size());
		
		// 상위 10%만 추천한다면...
		int limit = (int)(entryList.size() * 0.1f);
		
		log.info("new 상위 10% 음식 수량 : " + limit);
		
		entryList.stream().limit(limit).forEach(x -> { log.info("@@@@@@@@@@@@ new 선호 음식(들) : " + x); });
		
		// recipeNum 리스트
		List<Integer> recipeNumList = new ArrayList<>();
		
		// 상위 10개 추천 레시피에서 key(recipeNum)만 뽑아내서 리스트로 저장
		entryList.stream().limit(10).forEach(x -> { 
			
			recipeNumList.add(x.getKey());
		});
		
		for (int x : recipeNumList) {
			
			log.info("new 레시피 번호: {}", x);
		}
		
		// 뽑아온 recipeNum 리스트를 이용하여 recipeRecommList를 구성해줍니다.
		for (int x : recipeNumList) {
			
			recipeRecommList.add(recipeRepository.findById(x));
		}
		
		for (RecipeVO rcpVO : recipeRecommList) {
			
			log.info("●●●●●●●●● 추천 받아라!!!(제목만 확인) {}", rcpVO.getRecipeTitle());
		}
		
		long endTime = System.currentTimeMillis();
		
		log.info("실행에 걸린 시간: {}ms", endTime-beginTime);
		// 추천 레시피 리스트 리턴!
		return recipeRecommList;
	} //getRecipesRecommendation()	

	
}
