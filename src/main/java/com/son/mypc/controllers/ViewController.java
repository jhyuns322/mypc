package com.son.mypc.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.son.mypc.helper.DownloadHelper;
import com.son.mypc.helper.PageData;
import com.son.mypc.helper.RegexHelper;
import com.son.mypc.helper.WebHelper;
import com.son.mypc.model.Cart;
import com.son.mypc.model.Comment;
import com.son.mypc.model.Coupon;
import com.son.mypc.model.Document;
import com.son.mypc.model.Item;
import com.son.mypc.model.Member;
import com.son.mypc.model.Order;
import com.son.mypc.model.Pay;
import com.son.mypc.service.CartService;
import com.son.mypc.service.CommentService;
import com.son.mypc.service.CouponService;
import com.son.mypc.service.DocumentService;
import com.son.mypc.service.ItemService;
import com.son.mypc.service.MemberService;
import com.son.mypc.service.OrderService;
import com.son.mypc.service.PayService;
import com.google.gson.Gson;

@Controller
public class ViewController {

    @Autowired WebHelper webHelper;
    @Autowired RegexHelper regexHelper;
    @Autowired DownloadHelper downloadHelper;

	@Autowired CartService cartService;
	@Autowired CommentService commentService;
	@Autowired CouponService couponService;
	@Autowired DocumentService documentService;
    @Autowired ItemService itemService;
	@Autowired MemberService memberService;
	@Autowired OrderService orderService;
	@Autowired PayService payService;

	/* 사용자 */
    /* 1) 메인 페이지 */
	@RequestMapping(value = { "/", "index" })
	public ModelAndView index(Model model) {

		List<String> cookie = webHelper.getCookies("cookieItem");
		model.addAttribute("cookieValues", cookie);
		
		return new ModelAndView("index");
	}

	/** 2) 회원가입 페이지 */
	@RequestMapping(value = "join", method = RequestMethod.GET)
	public ModelAndView join(Model model) {

		List<String> cookie = webHelper.getCookies("cookieItem");
		model.addAttribute("cookieValues", cookie);
		
		return new ModelAndView("join");
	}	
	
	/** 3) 로그인 페이지 */
	@RequestMapping(value = "login")
	public ModelAndView login(Model model) {

		List<String> cookie = webHelper.getCookies("cookieItem");
		model.addAttribute("cookieValues", cookie);
		
		return new ModelAndView("login");
	}

	/** 4) 상품 목록 페이지 */
    @RequestMapping(value = "itemList", method = RequestMethod.GET)
    public ModelAndView itemList(Model model) {
        boolean showCategory = true;
        String keyword = webHelper.getString("keyword"); 				 // 상품 카테고리 파라미터 인식
        String search = webHelper.getString("search"); 					 // 상품 검색할 경우 검색어 파라미터 인식
        List<String> cookie = webHelper.getCookies("cookieItem"); 		 // 키 값에 cookieItem 이라는 단어가 포함되어 있는 쿠키들의 리스트
        String session = (String) webHelper.getSession("sessionUserId"); // 구매하기 버튼을 눌렀을 경우 유효성 검사를 위한 세션 전송
        
        // 카테고리 키워드나 검색어가 없을 경우
        if (keyword != null || search != null) {
            showCategory = true;
        } else {
            showCategory = false;
        }

        model.addAttribute("showCategory", showCategory);
        model.addAttribute("keyword", keyword);
        model.addAttribute("search", search);
        model.addAttribute("sessionUserId", session);
        model.addAttribute("cookieValues", cookie);
        
        return new ModelAndView("itemList");
    }

    /** 5) 상품 상세 페이지 */
    @RequestMapping(value = "itemView", method = RequestMethod.POST)
    public ModelAndView itemView(Model model) {
        // redirect 플러그인 사용, 상품의 모든 정보를 json 형식으로 받는다.
        String json = webHelper.getString("json");
        List<String> cookie = webHelper.getCookies("cookieItem"); // 키 값에 cookieItem 이라는 단어가 포함되어 있는 쿠키들의 리스트
        Gson gson = new Gson();
        Item item = new Item();
        item = gson.fromJson(json, Item.class);
        // 쿠키 저장을 위해 상품번호를 문자열로 변환
        String itemno = Integer.toString(item.getItemno());

        // 사용자가 본 상품을 쿠키에 저장 ex)'cookieItem상품번호'
        webHelper.setCookie("cookieItem" + itemno, itemno, 60 * 60);

        model.addAttribute("item", item);
        model.addAttribute("cookieValues", cookie);
        
        return new ModelAndView("itemView");
    }

    /** 6) 결제 페이지 */
	@RequestMapping(value = { "pay" }, method = {RequestMethod.POST,  RequestMethod.GET})
	public ModelAndView pay(Model model) {

		/** 1) 필요한 변수값 생성 */
		// request 객체를 사용해서 세션 객체를 만들기
		HttpSession session = webHelper.getSession();
		// session서버에 저장한 값을 mySession 변수에 담기
		int mySession1 = (int) session.getAttribute("sessionMembno");

		/** 받는 파라미터값에 따른 조회방법 설정 **/
		String cartno_list_S = webHelper.getString("cartnum");
		
		/** 장바구니 -> 결제 **/
		if(cartno_list_S != null) {
			String[] cartno_list = cartno_list_S.split(",");
			
			Integer cartno =0;
			Pay input =new Pay();
			List<Pay> output = new ArrayList<Pay>();
			
			for (int i=0; i < cartno_list.length; i++) {
				cartno = Integer.parseInt(cartno_list[i]);
				input.setCartno(cartno);
				
				try {
					output.add(payService.getCartItem(input));
				} catch (Exception e) {
					return webHelper.redirect(null, e.getLocalizedMessage());
				}
			}
			int total_price = 0;
			for(int i=0; i<output.size(); i++) {
				total_price += output.get(i).getPrice() * output.get(i).getSelected_count();
			}
			model.addAttribute("total_price", total_price);
			model.addAttribute("output", output);
		} else {
		
			Item input = new Item();
			List<Item> output = new ArrayList<Item>();
			int itemno = webHelper.getInt("itemno");

			input.setItemno(itemno);
			try {
				output.add(itemService.getItem(input));
			} catch (Exception e) {
				return webHelper.redirect(null, e.getLocalizedMessage());
			}
		
			int total_price = output.get(0).getPrice();
			model.addAttribute("item_count", 1);
			model.addAttribute("total_price", total_price);
			model.addAttribute("output", output);
		}
		
		Member input1 = new Member();
		Member output1 = null;
		input1.setMembno(mySession1);
		
		try {
			output1 = memberService.getMemberItem(input1);
		} catch (Exception e) {
			return webHelper.redirect(null, e.getLocalizedMessage());
		}
		
		
		/** 주소지가 null값이 아닐 경우에 대한 처리 **/
		if (!"".equals(output1.getAddr1()) && output1.getAddr1() != null) {
			String[] str = output1.getAddr1().split(",");
			
			String addr1_postcode = str[0];
			String addr1 = str[1];
			String addr2 = output1.getAddr2();
			String addr2_extraAddr = "";
			if (str[2] != null) {
				addr2_extraAddr = str[2];
			} 
			
			model.addAttribute("addr1_postcode", addr1_postcode);
			model.addAttribute("addr1", addr1);
			model.addAttribute("addr2", addr2);
			model.addAttribute("addr2_extraAddr", addr2_extraAddr);
		} 
		
		Coupon input_coup = new Coupon();
		List<Coupon> output_coup = null;
		input_coup.setMembno(mySession1);
		
		try {
			output_coup = couponService.getNotUseCouponList(input_coup);
		} catch (Exception e) {
			return webHelper.redirect(null, e.getLocalizedMessage());
		}
		
		List<String> cookieItem = webHelper.getCookies("cookieItem");
		model.addAttribute("cookieItem", cookieItem);
		
		model.addAttribute("cartnum", cartno_list_S);
		model.addAttribute("output_coup", output_coup);
		model.addAttribute("output1", output1);
		
		return new ModelAndView("pay");
	}   
    
	/** 7) 게시판 목록 페이지 */
	@RequestMapping(value = "boardMain", method = RequestMethod.GET)
	public ModelAndView boardMain(Model model) {

		/** 필요한 변수값 생성 */
		String keyword = webHelper.getString("keyword", ""); // 검색어
		String subKeyword = webHelper.getString("subKeyword", ""); // user_id 또는 subject 검색어
		List<String> cookie = webHelper.getCookies("cookieItem");
		int nowPage = webHelper.getInt("page", 1); // 페이지 번호 (기본값 1)
		int totalCount = 0; // 전체 게시글 수
		int listCount = 10; // 한 페이지당 표시할 목록 수
		int pageCount = 5; // 한 그룹당 표시할 페이지 번호 수

		String user_id = "";
		String subject = "";

		// subKeyword의 값에 따라 다른 변수에 Keyword 값을 저장
		if (subKeyword.equals("usr")) {
			user_id = keyword;
		} else if (subKeyword.equals("sub")) {
			subject = keyword;
		} else {
			// subKeyword가 공백(전체 검색)일 때 두 변수에 Keyword 값을 저장
			user_id = keyword;
			subject = keyword;
		}

		/** 데이터 조회하기 */
		// 조회에 필요한 조건값(검색어)를 Beans에 담는다.
		Document inputDocument = new Document();
		inputDocument.setUser_id(user_id);
		inputDocument.setSubject(subject);

		List<Document> output = null; // 조회결과가 저장될 객체
		PageData pageData = null; // 페이지 번호를 계산한 결과가 저장될 객체

		try {
			// 전체 게시글 수 조회
			totalCount = documentService.getBoardCount(inputDocument);
			// 페이지 번호 계산 -> 계산결과를 로그로 출력될 것이다.
			pageData = new PageData(nowPage, totalCount, listCount, pageCount);

			// SQL의 LIMIT절에서 사용될 값을 Beans의 static 변수에 저장
			Document.setOffset(pageData.getOffset());
			Document.setListCount(pageData.getListCount());

			// 데이터 조회하기
			output = documentService.getBoardList(inputDocument);
		} catch (Exception e) {
			return webHelper.redirect(null, e.getLocalizedMessage());
		}

		/** View 처리 */
		model.addAttribute("keyword", keyword);
		model.addAttribute("subKeyword", subKeyword);
		model.addAttribute("output", output);
		model.addAttribute("pageData", pageData);
		model.addAttribute("cookieValues", cookie);
		
		return new ModelAndView("boardMain");
	}

	/** 8) 게시글 확인 페이지 */
	@RequestMapping(value = "boardView", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView boardView(Model model) {

		List<String> cookie = webHelper.getCookies("cookieItem");
		// 세션 객체를 만들기
		HttpSession session = webHelper.getSession();

		int membno = 0;

		// session에 값이 있다면 변수에 값을 담고 없다면 0 (로그인 상태와 비로그인 상태를 구분하기 위함)
		if (session.getAttribute("sessionMembno") != null) {
			membno = (int) session.getAttribute("sessionMembno");
		}

		/** 필요한 변수값 생성 */
		// 조회할 대상에 대한 PK값
		int docno = webHelper.getInt("docno");

		// 이 값이 존재하지 않는다면 데이터 조회가 불가능하므로 반드시 필수값으로 처리해야 한다.
		if (docno == 0) {
			return webHelper.redirect(null, "게시글 번호가 없습니다.");
		}

		/** 데이터 조회하기 */
		// 데이터 조회에 필요한 조건값을 Beans에 저장하기
		Document input = new Document();
		input.setDocno(docno);
		Comment inputComm = new Comment();
		inputComm.setDocno(docno);

		// 조회결과를 저장할 객체 선언
		Document output = null;
		int outputCount = 0;
		List<Comment> outputList = null;

		try {
			// 데이터 조회
			output = documentService.getBoardView(input);
			outputCount = commentService.getCommentCount(inputComm);
			outputList = commentService.getCommentList(inputComm);
		} catch (Exception e) {
			return webHelper.redirect(null, e.getLocalizedMessage());
		}

		int hits = 0;
		if (membno != output.getMembno()) {

			// session의 membno와 DB에서 꺼내온 membno가 같지 않다면 조회수 증가 후 저장
			hits = output.getHits();
			hits++;
			input.setHits(hits);

			try {
				// 조회수 DB에 저장
				documentService.editBorardHits(input);
			} catch (Exception e) {
				return webHelper.redirect(null, e.getLocalizedMessage());
			}
		}

		model.addAttribute("outputCount", outputCount);
		model.addAttribute("outputList", outputList);
		model.addAttribute("output", output);
		model.addAttribute("cookieValues", cookie);
		
		return new ModelAndView("boardView");
	}

	/** 9) 게시글 작성 페이지 */
	@RequestMapping(value = "boardWrite", method = RequestMethod.GET)
	public ModelAndView boardWrite(Model model) {

		List<String> cookie = webHelper.getCookies("cookieItem");
		
		// boardView 페이지에서 접근 시 수정 페이지로 활용
		int docno = webHelper.getInt("docno");

		// docno에 값이 있다면 해당 게시글에 대한 정보를 조회
		if (docno != 0) {

			Document input = new Document();
			input.setDocno(docno);

			Document output = null;

			try {
				output = documentService.getBoardView(input);
			} catch (Exception e) {
				return webHelper.redirect(null, e.getLocalizedMessage());
			}

			model.addAttribute("output", output);
		}
		
		
		model.addAttribute("cookieValues", cookie);
		
		return new ModelAndView("boardWrite");
	}
   
	/** 10) 고객센터 페이지 */
	@RequestMapping(value = "support", method = RequestMethod.GET)
	public ModelAndView support(Model model) {

		List<String> cookie = webHelper.getCookies("cookieItem");
		model.addAttribute("cookieValues", cookie);
		
		return new ModelAndView("support");
	}   
    
	/** 11) 마이 페이지 */
	@RequestMapping(value = "myPage", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView myPage(Model model) {

		/** 1) 필요한 변수값 생성 */
		// request 객체를 사용해서 세션 객체를 만들기
		HttpSession session = webHelper.getSession();
		// session서버에 저장한 값을 mySession 변수에 담기
		int mySession1 = (int) session.getAttribute("sessionMembno");

		/** 2) 데이터 조회하기 */
		Cart input1 = new Cart();
		Coupon input2 = new Coupon();
		Order input3 = new Order();
		Member input4 = new Member();

		input1.setMembno(mySession1);
		input2.setMembno(mySession1);
		input3.setMembno(mySession1);
		input4.setMembno(mySession1);

		List<Cart> output1 = null;
		List<Coupon> output2 = null;
		List<Order> output3 = null;
		Member output4 = null;

		try {
			output1 = cartService.getCartList(input1);
		} catch (Exception e) {
			return webHelper.redirect(null, e.getLocalizedMessage());
		}

		try {
			output2 = couponService.getCouponList(input2);
		} catch (Exception e) {
			return webHelper.redirect(null, e.getLocalizedMessage());
		}

		try {
			output3 = orderService.getOrderList(input3);
		} catch (Exception e) {
			return webHelper.redirect(null, e.getLocalizedMessage());
		}

		try {
			output4 = memberService.getMemberItem(input4);
		} catch (Exception e) {
			return webHelper.redirect(null, e.getLocalizedMessage());
		}

		/** 주소지가 Null값이 아닐경우 */
		if (!"".equals(output4.getAddr1()) && output4.getAddr1() != null) {
			String[] str = output4.getAddr1().split(",");
			
			String addr1_postcode = str[0];
			String addr1 = str[1];
			String addr2 = output4.getAddr2();
			String addr2_extraAddr = "";
			if (str[2] != null) {
				addr2_extraAddr = str[2];
			} 
			
			model.addAttribute("addr1_postcode", addr1_postcode);
			model.addAttribute("addr1", addr1);
			model.addAttribute("addr2", addr2);
			model.addAttribute("addr2_extraAddr", addr2_extraAddr);
		} 

		model.addAttribute("output1", output1);
		model.addAttribute("output2", output2);
		model.addAttribute("output3", output3);
		model.addAttribute("output4", output4);
		
		List<String> cookieItem = webHelper.getCookies("cookieItem");
		model.addAttribute("cookieItem", cookieItem);

		/** 3) View 처리 */
		return new ModelAndView("myPage");
	}

	/** 12) 회원정보 수정 페이지 */
	@RequestMapping(value = { "myInfo" }, method = RequestMethod.GET)
	public ModelAndView myInfo(Model model) {

		/** 1) 필요한 변수값 생성 */
		// request 객체를 사용해서 세션 객체를 만들기
		HttpSession session = webHelper.getSession();
		// session서버에 저장한 값을 mySession 변수에 담기
		int mySession1 = (int) session.getAttribute("sessionMembno");

		Member input = new Member();

		input.setMembno(mySession1);

		Member output = null;

		try {
			output = memberService.getMemberItem(input);
		} catch (Exception e) {
			return webHelper.redirect(null, e.getLocalizedMessage());
		}
		if (!"".equals(output.getAddr1()) && output.getAddr1() != null) {
			String[] str = output.getAddr1().split(",");
			
			String addr1_postcode = str[0];
			String addr1 = str[1];
			String addr2 = output.getAddr2();
			String addr2_extraAddr = "";
			if (str[2] != null) {
				addr2_extraAddr = str[2];
			} 
			
			model.addAttribute("addr1_postcode", addr1_postcode);
			model.addAttribute("addr1", addr1);
			model.addAttribute("addr2", addr2);
			model.addAttribute("addr2_extraAddr", addr2_extraAddr);
		} 
		
		List<String> cookieItem = webHelper.getCookies("cookieItem");
		model.addAttribute("cookieItem", cookieItem);

		model.addAttribute("output", output);

		return new ModelAndView("myInfo");
	}

	/** 13) 장바구니 페이지 */
	@RequestMapping(value = { "myCart" }, method = RequestMethod.GET)
	public ModelAndView myCart(Model model) {

		/** 1) 필요한 변수값 생성 */
		// request 객체를 사용해서 세션 객체를 만들기
		HttpSession session = webHelper.getSession();
		// session서버에 저장한 값을 mySession 변수에 담기
		int mySession1 = (int) session.getAttribute("sessionMembno");

		Cart input = new Cart();
		input.setMembno(mySession1);

		List<Cart> output = null;

		try {
			output = cartService.getCartList(input);
		} catch (Exception e) {
			return webHelper.redirect(null, e.getLocalizedMessage());
		}
		
		/** 상품 개수에 따른 총 가격 **/
		int total_price = 0;
		for(int i=0; i<output.size(); i++) {
			total_price += output.get(i).getPrice() * output.get(i).getSelected_count();
		}
		
		List<String> cookieItem = webHelper.getCookies("cookieItem");
		model.addAttribute("cookieItem", cookieItem);

		model.addAttribute("total_price", total_price);
		model.addAttribute("output", output);

		return new ModelAndView("myCart");
	}

	/** 14) 주문관리 페이지 */
	@RequestMapping(value = { "myOrder" }, method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView myOrder(Model model) {

		/** 1) 필요한 변수값 생성 */
		// request 객체를 사용해서 세션 객체를 만들기
		HttpSession session = webHelper.getSession();
		// session서버에 저장한 값을 mySession 변수에 담기
		String mySession = (String) session.getAttribute("sessionUserId");
		int mySession1 = (int) session.getAttribute("sessionMembno");
		int select_month = webHelper.getInt("select_month");
		int select_year = webHelper.getInt("select_year");		
		
		String ordered_date =webHelper.getString("ordered_date");
		String ordered_date2 =webHelper.getString("ordered_date2");

		/** 필터링 기본값 설정 **/
		if (select_month == 0 && ordered_date == null && ordered_date2 == null) {
			select_month = 13;
		}
		
		/** 필터링에 사용될 날짜 **/
		Calendar cal = Calendar.getInstance();
		int mm = cal.get(Calendar.MONTH) + 1;
		int yy = cal.get(Calendar.YEAR);

		List<Integer> MM = new ArrayList<Integer>();
		List<Integer> YY = new ArrayList<Integer>();
		for (int i = 0; i < 5; i++) {
			mm -= i;
			if (mm < 1) {
				mm += 12;
			}
			MM.add(mm);
			mm = MM.get(0);
			YY.add(yy);
		}

		Order input = new Order();
		input.setMembno(mySession1);
		List<Order> output = null;
		
		/** 필터링 **/
		if(select_month == 13) {
			try {
				output = orderService.getOrderList(input);
			} catch (Exception e) {
				return webHelper.redirect(null, e.getLocalizedMessage());
			}
		}else if(select_month > 0) {
			String select_date = String.format("%4d-%02d", select_year,select_month);
			input.setOrdered_date(select_date);
			
			try {
				output = orderService.getOrderListByMonth(input);
				select_month = 0;
			} catch (Exception e) {
				return webHelper.redirect(null, e.getLocalizedMessage());
			}
		} else {
			input.setOrdered_date(ordered_date);
			input.setOrdered_date2(ordered_date2);
			try {
				output = orderService.getOrderListByDate(input);
			} catch (Exception e) {
				return webHelper.redirect(null, e.getLocalizedMessage());
			}
		}
		
		/** 쿠폰 데이터 조회 **/
		Coupon coup_input = new Coupon();
		List<Coupon> coup_output = null;
		
		coup_input.setMembno(mySession1);
		
		try {
			coup_output = couponService.getUseCouponList(coup_input);
		} catch (Exception e) {
			return webHelper.redirect(null, e.getLocalizedMessage());
		}
		int coup_len = 0;
		
		/** 쿠폰이 Null값일시 **/
		if(coup_output == null) {
			model.addAttribute("coup_len", coup_len);
		} else {
			coup_len = coup_output.size();
			model.addAttribute("coup_output", coup_output);
			model.addAttribute("coup_len", coup_len);
		}

		/** JSP 본문에 사용될 output의 size **/
		int length = output.size();
		select_month = 0;
		
		List<String> cookieItem = webHelper.getCookies("cookieItem");
		model.addAttribute("cookieItem", cookieItem);

		model.addAttribute("YY", YY);
		model.addAttribute("MM", MM);
		model.addAttribute("length", length);
		model.addAttribute("output", output);
		model.addAttribute("mySession", mySession);

		return new ModelAndView("myOrder");
	} 	
	
	/** 15) 쿠폰관리 페이지 */
	@RequestMapping(value = { "myCoupon" }, method = RequestMethod.GET)
	public ModelAndView myCoupon(Model model) {

		/** 1) 필요한 변수값 생성 */
		// request 객체를 사용해서 세션 객체를 만들기
		HttpSession session = webHelper.getSession();
		// session서버에 저장한 값을 mySession 변수에 담기
		int mySession1 = (int) session.getAttribute("sessionMembno");

		/** 2) 데이터 조회하기 */
		// 조회에 필요한 조건값(검색어)를 Beans에 담는다.
		Coupon input = new Coupon();
		input.setMembno(mySession1);

		List<Coupon> output = null; // 조회결과가 저장될 객체

		try {
			// 데이터 조회하기
			output = couponService.getCouponList(input);
		} catch (Exception e) {
			return webHelper.redirect(null, e.getLocalizedMessage());
		}
		
		/** 아웃풋이 널이 아닐경우 **/
		int empty = 0;
		if(output !=null) {
			empty = output.size();
		}
		
		List<String> cookieItem = webHelper.getCookies("cookieItem");
		model.addAttribute("cookieItem", cookieItem);

		/** 3) View 처리 */
		model.addAttribute("output", output);
		model.addAttribute("empty_b", empty);

		return new ModelAndView("myCoupon");
	}

	/* 관리자 */
	/** 16) 관리자 로그인 페이지 */
	@RequestMapping(value = "adminLogin", method = RequestMethod.GET)
	public ModelAndView adminLogin(Model model) {

		return new ModelAndView("adminLogin");
	}

	/** 17) 관리자 메인 페이지 */
	@RequestMapping(value = "adminIndex")
	public ModelAndView adminIndex(Model model) {

		String replied = "n";

		Member inputMember = new Member();
		Document inputReplied = new Document();
		inputReplied.setReplied(replied);

		int outputMember = 0;
		int outputReplied = 0;

		try {
			outputMember = memberService.getMemberCount(inputMember);
			outputReplied = documentService.getInquiryCount(inputReplied);
		} catch (Exception e) {
			return webHelper.redirect(null, e.getLocalizedMessage());
		}

		model.addAttribute("outputMember", outputMember);
		model.addAttribute("outputReplied", outputReplied);
		
		return new ModelAndView("adminIndex");
	}

	/** 18) 회원관리 목록 페이지 */
	@RequestMapping(value = "adminMember", method = RequestMethod.GET)
	public ModelAndView adminMember(Model model) {

		/** 필요한 변수값 생성 */
		String keyword = webHelper.getString("keyword", ""); // 검색어
		String gender = webHelper.getString("subKeyword", ""); // 성별 검색어
		int nowPage = webHelper.getInt("page", 1); // 페이지 번호 (기본값 1)
		int totalCount = 0; // 전체 게시글 수
		int listCount = 10; // 한 페이지당 표시할 목록 수
		int pageCount = 5; // 한 그룹당 표시할 페이지 번호 수

		String user_id = "";
		String name = "";
		if (regexHelper.isEngNum(keyword)) {
			user_id = keyword;
		} else if (regexHelper.isKor(keyword)) {
			name = keyword;
		}

		/** 데이터 조회하기 */
		// 조회에 필요한 조건값(검색어)를 Beans에 담는다.
		Member input = new Member();
		input.setUser_id(user_id);
		input.setName(name);
		input.setGender(gender);

		List<Member> output = null; // 조회결과가 저장될 객체
		PageData pageData = null; // 페이지 번호를 계산한 결과가 저장될 객체

		try {
			// 전체 게시글 수 조회
			totalCount = memberService.getMemberCount(input);
			// 페이지 번호 계산 > 계산결과를 로그로 출력
			pageData = new PageData(nowPage, totalCount, listCount, pageCount);

			// SQL의 LIMIT절에서 사용될 값을 Beans의 static 변수에 저장
			Member.setOffset(pageData.getOffset());
			Member.setListCount(pageData.getListCount());

			// 데이터 조회하기
			output = memberService.getMemberList(input);
		} catch (Exception e) {
			return webHelper.redirect(null, e.getLocalizedMessage());
		}

		/** View 처리 */
		model.addAttribute("keyword", keyword);
		model.addAttribute("gender", gender);
		model.addAttribute("output", output);
		model.addAttribute("pageData", pageData);
		
		return new ModelAndView("adminMember");
	}

	/** 19) 회원관리 정보 페이지 */
	@RequestMapping(value = "adminMemberInfo", method = RequestMethod.GET)
	public ModelAndView adminMemberInfo(Model model) {

		int membno = webHelper.getInt("membno");

		if (membno == 0) {
			return webHelper.redirect(null, "회원 정보가 없습니다.");
		}

		Member input = new Member();
		input.setMembno(membno);

		Member output = null;

		try {
			output = memberService.getMemberItem(input);
		} catch (Exception e) {
			return webHelper.redirect(null, e.getLocalizedMessage());
		}

		// 주소 양식 변환 (addr1 = addrPost, addrRoad, addrDetail)
		if (!"".equals(output.getAddr1()) && output.getAddr1() != null) {

			String[] temp = output.getAddr1().split(",");

			String addrPost = temp[0];
			String addrRoad = temp[1];
			String addrDetail = "";
			if (temp[2] != null) {
				addrDetail = temp[2];
			}

			model.addAttribute("addrPost", addrPost);
			model.addAttribute("addrRoad", addrRoad);
			model.addAttribute("addrDetail", addrDetail);
		}

		model.addAttribute("output", output);
		return new ModelAndView("adminMemberInfo");
	}

	/** 20) 문의글 목록 페이지 */
	@RequestMapping(value = "adminInquiry", method = RequestMethod.GET)
	public ModelAndView adminInquiry(Model model) {

		/** 필요한 변수값 생성 */
		String keyword = webHelper.getString("keyword", ""); // 검색어
		String subKeyword = webHelper.getString("subKeyword", ""); // 답장 여부
		int nowPage = webHelper.getInt("page", 1); // 페이지 번호 (기본값 1)
		int totalCount = 0; // 전체 게시글 수
		int listCount = 10; // 한 페이지당 표시할 목록 수
		int pageCount = 5; // 한 그룹당 표시할 페이지 번호 수

		/** 데이터 조회하기 */
		// 조회에 필요한 조건값(검색어)를 Beans에 담는다.
		Document input = new Document();
		input.setUser_id(keyword);
		input.setReplied(subKeyword);

		List<Document> output = null; // 조회결과가 저장될 객체
		PageData pageData = null; // 페이지 번호를 계산한 결과가 저장될 객체

		try {
			// 전체 게시글 수 조회
			totalCount = documentService.getInquiryCount(input);
			// 페이지 번호 계산 -> 계산결과를 로그로 출력될 것이다.
			pageData = new PageData(nowPage, totalCount, listCount, pageCount);

			// SQL의 LIMIT절에서 사용될 값을 Beans의 static 변수에 저장
			Document.setOffset(pageData.getOffset());
			Document.setListCount(pageData.getListCount());

			// 데이터 조회하기
			output = documentService.getInquiryList(input);
		} catch (Exception e) {
			return webHelper.redirect(null, e.getLocalizedMessage());
		}

		System.out.println(output);
		/** View 처리 */
		model.addAttribute("keyword", keyword);
		model.addAttribute("subKeyword", subKeyword);
		model.addAttribute("output", output);
		model.addAttribute("pageData", pageData);

		return new ModelAndView("adminInquiry");
	}

	/** 21) 문의글 확인 페이지 */
	@RequestMapping(value = "adminInquiryView", method = RequestMethod.GET)
	public ModelAndView adminInguiryView(Model model) {

		/** 필요한 변수값 생성 */
		// 조회할 대상에 대한 PK값
		int docno = webHelper.getInt("docno");

		// 이 값이 존재하지 않는다면 데이터 조회가 불가능하므로 반드시 필수값으로 처리.
		if (docno == 0) {
			return webHelper.redirect(null, "게시글 번호가 없습니다.");
		}

		/** 데이터 조회하기 */
		// 데이터 조회에 필요한 조건값을 Beans에 저장하기
		Document input = new Document();
		input.setDocno(docno);

		// 조회결과를 저장할 객체 선언
		Document output = null;

		try {
			// 데이터 조회
			output = documentService.getInquiryView(input);
		} catch (Exception e) {
			return webHelper.redirect(null, e.getLocalizedMessage());
		}

		/** View 처리 */
		model.addAttribute("output", output);

		return new ModelAndView("adminInquiryView");
	}
	
	/** 22) 상품관리 페이지 */
    @RequestMapping(value = "adminItem", method = RequestMethod.GET)
    public ModelAndView adminItem(Model model) {
        /** 필요한 변수값 생성 */
        String keyword = webHelper.getString("keyword", ""); // 검색어
        String subKeyword = webHelper.getString("subKeyword", ""); // 검색 옵션
        int nowPage = webHelper.getInt("page", 1); // 페이지 번호 (기본값 1)
        int totalCount = 0; // 전체 게시글 수
        int listCount = 10; // 한 페이지당 표시할 목록 수
        int pageCount = 5; // 한 그룹당 표시할 페이지 번호 수

        /** 데이터 조회하기 */
        // 조회에 필요한 조건값(검색어)를 Beans에 담는다.

        // subKeyword의 값에 따라 다른 멤버변수에 값을 저장
        Item input = new Item();
        if (subKeyword.equals("category")) {
            input.setCategory(keyword);
        } else if (subKeyword.equals("manufac")) {
            input.setManufac(keyword);
        } else if (subKeyword.equals("item_name")) {
            input.setItem_name(keyword);
        } else {
            // subKeyword가 공백(전체 검색)일 때 두 변수에 Keyword 값을 저장
            input.setCategory(keyword);
            input.setManufac(keyword);
            input.setItem_name(keyword);
        }
        input.setItem_alignment("0");
        List<Item> output = null; // 조회결과가 저장될 객체
        PageData pageData = null; // 페이지 번호를 계산한 결과가 저장될 객체

        try {
            // 전체 게시글 수 조회
            totalCount = itemService.getItemCount(input);
            // 페이지 번호 계산 -> 계산결과를 로그로 출력
            pageData = new PageData(nowPage, totalCount, listCount, pageCount);

            // SQL의 LIMIT절에서 사용될 값을 Beans의 static 변수에 저장
            Item.setOffset(pageData.getOffset());
            Item.setListCount(pageData.getListCount());

            // 데이터 조회하기
            output = itemService.getItemList(input);
        } catch (Exception e) {
            return webHelper.redirect(null, e.getLocalizedMessage());
        }

        /** View 처리 */
        model.addAttribute("keyword", keyword);
        model.addAttribute("subKeyword", subKeyword);
        model.addAttribute("output", output);
        model.addAttribute("pageData", pageData);

        return new ModelAndView("adminItem");
    }

    /** 23) 상품 등록/수정 페이지 */
    @RequestMapping(value = "adminItemRegist")
    public ModelAndView adminItemRegist(Model model) {
        // itemno 값이 존재하지 않을 경우 -1을 부여
        int itemno = webHelper.getInt("itemno", -1);

        // itemno 값이 -1이 아니라면 해당 itemno를 view에서 활용하여 RestcController에서 Item 데이터를 조회한다.
        if (itemno != -1) {
            model.addAttribute("itemno", itemno);
        }
        return new ModelAndView("adminItemRegist");
    }

    /** 24) 통계 페이지 */
    @RequestMapping(value = "adminStats", method = RequestMethod.GET)
    public String adminStats(Model model) {

        return "adminStats";
    }

    /** 기타) 이미지 다운로드 */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ModelAndView download(Model model, @RequestParam(value = "file") String filePath, @RequestParam(value = "origin", required = false) String originName, @RequestParam(value = "size", required = false) String size,
            @RequestParam(value = "crop", defaultValue = "false") String crop) {
        // 서버상에 저장되어 있는 파일 경로 (필수)
        // String filePath = webHelper.getString("file");
        // 원본 파일이름 (미필수)
        // String originName = webHelper.getString("origin");
        // 축소될 이미지 해상도 --> 320x320
        // String size = webHelper.getString("size");
        // 이미지 크롭 여부 --> 값이 없을 경우 기본값 false
        // String crop = webHelper.getString("crop", "false");

        /** 다운로드 스트림 출력하기 */
        if (filePath != null) {
            try {
                // 썸네일 생성을 위해 축소될 이미지의 사이즈가 요청되었다면?
                if (size != null) {
                    // x를 기준으로 나눠서 숫자로 변환
                    String[] temp = size.split("x");
                    int width = Integer.parseInt(temp[0]);
                    int height = Integer.parseInt(temp[1]);

                    // 모든 파라미터는 문자열이므로 크롭 여부를 boolean으로 재설정
                    boolean is_crop = false;
                    if (crop.equals("true")) {
                        is_crop = true;
                    }

                    // 썸네일 생성 후 다운로드 처리
                    downloadHelper.download(filePath, width, height, is_crop);
                } else {
                    // 원본에 대한 다운로드 처리
                    downloadHelper.download(filePath, originName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // View를 사용하지 않고 FileStream을 출력하므로 리턴값은 없다.
        return null;
    }
    
}
