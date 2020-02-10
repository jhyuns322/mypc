<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="inc/head.jsp"%>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/myInfo.css" />
</head>
<body>
	<%@ include file="./inc/userHeader.jsp"%>

	<!-- 컨텐츠 -->
	<section class="container contents">

		<form class="row" role="form" method="POST" id="edit_info" action="${pageContext.request.contextPath}/myPage">
		
			<!-- 페이지 헤더 -->
			<div class="page-header mypc-margin"><div class="mypc-icon pull-right" onclick="location.href='myPage'"><span class="glyphicon glyphicon-home"></span></div>
				<h1>회원정보 수정</h1>
			</div>

			<!-- 비밀번호 타이틀 -->
			<div class="mypc-layout01 hidden-xs col-sm-3 col-md-3 col-lg-3">
				<div>비밀번호</div>
			</div>

			<!-- 비밀번호 수정 -->
			<div class="mypc-layout02  col-sm-9 col-md-9 col-lg-9" >
				<fieldset class="pull-left col-sm-12 col-md-12 col-lg-12">
					<label>비밀번호 변경</label>
					<input type="password" name="mypc_now_pw" id="mypc_now_pw" class="mypc_now_pw" placeholder="현재 비밀번호" />
					<br />
					<input type="password" name="mypc_ch_pw" class="mypc_ch_pw" id="mypc_ch_pw" placeholder="새 비밀번호" />
					<input type="password" name="mypc_ch_pw_re" class="mypc_ch_pw_re" placeholder="새 비밀번호 확인" />
					<span class="clearfix"></span>
					<input type="hidden" name="now-pw" id="now-pw" value="${output.user_pw }"/>
				</fieldset>
			</div>

			<!-- 이메일 타이틀 -->
			<div class="mypc-layout01 hidden-xs col-sm-3 col-md-3 col-lg-3">
				<div>이메일</div>
			</div>

			<!-- 이메일 수정 -->
			<div class="mypc-layout02  col-sm-9 col-md-9 col-lg-9">
				<fieldset class="pull-left col-sm-12 col-md-12 col-lg-12">
					<span>이메일 변경</span> 
					<span ><b>현재 이메일</b><br/>${output.email}</span> 
					<input type="email" id="mypc_ch_email"  name="mypc_ch_email" class="mypc_ch_email" placeholder="변경할 이메일" />
					<input class="now_email" id="now_email" name="now_email" type="hidden" value="${output.email}" />
					<div></div>
					<span class="clearfix"></span>
				</fieldset>
			</div>


			<!-- 연락처 타이틀 -->
			<div class="mypc-layout01 hidden-xs col-sm-3 col-md-3 col-lg-3">
				<div>연락처</div>
			</div>

			<!-- 연락처 수정 -->
			<div class="mypc-layout02 col-sm-9 col-md-9 col-lg-9" >
				<fieldset class="pull-left col-sm-12 col-md-12 col-lg-12">
					<label>연락처 변경</label>
					<p>${output.tel}</p>
					<input type="tel" name="mypc_ch_tel" class="mypc_ch_tel" placeholder="변경할 연락처" />
					<div></div>
					<span class="clearfix"></span>
				</fieldset>
			</div>

			<!-- 주소 타이틀 -->
			<div class="mypc-layout01 addr-box-border hidden-xs col-sm-3 col-md-3 col-lg-3">
				<div>주소 (우편번호)</div>
			</div>

			<!-- 주소 수정 -->
			<div class="mypc-layout02 addr-box  col-sm-9 col-md-9 col-lg-9">
				<fieldset class="mypc-addr-box pull-left col-sm-12 col-md-12 col-lg-12">
					<label>주소지 변경</label>
					<p>${addr1_postcode}</p>
					<c:choose>
						<c:when test="${addr2_extraAddr == 'null' || output.addr1 == ''}">
							<span>${addr1}</span><br/>
							<span>${addr2}</span> <br/>
						</c:when>
						<c:otherwise>
							<span>${addr1}${addr2_extraAddr}</span><br/>
							<span>${addr2}</span> <br/>
						</c:otherwise>
					</c:choose>
					<input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기">
					<input type="text" name="mypc_postcode" class="mypc_postcode" id="sample6_postcode" placeholder="우편번호" readonly>
					<input type="text" name="mypc_addr1" class="mypc_addr1"  id="sample6_address" placeholder="주소" readonly><br>
					<input type="text" name="mypc_addr2" class="mypc_addr2"  id="sample6_detailAddress" placeholder="상세주소">
					<input type="text" name="mypc_extraAddr" class="mypc_extraAddr"  id="sample6_extraAddress" placeholder="참고항목" readonly>
				</fieldset>
			</div>
			
			<!-- 수정 버튼 -->
			<button type="submit" class="btn mypc-btn">수정</button>
		</form>
	</section>

	<%@ include file="./inc/footer.jsp"%>
	<%@ include file="./inc/userScript.jsp"%>
	<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script src="assets/js/myInfo.js"></script>
	<script type="text/javascript">
		$(function() {
			var contextPath = "${pageContext.request.contextPath}";
			userCommon(); // 상단 메뉴바의 기능을 구현
			infoVali(contextPath);
		});	
	</script>
</body>

</html>