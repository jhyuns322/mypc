<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="inc/head.jsp"%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/pay.css" />
</head>
<body>
	<%@ include file="./inc/userHeader.jsp"%>

	<!-- Content -->
	<section class="container content">
	
		<!-- page-header -->
		<div class="page-header mypc-margin"><div class="mypc-icon pull-right" onclick="location.href='myPage'"><span class="glyphicon glyphicon-home"></span></div>
			<h1>결제정보</h1>
		</div>
		
		<div class="row">
		
			<!-- 구매 절차 -->
			<div class="mypc-cart-step clearfix">
				<div class="pull-right">
					<span class="mypc-big-font">01</span> <span class="mypc-small-font">상품정보</span>
					<span class="mypc-big-font"> <font color="red">&#62;02</font></span> 
					<span class="mypc-small-font"> <font color="red">주문/결제</font></span> 
					<span class="mypc-big-font">&#62;03</span> 
					<span class="mypc-small-font">주문완료</span>
				</div>
			</div>
			
			<!-- 경고문 -->
			<article class="cautions" >
				<ul>
					<li>가격은 언제나 변동될 수 있으니 주의하시기 바랍니다.</li>
					<li>배송날짜는 다양한 이유로 지연될 수 있습니다.</li>
					<li>단순 변심으로 인한 환불은 불가입니다.</li>
				</ul>
			</article>
			
			<!-- 페이 폼 -->
			<form id="pay_form" method="post" action="myOrder" >
			
			<!-- itemList or myCart 확인 -->
			<c:choose>
				<c:when test="${item_count != null && item_count != 0}">
					<input name="itemnum" value="${output[0].itemno}" type="hidden"/>
				</c:when>	
				<c:otherwise>
					<input name="cartnum" value="${cartnum}" type="hidden"/>
				</c:otherwise>
			</c:choose> 
			<input name="coupno" type="hidden"/>
			
			<!-- 상품 정보 -->
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mypc-price-box row">
				<table class="col-md-12">
					<thead>
						<tr>
							<th>상품정보/수량</th>
							<th>쿠폰</th>
							<th>상품금액</th>
						</tr>
					</thead>
					<tbody>
					
					<!-- 결제상품 리스트 -->
					<c:forEach var="item" items="${output}" varStatus="status">
						<tr>
							<td>
								<div>
									<!-- 상품 이미지 -->
									<c:url value="/download" var="item_img">
										<c:param name="file" value="${item.item_imgthumb}" />
									</c:url>
									<div class="mypc-item-img">
										<img src="${item_img}">
									</div>
									<!-- 상품 정보(상품명, 스펙, 수량에 따른 가격) -->
									<div class="mypc-info">
										<div class="mypc-title">${item.item_name}</div>
										<div>${item.spec}</div>
										<c:choose>
											<c:when test="${item_count != null && item_count != 0}">
												<span><fmt:formatNumber value="${item.price}" pattern="#,###"/></span> <span>원</span>
											</c:when>	
											<c:otherwise>
												<span><fmt:formatNumber value="${item.price*item.selected_count}" pattern="#,###"/></span> <span>원</span>
											</c:otherwise>
										</c:choose> 
									</div>
									<!-- 상품 수량 -->
									<div class="mypc-quantity">
										<span>
										<c:choose>
											<c:when test="${item_count != null && item_count != 0}">
												${item_count}개
											</c:when>	
											<c:otherwise>
												${item.selected_count}개
											</c:otherwise>
										</c:choose> 
										</span>
									</div>
								</div>
							</td>
							<!-- 선택쿠폰 정보 -->
							<td id="coup_table${status.index}" class="mypc-selected-coupon">
								<span>이름</span>
								<br/>
								<span>(-)</span>
								<span id="coup_price${status.index}">0</span>
								<span>원</span>
								<br/>
								<input id="coupno${status.index}" name="coupno${status.index}" class="coupno" type="hidden" value="0"/>
								<button type="button" class="mypc-coupon-btn mypc-pay-btn btn
								 col-xs-offset-0 col-sm-offset-0 col-md-offset-2 col-lg-offset-3" 
								 value="${status.index}">쿠폰 선택</button>
							</td>
							<!-- 단일 상품 금액 -->
							<td class="mypc-one-price">
								<c:choose>
									<c:when test="${item_count != null && item_count != 0}">
										<span id="single_sale_price${status.index}"><fmt:formatNumber value="${item.price}" pattern="#,###"/></span>
									</c:when>	
									<c:otherwise>
										<span id="single_sale_price${status.index}"><fmt:formatNumber value="${item.price*item.selected_count}" pattern="#,###"/></span>
									</c:otherwise>
								</c:choose> 
								<span>원</span>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			
			<!-- 수령인 정보 -->
			<div class="mypc-pay-info-box row">
				<div class="col-xs-12 col-sm-7 col-md-7 col-lg-8 mypc-recipient">
					<table class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<caption class="mypc-title-name">결제 정보</caption>
						<tbody>
							<!-- 수령인 이름 -->
							<tr>
								<th>수령인</th>
								<td>
									<div class="info-oder-user">
										<input type="text" name="recipient" id="name" value="${output1.name}">
									</div>
								</td>
							</tr>
							<!-- 배송지 선택 버튼 -->
							<tr>
								<th>배송지선택</th>
								<td>
									<div id="mypc_address_btn">
										<ul>
											<li><a class="mypc-pay-btn mypc-address-btn btn" href="#">
											기본 배송지</a></li>
											<li><a class="mypc-pay-btn mypc-address-btn btn" href="#">
											새로운 배송지</a></li>
										</ul>
									</div>
								</td>
							</tr>
							<!-- 수령인 연락처 -->
							<tr>
								<th>연락처</th>
								<td>
									<div class="mypc-phone">
										<select class="mypc-number" name="mypc_number">
											<option value="">&nbsp;번호선택</option>
											<option value="010" selected="selected">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;010</option>
										</select> 
										<b> - </b> 
										<input type="text" class="mypc-number" name="mypc_number" maxlength="4" max="4" min="3" value="${fn:substring(output1.tel,3,7) }"/>
										<b> - </b> 
										<input type="text" class="mypc-number" name="mypc_number" maxlength="4" max="4" min="4" value="${fn:substring(output1.tel,7,11) }"/>
									</div>
								</td>
							</tr>
							<!-- 배송지 입력 -->
							<tr>
								<th>주소지</th>
								<td>
									<div class="mypc-address">
										<!-- 기존 배송지(숨김) -->
										<input type="hidden" id="postcode" value="${addr1_postcode}" />
										<input type="hidden" id="addr1" value="${addr1}" />
										<input type="hidden" id="addr2" value="${addr2}" />
										<c:choose>
											<c:when test="${addr2_extraAddr != 'null'}">
												<input type="hidden" id="extra" value="${addr2_extraAddr}" />
											</c:when>
											<c:otherwise>
												<input type="hidden" id="extra" value="" />
											</c:otherwise>
										</c:choose>
										<!-- 배송지 입력란 -->
										<input type="text" name="order_postcode" class="mypc_postcode" id="sample6_postcode" placeholder="우편번호" readonly value="${addr1_postcode}">
										<input type="text" name="order_addr1" class="mypc_addr1"  id="sample6_address" placeholder="주소" value="${addr1}" readonly>
										<input type="text" name="order_addr2" class="mypc_addr2"  id="sample6_detailAddress" placeholder="상세주소" value="${addr2}" > 
										<c:choose>
											<c:when test="${addr2_extraAddr != 'null'}">
												<input type="text" name="order_extraAddr" class="mypc_extraAddr"  id="sample6_extraAddress" placeholder="참고항목" readonly value="${addr2_extraAddr}"><br>
											</c:when>
											<c:otherwise>
												<input type="text" name="order_extraAddr" class="mypc_extraAddr"  id="sample6_extraAddress" placeholder="참고항목" readonly value=""><br>
											</c:otherwise>
										</c:choose>
										<input type="button" class="mypc-pay-btn btn" onclick="sample6_execDaumPostcode()" value="우편번호 찾기" >
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<!-- 최종 결제 금액 -->
				<div class="col-xs-12 col-sm-5 col-md-5 col-lg-4 mypc-last-pay-box">
					<div class="pay-right-container">
						<h4 class="mypc-price-title">결제 금액</h4>
						<div class="mypc-user-order">
							<div>
								<ul>
									<li>
										<span class="mypc-price-title01">상품 금액</span>
										<span class="mypc-price"><fmt:formatNumber value="${total_price}" pattern="#,###"/> 원</span>
								 	</li>
									<li>
										<span class="mypc-price-title01">배송비</span>
										<span class="mypc-price">무료</span>
									</li>
									<li>
										<div>
											<span class="mypc-price-title01">할인 금액</span> 
											<span id="total_sale_price" class="mypc-price"><span>0</span>원</span>
										</div>
									</li>
								</ul>
								<br />
								<div>
									<dl>
										<dt  class="mypc-price-list-title">최종 결제금액</dt>
										<dd>
											<span>원</span>
											<span id="total_result_price" class="mypc-price-list"><fmt:formatNumber value="${total_price}" pattern="#,###"/></span>
										</dd>
									</dl>
								</div>
							</div>
						</div>
						<a href="#" class="order-btn">결제 하기</a>
					</div>
				</div>
			</div>
			</form>
		</div>
	</section>
	
	<!-- 쿠폰 모달 -->
	<div id="coupon_modal" class="mypc-coupon-modal col-xs-12 col-sm-12 col-ms-12 col-lg-12"></div>
	<div id="fixed_box" class="mypc-fixed-box">
		<div class="mypc-hidden-box hidden-xs col-sm-2 col-ms-2 col-lg-2"></div>
		<div id="white_box" class="mypc-white-box col-xs-12 col-sm-8 col-ms-8col-lg-8">		
			<div class="mypc-scroll">
				<table class="mypc-select-coupon">
					<thead>
						<tr>
							<th class="col-xs-6 col-sm-6 col-ms-6 col-lg-6">쿠폰이름</th>
							<th class="col-xs-4 col-sm-4 col-ms-4 col-lg-4">유효기간</th>
							<th class="col-xs-2 col-sm-2 col-ms-2 col-lg-2">할인금액</th>
						</tr>
						<tr id="coupon_null_btn">
							<td>쿠폰을 사용하지 않음</td>
							<td></td>
							<td></td>
						</tr>
					</thead>
					<tbody >
					
						<!-- 쿠폰 유무 확인 -->
						<c:choose>
						
							<%-- 쿠폰이 있을 시 --%>
							<c:when test="${output_coup != null}">
								<c:forEach var="item" items="${output_coup}" varStatus="status">
									<tr class="mypc-coupon-box">
										<td class="lock-box${status.index}">${item.coupon_name}
											<input type="hidden" value="${item.coupno}"/>
											<input class="coup_order" type="hidden" value="${status.index}"/>
											<input class='lock' type="hidden" name="lock" value="0"/>
										</td>
										<td>${item.issue_date} ~<br/>${item.expired_date}</td>
										<td>
											<c:choose>
												<c:when test="${item.coupon_off<=50}">
													<span>${item.coupon_off}</span>%	
												</c:when>
												<c:otherwise>
													<span>${item.coupon_off}</span>원
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</c:forEach>
							</c:when>
							
							<%-- 쿠폰이 없을 시 --%>
							<c:when test="${output_coup == null}">
								<tr>
									<td>쿠폰이 없습니다.</td>
									<td></td>
									<td></td>
								</tr>
							</c:when>
						</c:choose>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	
	<%@ include file="./inc/footer.jsp"%>
	<%@ include file="./inc/userScript.jsp"%>
	<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script src="assets/js/pay.js"></script>
	<script type="text/javascript">
		$(function() {
			userCommon(); // 상단 메뉴바의 기능을 구현
		});
	</script>
	
</body>
</html>