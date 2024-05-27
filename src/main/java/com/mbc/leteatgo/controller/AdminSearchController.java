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
public class AdminSearchController {

	@Autowired
	MemberService memberService;
	
	@GetMapping("/searchAllWithRoles.do")
	public String searchAllWithRoles(@RequestParam(value="currPage", defaultValue="1", required=true) int currPage,
									 @RequestParam(value="limit", defaultValue="10") int limit,
									 @RequestParam(value="searchKey") String searchKey,
									 @RequestParam(value="searchWord") String searchWord,
									 Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String id2 = authentication.getName();
		
		if (id2.equals("anonymousUser")) {
			MemberDTO memberDTO = memberService.selectMember(id2);
			
			model.addAttribute("memberDTO", memberDTO);
			
			return "login";
		}
		
		log.info("관리자 화면 검색(search) : role 표시");
		List<Map<String, Object>> members = new ArrayList<>();
		
		// 성별 검색에 따른 검색값 변환
		if (searchKey.equals("member_gender")) {
			
			if (searchWord.equals("남")) {
				searchWord = "m";
			} else if (searchWord.equals("여")) {
				searchWord = "f";
			}
		
		}
		
		
		
		/*else if (searchKey.equals("role")) {
			
			searchWord = searchWord.trim().equals("관리자") ? "ROLE_ADMIN" : "ROLE_USER";
			
		}*/
		
//		if (searchKey.equals("member_gender")) {
//			
//			searchWord = searchWord.trim().equals("남") || searchWord.trim().equalsIgnoreCase("m") ? "m" : "f";
//		
//		} else if (searchKey.equals("member_gender")){
//			
//			searchWord = searchWord.trim().equals("여") || searchWord.trim().equalsIgnoreCase("f") ? "f" : "m";
//		}
		
		
		log.info("검색어 : {}", searchWord);
		
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication()
				.getPrincipal();

		CustomUser customUser = (CustomUser) principal;
		log.info("principal : {}", principal);
		log.info("id : {}", customUser.getUsername());
		
		String id = customUser.getUsername();
		
		MemberDTO memberDTO = memberService.selectMember(id);
		
		model.addAttribute("memberDTO", memberDTO);
				
		members = memberService.selectMembersWithRolesBySearching(currPage, limit, searchKey, searchWord.trim());
		
		log.info("관리자 확인용 인자 : {}, {}, {}, {}", limit, currPage, searchKey, searchWord);
		
		// 총 "검색" 인원 수
		int listCount = memberService.selectMembersCountBySearching(searchKey, searchWord.trim());
		
		log.info("총 검색 인원 수 : {}", listCount);
		
		// 총 페이지 수
		int maxPage = PageVO.getMaxPage(listCount, limit);
		
		// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21, ...)
		int startPage = PageVO.getStartPage(currPage, limit);
		
		// 현재 페이지에 보여줄 마지막 페이지 수(10, 20, 30, ...)
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
		
		model.addAttribute("searchKey", searchKey);
		model.addAttribute("searchWord", searchWord);
		
		log.info("adminAllWithRoles End");
		
		return "admin/adminAllWithRoles";
	}
}
