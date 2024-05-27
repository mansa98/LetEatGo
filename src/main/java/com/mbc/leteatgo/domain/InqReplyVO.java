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
@Table(name="inq_rpl_tbl")
@Slf4j
public class InqReplyVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
		    generator = "INQ_RPL_TBL_SEQ_GENERATOR")
			@SequenceGenerator(
			name = "INQ_RPL_TBL_SEQ_GENERATOR",
			sequenceName = "inq_rpl_tbl_seq",
			initialValue = 1,
			allocationSize = 1)
	@Column(name = "inq_rpl_num")
	private int inqReply;
	
	// 얘는 comm_inq_tbl 참조
	@Column(name = "inq_board_num")
	private int inqNum;
	
	@Column(name = "inq_rpl_content")
	private String inqReContent;
	
	@Column(name = "inq_rpl_writer")
	private String inqReWriter;
	
	public InqReplyVO() {}
	
	// InqReplyDTO -> InqReplyVO
	public InqReplyVO(InqReplyDTO inqReply) {
		this.inqReply = inqReply.getInqReply();
		this.inqNum = inqReply.getInqNum();
		this.inqReContent = inqReply.getInqReContent();
		this.inqReWriter = inqReply.getInqReWriter();
	}
	
	public InqReplyVO(Map<String, Object> map) {
		
		log.info("InqReplyVO 오버로딩 생성자 : Map to VO");
		
		this.inqReply = Integer.parseInt(map.get("inqInqReply").toString());
		this.inqNum = Integer.parseInt(map.get("inqNum").toString());
		this.inqReContent = (String)map.get("inqReContent");
		this.inqReWriter = (String)map.get("inqReWriter");
	}

	public int getInqReply() {
		return inqReply;
	}

	public void setInqReply(int inqReply) {
		this.inqReply = inqReply;
	}

	public int getInqNum() {
		return inqNum;
	}

	public void setInqNum(int inqNum) {
		this.inqNum = inqNum;
	}

	public String getInqReContent() {
		return inqReContent;
	}

	public void setInqReContent(String inqReContent) {
		this.inqReContent = inqReContent;
	}

	public String getInqReWriter() {
		return inqReWriter;
	}

	public void setInqReWriter(String inqReWriter) {
		this.inqReWriter = inqReWriter;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InqReplyVO [inqReply=").append(inqReply).append(", inqNum=").append(inqNum)
				.append(", inqReContent=").append(inqReContent).append(", inqReWriter=").append(inqReWriter)
				.append("]");
		return builder.toString();
	}
	
}
