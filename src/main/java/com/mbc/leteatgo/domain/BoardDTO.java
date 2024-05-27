package com.mbc.leteatgo.domain;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
// 게시글 전송용(Transfer) 자바빈
public class BoardDTO {
	
	private int boardNum;
	private String boardCategory;
	private String boardTitle;
	private Date boardDate; 
	private String boardWriter;
	private int boardCount = 0;
	private String boardContent;
	private MultipartFile boardFile;
	private String boardOriginalFile;
	
	// 텍스트 모드 (text:기본값) / 멀티미디어 모드(multi)
	private String textMulti = "text";

	// 업로드 파일 (파일명을 확인할 수 있도록 파일명 인쇄) : boardFile.getOriginalFilename()
	@Override
	public String toString() {
		return "BoardDTO [boardNum=" + boardNum + ", boardCategory=" + boardCategory + ", boardTitle=" + boardTitle
				+ ", boardDate=" + boardDate + ", boardWriter=" + boardWriter + ", boardCount=" + boardCount
				+ ", boardContent=" + boardContent + ", boardFile=" + boardFile.getOriginalFilename() + ", boardOriginalFile="
				+ boardOriginalFile + ", textMulti=" + textMulti + "]";
	}
}
