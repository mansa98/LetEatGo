package com.mbc.leteatgo.domain;


import org.springframework.stereotype.Component;

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

@Component
@Entity
@Table(name="RECIPE_TBL")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeVO {
	
	@SequenceGenerator(name="recipe_tbl_seq",
			initialValue=1,
			allocationSize=1)
	
	@GeneratedValue(strategy=GenerationType.SEQUENCE, 
				  	generator="recipe_tbl_seq")
	@Id
	@Column(name="recipe_num")
	Integer recipeNum;
	
	@Column(name="recipe_title")
	String recipeTitle;
	
	@Column(name="recipe_og_writer")
	String recipeOgWriter;
	
	@Column(name="recipe_image")
	String recipeImage;
	
	@Column(name="recipe_ingr_combined")
//	List<String> ingrCombined;
	String recipeIngrCombined;
	
	@Column(name="recipe_ingr_main")
	String recipeIngrMain;
	
	@Column(name="recipe_ingr_seasoning")
	String recipeIngrSeasoning;
	
	@Column(name="recipe_cookery")
//	List<String> cookery;
	String recipeCookery;
	
	@Column(name="recipe_like")
	int recipeLike;
	
	@Column(name="recipe_count")
	int recipeCount;

	@Override
	public String toString() {
		return "RecipeVO [recipeNum=" + recipeNum + ", recipeTitle=" + recipeTitle + ", recipeOgWriter="
				+ recipeOgWriter + ", recipeImage=" + recipeImage + ", recipeIngrCombined=" + recipeIngrCombined
				+ ", recipeIngrMain=" + recipeIngrMain + ", recipeIngrSeasoning=" + recipeIngrSeasoning
				+ ", recipeCookery=" + recipeCookery + ", recipeLike=" + recipeLike + ", recipeCount=" + recipeCount
				+ "]";
	}

	
	
	

	

}
