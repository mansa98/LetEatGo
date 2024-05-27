package com.mbc.leteatgo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.mbc.leteatgo.domain.InqVO;
import com.mbc.leteatgo.domain.InqReplyVO;

// public interface InqDAO extends JpaRepository<InqVO, Integer>{
// 페이징 메서드 추출위해 Repository 교체
// 게시글 관련 모든 CRUD 메소드들(JPA)
public interface InqDAO extends PagingAndSortingRepository<InqVO, Integer>{
	
	InqVO save(InqVO inqVO);
	
	long count();
	
	Page<InqVO> findAll(Pageable pageable);
	
	InqVO findById(int inqNum);
	
	int countByInqTitleLike(String inqTitle); // Like
	int countByInqTitleContaining(String inqTitle); // Containing
	int countByInqContentContaining(String inqContent);
	int countByInqWriterContaining(String inqWriter);
	
	Page<InqVO> findByInqTitleLike(String inqTitle, Pageable pageable); // Like
	Page<InqVO> findByInqTitleContaining(String searchKeyword, Pageable pageable); // Containing
	Page<InqVO> findByInqContentContaining(String inqContent, Pageable pageable);
	Page<InqVO> findByInqWriterContaining(String inqWriter, Pageable pageable);
	// 카테고리 분류 0422 추가
	
//	// 원글에 따른 소속 댓글들 가져오기
//	List<InqReplyVO> findByInqInqReply(int inqInqReply);
	
	// 댓글 제외한 원글들만의 게시글 수 : inqReRef = 0
//	long countByInqReRef(int inqReRef);
		
//	 댓글 제외한 원글들만의 게시글들만 가져오기(페이징) : inqReRef = 0
//	Page<InqVO> findByInqNum(int inqReRef, Pageable pageable); 
		
	// 게시글 조회수 갱신
	@Modifying
	@Query(value = "UPDATE inq_board_tbl SET " + 
				   "inq_board_count = inq_board_count + 1 " + 
				   "WHERE inq_board_num = :inq_board_num", nativeQuery = true)
	void updateInqCountByInqNum(@Param("inq_board_num") int inqNum);

	// 게시글 삭제
	void deleteById(int inqNum);

	
}