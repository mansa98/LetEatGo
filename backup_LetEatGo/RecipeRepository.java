package com.mbc.leteatgo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.mbc.leteatgo.domain.RecipeVO;


public interface RecipeRepository extends CrudRepository<RecipeVO, Integer> {

	
	// public RecipeVO save(RecipeVO recipeVO);
	// public Page<RecipeVO> findAll(RecipeVO recipeVO, Pageable pageable);
}
