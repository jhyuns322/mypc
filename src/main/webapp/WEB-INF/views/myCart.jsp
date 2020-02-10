<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="inc/head.jsp"%>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/myCart.css" />
</head>
<body>
	<%@ include file="./inc/userHeader.jsp"%>

	<!-- 컨텐츠 -->
	<section class="container content">

		<!-- 페이지 헤더 -->
		<div class="page-header mypc-margin"><div class="mypc-icon pull-right" onclick="location.href='myPage'"><span class="glyphicon glyphicon-home"></span></div>
			<h1>장바구니</h1>
		</div>

		<!-- 구매 절차 -->
		<div class="mypc-cart-step clearfix">
			<div class="pull-right">
				<span class="mypc-big-font"><font color="red">01</font></span> 
				<span class="mypc-small-font"><font color="red">장바구니</font></span> 
				<span class="mypc-big-font">&#62;02</span> 
				<span class="mypc-small-font">주문/결제</span>
				<span class="mypc-big-font">&#62;03</span> 
				<span class="mypc-small-font">주문완료</span>
			</div>
		</div>

		<!-- 경고문 -->
		<article class="mypc-cautions">
			<ul>
				<li>가격은 언제나 변동될 수 있으니 주의하시기 바랍니다.</li>
				<li>배송날짜는 다양한 이유로 지연될 수 있습니다.</li>
			</ul>
		</article>

		<!-- 카트_폼 [카트] -->
		<form id="cart_form" action="myCart">
			<input id="length" name="length" value="${output.size()}" type="hidden" />
			
			<!-- 상품에 대한 정보 -->
			<div class="mypc-product-desc clearfix row">

				<!-- 올 체크 -->
				<input type="checkbox" class="mypc-all-checked col-xs-1 col-sm-1 col-md-1 col-lg-1" /> 
				<span class="col-xs-2 col-sm-1 col-md-1 col-lg-1"><b>상품정보</b></span> 
				<span class="col-xs-1 col-sm-1 col-md-1 col-lg-1 col-xs-offset-6 col-sm-offset-7">수량</span>
				<span class="col-xs-2 col-sm-2 col-md-2 col-lg-2">상품금액</span>
			</div>


			<!-- 상품 정보 -->
			<section class="mypc-product-info">
				<c:choose>
				
					<%-- 장바구니가 비어있을시 --%>
					<c:when test="${output == null || output == '[]' }">
						<div class="mypc-empty-cart">장바구니가 비어있습니다.</div>
					</c:when>
					
					<%-- 장바구니에 상품이 있을시 --%>
					<c:otherwise>
						<c:forEach var="item" items="${output}" varStatus="status">
						
							<!-- 상품의 Cartno를 저장하고 있는 input -->
							<input id="cartno${status.index}" class="cartno" name="${status.index}" type="hidden" value="${item.cartno}" />
							<div class="mypc-product row">
								
								<!-- 상품의 체크박스 -->
								<input type="checkbox" name="mypc-chk-li" value="${item.cartno}" class="mypc-one-check col-xs-1 col-sm-1 col-md-1 col-lg-1" />

								<!-- 상품 정보 -->
								<div class="mypc-product-information col-xs-8 col-sm-8 col-md-8 col-lg-8">
								
									<!-- 상품 썸네일 -->
									<c:url value="/download" var="item_img">
										<c:param name="file" value="${item.item_imgthumb}" />
									</c:url>
									<div class="mypc-img-box pull-left"><img src="${item_img}"></div>
									
									<!-- 상품명 -->
									<div>${item.item_name}</div>
									<!-- 상품 제조사 -->
									<div>${item.manufac}</div>
									<!-- 상품 스펙 -->
									<div>${item.spec}</div>
								</div>

								<!-- 상품 수량 -->
								<div class="mypc-quantity col-xs-1 col-sm-1 col-md-1 col-lg-1 clearfix">
									<input id="mypc_quantity${status.index}" class="mypc-quantity-input" type="number" min="1" maxlength="99" name="mypc_quantity${status.index}" value="${item.selected_count}" disabled />
									<button id="mypc_quantity_btn${status.index}" class="mypc-quantity-btn mypc-btn btn pull-right" value="${status.index}">변경</button>
								</div>

								<!-- 상품 가격 -->
								<div class="mypc-product-price col-xs-2 col-sm-2 col-md-2 col-lg-2">
								
									<!-- 상품 수량에 따른 가격 -->
									<div>
										<fmt:formatNumber value="${item.price*item.selected_count}" pattern="#,###" />원
									</div>
									<span class="clearfix"></span>
									
									<!-- 반응형에 따른 주문, 삭제 버튼 -->
									<button type="button" class="del_num pull-right mypc-btn hidden-xs btn" value="${item.cartno}">삭제</button>
									<button type="button" class="mypc_order_btn mypc-btn pull-right hidden-xs btn" value="${item.cartno}">주문</button>

									<button type="button" class="del_num xs_btn mypc-btn visible-xs btn" value="${item.cartno}">삭제</button>
									<button type="button" class="mypc_order_btn mypc-btn xs_btn visible-xs btn" value="${item.cartno}">주문</button>
								</div>
							</div>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</section>
	</form>

		<!-- 페이_폼 [페이] -->
		<form id="pay_form" method="post" action="pay">
		
			<!-- Cartno를 갖게될 인풋 -->
			<input id="cartnum" type="hidden" name="cartnum"/>
			
			<!-- 선택된 상품들의 금액 합계 -->
			<section id="mypc-total-price" class="clearfix">
				
				<!-- 반응형에 따른 선택상품 삭제 버튼 -->
				<button type="button" class="mypc-delete-btn mypc-btn hidden-xs">선택상품 삭제</button>
				<div class="mypc-empty-box visible-xs"></div>
				<button type="button" class="mypc-delete-btn mypc-btn visible-xs">선택상품 삭제</button>
				
				<!-- 선택상품 주문 버튼 -->
				<button type="button" name="mypc-select-order" class="mypc-select-order-btn mypc-btn pull-right">선택상품 주문하기</button>
			</section>
		</form>
	</section>

	<%@ include file="./inc/footer.jsp"%>
	<%@ include file="./inc/userScript.jsp"%>
	<script src="assets/js/myCart.js"></script>

	<script type="text/javascript">
		$(function() {
			userCommon(); // 상단 메뉴바의 기능을 구현
		});
	</script>
</body>
</html>