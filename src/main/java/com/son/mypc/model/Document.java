package com.son.mypc.model;

import lombok.Data;

/** 게시판 테이블 */
@Data
public class Document {
	// 1) 기본 컬럼
	private int docno;  		 	/** 게시판번호 (PK) */
	private String doc_type;  		/** 글 유형 (자유게시판 or 문의) */	
	private String subject;  		/** 글 제목 */
	private String content;  		/** 글 내용 */
	private String reg_date;  		/** 작성일자 */
	private String edit_date;  		/** 수정일자 */
	private String replied;  		/** 글 확인 여부 (only 문의) */
	private int hits;  		 		/** 조회수 */
	private int comment;			/** 덧글 수 */
	private int membno;  		 	/** 회원번호 (FK) */
	
	// 2) JOIN절에 따른 추가 컬럼
	private String user_id; 		/** 아이디 */
	private String email; 			/** 이메일 */
	
	// 3) 페이지 구현을 위한 static 변수
	private static int offset;		/** LIMIT 절에서 사용할 조회 시작 위치 */
	private static int listCount; 	/** LIMIT 절에서 사용할 조회 데이터 수 */
	
	public static int getOffset() {
		return offset;
	}
	
	public static void setOffset(int offset) {
		Document.offset = offset;
	}
	
	public static int getListCount() {
		return listCount;
	}
	
	public static void setListCount(int listCount) {
		Document.listCount = listCount;
	}
}
