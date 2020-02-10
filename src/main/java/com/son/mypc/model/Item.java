package com.son.mypc.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/** 상품 테이블 */
@Data
public class Item {
	@SerializedName("itemno")			private int itemno; 					// 상품 일련번호 
	@SerializedName("category")			private String category; 				// 상품 카테고리
	@SerializedName("item_name")		private String item_name; 				// 상품 이름
	@SerializedName("manufac")			private String manufac; 				// 제조사
	@SerializedName("spec")				private String spec;  			 		// 상품 스펙
	@SerializedName("price")			private int price; ; 			 		// 가격
	@SerializedName("stock")			private int stock;  			 		// 상품 재고
	@SerializedName("reg_date")			private String reg_date;  		 		// 등록일
	@SerializedName("rel_date")			private String rel_date;  		 		// 출시일
	@SerializedName("item_img1")		private String item_img1;  				// 상품 이미지 경로
	@SerializedName("item_img2")		private String item_img2;  				// 상품 설명 이미지 경로
	@SerializedName("item_imgthumb")	private String item_imgthumb;			// 상품 이미지 썸네일 경로
	@SerializedName("item_sold")		private Integer item_sold;				// 상품 판매 개수
	@SerializedName("item_chked")		private List<String> item_chked;		// 체크박스 리스트,  DB Column 존재 X
	@SerializedName("item_alignment")	private String item_alignment; 			// 상품 정렬 옵션, 	 DB Column 존재 X
	@SerializedName("minimumPrice")		private int minimumPrice;				// 가격대 검색 옵션, DB Column 존재 X
	@SerializedName("maximumPrice")		private int maximumPrice;				// 가격대 검색 옵션, DB Column 존재 X

	/** 페이지 구현이 필요한 경우 아래 속성들을 추가한다. (static) */
	private static int offset;					/** LIMIT 절에서 사용할 검색 시작 위치 */
	private static int listCount;			/** LIMIT 절에서 사용할 검색할 데이터 수 */
	
	public static int getOffset() {
		return offset;
	}
	
	public static void setOffset(int offset) {
		Item.offset = offset;
	}
	
	public static int getListCount() {
		return listCount;
	}
	
	public static void setListCount(int listCount) {
		Item.listCount = listCount;
	}
}
