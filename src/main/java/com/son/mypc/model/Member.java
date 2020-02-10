package com.son.mypc.model;

import lombok.Data;

/** 회원 테이블 */
@Data
public class Member {
	private int membno;  		/** 회원 일련번호 */
	private String user_id;  	/** 아이디 */
	private String user_pw;  	/** 비밀번호 */
	private String name;  		/** 이름 */
	private String birthdate; 	/** 생년월일 */
	private String gender;  	/** 성별 */
	private String email;  		/** 이메일 */
	private String addr1;  		/** 주소1 */
	private String addr2;  		/** 주소2 */
	private String tel;  		/** 연락처 */
	private String reg_date;  	/** 가입 일자 */
	
	private static int offset;
	private static int listCount;
	
	public static int getOffset() {
		return offset;
	}
	public static void setOffset(int offset) {
		Member.offset = offset;
	}
	public static int getListCount() {
		return listCount;
	}
	public static void setListCount(int listCount) {
		Member.listCount = listCount;
	}
}
