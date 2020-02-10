<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="./inc/head.jsp"%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/animate/animate.css" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/adminItem.css" />
</head>
<!-- Head 끝 -->
<body>
	<%@ include file="./inc/adminHeader.jsp"%>
	

	<section class="container content">
		<div class="row">
			<div class="page-header mypc-margin"><div class="mypc-icon pull-right" onclick="location.href='adminIndex'"><span class="glyphicon glyphicon-home"></span></div>
				<h1>상품 관리</h1>
			</div>
			<!-- 검색폼 -->
			<form class="mypc-searchForm" method="get"
				action="${pageContext.request.contextPath}/adminItem">
				<span class="col-md-2 col-md-offset-6"> <select
					name="subKeyword" class="form-control">
						<option value="">전체</option>
						<option value="category"
							<c:if test="${subKeyword eq 'category'}"> selected</c:if>>카테고리</option>
						<option value="manufac"
							<c:if test="${subKeyword eq 'manufac'}"> selected</c:if>>제조사</option>
						<option value="item_name"
							<c:if test="${subKeyword eq 'item_name'}"> selected</c:if>>상품이름</option>
				</select>
				</span> <label for="keyword"></label> <span class="col-md-3"><input
					type="search" class="form-control" name="keyword" id="keyword"
					placeholder="상품 검색" value="${keyword}" /></span> <span class="col-md-1"><button
						type="submit" class="form-group btn btn-primary">검색</button></span>
			</form>

			<table class="table table-hover">
				<thead>
					<tr>
						<th class="col-md-1 text-center"><input class="mypc-cbxAll"
							type="checkbox" /></th>
						<th class="col-md-1 text-center">카테고리</th>
						<th class="col-md-1 text-center">제조사</th>
						<th class="col-md-2 text-center">상품이름</th>
						<th class="col-md-2 text-center">스펙</th>
						<th class="col-md-2 text-center">가격</th>
						<th class="col-md-2 text-center">재고</th>
						<th class="col-md-1 text-center">출시일</th>
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
								<tr class="mypc-itemno" data-value="${item.itemno}">
									<td onclick="event.cancelBubble = true;" align="center"><input
										class="mypc-cbx" name="mypc-cbx" type="checkbox"
										value="${item.itemno}" /></td>
									<td align="center">${item.category}</td>
									<td align="center">${item.manufac}</td>
									<td align="center">${item.item_name}</td>
									<td align="center">${item.spec}</td>
									<td align="center">${item.price}</td>
									<td align="center">${item.stock}</td>
									<td align="center">${item.rel_date}</td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			
			<div class="col-md-offset-10 col-md-1">
				<button type="button" onclick="location.href='adminItemRegist'"
					class="btn btn-primary">상품 등록</button>
			</div>
			<div class="col-md-1">
				<button type="button" class="btn btn-primary mypc-btn">선택
					삭제</button>
			</div>
			
			<div class="mypc-pagination-center">
				<ul class="pagination">
					<!-- 이전 그룹 링크 -->
					<c:choose>
						<c:when test="${pageData.prevPage > 0 }">
							<c:url value="adminItem" var="prevPageUrl">
								<c:param name="page" value="${pageData.prevPage}" />
								<c:param name="keyword" value="${keyword}" />
								<c:param name="subKeyword" value="${gender}" />
							</c:url>
						</c:when>
					</c:choose>
					<li><a href="${prevPageUrl}" aria-label="Previous"><span
							aria-hidden="true">&laquo;</span> </a></li>
					<!-- 페이지번호 -->
					<c:forEach var="i" begin="${pageData.startPage}"
						end="${pageData.endPage}" varStatus="status">
						<c:url value="adminItem" var="pageUrl">
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
							<c:url value="adminItem" var="nextPageUrl">
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
	<!---------------Content 끝--------------->

	<%@ include file="./inc/footer.jsp"%>
	<%@ include file="./inc/adminScript.jsp"%>
	<script src="${pageContext.request.contextPath}/assets/js/adminItem.js"></script>

	<script type="text/javascript">
		$(function() {
			var path = "${pageContext.request.contextPath}/removeItem/";
			adminCommon(); 		// 로그아웃
			allCheck(); 		// 체크박스 일괄 선택 및 해제
			editItem(); 		// 상품 수정 페이지 이동
			removeItem(path);   // 상품 삭제	
		});
	</script>
</body>
</html>