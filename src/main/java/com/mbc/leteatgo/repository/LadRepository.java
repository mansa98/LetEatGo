package com.mbc.leteatgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mbc.leteatgo.domain.LadVO;

public interface LadRepository extends JpaRepository<LadVO, Integer> {
	
	public LadVO save(LadVO ladVO);
	
	public LadVO findByMemberId(String memberId);
	
	@Modifying
	@Query(value="UPDATE lad_tbl lt SET lt.lad_like=:like, lt.lad_dislike=:dislike WHERE lt.member_id=:memberId", nativeQuery = true)
	// @Query(value="UPDATE FavVO f SET f.favLike=:like, f.favDislike=:dislike WHERE f.memberId=:memberId")
	public void update(@Param("like") String like, @Param("dislike") String dislike, @Param("memberId") String memberId);
	
}
