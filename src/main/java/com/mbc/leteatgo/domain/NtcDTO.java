package com.mbc.leteatgo.domain;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class NtcDTO {

	private int ntcNum;
	private String ntcTitle;
	private Date ntcDate;
	private String ntcWriter;
	private int ntcCount = 0;
	private String ntcContent;
	private MultipartFile ntcFile;
	private String ntcOriginalFile;
	
	private String textMulti = "text";

	@Override
	public String toString() {
		return "NtcBoardDTO [ntcNum=" + ntcNum + ", ntcTitle=" + ntcTitle + ", ntcDate=" + ntcDate + ", ntcWriter="
				+ ntcWriter + ", ntcCount=" + ntcCount + ", ntcContent=" + ntcContent + ", ntcFile=" + ntcFile
				+ ", ntcOriginalFile=" + ntcOriginalFile + ", textMulti=" + textMulti + "]";
	}
}