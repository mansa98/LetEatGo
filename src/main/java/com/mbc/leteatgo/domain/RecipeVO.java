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
import lombok.ToString;

@Component
@Entity
@Table(name="recipe_tbl")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RecipeVO {
	
	@SequenceGenerator(name="recipe_tbl_seq",
						initialValue=1,
						allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,
					generator="recipe_tbl_seq")
	@Id
	@Column(name="recipe_num")
	private int recipeNum;
	
	@Column(name="recipe_title")
	private String recipeTitle;
	
	@Column(name="recipe_og_writer")
	private String recipeOgWriter;
	
	@Column(name="recipe_image")
	private String recipeImage;
	
	@Column(name="recipe_ingr_combined")
	private String recipeIngrCombined;

	@Column(name="recipe_ingr_main")
	private String recipeIngrMain;
	
	@Column(name="recipe_ingr_seasoning")
	private String recipeIngrSeasoning;
	
	@Column(name="recipe_cookery")
	private String recipeCookery;
	
	@Column(name="recipe_cookery_images")
	private String recipeCookeryImages;
	
	@Column(name="recipe_like")
	private int recipeLike;
	
	@Column(name="recipe_count")
	private int recipeCount;

	public int getRecipeNum() {
		return recipeNum;
	}

	public void setRecipeNum(int recipeNum) {
		this.recipeNum = recipeNum;
	}

	public String getRecipeTitle() {
		return recipeTitle;
	}

	public void setRecipeTitle(String recipeTitle) {
		this.recipeTitle = recipeTitle;
	}

	public String getRecipeOgWriter() {
		return recipeOgWriter;
	}

	public void setRecipeOgWriter(String recipeOgWriter) {
		this.recipeOgWriter = recipeOgWriter;
	}

	public String getRecipeImage() {
		return recipeImage;
	}

	public void setRecipeImage(String recipeImage) {
		this.recipeImage = recipeImage;
	}

	public String getRecipeIngrCombined() {
		return recipeIngrCombined;
	}

	public void setRecipeIngrCombined(String recipeIngrCombined) {
		this.recipeIngrCombined = recipeIngrCombined;
	}

	public String getRecipeIngrMain() {
		return recipeIngrMain;
	}

	public void setRecipeIngrMain(String recipeIngrMain) {
		this.recipeIngrMain = recipeIngrMain;
	}

	public String getRecipeIngrSeasoning() {
		return recipeIngrSeasoning;
	}

	public void setRecipeIngrSeasoning(String recipeIngrSeasoning) {
		this.recipeIngrSeasoning = recipeIngrSeasoning;
	}

	public String getRecipeCookery() {
		return recipeCookery;
	}

	public void setRecipeCookery(String recipeCookery) {
		this.recipeCookery = recipeCookery;
	}

	public String getRecipeCookeryImages() {
		return recipeCookeryImages;
	}

	public void setRecipeCookeryImages(String recipeCookeryImages) {
		this.recipeCookeryImages = recipeCookeryImages;
	}

	public int getRecipeLike() {
		return recipeLike;
	}

	public void setRecipeLike(int recipeLike) {
		this.recipeLike = recipeLike;
	}

	public int getRecipeCount() {
		return recipeCount;
	}

	public void setRecipeCount(int recipeCount) {
		this.recipeCount = recipeCount;
	}
	
	

}
