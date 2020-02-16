package com.son.mypc.controllers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.son.mypc.helper.RegexHelper;
import com.son.mypc.helper.WebHelper;
import com.son.mypc.model.Coupon;
import com.son.mypc.model.Member;
import com.son.mypc.service.CouponService;
import com.son.mypc.service.MemberService;

@RestController
public class CouponController {

	@Autowired
	WebHelper webHelper;
	@Autowired
	RegexHelper regexHelper;
	
	@Autowired
	MemberService memberService;
	@Autowired
	CouponService couponService;
	
	/** 쿠폰 지급 */
	@RequestMapping(value = "couponSet", method = RequestMethod.POST)
	public Map<String, Object> couponSet() {

		String coupon_name = webHelper.getString("coupon_name");
		int coupon_off = webHelper.getInt("coupon_off");
		String expired_date = webHelper.getString("expired_date");
		String member_all = webHelper.getString("member_all");
		String user_id = webHelper.getString("user_id");
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar time = Calendar.getInstance();
		String issue_date = f.format(time.getTime());
		int enabled = 0;

		Coupon inputCoupon = new Coupon();
		inputCoupon.setCoupon_name(coupon_name);
		inputCoupon.setCoupon_off(coupon_off);
		inputCoupon.setEnabled(enabled);
		inputCoupon.setIssue_date(issue_date);
		inputCoupon.setExpired_date(expired_date);
		
		int outputCoupon = 0;
		
		if ("a".equals(member_all)) {

			Member inputMember = new Member();
			List<Member> outputMember = null;
			try {
				// 회원 번호 전체 조회
				outputMember = memberService.getMembnoList(inputMember);

				for (int i = 0; i < outputMember.size(); i++) {
					int membno = (int) outputMember.get(i).getMembno();
					inputCoupon.setMembno(membno);
					try {
						// 각 회원 번호에 쿠폰 저장
						outputCoupon = couponService.addCoupon(inputCoupon);
					} catch (Exception e) {
						return webHelper.getJsonError(e.getLocalizedMessage());
					}
				}
			} catch (Exception e) {
				return webHelper.getJsonError(e.getLocalizedMessage());
			}
		} else if (!"mypc".equals(user_id)) { // 전 회원 쿠폰 지급이 아니면, 입력된 아이디 값을 가진 회원에게 쿠폰 지급

			if (user_id == null) {
				return webHelper.getJsonWarning("아이디를 입력하세요.");
			}
			if (!regexHelper.isEngNum(user_id)) {
				return webHelper.getJsonWarning("아이디는 영어와 숫자로만 가능합니다.");
			}
			if (user_id.length() < 6) {
				return webHelper.getJsonWarning("아이디는 6글자 이상 입력하셔야 합니다.");
			}
			if (user_id.length() > 16) {
				return webHelper.getJsonWarning("아이디는 최대 16자까지 가능합니다.");
			}

			Member inputMember = new Member();
			inputMember.setUser_id(user_id);

			int outputMemberCount = 0;
			
			try {
				outputMemberCount = memberService.getMemberCount(inputMember);
			} catch (Exception e) {
				return webHelper.getJsonError(e.getLocalizedMessage());
			}

			if (outputMemberCount == 0) {
				return webHelper.getJsonWarning("존재하지 않는 아이디입니다.");
			} else {

				Member outputMember = new Member();
				try {
					outputMember = memberService.getIdCheck(inputMember);
				} catch (Exception e) {
					return webHelper.getJsonError(e.getLocalizedMessage());
				}
				
				int membno = (int) outputMember.getMembno();
				inputCoupon.setMembno(membno);
				try {
					// 쿠폰 저장
					outputCoupon = couponService.addCoupon(inputCoupon);
				} catch (Exception e) {
					return webHelper.getJsonError(e.getLocalizedMessage());
				}
			}
		} else {
			return webHelper.getJsonWarning("지급 가능한 회원이 아닙니다.");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", outputCoupon);
		return webHelper.getJsonData(map);
	}
	
	/** 쿠폰 삭제 */
	@RequestMapping(value = "/myCoupon_del", method = RequestMethod.PUT)
	public Map<String, Object> myCoupon_del(HttpServletRequest request) {

		Coupon input = new Coupon();
		int coupno = webHelper.getInt("coupno");
		input.setCoupno(coupno);

		try {
			couponService.deleteCoupon(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		return webHelper.getJsonData();
	}
	
}
