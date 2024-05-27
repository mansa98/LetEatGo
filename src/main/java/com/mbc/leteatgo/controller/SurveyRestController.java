package com.mbc.leteatgo.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mbc.leteatgo.domain.LadVO;
import com.mbc.leteatgo.service.LadService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/survey")
@Slf4j
public class SurveyRestController {
	
	@Autowired
	LadService ladService;
	
	
	@PostMapping("/chkSurvey")
	public ResponseEntity<String> chkSurvey(@RequestParam List<String> check1, 
											@RequestParam List<String> check2,
											Model model) {
		
	//public ResponseEntity<String> chkSurvey(@RequestParam Map<String, Object> map) {
		
//		log.info("check1: " + check1);
//		log.info("check2: " + check2);
		
		log.info("인자 확인 부분: ");
//		
//		map.forEach((x, y) -> {
//			
//			log.info("인자:" + x + "=" + y);
//		});
		
		check1.forEach(x -> {
			
			log.info("인자1:" + x);
		});
		
		check2.forEach(x -> {
			
			log.info("인자2:" + x);
		});
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String memberId = auth.getName();
		
		log.info("누구냐 넌?: {}", memberId);
		
		String like = check1.toString();
		
		log.info("선호 재료: {}", like);
		
		String dislike = check2.toString();
		
		log.info("비선호 재료: {}", dislike);
		
		
		ResponseEntity<String> responseEntity = null;
		
		log.info("◆ Auth에서 멤버ID를 잘 가져왔는가?: {}", memberId);
		
		LadVO ladVOBefore = new LadVO();
		
		if (ladService.getLadByMemberId(memberId) != null) {
			ladVOBefore = ladService.getLadByMemberId(memberId);
		}
		LadVO ladVOAfter = LadVO.builder()
								.memberId(memberId)
								.ladLike(like)
								.ladDislike(dislike)
								.build();
		
		log.info("◆ DB에서 조회한 데이터: {}", ladVOBefore.toString());
		
		log.info("◇ DB에 들어갈 new 데이터: {}", ladVOAfter.toString());
		
		String result = "";
		
		String msg = "";
		
		
		try {
			
			// TODO: DB 체크
			
			if (ladService.getLadByMemberId(memberId) == null) {
				
				
				result = "DBEmpty";
			} else {
				
				
				result = "DBExist";
			}
			
			log.info("--- result : {}", result);
			
			if (result.equals("DBEmpty")) {
				// 유저가 선호/비선호 리스트를 처음 저장하는 경우 : 성공 코드(200)
				
				// msg = "※ SYSTEM: fav_tbl에 " + memberId + "님의 선호/비선호 재료가 성공적으로 저장되었습니다.";
				msg = "선호/비선호 데이터를 최초로 저장합니다.";
				
				responseEntity = new ResponseEntity<>(msg, HttpStatus.OK);
				/** 처음 선호/비선호를 저장하는 경우라면 confirm을 거칠 필요없이 여기서 바로 데이터를 저장하고 종료 */
				ladService.insertLad(memberId, like, dislike);
				
				
			} else if (result.equals("DBExist")) {
				// 유저가 이미 선호/비선호 리스트를 저장한 경우
				/** 데이터베이스에 이미 유저의 선호/비선호 재료 데이터가 존재하는 경우 msg만 리턴하고 update는 confirmSurvey에서 처리한다!! */
				//msg = "※ SYSTEM: " + memberId + "님의 선호/비선호 재료 리스트를 새롭게 업데이트합니다. 계속하시려면 \"확인\" 취소하시려면 \"취소\"를 누르세요.";
				msg = "선호/비선호 데이터가 이미 존재합니다.";
				responseEntity = new ResponseEntity<>(msg, HttpStatus.OK);
				
				// favService.updateFav(memberVO, favVOAfter);
				
			}
			
			log.info("전송되는 메시지: {}", msg);
			
		} catch (Exception e) {
			log.error("Survey Error : {}", e);
			e.printStackTrace();
			
			String errMsg = "에러 났어 슈발!";

			// 실패 코드(417) : 내부 서버 에러
			responseEntity = new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	
	
	
	@PostMapping("/confirmSurvey")
	public ResponseEntity<String> confirmSurvey(@RequestParam List<String> check1, 
												@RequestParam List<String> check2) {
		
	//public ResponseEntity<String> chkSurvey(@RequestParam Map<String, Object> map) {
		
//		log.info("check1: " + check1);
//		log.info("check2: " + check2);
		
		log.info("confirmSurvey 인자 확인 부분: ");
//		
//		map.forEach((x, y) -> {
//			
//			log.info("인자:" + x + "=" + y);
//		});
		
		check1.forEach(x -> {
			
			log.info("confirmSurvey 인자1:" + x);
		});
		
		check2.forEach(x -> {
			
			log.info("confirmSurvey 인자2:" + x);
		});
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String memberId = auth.getName();
		
		log.info("confirmSurvey 누구냐 넌?: {}", memberId);
		
		String like = check1.toString();
		
		log.info("confirmSurvey 선호 재료: {}", like);
		
		String dislike = check2.toString();
		
		log.info("confirmSurvey 비선호 재료: {}", dislike);
		
		
		ResponseEntity<String> responseEntity = null;
		
		log.info("◆ confirmSurvey Auth에서 멤버Id를 잘 가져왔는가?: {}", memberId);
		
		LadVO ladVOBefore = ladService.getLadByMemberId(memberId);
		
		LadVO ladVOAfter = LadVO.builder()
								.memberId(memberId)
								.ladLike(like)
								.ladDislike(dislike)
								.build();
		
		log.info("◆ confirmSurvey DB에서 조회한 데이터: {}", ladVOBefore.toString());
		
		String result = "";
		
		String msg = "";
		
		try {
			
			// TODO: DB 체크
			
			if (ladService.getLadByMemberId(memberId) == null) {
				
				result = "DBEmpty";
			} else {
				
				result = "DBExist";
			}
			
			log.info("--- confirmSurvey result : {}", result);
			
			if (result.equals("DBEmpty")) {
				// 유저가 선호/비선호 리스트를 처음 저장하는 경우 : 성공 코드(200)
				
				msg = "※ SYSTEM: fav_tbl에 " + memberId + "님의 선호/비선호 재료가 성공적으로 저장되었습니다.";
				
				responseEntity = new ResponseEntity<>(msg, HttpStatus.OK);
				
				ladService.insertLad(memberId, like, dislike);
				
				
			} else if (result.equals("DBExist")) {
				// 유저가 이미 선호/비선호 리스트를 저장한 경우
				
				ladService.updateLad(memberId, ladVOAfter);
				
				msg = "※ SYSTEM: " + memberId + "님의 선호/비선호 재료 리스트가 성공적으로 업데이트되었습니다.";
				
				responseEntity = new ResponseEntity<>(msg, HttpStatus.OK);
				
				
				
			}
			
			log.info("전송되는 메시지: {}", msg);
			
		} catch (Exception e) {
			log.error("Survey Error : {}", e);
			e.printStackTrace();

			// 실패 코드(417) : 내부 서버 에러
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}

}
