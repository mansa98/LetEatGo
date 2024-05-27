package com.mbc.leteatgo.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.mbc.leteatgo.domain.NtcVO;

// public interface NtcDAO extends JpaRepository<NtcNtcVO, Integer>{
// 페이징 메서드 추출위해 Repository 교체
// 게시글 관련 모든 CRUD 메소드들(JPA)
public interface NtcDAO extends PagingAndSortingRepository<NtcVO, Integer>{
	
	NtcVO save(NtcVO ntcVO);
	
	long count();
	
	Page<NtcVO> findAll(Pageable pageable);
	
	NtcVO findById(int ntcNum);
	
	int countByNtcTitleLike(String ntcTitle); // Like
	int countByNtcTitleContaining(String ntcTitle); // Containing
	int countByNtcContentContaining(String ntcContent);
	int countByNtcWriterContaining(String ntcWriter);
	
	Page<NtcVO> findByNtcTitleLike(String ntcTitle, Pageable pageable); // Like
	Page<NtcVO> findByNtcTitleContaining(String searchKeyword, Pageable pageable); // Containing
	Page<NtcVO> findByNtcContentContaining(String ntcContent, Pageable pageable);
	Page<NtcVO> findByNtcWriterContaining(String ntcWriter, Pageable pageable);
	
	// 게시글 조회수 갱신
	@Modifying
	@Query(value = "UPDATE ntc_board_tbl SET " + 
				   "ntc_board_count = ntc_board_count + 1 " + 
				   "WHERE ntc_board_num = :ntc_board_num", nativeQuery = true)
	void updateNtcCountByNtcNtcNum(@Param("ntc_board_num") int ntcNum);

	// 게시글 삭제
	void deleteById(int ntcNum);

	
}