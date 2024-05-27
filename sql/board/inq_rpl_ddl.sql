-- 시퀀스(문의사항 댓글)
CREATE SEQUENCE inq_rpl_tbl_seq
START WITH 1
INCREMENT BY 1
MAXVALUE 99999
NOCYCLE;

-- inq_rpl_tbl (문의사항 댓글)
CREATE TABLE inq_rpl_tbl (
inq_rpl_num NUMBER(6),
inq_board_num NUMBER(5),
inq_rpl_content NVARCHAR2(200),
inq_rpl_writer VARCHAR2(30 CHAR)
);

ALTER TABLE inq_rpl_tbl
ADD CONSTRAINT irt_inq_rpl_num_pk PRIMARY KEY(inq_rpl_num);

ALTER TABLE inq_rpl_tbl
ADD CONSTRAINT irt_inq_board_num_fk FOREIGN KEY(inq_board_num)
REFERENCES inq_board_tbl(inq_board_num) ON DELETE CASCADE;

ALTER TABLE inq_rpl_tbl
MODIFY (inq_rpl_content CONSTRAINT irt_inq_rpl_content_nn NOT NULL);

ALTER TABLE inq_rpl_tbl
MODIFY (inq_rpl_writer CONSTRAINT irt_inq_rpl_writer_nn NOT NULL);

COMMENT ON COLUMN inq_rpl_tbl.inq_rpl_num IS '댓글 번호';
COMMENT ON COLUMN inq_rpl_tbl.inq_board_num IS '게시글 번호(inq_board_tbl 참조)';
COMMENT ON COLUMN inq_rpl_tbl.inq_rpl_content IS '댓글 본문';
COMMENT ON COLUMN inq_rpl_tbl.inq_rpl_writer IS '댓글 작성자(관리자만 가능?)';