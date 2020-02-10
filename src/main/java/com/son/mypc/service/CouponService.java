package com.son.mypc.service;

import java.util.List;

import com.son.mypc.model.Coupon;

public interface CouponService {
	
	/**
	 * 1) 미사용 쿠폰 리스트 조회
	 * @param Membno
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception
	 * @use 결제 페이지
	 */
	public List<Coupon> getNotUseCouponList(Coupon input) throws Exception;

	/**
	 * 2) 쿠폰 리스트 조회
	 * @param Membno
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception
	 * @use 마이페이지
	 */
	public List<Coupon> getCouponList(Coupon input) throws Exception;
	
	/**
	 * 3) 쿠폰 추가
	 * @param Coupno를 제외한 파라미터
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception, NullPointerException
	 * @use 회원가입, 관리자 페이지
	 */
	public int addCoupon(Coupon input) throws Exception;
	
	/**
	 * 4) 쿠폰 사용
	 * @param Coupno, Enabled
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception
	 * @use 결제 페이지
	 */
	public int editUseCoupon(Coupon input) throws Exception;
	
	/**
	 * 5) 쿠폰 삭제
	 * @param Coupno
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception, NullPointerException
	 * @use 쿠폰 페이지
	 */
	public int deleteCoupon(Coupon input) throws Exception;
	
	/**
	 * 6) 사용 쿠폰 리스트
	 * @param Membno
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception
	 * @use 주문관리 페이지
	 */
	public List<Coupon> getUseCouponList(Coupon input) throws Exception;
	
	/**
	 * 7) 만료된 쿠폰 수정
	 * @param Coupno
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception, NullPointerException
	 * @use 결제 페이지
	 */
	public int editExpiredCoupon(Coupon input) throws Exception;
	
	/**
	 * 8) 모든 쿠폰 데이터 조회
	 * @return Coupon 조회할 정보를 담고 있는 Beans
	 * @throws Exception
	 * @use 인터셉터[매 자정마다 만료쿠폰 수정]
	 */
	public List<Coupon> getAllList() throws Exception;
}