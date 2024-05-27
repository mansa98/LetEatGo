DECLARE
    
    temp_random_gender NUMBER(1); 
    v_gender CHAR(1);
    
BEGIN
 
    FOR i IN 1..100 LOOP
    
        -- 성별 생성
        temp_random_gender := round(DBMS_RANDOM.VALUE(1, 2), 0);        
        SELECT DECODE(temp_random_gender, 1, 'm', 2, 'f') INTO v_gender FROM dual; 
        
    
        INSERT INTO member_tbl VALUES
        ((4+i),
         'Ezen' || (1000+i),
         '$2a$10$BhrmdM6kjgG17XmPezAdqu4QZwhuSzpTdUirRvwOips4CA3mlfvD2',
         '김' || (1000+i),
         'Ezen' || (1000+i),
         v_gender,
         '1990-11-12',
         'ezen' || i || '@abcd.com',
         '010-1234-' || (1000+i),
         '08290',  
         '서울 관악구 남부순환로 1633',         
         '서울특별시 관악구 신림동 1433-120번지',
         '이젠컴퓨터아카데미 별관 801호',
         SYSDATE,
         1);

     END LOOP;
 
    COMMIT;    
END;
/

-- ROLE 정보 생성
DECLARE
BEGIN
 
    FOR i IN 1..100 LOOP
    
        INSERT INTO member_role_tbl VALUES
        (
         member_roles_seq.nextval + 4,
         'ezen' || (1000+i),        
		 'ROLE_USER'
         );

     END LOOP;
 
    COMMIT;    
END;
/

