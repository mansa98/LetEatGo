package com.mbc.leteatgo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mbc.leteatgo.domain.PageVO;
import com.mbc.leteatgo.domain.RecipeVO;
import com.mbc.leteatgo.service.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/recipe")
@Slf4j
public class RecipeSearchController {
	
	@Autowired
	RecipeService recipeService;

	@GetMapping("searchList.do")
	public String list(@RequestParam(value="currPage", defaultValue="1") int currPage,
					   @RequestParam(value="limit", defaultValue="20") int limit,
					   @RequestParam(value="searchKey") String searchKey,
					   @RequestParam(value="searchWord") String searchWord,
					   Model model) {
		
		log.info("레시피 검색 목록");
		log.info("검색 구분 : {}", searchKey);
		log.info("검색어 : {}", searchWord);
		
		List<RecipeVO> recipeList = new ArrayList<>();
		
		// 검색시는 "댓글"도 검색에 반영 (기존 대비 변경 없음)
		// 총 "검색" 게시글 수
		int listCount = recipeService.selectRecipesCountBySearching(searchKey, searchWord.trim());
		
		recipeList = recipeService.selectRecipesBySearching(currPage, limit, searchKey, searchWord.trim());	
		
		// 총 페이지 수
		int maxPage = PageVO.getMaxPage(listCount, limit);
		// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21,...)
		int startPage = PageVO.getStartPage(currPage);
   	    int endPage = PageVO.getEndPage(currPage);
   	    
   	    if (endPage> maxPage) endPage = maxPage;
   	    if (currPage > maxPage) currPage = maxPage;
   	    
   	    PageVO pageVO = new PageVO();
		pageVO.setEndPage(endPage);
		pageVO.setListCount(listCount);
		pageVO.setMaxPage(maxPage);
		pageVO.setCurrPage(currPage);
		pageVO.setStartPage(startPage);
		
		pageVO.setPrePage(pageVO.getCurrPage()-1 < 1 ? 1 : pageVO.getCurrPage()-1);
		// pageVO.setNextPage(pageVO.getCurrPage()+1 > pageVO.getEndPage() ? pageVO.getEndPage() : pageVO.getCurrPage()+1);
		pageVO.setNextPage(pageVO.getCurrPage()+1);
	
		model.addAttribute("pageVO", pageVO);
		model.addAttribute("recipeList", recipeList);
		
		model.addAttribute("searchKey", searchKey);
		model.addAttribute("searchWord", searchWord);
		
		return "recipe/recipegallery";		
	} //

}