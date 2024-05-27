package com.mbc.leteatgo.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import lombok.Builder;


@Builder
@Component
public class Role implements GrantedAuthority {

	private static final long serialVersionUID = 7464675970058486L;
	
//	private String username;
//	private String role;
	
	private String memberId;
	private String memberRole;
	
	// 추가 
	public Role() {}
	
	public Role(String memberId, String memberRole) {
		this.memberId = memberId;
		this.memberRole = memberRole;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberRole() {
		return memberRole;
	}

	public void setMemberRole(String memberRole) {
		this.memberRole = memberRole;
	}

	@Override
	public String getAuthority() {
		return this.memberRole;
	}
	
	@Override
	public String toString() {
		return "Role [memberId=" + memberId + ", memberRole=" + memberRole + "]";
	}
	
}
