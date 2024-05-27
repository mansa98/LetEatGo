package com.mbc.leteatgo.service;

import java.util.ArrayList;
import java.util.List;

//import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.mbc.leteatgo.repository.InqDAO;
import com.mbc.leteatgo.repository.InqReplyDAO;
import com.mbc.leteatgo.domain.InqReplyVO;
import com.mbc.leteatgo.domain.InqVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
//게시글 관련 서비스
public class InqService {
	
	@Autowired
	InqDAO inqDAO;
	
	@Autowired
	InqReplyDAO inqReplyDAO;
	
	@Transactional(rollbackFor = Exception.class)
	public InqVO insertInq(InqVO inqVO) {
		
		return inqDAO.save(inqVO);
	}
	
	@Transactional(readOnly = true)
	public int selectInqsCount() {
		
		return (int)inqDAO.count();
	} //

	// 게시글 리스트 페이지 처리
	public Page<InqVO> inqList(Pageable pageable) {
			
		return inqDAO.findAll(pageable);
	}
	// 게시글 검색 처리 (검색 오류 >> 확인)
	public Page<InqVO> inqSearchList(String searchKeyword, Pageable pageable) {
			
		return inqDAO.findByInqTitleContaining(searchKeyword, pageable);
	}
		
		
	@Transactional(readOnly = true)
	public List<InqVO> selectInqsByPaging(int currPage, int limit) {
				
		Pageable pageable = PageRequest.of(currPage-1, limit, Sort.by(Direction.DESC, "inqNum"));
		return inqDAO.findAll(pageable).getContent();
	} //
	


	@Transactional(readOnly = true)
	public InqVO selectInq(int inqNum) {
		
		return inqDAO.findById(inqNum);
	}

	@Transactional(readOnly = true)
	public int selectInqsCountBySearching(String searchKey, String searchWord) {

		// return searchKey.equals("inq_Title") ? inqDAO.countByInqSubjectLike("%"+searchWord+"%") : 
		return searchKey.equals("inq_title") ? inqDAO.countByInqTitleContaining(searchWord) :
			   searchKey.equals("inq_content") ? inqDAO.countByInqContentContaining(searchWord) : 
			   inqDAO.countByInqWriterContaining(searchWord);	
		
	}

	@Transactional(readOnly = true)
	public List<InqVO> selectInqsBySearching(int currPage, int limit, String searchKey, String searchWord) {
		
		Pageable pageable = PageRequest.of(currPage-1, limit, Sort.by(Direction.DESC, "inqNum"));
		
		// return searchKey.equals("inq_subject") ? inqDAO.findByInqTitleLike("%"+searchWord+"%", pageable).getContent() : 
		return searchKey.equals("inq_title") ? inqDAO.findByInqTitleContaining(searchWord, pageable).getContent() :
			   searchKey.equals("inq_content") ? inqDAO.findByInqContentContaining(searchWord, pageable).getContent() : 
			   inqDAO.findByInqWriterContaining(searchWord, pageable).getContent();
	}
	
	// imgUploadPath = /inq/image/
	public List<Integer> getImageList(String str, String imgUploadPath) {

		log.info("InqService.getImageList");
		List<Integer> imgList = new ArrayList<>(); // upload_file_tbl 테이블의 PK(기본키)
		
		if (str.contains(imgUploadPath) == false) { // 이미지 미포함
			
			log.info("이미지가 전혀 포함되어 있지 않습니다.");
			
		} else {

			// 포함된 전체 이미지 수 : 이 한계량 만큼 검색  => 카운터에 반영
			int imgLen = StringUtils.countOccurrencesOf(str, imgUploadPath);
			
			log.info("imgLen : " + imgLen);
			
			// 이미지 검색 카운터 설정 : 이미지 검색할 횟수
			int count = 0;  
			
			int initPos = str.indexOf(imgUploadPath);
			log.info("첫 발견 위치 : " + initPos);
			
			// 추출된 문자열 : 반복문에서 사용
			String subStr = str;
			
			while (count < imgLen) {
				
				initPos = subStr.indexOf(imgUploadPath);
				
				// 이미지 파일만 추출 (첫번째)
				// "/inq/image/".length()
				initPos += imgUploadPath.length();
				log.info("이미지 파일 시작 위치 : " + initPos);
				
				// 추출된 문자열
				// ex) 41 (.../inq/image/41" : upload_file_tbl 테이블의 삽입 이미지 PK(기본키))
				subStr = subStr.substring(initPos);
				
				log.info("subStr : " + subStr);
				
				// 첫번째 " (큰 따옴표) 위치 검색하여 순수한 숫자(PK)만 추출
				int quotMarkPos = subStr.indexOf("\"");
				
				// 이미지 파일 끝 검색하여 이미지 파일명/확장자 추출
				// 이미지 끝 검색 : 검색어(" )
				int imgFileNum = Integer.parseInt(subStr.substring(0, quotMarkPos));
				
				log.info("이미지 파일 테이블 PK(기본기) : " + imgFileNum);
				
				count++; // 이미지 추출되었으므로 카운터 증가
				
				imgList.add(imgFileNum); // 리스트에 추가
				
				log.info("----------------------------------------");
			
			} //  while
		
		} // if

		return imgList;
	}
	// 본글 수정
	@Transactional(rollbackFor = Exception.class)
	public InqVO updateInq(InqVO inqVO) {
		
		return inqDAO.save(inqVO);
	}
	// 댓글 수정
	@Transactional(rollbackFor = Exception.class)
	public InqReplyVO updateInq(InqReplyVO inqReplyVO) {
		
		return inqReplyDAO.save(inqReplyVO);
	}
	
	// 댓글
	@Transactional(rollbackFor = Exception.class)
	public List<InqReplyVO> selectInqReplyByInqNum(int inqNum){
		
		return inqReplyDAO.findByInqNum(inqNum);
	}
	// 댓글
	@Transactional(rollbackFor = Exception.class)
	public InqReplyVO insertInq(InqReplyVO inqReplyVO) {
		
		return inqReplyDAO.save(inqReplyVO);
	}
	// 댓글
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteByInqReply(int inqReply) {
		
		boolean result = false;
		
		try {
			inqReplyDAO.deleteByInqReply(inqReply);
			result = true;
		} catch (Exception e) {
			log.error("deleteByInqReply error : {}", e);
			result = false;
		}
		
		return result;
	}
	
	// 댓글 수량 조회
	@Transactional(readOnly = true)
	public int selectInqsCountWithReplies(int inqNum) {
		
		return (int)inqReplyDAO.countByInqReply(inqNum); // 댓글의 갯수 추출 : inq_re_ref = inqNum
	} //
	
//	@Transactional(readOnly = true)
//	public int selectInqsCountWithoutReplies() {
//		
//		return (int)inqDAO.countByInqReRef(0); // (댓글 아닌)원글만 추출 : inq_re_ref = 0
//	} //
//	
//	@Transactional(readOnly = true)
//	public List<InqVO> selectInqsByPagingWithoutReplies(int currPage, int limit) {
//				
//		Pageable pageable = PageRequest.of(currPage-1, limit, Sort.by(Direction.DESC, "inqNum"));
//		// return inqDAO.findAll(pageable).getContent();
//		return inqDAO.findByInqNum(0, pageable).getContent(); // (댓글 아닌)원글만 추출 : inq_re_ref = 0
//	} //
	
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteInqReplysById(int inqNum) {
		
		boolean result = false;
		
		try {
			inqDAO.deleteById(inqNum);
			result = true;
		} catch (Exception e) {
			log.error("deleteInqReplyById error : {}", e);
			result = false;
		}
		
		return result;
	}
	
	// 게시글(원글) 삭제
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteById(int inqNum) {
		
		boolean result = false;
		
		try {
			inqDAO.deleteById(inqNum);
			result = true;
		} catch (Exception e) {
			log.error("deleteById error : {}", e);
			result = false;
		}
		
		return result;
	}
	
	// 게시글 조회수 갱신
	@Transactional(rollbackFor = Exception.class)
	public boolean updateInqCountByInqNum(int inqNum) {
		
		boolean result = false;
		
		try {
			inqDAO.updateInqCountByInqNum(inqNum);
			result = true;
		} catch (Exception e) {
			log.error("updateInqByInqNum error : {}", e);
			result = false;
		}
		
		return result;
	}
	
	@Transactional(readOnly=true)
	public InqReplyVO findById(int inqNum) {
		
		return inqReplyDAO.findByInqReply(inqNum);
	}
}