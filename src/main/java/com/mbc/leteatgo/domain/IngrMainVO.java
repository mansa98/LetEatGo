package com.mbc.leteatgo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="ingr_main_tbl")
@Getter
@Setter
@ToString
public class IngrMainVO {

	
	@Id
	@Column(name="ingr_main_num")
	private int ingrMainNum;
	
	@Column(name="ingr_main_name")
	private String ingrMainName;
	
}
