package com.son.mypc.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.son.mypc.model.Pay;
import com.son.mypc.service.PayService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PayServiceImpl implements PayService {
	
	@Autowired
	SqlSession sqlSession;

	/**
	 * 1) 장바구니 데이터 상세 조회
	 * @param Cartno
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception, NullPointerException
	 * @use 결제 페이지
	 */
	@Override
	public Pay getCartItem(Pay input) throws Exception {
		Pay result = null;
		try {
			result = sqlSession.selectOne("PayMapper.selectCartItem", input);
			if (result == null) {
				throw new NullPointerException("result=null");
			}
		} catch (NullPointerException e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("조회된 데이터가 없습니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("장바구니 조회에 실패했습니다.");
		}
		return result;
	}
}