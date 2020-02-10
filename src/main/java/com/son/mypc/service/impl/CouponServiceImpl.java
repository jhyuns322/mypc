package com.son.mypc.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.son.mypc.model.Coupon;
import com.son.mypc.service.CouponService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CouponServiceImpl implements CouponService {

	@Autowired
	SqlSession sqlSession;

	/**
	 * 1) 미사용 쿠폰 리스트 조회
	 * @param Membno
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception
	 * @use 결제 페이지
	 */
	@Override
	public List<Coupon> getNotUseCouponList(Coupon input) throws Exception {
		List<Coupon> result = null;
		try {
			result = sqlSession.selectList("CouponMapper.selectNotUseCouponItem", input);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 조회에 실패했습니다.");
		}
		return result;
	}

	/**
	 * 2) 쿠폰 리스트 조회
	 * @param Membno
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception
	 * @use 마이페이지
	 */
	@Override
	public List<Coupon> getCouponList(Coupon input) throws Exception {
		List<Coupon> result = null;
		try {
			result = sqlSession.selectList("CouponMapper.selectCouponItem", input);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 조회에 실패했습니다.");
		}
		return result;
	}

	/**
	 * 3) 쿠폰 추가
	 * @param Coupno를 제외한 파라미터
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception, NullPointerException
	 * @use 회원가입, 관리자 페이지
	 */
	@Override
	public int addCoupon(Coupon input) throws Exception {
		int result = 0;
		try {			
			result = sqlSession.insert("CouponMapper.insertCouponItem", input);
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
	 * 4) 쿠폰 사용
	 * @param Coupno, Enabled
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception
	 * @use 결제 페이지
	 */
	@Override
	public int editUseCoupon(Coupon input) throws Exception {
		int result = 0;
		try {
			result = sqlSession.update("CouponMapper.useCoupon", input);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 수정에 실패했습니다.");
		}
		return result;
	}

	/**
	 * 5) 쿠폰 삭제
	 * @param Coupno
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception, NullPointerException
	 * @use 쿠폰 페이지
	 */
	@Override
	public int deleteCoupon(Coupon input) throws Exception {
		int result = 0;
		try {
			result = sqlSession.update("CouponMapper.delCoupon", input);
			if (result == 0) {
				throw new NullPointerException("result=0");
			}
		} catch (NullPointerException e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("수정된 데이터가 없습니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 수정에 실패했습니다.");
		}
		return result;
	}

	/**
	 * 6) 사용 쿠폰 리스트
	 * @param Membno
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception
	 * @use 주문관리 페이지
	 */
	@Override
	public List<Coupon> getUseCouponList(Coupon input) throws Exception {
		List<Coupon> result = null;
		try {
			result = sqlSession.selectList("CouponMapper.selectUseCouponItem", input);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 조회에 실패했습니다.");
		}
		return result;
	}

	/**
	 * 7) 만료된 쿠폰 수정
	 * @param Coupno
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception, NullPointerException
	 * @use 결제 페이지
	 */
	@Override
	public int editExpiredCoupon(Coupon input) throws Exception {
		int result = 0;
		try {
			result = sqlSession.update("CouponMapper.expireCoupon", input);
			if (result == 0) {
				throw new NullPointerException("result=0");
			}
		} catch (NullPointerException e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("수정된 데이터가 없습니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 수정에 실패했습니다.");
		}
		return result;
	}

	/**
	 * 8) 모든 쿠폰 데이터 조회
	 * @return Coupon 조회할 정보를 담고 있는 Beans
	 * @throws Exception
	 * @use 인터셉터[매 자정마다 만료쿠폰 수정]
	 */
	@Override
	public List<Coupon> getAllList() throws Exception {
		List<Coupon> result = null;

		try {
			result = sqlSession.selectList("CouponMapper.selectAllList");

		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 조회에 실패했습니다.");
		}

		return result;
	}
}