package com.mbc.leteatgo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mbc.leteatgo.domain.FavVO;

public interface FavRepository extends JpaRepository<FavVO, Integer> {
	
	FavVO save(FavVO favVO);
	
	void deleteById(int favNum);
	
//	@Query(value="DELETE FROM fav_tbl ft WHERE ft.recipe_num=:recipeNum AND ft.member_id=:memberId", nativeQuery=true)
//	void deleteById(@Param("recipeNum") int recipeNum, @Param("memberId") String memberId);
	
	List<FavVO> findByRecipeNum(int recipeNum);
	
	@Query(value="SELECT * FROM fav_tbl ft WHERE ft.member_id = :memberId", nativeQuery=true)
	List<FavVO> findAllByMemberId(@Param("memberId")String memberId);
	
	@Query(value="SELECT COUNT(*) FROM fav_tbl ft WHERE ft.member_id = :memberId", nativeQuery=true)
	int countByMemberId(@Param("memberId")String memberId);		

}
