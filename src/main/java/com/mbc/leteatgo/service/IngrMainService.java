package com.mbc.leteatgo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mbc.leteatgo.domain.IngrMainVO;
import com.mbc.leteatgo.repository.IngrMainRepository;

@Service
public class IngrMainService {
	
	@Autowired
	IngrMainRepository ingrMainRepository;
	
	public List<IngrMainVO> selectAll() {
		
		List<IngrMainVO> ingrMainList = new ArrayList<>();
		
		ingrMainList = ingrMainRepository.findAll();
		
		return ingrMainList;
	}

}
