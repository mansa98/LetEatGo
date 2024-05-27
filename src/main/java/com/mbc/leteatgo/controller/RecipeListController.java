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
/**
 * 
 * @author JAEUG
 * @apiNote 갤러리 페이징을 담당하는 컨트롤러
 *
 */
@Controller
@RequestMapping("recipe")
@Slf4j
public class RecipeListController {
	
	@Autowired
	RecipeService recipeService;
	
	/**
	 * 레시피 데이터를 페이징 처리하여 갤러리 페이지에 보여주는 기능을 담당하는 GetMapping
	 * 
	 * @param currPage
	 * @param limit
	 * @param model
	 * @return String
	 * 
	 */
	@GetMapping("/gallerylist.do")
	public String list(@RequestParam(value="currPage", defaultValue="1") int currPage,
					   @RequestParam(value="limit", defaultValue="20") int limit,
					   Model model) {
		
		log.info("게시글 목록");
		List<RecipeVO> recipeList = new ArrayList<>();
		
		// 총 게시글 수
		int listCount = (int)recipeService.selectRecipesCount();
		
		recipeList = recipeService.selectRecipesByPaging(currPage, limit);
		
		// 총 게시글 수 (댓글들을 제외한)
		// int listCount = (int)recipeService.selectRecipesCount();
		// 댓글들 제외
		// recipeList = recipeService.selectBoardsByPagingWithoutReplies(currPage, limit);
		
		// 총 페이지 수
   		// int maxPage=(int)((double)listCount/limit+0.95); //0.95를 더해서 올림 처리
		int maxPage = PageVO.getMaxPage(listCount, limit);
		// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21,...)
   		// int startPage = (((int) ((double)currPage / 10 + 0.9)) - 1) * 10 + 1;
		int startPage = PageVO.getStartPage(currPage);
		// 현재 페이지에 보여줄 마지막 페이지 수(10, 20, 30, ...)
   	    // int endPage = startPage + 10;
		int endPage = PageVO.getEndPage(currPage);
		
   	    if (endPage > maxPage) endPage = maxPage;
   	    
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
		
		return "/recipe/recipegallery";		
	} //
	
}