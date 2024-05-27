package com.mbc.leteatgo.domain;

import lombok.Data;

@Data
public class InqReplyDTO {

	private int inqReply; // 댓글 번호
	private int inqNum; // 게시글 번호(comm_inq_tbl 참조)
	private String inqReContent; // 댓글 본문
	private String inqReWriter; // 댓글 작성자
	
	@Override
	public String toString() {
		return "InqReplyDTO [inqReRef=" + inqReply + ", inqNum=" + inqNum + ", inqReContent=" + inqReContent
				+ ", inqReWriter=" + inqReWriter + "]";
	}
	
}
