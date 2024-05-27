package com.mbc.leteatgo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mbc.leteatgo.repository.MemberDAO;
import com.mbc.leteatgo.domain.MemberDTO;
import com.mbc.leteatgo.domain.Role;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberService {
	
	@Autowired
	MemberDAO memberDAO;
	
	/** 회원 등급 생성 */
	@Transactional
	public boolean insertRole(Role role) {
		
		boolean result = false;
		
		try {
			memberDAO.insertRole(role);
			result = true;
		} catch (Exception e) {
			log.error("MemberService.insertRole : {}", e);
			e.printStackTrace();
		}
		
		return result;
		
	} // insertRole
	
	@Transactional
	/** 회원 가입 과 동시에 멤버에 등급 부여 */
	public boolean insertMemberRole(MemberDTO memberDTO) {
		
		boolean result = false;
		
		try {
			memberDAO.insertMember(memberDTO);
			result = true;
		} catch(Exception e) {
			log.error("MemberService.insertMemberRole.insertMember : {}", e);
			e.printStackTrace();
		}
		
		result = false;
		
		try {
			memberDAO.insertRole(new Role(memberDTO.getMemberId(), "ROLE_USER"));
			
		} catch(Exception e) {
			log.error("MemberService.insertMemberRole.insertRole: {}", e);
			e.printStackTrace();
		}
		
		return result;
		
	} // insertMemberRole
	
	
	/** 회원 정보 조회 (단일) */
	@Transactional(readOnly = true)
	public MemberDTO selectMember(String id) {
		
		return memberDAO.selectMember(id);
		
	} // selectMember
	
	/** 회원 정보 수정 */
	@Transactional
	public boolean updateMember(MemberDTO memberDTO) {
		
		boolean result = false;
		
		try {
			memberDAO.updateMember(memberDTO);
			result = true;
		} catch(Exception e) {
			log.error("MemberService.updateMember: {}", e);
			e.printStackTrace();
		}
		
		return result;
		
	} // updateMember
	
/** ####################### 회원 정보 수정 (관리자) #########################  */
	
	/** 전체 회원 수 */
	@Transactional(readOnly = true)
	public int selectMembersCount() {
		
		return memberDAO.selectMembersCount();
		
	}
	
	/** 전체 회원 정보 조회 (페이징) */
	@Transactional(readOnly = true)
	public List<MemberDTO> selectMembersByPaging(int page, int limit) {
		
		return memberDAO.selectMembersByPaging(page, limit);
		
	} // selectMembersByPaging
	
	/** 전체 회원 정보 조회 (페이징 아이디 중심 내림차순 정렬) */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMembersWithRolesByPaging(int page, int limit) {
		
		return memberDAO.selectMembersWithRolesByPaging(page, limit);
		
	} // selectMembersWithRolesByPaging
	
	/** 아이디/이메일/휴대폰 존재여부(중복) 점검 : 회원 정보 수정 */
	@Transactional(readOnly = true)
	public boolean hasFldForUpdate (String id, String fld, String val) {
		
		log.info("hasFldForUpdate id : {}, fld : {}, val : {}", id, fld, val);
		
		return memberDAO.hasFldForUpdate(id, fld, val);
		
	} // hasFldForUpdate
	
	/** 회원 등급 수정 */
	public boolean updateRoles(String id, boolean roleUserYn, boolean roleAdminYn) {
		
		boolean result = false;
		
		// 먼저 해당 id의 등급(role)이 있는지 점검
		// 있으면 삽입하지 않고 그대로 두고, 없으면 롤(role) 삽입
		
		// 경우의 수 : 대부분 회원(ROLE_USER) 이상의 롤을 보유하고 있기 때문에 이런 경우는
		// 관리자 여부를 우선 점검하는 것이 유효함.
		
		List<String> roles = memberDAO.selectRolesById(id);
		
		// 회원(ROLE_USER)이면서 관리자 권한이 없는 경우
		if (roleAdminYn == true &&
			roles.contains("ROLE_USER") == true &&
			roles.contains("ROLE_ADNIN") == false)
		{
			log.info("관리자 권한 할당");
			
			Role role = new Role();
			role.setMemberId(id);
			role.setMemberRole("ROLE_ADMIN");
			
			result = this.insertRole(role);
		}
		// 회원(ROLE_USER)이면서 관리자 권한을 회수할 경우(관리자 권한 삭제)
		else if (roleAdminYn == false &&
				 roles.contains("ROLE_USER") == true &&
				 roles.contains("ROLE_ADMIN") == true)
		{
			log.info("관리자 권한 회수");
			
			String role = "ROLE_ADMIN";
			result = this.deleteRoleById(id, role);
		}
		
		return result;
	} // updateRoles
	
	/** 회원 등급 삭제 */
	@Transactional
	public boolean deleteRoleById(String id, String role) {
		
		boolean result = false;
		
		try {
			memberDAO.deleteRoleById(id, role);
			result = true;
		} catch (Exception e) {
			log.error("MemberService.deleteRoleById : {}", e);
			e.printStackTrace();
		}
		
		return result;
	} // deleteRoleById
	
	/** 검색 회원 정보 수 */
	@Transactional(readOnly = true)
	public int selectMembersCountBySearching(String searchKey, String searchWord) {
		
		log.info("관리자 ");
		
		return memberDAO.selectMembersCountBySearching(searchKey, searchWord);
	} // selectMembersCountBySearching
	
	/** 회원 정보 키워드 검색(조회) (페이징) : 아이디 중심 내림차순 정렬 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectMembersWithRolesBySearching(int page, int limit, String searchKey, String searchWord) {
		
		log.info(" 관리자 확인용 인자 : {}", limit, page, searchKey, searchWord);
		
		return memberDAO.selectMembersWithRolesBySearching(page, limit, searchKey, searchWord);
	
	} // selectMembersWithRolesBySearching
	
	/** 회원 활동/휴면 상태(enabled) 변경 */
	@Transactional
	public boolean changeEnabled(String id, int enabled) {
		
		
		log.info("id : {} , enabled : {}", id, enabled);
		
		boolean result = false;
		
		try {
			memberDAO.changeEnabled(id, enabled);
			result = true;
			
		} catch (Exception e) {
			result = false;
			log.error("MemberService.changeEnabled : {}", e);
			e.printStackTrace();
		}
		
		return result;
	} // changeEnabled
	
	
	/** 회원 정보 삭제 */
	@Transactional
	public boolean deleteMember(String id) {
		
		boolean result = false;
		
		try {
			memberDAO.deleteRolesById(id);
			result = true;
		} catch (Exception e) {
			result = false;
			log.error("MemberService.deleteMember-1 (role) : {}", e);
			e.printStackTrace();
		}
		
		result = false; //
		
		try {
			memberDAO.deleteLadById(id);
			result = true;
		} catch (Exception e) {
			result = false;
			log.error("MemberService.deleteMember-2 (lad_member) : {}", e);
			e.printStackTrace();
		}
		
		result = false; // 두번째 단계 위한 플래그 변수 초기화 : 순수 회원 정보 삭제
		
		try {
			memberDAO.deleteMemberById(id);
			result = true;
		} catch (Exception e) {
			result = false;
			log.error("MemberService.deleteMember-3 (member_info) : {}", e);
			e.printStackTrace();
		}
		
		return result;
		
	} // deleteMember
	
} // MemberService

