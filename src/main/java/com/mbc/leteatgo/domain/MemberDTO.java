package com.mbc.leteatgo.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class MemberDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** 회원 번호 */
	private int memberNum;
	
	/** 회원 아이디 */
	@NotNull(message = "아이디 입력은 필수 입니다.")
	@Pattern(regexp = "^[a-zA-Z0-9]{6,12}$", message = "아이디는 6~12자 영문 대 소문자, 숫자를 입력해주세요.")
	private String memberId;
	
	/** 회원 비밀번호 */
	@NotNull(message = "비밀번호 입력은 필수 입니다.")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 입력해주세요.")
	private String memberPw;
	
	/** 회원 이름 */
	@NotNull(message = "이름 입력은 필수 입니다.")
	@Pattern(regexp = "^[가-힣]{2,8}$", message = "이름은 2자 이상 한글만 입력해주세요.")
	private String memberName;
	
	/** 회원 닉네임 */
	@NotNull(message = "닉네임 입력은 필수 입니다.")
	@Pattern(regexp = "^[a-zA-Z0-9가-힣]{2,8}$", message = "닉네임는 2~8자 영문 대 소문자, 숫자, 한글을 입력해주세요.")
	private String memberNick;
	
	/** 회원 성별 */
	private String memberGender;
	
	/** 회원 생년 월일*/
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@NotNull(message = "생년 월일 입력은 필수 입니다.")
	private Date memberBirthday;
	
	/** 회원 이메일 */
	@NotNull(message = "이메일 입력은 필수 입니다.")
	@Pattern(regexp = "^[a-zA-Z0-9]+@[0-9a-zA-Z]+\\.[a-z]+$", message = "이메일 형식이 올바르지 않습니다.")
	private String memberEmail;
	
	/** 회원 번호 */
	@NotBlank(message = "핸드폰 번호 입력은 필수 입니다.")
	private String memberMobile;
	
	/** 회원 우편번호 */
	private String memberZip;
	
	/** 회원 도로명 주소 */
	private String memberRoadAddress;

	/** 회원 지번 주소 */
	private String memberJibunAddress;
	
	/** 회원 상세 주소 */
	private String memberDetailAddress;
	
	/** 회원 가입일 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date memberJoinDate;
	
	/** 회원 활성화 여부*/
	private int memberEnabled;
	
	public static String encodePassword(PasswordEncoder passwordEncoder, String rawPw) {
        return passwordEncoder.encode(rawPw);
    }
	
}
