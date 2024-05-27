package com.mbc.leteatgo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.mbc.leteatgo.domain.RecipeVO;

import jakarta.transaction.Transactional;

public interface RecipeRepository extends JpaRepository<RecipeVO, Integer> {

	RecipeVO save(RecipeVO recipeVO);
	
	RecipeVO findById(int recipeNum);
	List<RecipeVO> findAll();
	
	Page<RecipeVO> findAll(Pageable pageable);
	
	
	int countByRecipeTitleLike(String recipeTitle); // Like
	int countByRecipeTitleContaining(String recipeTitle); // Containing
	int countByRecipeOgWriterContaining(String recipeOgWriter);
	int countByRecipeIngrCombinedContaining(String recipeIngrCombined);
	
	Page<RecipeVO> findByRecipeTitleContaining(String recipeTitle, Pageable pageable);
	Page<RecipeVO> findByRecipeOgWriterContaining(String recipeOgWriter, Pageable pageable);
	Page<RecipeVO> findByRecipeIngrCombinedContaining(String recipeOgWriter, Pageable pageable);
	
	@Query(value="SELECT rt.recipe_title FROM recipe_tbl rt WHERE rt.recipe_title like :ingr", nativeQuery=true)
	//List<RecipeVO> findByIngrName(@Param("ingr") String ingr);
	List<String> findByIngrName(@Param("ingr") String ingr);
	
	@Modifying
	@Query(value="UPDATE recipe_tbl rt SET rt.recipe_like=rt.recipe_like+1 WHERE rt.recipe_num=:recipeNum", nativeQuery=true)
	void increaseRecipeLikeByRecipeNum(@Param("recipeNum") int recipeNum);
	
	@Modifying
	@Query(value="UPDATE recipe_tbl rt SET rt.recipe_like=rt.recipe_like-1 WHERE rt.recipe_num=:recipeNum", nativeQuery=true)
	void decreaseRecipeLikeByRecipeNum(@Param("recipeNum") int recipeNum);
	
	@Modifying
	@Query(value="UPDATE recipe_tbl rt SET rt.recipe_count=rt.recipe_count+1 WHERE rt.recipe_num=:recipeNum", nativeQuery=true)
	void updateRecipeCountByRecipeNum(@Param("recipeNum") int recipeNum);
	
//	@Modifying
//	@Query(value="update recipe_tbl r set r.recipe_like=?1 where r.recipe_num=?2")
//	// @Query("select u from User u where u.emailAddress = ?1")
//	void updateLike(int like, int num);
}	
