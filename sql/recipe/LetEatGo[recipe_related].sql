-- 레시피 테이블: 생성
CREATE TABLE recipe_tbl (
recipe_num NUMBER(5),
recipe_title NVARCHAR2(100),
recipe_og_writer NVARCHAR2(50),
recipe_image NVARCHAR2(200),
--추가, 수정--------------------------------------------------------------------
recipe_ingr_combined NVARCHAR2(1000),
--------------------------------------------------------------------------
--추가, 수정--------------------------------------------------------------------
recipe_ingr_main NVARCHAR2(1000),
--------------------------------------------------------------------------
--추가, 수정--------------------------------------------------------------------
recipe_ingr_seasoning NVARCHAR2(1000),
--------------------------------------------------------------------------
recipe_cookery CLOB,
--추가--------------------------------------------------------------------
recipe_cookery_images CLOB,
--------------------------------------------------------------------------
recipe_like NUMBER(10),
recipe_count NUMBER(10)
)

-- 레시피 테이블: 시퀀스 생성
CREATE SEQUENCE recipe_tbl_seq
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 9999
    NOCYCLE;
    
-- 칼럼 수정(value too large ㅅㅂ)    

ALTER TABLE recipe_tbl
MODIFY recipe_ingr_combined NVARCHAR2(1000);

ALTER TABLE recipe_tbl
MODIFY recipe_ingr_main NVARCHAR2(1000);

ALTER TABLE recipe_tbl
MODIFY recipe_ingr_seasoning NVARCHAR2(1000);

-- 레시피 테이블: 제약조건 생성
ALTER TABLE recipe_tbl
ADD CONSTRAINT rt_recipe_num_pk PRIMARY KEY(recipe_num);

ALTER TABLE recipe_tbl
ADD CONSTRAINT rt_recipe_title_unq UNIQUE(recipe_title);

ALTER TABLE recipe_tbl
MODIFY (recipe_title CONSTRAINT rt_recipe_title_nn NOT NULL);

ALTER TABLE recipe_tbl
MODIFY (recipe_og_writer CONSTRAINT rt_recipe_og_writer_nn NOT NULL);

ALTER TABLE recipe_tbl
MODIFY (recipe_image CONSTRAINT rt_recipe_image_nn NOT NULL);

ALTER TABLE recipe_tbl
MODIFY (recipe_ingr_combined CONSTRAINT rt_recipe_ingr_combined_nn NOT NULL);

ALTER TABLE recipe_tbl
MODIFY (recipe_ingr_main CONSTRAINT rt_recipe_ingr_main_nn NOT NULL);
--삭제--------------------------------------------------------------------
--ALTER TABLE recipe_tbl
--MODIFY (recipe_ingr_seasoning CONSTRAINT rt_recipe_ingr_seasoning_nn NOT NULL);
--------------------------------------------------------------------------
ALTER TABLE recipe_tbl
MODIFY (recipe_cookery CONSTRAINT rt_recipe_cookery_nn NOT NULL);

-- 레시피 테이블: 코멘트 생성
COMMENT ON COLUMN recipe_tbl.recipe_num IS '레시피 번호(프라이머리키, 시퀀스로 제어)';

COMMENT ON COLUMN recipe_tbl.recipe_title IS '레시피 제목(UNIQUE)';

COMMENT ON COLUMN recipe_tbl.recipe_og_writer IS '레시피 원작자';

COMMENT ON COLUMN recipe_tbl.recipe_image IS '레시피 이미지';

COMMENT ON COLUMN recipe_tbl.recipe_ingr_combined IS '레시피 재료(메인 + 양념 [데이터 분석용])';

COMMENT ON COLUMN recipe_tbl.recipe_ingr_main IS '레시피 재료(메인 [뷰 페이지 용])';

COMMENT ON COLUMN recipe_tbl.recipe_ingr_seasoning IS '레시피 재료(양념 [뷰 페이지 용])';

COMMENT ON COLUMN recipe_tbl.recipe_cookery IS '레시피 조리법';

COMMENT ON COLUMN recipe_tbl.recipe_cookery_images IS '레시피 조리법 이미지';

COMMENT ON COLUMN recipe_tbl.recipe_like IS '레시피 좋아요(랭킹)';

COMMENT ON COLUMN recipe_tbl.recipe_count IS '레시피 조회수';




------------------------------------------------------------------------------------------------------------------------------------------------------


-- 재료분류 테이블(대분류): 생성
CREATE TABLE ingr_main_tbl (
ingr_main_num NUMBER(5),
ingr_main_name NVARCHAR2(20)
)


-- 재료분류 테이블(대분류): 제약조건 생성
ALTER TABLE ingr_main_tbl
ADD CONSTRAINT imt_ingr_main_num_pk PRIMARY KEY(ingr_main_num);

ALTER TABLE ingr_main_tbl
MODIFY (ingr_main_name CONSTRAINT imt_ingr_main_name_nn NOT NULL);

-- 재료분류 테이블(대분류): 코멘트 생성
COMMENT ON COLUMN ingr_main_tbl.ingr_main_num IS '재료 번호(대분류, 프라이머리키, 시퀀스로 제어)';

COMMENT ON COLUMN ingr_main_tbl.ingr_main_name IS '재료 이름(대분류)';

-- 재료분류 테이블(대분류): 시퀀스 생성

CREATE SEQUENCE ingr_main_tbl_seq
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 999
    NOCYCLE;

------------------------------------------------------------------------------------------------------------------------------------------------------

-- 재료분류 테이블(소분류): 생성
CREATE TABLE ingr_sub_tbl (
ingr_sub_num NUMBER(5),
ingr_sub_name NVARCHAR2(30),
ingr_main_num NUMBER(5)
)

-- 재료분류 테이블(소분류): 시퀀스 생성
CREATE SEQUENCE ingr_sub_tbl_seq
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 999
    NOCYCLE;

-- 재료분류 테이블(소분류): 제약조건 생성
ALTER TABLE ingr_sub_tbl
ADD CONSTRAINT ist_ingr_sub_num_pk PRIMARY KEY(ingr_sub_num);

ALTER TABLE ingr_sub_tbl
MODIFY (ingr_sub_name CONSTRAINT ist_ingr_sub_name_nn NOT NULL);

ALTER TABLE ingr_sub_tbl
ADD CONSTRAINT ist_ingr_main_num_fk FOREIGN KEY(ingr_main_num) REFERENCES ingr_main_tbl(ingr_main_num);


-- 재료분류 테이블(소분류): 코멘트 생성
COMMENT ON COLUMN ingr_sub_tbl.ingr_sub_num IS '소재료 번호';

COMMENT ON COLUMN ingr_sub_tbl.ingr_sub_name IS '소재료 이름';

COMMENT ON COLUMN ingr_sub_tbl.ingr_main_num IS '재료 번호(ingr_main_tbl참조)';

------------------------------------------------------------------------------------------------------------------------------------------------------

-- 랭킹(즐겨찾기) 테이블: 생성
CREATE TABLE fav_tbl (
member_id NVARCHAR2(15),
recipe_num NUMBER(5),
fav_num NUMBER(5)
)

-- 랭킹(즐겨찾기) 테이블: 시퀀스 생성
CREATE SEQUENCE fav_tbl_seq
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 9999
    NOCYCLE;


-- 외래키가 될 칼럼의 데이터 타입과 참조하는 테이블의 칼럼의 데이터 타입이 일치해야함!
-- ALTER TABLE fav_tbl
-- MODIFY (member_id VARCHAR2(30));

-- 랭킹(즐겨찾기) 테이블: 제약조건 생성
ALTER TABLE fav_tbl
ADD CONSTRAINT ft_member_id_fk FOREIGN KEY(member_id) REFERENCES member_tbl(id);

ALTER TABLE fav_tbl
ADD CONSTRAINT ft_recipe_num_fk FOREIGN KEY(recipe_num) REFERENCES recipe_tbl(recipe_num);

-- 랭킹(즐겨찾기) 테이블: 코멘트 생성
COMMENT ON COLUMN fav_tbl.member_id IS '회원 아이디(member_tbl의 id 참조)';

COMMENT ON COLUMN fav_tbl.recipe_num IS '레시피 번호(recipe_tbl의 recipe_num 참조)';

COMMENT ON COLUMN fav_tbl.fav_num IS '즐겨찾기 번호(시퀀스로 제어)';

