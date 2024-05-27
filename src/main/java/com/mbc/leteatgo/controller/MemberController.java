package com.mbc.leteatgo.controller;

import java.security.Security;
import java.util.List;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mbc.leteatgo.domain.CustomUser;
import com.mbc.leteatgo.domain.MemberDTO;
import com.mbc.leteatgo.domain.MemberUpdateDTO;
import com.mbc.leteatgo.service.MemberService;
//import com.javateam.memberProject.domain.CustomUser;
//import com.javateam.memberProject.domain.MemberDTO;
import com.mbc.leteatgo.service.MemberServiceImpl;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberService memberService;

	@Autowired
	MemberServiceImpl memberServiceImpl;

	@Autowired
	MemberDTO memberDTO;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

//	// 메인페이지
//	@GetMapping("/")
//	public String welcome(Model model) {
//		
//		model.addAttribute("welcome", "환영합니다!");
//		
//		model.addAttribute("member", memberDTO);
//		
//		return "welcome";
//		//return "redirect:/erwa.html";
//	}

	// 로그인 페이지
	@GetMapping("/login")
	public String move(Model model) {

		// model.addAttribute("welcome", "환영합니다!");
		// model.addAttribute("MemberDTO2", memberDTO2);

//		log.info("안에 들어간 값 : " + memberDTO2.toString());
//		
//		if (memberService2.authenticate(id, pw)) {
//	    	   log.info("로그인 성공");
//	           // 로그인 성공 시 메인 페이지로 리다이렉트
//	           return "redirect:/";
//	       } else {
//	    	   log.info("로그인 실패");
//	           // 로그인 실패 시 로그인 페이지로 리다이렉트하면서 실패 메시지 전달
//	    	   redurectAttributes.addFlashAttribute("error", "로그인 실패");
//	           return "redirect:/login7";
//	       }

		return "login";
		// return "redirect:/erwa.html";
	}

	// 회원가입 페이지
	@GetMapping("join")
	public String join(Model model) {
		model.addAttribute("memberDTO", new MemberDTO());
		return "/member/join";
	}

	/** 회원 가입 저장 */
	@PostMapping("joinproc")
	public String insertMember(@Valid @ModelAttribute MemberDTO memberDTO, BindingResult bindingResult, Model model) {

		// 유효성 검사 실패 메시지를 가져옴
		List<String> errors = memberServiceImpl.getValidationErrors(bindingResult);
		// Map<String, String> errors =
		// memberService.getValidationErrors(bindingResult);

		if (!errors.isEmpty()) {

			// 유효성 검사 실패 메시지를 모델에 추가
			model.addAttribute("errors", errors);
			return "/member/join"; // 다시 등록 폼으로 이동
		}

		// 유효성 검사 통과 시, 회원 가입 처리
		memberDTO.setMemberPw(bCryptPasswordEncoder.encode(memberDTO.getMemberPw()));
		// memberService.insertMember(memberDTO);
		log.info("※※※※※※※※※ 오류 발생 예측 지점 ※※※※※※※※※※※※※※※※※※※※");
		memberServiceImpl.insertMemberAndRoleITF(memberDTO);
		
		String message = "회원가입에 성공했습니다.";
		String url = "login";
		model.addAttribute("message", message);
		model.addAttribute("url", url);

		return "joinMessage"; // 성공 페이지로 이동
	}// insertMember()

	/** 아이디 중복 점검 */
	@PostMapping("idCount")
	@ResponseBody
	public boolean countMemberId(@RequestParam("memberId") String memberId) {
		log.info("아이디 : " + memberId);
		return memberServiceImpl.memberIdCheck(memberId);
	}

	/** 닉네임 중복 점검 */
	@PostMapping("nickCount")
	@ResponseBody
	public boolean countMemberNick(@RequestParam("memberNick") String memberNick) {
		log.info("닉네임 : " + memberNick);
		return memberServiceImpl.memberNickCheck(memberNick);
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
	}// loginError()

	@GetMapping("/update.do")
	public String update(Model model) {
		
		log.info("회원정보 수정");
		
		org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String id2 = authentication.getName();
		
		if (id2.equals("anonymousUser")) {
			MemberDTO memberDTO = memberService.selectMember(id2);
			
			model.addAttribute("memberDTO", memberDTO);
			
			return "login";
		}

		// Spring Security Principal(Session) 조회
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		CustomUser customUser = (CustomUser) principal;
		log.info("principal : {}", principal);
		log.info("id : {}", customUser.getUsername()); // 로그인 아이디

		String id = customUser.getUsername();

		MemberDTO memberDTO = memberService.selectMember(id);

		if (memberDTO == null) {
			// 에러 처리
			model.addAttribute("errMsg", "회원 정보가 존재하지 않습니다.");
			return "/error/error";
		} else {

//			// 주의) ClassCastException 발생 가능성 있음
//			// MemberUpdateDTO memberUpdateDTO = (MemberUpdateDTO)memberDTO;
			MemberUpdateDTO memberUpdateDTO = new MemberUpdateDTO(memberDTO);

			model.addAttribute("memberUpdateDTO", memberUpdateDTO);

			log.info("기존 회원 정보 : {}", memberUpdateDTO);
//			
//			
		}

		return "update";

	}// update()

	@PostMapping("/updateProc.do")
	public String updateProc(@ModelAttribute("memberUpdateDTO") MemberUpdateDTO memberUpdateDTO,
			RedirectAttributes ra) {

		log.info("회원정보 수정 처리 : {}", memberUpdateDTO);

		// 신규 패스워드가 공백이 아니라면 패스워드 변경
		// 공백이면 패스워드 변경 의사가 없는 것으로 간주하여 기존 패스워드 그대로 사용
		if (memberUpdateDTO.getPw1().trim().equals("") != true) {
			// 패스워드 암호화
			// 주의) 변경된 패스워드(password1 혹은 password2) => 암호화 => 기존 패스워드에 대입
			bCryptPasswordEncoder = new BCryptPasswordEncoder();
			memberUpdateDTO.setMemberPw(bCryptPasswordEncoder.encode(memberUpdateDTO.getPw1()));
		}

		log.info("updateProc.do(암호화 이후) : {}", memberUpdateDTO);

		String msg = "";
		String movePath = "";

		boolean result = memberService.updateMember(memberUpdateDTO);

		if (result == true) {

			msg = "회원정보 수정에 성공하였습니다.";
			movePath = "redirect:/mypage";
		} else {

			msg = "회원정보 수정에 실패하였습니다.";
			movePath = "redirect:/update.do";
		}

		log.info("result : {}", msg);
		ra.addFlashAttribute("msg", msg);

		return movePath;
	}// updateProc()
	
	/** 아이디 찾기 */
	@PostMapping("/find.do")
	public String findMemberId(@RequestParam("memberEmail") String memberEmail, @RequestParam("memberName") String memberName,
									Model model) {
		 log.info("name :" + memberEmail + ", Name : "+ memberName);
		 
		 MemberDTO memberDTO = memberServiceImpl.findMemberITF(memberEmail);
		 if (memberDTO != null && memberDTO.getMemberName().equals(memberName) ) {
			 
			 String findId = memberDTO.getMemberId();
			 log.info("§§§ id : "+findId);
			 model.addAttribute("message", ("회원님의 아이디는 "+findId+" 입니다."));
			 // model.addAttribute("url", "/member/login");
			 return "message";
		 } else {
			 log.info("§§§ 찾기 실패");
			 model.addAttribute("messageFail", "이메일 또는 이름을 찾지 못했습니다.");
			 model.addAttribute("url", "/findId");
			 return "message";
		 }
	}// findMemberId()

}
