package com.mbc.leteatgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mbc.leteatgo.domain.IngrMainVO;

public interface IngrMainRepository extends JpaRepository<IngrMainVO, Integer> {
	
	List<IngrMainVO> findAll();
	
	@Query(value="select s.ingr_sub_name "
			   + "from ingr_main_tbl m join ingr_sub_tbl s  "
			   + "on m.ingr_main_num = s.ingr_main_num "
			   + "where m.ingr_main_name = :ingrMainName", nativeQuery=true)
	String findByIngrMainName(@Param("ingrMainName") String ingrMainName);
}
