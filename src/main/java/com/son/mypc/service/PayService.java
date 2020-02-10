package com.son.mypc.service;

	import com.son.mypc.model.Pay;

	public interface PayService {
		
		/**
		 * 1) 장바구니 데이터 상세 조회
		 * @param Cartno
		 * @return 조회된 데이터가 저장된 Beans
		 * @throws Exception, NullPointerException
		 * @use 결제 페이지
		 */
		public Pay getCartItem(Pay input) throws Exception;
		
	}