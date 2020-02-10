package com.son.mypc.service;

import java.util.List;

import com.son.mypc.model.Item;

public interface ItemService {
	/**
	 * 상품 데이터 상세 조회
	 * 
	 * @param Item 조회할 학과의 일련번호를 담고 있는 Beans
	 * @return 조회된 데이터가 저장된 Beans
	 * @throws Exception
	 */
	public Item getItem(Item input) throws Exception;

	/**
	 * 상품 데이터 목록 조회
	 * 
	 * @return 조회 결과에 대한 컬렉션
	 * @throws Exception
	 */
	public List<Item> getItemList(Item input) throws Exception;

	/**
	 * 상품 데이터가 저장되어 있는 갯수 조회
	 * 
	 * @return int
	 * @throws Exception
	 */
	public int getItemCount(Item input) throws Exception;

	/**
	 * 상품 데이터 등록하기
	 * 
	 * @param Item 저장할 정보를 담고 있는 Beans
	 * @throws Exception
	 */
	public int addItem(Item input) throws Exception;

	/**
	 * 상품 데이터 수정하기
	 * 
	 * @param Item 저장할 정보를 담고 있는 Beans
	 * @throws Exception
	 */
	public int editItem(Item input) throws Exception;

	/**
	 * 상품 데이터 삭제하기
	 * 
	 * @param Item 삭제할 학과의 일련번호를 담고 있는 Beans
	 * @throws Exception
	 */
	public int deleteItem(Item input) throws Exception;
	
	/**
	 * 상품 데이터 수량 수정하기
	 * 
	 * @param Item 수정할 상품의 정보를 담고있는 Beans
	 * @throws Exception
	 */
	public int editStock(Item input) throws Exception;
	
	/**
	 * 상품 판매 개수 데이터 수정하기
	 * 
	 * @param Item 수정할 상품의 정보를 담고있는 Beans
	 * @throws Exception
	 */
	public int editItemSold(Item input) throws Exception;
	
	/**
	 * 카테고리별 상품 등록 수 통계 데이터 목록 조회
	 * 
	 * @return 조회 결과에 대한 컬렉션
	 * @throws Exception
	 */
	public List<Item> getItemListByStats(Item input) throws Exception;
}