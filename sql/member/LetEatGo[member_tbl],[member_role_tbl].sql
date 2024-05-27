-- ��� ���̺� �ۼ�
CREATE TABLE member_tbl(
    member_num NUMBER(5),
    member_id VARCHAR2(40 CHAR),
    member_pw VARCHAR2(100 CHAR),
    member_name VARCHAR2(30 CHAR),
    member_nick VARCHAR2(30 CHAR),
    member_gender CHAR,
    member_birthday DATE,
    member_email VARCHAR2(50 CHAR),
    member_mobile VARCHAR2(26 CHAR),
    member_zip CHAR(5),
    member_road_address NVARCHAR2(50),
    member_jibun_address NVARCHAR2(50),
    member_detail_address NVARCHAR2(50),
    member_join_date DATE DEFAULT current_date,
    member_enabled NUMBER(1) DEFAULT 0
);
-- ���̺� ���� ����
-- primary key
ALTER TABLE member_tbl
ADD CONSTRAINT mt_member_num_pk PRIMARY KEY(member_num);

-- unique
ALTER TABLE member_tbl
ADD CONSTRAINT mt_member_id_uq UNIQUE(member_id);

ALTER TABLE member_tbl
ADD CONSTRAINT mt_member_nick_uq UNIQUE(member_nick);

ALTER TABLE member_tbl
ADD CONSTRAINT mt_member_email_uq UNIQUE(member_email);

-- check
ALTER TABLE member_tbl
ADD CONSTRAINT mt_member_gender_ck CHECK (member_gender IN ('m', 'f'));

-- not null
ALTER TABLE member_tbl
MODIFY (member_id CONSTRAINT mt_membe_id_nn NOT NULL);

ALTER TABLE member_tbl
MODIFY (member_pw CONSTRAINT mt_membe_pw_nn NOT NULL);

ALTER TABLE member_tbl
MODIFY (member_name CONSTRAINT mt_membe_name_nn NOT NULL);

ALTER TABLE member_tbl
MODIFY (member_nick CONSTRAINT mt_membe_nick_nn NOT NULL);

ALTER TABLE member_tbl
MODIFY (member_mobile CONSTRAINT mt_membe_mobile_nn NOT NULL);

ALTER TABLE member_tbl
MODIFY (member_email CONSTRAINT mt_membe_email_nn NOT NULL);

-- �ڸ�Ʈ
COMMENT ON COLUMN member_tbl.member_id IS 'ȸ�� ���̵�';
COMMENT ON COLUMN member_tbl.member_pw IS 'ȸ�� ��й�ȣ';
COMMENT ON COLUMN member_tbl.member_name IS 'ȸ�� �Ǹ�';
COMMENT ON COLUMN member_tbl.member_nick IS 'ȸ�� �г���';
COMMENT ON COLUMN member_tbl.member_gender IS 'ȸ�� ����';
COMMENT ON COLUMN member_tbl.member_birthday IS 'ȸ�� �������';
COMMENT ON COLUMN member_tbl.member_mobile IS 'ȸ�� �ڵ��� ��ȣ';
COMMENT ON COLUMN member_tbl.member_email IS 'ȸ�� �̸��� �ּ�';
COMMENT ON COLUMN member_tbl.member_zip IS 'ȸ�� �����ȣ';
COMMENT ON COLUMN member_tbl.member_road_address IS 'ȸ�� ���θ� �ּ�';
COMMENT ON COLUMN member_tbl.member_jibun_address IS 'ȸ�� ���� �ּ�';
COMMENT ON COLUMN member_tbl.member_detail_address IS 'ȸ�� �� �ּ�';
COMMENT ON COLUMN member_tbl.member_join_date IS 'ȸ�� ��������';
COMMENT ON COLUMN member_tbl.member_enabled IS 'ȸ�� Ȱ��ȭ ����';

-- ��� ������
CREATE SEQUENCE member_tbl_seq
START WITH 1
INCREMENT BY 1
MAXVALUE 99999
NOCYCLE;

-- ��� ��� ���̺� �ۼ�
CREATE TABLE member_role_tbl(
member_role_num NUMBER(11),
member_id VARCHAR2(40),
member_role VARCHAR2(20),
-- �������� ����
 PRIMARY KEY (member_role_num),
 CONSTRAINT fk_member_id FOREIGN KEY (member_id) 
 REFERENCES member_tbl (member_id)
);

-- �ڸ�Ʈ
COMMENT ON COLUMN member_role_tbl.member_role_num IS 'ȸ����� ������';
COMMENT ON COLUMN member_role_tbl.member_id IS 'ȸ�� ���̵�(member_tbl�� member_id�� �����ϴ� �ܷ�Ű)';
COMMENT ON COLUMN member_role_tbl.member_role IS 'ȸ�� ���'; 

-- ��� ��� ������
CREATE SEQUENCE member_roles_seq
	start with 1
	increment by 1
	maxvalue 99999
    nocycle; 
    
    
