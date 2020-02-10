package com.son.mypc.model;

import lombok.Data;

/** 덧글 테이블 */
@Data
public class Comment {
	// 1) 기본 컬럼
	private int commno;  		 	/** 덧글 번호 (PK) */
	private String comment;  		/** 덧글 내용 (자유게시판 or 문의) */	
	private String reg_date;  		/** 작성일자 */
	private int membno;  		 	/** 회원번호 (FK) */
	private int docno;  		 	/** 게시글번호 (FK) */
	
	// 2) JOIN절에 따른 추가 컬럼
	private String user_id;		    /** 아이디 */	
}