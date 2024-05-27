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
@Table(name="comm_board_tbl")
// Entity Bean (게시판 테이블 대응 자바빈)
public class BoardVO implements Serializable { // 10.25 (session으로 변환할 경우 에러 방지)
	
	private static final long serialVersionUID = 1L;
	
	/** 게시글 번호 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
		    generator = "COMM_BOARD_TBL_SEQ_GENERATOR")
			@SequenceGenerator(
			name = "COMM_BOARD_TBL_SEQ_GENERATOR",
			sequenceName = "comm_board_tbl_seq",
			initialValue = 1,
			allocationSize = 1)
	@Column(name = "comm_board_num") 
	private int boardNum;
	
	/** 게시글 분류 */
	@Column(name = "comm_board_category") 
	private String boardCategory; 
	
	/** 게시글 제목 */
	@Column(name = "comm_board_title") 
	private String boardTitle; 
	
	/** 게시글 작성일자 */
	@Column(name = "comm_board_date")
	@CreationTimestamp // 작성 날짜 (기본값) 생성
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") // Json 변환시 "년월일 및 시분초"까지 출력 (수정해야...)
	private Date boardDate; 
	
	/** 게시글 작성자 */
	@Column(name = "comm_board_writer") 
	private String boardWriter; 
	
	/** 게시글 조회수 */
	@Column(name = "comm_board_count") 
	private int boardCount = 0; 
	
	/** 게시글 내용 */
	@Column(name = "comm_board_content") 
	private String boardContent; 
	
	/** 게시글 첨부파일 (인코딩 된 파일명) */
	@Column(name = "comm_board_file") 
	private String boardFile; 
	
	/** 게시글 원래 파일명 */
	@Column(name = "comm_board_original_file") 
	private String boardOriginalFile; 
	
	public BoardVO() {}
	
	// BoardDTO -> BoardVO
	public BoardVO(BoardDTO board) {
		
		this.boardNum = board.getBoardNum();
		this.boardCategory = board.getBoardCategory();
		this.boardTitle = board.getBoardTitle();
		this.boardDate = board.getBoardDate();
		this.boardWriter = board.getBoardWriter();
		this.boardCount = board.getBoardCount();
		this.boardContent = board.getBoardContent();
		this.boardFile = board.getBoardFile().getOriginalFilename();
		this.boardOriginalFile = board.getBoardFile().getOriginalFilename();
	}

//	##################################################################################
	// 게시글 수정시 : Map<String, Object> -> BoardVO
	// 이 생성자는 Map<String, Object> 타입의 매개변수를 받아서 BoardVO 객체의 필드를 초기화합니다.
	// 일부 값들은 적절한 형태로 변환됩니다.
	// 이 생성자를 사용하면 맵으로부터 데이터를 직접적으로 BoardVO 객체로 변환할 수 있습니다.
	// MultipartFile > File 관련 인터페이스(?)
	public BoardVO(Map<String, Object> map, MultipartFile boardFile) {
		
		log.info("BoardVO 오버로딩 생성자 : Map to VO");
		
//		this.boardNum = Integer.parseInt(map.get("boardNum").toString());
    	this.boardNum = map.get("boardNum") == null ? 0 : Integer.parseInt(map.get("boardNum").toString());
		this.boardCategory = (String)map.get("boardCategory");
		this.boardTitle = (String)map.get("boardTitle");
		this.boardDate = (Date)map.get("boardDate");
		this.boardWriter = (String)map.get("boardWriter");
		this.boardContent = (String)map.get("boardContent");
		
		log.info("map.get(\"boardOriginal\") : " + map.get("boardOriginal"));
		if (boardFile.isEmpty() == false) {
			this.boardOriginalFile = boardFile.getOriginalFilename();
			
			// 암호화 파일 부분 추가
			// 첨부 파일 유무 : 없으면 -> "", 있으면 -> 암호화
			this.boardFile = FileUploadUtil.encodeFilename(boardFile.getOriginalFilename());
		}
	}
	
//	##################################################################################


	public int getBoardNum() {
		return boardNum;
	}

	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}

	public String getBoardCategory() {
		return boardCategory;
	}

	public void setBoardCategory(String boardCategory) {
		this.boardCategory = boardCategory;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	public Date getBoardDate() {
		return boardDate;
	}

	public void setBoardDate(Date boardDate) {
		this.boardDate = boardDate;
	}

	public String getBoardWriter() {
		return boardWriter;
	}

	public void setBoardWriter(String boardWriter) {
		this.boardWriter = boardWriter;
	}

	public int getBoardCount() {
		return boardCount;
	}

	public void setBoardCount(int boardCount) {
		this.boardCount = boardCount;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}

	public String getBoardFile() {
		return boardFile;
	}

	public void setBoardFile(String boardFile) {
		this.boardFile = boardFile;
	}

	public String getBoardOriginalFile() {
		return boardOriginalFile;
	}

	public void setBoardOriginalFile(String boardOriginalFile) {
		this.boardOriginalFile = boardOriginalFile;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BoardVO [boardNum=").append(boardNum).append(", boardCategory=").append(boardCategory)
				.append(", boardTitle=").append(boardTitle).append(", boardDate=").append(boardDate)
				.append(", boardWriter=").append(boardWriter).append(", boardCount=").append(boardCount)
				.append(", boardContent=").append(boardContent).append(", boardFile=").append(boardFile)
				.append(", boardOriginalFile=").append(boardOriginalFile).append("]");
		return builder.toString();
	}

	// 게시글 수정시 기존 정보와 수정 정보 동일성 여부 점검시 활용
	
	@Override
	public int hashCode() {
		
		final int prime = 31;
		
		int result = 1;
		
		result = prime * result + ((boardContent == null) ? 0 : boardContent.hashCode());
		result = prime * result + ((boardFile == null) ? 0 : boardFile.hashCode());
		result = prime * result + boardNum;
		result = prime * result + ((boardOriginalFile == null) ? 0 : boardOriginalFile.hashCode());
		result = prime * result + ((boardTitle == null) ? 0 : boardTitle.hashCode());
		result = prime * result + ((boardWriter == null) ? 0 : boardWriter.hashCode());
		
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
		if (!(obj instanceof BoardVO)) {
			return false;
		}
		
		BoardVO other = (BoardVO) obj;
		
		if (boardContent == null) {
			if(other.boardContent != null) {
				return false;
			}
		} else if (!boardContent.equals(other.boardContent)) {
			return false;
		}
		if (boardFile == null) {
			if (other.boardFile != null) {
				return false;
			}
		} else if (!boardFile.equals(other.boardFile)) {
			return false;
		}
		if (boardNum != other.boardNum) {
			return false;
		}
		if (boardOriginalFile == null) {
			if (other.boardOriginalFile != null) {
				return false;
			}
		} else if (!boardOriginalFile.equals(other.boardOriginalFile)) {
			return false;
		} 
		if (boardTitle == null) {
			if (other.boardTitle != null) {
				return false;
			}
		} else if (!boardTitle.equals(other.boardTitle)) {
			return false;
		}
		if (boardWriter == null) {
			if (other.boardWriter != null) {
				return false;
			}
		} else if (!boardWriter.equals(other.boardWriter)) {
			return false;
		}
		return true;
	}
}



