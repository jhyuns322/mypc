<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="inc/head.jsp"%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login.css" />
</head>

<body>
	<%@ include file="./inc/userHeader.jsp"%>

	<!-- content  -->
	<section id="contents" class="container content">
		<!-- login box -->
		<div class="mypc-login-box">
			<!-- logo -->
			<div class="mypc-login-box-logo">MY<font color="red">P</font>C</div>
			
			<!-- login form -->
			<form id="login_form" class="mypc-slide-box form-horizontal" role="form" method="post" action="${pageContext.request.contextPath}/index">
				<fieldset>
					<!-- ID 입력창 -->
					<div id="id_box" class="mypc-input-box form-group">
						<input type="text" id="user_id" name="user_id" class="form-control" maxlength="20" />
						<span> 아이디를 입력하세요. </span>
					</div>	
					<!-- PW 입력창 -->
					<div id="pw_box" class="mypc-input-box form-group">
						<input type="password" id="user_pw" name="user_pw" class="form-control" maxlength="20" />
						<span> 비밀번호를 입력하세요. </span>
					</div>
				</fieldset>
					<!-- submit button -->
					<button type="submit" id="mypc-login-button" class="btn btn-dark btn-lg">LOGIN</button>
					<!-- find ID/PW link -->
					<div id="mypc-find-info">
						<span><a href="#" id="find_info_btn"><strong>아이디&nbsp;&nbsp;</strong></a></span> / <span><a href="#"><strong>&nbsp;&nbsp;비밀번호 </strong></a> 찾기</span>
					</div>
			</form>
			
			<!-- FindId form -->
			<form  id="findId_form" class="mypc-slide-box form-horizontal" role="form" method="post" action="${pageContext.request.contextPath}/login" >
				<div class="mypc-login-box-logo">가입 시 등록한 이메일을 입력하세요.</div>
				<fieldset id="mypc-find-id-box" class="form-group">
					<input type="text" name="email" id="mypc_find_id" class="form-control" maxlength="40" />
					<span> e-mail을 입력해주세요. </span>
				</fieldset>
				<!-- submit button -->
				<button type="submit" class="btn btn-dark btn-lg">이메일로 발송</button>
				<div class="mypc-back-btn"><span><a href="#"><strong>뒤로가기</strong></a></span></div>
			</form>
			
			<!-- FindPwCheckID form -->
			<form  id="findPwCI_form" class="mypc-slide-box form-horizontal" role="form" method="post" action="${pageContext.request.contextPath}/login" >
				<div class="mypc-login-box-logo">가입 시 등록한 아이디를 입력하세요.</div>
				<fieldset id="mypc-find-pw-box" class="form-group">
					<input type="text" name="user_id" id="mypc_find_pw" class="form-control" maxlength="20" value="" />
					<span> 아이디를 입력하세요. </span>
				</fieldset>
				<!-- submit button -->
				<button type="submit" class="btn btn-dark btn-lg">다음</button>
				<div class="mypc-back-btn"><span><a href="#"><strong>뒤로가기</strong></a></span></div>
			</form>

			<!-- FindPwSendEmail form -->
			<form  id="findPwSE_form" class="mypc-slide-box form-horizontal" role="form" method="post" action="${pageContext.request.contextPath}/login" >
				<div class="mypc-login-box-logo">가입 시 등록한 이메일을 입력하세요.</div>
				<fieldset id="mypc-find-pw-box01" class="form-group">
					<input type="text" name="email" id="mypc_find_pw01" class="form-control" maxlength="40" />
					<span> 이메일을 입력하세요. </span>
				</fieldset>
				<!-- submit button -->
				<button type="submit" class="btn btn-dark btn-lg">임시비밀번호 발송</button>
				<div class="mypc-back-btn"><span><a href="#"><strong>뒤로가기</strong></a></span></div>
			</form>
			
		</div>
	</section>
	
	<%@ include file="./inc/footer.jsp"%>
	<%@ include file="./inc/userScript.jsp"%>
	<script src="./assets/js/login.js"></script>
	<script type="text/javascript">
		$(function() {
			userCommon(); // 상단 메뉴바의 기능을 구현
		});
	</script>
</body>
</html>