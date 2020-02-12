package com.son.mypc.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.son.mypc.helper.WebHelper;
import com.son.mypc.model.Cart;
import com.son.mypc.model.Item;
import com.son.mypc.service.CartService;
import com.son.mypc.service.ItemService;

@RestController
public class CartController {
	
	@Autowired
	WebHelper webHelper;
	
	@Autowired
	CartService cartService;
	@Autowired
	ItemService itemService;
	/** 장바구니_확인 페이지 */
	@RequestMapping(value = "/myCart_ok", method = RequestMethod.PUT)
	public Map<String, Object> myCart_ok(HttpServletRequest request) {

		/** 1) 필요한 변수값 생성 */

		int length = webHelper.getInt("length");
		List<Integer> cartno = new ArrayList<Integer>();
		List<Integer> selected_count = new ArrayList<Integer>();

		Cart input = new Cart();

		for (int i = 0; i < length; i++) {
			cartno.add(webHelper.getInt("" + i));
			selected_count.add(webHelper.getInt("mypc_quantity" + i));
			if (selected_count.get(i) != 0) {
				input.setCartno(cartno.get(i));
				input.setSelected_count(selected_count.get(i));
				try {
					cartService.editQuantityCart(input);
				} catch (Exception e) {
					return webHelper.getJsonError(e.getLocalizedMessage());
				}
			}
		}
		return webHelper.getJsonData();
	}

	/** 장바구니_단일삭제 페이지 */
	@RequestMapping(value = "/myCart_del/{cartno}", method = RequestMethod.DELETE)
	public Map<String, Object> myCart_del(@PathVariable("cartno") int cartno, HttpServletRequest request) {

		Cart input = new Cart();

		input.setCartno(cartno);
		try {
			cartService.deleteCart(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		return webHelper.getJsonData();
	}

	/** 장바구니_리스트삭제 페이지 */
	@RequestMapping(value = "/myCart_del_list", method = RequestMethod.DELETE)
	public Map<String, Object> myCart_del_list(HttpServletRequest request) {

		/** 1) 필요한 변수값 생성 */

		Cart input = new Cart();

		String[] LtCartno = webHelper.getStringArray("mypc-chk-li");

		int result = 0;

		for (int i = 0; i < LtCartno.length; i++) {

			result = Integer.parseInt(LtCartno[i]);
			input.setCartno(result);
			try {
				cartService.deleteCart(input);
			} catch (Exception e) {
				return webHelper.getJsonError(e.getLocalizedMessage());
			}
		}

		return webHelper.getJsonData();
	}
	
	/** 작성 폼에 대한 action 페이지 */
	@RequestMapping(value = "/addCart{itemno}", method = RequestMethod.POST)
	public Map<String, Object> addCart(@PathVariable("itemno") int itemno) {

		/** 1) 필요한 변수값 생성 */
		// request 객체를 사용해서 세션 객체를 만들기
		HttpSession session = webHelper.getSession();
		// session서버에 저장한 값을 userId 변수에 담기
		int membno = 0;

		try {
			membno = (int) session.getAttribute("sessionMembno");
		} catch (NullPointerException e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		/** 2) 상품 리스트에서 선택한 상품 번호 조회 */

		Item inputItem = new Item();
		// 저장된 결과를 조회하기 위한 객체
		Item outputItem = null;
		try {
			// 데이터 저장
			// --> 데이터 저장에 성공하면 파라미터로 전달하는 input 객체에 PK값이 저장된다.
			inputItem.setItemno(itemno);
			// 데이터 조회
			outputItem = itemService.getItem(inputItem);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		Cart input = new Cart();
		input.setSelected_count(1); // 수정해야함
		input.setItem_name(outputItem.getItem_name());
		input.setManufac(outputItem.getManufac());
		input.setSpec(outputItem.getSpec());
		input.setPrice(outputItem.getPrice());
		input.setReg_date(outputItem.getReg_date());
		input.setRel_date(outputItem.getRel_date());
		input.setItem_img1(outputItem.getItem_img1());
		input.setItem_img2(outputItem.getItem_img2());
		input.setItem_imgthumb(outputItem.getItem_imgthumb());
		input.setMembno(membno);
		input.setItemno(itemno);

		// 저장된 결과를 조회하기 위한 객체
		List<Cart> output = null;
		try {
			// 데이터 저장
			// --> 데이터 저장에 성공하면 파라미터로 전달하는 input 객체에 PK값이 저장된다.
			cartService.addCart(input);
			// 데이터 조회
			output = cartService.getCartList(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		/** 7) 결과를 확인하기 위한 JSON 출력 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", output);
		return webHelper.getJsonData(map);
	}

}
