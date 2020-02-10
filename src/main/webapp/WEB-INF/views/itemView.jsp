<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="./inc/head.jsp"%>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/itemView.css" />
</head>
<!-- Head -->

<body>
	<%@ include file="./inc/userHeader.jsp"%>

	<section id="item_view-section" class="container content">
		<c:url value="/download" var="item_img1">
			<c:param name="file" value="${item.item_img1}" />
		</c:url>
		<c:url value="/download" var="item_img2">
			<c:param name="file" value="${item.item_img2}" />
		</c:url>
		<div class="page-header mypc-margin"><div class="mypc-icon pull-right" onclick="location.href='itemList'"><span class="glyphicon glyphicon-home"></span></div>
			<h1>상품 정보</h1>
		</div>

		<!-- 상품 정보 -->
		<table class="mypc-item-info col-xs-12 col-sm-12 col-md-12 col-lg-12 ">
			<tr>
				<!-- 상품 이미지 -->
				<td class="mypc-pic-box col-xs-2 col-sm-3 col-md-3 col-lg-3 ">
					<div class="mypc-pic"><img src="${item_img1 }" alt="사진1" class="img-responsive img-rounded"/></div>
				</td>
				
				<!-- 상품 기본 정보 -->
				<td class="mypc-info-box col-xs-10 col-sm-9 col-md-9 col-lg-9 mypc-buy">
					<h4>${item.item_name }</h4>
					<div>${item.manufac }</div>
					<h3><span><fmt:formatNumber value="${item.price}" pattern="#,###"/></span>원</h3>
					<button type="button" data-itemno="${item.itemno}" class="btn btn-primary pull-right mypc-addItem">구매하기</button>
					<button type="button" data-itemno="${item.itemno}" class="btn btn-primary pull-right mypc-cart">장바구니</button>
				</td>
			</tr>
		</table>
		<hr class="col-xs-12 col-sm-12 col-md-12 col-lg-12"/>
		
		<!-- 상품 상세 정보 -->
		<table class="mypc-detail-item-info col-xs-12 col-sm-12 col-md-12 col-lg-12 ">
			<caption>상품 상세 정보</caption>
			<tr class="mypc-item-name">
				<th><div>상품명</div></th>
				<td><div>${item.item_name }</div></td>
			</tr>
			<tr class="mypc-item-manufac">
				<th><div>회사명</div></th>
				<td><div>${item.manufac}</div></td>
			</tr>
			<tr class="mypc-item-spec">
				<th><div>상품 스펙</div></th>
				<td><div>${item.spec}</div></td>
			</tr>
			<tr class="mypc-item-rel">
				<th><div>상품 출시일</div></th>
				<td><div>${item.rel_date}</div></td>
			</tr>
		</table>
		
		<hr class="col-xs-12 col-sm-12 col-md-12 col-lg-12"/>
		
		<!-- 상품 이미지 정보 -->
		<div class="mypc-img-item-info">
			<img src="${item_img2 }" alt="사진2"/>
		</div>
	</section>

	<%@ include file="./inc/footer.jsp"%>
	<%@ include file="./inc/userScript.jsp"%>
	<script src="${pageContext.request.contextPath}/assets/js/itemView.js"></script>
	<script type="text/javascript">


		$(function() {
			var sessionUserId = "${sessionUserId}";	 // 사용자의 세션 값
			userCommon();							 // 상단 메뉴바의 기능을 구현
			addCart(sessionUserId); 				 // 상품 장바구니 추가 기능
			addItem(sessionUserId);					 // 상품 구매하기 기능
		});
	</script>

</body>
</html>