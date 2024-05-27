package com.mbc.leteatgo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.mbc.leteatgo.domain.CustomUser;
import com.mbc.leteatgo.domain.FavDTO;
import com.mbc.leteatgo.domain.FavVO;
import com.mbc.leteatgo.domain.MemberDTO;
import com.mbc.leteatgo.domain.MemberInfoVO;
import com.mbc.leteatgo.domain.RecipeVO;
import com.mbc.leteatgo.service.FavService;
import com.mbc.leteatgo.service.LadService;
import com.mbc.leteatgo.service.MemberService;
import com.mbc.leteatgo.service.MemberServiceImpl;
import com.mbc.leteatgo.service.RecipeService;

//import com.javateam.crawlTest.domain.MemberVO;
//import com.javateam.crawlTest.domain.RecipeVO;
//import com.javateam.crawlTest.repository.RecipeRepository;
//import com.javateam.crawlTest.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
//@RequestMapping("/main")
public class WelcomeController {

	@Autowired
	MemberService memberService;

	@Autowired
	MemberServiceImpl memberServiceImpl;

	@Autowired
	MemberDTO memberDTO;
	
	@Autowired
	FavService favService;
	
	@Autowired
	RecipeService recipeService;
	
	@Autowired
	LadService ladService;

	@GetMapping("/")
	public String goHome(Model model) {
		
		/** 스프링 시큐리티 ContextHolder가 가지고 있는 id(memberDTO로 치면 memberId)정보 가져오기 */
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// auth.getAuthorities().isEmpty();
		
		log.info("권한 여부 : {}", auth);

		String id = auth.getName();

		// 처음 접속 alert 관련 추가
		model.addAttribute("id", id);
		
		log.info("$$$$$$$$$ auth 확인용 : {}", auth.getAuthorities().toString().contains("ROLE_ANONYMOUS"));
		
		
		
		// 유저가 로그인 상태일때만 memberDTO 전송!!
		// if (!id.equals("anonymousUser")) {
		
		if (auth.getAuthorities().toString().contains("ROLE_ANONYMOUS") == false) {

			MemberDTO memberDTO = memberServiceImpl.loginMemberITF(id);
			// 처음 접속 alert 관련 추가
			String memberNick = memberDTO.getMemberNick();

			// 처음 접속 alert 관련 추가
			model.addAttribute("memberNick", memberNick);
			model.addAttribute("memberDTO", memberDTO);

		}
		
		/** 현재 로그인 멤버의 lad(선호/비선호 재료) 데이터가 존재한다면 */
		if (ladService.getLadByMemberId(id) != null) {
			
			model.addAttribute("ladVO", ladService.getLadByMemberId(id));
		}
		
		/* 캐러셀 슬라이드 이미지 데이터 생성 */
		List<RecipeVO> recipeList = recipeService.selectRecipesByPaging(30, 10);
		
		// List<String> recipeImageList = new ArrayList<>();
		
//		for (int i = 0; i < recipeList.size(); i++) {
//			
//			recipeImageList.add(recipeList.get(i).getRecipeImage());
//		}
		
		model.addAttribute("recipeList", recipeList);
		
		return "welcome";

	}// goHome()

	@GetMapping("/mypage")
	public String goMypage(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String memberId = authentication.getName();
		
		if (memberId.equals("anonymousUser")) {
			MemberDTO memberDTO = memberService.selectMember(memberId);
			
			model.addAttribute("memberDTO", memberDTO);
			
			return "login";
		}
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		CustomUser customUser = (CustomUser) principal;
		log.info("principal : {}", principal);
		log.info("id : {}", customUser.getUsername());
		
		String id = customUser.getUsername();
		
		MemberDTO memberDTO = memberService.selectMember(id);
		
		model.addAttribute("memberDTO", memberDTO);
		
//		int dataCountByMemberId = favService.getDataCountByMemberId(id);
//		
//		log.info("멤버가 추가한 즐겨찾기의 갯수는: {}", dataCountByMemberId);
		
		List<FavVO> favList = favService.getFavList(id);
		
		List<RecipeVO> recipeList = new ArrayList<>();
		
		log.info("멤버가 추가한 즐겨찾기의 갯수는: {}개", favList.size());
		
		for (int i = 0; i < favList.size(); i++) {
			
			//recipeList.set((i+1), recipeService.selectRecipe(favList.get(i).getRecipeNum()));
			recipeList.add(recipeService.selectRecipe(favList.get(i).getRecipeNum()));
		}
		
		for (FavVO fav : favList) {
			
			log.info("§ 가져온 favList: {}", fav.toString());
			
		}
		
		for (RecipeVO recipe : recipeList) {
			
			log.info("§ 가져온 recipeList: {}", recipe.toString());
			
		}
		
		List<FavDTO> favDTOList = new ArrayList<>();
		
		for (int i = 0; i < favList.size(); i++) {
			
			favDTOList.add(favService.getFavDTO(favList.get(i).getRecipeNum()));
			
		}
		
		for (FavDTO favDTO : favDTOList) {
			
			log.info("모델에 추가할 최종 favDTOList: {}", favDTO.toString());
		}
		
		
		model.addAttribute("favList", favDTOList);
		

		//List<FavDTO> favDTOs = favService.getFavDTO(recipeNum, memberId);
//		
		//model.addAttribute("favDTOs", favDTOs);
		
		return "mypage";
		
	}// goMypage()

	@GetMapping("/testpage")
	public String goTestpage(Model model) {
		/**
		 * 로그아웃 상태(CustomUser가 null상태면 오류 페이지가 뜹니다.) 아마 Authentication이 null이 아닐때만 실행하는
		 * 코드가 필요할 것으로 보입니다 ㅎㅎ
		 */

//		boolean authCheck = SecurityContextHolder.getContext().getAuthentication() != null;
//		
//		log.info("한번 보자: " + authCheck);

//		if (SecurityContextHolder.getContext()
//				.getAuthentication().getPrincipal() != null) {
//			// Authentication에서 principal(username, role 등 정보를 담고있는 객체) 가져오기
//			Object principal = SecurityContextHolder.getContext()
//					.getAuthentication().getPrincipal();
//			// domain의 CustomUser 객체로 캐스팅(형 변환?)시켜줌
//			CustomUser cu = (CustomUser)principal;
//			
//			// CustomUser의 멤버필드 username(memberId) 가져오기
//			String username = cu.getUsername();
//			
//			log.info("★ 세션에서 가져온 아이디는: {}", username);
//			
//			model.addAttribute("username", username);
//			
//			memberDTO = memberService.selectMember(username);
//			
//			model.addAttribute("user", memberDTO);
//		
//		} else {
//			
//			
//		}

		/** 변경: principal => Authentication */
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		/**
		 * Authentication에서 바로 name을 가져온다(principal의 username과 같음) 이로써 CustomUser로 강제 형
		 * 변환이 더 이상 필요하지 않음
		 */

		String username = auth.getName();

		log.info("되냐? {}", username);

		model.addAttribute("username", username);

		/**
		 * if문: 비로그인 상태에서(memberDTO null 오류 뜨는것 방지) 참고: 비로그인 상태에서 username을 찍어보면
		 * "anonymousUser"라고 정확히 나온다. username이 "anonymousUser"가 아닐때만 (로그인 상태일때만)"
		 */
		if (!username.equals("anonymousUser")) {
			memberDTO = memberService.selectMember(username);

			log.info("되냐2? {}", memberDTO.toString());

			model.addAttribute("user", memberDTO);
		}

		// model.addAttribute("authCheck", authCheck);

		// Authentication에서 가져온 username(memberId)이용해서 DB에서 데이터 셀렉트해오기

		return "testpage";
	}

	@GetMapping("/loginError")
	public String loginError(Model model, HttpSession session) {

		// Spring CustomProvider 인증(Auth) 에러 메시지 처리
		Object secuSess = session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");

		log.info("#### 인증 오류 메시지-1 : " + secuSess);
		log.info("#### 인증 오류 메시지-2 : " + secuSess.toString());

		model.addAttribute("error", "true");
		model.addAttribute("msg", secuSess);

		return "login";
	}

	@GetMapping("/403")
	public String acessDenided(Model model, HttpSession session) {

		log.error("403 mapping");

		model.addAttribute("errMsg", "페이지 접근 권한이 없습니다.");
		model.addAttribute("movePage", "/");

		return "/error/error";
	}

	@GetMapping("/join")
	public String join(Model model) {
		model.addAttribute("memberDTO", new MemberDTO());
		return "/member/join";
	}
	
	/** 아이디 찾기 페이지 */
	@GetMapping("/findId")
	public String findId() {
		return "/member/memberId";
	}

}
