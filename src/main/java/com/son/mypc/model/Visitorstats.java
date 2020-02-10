package com.son.mypc.model;

import lombok.Data;

/** 방문자통계 테이블 */
@Data
public class Visitorstats {
	private int visitno;  		/** 방문자통계 일련번호 */
	private String visit_date;  /** 방문 일자 */
	private String visit_time;  /** 방문 시간 */
	private String visit_num;  	/** 방문자 수 */
	private int member_membno; 	/** 회원 일련번호 */
}
