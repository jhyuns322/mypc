<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="inc/head.jsp" %>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/myPage.css" />
</head>
<body>
	<%@ include file="./inc/userHeader.jsp"%>

	<!-- 컨텐츠 -->
	<nav class="container contents">
		<div class="row">
		
			<!-- 타이틀 -->
			<div class="top_title col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<h1>마이페이지</h1>
			</div>			
			<hr/>
			

			<!-- 회원정보 수정 -->
			<article class="col-lg-6 col-md-6 col-sm-6 col-xs-12">회원정보 수정
				<div class="mypc-box">
					<h3>회원정보 수정</h3>
					
					<!-- 이름 -->
					<div class="clearfix">
						<span><b>이름</b></span>
						<span class="pull-right">${output4.name}</span>
					</div>
					
					<!--  이메일 -->
					<div class="clearfix">
						<span><b>E-mail</b></span>
						<span class="pull-right">${output4.email}</span>
					</div>

					<!-- 연락처 -->
					<div class="clearfix">
						<span><b>휴대전화</b></span>
						<c:choose>
							<c:when test="${output4.tel != null }">
								<span class="pull-right">${output4.tel}</span>
							</c:when>
							<c:otherwise>
								<span class="pull-right">연락처가 없습니다.</span>
							</c:otherwise>
						</c:choose>
					</div>
					
					<!-- 주소 -->
					<div class="clearfix">
						<span><b>주소 (우편번호)</b></span>
						<c:choose>
						
							<%-- 주소지 有 --%>
							<c:when test="${output4.addr1 != ''}">
							<span class="pull-right">
								${addr1_postcode} / ${addr1}
							</span><br/>
								<span class="pull-right">
								<c:choose>
									<c:when test="${addr2_extraAddr == 'null'}">
									${addr2}
									</c:when>
									<c:otherwise>
										${addr2} / ${addr2_extraAddr}
									</c:otherwise>
								</c:choose>
								</span>
							</c:when>
							
							<%-- 주소지 無 --%>
							<c:otherwise>
								<span class="pull-right">주소지가 없습니다.</span>
							</c:otherwise>
						</c:choose>
					</div>
					
					<!-- 회원정보 수정 버튼 -->
					<a href="myInfo" class="mypc-btn-hover">수정</a>
				</div>
			</article>


			<!-- 장바구니 -->
			<article class="col-lg-6 col-md-6 col-sm-6 col-xs-12">장바구니
				<div class="mypc-box">
					<h3>장바구니</h3>
					<c:choose>
						<%-- 장바구니 無 --%>
						<c:when test="${output1 == '[]'}">
							<div class="clearfix">
								<span><b>장바구니</b></span>
								<span class="pull-right">장바구니가 비어있습니다.</span>
							</div>
						</c:when>
						
						<%-- 장바구니 有 --%>
						<c:otherwise>
							<c:forEach var="item" items="${output1}" end="3">
								<div class="clearfix">
									<span><b>장바구니</b></span>
									<span class="pull-right">${item.item_name}</span>
								</div>
							</c:forEach>
						</c:otherwise>
					</c:choose>
					
					<!-- 장바구니 조회 버튼 -->
					<a href="myCart" class="mypc-btn-hover">목록조회</a>
				</div>
			</article>
			
			
			<!-- 주문관리 -->
			<article class="col-lg-6 col-md-6 col-sm-6 col-xs-12">주문관리
				<div class="mypc-box">
					<h3>주문관리</h3>
					<c:choose>
					
						<%-- 주문상품 無 --%>
						<c:when test="${output3 == '[]'}">
							<div class="clearfix">
									<span><b>상품 이름</b></span>
									<span class="pull-right">없음</span>
								</div>
								<div class="clearfix">
									<span><b>가격</b></span>
									<span class="pull-right">없음</span>
								</div>
								<div class="clearfix">
									<span><b>구매 날짜</b></span>
									<span class="pull-right">없음</span>
								</div>
						</c:when>
						
						<%-- 주문상품 有 --%>
						<c:otherwise>
							<c:forEach var="item" items="${output3}" begin="${fn:length(output3)-1}" end="${fn:length(output3)}">
								
								<!-- 상품명 -->
								<div class="clearfix">
									<span><b>상품 이름</b></span>
									<span class="pull-right">${item.item_name}</span>
								</div>
								
								<!-- 상품가격 -->
								<div class="clearfix">
									<span><b>가격</b></span>
									<span class="pull-right">
										<c:set var="result" value="0"/>
												<c:set var="totalPrice" value="0"/>
												<c:set var="sale" value="0"/>
												<c:forEach var="i" begin="0" end="${fn:length(output3)-1}" step="1" >
													<c:choose>
														<c:when test="${(i==0 || item.ordered_num == output3[fn:length(output3)-i].ordered_num) && result==0}">
															<c:set var="totalPrice" value="${item.price*item.selected_count}"/>
															<c:if test="${output2 ne null}">
																<c:forEach var="coup" items="${output2 }">
																	<c:if test="${item.orderno == coup.enabled}">
																		<c:set var="sale" value="${totalPrice/100*coup.coupon_off}"/>
																	</c:if>
																</c:forEach>
															</c:if>
														</c:when>
														<c:otherwise>
															<c:set var="result" value="1"/>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											<fmt:formatNumber value="${totalPrice-sale}" type="int" pattern="#,###" />원
									</span>
								</div>
								
								<!-- 상품 주문 날짜 -->
								<div class="clearfix">
									<span><b>구매 날짜</b></span>
									<span class="pull-right">${fn:substring(item.ordered_date,0,10)}</span>
								</div>
							</c:forEach>
						</c:otherwise>
					</c:choose>
					
					<!-- 주문관리 조회 버튼 -->
					<a href="myOrder" class="mypc-btn-hover">상세조회</a>
				</div>
			</article>


			<!-- 쿠폰관리 -->
			<article class="col-lg-6 col-md-6 col-sm-6 col-xs-12">쿠폰
				<div class="mypc-box">
					<h3>쿠폰</h3>
					<c:choose>
						
						<%-- 쿠폰 無 --%>
						<c:when test="${output2 == '[]'}">
							<div class="clearfix">
							<span><b>쿠폰명</b></span>
							<span class="pull-right">쿠폰이 없습니다.</span>
						</div>
						</c:when>
						
						<%-- 쿠폰 有 --%>
						<c:otherwise>
							<c:forEach var="item" items="${output2}" end="3">
								<div class="clearfix">
									<span><b>쿠폰명</b></span>
									<span class="pull-right">${item.coupon_name}</span>
								</div>
							</c:forEach>
						</c:otherwise>
					</c:choose>
					
					<!-- 쿠폰관리 조회 버튼 -->
					<a href="myCoupon" class="mypc-btn-hover">쿠폰 조회</a>
				</div>
			</article>
		</div>
		
		<!-- 회원탈퇴 -->
		<form id="secession-form" method="post" action="index">
			<input type="hidden" name="membno" value="${output4.membno}"/>
			<button type="button" id="secession-btn" class="secession-btn mypc-btn-hover pull-right" >회원탈퇴</button>
		</form>
	</nav>
	<!---------------Content 끝--------------->

	
	<%@ include file="./inc/footer.jsp"%>
	<%@ include file="./inc/userScript.jsp"%>
	<script src="${pageContext.request.contextPath }/assets/js/myPage.js"></script> 
	<script type="text/javascript">
		$(function(){
			userCommon(); // 상단 메뉴바의 기능을 구현
		});
	</script>
</body>
</html>