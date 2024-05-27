-- 시퀀스 (공지사항)
CREATE SEQUENCE ntc_board_tbl_seq
START WITH 1
INCREMENT BY 1
MAXVALUE 99999
NOCYCLE;

-- ntc_board_tbl (공지사항)
CREATE TABLE ntc_board_tbl (
ntc_board_num NUMBER(5) DEFAULT 0,
ntc_board_title VARCHAR2(100 char) NOT NULL,
ntc_board_date DATE DEFAULT current_date NOT NULL,
ntc_board_writer VARCHAR2(30 CHAR) NOT NULL,
ntc_board_count NUMBER(5) DEFAULT 0,
ntc_board_content LONG NOT NULL,
ntc_board_file NVARCHAR2(300),
ntc_board_original_file NVARCHAR2(200),
PRIMARY KEY(ntc_board_num)
);

COMMENT ON COLUMN ntc_board_tbl.ntc_board_num IS '공지사항 번호';
COMMENT ON COLUMN ntc_board_tbl.ntc_board_title IS '공지사항 제목';
COMMENT ON COLUMN ntc_board_tbl.ntc_board_date IS '공지사항 작성일';
COMMENT ON COLUMN Ntc_board_tbl.ntc_board_writer IS '공지사항 작성자';
COMMENT ON COLUMN ntc_board_tbl.ntc_board_count IS '공지사항 조회수';
COMMENT ON COLUMN ntc_board_tbl.ntc_board_content IS '공지사항 본문';
COMMENT ON COLUMN ntc_board_tbl.ntc_board_file IS '공지사항 첨부파일(암호화)';
COMMENT ON COLUMN ntc_board_tbl.ntc_board_original_file IS '공지사항 첨부파일(원본)';