package com.son.mypc.interceptor;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.son.mypc.helper.WebHelper;

import lombok.extern.slf4j.Slf4j;
import uap_clj.java.api.Browser;
import uap_clj.java.api.Device;
import uap_clj.java.api.OS;

@Slf4j
public class AppInterceptor extends HandlerInterceptorAdapter {
	long startTime = 0, endTime = 0;

	/** WebHelper 객체 주입 */
	@Autowired
	WebHelper webHelper;

	/**
	 * Controller 실행 요청 전에 수행되는 메서드 클라이언트의 요청을 컨트롤러에 전달 하기 전에 호출된다. return 값으로
	 * boolean 값을 전달하는데 false 인 경우 controller를 실행 시키지 않고 요청을 종료한다. 보통 이곳에서 각종 체크작업과
	 * 로그를 기록하는 작업을 진행한다.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		log.debug("AppInterceptor.preHandle 실행됨");

		// WebHelper의 초기화는 모든 컨트롤러마다 개별적으로 호출되어야 한다.
		// Interceptor에서 이 작업을 수행하면 모든 메서드마다 수행하는 동일 작업을 일괄처리할 수 있다.
		webHelper.init();
		response.setContentType("text/html; charset=UTF-8");

		// 컨트롤러 실행 직전에 현재 시각을 저장한다.
		startTime = System.currentTimeMillis();

		/** 1) 클라이언트의 요청 정보 확인하기 */
		// 현재 URL 획득
		String url = request.getRequestURL().toString();

		// GET방식인지, POST방식인지 조회한다.
		String methodName = request.getMethod();

		// URL에서 "?"이후에 전달되는 GET파라미터 문자열을 모두 가져온다.
		String queryString = request.getQueryString();

		// 가져온 값이 있다면 URL과 결합하여 완전한 URL을 구성한다.
		if (queryString != null) {
			url = url + "?" + queryString;
		}

		// 클라이언트의 요청 컨트롤러
		String controllerName = url.substring(url.lastIndexOf("/"));

		// 획득한 정보를 로그로 표시한다.
		log.debug(String.format("[%s] %s", methodName, url));

		/** 2) 클라이언트가 전달한 모든 파라미터 확인하기 */
		Map<String, String[]> params = request.getParameterMap();

		for (String key : params.keySet()) {
			String[] value = params.get(key);
			log.debug(String.format("(p) <-- %s = %s", key, String.join(",", value)));
		}

		/** 3) 클라이언트가 머물렀던 이전 페이지와 이전 페이지에 머문 시간 확인하기 */
		// 이전에 머물렀던 페이지
		String referer = request.getHeader("referer");

		// 이전 종료 시간이 0보다 크다면 새로운 시작시간과의 차이는 이전 페이지에 머문 시간을 의미한다.
		if (referer != null && endTime > 0) {
			log.debug(String.format("- REFERER : time=%d, url=%s", startTime - endTime, referer));
		}

		/** 4) 접속한 클라이언트의 장치 정보를 로그로 기록 */
		// 접근한 클라이언트의 HTTP 헤더 정보 가져오기

		// "uap" 라이브러리의 기능을 통해 UserAgent 정보 파싱
		String ua = request.getHeader("User-Agent");
		// -> import uap_clj.java.api.Browser;
		Map<String, String> browser = Browser.lookup(ua);
		// -> import uap_clj.java.api.OS;
		Map<String, String> os = (Map<String, String>) OS.lookup(ua);
		// -> import uap_clj.java.api.Device;
		Map<String, String> device = (Map<String, String>) Device.lookup(ua);

		// 추출된 정보들을 출력하기 위해 문자열로 묶기
		String browserStr = String.format("- Browser: {family=%s, patch=%s, major=%s, minor=%s}", browser.get("family"),
				browser.get("patch"), browser.get("major"), browser.get("minor"));

		String osStr = String.format("- OS: {family=%s, patch=%s, patch_minor=%s, major=%s, minor=%s}",
				os.get("family"), os.get("patch"), os.get("patch_minor"), os.get("major"), os.get("minor"));

		String deviceStr = String.format("- Device: {family=%s, model=%s, brand=%s}", device.get("family"),
				device.get("model"), device.get("brand"));

		// 로그 저장
		log.debug(browserStr);
		log.debug(osStr);
		log.debug(deviceStr);

		/** 5) 접속한 클라이언트의 Session 검사 및 권한에 따른 컨트롤러 요청 처리 제어 */
		try {
			log.debug("세션 검사 실행됨");
			log.debug("요청 컨트롤러 -> " + controllerName);

			// 사용자와 관리자의 페이지 목록
			String[] adminPageList = { "/adminIndex", "/adminLogin", "/adminMember", "/adminInquiry",
					"/adminInquiryView", "/inquiryMailSend", "/adminMemberInfo", "/adminStats", "/adminItem",
					"/adminItemRegist" };
			String[] userPageList = { "/sessionSave", "/", "/index", "/login", "/join", "/boardMain", "/boardView",
					"/boardWrite", "/support", "/myPage", "/myCoupon", "/myCart", "/myInfo", "/myOrder", "/pay",
					"/itemList", "/itemview" };

			// 필터링 => 권한이 없는 페이지 접속 방지 (사용자 -> 관리자 X , 관리자 -> 사용자 X)
			// 관리자 로그인 상태인 경우
			if (webHelper.getSession("sessionAdminId") != null) {
				log.debug("관리자 로그인 상태입니다.");

				// 관리자가 로그인이 된 상태에서 주소 표시줄에 직접 adminLogin 페이지를 요청했을 경우, 세션을 삭제 (->로그아웃)
				if (controllerName.indexOf("/adminLogin") > -1) {
					webHelper.removeAllSession();
				}

				// 관리자 로그인 상태에서 사용자 페이지에 접근을 시도한 경우
				for (int i = 0; i < userPageList.length; i++) {
					if (userPageList[i].equals(controllerName)) {
						PrintWriter out = response.getWriter();
						out.println("<script>alert('관리자 로그아웃 후 이용 가능합니다.'); location.href='adminIndex'; </script>");
						out.flush();
						return false;
					}
				}
			}
			// 사용자가(회원, 비회원) 관리자 페이지에 접근을 시도한 경우
			else {
				if (webHelper.getSession("sessionUserId") != null) {
					log.debug("회원입니다.");
					// 사용자가 로그인이 된 상태에서 주소 표시줄에 직접 login 또는 join 입력하여 페이지를 요청했을 경우, 세션을
					// 삭제합니다.(->로그아웃)
					if (controllerName.indexOf("/login") > -1 || controllerName.indexOf("/join") > -1) {
						webHelper.removeAllSession();
					}
					// 사용자(회원)이 로그인 상태에서 관리자 페이지에 접근을 시도한 경우
					for (int i = 0; i < adminPageList.length; i++) {
						if (adminPageList[i].equals(controllerName)) {
							PrintWriter out = response.getWriter();
							out.println("<script>alert('권한이 없습니다.'); location.href='index'; </script>");
							out.flush();
							return false;
						}
					}
				} else {
					log.debug("비회원입니다.");
					// 비회원이 boardWrite 페이지 또는 pay 페이지를 요청한 경우
					if (controllerName.indexOf("/boardWrite") > -1 || controllerName.indexOf("/pay") > -1
							|| controllerName.indexOf("/myCart") > -1 || controllerName.indexOf("/myCoupon") > -1
							|| controllerName.indexOf("/myOrder") > -1 || controllerName.indexOf("/myInfo") > -1
							|| controllerName.indexOf("/myPage") > -1) {
						PrintWriter out = response.getWriter();
						out.println("<script>alert('로그인 후 이용 가능합니다.'); location.href='index';</script>");
						out.flush();
						return false;
					}
					if (isAjaxRequest(request)) {
						log.debug("세션=NULL, Ajax call");
						response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
						// return false;
					} else {
						// response.sendRedirect(request.getContextPath() + "/login");
					}
				}

				for (int i = 0; i < adminPageList.length; i++) {
					if (adminPageList[i].equals(controllerName)) {
						// 단, 비로그인 상태에서 관리자 로그인 페이지를 요청할 경우는 허용
						if (webHelper.getSession("sessionUserId") == null && adminPageList[i].equals("/adminLogin")) {
							continue;
						}
						PrintWriter out = response.getWriter();
						out.println("<script>alert('권한이 없습니다.'); location.href='adminLogin'; </script>");
						out.flush();
						return false;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			return false;
		}
		return super.preHandle(request, response, handler);
	}

	private boolean isAjaxRequest(HttpServletRequest req) {
		String ajaxHeader = "AJAX";
		log.debug("AJAX 인터셉터 req.getHeader --> " + req.getHeader(ajaxHeader));
		return req.getHeader(ajaxHeader) != null && req.getHeader(ajaxHeader).equals(Boolean.TRUE.toString());
	}

	/**
	 * view 단으로 forward 되기 전에 수행. 컨트롤러 로직이 실행된 이후 호출된다. 컨트롤러 단에서 에러 발생 시 해당 메서드는
	 * 수행되지 않는다. request로 넘어온 데이터 가공 시 많이 사용된다.
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// log.debug("AppInterceptor.postHandle 실행됨");

		// 컨트롤러 종료시의 시각을 가져온다.
		endTime = System.currentTimeMillis();

		// 시작시간과 종료시간 사이의 차이를 구하면 페이지의 실행시간을 구할 수 있다.
		log.debug(String.format("running time: %d(ms)\n", endTime - startTime));

		super.postHandle(request, response, handler, modelAndView);
	}

	/**
	 * 컨트롤러 종료 후 view가 정상적으로 랜더링 된 후 제일 마지막에 실행이 되는 메서드.
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// log.debug("AppInterceptor.afterCompletion 실행됨");
		super.afterCompletion(request, response, handler, ex);
	}

	/**
	 * Servlet 3.0부터 비동기 요청이 가능해짐에 따라 비동기 요청 시 PostHandle와 afterCompletion메서드를 수행하지
	 * 않고 이 메서드를 수행하게 된다. 거의 사용 안함.
	 */
	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// log.debug("AppInterceptor.afterConcurrentHandlingStarted 실행됨");
		super.afterConcurrentHandlingStarted(request, response, handler);
	}
}