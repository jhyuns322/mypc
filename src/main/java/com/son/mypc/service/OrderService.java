package com.son.mypc.service;

	import java.util.List;

	import com.son.mypc.model.Order;

	public interface OrderService {
		
		/**
		 * 1) 주문 리스트 조회
		 * @param Membno
		 * @return 조회된 데이터가 저장된 Beans
		 * @throws Exception
		 * @use 마이페이지, 주문관리 페이지
		 */
		public List<Order> getOrderList(Order input) throws Exception;
		
		/**
		 * 2) 주문 리스트 필터링 [월별 조회]
		 * @param Membno, Ordered_date
		 * @return 조회된 데이터가 저장된 Beans
		 * @throws Exception
		 * @use 주문관리 페이지
		 */
		public List<Order> getOrderListByMonth(Order input) throws Exception;
		
		/**
		 * 3) 주문 리스트 필터링 [상세 조회]
		 * @param Membno, Ordered_date, Ordered_date2
		 * @return 조회된 데이터가 저장된 Beans
		 * @throws Exception
		 * @use 주문관리 페이지
		 */
		public List<Order> getOrderListByDate(Order input) throws Exception;
	
		/**
		 * 4) 주문 추가
		 * @param Orderno를 제외한 파라미터
		 * @return 조회된 데이터가 저장된 Beans
		 * @throws Exception, NullPointerException
		 * @use 결제 페이지
		 */
		public int addOrder(Order input) throws Exception;

		/**
		 * 5) 주문 삭제
		 * @param Orderno, Membno
		 * @return 조회된 데이터가 저장된 Beans
		 * @throws Exception
		 * @use 주문관리 페이지
		 */
		public int deleteOrder(Order input) throws Exception;
	
		/**
		 * 6) 카테고리별 판매량 통계 데이터 목록 조회
		 * @return 조회 결과에 대한 컬렉션
		 * @throws Exception
		 * @use 통계 페이지
		 */
		public List<Order> getOrderListByStats1(Order input) throws Exception;
		
		/**
		 * 7) 기간별 상품 판매 금액 통계 데이터 목록 조회
		 * @param startDate, endDate
		 * @return 조회 결과에 대한 컬렉션
		 * @throws Exception
		 * @use 통계 페이지
		 */
		public List<Order> getOrderListByStats2(Order input) throws Exception;
}
