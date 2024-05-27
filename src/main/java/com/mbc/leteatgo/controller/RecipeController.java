package com.mbc.leteatgo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mbc.leteatgo.domain.FavVO;
import com.mbc.leteatgo.domain.RecipeVO;
import com.mbc.leteatgo.service.FavService;
import com.mbc.leteatgo.service.RecipeService;

import lombok.extern.slf4j.Slf4j;
/**
 * 레시피 갤러리와 레시피 상세페이지로의 이동을 담당하는 컨트롤러
 * RequestMapping("/recipe")
 * 
 * @author JAEUG
 */
@Controller
@Slf4j
@RequestMapping("/recipe")
public class RecipeController {

	@Autowired
	RecipeService recipeService;
	
	@Autowired
	FavService favService;
	/**
	 * 데이터베이스(recipe_tbl)에서 모든 레시피 데이터를 select하여 리스트에 저장한 후 model로 추가하여 갤러리 페이지로 return
	 * 
	 * @deprecated 데이터 수가 많아지면서 select에 걸리는 시간이 너무 길어져 페이징으로 대체
	 * @param model
	 * @return String
	 */
	@GetMapping("/gallery")
	public String goGallery(Model model) {
		
		List<RecipeVO> foodList = new ArrayList<>();
		
		foodList = recipeService.getDatasFromDB();
		
		model.addAttribute("foodList", foodList);
		
		return "/recipe/recipegallery";
	}// goGallery()
	
	/**
	 * <p>recipeNum(레시피 번호)를 RequestParam으로하여 recipeNum에 해당하는 레시피의 상세 페이지로 이동</p>
	 * <p>회원이 즐겨찾기한 레시피 데이터가 존재하는지 
	 * 
	 * @param recipeNum
	 * @param recipeVO
	 * @param model
	 * @return String
	 */
	@GetMapping("/detailpage")
	public String goDetailPage(@RequestParam("recipeNum") int recipeNum, 
							   RecipeVO recipeVO,
							   Model model) {
		
		recipeVO = recipeService.selectRecipe(recipeNum);
		
		log.info("㈜㈜㈜㈜㈜㈜ recipeVO: {}", recipeVO.toString());
		
		
		model.addAttribute("recipeVO", recipeVO);
		
		/////////////////////////////////////////////////////////////////////////
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String memberId = auth.getName();
		
		model.addAttribute("memberId", memberId);
		
		FavVO favVO = new FavVO();
		
		List<FavVO> favList = new ArrayList<>();
		
		// 리퀘파람의 recipeNum으로 select해올 데이터가 존재한다면
		if (favService.getFavByRecipeNum(recipeNum) != null) {
			
			favList = favService.getFavByRecipeNum(recipeNum);
			
			// 그 데이터 내 요소의 memberId가 현재 로그인한 유저의 id와 일치한다면
			for (int i = 0; i < favList.size(); i++) {
				
				if (favList.get(i).getMemberId().equals(memberId)) {
					
					// favVO에 할당해줌!
					favVO = favList.get(i);
				}//
			}//
			
		}//
		
		log.info("★ 모델에 추가 직전의 memberId {}", memberId);
		log.info("★ 모델에 추가 직전의 favVO {}", favVO.toString());
		
		model.addAttribute("favVO", favVO);
		
		// 임시 상세페이지를 접속할때마다 해당 레시피의 recipeCount를 +1씩 업데이트하는 쿼리 적용
		
		boolean updateCountResult = false;
		
		// 여기서 recipe_count 를 +1
		updateCountResult = recipeService.updateCount(recipeVO);
		// update 결과를 전송
		model.addAttribute("updateCountResult", updateCountResult);
		// update 적용된 데이터를 가져옴
		RecipeVO updatedRecipeVO = recipeService.selectRecipe(recipeVO.getRecipeNum());
		// update 적용된 데이터를 전송
		model.addAttribute("updatedRecipeVO", updatedRecipeVO);
		
		log.info("▼ 업데이트된 상황은: {}", updatedRecipeVO.toString());
		
		
		return "recipe/recipedetailpage";
	}
	
	
}
