package com.mbc.leteatgo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.mbc.leteatgo.domain.Role;
import com.mbc.leteatgo.domain.MemberDTO;
import com.mbc.leteatgo.mapper.MemberMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberServiceImpl {
	
	@Autowired
	private MemberMapper memberMapper;
	
	// 회원정보 주입
	public void insertMemberITF(MemberDTO memberDTO) {
		memberMapper.insertMemberITF(memberDTO);
	}
	
//	public boolean loginCK(String member_id, String member_pw) {
//		
//        MemberDTO memberDTO = memberMapper.loginMember(member_id);
//        log.info("memberDTO : {}",memberDTO);
//        
//        if (memberDTO != null && memberDTO.getMember_pw().equals(member_pw)) {
//            return true; // 인증 성공
//        }
//        return false; // 인증 실패
//        
//        // return memberDTO != null; // 회원이 조회되면 인증 성공, 그렇지 않으면 인증 실패
//    }
	
	// 아이디 찾기
	public MemberDTO findMemberITF(String memberEmail) {
	    return memberMapper.findMemberITF(memberEmail);
	}
	
	// 아이디 찾기
	public MemberDTO loginMemberITF(String memberId) {
	    return memberMapper.loginMemberITF(memberId);
	}
	 
	// 유효성 에러 확인
	 public List<String> getValidationErrors(BindingResult bindingResult) {
	        List<String> errors = new ArrayList<>();

	        // BindingResult에 에러가 있는지 확인
	        if (bindingResult.hasErrors()) {
	            // 모든 필드에 대한 에러를 반복하여 처리
	            for (FieldError error : bindingResult.getFieldErrors()) {
	                // 에러 메시지 생성
	                String errorMessage = error.getDefaultMessage();
	                errors.add(errorMessage);
	            }
	        }

	        return errors;
	    }
	 
	 // 아이디 중복 체크 메서드
	    public boolean memberIdCheck(String memberId) {
	        int count = memberMapper.idCheck(memberId);
	        return count > 0;
	    }
	    
	 // 닉네임 중복 체크 메서드
	    public boolean memberNickCheck(String memberNick) {
	        int count = memberMapper.nickCheck(memberNick);
	        return count > 0;
	    }
	    
	    // 회원 가입,회원 등급 삽입
		@Transactional
		public boolean insertMemberAndRoleITF(MemberDTO memberDTO) {
			
			boolean result = false;
			
			try {
	            // 회원 정보 삽입
	            memberMapper.insertMemberITF(memberDTO);
	            // 롤 삽입
	            Role role = new Role();
	            role.setMemberId(memberDTO.getMemberId());
	            role.setMemberRole("ROLE_USER"); // 새로운 사용자에 대한 기본 롤
	            memberMapper.insertRoleITF(role);
	            
	            result = true;
	        } catch (Exception e) {
	            log.error("롤을 포함하여 회원 등록 중 오류 발생: {}", e);
	            e.printStackTrace();
	        }
	        
	        return result;
	    }
		
}
