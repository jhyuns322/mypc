package com.son.mypc.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.son.mypc.model.Order;
import com.son.mypc.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	SqlSession sqlSession;

	/**
	 * 1) 주문 리스트 조회
	 * @param Membno
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception
	 * @use 마이페이지, 주문관리 페이지
	 */
	@Override
	public List<Order> getOrderList(Order input) throws Exception {
		List<Order> result = null;
		try {
			result = sqlSession.selectList("OrderMapper.selectOrderList", input);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 조회에 실패했습니다.");
		}
		return result;
	}
	
	/**
	 * 2) 주문 리스트 필터링 [월별 조회]
	 * @param Membno, Ordered_date
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception
	 * @use 주문관리 페이지
	 */
	@Override
	public List<Order> getOrderListByMonth(Order input) throws Exception {
		List<Order> result = null;
		try {
			result = sqlSession.selectList("OrderMapper.selectOrderListByMonth", input);
		}  catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("장바구니 조회에 실패했습니다.");
		}
		return result;
	}

	/**
	 * 3) 주문 리스트 필터링 [상세 조회]
	 * @param Membno, Ordered_date, Ordered_date2
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception
	 * @use 주문관리 페이지
	 */
	@Override
	public List<Order> getOrderListByDate(Order input) throws Exception {
		List<Order> result = null;
		try {
			result = sqlSession.selectList("OrderMapper.selectOrderListByDate", input);
		}  catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("장바구니 조회에 실패했습니다.");
		}
		return result;
	}

	/**
	 * 4) 주문 추가
	 * @param Orderno를 제외한 파라미터
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception, NullPointerException
	 * @use 결제 페이지
	 */
	@Override
	public int addOrder(Order input) throws Exception {
		int result = 0;
		try {
			result = sqlSession.insert("OrderMapper.insertOrder", input);
			if (result == 0) {
				throw new NullPointerException("result=0");
			}
		} catch (NullPointerException e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("저장된 데이터가 없습니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 저장에 실패했습니다.");
			// 예외 발생하면 return 값 없음.
		}
		return result;
	}

	/**
	 * 5) 주문 삭제
	 * @param Orderno, Membno
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception
	 * @use 주문관리 페이지
	 */
	@Override
	public int deleteOrder(Order input) throws Exception {
		int result =0;
		try {
			result = sqlSession.update("OrderMapper.deleteOrderByUpdate", input);
		} catch(Exception e){
			log.error(e.getLocalizedMessage());
		}
		return result;
	}
	
	/**
	 * 6) 카테고리별 판매량 통계 데이터 목록 조회
	 * @return 조회 결과에 대한 컬렉션
	 * @throws Exception
	 * @use 통계 페이지
	 */
	@Override
	public List<Order> getOrderListByStats1(Order input) throws Exception {
		List<Order> result = null;
		try {
			result = sqlSession.selectList("OrderMapper.selectStats1", input);
		} catch (NullPointerException e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("조회된 데이터가 없습니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 조회에 실패했습니다.");
		}
		return result;
	}
	
	/**
	 * 7) 기간별 상품 판매 금액 통계 데이터 목록 조회
	 * @param startDate, endDate
	 * @return 조회 결과에 대한 컬렉션
	 * @throws Exception
	 * @use 통계 페이지
	 */
	@Override
	public List<Order> getOrderListByStats2(Order input) throws Exception {
		List<Order> result = null;
		try {
			result = sqlSession.selectList("OrderMapper.selectStats2", input);
		} catch (NullPointerException e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("조회된 데이터가 없습니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 조회에 실패했습니다.");
		}
		return result;
	}
}