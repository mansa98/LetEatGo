package com.mbc.leteatgo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.mbc.leteatgo.domain.MemberDTO;
import com.mbc.leteatgo.domain.Role;

@Mapper
public interface MemberMapper {
	
	// 회원 정보 저장
	void insertMemberITF(MemberDTO memberDTO);
	
	// 아이디 찾기
	MemberDTO findMemberITF(String memberEmail);
	
	// 회원 정보 조회
	MemberDTO loginMemberITF(String memberId);
	
	// 아이디 중복 체크
	int idCheck(String memberId);
	
	// 닉네임 중복 체크
	int nickCheck(String memberNick);
	
	// 회원 등급 생성
	void insertRoleITF(Role role);
	
}
