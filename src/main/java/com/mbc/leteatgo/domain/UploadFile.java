package com.mbc.leteatgo.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="upload_file_tbl")
// 게시글 내용 중 삽입 이미지 파일 자바빈
public class UploadFile {
    
    @Id
    // @GeneratedValue
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    			    generator = "UPLOADFILE_SEQ_GENERATOR")
    @SequenceGenerator(
    	    name = "UPLOADFILE_SEQ_GENERATOR",
    	    sequenceName = "image_upload_file_seq",
    	    initialValue = 1,
    	    allocationSize = 1)
    @Column(name = "UPLOAD_FILE_ID")
    int id;

    @Column(name = "UPLOAD_FILE_FILENAME")
    String fileName;
    
    @Column(name="UPLOAD_FILE_SAVE_FILENAME")
    String saveFileName;
    
    @Column(name="UPLOAD_FILE_FILE_PATH")
    String filePath;
    
    @Column(name="UPLOAD_FILE_CONTENT_TYPE")
    String contentType;
    
    @Column(name="UPLOAD_FILE_FILE_SIZE")
    long fileSize;
    
    @Column(name="REGDATE")
    Date regDate;
    
}