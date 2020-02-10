<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="./inc/head.jsp"%>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/plugins/billboard/billboard.min.css" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/plugins/datepicker/datepicker.min.css" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/adminStats.css" />
</head>
<!-- Head 끝 -->
<body>
	<%@ include file="./inc/adminHeader.jsp"%>
	
	<!-- Content -->
	<section class="container content">
		<div class="row">
			<div class="page-header mypc-margin">
				<div class="mypc-icon pull-right"
					onclick="location.href='adminIndex'">
					<span class="glyphicon glyphicon-home"></span>
				</div>
				<h1>관리자 통계</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12 col-xs-12 mypc-menu">
				<ul class="nav nav-tabs mypc-ul">
					<li class="active" data-mypc="1"><a href="#chart"
						data-toggle="tab">카테고리별 판매량</a></li>
					<li data-mypc="2"><a href="#chart" data-toggle="tab">카테고리별 상품 등록 수</a></li>
					<li data-mypc="3"><a href="#chart" data-toggle="tab">기간별 상품 판매 금액</a></li>
				</ul>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6 col-xs-6 mypc-chart1">
				<div id="mypc-chart1"></div>
			</div>
			<div class="col-md-6 col-xs-6 mypc-chart2">
				<div id="mypc-chart2"></div>
			</div>
			<div class="col-md-12 col-xs-12 mypc-chart3 common-hide">
				<form class="form-inline" id="mypc-formChart" role="form">
					<fieldset>
						<div class="form-group col-md-2 col-xs-4 text-center">
							<label for="sel_date1">기간선택 : </label>
						</div>
						<div class="input-group col-md-4 col-xs-6">
							<input type="text" name="sel_date1" id="sel_date1"
								class="form-control" readonly /><span id="mypc-showCal1"
								class="input-group-addon"><span
								class="glyphicon glyphicon-calendar"></span></span>
						</div>
						~
						<div class="input-group col-md-4 col-xs-6">
							<input type="text" name="sel_date2" id="sel_date2"
								class="form-control" readonly /> <span id="mypc-showCal2"
								class="input-group-addon"><span
								class="glyphicon glyphicon-calendar"></span></span>
						</div>
						<button type="submit" class="btn btn-default mypc-chartBtn">조회</button>
					</fieldset>
				</form>
				<div id="mypc-chartBox" class="mypc-chartBox"></div>
			</div>
		</div>
	</section>

	<%@ include file="./inc/footer.jsp"%>
	<%@ include file="./inc/adminScript.jsp"%>
	<script src="https://d3js.org/d3.v5.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/plugins/billboard/billboard.min.js"></script>
	<script src="https://www.amcharts.com/lib/4/core.js"></script>
	<script src="https://www.amcharts.com/lib/4/charts.js"></script>
	<script src="https://www.amcharts.com/lib/4/themes/animated.js"></script>
	<script src="${pageContext.request.contextPath}/assets/plugins/datepicker/datepicker.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/plugins/datepicker/i18n/datepicker.ko-KR.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/adminStats.js"></script>
	<script type="text/javascript">
		$(function() {
			adminCommon(); // 로그아웃
			datePicker();
			loadChart("adminStatsGet/1");

		});
	</script>

</body>
</html>