package com.mbc.leteatgo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="fav_tbl")
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FavVO {
	
	@SequenceGenerator(name="fav_tbl_seq",
						allocationSize=1)
	
	
	@GeneratedValue(generator="fav_tbl_seq",
					strategy=GenerationType.SEQUENCE)
	@Id
	@Column(name="fav_num")
	private int favNum;
	
	@Column(name="member_id")
	private String memberId;
	
	@Column(name="recipe_num")
	private int recipeNum;

}
