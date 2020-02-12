<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="./inc/head.jsp"%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/adminInquiry.css" />
</head>

<body>
	<%@ include file="./inc/adminHeader.jsp"%>

	<!-- content -->
	<section class="container content">
		<div class="row">
			<div class="page-header mypc-margin"><div class="mypc-icon pull-right" onclick="location.href='adminIndex'"><span class="glyphicon glyphicon-home"></span></div>
				<h1>문의 관리</h1>
			</div>
			<!-- 검색폼 -->
			<form method="get" action="${pageContext.request.contextPath}/adminInquiry">
				<span class="col-md-2 col-md-offset-6"> <select name="subKeyword" class="form-control">
						<option value="" selected>전체</option>
						<option value="y" <c:if test="${subKeyword eq 'y'}"> selected</c:if>>답변완료</option>
						<option value="n" <c:if test="${subKeyword eq 'n'}"> selected</c:if>>미답변</option>
				</select>
				</span> <label for="keyword"></label>
				<span class="col-md-3"><input type="search" class="form-control" name="keyword" id="keyword" placeholder="작성자 검색" value="${keyword}" /></span>
				<span class="col-md-1"><button type="submit" class="form-group btn btn-primary">검색</button></span>
			</form>

			<table class="table table-hover">
				<thead>
					<tr>
						<th class="col-md-1 text-center"><input class="mypc-cbxAll" type="checkbox" /></th>
						<th class="col-md-5 text-center">제목</th>
						<th class="col-md-2 text-center">작성자</th>
						<th class="col-md-2 text-center">작성일</th>
						<th class="col-md-2 text-center">확인</th>
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
								<c:url value="adminInquiryView" var="viewUrl">
									<c:param name="docno" value="${item.docno}" />
								</c:url>

								<tr class="mypc-docno" data-value="${item.docno}">
									<td onclick="event.cancelBubble = true;" align="center">
									<input class="mypc-cbx" name="mypc-cbx" type="checkbox" value="${item.docno}" /></td>
									<td class="mypc-font-black" align="center"><a href="${viewUrl}">${item.subject}</a></td>
									<c:choose>
										<c:when test="${item.membno eq 1}">
											<td class="mypc-font-grey" align="center">(알 수 없음)</td>
										</c:when>
										<c:otherwise>
											<td align="center">${item.user_id}</td>
										</c:otherwise>
									</c:choose>
									<td align="center">${item.reg_date}</td>
									<c:choose>
										<c:when test="${item.replied eq 'y'}">
											<td align="center">답변완료</td>
										</c:when>
										<c:otherwise>
											<td align="center">미답변</td>
										</c:otherwise>
									</c:choose>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			
			<div class="col-md-offset-11 col-md-1">
				<button type="button" class="btn btn-primary mypc-btn">선택 삭제</button>
			</div>
			
			<div class="mypc-pagination-center">
				<ul class="pagination">
					<!-- 이전 그룹 링크 -->
					<c:choose>
						<c:when test="${pageData.prevPage > 0 }">
							<c:url value="adminInquiry" var="prevPageUrl">
								<c:param name="page" value="${pageData.prevPage}" />
								<c:param name="keyword" value="${keyword}" />
								<c:param name="subKeyword" value="${subKeyword}" />
							</c:url>
						</c:when>
					</c:choose>
					<li><a href="${prevPageUrl}" aria-label="Previous"><span aria-hidden="true">&laquo;</span> </a></li>
					<!-- 페이지번호 -->
					<c:forEach var="i" begin="${pageData.startPage}"
						end="${pageData.endPage}" varStatus="status">
						<c:url value="adminInquiry" var="pageUrl">
							<c:param name="page" value="${i}" />
							<c:param name="keyword" value="${keyword}" />
							<c:param name="subKeyword" value="${subKeyword}" />
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
							<c:url value="adminInquiry" var="nextPageUrl">
								<c:param name="page" value="${pageData.nextPage}" />
								<c:param name="keyword" value="${keyword}" />
								<c:param name="subKeyword" value="${subKeyword}" />
							</c:url>
						</c:when>
					</c:choose>
					<li><a href="${nextPageUrl}" aria-label="Next"><span aria-hidden="true">&raquo;</span> </a></li>
				</ul>
			</div>
		</div>
	</section>
	
	<%@ include file="./inc/footer.jsp"%>
	<%@ include file="./inc/adminScript.jsp"%>
	<script src="./assets/js/adminInquiry.js"></script>
	<script type="text/javascript">
		$(function() {
			var contextPath = "${pageContext.request.contextPath}";
			adminCommon();			    // 로그아웃
			allCheck(); 				// 체크 박스 일괄 체크
			removeInquiry(contextPath); // 문의글 삭제
		});
	</script>
</body>
</html>