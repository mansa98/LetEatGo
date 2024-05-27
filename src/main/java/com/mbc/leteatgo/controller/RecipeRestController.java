package com.mbc.leteatgo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mbc.leteatgo.domain.FavVO;
import com.mbc.leteatgo.service.FavService;
import com.mbc.leteatgo.service.RecipeService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/recipe")
@Slf4j
public class RecipeRestController {
	
	@Autowired
	RecipeService recipeService;
	
	@Autowired
	FavService favService;

	@PostMapping("/raiseLike")
	public ResponseEntity<String> raiseLike(@RequestParam("recipeLike") int recipeLike,
											@RequestParam("recipeNum") int recipeNum,
											Model model) {
//	public ResponseEntity<String> raiseLike(@RequestParam("map1") Map<String, Integer> map1,
//											@RequestParam("map2") Map<String, Integer> map2) {
		
		ResponseEntity<String> responseEntity = null;
		
		log.info("rasieLike 인자 확인 부분////////////////////////////////////");
		//log.info("인자 확인 들어갑니다 yo! {}개!", map1.get());
		log.info("#inc 인자 확인 들어갑니다 yo! {}개!", recipeLike);
		log.info("#inc 인자 확인 들어갑니다 yo! {}!", recipeNum);
		
		String result = "";
		
		String msg = "";
		
		try {
			
			// TODO: DB 체크
			boolean success = false;
			
			success = recipeService.increaseLike(recipeNum);
			
			if (success == true) {
				
				result = "업데이트(증가) 성공";
			} else {
				
				result = "업데이트(증가) 실패";
			}
			
			
			log.info("--- #inc result : {}", result);
			
			if (result.equals("업데이트(증가) 성공")) {
				msg = "좋아요 +1";
				
				responseEntity = new ResponseEntity<>(msg, HttpStatus.OK);
				
				model.addAttribute("newLike", recipeService.selectRecipe(recipeNum).getRecipeLike());
				
				
			} else if (result.equals("업데이트(증가) 실패")) {
				msg = "좋아요 +0";

				responseEntity = new ResponseEntity<>(msg, HttpStatus.OK);
				
			}
			
			log.info("전송되는 메시지: {}", msg);
			
		} catch (Exception e) {
			log.error("Survey Error : {}", e);
			e.printStackTrace();
			
			String errMsg = "에러 났어 슈발!";

			// 실패 코드(417) : 내부 서버 에러
			responseEntity = new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		
		
		return responseEntity;
	}// rasiseLike()
	
	
	@PostMapping("/decreaseLike")
	public ResponseEntity<String> decreaseLike(@RequestParam("recipeLike") int recipeLike,
											@RequestParam("recipeNum") int recipeNum,
											Model model) {
//	public ResponseEntity<String> raiseLike(@RequestParam("map1") Map<String, Integer> map1,
//											@RequestParam("map2") Map<String, Integer> map2) {
		
		ResponseEntity<String> responseEntity = null;
		
		log.info("decreaseLike 인자 확인 부분////////////////////////////////////");
		//log.info("인자 확인 들어갑니다 yo! {}개!", map1.get());
		log.info("#dec 인자 확인 들어갑니다 yo! {}개!", recipeLike);
		log.info("#dec 인자 확인 들어갑니다 yo! {}!", recipeNum);
		
		String result = "";
		
		String msg = "";
		
		try {
			
			// TODO: DB 체크
			boolean success = false;
			
			success = recipeService.decreaseLike(recipeNum);
			
			if (success == true) {
				
				result = "업데이트(감소) 성공";
			} else {
				
				result = "업데이트(감소) 실패";
			}
			
			
			log.info("--- #dec result : {}", result);
			
			if (result.equals("업데이트(감소) 성공")) {
				msg = "좋아요 -1";
				
				responseEntity = new ResponseEntity<>(msg, HttpStatus.OK);
				
				model.addAttribute("newLike", recipeService.selectRecipe(recipeNum).getRecipeLike());
				
				
			} else if (result.equals("업데이트(감소) 실패")) {
				msg = "좋아요 +0";

				responseEntity = new ResponseEntity<>(msg, HttpStatus.OK);
				
			}
			
			log.info("전송되는 메시지: {}", msg);
			
		} catch (Exception e) {
			log.error("Survey Error : {}", e);
			e.printStackTrace();
			
			String errMsg = "업데이트(감소) 에러 났어 슈발!";

			// 실패 코드(417) : 내부 서버 에러
			responseEntity = new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		
		
		return responseEntity;
	}// decreaseLike()
	
	
	@PostMapping("/addFav")
	public ResponseEntity<String> addFav(@RequestParam("recipeNum") int recipeNum) {
		
		ResponseEntity<String> responseEntity = null;
		
		log.info("addFav 인자확인: {}", recipeNum);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String memberId = auth.getName();
		
		FavVO favVO = FavVO.builder()
						   .memberId(memberId)
						   .recipeNum(recipeNum)
						   .build();
		
		String result = "";
		
		String msg = "";
		
		try {
			
			// TODO: DB 체크
			boolean success1 = true;
			boolean success2 = false;
			
			List<FavVO> favList = new ArrayList<>();
			
			// 체크해야될거: 해당 로그인 유저의 id랑 recipeNum으로 가져온 fav 리스트 내 요소의 memberId가 일치 혹은 불일치
			// 해당 레시피 번호(recipeNum)로 select 하는 fav 데이터가 존재한다면
			if (favService.getFavByRecipeNum(recipeNum) != null) {
				
				favList = favService.getFavByRecipeNum(recipeNum);
				
				// 존재하는 데이터중에서 현재 로그인한 유저의 id(memberID)와 일치하는 fav 요소가 있다면 
				// 이미 해당 레시피에 대한 로그인 유저의 즐겨찾기가 존재하는것!
				for (int i = 0; i < favList.size(); i++) {
					
					// 필터 걸리면 false로 상태 변경!
					if (favList.get(i).getMemberId().equals(memberId)) {
						success1 = false;
					} 
					
				}// for
				
				if (success1 == true) {
					
					success2 = favService.insertFav(favVO);
					
				}// if
				
				
			}// if
			
			// 해당 레시피 번호(recipeNum)로 select 하는 fav 데이터가 존재하지 않는다면 바로 즐겨찾기 추가
			if (favService.getFavByRecipeNum(recipeNum) == null) {
				
				success2 = favService.insertFav(favVO);
				
			}// 
			
			if (success2 == true) {
				
				result = "즐겨찾기(저장) 성공";
			} else {
				
				result = "즐겨찾기(저장) 실패";
			}
			
			
			log.info("--- #addFav result : {}", result);
			
			if (result.equals("즐겨찾기(저장) 성공")) {
				msg = "즐겨찾기가 성공적으로 저장되었습니다.";
				
				responseEntity = new ResponseEntity<>(msg, HttpStatus.OK);
								
			} else if (result.equals("즐겨찾기(저장) 실패")) {
				msg = "즐겨찾기 저장에 실패하였습니다.";

				responseEntity = new ResponseEntity<>(msg, HttpStatus.OK);
				
			}
			
			log.info("전송되는 메시지: {}", msg);
			
		} catch (Exception e) {
			log.error("Survey Error : {}", e);
			e.printStackTrace();
			
			String errMsg = "즐겨찾기(저장) 에러 났어 슈발!";

			// 실패 코드(417) : 내부 서버 에러
			responseEntity = new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		
		
		
		return responseEntity;
	}// addFav()
	
	
	@PostMapping("/delFav")
	public ResponseEntity<String> delFav(@RequestParam("recipeNum") int recipeNum) {
		
		ResponseEntity<String> responseEntity = null;
		
		log.info("delFav 인자확인: {}", recipeNum);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String memberId = auth.getName();
		
		
		String result = "";
		
		String msg = "";
		
		
		FavVO favVO = new FavVO(); 
				
		List<FavVO> favList = favService.getFavByRecipeNum(recipeNum);
		
		for (int i = 0; i < favList.size(); i++) {
			
			if (favList.get(i).getMemberId().equals(memberId)) {
				
				favVO = favList.get(i);
			}
		}//
		
		int favNum = favVO.getFavNum();
		
		log.info("§ 고유값인 favNum: {}", favNum);
		
		try {
			
			// TODO: DB 체크
			boolean success = false;
			
			success = favService.deleteFav(favNum);
			
			if (success == true) {
				
				result = "즐겨찾기(삭제) 성공";
			} else {
				
				result = "즐겨찾기(삭제) 실패";
			}
			
			
			log.info("--- #delFav result : {}", result);
			
			if (result.equals("즐겨찾기(삭제) 성공")) {
				msg = "즐겨찾기가 성공적으로 삭제되었습니다.";
				
				responseEntity = new ResponseEntity<>(msg, HttpStatus.OK);
								
			} else if (result.equals("즐겨찾기(삭제) 실패")) {
				msg = "즐겨찾기 삭제에 실패하였습니다.";

				responseEntity = new ResponseEntity<>(msg, HttpStatus.OK);
				
			}
			
			log.info("전송되는 메시지: {}", msg);
			
		} catch (Exception e) {
			log.error("Survey Error : {}", e);
			e.printStackTrace();
			
			String errMsg = "즐겨찾기(삭제) 에러 났어 슈발!";

			// 실패 코드(417) : 내부 서버 에러
			responseEntity = new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		
		
		
		return responseEntity;
	}// delFav()

	
	
	
	
	
	
	
	
}
