package com.mbc.leteatgo.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.mbc.leteatgo.domain.MemberDTO;
import com.mbc.leteatgo.domain.Role;

import lombok.extern.slf4j.Slf4j;

@Primary
@Repository
@Slf4j
public class MemberDAO {
	
	@Autowired
	SqlSession sqlSession;
	
	/** Member.xml의 name : mapper.Member 
	 * <mapper namespace="mapper.Member"></mapper> 요거 */
	
	/** 회원 가입 */ 
	public void insertMember (MemberDTO memberDTO) {
		
		sqlSession.insert("mapper.Member.insertMember", memberDTO);
		
	} // insertMember
	
	/** 롤(등급) 부여 */ 
	public void insertRole(Role role) {
		
		sqlSession.insert("mapper.Member.insertRole", role);
		
	} // insertRole
	
	/** 회원 정보 조회 (단일) */ 
	public MemberDTO selectMember(String id) {

		return sqlSession.selectOne("mapper.Member.selectMember", id);
		
	} // selectMember
	
	/** 회원 정보 수정 */ 
	public void updateMember (MemberDTO memberDTO) {
		
		log.info("memberDTO : {}", memberDTO);
		
		sqlSession.insert("mapper.Member.updateMember", memberDTO);
		
	} // updateMember
	
	/** 전체 회원 수 */ 
	public int selectMembersCount() {
		
		return sqlSession.selectOne("mapper.Member.selectMembersCount");
		
	} // selectMembersCount
	
	/** 전체 회원 정보 조회 (페이징) */ 
	public List<MemberDTO> selectMembersByPaging(int page, int limit) {
		
		Map<String, Integer> map = new HashMap<>();
		map.put("page", page);
		map.put("limit", limit);
		
		return sqlSession.selectList("mapper.Member.selectMembersByPaging", map);
		
	} // selectMembersByPaging
		
	/** 전체 회원 정보 조회 (페이징 아이디 중심 내림차순 정렬) */ 
	public List<Map<String, Object>> selectMembersWithRolesByPaging(int page, int limit) {
		
		Map<String, Integer> map = new HashMap<>();
		map.put("page", page);
		map.put("limit", limit);
		
		return sqlSession.selectList("mapper.Member.selectMembersWithRolesByPaging", map);
		
	} // selectMembersWithRolesByPaging
	
	/** 아이디/이메일/휴대폰 존재여부(중복) 점검 : 회원 정보 수정 */ 
	public boolean hasFldForUpdate(String id, String fld, String val) {
		
		log.info("hasFldForUpdate id : {}, fld : {}, val : {}", id, fld, val);
		
		Map<String, String> map = new HashMap<>();
		map.put("id", id);
		map.put("fld", fld);
		map.put("val", val);
		
		return (int)sqlSession.selectOne("mapper.Member.hasFldForUpdate", map) == 1 ? true : false;

	} // hasFldForUpdate
	
	/** 회원 롤(등급) 조회 */ 
	public List<String> selectRolesById(String id) {
		
		return sqlSession.selectList("mapper.Member.selectRolesById", id);
		
	} // selectRolesById
	
	/** 회원 롤(등급) 삭제 */ 
	public void deleteRoleById(String id, String role) {
		
		Map<String, String> map = new HashMap<>();
		map.put("id", id);
		map.put("role", role);
		
		sqlSession.delete("mapper.Member.deleteRoleById", map);
		
	} // deleteRoleById
	
	/** 회원 롤(등급)들 삭제 */ 
	public void deleteRolesById(String id) {
		
		sqlSession.delete("mapper.Member.deleteRolesById", id);
		
	} // deleteRolesById
	
	/** 회원 정보 삭제 */ 
	public void deleteMemberById(String id) {
		
		sqlSession.delete("mapper.Member.deleteMemberById", id);
		
	} // deleteMemberById
	
	/** 회원 선호/비선호 삭제 */
	public void deleteLadById(String id) {
		
		sqlSession.delete("mapper.Member.deleteLadById", id);
		
	} // deleteLadById
	
	/** 검색 회원 정보 수 */ 
	public int selectMembersCountBySearching(String searchKey, String searchWord) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("searchKey", searchKey);
		map.put("searchWord", searchWord);
		
		return (int)sqlSession.selectOne("mapper.Member.selectMembersCountBySearching", map) ;
		
	} // selectMembersCountBySearching
	
	/** 회원 정보 키워드 검색(조회) (페이징) : 아이디 중심 내림차순 정렬 */ 
	public List<Map<String, Object>> selectMembersWithRolesBySearching(int page, int limit, String searchKey, String searchWord) {
		
		log.info(" 관리자 확인용 인자 : {}", limit, page, searchKey, searchWord);
		
		Map<String, Object> map = new HashMap<>();
		map.put("page", page);
		map.put("limit", limit);
		map.put("searchKey", searchKey);
		map.put("searchWord", searchWord);
		
		return sqlSession.selectList("mapper.Member.selectMembersWithRolesBySearching", map);
		
	} // selectMembersWithRolesBySearching
	
	/** 회원 활동/휴면 상태(enabled) 변경 */ 
	public void changeEnabled(String id, int enabled) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("enabled", enabled);
		
		log.info("상태 정보 : {}", enabled);
		
		log.info("id : {} , enabled : {}", id, enabled);
		
		sqlSession.update("mapper.Member.changeEnabled", map);
		
	} // changeEnabled
	
}
