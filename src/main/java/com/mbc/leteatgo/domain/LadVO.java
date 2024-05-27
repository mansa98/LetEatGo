package com.mbc.leteatgo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="lad_tbl")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LadVO {
	
	@SequenceGenerator(name="lad_tbl_seq",
						allocationSize=1)
	
	@GeneratedValue(strategy=GenerationType.SEQUENCE,
					generator="lad_tbl_seq")
	@Id
	@Column(name="lad_num")
	private int ladNum;
	
	@Column(name="member_id")
	private String memberId;
	
	@Column(name="lad_like")
	private String ladLike;
	
	@Column(name="lad_dislike")
	private String ladDislike;
}
