<%@page import="java.io.Console"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="inc/head.jsp"%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/myCoupon.css" />
</head>
<body>
	<%@ include file="./inc/userHeader.jsp"%>

	<!-- 컨텐츠 -->
	<section class="container content">

		<!-- 페이지 헤더 -->
		<div class="page-header mypc-margin"><div class="mypc-icon pull-right" onclick="location.href='myPage'"><span class="glyphicon glyphicon-home"></span></div>
			<h1>쿠폰관리</h1>
		</div>

		<!-- 쿠폰 카운트 -->
		<nav class="mypc-coupon-nav clearfix">
			<div>
				<div class="mypc-coup-alram">사용 가능한 쿠폰 
					<span class="mypc-coupon-count">
						<!-- 사용가능 쿠폰 갯수 -->
						<c:set var="result" value="0"/>
						<c:forEach var="item" items="${output}">
							<c:if test="${item.enabled == 0}">
								<c:set var="result" value="${result+1}"/>
							</c:if>
						</c:forEach>
						${result }
					</span>장
				</div>
			</div>
		</nav>

		<br />

		<!-- 경고문 -->
		<article class="mypc-cautions">
			<ul>
				<li>가격은 언제나 변동될 수 있으니 주의하시기 바랍니다.</li>
				<li>배송날짜는 다양한 이유로 지연될 수 있습니다.</li>
				<li>단순 변심으로 인한 환불은 불가입니다.</li>
				<li>쿠폰의 유효기간은 해당일 자정까지 입니다.</li>
			</ul>
		</article>


		<!-- 쿠폰 리스트 -->
		<c:choose>
		
			<%-- 쿠폰 無 --%>
			<c:when test="${empty_b==0}">
				<div class="mypc-empty">쿠폰이 없습니다.</div>
			</c:when>
			
			<%-- 쿠폰 有 --%>
			<c:otherwise>
				<c:forEach var="item" items="${output}">
					<article class="mypc-coupon-box col-lg-6 col-md-6 col-sm-6 col-xs-12 clearfix">

						<!-- 사이드 쿠폰 -->						
						<form>
							<input type="hidden" name="coupno" value="${item.coupno }" />
							<div class="mypc-black-box pull-right">
								
								<!-- 쿠폰삭제 버튼 -->
								<a href="#" class="mypc-coupon-delete">x</a>
								
								<!-- 할인율 -->
								<div class="mypc-coupon-text">
									${item.coupon_off}%
								</div>
							</div>
						</form>

						<!-- 쿠폰 본문 -->
						<div class="mypc-white-box pull-right">
						
							<!-- 쿠폰명 -->
							<div class="mypc-coupon-name">${item.coupon_name}</div>
							
							<!-- 할인율 -->
							<div class="mypc-coupon-content">
								${item.coupon_off}%
							</div>

							<!-- 사용가능 기간 -->
							<div class="mypc-coupon-term">
								<span>중복사용은 불가능합니다.</span><br/>
								<span>${fn:substring(item.issue_date,0,10)}</span> ~ <span>${fn:substring(item.expired_date,0,10)}</span>
							</div>
							
							<!-- 사용가능 여부 -->
							<c:set var="d_day" value=""/>
							<c:choose>
								<c:when test="${item.enabled == 0}">
									<div class="mypc-coupon-dDay available">
										<span>
											사용가능
										</span>
									</div>
								</c:when>
								<c:when test="${item.enabled == 1}">
									<div class="mypc-coupon-dDay expired">
										<span>
											만료
										</span>
									</div>
								</c:when>
								<c:otherwise>
									<div class="mypc-coupon-dDay expired">
										<span>
											사용한쿠폰
										</span>
									</div>
								</c:otherwise>
							</c:choose>
						</div>
					</article>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</section>

	<%@ include file="./inc/footer.jsp"%>
	<%@ include file="./inc/userScript.jsp"%>
	<script src="${pageContext.request.contextPath }/assets/js/myCoupon.js"></script>
	<script type="text/javascript">
		$(function() {
			userCommon(); // 상단 메뉴바의 기능을 구현
		});
	</script>

</body>
</html>