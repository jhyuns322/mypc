package com.son.mypc.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.son.mypc.model.Item;
import com.son.mypc.service.ItemService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	SqlSession sqlSession;

	/**
	 * 상품 데이터 상세 조회
	 * 
	 * @param Item 조회할 학과의 일련번호를 담고 있는 Beans
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception
	 */
	@Override
	public Item getItem(Item input) throws Exception {
		Item result = null;

		try {
			result = sqlSession.selectOne("ItemMapper.selectItem", input);
			if (result == null) {
				throw new NullPointerException("result=null");
			}
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
	 * 상품 데이터 목록 조회
	 * 
	 * @return 조회 결과에 대한 컬렉션
	 * @throws Exception
	 */
	@Override
	public List<Item> getItemList(Item input) throws Exception {
		List<Item> result = null;

		try {	
			result = sqlSession.selectList("ItemMapper.selectList", input);

			if (result == null) {
				throw new NullPointerException("result=null");
			}
		} catch (NullPointerException e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("조회된 데이터가 없습니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public int getItemCount(Item input) throws Exception {
		int result = 0;

		try {
			result = sqlSession.selectOne("ItemMapper.selectItemCount", input);
		} 
		catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 조회 실패했습니다.");
		}
		return result;
	}

	/**
	 * 상품 데이터 등록하기
	 * 
	 * @param Item 저장할 정보를 담고 있는 Beans
	 * @throws Exception
	 */
	@Override
	public int addItem(Item input) throws Exception {
		int result = 0;

		try {
			result = sqlSession.insert("ItemMapper.insertItem", input);

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
	 * 상품 데이터 수정하기
	 * 
	 * @param Item 저장할 정보를 담고 있는 Beans
	 * @throws Exception
	 */
	@Override
	public int editItem(Item input) throws Exception {
		int result = 0;

		try {
			result = sqlSession.update("ItemMapper.updateItem", input);

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
	 * 상품 데이터 삭제하기
	 * 
	 * @param Item 삭제할 학과의 일련번호를 담고 있는 Beans
	 * @throws Exception
	 */
	@Override
	public int deleteItem(Item input) throws Exception {
		int result = 0;

		try {
			// 상품 데이터 삭제하기 전에 장바구니 데이터 삭제
			sqlSession.delete("CartMapper.deleteCartForItem",input);
			result = sqlSession.delete("ItemMapper.deleteItem", input);

			if (result == 0) {
				throw new NullPointerException("result=0");
			}
		} catch (NullPointerException e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("삭제된 데이터가 없습니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 삭제에 실패했습니다.");
		}

		return result;
	}

	/**
	 * 상품 수량 수정하기
	 * 
	 * @param Item 수정할 상품의 정보를 담고있는 Beans
	 * @throws Exception
	 */
	@Override
	public int editStock(Item input) throws Exception {
		int result = 0;

		try {
			result = sqlSession.update("editStock", input);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("수량변경에 실패했습니다.");
		}

		return result;
	}
	
	/**
	 * 상품 판매 개수 데이터 수정하기
	 * 
	 * @param Item 수정할 상품의 정보를 담고있는 Beans
	 * @throws Exception
	 */
	@Override
	public int editItemSold(Item input) throws Exception {
		int result =0;
		try {
			result = sqlSession.update("editItemSold", input);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 수정에 실패했습니다.");
		}

		return result;
	}
	
	/**
	 * 카테고리별 상품 등록 수 통계 데이터 목록 조회
	 * 
	 * @return 조회 결과에 대한 컬렉션
	 * @throws Exception
	 */
	@Override
	public List<Item> getItemListByStats(Item input) throws Exception {
		List<Item> result = null;

		try {
			result = sqlSession.selectList("ItemMapper.selectStats1", input);
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
