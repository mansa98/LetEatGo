-- 멤버 테이블 작성
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
-- 테이블 제약 조건
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

-- 코멘트
COMMENT ON COLUMN member_tbl.member_id IS '회원 아이디';
COMMENT ON COLUMN member_tbl.member_pw IS '회원 비밀번호';
COMMENT ON COLUMN member_tbl.member_name IS '회원 실명';
COMMENT ON COLUMN member_tbl.member_nick IS '회원 닉네임';
COMMENT ON COLUMN member_tbl.member_gender IS '회원 성별';
COMMENT ON COLUMN member_tbl.member_birthday IS '회원 생년월일';
COMMENT ON COLUMN member_tbl.member_mobile IS '회원 핸드폰 번호';
COMMENT ON COLUMN member_tbl.member_email IS '회원 이메일 주소';
COMMENT ON COLUMN member_tbl.member_zip IS '회원 우편번호';
COMMENT ON COLUMN member_tbl.member_road_address IS '회원 도로명 주소';
COMMENT ON COLUMN member_tbl.member_jibun_address IS '회원 지번 주소';
COMMENT ON COLUMN member_tbl.member_detail_address IS '회원 상세 주소';
COMMENT ON COLUMN member_tbl.member_join_date IS '회원 가입일자';
COMMENT ON COLUMN member_tbl.member_enabled IS '회원 활성화 여부';

-- 멤버 시퀀스
CREATE SEQUENCE member_tbl_seq
START WITH 1
INCREMENT BY 1
MAXVALUE 99999
NOCYCLE;

-- 멤버 등급 테이블 작성
CREATE TABLE member_role_tbl(
member_role_num NUMBER(11),
member_id VARCHAR2(40),
member_role VARCHAR2(20),
-- 제약조건 생성
 PRIMARY KEY (member_role_num),
 CONSTRAINT fk_member_id FOREIGN KEY (member_id) 
 REFERENCES member_tbl (member_id)
);

-- 코멘트
COMMENT ON COLUMN member_role_tbl.member_role_num IS '회원등급 시퀀스';
COMMENT ON COLUMN member_role_tbl.member_id IS '회원 아이디(member_tbl의 member_id를 참조하는 외래키)';
COMMENT ON COLUMN member_role_tbl.member_role IS '회원 등급'; 

-- 멤버 등급 시퀀스
CREATE SEQUENCE member_roles_seq
	start with 1
	increment by 1
	maxvalue 99999
    nocycle; 
    
    
