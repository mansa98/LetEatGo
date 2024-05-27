package com.mbc.leteatgo.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.mbc.leteatgo.domain.InqReplyVO;


public interface InqReplyDAO extends PagingAndSortingRepository<InqReplyVO, Integer> {
	
	InqReplyVO save(InqReplyVO inqReplyVO);
	
	InqReplyVO findByInqReply(int inqReply);
	
	// 원글에 따른 소속 댓글들 가져오기
	List<InqReplyVO> findByInqNum(int inqNum);
	
	// 댓글 제외한 원글들만의 게시글 수 : inqReRef = 0
	long countByInqReply(int inqReRef);
	
	// 댓글 삭제
	void deleteByInqReply(int inqReply);
	
	
}
