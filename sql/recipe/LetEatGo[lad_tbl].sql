
CREATE TABLE lad_tbl(
lad_num NUMBER(5) PRIMARY KEY,
member_id VARCHAR2(40 CHAR),
lad_like NVARCHAR2(500),
lad_dislike NVARCHAR2(500)
)

CREATE SEQUENCE lad_tbl_seq
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 99999
    NOCYCLE;

ALTER TABLE lad_tbl
ADD CONSTRAINT lt_member_id_fk FOREIGN KEY(member_id)
REFERENCES member_tbl(member_id);

ALTER TABLE lad_tbl
ADD CONSTRAINT lt_member_id_unq UNIQUE(member_id);

ALTER TABLE lad_tbl
MODIFY (member_id CONSTRAINT lt_member_id_nn NOT NULL);

