package com.mbc.leteatgo.domain;

import lombok.Data;

@Data
public class ReplyDTO {

	private int boardReply; // 댓글 번호
	private int boardNum; // 게시글 번호(comm_board_tbl 참조)
	private String boardReContent; // 댓글 본문
	private String boardReWriter; // 댓글 작성자
	
	@Override
	public String toString() {
		return "ReplyDTO [boardReRef=" + boardReply + ", boardNum=" + boardNum + ", boardReContent=" + boardReContent
				+ ", boardReWriter=" + boardReWriter + "]";
	}
	
}
