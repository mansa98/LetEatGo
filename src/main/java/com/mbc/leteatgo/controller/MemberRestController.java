package com.mbc.leteatgo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.mbc.leteatgo.service.MemberService;
import com.mbc.leteatgo.domain.MemberDTO;
import com.mbc.leteatgo.domain.MemberUpdateDTO;


import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/member")
@Slf4j
public class MemberRestController {
	
	@Autowired
	MemberService memberService;
	 
	// REST Test (swagger) ready
	@GetMapping("/hasFldForUpdate/{id}/{fld}/{val}")
	public ResponseEntity<Boolean> hasFldForUpdate(@Parameter(name = "id", required = true) @PathVariable("id") String id, 
			 @Parameter(name = "fld", required = true) @PathVariable("fld") String fld,
			 @Parameter(name = "val", required = true) @PathVariable("val") String val) {
		
		log.info("중복 점검 REST(회원 정보 수정) : {}, {}, {}", id, fld, val);
		
		ResponseEntity<Boolean> responseEntity = null; 
		
		try {
			boolean result = memberService.hasFldForUpdate(id, fld, val);
			
			log.info("--- result : {}", result);
			
			if (result == true) {
				// 중복된 아이디가 있음 : 성공 코드(200)
				responseEntity = new ResponseEntity<>(result, HttpStatus.OK); 
			} else {
				// 중복된 아이디가 없음 : 실패 코드(204)
				responseEntity = new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			log.error("hasFldForUpdate error : {}", e);
			e.printStackTrace();

			// 실패 코드(417) : 내부 서버 에러
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;		
	} //
	
	// 관리자 회원 관리용 REST Test (swagger) ready
	@GetMapping("/hasFldForUpdateByAdmin/{id}/{fld}/{val}")
	public ResponseEntity<Boolean> hasFldForUpdateByAdmin(@Parameter(name = "id", required = true) @PathVariable("id") String id, 
			 @Parameter(name = "fld", required = true) @PathVariable("fld") String fld,
			 @Parameter(name = "val", required = true) @PathVariable("val") String val) {
		
		log.info("중복 점검 REST(회원 정보 수정) : {}, {}, {}", id, fld, val);
		
		ResponseEntity<Boolean> responseEntity = null; 
		
		try {
			boolean result = memberService.hasFldForUpdate(id, fld, val);
			
			log.info("--- result : {}", result);
			
			if (result == true) {
				// 중복된 아이디가 있음 : 성공 코드(200)
				responseEntity = new ResponseEntity<>(result, HttpStatus.OK); 
			} else {
				// 중복된 아이디가 없음 : 실패 코드(200)
				responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			log.error("hasFldForUpdateByAdmin error : {}", e);
			e.printStackTrace();

			// 실패 코드(417) : 내부 서버 에러
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;		
	} //

	// REST Test (swagger) ready
	@GetMapping("/updateRoles/{id}/roleUser/{roleUserYn}/roleAdmin/{roleAdminYn}")
	public ResponseEntity<Boolean> updateRoles(@Parameter(name = "id", required = true) @PathVariable("id") String id, 
			 @Parameter(name = "roleUserYn", required = true) @PathVariable("roleUserYn") boolean roleUserYn,
			 @Parameter(name = "roleAdminYn", required = true) @PathVariable("roleAdminYn") boolean roleAdminYn) {
		
		log.info("회원 등급(role) 수정 REST(회원 정보 role 수정) : {}, {}, {}", id, roleUserYn, roleAdminYn);
		
		ResponseEntity<Boolean> responseEntity = null; 
		
		try {
			boolean result = memberService.updateRoles(id, roleUserYn, roleAdminYn);
			
			log.info("--- result : {}", result);
			
			if (result == true) {
				// 중복된 아이디가 있음 : 성공 코드(200)
				responseEntity = new ResponseEntity<>(result, HttpStatus.OK); 
			} else {
				// 중복된 아이디가 없음 : 실패 코드(204)
				responseEntity = new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			log.error("updateRoles error : {}", e);
			e.printStackTrace();

			// 실패 코드(417) : 내부 서버 에러
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;		
	} //
	
	// REST Test (swagger) ready
	@GetMapping("/changeMemberState/{id}/{enabled}")
	public ResponseEntity<Boolean> changeMemberState(@Parameter(name = "id", required = true) @PathVariable("id") String id,
					@Parameter(name = "enabled", required = true) @PathVariable("enabled") int enabled) {
		
		log.info("회원 활동/휴면 계정 처리 : {}", id);
		
		ResponseEntity<Boolean> responseEntity = null; 
		
		try {
			boolean result = memberService.changeEnabled(id, enabled);
			
			log.info("--- result : {}", result);
			
			if (result == true) {
				// 성공 코드(200)
				responseEntity = new ResponseEntity<>(result, HttpStatus.OK); 
			} else {
				// 실패 코드(204)
				responseEntity = new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			log.error("changeEnabled error : {}", e);
			e.printStackTrace();

			// 실패 코드(417) : 내부 서버 에러
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;		
	} //
	
	// REST Test (swagger) ready
	@PostMapping("/updateMemberByAdmin")
	public ResponseEntity<Boolean> updateMember(@Parameter(required = true) @RequestParam Map<String, Object> requestMap) {
		
		log.info("회원 정보 수정 처리(관리자 REST) : ");
		
		requestMap.entrySet().forEach(x->{ log.info("인자 : {}", x); });
		// MemberDTO memberDTO = new MemberDTO(requestMap);
		MemberDTO memberDTO = new MemberDTO();
		
		memberDTO.setMemberId(requestMap.get("id").toString());
		memberDTO.setMemberMobile(requestMap.get("mobile").toString());
		
		log.info("MemberDTO : " + memberDTO);
		
		ResponseEntity<Boolean> responseEntity = null; 
		
		try {
			// 동적 SQL에서 입력된 컬럼 값만 선택적으로 처리 : MyBatis 동적 SQL <set> 태그 
			boolean result = memberService.updateMember(memberDTO); 
			
			log.info("--- result : {}", result);
			
			if (result == true) {
				// 성공 코드(200)
				responseEntity = new ResponseEntity<>(result, HttpStatus.OK); 
			} else {
				// 실패 코드(204) -> 성공 코드(200)
				responseEntity = new ResponseEntity<>(result, HttpStatus.OK); // NO_CONTENT -> OK로 바꿈
			}
			
		} catch (Exception e) {
			log.error("updateMember error : {}", e);
			e.printStackTrace();

			// 실패 코드(417) : 내부 서버 에러
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;		
	} //
	
	// 회원 정보 삭제
	@GetMapping("/deleteMemberByAdmin/{id}")
	public ResponseEntity<Boolean> deleteMember(@Parameter(name="id", required = true) 
										@PathVariable(value="id", required = true) String id) {
		
		log.info("회원 정보 삭제 처리(관리자 REST) : {}", id);
		ResponseEntity<Boolean> responseEntity = null; 
		
		try { 
			boolean result = memberService.deleteMember(id); 
			
			log.info("--- result : {}", result);
			
			if (result == true) {
				// 성공 코드(200)
				responseEntity = new ResponseEntity<>(result, HttpStatus.OK); 
			} else {
				// 실패 코드(204)
				responseEntity = new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			log.error("deleteMember error : {}", e);
			e.printStackTrace();

			// 실패 코드(417) : 내부 서버 에러
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;		
	} //
	
	
	
	
	@GetMapping("/deleteMemberByMyId/{id}")
	public ResponseEntity<Boolean> deleteMemberByMyId(@Parameter(name="id", required = true) 
										@PathVariable(value="id", required = true) String id) {
		
		log.info("회원 정보 삭제 처리(회원 탈퇴) : {}", id);
		ResponseEntity<Boolean> responseEntity = null; 
		
		try { 
			boolean result = memberService.deleteMember(id); 
			
			log.info("--- result : {}", result);
			
			if (result == true) {
				// 성공 코드(200)
				responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
				
			} else {
				// 실패 코드(204)
				responseEntity = new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			log.error("deleteMember error : {}", e);
			e.printStackTrace();
			
			// 실패 코드(417) : 내부 서버 에러
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;		
	} //
	
}