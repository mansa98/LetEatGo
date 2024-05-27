package com.mbc.leteatgo.domain;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
// 게시글 전송용(Transfer) 자바빈
public class InqDTO {
	
	private int inqNum;
	private String inqTitle;
	private Date inqDate; 
	private String inqWriter;
	private int inqCount = 0;
	private String inqContent;
	private MultipartFile inqFile;
	private String inqOriginalFile;
	
	// 텍스트 모드 (text:기본값) / 멀티미디어 모드(multi)
	private String textMulti = "text";

	// 업로드 파일 (파일명을 확인할 수 있도록 파일명 인쇄) : inqFile.getOriginalFilename()
	@Override
	public String toString() {
		return "InqDTO [inqNum=" + inqNum + ", inqTitle=" + inqTitle
				+ ", inqDate=" + inqDate + ", inqWriter=" + inqWriter + ", inqCount=" + inqCount
				+ ", inqContent=" + inqContent + ", inqFile=" + inqFile.getOriginalFilename() + ", inqOriginalFile="
				+ inqOriginalFile + ", textMulti=" + textMulti + "]";
	}
}
