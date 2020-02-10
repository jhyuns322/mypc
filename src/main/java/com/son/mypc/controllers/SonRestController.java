package com.son.mypc.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.son.mypc.helper.MailHelper;
import com.son.mypc.helper.RegexHelper;
import com.son.mypc.helper.WebHelper;
import com.son.mypc.model.Comment;
import com.son.mypc.model.Coupon;
import com.son.mypc.model.Document;
import com.son.mypc.model.Member;
import com.son.mypc.service.CommentService;
import com.son.mypc.service.CouponService;
import com.son.mypc.service.DocumentService;
import com.son.mypc.service.MemberService;

@RestController
public class SonRestController {

	@Autowired
	WebHelper webHelper;
	@Autowired
	RegexHelper regexHelper;
	@Autowired
	MailHelper mailHelper;
	@Autowired
	MemberService memberService;
	@Autowired
	DocumentService documentService;
	@Autowired
	CommentService commentService;
	@Autowired
	CouponService couponService;

	/** 1) 회원가입 컨트롤러 */
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
		String addrPost = webHelper.getString("addrPost");    // null 허용
		String addrRoad = webHelper.getString("addrRoad");    // null 허용
		String addr2 = webHelper.getString("addrDetail");     // null 허용
		String addrExtra = webHelper.getString("addrExtra");  // null 허용
		String tel = webHelper.getString("tel");              // null 허용

		// 주소 양식 변환
		String addr1 = "";
		if (addrPost != null) {
			addr1 = addrPost + "," + addrRoad + "," + addrExtra;
		}

		// 현재 날짜 불러오기
		SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd");
		Calendar time = Calendar.getInstance();
		String reg_date = f1.format(time.getTime());

		// 유효성 검사
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

		// 세션 삭제
		webHelper.removeAllSession();

		/** 더미 데이터 만들기 위한 반복문 시작 */
//		int outputCoupon = 0;
//		for (int i = 0; i < 300; i++) {
//			user_id = RandomData.randomId();
//			user_pw = RandomData.randomPw();
//			name = RandomData.randomName();
//			birthdate = RandomData.randomBirthdate();
//			gender = RandomData.randomGender();
//			email = RandomData.randomEmail();
//			tel = RandomData.randomTel();
//			Member inputMember = new Member();
//			inputMember.setUser_id(user_id);
//			inputMember.setUser_pw(user_pw);
//			inputMember.setName(name);
//			inputMember.setBirthdate(birthdate);
//			inputMember.setGender(gender);
//			inputMember.setEmail(email);
//			inputMember.setAddr1(addr1);
//			inputMember.setAddr2(addr2);
//			inputMember.setTel(tel);
//			inputMember.setReg_date(reg_date);
//			Member outputMember = null;
//			try {
//				memberService.addMember(inputMember);
//				outputMember = memberService.getIdCheck(inputMember);
//			} catch (Exception e) {
//				return webHelper.getJsonError(e.getLocalizedMessage());
//			}
//			String coupon_name = "MYPC 신규가입 기념 쿠폰";
//			int coupon_off = 10;
//			int enabled = 0;
//			String issue_date = reg_date;
//			time.add(Calendar.DAY_OF_MONTH, +7);
//			String expired_date = f2.format(time.getTime());
//			Coupon inputCoupon = new Coupon();
//			int membno = (int) outputMember.getMembno();
//			inputCoupon.setMembno(membno);
//			inputCoupon.setCoupon_name(coupon_name);
//			inputCoupon.setCoupon_off(coupon_off);
//			inputCoupon.setEnabled(enabled);
//			inputCoupon.setIssue_date(issue_date);
//			inputCoupon.setExpired_date(expired_date);
//			try {
//				outputCoupon = couponService.addCoupon(inputCoupon);
//			} catch (Exception e) {
//				return webHelper.getJsonError(e.getLocalizedMessage());
//			}
//		}
		/** 더미 데이터 만들기 위한 반복문 종료 */

		/** 원본 */
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
			// 입력값 저장 후 저장된 회원의 membno 불러오기
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
		/** 원본 */

		/** 결과를 확인하기 위한 JSON 출력 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", outputCoupon);
		return webHelper.getJsonData(map);
	}

	/** 2) 회원가입 시 아이디 중복 검사 컨트롤러 */
	@RequestMapping(value = "userIdCheck", method = RequestMethod.GET)
	public Map<String, Object> userIdCheck() {

		HttpSession session = webHelper.getSession();

		// request 객체를 사용해서 세션 객체를 만들기
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

	/** 3) 회원가입 시 이메일 중복 검사 컨트롤러 */
	@RequestMapping(value = "emailCheck", method = RequestMethod.GET)
	public Map<String, Object> emailCheck() {

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

		// 조회 결과가 없다면(=중복 아이디가 없다면) 세션에 중복검사 여부 확인을 위한 값을 저장
		if (output == 0) {
			session.setAttribute("ckdEmail", email);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", output);
		return webHelper.getJsonData(map);
	}

	/** 4) 로그인(세션 저장) 컨트롤러(사용자) */
	@RequestMapping(value = "sessionSave", method = RequestMethod.POST)
	public Map<String, Object> sessionSave() {
		// 세션 객체를 만들기
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

	/** 5) 아이디 찾기 컨트롤러 */
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

	/** 6) 비밀번호 찾기(아이디 검사) 컨트롤러 */
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

	/** 7) 비밀번호 찾기(아이디와 이메일 일치 여부 검사 후 임시비밀번호 이메일 발송) 컨트롤러 */
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

	/** 8) 새 게시글 작성 컨트롤러(사용자) */
	@RequestMapping(value = "boardPost", method = RequestMethod.POST)
	public Map<String, Object> boardPost() {

		/** session 조회 시작 */
		HttpSession session = webHelper.getSession();
		int membno = (int) session.getAttribute("sessionMembno");
		/** session 조회 종료 */

		/** 입력한 파라미터 수신 및 유효성 검사 */
		String subject = webHelper.getString("subject");
		String content = webHelper.getString("content");

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar time = Calendar.getInstance();
		String reg_date = f.format(time.getTime());

		// 자유게시판 게시글의 doc_type은 b (문의글은 i)
		String doc_type = "b";

		int hits = 0;

		if (subject == null) {
			return webHelper.getJsonWarning("제목을 입력하세요.");
		}
		if (content == null) {
			return webHelper.getJsonWarning("내용을 입력하세요.");
		}

		/** 더미 데이터 만들기 위한 반복문 시작 */
//		Document output = null;
//
//		for (int i = 0; i < 300; i++) {
//			
//			subject = "자유게시판 테스트 [" + i + "] 글입니다.";
//			content = "자유게시판 테스트 [" + i + "] 글입니다.";
//			Random r = new Random();
//			membno = r.nextInt(300);
//			
//			Document input = new Document();
//			input.setDoc_type(doc_type);
//			input.setSubject(subject);
//			input.setContent(content);
//			input.setReg_date(reg_date);
//			input.setHits(hits);
//			input.setMembno(membno);
//			try {
//				documentService.addBoard(input);
//				output = documentService.getBoardView(input);
//			} catch (Exception e) {
//				return webHelper.getJsonError(e.getLocalizedMessage());
//			}
//		}
		/** 더미 데이터 만들기 위한 반복문 종료 */

		/** 원본 */
		Document input = new Document();
		input.setDoc_type(doc_type);
		input.setSubject(subject);
		input.setContent(content);
		input.setReg_date(reg_date);
		input.setHits(hits);
		input.setMembno(membno);

		int output = 0;

		try {
			output = documentService.addBoard(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}
		/** 원본 */

		/** 결과를 확인하기 위한 JSON 출력 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", output);
		return webHelper.getJsonData(map);
	}

	/** 9) 게시글 수정 컨트롤러(사용자) */
	@RequestMapping(value = "boardUpdate", method = RequestMethod.PUT)
	public Map<String, Object> boardUpdate() {

		/** 사용자가 입려간 파라미터 수신 및 유효성 검사 */
		int docno = webHelper.getInt("docno");
		String subject = webHelper.getString("subject");
		String content = webHelper.getString("content");

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar time = Calendar.getInstance();
		String edit_date = f.format(time.getTime());

		/** 데이터 수정하기 */
		// 수정할 값들을 Beans에 담는다.
		Document input = new Document();
		input.setDocno(docno);
		input.setSubject(subject);
		input.setContent(content);
		input.setEdit_date(edit_date);

		Document output = null;

		try {
			// 데이터 수정
			documentService.editBoard(input);
			// 데이터 조회
			output = documentService.getBoardView(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		/** 결과를 확인하기 위한 JSON 출력 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", output);
		return webHelper.getJsonData(map);
	}

	/** 10) 게시글 삭제 컨트롤러(사용자) */
	@RequestMapping(value = "boardDelete", method = RequestMethod.DELETE)
	public Map<String, Object> boardDelete() {

		/** session 조회 시작 */
		HttpSession session = webHelper.getSession();
		String userId = (String) session.getAttribute("sessionUserId");
		/** session 조회 종료 */

		int docno = webHelper.getInt("docno");
		String user_id = webHelper.getString("user_id");

		int output = 0;

		if (userId.equals(user_id)) {

			// 데이터 삭제에 필요한 조건값을 Beans에 저장하기
			Document input = new Document();
			input.setDocno(docno);

			try {
				output = documentService.deleteBoard(input); // 데이터 삭제
			} catch (Exception e) {
				return webHelper.getJsonError(e.getLocalizedMessage());
			}

		}

		/** 결과를 확인하기 위한 JSON 출력 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", output);
		return webHelper.getJsonData(map);
	}

	/** 11) 덧글 작성 컨트롤러(사용자) */
	@RequestMapping(value = "commentPost", method = RequestMethod.POST)
	public Map<String, Object> commentPost() {

		int membno = webHelper.getInt("membno");
		String comment = webHelper.getString("comment");
		int docno = webHelper.getInt("docno");

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar time = Calendar.getInstance();
		String reg_date = f.format(time.getTime());

		Comment inputComment = new Comment();
		inputComment.setComment(comment);
		inputComment.setMembno(membno);
		inputComment.setDocno(docno);
		inputComment.setReg_date(reg_date);
		
		int outputComm = 0;

		try {
			Document inputDocument = new Document();
			inputDocument.setDocno(docno);
			outputComm = commentService.addComment(inputComment, inputDocument);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("itemComm", outputComm);
		return webHelper.getJsonData(map);
	}

	/** 12) 덧글 삭제 컨트롤러(사용자) */
	@RequestMapping(value = "commentDelete", method = RequestMethod.DELETE)
	public Map<String, Object> commentDelete() {

		int commno = webHelper.getInt("commno");
		int docno = webHelper.getInt("docno");
		// 데이터 삭제에 필요한 조건값을 Beans에 저장하기
		Comment inputComment = new Comment();
		inputComment.setCommno(commno);

		int output = 0;

		try {
			Document inputDocument = new Document();
			inputDocument.setDocno(docno);
			output = commentService.deleteComment(inputComment, inputDocument); // 데이터 삭제
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		/** 결과를 확인하기 위한 JSON 출력 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", output);
		return webHelper.getJsonData(map);
	}

	/** 12) 문의글 작성 컨트롤러(사용자) */
	@RequestMapping(value = "supportPost", method = RequestMethod.POST)
	public Map<String, Object> supportPost() {

		/** session 조회 시작 */
		HttpSession session = webHelper.getSession();

		int membno = 0;

		if (session.getAttribute("sessionMembno") != null) {
			membno = (int) session.getAttribute("sessionMembno");
		} else {
			return webHelper.getJsonWarning("로그인 시 이용이 가능합니다.");
		}

		/** 입력한 파라미터 수신 및 유효성 검사 */
		String subject = webHelper.getString("subject");
		String content = webHelper.getString("content");

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar time = Calendar.getInstance();
		String reg_date = f.format(time.getTime());

		// 미답변 = 'n', 답변 = 'y'
		String replied = "n";
		// 게시글 = 'b', 문의글= 'i'
		String doc_type = "i";

		if (subject == null) {
			return webHelper.getJsonWarning("제목을 입력하세요.");
		}
		if (content == null) {
			return webHelper.getJsonWarning("내용을 입력하세요.");
		}

		/** 더미 데이터 만들기 위한 반복문 시작 */
//		Document output = null;
//
//		for (int i = 0; i < 50; i++) {
//			
//			subject = "문의 테스트 [" + i + "] 글입니다.";
//			content = "문의 테스트 [" + i + "] 글입니다.";
//			Random r = new Random();
//			membno = r.nextInt(300);
//			
//			Document input = new Document();
//			input.setDoc_type(doc_type);
//			input.setSubject(subject);
//			input.setContent(content);
//			input.setReg_date(reg_date);
//			input.setReplied(replied);
//			input.setMembno(membno);
//			try {
//				documentService.addInquiry(input);
//				output = documentService.getInquiryView(input);
//			} catch (Exception e) {
//				return webHelper.getJsonError(e.getLocalizedMessage());
//			}
//		}
		/** 더미 데이터 만들기 위한 반복문 종료 */

		/** 원본 */
		Document input = new Document();
		input.setDoc_type(doc_type);
		input.setSubject(subject);
		input.setContent(content);
		input.setReg_date(reg_date);
		input.setReplied(replied);
		input.setMembno(membno);

		int output = 0;

		try {
			output = documentService.addInquiry(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}
		/** 원본 */

		/** 결과를 확인하기 위한 JSON 출력 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", output);
		return webHelper.getJsonData(map);
	}

	/** 13) 로그인(세션 저장) 컨트롤러(관리자) */
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

	/** 14) 회원정보 수정 컨트롤러(관리자) */
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

	/** 15) 회원정보 수정 시 이메일 중복 검사 컨트롤러 */
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

	/** 16) 회원정보 삭제 컨트롤러(관리자) */
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

	/** 17) 문의글 답변 메일 전송 컨트롤러(관리자) */
	@RequestMapping(value = "inquiryReply", method = RequestMethod.POST)
	public Map<String, Object> inquiryReply() {

		/** 사용자의 입력값 받기 */
		int docno = webHelper.getInt("docno");
		String email = webHelper.getString("email");
		String subject = webHelper.getString("subject");
		String content = webHelper.getString("content");
		String reg_date = webHelper.getString("reg_date");
		String reSubject = webHelper.getString("mailSubject");
		String reContent = webHelper.getString("mailContent");

		/** 제목, 내용에 대한 입력 여부 검사 */
		if (reSubject == null) {
			return webHelper.getJsonWarning("제목을 입력하세요.");
		}
		if (reContent == null) {
			return webHelper.getJsonWarning("답변 내용을 입력하세요.");
		}

		// 기존 mailHelper 수정 > content에 문의글 작성일, 문의 제목, 내용, 문의 답변을 포함하여 mailContent에 담음
		String mailContent = mailHelper.mailInquiryContent(reg_date, subject, content, reContent);

		/** 메일 발송 처리 */
		try {
			// sendMail() 메서드 선언 시 throws를 정의했기 때문에 예외처리가 요구
			mailHelper.sendMail(email, reSubject, mailContent);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		// 답변 여부에 대한 값을 n -> y로 변경 후 저장
		String replied = "y";

		/** 데이터 저장하기 */
		Document input = new Document();
		input.setDocno(docno);
		input.setReplied(replied);

		int output = 0;

		try {
			// 데이터 저장
			output = documentService.editInquiry(input);
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		/** 결과를 확인하기 위한 JSON 출력 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", output);
		return webHelper.getJsonData(map);
	}

	/** 18) 문의글 삭제 컨트롤러(관리자) */
	@RequestMapping(value = "deleteInquiry/{docno}", method = RequestMethod.DELETE)
	public Map<String, Object> deleteInquiry(@PathVariable String[] docno) {

		// 저장된 결과를 조회하기 위한 객체
		List<Document> output = new ArrayList<Document>();
		try {
			for (int i = 0; i < docno.length; i++) {
				// 데이터 저장
				Document input = new Document();
				input.setDocno(Integer.parseInt(docno[i]));

				documentService.deleteBoard(input);

				// 삭제 데이터를 리스트에 저장
				output.add(input);
			}
		} catch (Exception e) {
			return webHelper.getJsonError(e.getLocalizedMessage());
		}

		/** 결과를 확인하기 위한 JSON 출력 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", output);
		return webHelper.getJsonData(map);
	}

	/** 19) 로그아웃(세션 삭제) 컨트롤러(공용) */
	@RequestMapping(value = "sessionRemove", method = RequestMethod.GET)
	public Map<String, Object> sessionRemove() {

		// session 모두 초기화
		webHelper.removeAllSession();

		return webHelper.getJsonData();
	}

	/** 20) 쿠폰 지급 컨트롤러(관리자) */
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
}