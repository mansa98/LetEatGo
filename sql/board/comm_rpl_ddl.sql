-- 시퀀스(커뮤니티 댓글)
CREATE SEQUENCE comm_rpl_tbl_seq
START WITH 1
INCREMENT BY 1
MAXVALUE 99999
NOCYCLE;

-- inq_rpl_tbl (커뮤니티 댓글)
CREATE TABLE comm_rpl_tbl (
comm_rpl_num NUMBER(6),
comm_board_num NUMBER(5),
comm_rpl_content NVARCHAR2(200),
comm_rpl_writer VARCHAR2(30 char)
);

ALTER TABLE comm_rpl_tbl
ADD CONSTRAINT crt_comm_rpl_num_pk PRIMARY KEY(comm_rpl_num);

ALTER TABLE comm_rpl_tbl
ADD CONSTRAINT crt_comm_rpl_num_fk FOREIGN KEY(comm_board_num)
REFERENCES comm_board_tbl(comm_board_num) ON DELETE CASCADE;

ALTER TABLE comm_rpl_tbl
MODIFY (comm_rpl_content CONSTRAINT crt_comm_rpl_content_nn NOT NULL);

ALTER TABLE comm_rpl_tbl
MODIFY (comm_rpl_writer CONSTRAINT crt_comm_rpl_writer_nn NOT NULL);

COMMENT ON COLUMN comm_rpl_tbl.comm_rpl_num IS '댓글 번호';
COMMENT ON COLUMN comm_rpl_tbl.comm_board_num IS '게시글 번호(comm_board_tbl 참조)';
COMMENT ON COLUMN comm_rpl_tbl.comm_rpl_content IS '댓글 본문';
COMMENT ON COLUMN comm_rpl_tbl.comm_rpl_writer IS '댓글 작성자';