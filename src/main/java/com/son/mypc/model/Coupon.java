package com.son.mypc.model;

import lombok.Data;

/** 쿠폰 테이블 */
@Data
public class Coupon {
	private int coupno; 			/** 쿠폰 일련번호 */
	private String coupon_name; 	/** 쿠폰 이름 */
	private int coupon_off; 		/** 상품 할인 금액 */
	private String issue_date; 		/** 발행일 */
	private String expired_date; 	/** 만료일 */
	private int enabled; 			/** 쿠폰 사용 여부 */
	private int membno; 			/** 회원 일련번호 */
}