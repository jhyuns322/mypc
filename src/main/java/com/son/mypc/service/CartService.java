package com.son.mypc.service;

	import java.util.List;

	import com.son.mypc.model.Cart;

	public interface CartService {
		
		/** 장바구니 (Cart) **/
		
		/**
		 * 1) 장바구니 상세 조회
		 * @param Membno
		 * @return 조회된 데이터가 저장된 Beans
		 * @throws Exception, NullPointerException
		 * @use  주문관리 페이지
		 */
		public Cart getCartItem(Cart input) throws Exception;
		
		/**
		 * 2) 장바구니 상세 조회
		 * @param Cartno
		 * @return 조회된 데이터가 저장된 Beans
		 * @throws Exception, NullPointerException
		 * @use 결제 페이지
		 */
		public Cart getCartnoItem(Cart input) throws Exception;
		
		/**
		 * 3) 장바구니 전체 조회
		 * @return 조회된 데이터가 저장된 Beans
		 * @throws Exception
		 * @use 마이페이지 ,장바구니 페이지
		*/
		public List<Cart> getCartList(Cart input1) throws Exception;
		
		/**
		 * 4) 장바구니 추가
		  * @param Cartno를 제외한 Cart의 모든 파라미터
		  * @return Cart 저장할 정보를 담고 있는 Beans
		  * @throws Exception, NullPointerException
		  * @use 장바구니 추가
		*/
		public int addCart(Cart input) throws Exception;
		
		/**
		 * 5) 장바구니 삭제
		 * @param Cartno
		 * @return Cart 삭제할 정보를 담고 있는 Beans
		 * @throws Exception
		 * @use 결제 페이지, 장바구니 페이지
		 */
		public int deleteCart(Cart input) throws Exception;
	
		/**
		 * 6) 장바구니 수량 변경
		 * @param Cartno, Selected_count
		 * @return Cart 수정할 정보를 담고 있는 Beans
		 * @throws Exception
		 * @use 장바구니 페이지
		 */
		public int editQuantityCart(Cart input) throws Exception;
		
}
