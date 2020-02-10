<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>

<!DOCTYPE html>
<html lang="ko">
<%@ include file="./inc/head.jsp"%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/adminIndex.css" />
</head>

<body>
	<%@ include file="./inc/adminHeader.jsp"%>
	
	<!-- Content 시작 -->
	<nav id="contents" class="container">
		<div class="mypc-title col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<h2>관리자 메뉴</h2>
		</div>

		<!-- Menu_Container 시작 -->
		<article class="mypc-menucontainer col-lg-3 col-md-3 col-sm-6 col-xs-12">
			<div onclick="location.href ='adminMember'" style="cursor: pointer;" class="mypc-menubox">
				<div>전체 회원 수 : <span class="mypc-font-color"><strong>${outputMember}</strong></span> 명</div>
				<span class="glyphicon glyphicon-user mypc-padding"></span>
			    <div><h4>회원관리</h4></div>
			</div>
		</article>

		<article class="mypc-menucontainer col-lg-3 col-md-3 col-sm-6 col-xs-12">
			<div onclick="location.href ='adminInquiry'" style="cursor: pointer;" class="mypc-menubox">
				<div>미답변 문의 수 : <span class="mypc-font-color"><strong>${outputReplied}</strong></span> 건</div>
				<span class="glyphicon glyphicon-envelope mypc-padding"></span>
			    <div><h4>문의관리</h4></div>
			</div>
		</article>


		<article class="mypc-menucontainer col-lg-3 col-md-3 col-sm-6 col-xs-12">
			<div onclick="location.href ='adminItem'" style="cursor: pointer;" class="mypc-menubox">
				<div>&nbsp;</div>
				<span class="glyphicon glyphicon-credit-card mypc-padding"></span>
			    <div><h4>상품관리</h4></div>
			</div>
		</article>

		<article class="mypc-menucontainer col-lg-3 col-md-3 col-sm-6 col-xs-12">
			<div onclick="location.href ='adminStats'" style="cursor: pointer;" class="mypc-menubox">
				<div>&nbsp;</div>
				<span class="glyphicon glyphicon-stats mypc-padding"></span>
				<div><h4>통계</h4></div>
			</div>
		</article>
	</nav>
	<!-- Content 끝 -->

	<%@ include file="./inc/footer.jsp"%>
	<%@ include file="./inc/adminScript.jsp"%>
	<script type="text/javascript">
		$(function() {
			adminCommon(); // 로그아웃
		});
	</script>
</body>
</html>