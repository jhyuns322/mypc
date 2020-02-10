package com.son.mypc.model;

import lombok.Data;

/** 판매통계 테이블 */
@Data
public class Salesstats {
	private int saleno;  		 	/** 판매통계 일련번호 */		
	private String sale_date;  		/** 판매일자 */
	private int sale_price;  		/** 판매금액 */
	private int sale_quantity;  	/** 판매수량 */
	private int order_orderno;  	/** 주문 일련번호 */
}