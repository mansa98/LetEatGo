-- JPA의 테이블 자동 생성 기능을 사용하지 않고, 수동으로 테이블을 먼저 생성하는 방향으로 전개

-- 게시글 삽입 이미지 파일명 저장 테이블

-- 테이블 PK 시퀀스
CREATE SEQUENCE image_upload_file_seq
START WITH 1
INCREMENT BY 1
MAXVALUE 99999
NOCYCLE;

CREATE TABLE upload_file_tbl (
	upload_file_id number(10,0) primary key,
	upload_file_filename nvarchar2(300) not null,
	upload_file_save_filename nvarchar2(500) not null,
	upload_file_file_path nvarchar2(500) not null,
	upload_file_content_type nvarchar2(10),
	upload_file_file_size number(20,0) default 0,
	regdate date default sysdate
);

COMMENT ON COLUMN upload_file_tbl.upload_file_id IS '파일 번호';
COMMENT ON COLUMN upload_file_tbl.upload_file_filename IS '파일 이름';
COMMENT ON COLUMN upload_file_tbl.upload_file_save_filename IS '파일 이름(저장)';
COMMENT ON COLUMN upload_file_tbl.upload_file_file_path IS '파일 경로';
COMMENT ON COLUMN upload_file_tbl.upload_file_content_type IS '파일 자료 타입';
COMMENT ON COLUMN upload_file_tbl.upload_file_file_size IS'파일 크기';
COMMENT ON COLUMN upload_file_tbl.regdate IS'날짜';
