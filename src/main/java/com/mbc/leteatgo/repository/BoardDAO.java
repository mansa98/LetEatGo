package com.mbc.leteatgo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.mbc.leteatgo.domain.BoardVO;

// public interface BoardDAO extends JpaRepository<BoardVO, Integer>{
// 페이징 메서드 추출위해 Repository 교체
// 게시글 관련 모든 CRUD 메소드들(JPA)
public interface BoardDAO extends PagingAndSortingRepository<BoardVO, Integer>{
	
	BoardVO save(BoardVO boardVO);
	
	BoardVO findById(int boardNum);
	
	long count();
	
	int countByBoardTitleLike(String boardTitle); // Like
	int countByBoardTitleContaining(String boardTitle); // Containing
	int countByBoardContentContaining(String boardContent);
	int countByBoardWriterContaining(String boardWriter);
	
	Page<BoardVO> findAll(Pageable pageable);
	
	Page<BoardVO> findByBoardTitleLike(String boardTitle, Pageable pageable); // Like
	Page<BoardVO> findByBoardTitleContaining(String searchKeyword, Pageable pageable); // Containing
	Page<BoardVO> findByBoardContentContaining(String boardContent, Pageable pageable);
	Page<BoardVO> findByBoardWriterContaining(String boardWriter, Pageable pageable);
	
	/* 카테고리 분류 0422 추가 */
	Page<BoardVO> findByBoardCategory(String category, Pageable pageable);
	
	/* 카테고리 수 */
	int countByBoardCategory(String category);
		
	/* 게시글 조회수 갱신 */
	@Modifying
	@Query(value = "UPDATE comm_board_tbl SET " + 
				   "comm_board_count = comm_board_count + 1 " + 
				   "WHERE comm_board_num = :comm_board_num", nativeQuery = true)
	void updateBoardCountByBoardNum(@Param("comm_board_num") int boardNum);

	/* 게시글 삭제 */
	void deleteById(int boardNum);

}