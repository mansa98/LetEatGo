package com.mbc.leteatgo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mbc.leteatgo.domain.LadVO;
import com.mbc.leteatgo.repository.LadRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LadService {

	
	@Autowired
	LadRepository ladRepository;
	
	public void insertLad(LadVO ladVO) {
		
		ladRepository.save(ladVO);
	}
	
	public void insertLad(String id, String like, String dislike) {
		
		// ladVO 생성자 (멤버id, like스트링, dislike스트링)
		
		LadVO ladVO = LadVO.builder()
						   .memberId(id)
						   .ladLike(like)
						   .ladDislike(dislike)
						   .build();
		
		ladRepository.save(ladVO);
	}
	
	
	public LadVO getLadByMemberId(String memberId) {
		
		LadVO ladVO = new LadVO();
		
		ladVO = ladRepository.findByMemberId(memberId);
		
		return ladVO; 
	}
	
	@Transactional
	public void updateLad(String memberId, LadVO ladVO) {
		String like = ladVO.getLadLike();
		String dislike = ladVO.getLadDislike();
		
		log.info("like는 {}, dislilke는 {}, memberId는 {}", like, dislike, memberId);
		
		ladRepository.update(like, dislike, memberId);
		
	}
	
}
