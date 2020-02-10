<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>

<!DOCTYPE html>
<html lang="ko">
<%@ include file="inc/head.jsp"%>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/adminLogin.css" />
</head>
<!-- Head -->
<body>
	<%@ include file="./inc/adminHeader.jsp"%>

	<!-- content -->
	<section id="contents" class="container content">
		<!-- LOGIN 박스 -->
		<div class="mypc-login-box">
			<!-- LOGO -->
			<div class="mypc-login-box-logo">MY<font color="red">P</font>C<h4>For <font color="red">A</font>dministrator</h4></div>
			<!-- LOGIN FORM -->
			<form id="login_form" class="mypc-slide-box" role="form" method="post" action="${pageContext.request.contextPath}/adminIndex">
				<fieldset>
					<!-- ID 입력창 -->
					<div id="id_box" class="mypc-input-box">
						<input type="text" name="user_id" id="user_id" maxlength="20" />
						<span> 아이디 : mypc </span>
					</div>
					<!-- PW 입력창 -->
					<div id="pw_box" class="mypc-input-box">
						<input type="password" name="user_pw" id="user_pw" maxlength="20" />
						<span> 비밀번호 : 123qwe </span>
					</div>
					<!-- SUBMIT BTN -->
					<button type="submit" id="mypc-login-button" class="btn btn-dark btn-lg">LOGIN</button>
				</fieldset>
			</form>
		</div>
	</section>

	<%@ include file="./inc/footer.jsp"%>
	<%@ include file="./inc/adminScript.jsp"%>
	<script src="assets/js/adminLogin.js"></script>
	<script type="text/javascript">
		$(function() {
			loginVail();
			loginBoxEvent();
		});
	</script>
</body>
</html>