package com.son.mypc.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.son.mypc.helper.WebHelper;
import com.son.mypc.model.Item;
import com.son.mypc.model.Order;
import com.son.mypc.service.ItemService;
import com.son.mypc.service.OrderService;

@RestController
public class StatsController {

	@Autowired
	WebHelper webHelper;
	
	@Autowired
	ItemService itemService;
	@Autowired
	OrderService orderService;
    @RequestMapping(value = "/adminStatsGet/{chartno}", method = RequestMethod.GET)
	public Map<String, Object> adminStats(@PathVariable int chartno) {
		Map<String, Object> data = new HashMap<String, Object>();
		String startDate = webHelper.getString("stDate");
		String endDate = webHelper.getString("edDate");

		/** 1) 데이터 조회하기 */
		switch (chartno) {
		case 1:
			// 조회에 필요한 조건값(검색어)를 Beans에 담는다.
			Order inputOrder = new Order();
			List<Order> outputOrder = null; // 조회결과가 저장될 객체

			try {
				// 데이터 조회하기
				outputOrder = orderService.getOrderListByStats1(inputOrder);
			} catch (Exception e) {
				return webHelper.getJsonError(e.getLocalizedMessage());
			}
			data.put("item", outputOrder);
			break;
		case 2:
			// 조회에 필요한 조건값(검색어)를 Beans에 담는다.
			Item inputItem = new Item();
			List<Item> outputItem = null; // 조회결과가 저장될 객체

			try {
				// 데이터 조회하기
				outputItem = itemService.getItemListByStats(inputItem);
			} catch (Exception e) {
				return webHelper.getJsonError(e.getLocalizedMessage());
			}
			data.put("item", outputItem);
			break;
		case 3:
			// 조회에 필요한 조건값(검색어)를 Beans에 담는다.
			Order inputOrder2 = new Order();
			List<Order> outputOrder2 = null; // 조회결과가 저장될 객체

			if (startDate != null || startDate != "") {
				inputOrder2.setStartDate(startDate);
			}
			if (endDate != null || endDate != "") {
				inputOrder2.setEndDate(endDate);
			}

			try {
				// 데이터 조회하기
				outputOrder2 = orderService.getOrderListByStats2(inputOrder2);
			} catch (Exception e) {
				return webHelper.getJsonError(e.getLocalizedMessage());
			}
			data.put("item", outputOrder2);
			break;
		}

		data.put("chartno", chartno);
		/** 2) JSON 출력하기 */
		return webHelper.getJsonData(data);
	}
    
}
