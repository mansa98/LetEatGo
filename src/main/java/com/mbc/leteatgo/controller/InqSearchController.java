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

import com.mbc.leteatgo.domain.InqVO;
import com.mbc.leteatgo.domain.PageVO;
import com.mbc.leteatgo.service.InqService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("inq")
@Slf4j
public class InqSearchController {

	@Autowired
	InqService inqService;
	
	@GetMapping("searchList.do")
	public String list(//@RequestParam(value="currPage", defaultValue = "1") int currPage,
					   //@RequestParam(value="limit", defaultValue="10") int limit,
					   //@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable,
					   //String searchKeyword) {
					   @RequestParam(value="currPage", defaultValue="1") int currPage,
					   @RequestParam(value="limit", defaultValue="10") int limit,
					   @RequestParam(value="searchKey") String searchKey,
					   @RequestParam(value="searchWord") String searchWord,
					   Model model
					   ) {
		
//		Page<InqVO> list = null;
//		
//		if(searchKeyword == null) {
//			list = inqService.inqList(pageable);
//		} else {
//			list = inqService.inqSearchList(searchKeyword, pageable);
//		}
//		int startPage = PageVO.getStartPage(currPage, limit);
//		int endPage = startPage + 10;
		
//		model.addAttribute("list", list);
//		model.addAttribute("currPage", currPage);
//		model.addAttribute("startPage", startPage);
//		model.addAttribute("endPage", endPage);
		
//		return "/inq/list";
		
		log.info("게시글 검색 목록");
		log.info("검색 구분 : {}", searchKey);	
		log.info("검색어 : {}", searchWord);
//		
		List<InqVO> inqList = new ArrayList<>();
//
		int listCount = inqService.selectInqsCountBySearching(searchKey, searchWord.trim());
//		
		inqList = inqService.selectInqsBySearching(currPage, limit, searchKey, searchWord.trim());
//
		int maxPage = PageVO.getMaxPage(listCount, limit);
		int startPage = PageVO.getStartPage(currPage, limit);
		int endPage = startPage + 10;
//		
		if (endPage> maxPage) endPage = maxPage;
//   	    
//   	    if (currPage > maxPage) {
//   	    	currPage = maxPage;
//   	    }
//   	    
		PageVO pageVO = new PageVO();
		pageVO.setEndPage(endPage);
		pageVO.setListCount(listCount);
		pageVO.setMaxPage(maxPage);
		pageVO.setCurrPage(currPage);
		pageVO.setStartPage(startPage);
//		
		pageVO.setPrePage(pageVO.getCurrPage()-1 < 1 ? 1 : pageVO.getCurrPage()-1);
		pageVO.setNextPage(pageVO.getCurrPage()+1 > pageVO.getEndPage() ? pageVO.getEndPage() : pageVO.getCurrPage()+1);
//		
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("pageVO", pageVO);
		model.addAttribute("inqList", inqList);	
		
		model.addAttribute("searchKey", searchKey);
		model.addAttribute("searchWord", searchWord);
		
//		
		return "/inq/inqList";		
	} //
	
}
