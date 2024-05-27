package com.mbc.leteatgo.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.Map;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mbc.leteatgo.util.FileUploadUtil;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author oracle
 *
 */
@Slf4j
@Entity
@Table(name="inq_board_tbl")
// Entity Bean (게시판 테이블 대응 자바빈)
public class InqVO implements Serializable { // 10.25 (session으로 변환할 경우 에러 방지)
	
	private static final long serialVersionUID = 1L;
	
	/** 게시글 번호 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
		    generator = "INQ_BOARD_TBL_SEQ_GENERATOR")
			@SequenceGenerator(
			name = "INQ_BOARD_TBL_SEQ_GENERATOR",
			sequenceName = "inq_board_tbl_seq",
			initialValue = 1,
			allocationSize = 1)
	@Column(name = "inq_board_num") 
	private int inqNum;
	
	/** 게시글 제목 */
	@Column(name = "inq_board_title") 
	private String inqTitle; 
	
	/** 게시글 작성일자 */
	@Column(name = "inq_board_date")
	@CreationTimestamp // 작성 날짜 (기본값) 생성
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") // Json 변환시 "년월일 및 시분초"까지 출력 (수정해야...)
	private Date inqDate; 
	
	/** 게시글 작성자 */
	@Column(name = "inq_board_writer") 
	private String inqWriter; 
	
	/** 게시글 조회수 */
	@Column(name = "inq_board_count") 
	private int inqCount = 0; 
	
	/** 게시글 내용 */
	@Column(name = "inq_board_content") 
	private String inqContent; 
	
	/** 게시글 첨부파일 (인코딩 된 파일명) */
	@Column(name = "inq_board_file") 
	private String inqFile; 
	
	/** 게시글 원래 파일명 */
	@Column(name = "inq_board_original_file") 
	private String inqOriginalFile; 
	
	public InqVO() {}
	
	// InqDTO -> InqVO
	public InqVO(InqDTO inq) {
		
		this.inqNum = inq.getInqNum();
		this.inqTitle = inq.getInqTitle();
		this.inqDate = inq.getInqDate();
		this.inqWriter = inq.getInqWriter();
		this.inqCount = inq.getInqCount();
		this.inqContent = inq.getInqContent();
		this.inqFile = inq.getInqFile().getOriginalFilename();
		this.inqOriginalFile = inq.getInqFile().getOriginalFilename();
	}

//	##################################################################################
	// 게시글 수정시 : Map<String, Object> -> InqVO
	// 이 생성자는 Map<String, Object> 타입의 매개변수를 받아서 InqVO 객체의 필드를 초기화합니다.
	// 일부 값들은 적절한 형태로 변환됩니다.
	// 이 생성자를 사용하면 맵으로부터 데이터를 직접적으로 InqVO 객체로 변환할 수 있습니다.
	// MultipartFile > File 관련 인터페이스(?)
	public InqVO(Map<String, Object> map, MultipartFile inqFile) {
		
		log.info("InqVO 오버로딩 생성자 : Map to VO");
		
//		this.inqNum = Integer.parseInt(map.get("inqNum").toString());
    	this.inqNum = map.get("inqNum") == null ? 0 : Integer.parseInt(map.get("inqNum").toString());
		this.inqTitle = (String)map.get("inqTitle");
		this.inqDate = (Date)map.get("inqDate");
		this.inqWriter = (String)map.get("inqWriter");
		this.inqContent = (String)map.get("inqContent");
		
		log.info("map.get(\"inqOriginal\") : " + map.get("inqOriginal"));
		if (inqFile.isEmpty() == false) {
			this.inqOriginalFile = inqFile.getOriginalFilename();
			
			// 암호화 파일 부분 추가
			// 첨부 파일 유무 : 없으면 -> "", 있으면 -> 암호화
			this.inqFile = FileUploadUtil.encodeFilename(inqFile.getOriginalFilename());
		}
	}
	
//	##################################################################################


	public int getInqNum() {
		return inqNum;
	}

	public void setInqNum(int inqNum) {
		this.inqNum = inqNum;
	}

	public String getInqTitle() {
		return inqTitle;
	}

	public void setInqTitle(String inqTitle) {
		this.inqTitle = inqTitle;
	}

	public Date getInqDate() {
		return inqDate;
	}

	public void setInqDate(Date inqDate) {
		this.inqDate = inqDate;
	}

	public String getInqWriter() {
		return inqWriter;
	}

	public void setInqWriter(String inqWriter) {
		this.inqWriter = inqWriter;
	}

	public int getInqCount() {
		return inqCount;
	}

	public void setInqCount(int inqCount) {
		this.inqCount = inqCount;
	}

	public String getInqContent() {
		return inqContent;
	}

	public void setInqContent(String inqContent) {
		this.inqContent = inqContent;
	}

	public String getInqFile() {
		return inqFile;
	}

	public void setInqFile(String inqFile) {
		this.inqFile = inqFile;
	}

	public String getInqOriginalFile() {
		return inqOriginalFile;
	}

	public void setInqOriginalFile(String inqOriginalFile) {
		this.inqOriginalFile = inqOriginalFile;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InqVO [inqNum=").append(inqNum)
				.append(", inqTitle=").append(inqTitle).append(", inqDate=").append(inqDate)
				.append(", inqWriter=").append(inqWriter).append(", inqCount=").append(inqCount)
				.append(", inqContent=").append(inqContent).append(", inqFile=").append(inqFile)
				.append(", inqOriginalFile=").append(inqOriginalFile).append("]");
		return builder.toString();
	}

	// 게시글 수정시 기존 정보와 수정 정보 동일성 여부 점검시 활용
	
	@Override
	public int hashCode() {
		
		final int prime = 31;
		
		int result = 1;
		
		result = prime * result + ((inqContent == null) ? 0 : inqContent.hashCode());
		result = prime * result + ((inqFile == null) ? 0 : inqFile.hashCode());
		result = prime * result + inqNum;
		result = prime * result + ((inqOriginalFile == null) ? 0 : inqOriginalFile.hashCode());
		result = prime * result + ((inqTitle == null) ? 0 : inqTitle.hashCode());
		result = prime * result + ((inqWriter == null) ? 0 : inqWriter.hashCode());
		
		return result;
	}

	// 게시글 수정시 기존 정보와 수정 정보 동일성 여부 점검시 활용
	
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof InqVO)) {
			return false;
		}
		
		InqVO other = (InqVO) obj;
		
		if (inqContent == null) {
			if(other.inqContent != null) {
				return false;
			}
		} else if (!inqContent.equals(other.inqContent)) {
			return false;
		}
		if (inqFile == null) {
			if (other.inqFile != null) {
				return false;
			}
		} else if (!inqFile.equals(other.inqFile)) {
			return false;
		}
		if (inqNum != other.inqNum) {
			return false;
		}
		if (inqOriginalFile == null) {
			if (other.inqOriginalFile != null) {
				return false;
			}
		} else if (!inqOriginalFile.equals(other.inqOriginalFile)) {
			return false;
		} 
		if (inqTitle == null) {
			if (other.inqTitle != null) {
				return false;
			}
		} else if (!inqTitle.equals(other.inqTitle)) {
			return false;
		}
		if (inqWriter == null) {
			if (other.inqWriter != null) {
				return false;
			}
		} else if (!inqWriter.equals(other.inqWriter)) {
			return false;
		}
		return true;
	}
}



