package com.mbc.leteatgo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mbc.leteatgo.domain.LadVO;
import com.mbc.leteatgo.domain.RecipeVO;
import com.mbc.leteatgo.service.LadService;
import com.mbc.leteatgo.service.RecipeRecommService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/recomm")
public class RecommendationRestController {
	
	@Autowired
	LadService ladService;
	
	@Autowired
	RecipeRecommService recipeRecommService;

	@PostMapping("/getRecomm")
	public ResponseEntity<List<RecipeVO>> getRecomm(@RequestBody String requestMsg) {

		ResponseEntity<List<RecipeVO>> responseEntity = null;
		
		List<RecipeVO> recipeRecommList = new ArrayList<>();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String memberId = auth.getName();
		
//		LadVO testLadVO = ladService.getLadByMemberId(memberId); 
//		
//		log.info("ladVO 가져와지나? {}", testLadVO.toString());

		log.info("axios에서 인자 잘 넘어왔니? {}", requestMsg);
		
		String result = "";
		
		String msg = "";

		try {

			// TODO: DB 체크
			if (requestMsg.equals("{\"request_msg\":\"request:해줘\"}")) {
				
				recipeRecommList = recipeRecommService.getRecipesRecommendation(memberId);

				result = "요청 잘 넘어옴";
			} else {

				result = "요청 안 넘어옴";
			}

			log.info("--- result : {}", result);

			if (result.equals("요청 잘 넘어옴")) {

				// msg = "※ axios => restController 전송 성공적";

				responseEntity = new ResponseEntity<>(recipeRecommList, HttpStatus.OK);

			} 

			// log.info("전송되는 메시지: {}", msg);

		} catch (Exception e) {
			log.error("Survey Error : {}", e);
			e.printStackTrace();

			//String errMsg = "에러 났어 슈발!";

			// 실패 코드(417) : 내부 서버 에러
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}

}
