package com.mbc.leteatgo.domain;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
public class MemberInfoVO {
	
	
	private String userId;

	private String userNick;
	
	


}
