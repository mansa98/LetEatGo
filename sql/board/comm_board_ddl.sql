-- 시퀀스 (커뮤니티)
CREATE SEQUENCE comm_board_tbl_seq
START WITH 1
INCREMENT BY 1
MAXVALUE 99999
NOCYCLE;

-- comm_board_tbl (커뮤니티)
CREATE TABLE comm_board_tbl (
comm_board_num NUMBER(5) DEFAULT 0,
comm_board_category VARCHAR2(6 char) NOT NULL,
comm_board_title NVARCHAR2(60) NOT NULL,
comm_board_date DATE DEFAULT current_date NOT NULL,
comm_board_writer VARCHAR2(30 char) NOT NULL,
comm_board_count NUMBER DEFAULT 0,
comm_board_content LONG NOT NULL,
comm_board_file NVARCHAR2(300),
comm_board_original_file NVARCHAR2(200),
PRIMARY KEY(comm_board_num)
);

COMMENT ON COLUMN comm_board_tbl.comm_board_num IS '게시글 번호';
COMMENT ON COLUMN comm_board_tbl.comm_board_category IS '게시글 분류';
COMMENT ON COLUMN comm_board_tbl.comm_board_title IS '게시글 제목';
COMMENT ON COLUMN comm_board_tbl.comm_board_date IS '게시글 작성일';
COMMENT ON COLUMN comm_board_tbl.comm_board_writer IS '게시글 작성자';
COMMENT ON COLUMN comm_board_tbl.comm_board_count IS'게시글 조회수';
COMMENT ON COLUMN comm_board_tbl.comm_board_content IS'게시글 본문';
COMMENT ON COLUMN comm_board_tbl.comm_board_file IS'게시글 첨부파일(암호화)';
COMMENT ON COLUMN comm_board_tbl.comm_board_original_file IS'게시글 첨부파일(원본)';