package com.son.mypc.scheduler;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.son.mypc.helper.WebHelper;
import com.son.mypc.model.Coupon;
import com.son.mypc.service.CouponService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class Scheduler {

	@Autowired
	WebHelper webHelper;
	@Autowired
	CouponService couponService;
		
		public void everyMidNight() {
			
			List<Coupon> output = null;
			Coupon input = new Coupon();
			try {
				output = couponService.getAllList();
			} catch (Exception e) {
				return;
			}
			
			Calendar cal = Calendar.getInstance();
			
			int yy = cal.get(Calendar.YEAR);
			int mm = cal.get(Calendar.MONTH) +1;
			int dd = cal.get(Calendar.DAY_OF_MONTH);
			
			int now_date = Integer.parseInt(String.format("%d%02d%02d", yy,mm,dd));
			int expired_date = 0;
			String expired_date_s;
			System.out.println("현재시각"+now_date);
			
			for (int i = 0; i < output.size(); i++) {
				expired_date_s = output.get(i).getExpired_date().replace("-", "");
				expired_date = Integer.parseInt(expired_date_s.substring(0, 8));
				System.out.println(expired_date);
				if (now_date >= expired_date) {
					input.setCoupno(output.get(i).getCoupno());
					try {
						couponService.editExpiredCoupon(input);
						log.debug("쿠폰 수정에 성공했습니다.");
					} catch (Exception e) {
						return;
					}
				}
			}
		}
}
