package com.mbc.leteatgo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mbc.leteatgo.domain.BoardVO;
import com.mbc.leteatgo.domain.PageVO;
import com.mbc.leteatgo.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("board")
// 게시글 분류 (자유, 질문)
public class BoardCategoryController {

	@Autowired
	BoardService boardService;
	
	@GetMapping("categoryList.do")
	public String list(@RequestParam(value="currPage", defaultValue="1") int currPage,
					   @RequestParam(value="limit", defaultValue="10") int limit,
					   @RequestParam(value="category") String category,
					   @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable,
					   Model model) {
		
		List<BoardVO> boardList = new ArrayList<>();
		
		int listCount = boardService.selectCategoryCount(category);
		
		boardList = boardService.findByBoardCategory(currPage, limit, category);
		
		int maxPage = PageVO.getMaxPage(listCount, limit);
		int startPage = PageVO.getStartPage(currPage, limit);
		int endPage = PageVO.getEndPage(currPage, limit);
	
		if (endPage> maxPage) endPage = maxPage;
   	     	    
		PageVO pageVO = new PageVO();
		pageVO.setEndPage(endPage);
		pageVO.setListCount(listCount);
		pageVO.setMaxPage(maxPage);
		pageVO.setCurrPage(currPage);
		pageVO.setStartPage(startPage);
		
		pageVO.setPrePage(pageVO.getCurrPage()-1 < 1 ? 1 : pageVO.getCurrPage()-1);
		//pageVO.setNextPage(pageVO.getCurrPage()+1 > pageVO.getEndPage() ? pageVO.getEndPage() : pageVO.getCurrPage()+1);
		pageVO.setNextPage(pageVO.getCurrPage()+1);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("pageVO", pageVO);
		model.addAttribute("boardList", boardList);	
		
		model.addAttribute("category", category);
		
		return "/board/list";		
	}
	
	}