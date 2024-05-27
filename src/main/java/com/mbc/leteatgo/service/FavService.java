package com.mbc.leteatgo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mbc.leteatgo.domain.FavDTO;
import com.mbc.leteatgo.domain.FavVO;
import com.mbc.leteatgo.domain.RecipeVO;
import com.mbc.leteatgo.repository.FavRepository;
import com.mbc.leteatgo.repository.RecipeRepository;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class FavService {
	
	@Autowired
	FavRepository favRepo;
	
	@Autowired
	RecipeRepository recipeRepository;
	
	
	
	@Transactional(rollbackFor = Exception.class)
	public boolean insertFav(FavVO favVO) {
		
		boolean result = false;
		
		try {
			
			favRepo.save(favVO);
			
			result = true;
			
		} catch(Exception e) {
			
			log.error("즐겨찾기 인서트 실패");
		}
		
		return result;
	}// insertFav()
	
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteFav(int favNum) {
		
		boolean result = false;
		
		try {
			
			favRepo.deleteById(favNum);
			
			result = true;
			
		} catch(Exception e) {
			
			log.error("즐겨찾기 삭제 실패");
		}
		
		
		return result;
	}// deleteFav()
	
	
	public List<FavVO> getFavByRecipeNum(int recipeNum) {
		
		List<FavVO> favList = new ArrayList<>();
		
		return favList = favRepo.findByRecipeNum(recipeNum);
		
	}// getFavByRecipeNum()
	
	
	@Transactional(readOnly = true)
	public FavDTO getFavDTO(int recipeNum) {
		
		RecipeVO recipeVO = recipeRepository.findById(recipeNum);
		
//		// fav_tbl에 저장 하는 코드
//		FavVO favVO = new FavVO();
//		favVO.setMemberId(memberId);
//		favVO.setRecipeNum(recipeNum);
		
//		favRepo.save(favVO);
		
		// 회원 아이디(member_tbl.member_id),
		// 레시피 넘버(recipe_tbl.recipe_num),
		// 사진(recipe_tbl.recipe_image),
		// 제작자(recipe_tbl.recipe_og_writer),
		// 타이틀(recipe_tbl.recipe_title)
		return FavDTO.builder()
					  .recipeNum(recipeNum)
					  .recipeImage(recipeVO.getRecipeImage())
					  .recipeOgWriter(recipeVO.getRecipeOgWriter())
					  .recipeTitle(recipeVO.getRecipeTitle())
					  .build();
	}// getFavDTO()
	
	
	public int getDataCountByMemberId(String memberId) {
		
		
		return favRepo.countByMemberId(memberId);
	}// getDataCountByMemberId()
	
	
	public List<FavVO> getFavList(String memberId) {
		
		List<FavVO> favList = new ArrayList<>();
		
		favList = favRepo.findAllByMemberId(memberId);
		
		return favList;
	}// getFavList()
	
	

}
