package com.mbc.leteatgo.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class MemberUpdateDTO extends MemberDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/** 회원 패스워드(수정) */
	private String pw1;
	
	/** 회원 패스워드(수정) */
	private String pw2;
	
	// memberDTO에 저장된 값을 불러와 MemberUpdateDTO에 지정
	public MemberUpdateDTO(MemberDTO memberDTO) {
		this.setMemberNum(memberDTO.getMemberNum());
		this.setMemberId(memberDTO.getMemberId());
		this.setMemberPw(memberDTO.getMemberPw());
		this.setMemberName(memberDTO.getMemberName());
		this.setMemberNick(memberDTO.getMemberNick());
		this.setMemberGender(memberDTO.getMemberGender());
		this.setMemberEmail(memberDTO.getMemberEmail());
		this.setMemberMobile(memberDTO.getMemberMobile());
		this.setMemberZip(memberDTO.getMemberZip());
		this.setMemberRoadAddress(memberDTO.getMemberRoadAddress());
		this.setMemberJibunAddress(memberDTO.getMemberJibunAddress());
		this.setMemberDetailAddress(memberDTO.getMemberDetailAddress());
		this.setMemberJoinDate(memberDTO.getMemberJoinDate());
		this.setMemberEnabled(memberDTO.getMemberEnabled());
	}

	@Override
	public String toString() {
		return "MemberUpdateDTO [pw1=" + pw1 + ", pw2=" + pw2 + ", getPw1()=" + getPw1() + ", getPw2()=" + getPw2()
				+ ", getMemberNum()=" + getMemberNum() + ", getMemberId()=" + getMemberId() + ", getMemberPw()="
				+ getMemberPw() + ", getMemberName()=" + getMemberName() + ", getMemberNick()=" + getMemberNick()
				+ ", getMemberGender()=" + getMemberGender() + ", getMemberBirthday()=" + getMemberBirthday()
				+ ", getMemberEmail()=" + getMemberEmail() + ", getMemberMobile()=" + getMemberMobile()
				+ ", getMemberZip()=" + getMemberZip() + ", getMemberRoadAddress()=" + getMemberRoadAddress()
				+ ", getMemberJibunAddress()=" + getMemberJibunAddress() + ", getMemberDetailAddress()="
				+ getMemberDetailAddress() + ", getMemberJoinDate()=" + getMemberJoinDate() + ", getMemberEnabled()="
				+ getMemberEnabled() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + "]";
	}
	
	

	

	



	

	
	
	
}
