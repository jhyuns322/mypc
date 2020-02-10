<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- 헤더영역 -->
<header>
	<nav class="navbar navbar-inverse navbar-fixed-top common-nav" role="navigation">
		<div class="container">
			<!-- 로고 영역 -->
			<div class="navbar-header common-nav-header">
				<!-- 반응형 메뉴 버튼 -->
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
					<span class="sr-only">메뉴열기</span> <span class="icon-bar"></span>
					<span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<c:url value="/" var="homeUrl" />
				<!-- 로고 이미지 사이즈 (110x45) -->
				<a class="navbar-brand common-navbar-brand" href="${homeUrl}">
					<img class="common-img-logo center-block" src="${pageContext.request.contextPath}/download?file=logo.png" />
				</a>
			</div>

			<!-- 메뉴 영역 -->
			<div class="navbar-collapse collapse">
				<!-- 메뉴항목 -->
				<ul id="common-nav-ul01" class="nav navbar-nav common-nav-ul01">
					<li><a data-submenu="sub01" href="adminLogin">관리자 페이지</a></li>
					<li><a data-submenu="sub02" href="itemList">상품목록</a></li>
					<li><a data-submenu="sub03" href="boardMain">게시판</a></li>
					<li><a data-submenu="sub04" href="support">고객센터</a></li>
				</ul>
				<form method="get" action="${pageContext.request.contextPath}/itemList" id="common-form" class="nav navbar-nav common-form common-hide" role="form">
					<input id="common-input" name="search" class="form-control pull-right common-input" type="text" />
				</form>
				<ul class="nav navbar-nav common-nav-ul02">
					<li>
						<a id="common-search" class="glyphicon glyphicon-search common-serach" href="#"></a>
					</li>
					<c:choose>
						<c:when test="${sessionUserId != null}">
							<li style="font-size:12px"><a id="logout" href="#">LogOut</a></li>
							<li style="font-size:12px"><a href="myPage"><strong>${sessionName}</strong>님</a></li>
						</c:when>
						<c:otherwise>
							<li style="font-size:12px"><a href="login">Login</a></li>
							<li style="font-size:12px"><a href="join">Join</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
			<!-- 서브메뉴 영역 -->
			<div id="common-submenu" class="navbar-collapse collapse">
				<ul id="common-submenu-ul01" class="nav navbar-nav common-submenu-ul01 common-hide"></ul>
			</div>
		</div>
	</nav>
	
	<!-- 사이드바(최근 본 상품) 영역 -->
	<div class="mypc-sideBar">
		<div class="mypc-sideBar-side">l
			<div>l
			</div>
		</div>
		<a href="#" class="top-btn">TOP</a>
		<div class="side-title">최근 본 상품</div>
		<form id="cookie_form" method="POST" class="sidebar-form" action="cookieList">
			<ul id="cookie_list" class="side_ul_list">
			</ul>
		</form>
		<a href="#" class="bottom-btn">BOTTOM</a>
	</div>
</header>