package com.mbc.leteatgo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FavDTO {
	
	
//	/** 즐겨찾기 번호 */
//	private int favNum;
//	
//	/** 회원 아이디 */
//	private String memberId;
	
	/** 레시피 번호 */
	private int recipeNum;
	
	/** 레시피 이미지 */
	private String recipeImage;
	
	/** 레시피 오리지날 제작자 */
	private String recipeOgWriter;
	
	/** 레시피 제목 */
	private String recipeTitle;
	
}
