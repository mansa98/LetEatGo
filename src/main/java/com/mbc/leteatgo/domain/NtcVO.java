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

@Slf4j
@Entity
@Table(name="ntc_board_tbl")
public class NtcVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/* 공지사항 번호 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
			generator = "NTC_BOARD_TBL_SEQ_GENERATOR")
			@SequenceGenerator(
			name = "NTC_BOARD_TBL_SEQ_GENERATOR",
			sequenceName = "ntc_board_tbl_seq",
			initialValue = 1,
			allocationSize = 1)
	@Column(name = "ntc_board_num")
	private int ntcNum;
	
	/* 공지사항 제목 */
	@Column(name = "ntc_board_title")
	private String ntcTitle;
	
	/* 공지사항 작성일자 */
	@Column(name = "ntc_board_date")
	@CreationTimestamp // 작성 날짜 (기본값) 생성
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") // Json 변환시 "년월일 및 시분초"까지 출력
	private Date ntcDate;
	
	/* 공지사항 작성자 */
	@Column(name = "ntc_board_writer")
	private String ntcWriter;
	
	/* 공지사항 조회수 */
	@Column(name = "ntc_board_count")
	private int ntcCount;
	
	/* 공지사항 내용 */
	@Column(name = "ntc_board_content")
	private String ntcContent;
	
	/* 공지사항 첨부파일 */
	@Column(name = "ntc_board_file")
	private String ntcFile;
	
	/* 공지사항 원래 파일명 */
	@Column(name = "ntc_board_original_file")
	private String ntcOriginalFile;
	
	public NtcVO() {}
	
	public NtcVO(NtcDTO ntc) {
		
		this.ntcNum = ntc.getNtcNum();
		this.ntcTitle = ntc.getNtcTitle();
		this.ntcDate = ntc.getNtcDate();
		this.ntcWriter = ntc.getNtcWriter();
		this.ntcCount = ntc.getNtcCount();
		this.ntcContent = ntc.getNtcContent();
		this.ntcFile = ntc.getNtcFile().getOriginalFilename();
		this.ntcOriginalFile = ntc.getNtcFile().getOriginalFilename();
	}
		
		public NtcVO(Map<String, Object> map, MultipartFile ntcFile) {
			
			log.info("BoardVO 오버로딩 생성자 : Map to VO");
			
//			this.boardNum = Integer.parseInt(map.get("boardNum").toString());
	    	this.ntcNum = map.get("ntcNum") == null ? 0 : Integer.parseInt(map.get("ntcNum").toString());
			this.ntcTitle = (String)map.get("ntcTitle");
			this.ntcDate = (Date)map.get("ntcDate");
			this.ntcWriter = (String)map.get("ntcWriter");
			this.ntcContent = (String)map.get("ntcContent");
			
			log.info("map.get(\"ntcOriginal\") : " + map.get("ntcOriginal"));
			if (ntcFile.isEmpty() == false) {
				this.ntcOriginalFile = ntcFile.getOriginalFilename();
				
				// 암호화 파일 부분 추가
				// 첨부 파일 유무 : 없으면 -> "", 있으면 -> 암호화
				this.ntcFile = FileUploadUtil.encodeFilename(ntcFile.getOriginalFilename());
			}
		}
		
		public int getNtcNum() {
			return ntcNum;
		}

		public void setNtcNum(int ntcNum) {
			this.ntcNum = ntcNum;
		}

		public String getNtcTitle() {
			return ntcTitle;
		}

		public void setNtcTitle(String ntcTitle) {
			this.ntcTitle = ntcTitle;
		}

		public Date getNtcDate() {
			return ntcDate;
		}

		public void setNtcDate(Date ntcDate) {
			this.ntcDate = ntcDate;
		}

		public String getNtcWriter() {
			return ntcWriter;
		}

		public void setNtcWriter(String ntcWriter) {
			this.ntcWriter = ntcWriter;
		}

		public int getNtcCount() {
			return ntcCount;
		}

		public void setNtcCount(int ntcCount) {
			this.ntcCount = ntcCount;
		}

		public String getNtcContent() {
			return ntcContent;
		}

		public void setNtcContent(String ntcContent) {
			this.ntcContent = ntcContent;
		}

		public String getNtcFile() {
			return ntcFile;
		}

		public void setNtcFile(String ntcFile) {
			this.ntcFile = ntcFile;
		}

		public String getNtcOriginalFile() {
			return ntcOriginalFile;
		}

		public void setNtcOriginalFile(String ntcOriginalFile) {
			this.ntcOriginalFile = ntcOriginalFile;
		}
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("NtcVO [ntcNum=").append(ntcNum).append(", ntcTitle=").append(ntcTitle)
					.append(", ntcDate=").append(ntcDate).append(", ntcWriter=").append(ntcWriter)
					.append(", ntcCount=").append(ntcCount)
					.append(", ntcContent=").append(ntcContent).append(", ntcFile=").append(ntcFile)
					.append(", ntcOriginalFile=").append(ntcOriginalFile).append("]");
			return builder.toString();
		}
		
		@Override
		public int hashCode() {
			
			final int prime = 31;
			
			int result = 1;
			
			result = prime * result + ((ntcContent == null) ? 0 : ntcContent.hashCode());
			result = prime * result + ((ntcFile == null) ? 0 : ntcFile.hashCode());
			result = prime * result + ntcNum;
			result = prime * result + ((ntcOriginalFile == null) ? 0 : ntcOriginalFile.hashCode());
			result = prime * result + ((ntcTitle == null) ? 0 : ntcTitle.hashCode());
			result = prime * result + ((ntcWriter == null) ? 0 : ntcWriter.hashCode());
			
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
			if (!(obj instanceof NtcVO)) {
				return false;
			}
			
			NtcVO other = (NtcVO) obj;
			
			if (ntcContent == null) {
				if(other.ntcContent != null) {
					return false;
				}
			} else if (!ntcContent.equals(other.ntcContent)) {
				return false;
			}
			if (ntcFile == null) {
				if (other.ntcFile != null) {
					return false;
				}
			} else if (!ntcFile.equals(other.ntcFile)) {
				return false;
			}
			if (ntcNum != other.ntcNum) {
				return false;
			}
			if (ntcOriginalFile == null) {
				if (other.ntcOriginalFile != null) {
					return false;
				}
			} else if (!ntcOriginalFile.equals(other.ntcOriginalFile)) {
				return false;
			} 
			if (ntcTitle == null) {
				if (other.ntcTitle != null) {
					return false;
				}
			} else if (!ntcTitle.equals(other.ntcTitle)) {
				return false;
			}
			if (ntcWriter == null) {
				if (other.ntcWriter != null) {
					return false;
				}
			} else if (!ntcWriter.equals(other.ntcWriter)) {
				return false;
			}
			return true;
		}

		
	}