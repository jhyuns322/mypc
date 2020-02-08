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
				<!--// 반응형 메뉴 버튼 -->

				<!-- 로고 이미지 사이즈 (360x150) -->
				<c:choose>
					<c:when test="${sessionAdminId == 'mypc'}">
						<a class="navbar-brand common-navbar-brand" href="adminIndex">
							<img class="common-img-logo center-block" src="${pageContext.request.contextPath}/download?file=logo.png">
						</a>
					</c:when>
					<c:otherwise>
						<span class="navbar-brand common-navbar-brand">
							<img class="common-img-logo center-block" src="${pageContext.request.contextPath}/download?file=logo.png">
						</span>
					</c:otherwise>
				</c:choose>
			</div>
			<!--// 로고 영역 -->

			<!-- 메뉴 영역 -->
			<div class="navbar-collapse collapse">
				<!-- 메뉴항목 -->
				<ul id="common-nav-ul01" class="nav navbar-nav common-nav-ul01">
					<li><a data-submenu="sub01" href="aboutUs">About Us</a></li>
				</ul>
				<!--// 메뉴항목 -->
				<c:choose>
					<c:when test="${sessionAdminId == 'mypc'}">
						<ul class="nav navbar-nav common-nav-ul02">
							<li style="font-size:12px"><a id="logout" href="#">Logout</a></li>
						</ul>
					</c:when>
					<c:otherwise>
						<ul class="nav navbar-nav common-nav-ul02">
							<li></li>
						</ul>
					</c:otherwise>
				</c:choose>
			</div>
			<!-- 서브메뉴 영역 -->
			<div id="common-submenu" class="navbar-collapse collapse">
				<ul id="common-submenu-ul01" class="nav navbar-nav common-submenu-ul01 common-hide"></ul>
			</div>
			<!--// 서브메뉴 영역 -->
		</div>
	</nav>
</header>
<!--// 헤드 영역 -->