package com.son.mypc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.son.mypc.helper.WebHelper;

@Controller
public class HomeController {

	@Autowired
	WebHelper webHelper;

	/** 1) 메인 페이지(사용자) */
	@RequestMapping(value = { "/", "index" })
	public ModelAndView index(Model model) {

		return new ModelAndView("index");
	}

}
