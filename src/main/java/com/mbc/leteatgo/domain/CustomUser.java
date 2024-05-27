package com.mbc.leteatgo.domain;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CustomUser implements UserDetails {
	
	private static final long serialVersionUID = 1l;
	
	private String username;
	private String password;
	private String memberNick; //0418 닉네임 추가!
	
	private List<Role> authorities;
	private boolean accountNonExpired = true;
	private boolean accountNonLocked = true;
	private boolean credentialsNonExpired = true;
	private boolean enabled = true;
	
	public CustomUser(Users users) {
		this.username = users.getId();
		this.password = users.getPw();
		this.enabled = users.getEnabled() == 1 ? true : false;
		this.memberNick = users.getMemberNick();
	}

	// 추가 
	public CustomUser(String username, String password, boolean enabled) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}
	
	public CustomUser(String username, String password, boolean enabled, String memberNick) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.memberNick = memberNick;
	}
}


