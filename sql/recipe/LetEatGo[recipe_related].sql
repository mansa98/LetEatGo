-- ������ ���̺�: ����
CREATE TABLE recipe_tbl (
recipe_num NUMBER(5),
recipe_title NVARCHAR2(100),
recipe_og_writer NVARCHAR2(50),
recipe_image NVARCHAR2(200),
--�߰�, ����--------------------------------------------------------------------
recipe_ingr_combined NVARCHAR2(1000),
--------------------------------------------------------------------------
--�߰�, ����--------------------------------------------------------------------
recipe_ingr_main NVARCHAR2(1000),
--------------------------------------------------------------------------
--�߰�, ����--------------------------------------------------------------------
recipe_ingr_seasoning NVARCHAR2(1000),
--------------------------------------------------------------------------
recipe_cookery CLOB,
--�߰�--------------------------------------------------------------------
recipe_cookery_images CLOB,
--------------------------------------------------------------------------
recipe_like NUMBER(10),
recipe_count NUMBER(10)
)

-- ������ ���̺�: ������ ����
CREATE SEQUENCE recipe_tbl_seq
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 9999
    NOCYCLE;
    
-- Į�� ����(value too large ����)    

ALTER TABLE recipe_tbl
MODIFY recipe_ingr_combined NVARCHAR2(1000);

ALTER TABLE recipe_tbl
MODIFY recipe_ingr_main NVARCHAR2(1000);

ALTER TABLE recipe_tbl
MODIFY recipe_ingr_seasoning NVARCHAR2(1000);

-- ������ ���̺�: �������� ����
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
--����--------------------------------------------------------------------
--ALTER TABLE recipe_tbl
--MODIFY (recipe_ingr_seasoning CONSTRAINT rt_recipe_ingr_seasoning_nn NOT NULL);
--------------------------------------------------------------------------
ALTER TABLE recipe_tbl
MODIFY (recipe_cookery CONSTRAINT rt_recipe_cookery_nn NOT NULL);

-- ������ ���̺�: �ڸ�Ʈ ����
COMMENT ON COLUMN recipe_tbl.recipe_num IS '������ ��ȣ(�����̸Ӹ�Ű, �������� ����)';

COMMENT ON COLUMN recipe_tbl.recipe_title IS '������ ����(UNIQUE)';

COMMENT ON COLUMN recipe_tbl.recipe_og_writer IS '������ ������';

COMMENT ON COLUMN recipe_tbl.recipe_image IS '������ �̹���';

COMMENT ON COLUMN recipe_tbl.recipe_ingr_combined IS '������ ���(���� + ��� [������ �м���])';

COMMENT ON COLUMN recipe_tbl.recipe_ingr_main IS '������ ���(���� [�� ������ ��])';

COMMENT ON COLUMN recipe_tbl.recipe_ingr_seasoning IS '������ ���(��� [�� ������ ��])';

COMMENT ON COLUMN recipe_tbl.recipe_cookery IS '������ ������';

COMMENT ON COLUMN recipe_tbl.recipe_cookery_images IS '������ ������ �̹���';

COMMENT ON COLUMN recipe_tbl.recipe_like IS '������ ���ƿ�(��ŷ)';

COMMENT ON COLUMN recipe_tbl.recipe_count IS '������ ��ȸ��';




------------------------------------------------------------------------------------------------------------------------------------------------------


-- ���з� ���̺�(��з�): ����
CREATE TABLE ingr_main_tbl (
ingr_main_num NUMBER(5),
ingr_main_name NVARCHAR2(20)
)


-- ���з� ���̺�(��з�): �������� ����
ALTER TABLE ingr_main_tbl
ADD CONSTRAINT imt_ingr_main_num_pk PRIMARY KEY(ingr_main_num);

ALTER TABLE ingr_main_tbl
MODIFY (ingr_main_name CONSTRAINT imt_ingr_main_name_nn NOT NULL);

-- ���з� ���̺�(��з�): �ڸ�Ʈ ����
COMMENT ON COLUMN ingr_main_tbl.ingr_main_num IS '��� ��ȣ(��з�, �����̸Ӹ�Ű, �������� ����)';

COMMENT ON COLUMN ingr_main_tbl.ingr_main_name IS '��� �̸�(��з�)';

-- ���з� ���̺�(��з�): ������ ����

CREATE SEQUENCE ingr_main_tbl_seq
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 999
    NOCYCLE;

------------------------------------------------------------------------------------------------------------------------------------------------------

-- ���з� ���̺�(�Һз�): ����
CREATE TABLE ingr_sub_tbl (
ingr_sub_num NUMBER(5),
ingr_sub_name NVARCHAR2(30),
ingr_main_num NUMBER(5)
)

-- ���з� ���̺�(�Һз�): ������ ����
CREATE SEQUENCE ingr_sub_tbl_seq
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 999
    NOCYCLE;

-- ���з� ���̺�(�Һз�): �������� ����
ALTER TABLE ingr_sub_tbl
ADD CONSTRAINT ist_ingr_sub_num_pk PRIMARY KEY(ingr_sub_num);

ALTER TABLE ingr_sub_tbl
MODIFY (ingr_sub_name CONSTRAINT ist_ingr_sub_name_nn NOT NULL);

ALTER TABLE ingr_sub_tbl
ADD CONSTRAINT ist_ingr_main_num_fk FOREIGN KEY(ingr_main_num) REFERENCES ingr_main_tbl(ingr_main_num);


-- ���з� ���̺�(�Һз�): �ڸ�Ʈ ����
COMMENT ON COLUMN ingr_sub_tbl.ingr_sub_num IS '����� ��ȣ';

COMMENT ON COLUMN ingr_sub_tbl.ingr_sub_name IS '����� �̸�';

COMMENT ON COLUMN ingr_sub_tbl.ingr_main_num IS '��� ��ȣ(ingr_main_tbl����)';

------------------------------------------------------------------------------------------------------------------------------------------------------

-- ��ŷ(���ã��) ���̺�: ����
CREATE TABLE fav_tbl (
member_id NVARCHAR2(15),
recipe_num NUMBER(5),
fav_num NUMBER(5)
)

-- ��ŷ(���ã��) ���̺�: ������ ����
CREATE SEQUENCE fav_tbl_seq
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 9999
    NOCYCLE;


-- �ܷ�Ű�� �� Į���� ������ Ÿ�԰� �����ϴ� ���̺��� Į���� ������ Ÿ���� ��ġ�ؾ���!
-- ALTER TABLE fav_tbl
-- MODIFY (member_id VARCHAR2(30));

-- ��ŷ(���ã��) ���̺�: �������� ����
ALTER TABLE fav_tbl
ADD CONSTRAINT ft_member_id_fk FOREIGN KEY(member_id) REFERENCES member_tbl(id);

ALTER TABLE fav_tbl
ADD CONSTRAINT ft_recipe_num_fk FOREIGN KEY(recipe_num) REFERENCES recipe_tbl(recipe_num);

-- ��ŷ(���ã��) ���̺�: �ڸ�Ʈ ����
COMMENT ON COLUMN fav_tbl.member_id IS 'ȸ�� ���̵�(member_tbl�� id ����)';

COMMENT ON COLUMN fav_tbl.recipe_num IS '������ ��ȣ(recipe_tbl�� recipe_num ����)';

COMMENT ON COLUMN fav_tbl.fav_num IS '���ã�� ��ȣ(�������� ����)';

