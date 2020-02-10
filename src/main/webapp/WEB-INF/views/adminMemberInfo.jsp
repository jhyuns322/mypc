<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>

<!DOCTYPE html>
<html lang="ko">
<%@ include file="./inc/head.jsp"%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/adminMemberInfo.css" />
</head>
<!-- Head 끝 -->
<body>
	<%@ include file="./inc/adminHeader.jsp"%>

	<!-- Content 시작 -->
	<section class="container content">
		<div class="row">
			<div class="page-header mypc-margin"><div class="mypc-icon pull-right" onclick="location.href='adminMember'"><span class="glyphicon glyphicon-list"></span></div>
				<h1>회원 관리</h1>
			</div>
			
			<div class="col-md-8 col-md-offset-2">
				<!-- Join Form 시작 -->
				<form id="memberInfo_form" class="join-form form-horizontal" role="form" method="get" action="${pageContext.request.contextPath}/adminMember">
					<fieldset>
					<input id="ckd_email" name="ckd_email" type="hidden" value="1" />
						<!-- 필수 입력사항  시작 -->
						<legend>필수 입력사항</legend>

						<!-- 아이디 입력 -->
						<div class="form-group">
							<span class="col-md-3"><label class="control-label" for="user_pw">아이디</label></span> 
							<span class="col-md-7" style="margin-top:3px">${output.user_id}</span>
						</div>
						
						<br />

						<!-- 이름 입력 -->
						<div class="form-group">
							<span class="col-md-3"><label class="control-label" for="name">이름</label></span>
							<span class="col-md-7"><input type="text" id="name" class="form-control" name="name" placeholder="이름을 입력하세요." value="${output.name}"></span>
						</div>
						
						<br />

						<!-- 생년월일 입력 -->
						<div class="form-group">
							<span class="col-md-3"><label class="control-label" for="birthdate">생년월일</label></span>
							<span class="col-md-7"><input type="date" id="birthdate" class="form-control" name="birthdate" placeholder="생년월일을 입력하세요." value="${output.birthdate}"></span>
						</div>
						<br />

						<!-- 성별 입력 -->
						<div class="form-group">
							<span class="col-md-4"><label class="control-label" for="gender">성별</label></span>
							<span class="radio-inline"><input type="radio" name="gender" value="m" <c:if test="${output.gender eq 'm'}"> checked</c:if>>남자</span>
							<span class="radio-inline"><input type="radio" name="gender" value="f" <c:if test="${output.gender eq 'f'}"> checked</c:if>>여자</span>
						</div>
						
						<br />

						<!-- 이메일 입력 -->
						<!-- 이메일 입력 -->
						<div class="form-group">
							<span class="col-md-3"><label class="control-label" for="email">이메일</label></span> 
							<span class="col-md-7"><input type="email" id="email" class="form-control" name="email" placeholder="이메일을 입력하세요." value="${output.email}"></span>
							
						<!-- 이메일 중복 검사 버튼 -->
							<span class="col-md-2"><button type="button" id="email_uniq_check" class="btn btn-primary" name="email_uniq_check">중복검사</button></span>
							<span class="col-md-2"><button type="button" id="edit_email" class="btn btn-primary" name="edit_email">수정하기</button></span>
						</div>

						<!-- 선택 입력사항 시작 -->
						<legend>선택 입력사항</legend>

						<!-- 주소 입력 -->
						<div class="form-group">
							<span class="col-md-3"><label class="control-label" for="addr1">주소</label></span> 
							<span class="col-md-2"><input type="text" id="postcode" class="form-control" name="addrPost" placeholder="우편번호" value="${addrPost}"readonly></span>
							<span class="col-md-5"><input type="text" id="address" class="form-control" name="addrRoad" placeholder="주소를 검색하세요." value="${addrRoad}" readonly></span>
							<span class="col-md-2"><button type="button" class="btn btn-primary" onclick="daumPostcode()" >주소검색</button></span>
						</div>
						<div class="form-group">
							<span class="col-md-3"><label class="control-label" for="addr2">상세 주소</label></span>
							<span class="col-md-5"><input type="text" id="detailAddress" class="form-control" name="addrDetail" placeholder="주소 검색 후 입력 가능합니다." value="${output.addr2}" readonly></span>
							<span class="col-md-2"><input type="text" id="extraAddress" class="form-control" name="addrExtra"  placeholder="참고항목" value="${addrDetail}" readonly></span>
						</div>
						
						<br />

						<!-- 연락처 입력 -->
						<div class="form-group">
							<span class="col-md-3"><label class="control-label" for="tel">연락처</label></span>
							<span class="col-md-7"><input type="tel" id="tel" class="form-control" name="tel" placeholder="연락처를 '-'없이 입력하세요." value="${output.tel}"></span>
						</div>
						
						<br />

						<!-- 삭제 -->
						<div class="col-md-offset-9 col-md-2">
							<button type="button" id="memberDelete" data-membno="${output.membno}" class="btn btn-primary boardWrite-btn">삭제하기</button>
						</div>
						<!-- 수정 -->
						<input type="hidden" id="membno" name="membno" value="${output.membno}">
						<div class="col-md-1"><button type="submit" class="form-group btn btn-primary">수정완료</button></div>
							
					</fieldset>
				</form>
			</div>
		</div>
	</section>
	<%@ include file="./inc/footer.jsp"%>
	<%@ include file="./inc/adminScript.jsp"%>
	<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script src="./assets/js/adminMemberInfo.js"></script>
	<script type="text/javascript">
	$(function() {
		var contextPath = "${pageContext.request.contextPath}";
		emailCheck();   			// 이메일 중복 검사
		memberInfoVali(); 			// 회원정보 수정 시 유효성 검사
		memberDelete(contextPath);  // 회원 삭제 기능을 구현.
		adminCommon(); 				// 로그아웃
	});
	</script>
</body>
</html>