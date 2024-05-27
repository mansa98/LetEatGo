package com.mbc.leteatgo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class IngrSubVO {
	
	@Id
	@Column(name = "INGR_SUB_NUM")
	private int ingrSubNum;
	
	@Column(name = "INGR_SUB_NAME")
	private String ingrSubName;
	
	@Column(name = "INGR_MAIN_NUM")
	private String ingrMainNum;
	
}