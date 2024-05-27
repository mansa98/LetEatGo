package com.mbc.leteatgo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mbc.leteatgo.domain.RecipeVO;
import com.mbc.leteatgo.service.RecipeService;

import lombok.extern.slf4j.Slf4j;

/**
 * 페이지 이동 담당 컨트롤러
 * RequestMapping("/ranking")
 * 
 * @author JAEUG
 */
@Controller
@Slf4j
@RequestMapping("/ranking")
public class RankingController {

	@Autowired
	RecipeService recipeService;
	
	/**
	 * 전체 레시피에서 1페이지 분량만 페이징하고(좋아요순으로 내림차순) 그 중에서 상위 5개 레시피를 select하여 model로 추가하여 ranking.html 페이지로 return
	 * 
	 * @param model
	 * @return String
	 */
	@GetMapping("/")
	public String getTop5(Model model) {
		
		List<RecipeVO> top5List = new ArrayList<>();
		
		top5List = recipeService.selectRecipesByPagingOrderByRecipeLikeDesc(1, 5);
		
		model.addAttribute("rankedRecipe1", top5List.get(0));
		model.addAttribute("rankedRecipe2", top5List.get(1));
		model.addAttribute("rankedRecipe3", top5List.get(2));
		model.addAttribute("rankedRecipe4", top5List.get(3));
		model.addAttribute("rankedRecipe5", top5List.get(4));
		
		
		return "ranking";
	}
	
	
}
