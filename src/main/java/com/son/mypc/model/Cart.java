package com.son.mypc.model;

import lombok.Data;

/** 장바구니 테이블 */
@Data
public class Cart {
	private int cartno;						/** 장바구니 일련번호 */
	private int selected_count; 		/** 선택상품 수량 */
	private String item_name; 		/** 상품 이름 */
	private String manufac; 			/** 제조사 */
	private String spec; 					/** 상품 스펙 */
	private int price; 						/** 가격 */
	private String reg_date; 			/** 등록일 */
	private String rel_date; 				/** 출시일 */
	private String item_img1; 			/** 이미지 경로1 */
	private String item_img2; 			/** 이미지 경로2 */
	private String item_imgthumb;/** 썸네일 경로 */
	private int membno; 					/** 회원 일련번호 */
	private int itemno; 					/** 상품 일련번호 */
}