package com.son.mypc.model;

import lombok.Data;

/** 주문 테이블 */
@Data
public class Order {
	private int orderno;  		 		/** 주문 일련번호 */
	private String ordered_num;  		/** 주문번호 */
	private String ordered_date;  		/** 주문일자 */
	private int selected_count;  		/** 선택상품 수량 */
	private String category;  			/** 상품 종류 */
	private String item_name;  			/** 상품 이름 */
	private String manufac;  		 	/** 제조사 */
	private String spec;  		 		/** 상품 스펙 */
	private int price;  		 		/** 가격 */
	private String reg_date;  			/** 등록일 */
	private String rel_date;  		 	/** 출시일 */
	private String item_img1;  			/** 이미지 경로1 */
	private String item_img2;  			/** 이미지 경로2 */
	private String item_imgthumb;		/** 썸네일 경로 */
	private Integer membno;  			/** 회원 일련번호 */
	private String name;				/** 수령인 **/
	private String addr1;				/** 수령받을 주소1 **/
	private String addr2;				/** 수령받을 주소2 **/
	private String tel;					/** 수령인 연락처 **/
	private String startDate;			/** 통계 데이터 조회 시작일 **/
	private String endDate;				/** 통계 데이터 조회 종료일 **/
	
	
	private String ordered_date2;
}