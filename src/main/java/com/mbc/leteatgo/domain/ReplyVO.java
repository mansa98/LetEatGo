package com.mbc.leteatgo.domain;

import java.io.Serializable;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name="comm_rpl_tbl")
@Slf4j
public class ReplyVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
		    generator = "COMM_RPL_TBL_SEQ_GENERATOR")
			@SequenceGenerator(
			name = "COMM_RPL_TBL_SEQ_GENERATOR",
			sequenceName = "comm_rpl_tbl_seq",
			initialValue = 1,
			allocationSize = 1)
	@Column(name = "comm_rpl_num")
	private int boardReply;
	
	// 얘는 comm_board_tbl 참조
	@Column(name = "comm_board_num")
	private int boardNum;
	
	@Column(name = "comm_rpl_content")
	private String boardReContent;
	
	@Column(name = "comm_rpl_writer")
	private String boardReWriter;
	
	public ReplyVO() {}
	
	// ReplyDTO -> ReplyVO
	public ReplyVO(ReplyDTO reply) {
		this.boardReply = reply.getBoardReply();
		this.boardNum = reply.getBoardNum();
		this.boardReContent = reply.getBoardReContent();
		this.boardReWriter = reply.getBoardReWriter();
	}
	
	public ReplyVO(Map<String, Object> map) {
		
		log.info("ReplyVO 오버로딩 생성자 : Map to VO");
		
		this.boardReply = Integer.parseInt(map.get("boardReply").toString());
		this.boardNum = Integer.parseInt(map.get("boardNum").toString());
		this.boardReContent = (String)map.get("boardReContent");
		this.boardReWriter = (String)map.get("boardReWriter");
	}

	public int getBoardReply() {
		return boardReply;
	}

	public void setBoardReply(int boardreply) {
		this.boardReply = boardreply;
	}

	public int getBoardNum() {
		return boardNum;
	}

	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}

	public String getBoardReContent() {
		return boardReContent;
	}

	public void setBoardReContent(String boardReContent) {
		this.boardReContent = boardReContent;
	}

	public String getBoardReWriter() {
		return boardReWriter;
	}

	public void setBoardReWriter(String boardReWriter) {
		this.boardReWriter = boardReWriter;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReplyVO [boardReply=").append(boardReply).append(", boardNum=").append(boardNum)
				.append(", boardReContent=").append(boardReContent).append(", boardReWriter=").append(boardReWriter)
				.append("]");
		return builder.toString();
	}
	
}
