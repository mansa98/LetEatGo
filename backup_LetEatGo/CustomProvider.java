package com.mbc.leteatgo.service;

import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mbc.leteatgo.domain.CustomUser;
import com.mbc.leteatgo.domain.Role;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomProvider implements AuthenticationProvider, UserDetailsService {
	
	private JdbcTemplate jdbcTemplate;
	
	// JDBC 커넥션 스타트
	@Autowired // 보완 패치(04-11)
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/** 구현하는 인터페이스에서 정의해놓은 추상 함수(반드시 구현필요) */
	// 리턴형을 UserDetails => CustomUser 로 변경
	@Override
	public CustomUser loadUserByUsername(String username) {
		
		log.info("Service패키지의 loadUserByUsername");
		
		CustomUser customUser = null;
		try {
			
			customUser = (CustomUser)jdbcTemplate.queryForObject( 
					"SELECT member_id as username, member_pw as password, member_enabled as enabled FROM member_tbl WHERE member_id=?",
					new BeanPropertyRowMapper<CustomUser>(CustomUser.class),
					new Object[] {username}
					);
			
		} catch(EmptyResultDataAccessException e) {
			log.info("EmptyResultDataAccessException");
			return null;
		}
		
		log.info("customUser : {}", customUser);
		
		return customUser;
		
	}// loadUserByUsername()
	
	
	private List<Role> loadUserRole(String memberId) {
		
		log.info("Service패키지의 loadUserRole");
		
		try {
			return (List<Role>)jdbcTemplate.query(
					"SELECT member_id as memberId, member_role as memberRole FROM member_role_tbl WHERE member_id=?",
					new BeanPropertyRowMapper<Role>(Role.class),
					new Object[] {memberId}
					);
		} catch(EmptyResultDataAccessException e) {
			log.info("EmptyResultDataAccessException");
			return null;
		}
	}
	
	
	/** 구현하는 인터페이스에서 정의해놓은 추상 함수(반드시 구현필요) */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		log.info("----- authenticate");
		
		/** Role의 멤버필드명을 사용한다 */
		/** principal 인터페이스의 메소드 getName: this principal의 이름을 반환합니다 */
		String memberId = authentication.getName();
		String password = "";
		
		CustomUser user = null;
		Collection<? extends GrantedAuthority> authorities = null;
		
		try {
			if (memberId.trim().equals("")) {
				
				throw new InternalAuthenticationServiceException("Id를 입력해주세요.");
				
			}
			
			if (this.loadUserByUsername(memberId) == null)  {
				throw new UsernameNotFoundException("존재하지 않는 Id입니다.");
			}
			
			user = this.loadUserByUsername(memberId);
			
			log.info("user(사용자 현황): " + user);
			
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			/** getCredentials(): returns the credentials that prove the identity of the Principal */
			password = (String)authentication.getCredentials(); // 비교할 비밀번호
			
			if (passwordEncoder.matches(password, user.getPassword())) {
				log.info("비밀번호가 일치합니다 ");
			} else {
				throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
			}
			
			List<Role> roles = this.loadUserRole(memberId);
			user.setAuthorities(roles);
			
			authorities = user.getAuthorities();
			
			authorities.forEach(x -> {
				log.info("@@@ authorities: {}", x);
			});
			
		} catch(InternalAuthenticationServiceException e) {
			log.info("회원 아이디 미입력: " + e.toString());
			throw new InternalAuthenticationServiceException(e.getMessage());
		} catch(UsernameNotFoundException e) {
			log.info("회원 아이디가 존재하지 않음: " + e.toString());
			throw new InternalAuthenticationServiceException(e.getMessage());
		} catch(BadCredentialsException e) {
			log.info("잘못된 패스워드입니다: " + e.toString());
			throw new BadCredentialsException(e.getMessage());
		} catch(Exception e) {
			log.info("다른 종류의 에러: " + e.toString());
			e.printStackTrace();
		}
		
		return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}// authenticate()
	
	/** 구현하는 인터페이스에서 정의해놓은 추상 함수(반드시 구현필요) */
	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
	

}// CustomProvider
