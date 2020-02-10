<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>

<!DOCTYPE html>
<html lang="ko">
<%@ include file="./inc/head.jsp"%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/adminMember.css" />
</head>

<body>
	<%@ include file="./inc/adminHeader.jsp"%>

	<!-- Content 시작 -->
	<section class="container content">
		<div class="row">
			<div class="page-header mypc-margin">
				<div class="mypc-icon pull-right"
					onclick="location.href='adminIndex'">
					<span class="glyphicon glyphicon-home"></span>
				</div>
				<h1>회원 관리</h1>
			</div>
			<!-- 검색폼 -->
			<form class="mypc-searchForm" method="get" action="${pageContext.request.contextPath}/adminMember">
				<span class="col-md-2 col-md-offset-6">
					<select name="subKeyword" class="form-control">
						<option value="">전체</option>
						<option value="m" <c:if test="${gender eq 'm'}"> selected</c:if>>남성</option>
						<option value="f" <c:if test="${gender eq 'f'}"> selected</c:if>>여성</option>
					</select>
				</span>
				<label for="keyword"></label>
				<span class="col-md-3"><input type="search" class="form-control" name="keyword" id="keyword" placeholder="아이디, 이름 검색" value="${keyword}" /></span>
				<span class="col-md-1"><button type="submit" class="form-group btn btn-primary">검색</button></span>
			</form>

			<table class="table table-hover">
				<thead>
					<tr>
						<th class="col-md-1 text-center">번호</th>
						<th class="col-md-2 text-center">아이디</th>
						<th class="col-md-2 text-center">이름</th>
						<th class="col-md-2 text-center">생년월일</th>
						<th class="col-md-1 text-center">성별</th>
						<th class="col-md-2 text-center">이메일</th>
						<th class="col-md-2 text-center">가입일</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<%-- 조회결과가 없는 경우 --%>
						<c:when test="${output == null || fn:length(output) == 0}">
							<tr>
								<td></td>
							</tr>
						</c:when>
						<%-- 조회결과가 있는  경우 --%>
						<c:otherwise>
							<%-- 조회 결과에 따른 반복 처리 --%>
							<c:forEach var="item" items="${output}" varStatus="status">

								<%-- 상세페이지로 이동하기 위한 URL --%>
								<c:url value="adminMemberInfo" var="viewUrl">
									<c:param name="membno" value="${item.membno}" />
								</c:url>

								<tr>
									<td align="center">${item.membno}</td>
									<td class="mypc-font-black" align="center"><strong><a href="${viewUrl}">${item.user_id}</a></strong></td>
									<td align="center">${item.name}</td>
									<td align="center">${item.birthdate}</td>
									<td align="center">${item.gender}</td>
									<td align="center">${item.email}</td>
									<td align="center">${item.reg_date}</td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>

			<div class="col-md-offset-11 col-md-1">
				<button id="open_modal_btn" class="btn btn-primary">쿠폰 지급</button>
			</div>

			<div class="mypc-pagination-center">
				<ul class="pagination">
					<!-- 이전 그룹 링크 -->
					<c:choose>
						<c:when test="${pageData.prevPage > 0 }">
							<c:url value="adminMember" var="prevPageUrl">
								<c:param name="page" value="${pageData.prevPage}" />
								<c:param name="keyword" value="${keyword}" />
								<c:param name="subKeyword" value="${gender}" />
							</c:url>
						</c:when>
					</c:choose>
					<li><a href="${prevPageUrl}" aria-label="Previous"><span aria-hidden="true">&laquo;</span> </a></li>
					<!-- 페이지번호 -->
					<c:forEach var="i" begin="${pageData.startPage}" end="${pageData.endPage}" varStatus="status">
						<c:url value="adminMember" var="pageUrl">
							<c:param name="page" value="${i}" />
							<c:param name="keyword" value="${keyword}" />
							<c:param name="subKeyword" value="${gender}" />
						</c:url>
						<c:choose>
							<c:when test="${pageData.nowPage == i}">
								<li class="active"><a href="${pageUrl}">${i}</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="${pageUrl}">${i}</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<!-- 다음 그룹 링크 -->
					<c:choose>
						<c:when test="${pageData.nextPage > 0 }">
							<c:url value="adminMember" var="nextPageUrl">
								<c:param name="page" value="${pageData.nextPage}" />
								<c:param name="keyword" value="${keyword}" />
								<c:param name="subKeyword" value="${gender}" />
							</c:url>
						</c:when>
					</c:choose>
					<li><a href="${nextPageUrl}" aria-label="Next"><span aria-hidden="true">&raquo;</span> </a></li>
				</ul>
			</div>
		</div>
	</section>
	<!-- 쿠폰지급창(모달) -->
	<div class="modal fade" id="couponModal">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">쿠폰 지급</h4>
				</div>
				<div class="modal-body">
					<form id="coupon_form" class="join-form form-horizontal" role="form" action="${pageContext.request.contextPath}/adminMember">
						<fieldset>
							<legend>쿠폰 정보</legend>
							<div class="form-group">
								<span class="col-md-offset-1 col-md-2">
									<label class="control-label" for="coupon_name">쿠폰명</label>
								</span>
								<span class="col-md-7"><input type="text" id="coupon_name" class="form-control" name="coupon_name" placeholder="쿠폰명을 입력하세요." maxlength="35"></span>
							</div>
							<div class="form-group">
								<span class="col-md-offset-1 col-md-2">
									<label class="control-label" for="coupon_off">쿠폰 할인율</label></span>
								<span class="col-md-3"><input type="number" id="coupon_off" class="form-control" name="coupon_off" placeholder="할인율 1~100%"></span>
							</div>
							<div class="form-group">
								<span class="col-md-offset-1 col-md-2">
									<label class="control-label" for="expired_date">유효기간</label></span>
								<span id="nowDate" class="col-md-2 mypc-margin-top"></span>
								<span class="col-md-3"><input type="date" id="expired_date" class="form-control" name="expired_date" placeholder="쿠폰 만료일자를 입력하세요."></span>
							</div>
							<br />
							<legend>지급 대상</legend>
							<div class="form-group">
								<span class="col-md-offset-1 col-md-2">
									<label class="control-label" for="member_all">전 회원</label></span>
								<span class="col-md-1" style="margin-top: 4px"><input id="member_all" type="checkbox" name="member_all" value="a"></span>
							</div>
							<div class="form-group">
								<span class="col-md-offset-1 col-md-2">
									<label class="control-label" for="user_id">회원 아이디</label></span>
								<span class="col-md-3"><input type="text" id="user_id" class="form-control" name="user_id" placeholder="아이디를 입력하세요."></span>
							</div>
							<span class="col-md-offset-10 col-md-1"><button type="submit" class="btn btn-primary">쿠폰 전송</button></span>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- Content 끝 -->

	<%@ include file="./inc/footer.jsp"%>
	<%@ include file="./inc/adminScript.jsp"%>
	<script src="./assets/js/adminMember.js"></script>
	<script type="text/javascript">
		$(function() {
			adminCommon(); // 로그아웃
			couponModal(); // 쿠폰지급창
			nowDate();
			couponVali();
		});
	</script>
</body>
</html>