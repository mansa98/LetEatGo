package com.mbc.leteatgo.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mbc.leteatgo.domain.IngrMainVO;
import com.mbc.leteatgo.service.IngrMainService;
import com.mbc.leteatgo.service.LadService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/survey")
@Slf4j
public class SurveyController {
	
	@Autowired
	IngrMainService ingrMainService;
	
	@Autowired
	LadService ladService;
	
	
	@GetMapping("/")
	public String goSurvey(Model model) {
		
		log.info("Get 방식의 survey 실행");
		
		List<IngrMainVO> ingrList = ingrMainService.selectAll();
		
		model.addAttribute("ingrList", ingrList);
		
		for (IngrMainVO VO : ingrList) {
			
			log.info("ingrVO: {}\n", VO);
			
		}
		/** 로그인/비로그인 상태 체크용 */
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String memberId = auth.getName();
		
		if (memberId.equals("AnonymousUser")) {
			
			return "redirect:/member/login";
		}
		
		
		return "survey/survey";
	}
	
	
	@PostMapping("/survey.do")
	public String surveyProc(HttpServletRequest request,
							Model model) throws NoSuchFieldException, SecurityException {
		
		String[] likeArray = request.getParameterValues("check1");
		String[] dislikeArray = request.getParameterValues("check2");
		
		for (String str : likeArray) {
			log.info("★ 평소 즐기는 재료: {}\t", str);
		}
		
		for (String str : dislikeArray) {
			log.info("▲ 평소 기피하는 재료: {}\t", str);
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String id = auth.getName();
		
		log.info("유저정보: {}", id);
		
//		/** 세션을 => VO로 치환!! */
//		MemberVO memberVO = (MemberVO)session.getAttribute("member");
//		
//		log.info("세션정보(memberId): {}", memberVO.getMemberId());
		
		/** fav_tbl에 INSERT할때 사용될 최종 인자들 정리 */
		String memberId = id;
		String likeStr = Arrays.toString(likeArray);
		String dislikeStr = Arrays.toString(dislikeArray);
		
		
		/** fav_tbl내 member_id 중복 방지 */
		if (ladService.getLadByMemberId(memberId) == null) {
			
			ladService.insertLad(memberId, likeStr, dislikeStr);
			
			log.info("※ SYSTEM: fav_tbl에 {}님의 선호/비선호 재료가 성공적으로 저장되었습니다.", memberId);
			
			
		} else {
			
			log.info("※ SYSTEM: 이미 {}님의 선호/비선호 재료가 데이터베이스에 존재합니다.", memberId);
			
		}
		
		
		
		
		return "redirect:/";
	}


}
