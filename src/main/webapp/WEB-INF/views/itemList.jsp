<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="./inc/head.jsp"%>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/plugins/animate/animate.css" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/itemList.css" />
</head>
<!-- Head -->

<body>
	<%@ include file="./inc/userHeader.jsp"%>


	<section class="contents clearfix">
		<div class="container-fluid">
			<img src="${pageContext.request.contextPath}/download?file=itemList_Pic1.jpeg" alt="itemList" class="img-responsive mypc-img">
		</div>

		<hr />
		<!-- 상품목록 -->
		<div class="container-fluid">
			<!-- 상품 옵션 선택 -->
			<div id="mypc-div01" class="container common-hide">
				<div class="row mypc-option">
					<div class="row">
						<button type="button" class="btn btn-default btn-xs pull-right mypc-clear">검색 초기화</button>
					</div>
				</div>
			</div>

			<!-- 상품 정렬 및 검색 -->
			<div id="mypc-div02" class="container mypc-div02 common-hide">
				<ul class="row mypc-ul02">
					<li class="col-md-2 col-xs-6"><a data-align="1" href="#">인기상품</a></li>
					<li class="col-md-2 col-xs-6"><a data-align="2" href="#">신상품</a></li>
					<li class="col-md-2 col-xs-6"><a data-align="3" href="#">낮은가격</a></li>
					<li class="col-md-2 col-xs-6"><a data-align="4" href="#">높은가격</a></li>
					<li class="col-md-4 col-xs-6">
						<form class="form-inline mypc-form" role="form">
							<input class="form-control mypc-input" type="text" /> ~ <input
								class="form-control mypc-input" type="text" /> 원
							<button class="btn btn-default btn-xs mypc-btn">검색</button>
						</form>
					</li>
				</ul>
			</div>

			<!-- 상품 목록 -->
			<div id="mypc-div03" class="container common-hide">
				<ul id="mypc-ul03" class="row mypc-ul03"></ul>
			</div>

			<!-- 상품 카테고리 -->
			<div id="mypc-div04" class="container common-hide">
				<div class="row mypc-div05">
					<div class="col-md-11 box">
						<ul>
							<li><strong>CPU</strong> <span>Category CPU</span>
								<img src="${pageContext.request.contextPath}/download?file=cpu1.jpg" data-submenu="CPU" /></li>
						</ul>
						<ul>
							<li><strong>VGA</strong> <span>Category VGA</span>
								<img src="${pageContext.request.contextPath}/download?file=VGA1.jpg" data-submenu="VGA" /></li>
						</ul>
						<ul>
							<li><strong>Mainboard</strong> <span>Category Mainboard</span>
								<img src="${pageContext.request.contextPath}/download?file=mainboard.jpg" data-submenu="Mainboard" /></li>
						</ul>
						<ul>
							<li><strong>RAM</strong> <span>Category RAM</span>
								<img src="${pageContext.request.contextPath}/download?file=RAM1.jpg" data-submenu="RAM" /></li>
						</ul>
						<ul>
							<li><strong>SSD</strong> <span>Category SSD</span>
								<img src="${pageContext.request.contextPath}/download?file=ssd.jpeg" data-submenu="SSD" /></li>
						</ul>
						<ul>
							<li><strong>HDD</strong> <span>Category HDD</span>
								<img src="${pageContext.request.contextPath}/download?file=hdd.jpg" data-submenu="HDD" /></li>
						</ul>
					</div>
				</div>
			</div>

			<div id="mypc-div06" class="container">
				<div class="row text-center">
					<ul class="pagination"></ul>
				</div>
			</div>
		</div>
	</section>


	<%@ include file="./inc/footer.jsp"%>
	<%@ include file="./inc/userScript.jsp"%>
	<script src="${pageContext.request.contextPath}/assets/plugins/handlebars/handlebars-v4.3.1.js"></script>
	<script src="${pageContext.request.contextPath}/assets/plugins/number/jquery.number.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/plugins/animate/jquery.animatecss.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/itemList.js"></script>

	<!-- template -->
	<!-- 옵션선택 -->
	<script id="mypc-template01" type="text/x-handlebars-template">
		{{#each item.2.spec}}
			{{getItemOption this}}
		{{/each}}
	</script>

	<!-- 상품목록 -->
	<script id="mypc-template02" type="text/x-handlebars-template">
		{{#item}}
			<li class="col-md-2 col-md-offset-1 center-block">
				<a href="#" onclick="ajaxRedirect({{itemno}}); return false;">
					<img class="img-responsive center-block mypc-img" src="${pageContext.request.contextPath}/download?file={{item_imgthumb}}" alt="상품" />
				</a>	
			</li>

			<li class="col-md-5">
				<h4><a href="#" onclick="ajaxRedirect({{itemno}}); return false;">{{item_name}}</a></h4>
				<h5>{{itemManufac}}:{{spec}}</h5>
				<h6>등록월:{{reg_date}}</h6>
			</li>
			<li class="col-md-2 mypc-price">
				<h4>
					{{formatNumber price}}원
				</h4>
			</li>
			<li class="col-md-3 mypc-buy">
				<button type="button" data-itemno="{{itemno}}" class="btn btn-primary mypc-cart">장바구니</button>
				<button type="button" data-itemno="{{itemno}}" class="btn btn-primary mypc-addItem">구매하기</button>
			</li>
		{{/item}}
	</script>

	<!-- index_temp 스크립트 -->
	<script type="text/javascript">
		$(function() {
			var sessionUserId = "${sessionUserId}";	 // 사용자의 세션 값
			var show = "${showCategory}";			 // show 값에 따라 화면 분기
			var keyword = "${keyword}"; 			 // 선택된 카테고리의 값
			var search = "${search}"; 				 // 검색한 검색어의 값
			var path = "${pageContext.request.contextPath}/itemListGet?keyword=" + keyword + "&search=" + search; // ajax 요청 경로
			userCommon();							 // 상단 메뉴바의 기능을 구현
			viewChange(show, path, keyword); 		 // show 값에 따라 View 화면 분기처리
			categoryRedirect();						 // keyword 값이 NULL인 경우 카테고리를 선택하여 페이지 전환
			addCart(sessionUserId); 				 // 상품 장바구니 추가 기능
			addItem(sessionUserId);					 // 상품 구매하기 기능
			cbxSelect(path); 						 // 상품 옵션 선택 기능
			itemPrice(path); 						 // 상품 가격대 검색 기능
			alignSelect(path); 						 // 상품 정렬 기능
		});
	</script>
</body>
</html>