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

import com.mbc.leteatgo.domain.InqReplyVO;
import com.mbc.leteatgo.service.InqService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("inq")
@Slf4j
public class InqReplyController {
	
	@Autowired
	InqService inqService; 

	@PostMapping("/replyWrite.do")
	// public ResponseEntity<Boolean> inqReplyWrite(@RequestBody Map<String, Object> map) {
	// 댓글을 작성하면서 즉시 현재까지의 댓글들 현황을 집계하여 리턴
	public ResponseEntity<List<InqReplyVO>> inqReplyWrite(@RequestBody Map<String, Object> map) {
		
		log.info("inqReplyWrite.do : inqNum={}, inqReContent={}", map.get("inqNum"), map.get("inqReContent"));
	
		List<InqReplyVO> inqReplyList = new ArrayList<>();

		// ResponseEntity<Boolean> responseEntity = null; 
		ResponseEntity<List<InqReplyVO>> responseEntity = null;
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		try {
			InqReplyVO inqReplyVO = new InqReplyVO();
			
			//
			// 주의사항) 
			// 여기서 댓글의 고유 아이디는 DB를 통해서 생성되므로 원글의 아이디(inqNum)는 다른 필드에 입력됩니다.
			inqReplyVO.setInqReWriter(map.get("inqReWriter").toString());
			inqReplyVO.setInqReContent(map.get("inqReContent").toString());
			inqReplyVO.setInqNum(Integer.parseInt(map.get("inqNum").toString()));
			
			log.info("inqReplyWrite.do : ", inqReplyVO.toString());
			
			// 댓글의 현황을 보면서 댓글 시퀀스 결정
			inqReplyVO = inqService.insertInq(inqReplyVO);
			
			log.info("--- result : {}", inqReplyVO);
			
			if (inqReplyVO != null) {
				
				// 원글에 따른 전체 댓글 현황 목록(리스트) 가져오기 => 리턴 => Client(웹 브라우저)
				inqReplyList = inqService.selectInqReplyByInqNum(inqReplyVO.getInqNum());				
				
				// 댓글 등록 성공 : 성공 코드(200)
				// responseEntity = new ResponseEntity<>(true, HttpStatus.OK);
				
				// 원글에 따른 전체 댓글 현황 목록(리스트) 리턴(클라리언트에 전송)
				responseEntity = new ResponseEntity<>(inqReplyList, HttpStatus.OK); 
				
			} else {
				// 댓글 등록 실패 : 실패 코드(204)
				// responseEntity = new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
				responseEntity = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			log.error("inqReplyWrite error : {}", e);
			e.printStackTrace();

			// 실패 코드(417) : 내부 서버 에러
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;		
	} //
	
	@GetMapping("/getReply.do")
	public ResponseEntity<InqReplyVO> getReply(@RequestParam("inqNum") int inqNum) { 
		
		log.info("getReply.do : inqNum={}", inqNum);
		
		InqReplyVO inqReplyVO = null;
		
		ResponseEntity<InqReplyVO> responseEntity = null;
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		try {
			// 원글에 따른 전체 댓글 현황 목록(리스트) 가져오기 => 리턴 => Client(웹 브라우저)
			// 원글에 따른 전체 댓글 현황 목록(리스트) 리턴(클라리언트에 전송)
			inqReplyVO = inqService.findById(inqNum);				
			
			if (inqReplyVO != null) {
				
				responseEntity = new ResponseEntity<>(inqReplyVO, HttpStatus.OK); 
				
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
	public ResponseEntity<List<InqReplyVO>> getRepliesAll(@RequestParam("inqNum") int inqNum) { 
		
		log.info("getRepliesAll.do : inqNum={}", inqNum);
		
		List<InqReplyVO> inqReplyList = new ArrayList<>();
		ResponseEntity<List<InqReplyVO>> responseEntity = null;
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		try {
			// 원글에 따른 전체 댓글 현황 목록(리스트) 가져오기 => 리턴 => Client(웹 브라우저)
			// 원글에 따른 전체 댓글 현황 목록(리스트) 리턴(클라리언트에 전송)
			inqReplyList = inqService.selectInqReplyByInqNum(inqNum);				
			
			// inqReplyList.forEach(x-> { log.info("날짜 : {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(x.getInqDate())); });
//			inqReplyList.forEach(x-> { log.info("날짜 : {}", x.getInqDate()); });
			
			// 댓글들이 있다면...
			if (inqReplyList.size() > 0) {

				// 댓글 등록 성공 : 성공 코드(200)
				// 원글에 따른 전체 댓글 현황 목록(리스트) 리턴(클라리언트에 전송)
				responseEntity = new ResponseEntity<>(inqReplyList, HttpStatus.OK); 
				
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
	public ResponseEntity<List<InqReplyVO>> inqReplyUpdate(@RequestBody Map<String, Object> map) { 
		
		log.info("inqReplyUpdate.do : inqReply={}, inqNum={}, inqReContent={}, inqReWriter={}", 
					map.get("inqReply"), map.get("inqNum"), map.get("inqReContent"), map.get("inqReWriter"));
		
		List<InqReplyVO> inqReplyList = new ArrayList<>();

		ResponseEntity<List<InqReplyVO>> responseEntity = null;
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		try {
			InqReplyVO inqReplyVO = new InqReplyVO();
			
			int inqNum = Integer.parseInt(map.get("inqNum").toString());
			
			// 주의) 댓글 수정에서는 댓글의 아이디가 이미 등록시 발행이 되어 있으므로 댓글의 실제 아이디 !
			inqReplyVO.setInqReply(Integer.parseInt(map.get("inqReply").toString()));
			inqReplyVO.setInqNum(inqNum);  
			inqReplyVO.setInqReWriter(map.get("inqReWriter").toString());
			inqReplyVO.setInqReContent(map.get("inqReContent").toString());
			
			log.info("InqReplyVO : {}", inqReplyVO);
			
			inqReplyVO = inqService.updateInq(inqReplyVO);
			
			if (inqReplyVO != null) {
				
				// 원글에 따른 전체 댓글 현황 목록(리스트) 가져오기 => 리턴 => Client(웹 브라우저)
				inqReplyList = inqService.selectInqReplyByInqNum(inqReplyVO.getInqNum());				
				
				// 댓글 등록 성공 : 성공 코드(200)
				// responseEntity = new ResponseEntity<>(true, HttpStatus.OK);
				
				// 원글에 따른 전체 댓글 현황 목록(리스트) 리턴(클라리언트에 전송)
				responseEntity = new ResponseEntity<>(inqReplyList, HttpStatus.OK); 
				
			} else {
				// 댓글 등록 실패 : 실패 코드(204)
				// responseEntity = new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
				responseEntity = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			log.error("inqReplyUpdate error : {}", e);
			e.printStackTrace();

			// 실패 코드(417) : 내부 서버 에러
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;		
	} //
	
	@PostMapping("/replyDelete.do")
	public ResponseEntity<List<InqReplyVO>> inqReplyDelete(@RequestBody Map<String, Object> map) { 
		
		log.info("inqReplyDelete.do : inqReply={}, originalInqNum={}", 
					map.get("inqReply"), map.get("originalInqNum"));
		
		List<InqReplyVO> inqReplyList = new ArrayList<>();

		ResponseEntity<List<InqReplyVO>> responseEntity = null;
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		try {
			
			int inqReply = Integer.parseInt(map.get("inqReply").toString());
			int originalInqNum = Integer.parseInt(map.get("originalInqNum").toString());
			
			boolean result = false; // 삭제 결과
			
			result = inqService.deleteByInqReply(inqReply);
			
			if (result == true) {
				
				// 원글에 따른 전체 댓글 현황 목록(리스트) 가져오기 => 리턴 => Client(웹 브라우저)
				inqReplyList = inqService.selectInqReplyByInqNum(originalInqNum);				
				
				// 원글에 따른 전체 댓글 현황 목록(리스트) 리턴(클라리언트에 전송)
				responseEntity = new ResponseEntity<>(inqReplyList, HttpStatus.OK); 
				
			} else {
				// 댓글 등록 실패 : 실패 코드(204)
				// responseEntity = new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
				responseEntity = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			log.error("inqReplyDelete error : {}", e);
			e.printStackTrace();

		// 실패 코드(417) : 내부 서버 에러
		responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
		
		
		return responseEntity;		
	} //
	
}