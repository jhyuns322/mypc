package com.son.mypc.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.son.mypc.helper.WebHelper;
import com.son.mypc.model.Cart;
import com.son.mypc.model.Coupon;
import com.son.mypc.model.Item;
import com.son.mypc.model.Order;
import com.son.mypc.service.CartService;
import com.son.mypc.service.CouponService;
import com.son.mypc.service.ItemService;
import com.son.mypc.service.OrderService;

@RestController
public class OrderPayController {

	@Autowired
	WebHelper webHelper;
	
	@Autowired
	OrderService orderService;
	@Autowired
	CartService cartService;
	@Autowired
	ItemService itemService;
	@Autowired
	CouponService couponService;

	/** 주문관리_취소 페이지 */
	@RequestMapping(value = "/myOrder_cancel", method = RequestMethod.PUT)
	public Map<String, Object> myOrder_cancel(HttpServletRequest request) {

		String orderno_s = webHelper.getString("orderno");

		if (orderno_s == null) {
			return webHelper.getJsonWarning("상품이 없습니다.");
		}

		String[] orderno_list_s = orderno_s.split(",");
		Order input = new Order();
		input.setMembno(null);

		for (int i = 0; i < orderno_list_s.length; i++) {
			input.setOrderno(Integer.parseInt(orderno_list_s[i]));
			try {
				orderService.deleteOrder(input);
			} catch (Exception e) {
				return webHelper.getJsonError(e.getLocalizedMessage());
			}
		}
		return webHelper.getJsonData();
	}
	
	/** 결제_완료 페이지 */
	@RequestMapping(value = "/myPay_ok", method = RequestMethod.POST)
	public Map<String, Object> myPay_ok(HttpServletRequest request) {

		/** 1) 필요한 변수값 생성 */
		// request 객체를 사용해서 세션 객체를 만들기
		HttpSession session = request.getSession();
		// session서버에 저장한 값을 mySession 변수에 담기
		int mySession = (int) session.getAttribute("sessionMembno");

		Item i_output = null;
		Order o_input = new Order();

		/** 현재시각 및 ordered_num **/
		Calendar cal = Calendar.getInstance();

		int yy = cal.get(Calendar.YEAR);
		int mm = cal.get(Calendar.MONTH) + 1;
		int dd = cal.get(Calendar.DAY_OF_MONTH);
		int hh = cal.get(Calendar.HOUR_OF_DAY);
		int mi = cal.get(Calendar.MINUTE);
		int ss = cal.get(Calendar.SECOND);

		String now_date = String.format("%d-%02d-%02d %02d:%02d:%02d", yy, mm, dd, hh, mi, ss);

		int random_num = (int) ((Math.random() * (999999 - 0 + 1)) + 0);

		String date = now_date.replace("-", "");
		date = date.replace(":", "");
		date = date.replace(" ", "");
		date = date.substring(2);
		String ordered_num = String.format(date + "%06d" + mySession, random_num);
		// 캘린더 끝

		/** 수령인 정보 **/
		String name = webHelper.getString("recipient");
		String mypc_postcode = webHelper.getString("order_postcode");
		String mypc_addr1 = webHelper.getString("order_addr1");
		String mypc_addr2 = webHelper.getString("order_addr2");
		String mypc_extraAddr = webHelper.getString("order_extraAddr");
		String[] tel_list = webHelper.getStringArray("mypc_number");
		String tel = null;
		
		/** 유효성 검사 **/
		if (tel_list != null) {
			tel = tel_list[0] + tel_list[1] + tel_list[2];
		} else {
			return webHelper.getJsonError("연락처를 입력해주세요.");
		}

		String addr1 = mypc_postcode + "," + mypc_addr1;
		String addr2 = mypc_addr2 + "," + mypc_extraAddr;

		if (name == null) {
			return webHelper.getJsonError("수령인을 입력해주세요.");
		}

		if (mypc_addr1 == null) {
			return webHelper.getJsonError("배송지를 입력해주세요.");
		} else if (mypc_addr2 == null) {
			return webHelper.getJsonError("상세주소를 입력해주세요.");
		}

		if (tel.length() != 11) {
			return webHelper.getJsonError("잘못된 형식의 연락처입니다.");
		} else {
			if (Pattern.matches("^[0-9]*$", "tel")) {
				return webHelper.getJsonError("연락처는 숫자만 가능합니다.");
			}
		}

		/** 상품 **/
		if (webHelper.getString("itemnum") != null) {
			int itemno = webHelper.getInt("itemnum");
			int coupno = webHelper.getInt("coupno");
			Coupon c_input = new Coupon();

			Item i_input = new Item();
			Integer countSold = 0;
			i_input.setItemno(itemno);

			/** 상품정보 불러오기 **/
			try {
				i_output = itemService.getItem(i_input);
			} catch (Exception e) {
				return webHelper.getJsonError(e.getLocalizedMessage());
			}

			/** 상품 수량 변경 및 상품 구매 개수 증가 **/
			int stock = i_output.getStock() - 1;
			
			if(i_output.getItem_sold()==null) {				// item_sold 값이 null인 경우
				countSold = 1;
			}else {
				countSold = i_output.getItem_sold() + 1;
			}
			i_input.setStock(stock);
			i_input.setItem_sold(countSold);
			
			try {
				itemService.editStock(i_input);
				itemService.editItemSold(i_input);
			} catch (Exception e) {
				return webHelper.getJsonError(e.getLocalizedMessage());
			}

			o_input.setOrdered_num(ordered_num);
			o_input.setOrdered_date(now_date);
			o_input.setSelected_count(1);
			o_input.setCategory(i_output.getCategory());
			o_input.setItem_name(i_output.getItem_name());
			o_input.setManufac(i_output.getManufac());
			o_input.setSpec(i_output.getSpec());
			o_input.setPrice(i_output.getPrice());
			o_input.setReg_date(i_output.getReg_date());
			o_input.setRel_date(i_output.getRel_date());
			o_input.setItem_img1(i_output.getItem_img1());
			o_input.setItem_img2(i_output.getItem_img2());
			o_input.setItem_imgthumb(i_output.getItem_imgthumb());
			o_input.setMembno(mySession);
			o_input.setName(name);
			o_input.setAddr1(addr1);
			o_input.setAddr2(addr2);
			o_input.setTel(tel);
			
			try {
				orderService.addOrder(o_input);
			} catch (Exception e) {
				return webHelper.getJsonError(e.getLocalizedMessage());
			}

			/** 쿠폰 사용여부 변경 **/
			c_input.setCoupno(coupno);
			c_input.setEnabled(o_input.getOrderno());

			try {
				couponService.editUseCoupon(c_input);
			} catch (Exception e) {
				return webHelper.getJsonError(e.getLocalizedMessage());
			}

		} else if (webHelper.getStringArray("cartnum") != null) {
			String cartno_list_s = webHelper.getString("cartnum");
			String coupno_noSplit = webHelper.getString("coupno");
			List<Integer> cartno = new ArrayList<Integer>();
			List<Integer> coupno = new ArrayList<Integer>();

			Cart cart_input = new Cart();
			Cart cart_output = null;
			Coupon coup_input = new Coupon();
			Item i_input = new Item();
			Integer countSold = 0;
			String[] cartno_s = cartno_list_s.split(",");
			String[] coupno_s = coupno_noSplit.split(",");

			/** 추가할 Order 수 만큼 반복 **/
			for (int i = 0; i < coupno_s.length; i++) {
				/** 카트넘버 형변환 **/
				cartno.add(Integer.parseInt(cartno_s[i]));
				cart_input.setCartno(cartno.get(i));

				/** 카트 정보 조회 **/
				try {
					cart_output = cartService.getCartnoItem(cart_input);
				} catch (Exception e) {
					return webHelper.getJsonError(e.getLocalizedMessage());
				}
				i_input.setItemno(cart_output.getItemno());

				/** 상품정보 조회 **/
				try {
					i_output = itemService.getItem(i_input);
				} catch (Exception e) {
					return webHelper.getJsonError(e.getLocalizedMessage());
				}

				/** 상품 수량 변경 및 상품 구매 개수 증가 **/
				if (i_output.getStock() - cart_output.getSelected_count() >= 0) {
					int stock = i_output.getStock() - cart_output.getSelected_count();
					
					if(i_output.getItem_sold()==null) {				// item_sold 값이 null인 경우
						countSold = cart_output.getSelected_count();
					}else {
						countSold = i_output.getItem_sold() + cart_output.getSelected_count();
					}
					
					i_input.setStock(stock);
					i_input.setItem_sold(countSold);
					try {
						itemService.editStock(i_input);
						itemService.editItemSold(i_input);
					} catch (Exception e1) {
						return webHelper.getJsonError(e1.getLocalizedMessage());
					}
				} else {
					return webHelper.getJsonError(cart_output.getItem_name() + "의 수량이 부족합니다. ");
				}
				coupno.add(Integer.parseInt(coupno_s[i]));

				/** 오더 정보 추가 **/
				o_input.setOrdered_num(ordered_num);
				o_input.setOrdered_date(now_date);
				o_input.setSelected_count(cart_output.getSelected_count());
				o_input.setCategory(i_output.getCategory());
				o_input.setItem_name(cart_output.getItem_name());
				o_input.setManufac(cart_output.getManufac());
				o_input.setSpec(cart_output.getSpec());
				o_input.setPrice(cart_output.getPrice());
				o_input.setReg_date(cart_output.getReg_date());
				o_input.setRel_date(cart_output.getRel_date());
				o_input.setItem_img1(cart_output.getItem_img1());
				o_input.setItem_img2(cart_output.getItem_img2());
				o_input.setItem_imgthumb(cart_output.getItem_imgthumb());
				o_input.setMembno(mySession);
				o_input.setName(name);
				o_input.setAddr1(addr1);
				o_input.setAddr2(addr2);
				o_input.setTel(tel);

				try {
					orderService.addOrder(o_input);
				} catch (Exception e) {
					return webHelper.getJsonError(e.getLocalizedMessage());
				}

				/** 쿠폰 사용여부 변경 **/
				if (coupno.get(i) != 0) {
					coup_input.setCoupno(coupno.get(i));
					coup_input.setEnabled(o_input.getOrderno());

					try {
						couponService.editUseCoupon(coup_input);
					} catch (Exception e) {
						return webHelper.getJsonError(e.getLocalizedMessage());
					}
				}

				/** 카트정보 삭제 **/
				try {
					cartService.deleteCart(cart_output);
				} catch (Exception e) {
					return webHelper.getJsonError(e.getLocalizedMessage());
				}

				/** 카트 아웃풋 초기화 **/
				cart_output = null;
			}

		} else if (webHelper.getString("itemnum") == null && webHelper.getStringArray("cartnum") == null) {
			return webHelper.getJsonWarning("잘못된 접근입니다.");
		}

		return webHelper.getJsonData();
	}
	
}
