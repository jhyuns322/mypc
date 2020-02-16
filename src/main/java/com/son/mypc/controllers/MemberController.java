package com.son.mypc.controllers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.son.mypc.helper.MailHelper;
import com.son.mypc.helper.RegexHelper;
import com.son.mypc.helper.WebHelper;
import com.son.mypc.model.Coupon;
import com.son.mypc.model.Member;
import com.son.mypc.service.CouponService;
import com.son.mypc.service.MemberService;

@RestController
public class MemberController {

	@Autowired
	WebHelper webHelper;
	@Autowired
	RegexHelper regexHelper;
	@Autowired
	MailHelper mailHelper;
	
	@Autowired
	MemberService memberService;
	@Autowired
	CouponService couponService;
	
	/** 회원가입 */
	@RequestMapping(value = "joinPost", method = RequestMethod.POST)
	public Map<String, Object> joinPost() {

		HttpSession session = webHelper.getSession();

		// 중복검사 확인 여부 검사를 위한 값
		String tempId = (String) session.getAttribute("ckdUserId");
		String tempEmail = (String) session.getAttribute("ckdEmail");

		/** 사용자가 입력한 파라미터 수신 및 유효성 검사 */
		String user_id = webHelper.getString("user_id");
		String user_pw = webHelper.getString("user_pw");
		String name = webHelper.getString("name");
		String birthdate = webHelper.getString("birthdate");
		String gender = webHelper.getString("gender");
		String email = webHelper.getString("email");
		String addrPost = webHelper.getString("addrPost");	 // null 허용
		String addrRoad = webHelper.getString("addrRoad");   // null 허용
		String addr2 = webHelper.getString("addrDetail");    // null 허용
		String addrExtra = webHelper.getString("addrExtra"); // null 허용
		String tel = webHelper.getString("tel"); 			 // null 허용

		if (user_id == null) {
			return webHelper.getJsonWarning("아이디를 입력하세요.");
		}
		if (!user_id.equals(tempId)) {
			return webHelper.getJsonWarning("아이디 중복검사가 필요합니다.");
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
		if (name == null) {
			return webHelper.getJsonWarning("이름을 입력하세요.");
		}
		if (!regexHelper.isKor(name)) {
			return webHelper.getJsonWarning("이름은 한글로만 가능합니다.");
		}
		if (birthdate == null) {
			return webHelper.getJsonWarning("생년월일을 입력하세요.");
		}
		if (gender == null) {
			return webHelper.getJsonWarning("성별을 선택하세요.");
		}
		if (email == null) {
			return webHelper.getJsonWarning("이메일을 입력하세요.");
		}
		if (!regexHelper.isEmail(email)) {
			return webHelper.getJsonWarning("이메일 형식이 잘못되었습니다.");
		}
		if (!email.equals(tempEmail)) {
			return webHelper.getJsonWarning("이메일 중복검사가 필요합니다.");
		}
		
		// 주소 양식 변환
		String addr1 = "";
		if (addrPost != null) {
			addr1 = addrPost + "," + addrRoad + "," + addrExtra;
		}

		// 현재 날짜 불러오기.
		SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd");
		Calendar time = Calendar.getInstance();
		String reg_date = f1.format(time.getTime());

		// 세션 삭제.
		webHelper.removeAllSession();

		Member inputMember = new Member();
		inputMember.setUser_id(user_id);
		inputMember.setUser_pw(user_pw);
		inputMember.setName(name);
		inputMember.setBirthdate(birthdate);
		inputMember.setGender(gender);
		inputMember.setEmail(email);
		inputMember.setAddr1(addr1);
		inputMember.setAddr2(addr2);
		inputMember.setTel(tel);
		inputMember.setReg_date(reg_date);

		Member outputMember = null;

		try {
			// 입력값 저장 후 저장된 회원의 membno 불러오기.
			memberService.addMember(inputMember);
			outputMember = memberService.getIdCheck(inputMember);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		/** 신규 회원 쿠폰 발급 */
		String coupon_name = "MYPC 신규가입 기념 쿠폰";
		int coupon_off = 10;
		int enabled = 0;
		String issue_date = reg_date;
		time.add(Calendar.DAY_OF_MONTH, +7); // 현재 날짜에 + 7 (일주일)
		String expired_date = f2.format(time.getTime());

		Coupon inputCoupon = new Coupon();
		int membno = (int) outputMember.getMembno();
		inputCoupon.setMembno(membno);
		inputCoupon.setCoupon_name(coupon_name);
		inputCoupon.setCoupon_off(coupon_off);
		inputCoupon.setEnabled(enabled);
		inputCoupon.setIssue_date(issue_date);
		inputCoupon.setExpired_date(expired_date);

		int outputCoupon = 0;

		try {
			// 쿠폰 저장
			outputCoupon = couponService.addCoupon(inputCoupon);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", outputCoupon);
		return webHelper.getJsonData(map);
	}
	
	/** 회원가입 시 아이디 중복 검사 */
	@RequestMapping(value = "userIdCheck", method = RequestMethod.GET)
	public Map<String, Object> userIdCheck() {

		HttpSession session = webHelper.getSession();

		String user_id = webHelper.getString("userId");

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

		Member input = new Member();
		input.setUser_id(user_id);

		int output = 0;

		try {
			// Beans에 저장한 값을 파라미터로 전달 한 뒤 쿼리문을 통해 원하는 값을 불러와 output에 값 담기
			output = memberService.getMemberCount(input);
			
			// 조회 결과가 없다면(=중복 아이디가 없다면) 세션에 중복검사 여부 확인을 위한 값을 저장
			if (output == 0) {
				session.setAttribute("ckdUserId", user_id);
			}
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", output);
		return webHelper.getJsonData(map);
	}
	
	/** 회원가입 시 이메일 중복 검사 */
	@RequestMapping(value = "emailCheck", method = RequestMethod.GET)
	public Map<String, Object> emailCheck() {

		HttpSession session = webHelper.getSession();

		String email = webHelper.getString("email");

		if (email == null) {
			return webHelper.getJsonWarning("이메일을 입력하세요.");
		}
		if (!regexHelper.isEmail(email)) {
			return webHelper.getJsonWarning("이메일 형식이 잘못되었습니다.");
		}

		Member input = new Member();
		input.setEmail(email);

		int output = 0;

		try {
			// Beans에 저장한 값을 파라미터로 전달 한 뒤 쿼리문을 통해 원하는 값을 불러와 output에 값 담기
			output = memberService.getMemberCount(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		// 조회 결과가 없다면(=중복 이메일이 없다면) 세션에 중복검사 여부 확인을 위한 값을 저장
		if (output == 0) {
			session.setAttribute("ckdEmail", email);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", output);
		return webHelper.getJsonData(map);
	}
	
	/** 로그인 */
	@RequestMapping(value = "sessionSave", method = RequestMethod.POST)
	public Map<String, Object> sessionSave() {

		HttpSession session = webHelper.getSession();

		// login 페이지 form 입력값 받기
		String user_id = webHelper.getString("user_id");
		String user_pw = webHelper.getString("user_pw");

		// 필수 값의 존재여부 검사
		if (user_id == null || user_id.equals("")) {
			return webHelper.getJsonWarning("아이디를 입력하세요.");
		}

		if (user_pw == null || user_pw.equals("")) {
			return webHelper.getJsonWarning("비밀번호를 입력하세요.");
		}

		// Member객체에 입력값 저장
		Member input = new Member();
		input.setUser_id(user_id);

		// 불러올 데이터를 담을 변수 생성
		Member output = null;

		try {
			// Beans에 저장한 값을 파라미터로 전달 한 뒤 쿼리문을 통해 원하는 값을 불러와 output에 값 담기
			output = memberService.getIdCheck(input);
		} catch (Exception e) {
			return webHelper.getJsonWarning(e.getLocalizedMessage());
		}

		// 입력값과 output에 담겨있는 값의 일치 여부 검사
		if (user_id.equals(output.getUser_id()) && user_pw.equals(output.getUser_pw())) {
			// session에 아이디와 회원번호 저장
			session.setAttribute("sessionUserId", output.getUser_id());
			session.setAttribute("sessionName", output.getName());
			session.setAttribute("sessionMembno", output.getMembno());
		} else {
			return webHelper.getJsonWarning("아이디 또는 비밀번호가 일치하지 않습니다.");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", output);
		return webHelper.getJsonData(map);
	}
	
	/** 아이디 찾기  */
	@RequestMapping(value = "findUserId", method = RequestMethod.POST)
	public Map<String, Object> findUserId() {

		String email = webHelper.getString("email");

		if (email == null) {
			return webHelper.getJsonWarning("이메일을 입력하세요.");
		}
		if (!regexHelper.isEmail(email)) {
			return webHelper.getJsonWarning("이메일 형식이 잘못되었습니다.");
		}

		Member input = new Member();
		input.setEmail(email);

		Member output = null;

		try {
			// Beans에 저장한 값을 파라미터로 전달 한 뒤 쿼리문을 통해 원하는 값을 불러와 output에 값 담기
			output = memberService.getEmailCheck(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		if (output != null) {

			String userId = output.getUser_id();
			String name = output.getName();
			String subject = "마이피씨(MYPC)에서 " + name + " 회원님의 아이디를 알려드립니다.";
			String mailContent = mailHelper.mailFindIdContent(userId, name);

			/** 메일 발송 처리 */
			try {
				// sendMail() 메서드 선언 시 throws를 정의했기 때문에 예외처리가 요구
				mailHelper.sendMail(email, subject, mailContent);
			} catch (Exception e) {
				return webHelper.getJsonError(e.getLocalizedMessage());
			}
		} else {
			return webHelper.getJsonWarning("등록되지 않은 이메일 입니다.");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", output);
		return webHelper.getJsonData(map);
	}
	
	/** 비밀번호 찾기(아이디 검사) */
	@RequestMapping(value = "findPwCheckId", method = RequestMethod.POST)
	public Map<String, Object> findPwCheckId() {

		HttpSession session = webHelper.getSession();

		String user_id = webHelper.getString("user_id");

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

		Member input = new Member();
		input.setUser_id(user_id);

		Member output = null;

		try {
			// Beans에 저장한 값을 파라미터로 전달 한 뒤 쿼리문을 통해 원하는 값을 불러와 output에 값 담기
			output = memberService.getIdCheck(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		if (output != null) {

			// findPwSendEmail Controller에서 사용할 값을 세션에 저장
			session.setAttribute("sessionMembno", output.getMembno());
			session.setAttribute("sessionEmail", output.getEmail());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("item", output);
			return webHelper.getJsonData(map);
		} else {
			return webHelper.getJsonWarning("등록되지 않은 아이디 입니다.");
		}
	}
	
	/** 7) 비밀번호 찾기(아이디와 이메일 일치 여부 검사 후 임시비밀번호 이메일 발송) */
	@RequestMapping(value = "findPwSendEmail", method = RequestMethod.POST)
	public Map<String, Object> findPwSendEmail() {

		HttpSession session = webHelper.getSession();

		String email = webHelper.getString("email");

		String sessionEmail = "";
		int sessionMembno = 0;

		try {
			sessionMembno = (int) session.getAttribute("sessionMembno");
			sessionEmail = (String) session.getAttribute("sessionEmail");
		} catch (Exception e) {
			return webHelper.getJsonWarning("등록되지 않은 이메일입니다.");
		}

		if (email == null) {
			return webHelper.getJsonWarning("이메일을 입력하세요.");
		}
		if (!regexHelper.isEmail(email)) {
			return webHelper.getJsonWarning("이메일 형식이 잘못되었습니다.");
		}

		if (!email.equals(sessionEmail)) {
			webHelper.removeAllSession();
			return webHelper.getJsonWarning("등록된 이메일과 일치하지 않습니다.");
		}

		// 임시 비밀번호로 저장 및 전송할 값 생성
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		String user_pw = uuid.substring(0, 6);

		Member input = new Member();
		input.setMembno(sessionMembno);
		input.setUser_pw(user_pw);

		Member output = null;

		try {
			memberService.editUserPw(input);
			output = memberService.getMemberItem(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}
		// 사용한 세션 초기화
		webHelper.removeAllSession();

		String name = output.getName();

		String subject = "마이피씨(MYPC)에서 " + name + " 회원님의 임시비밀번호를 알려드립니다.";
		String mailContent = mailHelper.mailFindPwContent(user_pw, name);

		/** 메일 발송 처리 */
		try {
			// sendMail() 메서드 선언 시 throws를 정의했기 때문에 예외처리가 요구
			mailHelper.sendMail(email, subject, mailContent);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", output);
		return webHelper.getJsonData(map);
	}

	/** 로그인(관리자) */
	@RequestMapping(value = "sessionSaveAdmin", method = RequestMethod.POST)
	public Map<String, Object> sessionSaveAdmin() {

		// 세션 객체를 만들기
		HttpSession session = webHelper.getSession();

		// login 페이지에서 넘어온 입력값
		String user_id = webHelper.getString("user_id");
		String user_pw = webHelper.getString("user_pw");

		// 필수 값의 존재여부 검사
		if (user_id == null || user_id.equals("")) {
			return webHelper.getJsonError("아이디를 입력하세요.");
		}

		if (user_pw == null || user_pw.equals("")) {
			return webHelper.getJsonError("비밀번호를 입력하세요.");
		}

		// user_id가 'mypc'일 때만 로그인이 가능
		if (!user_id.equals("mypc")) {
			return webHelper.getJsonError("관리자 계정으로 로그인이 가능합니다.");
		}

		// Member객체에 입력값 저장
		Member input = new Member();
		input.setUser_id(user_id);

		// 불러올 데이터를 담을 변수 생성
		Member output = null;

		try {
			// Beans에 저장한 값을 파라미터로 전달 한 뒤 쿼리문을 통해 원하는 값을 불러와 output에 값 담기
			output = memberService.getIdCheck(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		// 입력값과 output에 담겨있는 값의 일치 여부 검사
		if (user_id.equals(output.getUser_id()) && user_pw.equals(output.getUser_pw())) {
			session.setAttribute("sessionAdminId", output.getUser_id());
		} else {
			return webHelper.getJsonWarning("아이디 또는 비밀번호가 일치하지 않습니다.");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", output);
		return webHelper.getJsonData(map);
	}
	
	/** 회원정보 수정(관리자) */
	@RequestMapping(value = "memberEdit", method = RequestMethod.POST)
	public Map<String, Object> memberEdit() {

		HttpSession session = webHelper.getSession();

		String tempEmail = (String) session.getAttribute("ckdEmail");

		/** 사용자가 입려간 파라미터 수신 및 유효성 검사 */
		int membno = webHelper.getInt("membno");
		String name = webHelper.getString("name");
		String birthdate = webHelper.getString("birthdate");
		String gender = webHelper.getString("gender");
		String email = webHelper.getString("email");
		String addrPost = webHelper.getString("addrPost");
		String addrRoad = webHelper.getString("addrRoad");
		String addr2 = webHelper.getString("addrDetail");
		String addrExtra = webHelper.getString("addrExtra");
		String tel = webHelper.getString("tel");

		String addr1 = "";
		// 주소 변환
		if (addrPost != null) {
			addr1 = addrPost + "," + addrRoad + "," + addrExtra;
		}

		/** 유효성 검사 시작 */
		if (name == null) {
			return webHelper.getJsonWarning("이름을 입력하세요.");
		}
		if (!regexHelper.isKor(name)) {
			return webHelper.getJsonWarning("이름은 한글로만 가능합니다.");
		}
		if (birthdate == null) {
			return webHelper.getJsonWarning("생년월일을 입력하세요.");
		}
		if (gender == null) {
			return webHelper.getJsonWarning("성별을 선택하세요.");
		}
		if (email == null) {
			return webHelper.getJsonWarning("이메일을 입력하세요.");
		}
		if (!regexHelper.isEmail(email)) {
			return webHelper.getJsonWarning("이메일 형식이 잘못되었습니다.");
		}
		if (!email.equals(tempEmail)) {
			return webHelper.getJsonWarning("이메일 중복검사가 필요합니다.");
		}

		webHelper.removeSession("ckdEmail");
		/** 데이터 수정하기 */
		// 수정할 값들을 Beans에 담는다.
		Member input = new Member();
		input.setMembno(membno);
		input.setName(name);
		input.setBirthdate(birthdate);
		input.setGender(gender);
		input.setEmail(email);
		input.setAddr1(addr1);
		input.setAddr2(addr2);
		input.setTel(tel);

		Member output = null;

		try {
			// 데이터 수정
			memberService.editMemberForAdmin(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		/** 결과를 확인하기 위한 JSON 출력 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", output);
		return webHelper.getJsonData(map);
	}

	/** 회원정보 수정 시 이메일 중복 검사 */
	@RequestMapping(value = "emailCheckForUpdate", method = RequestMethod.GET)
	public Map<String, Object> emailCheckForUpdate() {

		HttpSession session = webHelper.getSession();

		// request 객체를 사용해서 세션 객체를 만들기
		String email = webHelper.getString("email");

		if (email == null) {
			return webHelper.getJsonWarning("이메일을 입력하세요.");
		}
		if (!regexHelper.isEmail(email)) {
			return webHelper.getJsonWarning("이메일 형식이 잘못되었습니다.");
		}

		Member input = new Member();
		input.setEmail(email);

		int output = 0;

		try {
			// Beans에 저장한 값을 파라미터로 전달 한 뒤 쿼리문을 통해 원하는 값을 불러와 output에 값 담기
			output = memberService.getMemberCount(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		// 조회 결과가 0(중복X) 또는 1(= 내가 쓰는 주소)이라면 세션에 중복검사 여부 확인을 위한 값을 저장
		if (output == 0 || output == 1) {
			session.setAttribute("ckdEmail", email);
		} else if (output > 1) {
			return webHelper.getJsonWarning("이미 사용 중인 아이디 입니다.");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", output);
		return webHelper.getJsonData(map);
	}

	/** 회원정보 삭제(관리자) */
	@RequestMapping(value = "memberDelete", method = RequestMethod.DELETE)
	public Map<String, Object> memberDelete() {

		int membno = webHelper.getInt("membno");

		// 데이터 삭제에 필요한 조건값을 Beans에 저장하기

		Member input = new Member();
		input.setMembno(membno);
		int output = 0;

		try {
			output = memberService.deleteMember(input); // 데이터 삭제
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		/** 결과를 확인하기 위한 JSON 출력 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", output);
		return webHelper.getJsonData(map);
	}
	
	/** 로그아웃(공용) */
	@RequestMapping(value = "sessionRemove", method = RequestMethod.GET)
	public Map<String, Object> sessionRemove() {

		// session 모두 초기화.
		webHelper.removeAllSession();

		return webHelper.getJsonData();
	}
	
	/** 회원정보 수정 */
	@RequestMapping(value = "/myInfo_ok", method = RequestMethod.PUT)
	public Map<String, Object> myInfo_ok(HttpServletRequest request) {

		/** 1) 필요한 변수값 생성 */
		// request 객체를 사용해서 세션 객체를 만들기
		HttpSession session = request.getSession();
		// session서버에 저장한 값을 mySession 변수에 담기
		int mySession1 = (int) session.getAttribute("sessionMembno");
		Member input = new Member();

		input.setMembno(mySession1);

		try {
			input = memberService.getMemberItem(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}
		String mypc_ch_pw = webHelper.getString("mypc_ch_pw");
		String mypc_now_pw = webHelper.getString("mypc_now_pw");
		String mypc_ch_pw_re = webHelper.getString("mypc_ch_pw_re");
		String mypc_ch_email = webHelper.getString("mypc_ch_email");
		String mypc_addr1 = webHelper.getString("mypc_addr1");
		String mypc_addr2 = webHelper.getString("mypc_addr2");
		String mypc_postcode = webHelper.getString("mypc_postcode");
		String mypc_extraAddr = webHelper.getString("mypc_extraAddr");
		String mypc_ch_tel = webHelper.getString("mypc_ch_tel");

		String add_addr1 = mypc_postcode + "," + mypc_addr1 + "," + mypc_extraAddr;

		String addr2 = mypc_addr2;

		/** 유효성 검사 시작 **/
		if (mypc_ch_pw == null && mypc_ch_pw == null && mypc_ch_email == null && mypc_ch_tel == null
				&& mypc_addr1 == null) {
			return webHelper.getJsonWarning("변동사항이 없습니다.");
		} else {

			/** 비밀번호 검사 시작 **/
			if (mypc_now_pw == null) {
				return webHelper.getJsonWarning("현재 비밀번호를 입력하세요.");
			} else if (!input.getUser_pw().equals(mypc_now_pw)) {
				return webHelper.getJsonWarning("현재 비밀번호가 틀렸습니다.");
			} else {

				/** 비밀번호 유효성 검사 시작 **/
				if (mypc_ch_pw == null) {
					// null값일경우 비밀번호 변경이 이루어지지 않는다고 판단하여 패스
				} else if (mypc_ch_pw != null && mypc_ch_pw_re == null) {
					return webHelper.getJsonWarning("변경할 비밀번호를 한번 더 입력하세요.");
				} else if (!mypc_ch_pw.equals(mypc_ch_pw_re)) {
					return webHelper.getJsonWarning("변경할 비밀번호가 다릅니다.");
				} else {
					input.setUser_pw(mypc_ch_pw);
				}

				/** 이메일 유효성 검사 시작 **/
				if (mypc_ch_email == null) {
					// null값일경우 이메일 변경이 이루어지지 않는다고 판단하여 패스
				} else if (mypc_ch_email != null && input.getEmail().equals(mypc_ch_email)) {
					return webHelper.getJsonWarning("변경 전 이메일과 다르게 입력하세요.");
				} else {
					input.setEmail(mypc_ch_email);
				}

				/** 연락처 유효성 검사 시작 **/
				if (mypc_ch_tel == null) {
					// null값일경우 연락처 변경이 이루어지지 않는다고 판단하여 패스
				} else if (mypc_ch_tel != null && input.getTel() != null && input.getTel().equals(mypc_ch_tel)) {
					return webHelper.getJsonWarning("변경 전 연락처와 다르게 입력하세요.");
				} else {
					input.setTel(mypc_ch_tel);
				}

				/** 주소지 유효성 검사 시작 **/
				if (mypc_addr1 != null && mypc_addr2 != null) {
					input.setAddr1(add_addr1);
					input.setAddr2(addr2);
				} else if (mypc_addr1 != null && mypc_addr2 == null) {
					return webHelper.getJsonWarning("상세주소를 입력하세요.");
				} else if (mypc_addr1 == null && mypc_addr2 != null) {
					return webHelper.getJsonWarning("주소를 올바르게 입력하세요.");
				}
			}
		}

		// 수정된 결과를 조회하기 위한 객체
		try {
			memberService.editMember(input);
			memberService.getMemberItem(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		return webHelper.getJsonData();
	}

	/** 회원 탈퇴 */
	@RequestMapping(value = "/secession_ok", method = RequestMethod.DELETE)
	public Map<String, Object> secession_ok(HttpServletRequest request) {

		Member input = new Member();
		int membno = webHelper.getInt("membno");
		input.setMembno(membno);

		try {
			memberService.deleteMember(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		webHelper.removeAllSession();

		return webHelper.getJsonData();
	}

}
