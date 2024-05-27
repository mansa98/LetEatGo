package com.mbc.leteatgo.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.mbc.leteatgo.domain.ReplyVO;

public interface ReplyDAO extends PagingAndSortingRepository<ReplyVO, Integer> {
	
	ReplyVO save(ReplyVO replyVO);
	
	ReplyVO findByBoardReply(int boardReply);
	
	// 원글에 따른 소속 댓글들 가져오기
	List<ReplyVO> findByBoardNum(int boardNum);
	
	// 댓글 제외한 원글들만의 게시글 수 : boardReRef = 0
	long countByBoardReply(int boardReRef);
	
	// 댓글 삭제
	void deleteByBoardReply(int boardReply);
	
	
}
