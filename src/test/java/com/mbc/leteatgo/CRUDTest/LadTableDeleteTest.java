package com.mbc.leteatgo.CRUDTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mbc.leteatgo.repository.LadRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class LadTableDeleteTest {

	@Autowired
	LadRepository ladRepo;
	
	@Test
	void test() {
		
		ladRepo.deleteAll();
		
		long dataNum = ladRepo.count();
		
		log.info("lad_tbl에 남은 데이터 수는: {}", dataNum);
	}

}
