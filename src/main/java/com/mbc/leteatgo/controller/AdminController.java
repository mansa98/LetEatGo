package com.mbc.leteatgo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mbc.leteatgo.domain.CustomUser;
import com.mbc.leteatgo.domain.MemberDTO;
import com.mbc.leteatgo.domain.PageVO;
import com.mbc.leteatgo.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

	@Autowired
	MemberService memberService;
	
	@GetMapping("/")
	public String admin() {
		
		log.info("관리자 화면 admin");
		// return "redirect:/admin/viewAllWithRoles";
		return "/welcome";
	}
	
//	@GetMapping("/adminAll.do")
//	public String adminView(@RequestParam(value="currPage", defaultValue="1", required=true) int currPage,
//							@RequestParam(value="limit", defaultValue="10") int limit,
//							Model model)  {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		
//		String id2 = authentication.getName();
//		
//		if (id2.equals("anonymousUser")) {
//			MemberDTO memberDTO = memberService.selectMember(id2);
//			
//			model.addAttribute("memberDTO", memberDTO);
//			
//			return "login";
//		}
//		
//		
//		log.info("관리자 화면");
//		List<MemberDTO> members = new ArrayList<>();
//		
//		members = memberService.selectMembersByPaging(currPage, limit);
//		
//		
//		// 총 인원 수
//		int listCount = memberService.selectMembersCount();
//		
//		log.info("총 인원 수 : {}", listCount);
//		
//		// 총 페이지 수
//		// int maxPage=(int)((double)listCount/limit+0.95); // 0.95를 더해서 올림 처리
//		int maxPage = PageVO.getMaxPage(listCount, limit);
//		
//		// 현재 페이지에 보여줄 시작 페이지 수(1, 11, 21, ...)
//		// int srartPage = (((int) ((double)currPage / 10 + 0.9)) - 1) * 10 + 1;
//		int startPage = PageVO.getStartPage(currPage, limit);
//		
//		// 현재 페이지에 보여줄 마지막 페이지 수(10, 20, 30, ...)
//		int endPage = startPage + 10;
//		
//		if (endPage > maxPage) endPage = maxPage;
//		
//		PageVO pageVO = new PageVO();
//		pageVO.setEndPage(endPage);
//		pageVO.setListCount(listCount);
//		pageVO.setMaxPage(maxPage);
//		pageVO.setCurrPage(currPage);
//		pageVO.setStartPage(startPage);
//		
//		pageVO.setPrePage(pageVO.getCurrPage()-1 < 1 ? 1 : pageVO.getCurrPage()-1);
//		pageVO.setNextPage(pageVO.getCurrPage()+1 > pageVO.getEndPage() ? pageVO.getEndPage() : pageVO.getCurrPage()+1);
//		
//		model.addAttribute("pageVO",pageVO);
//		model.addAttribute("members", members);
//		
//		return "admin/adminAll";
//	}
	
	

	@GetMapping("/adminAllWithRoles.do")
	public String adminViewWithRoles(@RequestParam(value="currPage", defaultValue="1", required=true) int currPage,
									 @RequestParam(value="limit", defaultValue="10") int limit,
									 Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String id2 = authentication.getName();
		
		if (id2.equals("anonymousUser")) {
			MemberDTO memberDTO = memberService.selectMember(id2);
			
			model.addAttribute("memberDTO", memberDTO);
			
			return "login";
		} 
		
		log.info("관리자 화면 : role 표시");
		List<Map<String, Object>> members = new ArrayList<>();
		
		members = memberService.selectMembersWithRolesByPaging(currPage, limit);
		
		log.info("★★★★★★★★★★★★★★★★★★★★★이거검색: {}",members.get(0).toString() );
		
		Object principal = SecurityContextHolder.getContext()
											.getAuthentication()
											.getPrincipal();

		CustomUser customUser = (CustomUser) principal;
		log.info("principal : {}", principal);
		log.info("id : {}", customUser.getUsername());
		
		String id = customUser.getUsername();
		
		MemberDTO memberDTO = memberService.selectMember(id);
		
		model.addAttribute("memberDTO", memberDTO);
		
		// 총 인원 수
		int listCount = memberService.selectMembersCount();
		
		log.info("총 인원수 : {}", listCount);
		
		// 총 페이지 수
		int maxPage = PageVO.getMaxPage(listCount, limit);
		
		// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21, ...)
		int startPage = PageVO.getStartPage(currPage, limit);
		
		// 현재 페이지에 보여줄 마지막 페이지 수 (10, 20, 30, ...)
		//int endPage = startPage + 10;
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
		
		model.addAttribute("pageVO", pageVO);
		model.addAttribute("members", members);
		return "admin/adminAllWithRoles";
	}
	
}
