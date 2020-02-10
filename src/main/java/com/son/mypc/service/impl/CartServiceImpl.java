package com.son.mypc.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.son.mypc.model.Cart;
import com.son.mypc.service.CartService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

	@Autowired
	SqlSession sqlSession;

	/**
	 * 1) 장바구니 상세 조회
	 * @param Membno
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception, NullPointerException
	 * @use  주문관리 페이지
	 */
	@Override
	public Cart getCartItem(Cart input) throws Exception {
		Cart result = null;
		try {
			result = sqlSession.selectOne("CartMapper.selectCartItem", input);
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
	
	/**
	 * 2) 장바구니 상세 조회
	 * @param Cartno
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception, NullPointerException
	 * @use 결제 페이지
	 */
	@Override
	public Cart getCartnoItem(Cart input) throws Exception {
		Cart result = null;
		try {
			result = sqlSession.selectOne("CartMapper.selectCartNoItem", input);
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

	/**
	 * 3) 장바구니 전체 조회
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception
	 * @use 마이페이지 ,장바구니 페이지
	*/
	@Override
	public List<Cart> getCartList(Cart input) throws Exception {
		List<Cart> result = null;
		try {
			result = sqlSession.selectList("CartMapper.selectCartItem", input);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 조회에 실패했습니다.");
		}
		return result;
	}

	/**
	 * 4) 장바구니 추가
	  * @param Cartno를 제외한 Cart의 모든 파라미터
	  * @return Cart 저장할 정보를 담고 있는 Beans
	  * @throws Exception, NullPointerException
	  * @use 장바구니 추가
	*/
	@Override
	public int addCart(Cart input) throws Exception {
		int result = 0;
		try {
			result = sqlSession.insert("CartMapper.insertCartItem", input);
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
	 * 5) 장바구니 삭제
	 * @param Cartno
	 * @return Cart 삭제할 정보를 담고 있는 Beans
	 * @throws Exception
	 * @use 결제 페이지, 장바구니 페이지
	 */
	@Override
	public int deleteCart(Cart input) throws Exception {
		int result =0;
		try {
		result = sqlSession.delete("deleteCart", input);
		}catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("삭제에 실패했습니다.");
		}
		return result;
	}

	/**
	 * 6) 장바구니 수량 변경
	 * @param Cartno, Selected_count
	 * @return Cart 수정할 정보를 담고 있는 Beans
	 * @throws Exception
	 * @use 장바구니 페이지
	 */
	@Override
	public int editQuantityCart(Cart input) throws Exception {
		int result = 0;
		try {
			result = sqlSession.update("editQuantity", input);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("수량변경에 실패했습니다.");
		}
		return result;
	}

}
