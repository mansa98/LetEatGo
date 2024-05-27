package com.mbc.leteatgo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mbc.leteatgo.domain.UploadFile;

// 게시글 내용 중 삽입 이미지 관련 DAO (JPA)
public interface FileDAO extends JpaRepository<UploadFile, Integer> {
	 
	public UploadFile findOneByFileName(String fileName);
	
	public UploadFile findOneById(int id);
	
	public void deleteById(int id);
	
}