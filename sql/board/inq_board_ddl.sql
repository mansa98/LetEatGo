-- 시퀀스 (문의사항)
CREATE SEQUENCE inq_board_tbl_seq
START WITH 1
INCREMENT BY 1
MAXVALUE 99999
NOCYCLE;

-- inq_board_tbl (문의사항)
CREATE TABLE inq_board_tbl (
inq_board_num NUMBER(5) DEFAULT 0,
inq_board_title VARCHAR2(100 char) NOT NULL,
inq_board_date DATE DEFAULT current_date NOT NULL,
inq_board_writer VARCHAR2(30 CHAR) NOT NULL,
inq_board_count NUMBER(5) DEFAULT 0,
inq_board_content LONG NOT NULL,
inq_board_file NVARCHAR2(300),
inq_board_original_file NVARCHAR2(200),
PRIMARY KEY(inq_board_num)
);

COMMENT ON COLUMN inq_board_tbl.inq_board_num IS '문의사항 번호';
COMMENT ON COLUMN inq_board_tbl.inq_board_title IS '문의사항 제목';
COMMENT ON COLUMN inq_board_tbl.inq_board_date IS '문의사항 작성일';
COMMENT ON COLUMN inq_board_tbl.inq_board_writer IS '문의사항 작성자';
COMMENT ON COLUMN inq_board_tbl.inq_board_count IS '문의사항 조회수';
COMMENT ON COLUMN inq_board_tbl.inq_board_content IS '문의사항 본문';
COMMENT ON COLUMN inq_board_tbl.inq_board_file IS '문의사항 첨부파일(암호화)';
COMMENT ON COLUMN inq_board_tbl.inq_board_original_file IS '문의사항 첨부파일(원본)';