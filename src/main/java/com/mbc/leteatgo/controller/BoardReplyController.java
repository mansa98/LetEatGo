package com.mbc.leteatgo.controller;

//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mbc.leteatgo.domain.ReplyVO;
import com.mbc.leteatgo.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("board")
@Slf4j
public class BoardReplyController {
	
	@Autowired
	BoardService boardService; 

	@PostMapping("/replyWrite.do")
	// public ResponseEntity<Boolean> replyWrite(@RequestBody Map<String, Object> map) {
	// 댓글을 작성하면서 즉시 현재까지의 댓글들 현황을 집계하여 리턴
	public ResponseEntity<List<ReplyVO>> replyWrite(@RequestBody Map<String, Object> map) {
		
		log.info("replyWrite.do : boardNum={}, boardReContent={}", map.get("boardNum"), map.get("boardReContent"));
	
		List<ReplyVO> replyList = new ArrayList<>();

		// ResponseEntity<Boolean> responseEntity = null; 
		ResponseEntity<List<ReplyVO>> responseEntity = null;
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		try {
			ReplyVO replyVO = new ReplyVO();
			
			//
			// 주의사항) 
			// 여기서 댓글의 고유 아이디는 DB를 통해서 생성되므로 원글의 아이디(boardNum)는 다른 필드에 입력됩니다.
			replyVO.setBoardReWriter(map.get("boardReWriter").toString());
			replyVO.setBoardReContent(map.get("boardReContent").toString());
			replyVO.setBoardNum(Integer.parseInt(map.get("boardNum").toString()));
			
			log.info("replyWrite.do : ", replyVO.toString());
			
			// 댓글의 현황을 보면서 댓글 시퀀스 결정
			replyVO = boardService.insertBoard(replyVO);
			
			log.info("--- result : {}", replyVO);
			
			if (replyVO != null) {
				
				// 원글에 따른 전체 댓글 현황 목록(리스트) 가져오기 => 리턴 => Client(웹 브라우저)
				replyList = boardService.selectReplyByBoardNum(replyVO.getBoardNum());				
				
				// 댓글 등록 성공 : 성공 코드(200)
				// responseEntity = new ResponseEntity<>(true, HttpStatus.OK);
				
				// 원글에 따른 전체 댓글 현황 목록(리스트) 리턴(클라리언트에 전송)
				responseEntity = new ResponseEntity<>(replyList, HttpStatus.OK); 
				
			} else {
				// 댓글 등록 실패 : 실패 코드(204)
				// responseEntity = new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
				responseEntity = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			log.error("replyWrite error : {}", e);
			e.printStackTrace();

			// 실패 코드(417) : 내부 서버 에러
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;		
	} //
	
	@GetMapping("/getReply.do")
	public ResponseEntity<ReplyVO> getReply(@RequestParam("boardNum") int boardNum) { 
		
		log.info("getReply.do : boardNum={}", boardNum);
		
		ReplyVO replyVO = null;
		
		ResponseEntity<ReplyVO> responseEntity = null;
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		try {
			// 원글에 따른 전체 댓글 현황 목록(리스트) 가져오기 => 리턴 => Client(웹 브라우저)
			// 원글에 따른 전체 댓글 현황 목록(리스트) 리턴(클라리언트에 전송)
			replyVO = boardService.findById(boardNum);				
			
			if (replyVO != null) {
				
				responseEntity = new ResponseEntity<>(replyVO, HttpStatus.OK); 
				
			} else {
				// 댓글 등록 실패 : 실패 코드(204)
				responseEntity = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			log.error("getReply error : {}", e);
			e.printStackTrace();

			// 실패 코드(417) : 내부 서버 에러
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;		
	} //
	
	@GetMapping("/getRepliesAll.do")
	public ResponseEntity<List<ReplyVO>> getRepliesAll(@RequestParam("boardNum") int boardNum) { 
		
		log.info("getRepliesAll.do : boardNum={}", boardNum);
		
		List<ReplyVO> replyList = new ArrayList<>();
		ResponseEntity<List<ReplyVO>> responseEntity = null;
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		try {
			// 원글에 따른 전체 댓글 현황 목록(리스트) 가져오기 => 리턴 => Client(웹 브라우저)
			// 원글에 따른 전체 댓글 현황 목록(리스트) 리턴(클라리언트에 전송)
			replyList = boardService.selectReplyByBoardNum(boardNum);				
			
			// replyList.forEach(x-> { log.info("날짜 : {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(x.getBoardDate())); });
//			replyList.forEach(x-> { log.info("날짜 : {}", x.getBoardDate()); });
			
			// 댓글들이 있다면...
			if (replyList.size() > 0) {

				// 댓글 등록 성공 : 성공 코드(200)
				// 원글에 따른 전체 댓글 현황 목록(리스트) 리턴(클라리언트에 전송)
				responseEntity = new ResponseEntity<>(replyList, HttpStatus.OK); 
				
			} else {
				// 댓글 등록 실패 : 실패 코드(204)
				responseEntity = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			log.error("getRepliesAll error : {}", e);
			e.printStackTrace();

			// 실패 코드(417) : 내부 서버 에러
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;		
	} //
	
	@PostMapping("/replyUpdate.do")
	public ResponseEntity<List<ReplyVO>> replyUpdate(@RequestBody Map<String, Object> map) { 
		
		log.info("replyUpdate.do : boardReply={}, boardNum={}, boardReContent={}, boardReWriter={}", 
					map.get("boardReply"), map.get("boardNum"), map.get("boardReContent"), map.get("boardReWriter"));
		
		List<ReplyVO> replyList = new ArrayList<>();

		ResponseEntity<List<ReplyVO>> responseEntity = null;
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		try {
			ReplyVO replyVO = new ReplyVO();
			
			int boardNum = Integer.parseInt(map.get("boardNum").toString());
			
			// 주의) 댓글 수정에서는 댓글의 아이디가 이미 등록시 발행이 되어 있으므로 댓글의 실제 아이디 !
			replyVO.setBoardReply(Integer.parseInt(map.get("boardReply").toString()));
			replyVO.setBoardNum(boardNum);  
			replyVO.setBoardReWriter(map.get("boardReWriter").toString());
			replyVO.setBoardReContent(map.get("boardReContent").toString());
			
			log.info("ReplyVO : {}", replyVO);
			
			replyVO = boardService.updateBoard(replyVO);
			
			if (replyVO != null) {
				
				// 원글에 따른 전체 댓글 현황 목록(리스트) 가져오기 => 리턴 => Client(웹 브라우저)
				replyList = boardService.selectReplyByBoardNum(replyVO.getBoardNum());				
				
				// 댓글 등록 성공 : 성공 코드(200)
				// responseEntity = new ResponseEntity<>(true, HttpStatus.OK);
				
				// 원글에 따른 전체 댓글 현황 목록(리스트) 리턴(클라리언트에 전송)
				responseEntity = new ResponseEntity<>(replyList, HttpStatus.OK); 
				
			} else {
				// 댓글 등록 실패 : 실패 코드(204)
				// responseEntity = new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
				responseEntity = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			log.error("replyUpdate error : {}", e);
			e.printStackTrace();

			// 실패 코드(417) : 내부 서버 에러
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;		
	} //
	
	@PostMapping("/replyDelete.do")
	public ResponseEntity<List<ReplyVO>> replyDelete(@RequestBody Map<String, Object> map) { 
		
		log.info("replyDelete.do : boardReply={}, originalBoardNum={}", 
					map.get("boardReply"), map.get("originalBoardNum"));
		
		List<ReplyVO> replyList = new ArrayList<>();

		ResponseEntity<List<ReplyVO>> responseEntity = null;
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		try {
			
			int boardReply = Integer.parseInt(map.get("boardReply").toString());
			int originalBoardNum = Integer.parseInt(map.get("originalBoardNum").toString());
			
			boolean result = false; // 삭제 결과
			
			result = boardService.deleteByBoardReply(boardReply);
			
			if (result == true) {
				
				// 원글에 따른 전체 댓글 현황 목록(리스트) 가져오기 => 리턴 => Client(웹 브라우저)
				replyList = boardService.selectReplyByBoardNum(originalBoardNum);				
				
				// 원글에 따른 전체 댓글 현황 목록(리스트) 리턴(클라리언트에 전송)
				responseEntity = new ResponseEntity<>(replyList, HttpStatus.OK); 
				
			} else {
				// 댓글 등록 실패 : 실패 코드(204)
				// responseEntity = new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
				responseEntity = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			log.error("replyDelete error : {}", e);
			e.printStackTrace();

		// 실패 코드(417) : 내부 서버 에러
		responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
		
		
		return responseEntity;		
	} //
	
}