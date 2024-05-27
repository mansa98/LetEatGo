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

import com.mbc.leteatgo.domain.NtcVO;
import com.mbc.leteatgo.domain.NtcVO;
import com.mbc.leteatgo.domain.PageVO;
import com.mbc.leteatgo.service.NtcService;
import com.mbc.leteatgo.service.MemberServiceImpl;
import com.mbc.leteatgo.service.NtcService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("ntc")
@Slf4j
// 게시글 목록 처리
public class NtcListController {
	
	@Autowired
	NtcService ntcService;
	
	@Autowired
	MemberServiceImpl memberServiceImpl;
	
	@GetMapping("list.do")
	public String Ntclist(@RequestParam(value = "currPage", defaultValue = "1") int currPage,
			@RequestParam(value = "limit", defaultValue = "10") int limit, Model model,
			@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable,
			String searchKeyword) {
		
		//NtcVO
		Page<NtcVO> list = null;
		if (searchKeyword == null) {
			list = ntcService.ntcList(pageable);
		} else {
			list = ntcService.ntcSearchList(searchKeyword, pageable);
		}

		log.info("게시글 목록");
		List<NtcVO> ntcList = new ArrayList<>();
		
		int listCount = ntcService.selectNtcsCount();
		
		ntcList = ntcService.selectNtcsByPaging(currPage, limit);

		for (NtcVO testVO : ntcList) {
			log.info("ntcLists : {}", ntcList);
		}
		
		int maxPage = PageVO.getMaxPage(listCount, limit);
		// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21,...)
//   	int startPage = (((int) ((double)currPage / 10 + 0.9)) - 1) * 10 + 1;
		int startPage = PageVO.getStartPage(currPage, limit);
		// 현재 페이지에 보여줄 마지막 페이지 수(10, 20, 30, ...)
		int endPage = PageVO.getEndPage(currPage, limit);
		
		if (endPage > maxPage)
			endPage = maxPage;

		if (currPage > maxPage) {
			currPage = maxPage;
		}

		PageVO pageVO = new PageVO();
		pageVO.setEndPage(endPage);
		pageVO.setListCount(listCount);
		pageVO.setMaxPage(maxPage);
		pageVO.setCurrPage(currPage);
		pageVO.setStartPage(startPage);

		pageVO.setPrePage(pageVO.getCurrPage() - 1 < 1 ? 1 : pageVO.getCurrPage() - 1);
		pageVO.setNextPage(currPage + 1);

//		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("pageVO", pageVO);
		model.addAttribute("ntcList", ntcList);
		return "/ntc/ntcList";
	} //
	
}