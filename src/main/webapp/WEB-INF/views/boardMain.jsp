<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="./inc/head.jsp"%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/boardMain.css" />
</head>

<body>
	<%@ include file="./inc/userHeader.jsp"%>

	<section class="container content">
		<div class="row">
			<div class="page-header mypc-margin"><div class="mypc-icon pull-right" onclick="location.href='index'"><span class="glyphicon glyphicon-home"></span></div>
				<h1>자유 게시판</h1>
			</div>
			<!-- 검색폼 -->
			<form class="mypc-searchForm" method="get" action="${pageContext.request.contextPath}/boardMain">
				<span class="col-md-2 col-md-offset-6">
					<select name="subKeyword" class="form-control">
						<option value="">전체</option>
						<option value="usr" <c:if test="${subKeyword eq 'usr'}"> selected</c:if>>작성자</option>
						<option value="sub" <c:if test="${subKeyword eq 'sub'}"> selected</c:if>>제목</option>
					</select>
				</span>			
				<label for="keyword"></label>
				<span class="col-md-3"><input type="search" class="form-control" name="keyword" id="keyword" placeholder="글 제목, 작성자 검색" value="${keyword}" /></span>
				<span class="col-md-1"><button type="submit" class="form-group btn btn-primary">검색</button></span>
			</form>
			
			<table class="table table-hover">
				<thead>
					<tr>
						<th class="col-md-1 text-center">번호</th>
						<th class="col-md-5 text-center">제목</th>
						<th class="col-md-2 text-center">작성자</th>
						<th class="col-md-3 text-center">작성일</th>
						<th class="col-md-1 text-center">조회수</th>
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
								<c:url value="boardView" var="viewUrl">
									<c:param name="docno" value="${item.docno}" />
								</c:url>

								<tr>
									<td align="center">${item.docno}</td>
										<c:choose>
											<c:when test="${item.hits > 49}">
												<c:choose>
													<c:when test="${item.membno eq 1}">
														<c:choose>
															<c:when test="${item.comment eq 0}">
																<td class="mypc-font-grey" align="center"><strong><a href="${viewUrl}">${item.subject}</a></strong>
															</c:when>
															<c:otherwise>
																<td class="mypc-font-grey" align="center"><strong><a href="${viewUrl}">${item.subject}</a></strong><span class="mypc-font-grey">&nbsp;&nbsp;<strong>[${item.comment}]</strong></span></td>
															</c:otherwise>
														</c:choose>
									    			</c:when>
									    			<c:otherwise>
									    				<c:choose>
									    					<c:when test="${item.comment eq 0}">
																<td class="mypc-font-black" align="center"><strong><a href="${viewUrl}">${item.subject}</a></strong>
															</c:when>
															<c:otherwise>
																<td class="mypc-font-black" align="center"><strong><a href="${viewUrl}">${item.subject}</a></strong><span class="mypc-font-red">&nbsp;&nbsp;<strong>[${item.comment}]</strong></span></td>
															</c:otherwise>
														</c:choose>
											   		</c:otherwise>	
												</c:choose>	
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${item.membno eq 1}">
														<c:choose>
															<c:when test="${item.comment eq 0}">
																<td class="mypc-font-grey" align="center"><a href="${viewUrl}">${item.subject}</a>
															</c:when>
															<c:otherwise>
																<td class="mypc-font-grey" align="center"><a href="${viewUrl}">${item.subject}</a><span class="mypc-font-grey">&nbsp;&nbsp;<strong>[${item.comment}]</strong></span></td>
															</c:otherwise>
														</c:choose>
									    			</c:when>
									    			<c:otherwise>
									    				<c:choose>
									    					<c:when test="${item.comment eq 0}">
																<td class="mypc-font-black" align="center"><a href="${viewUrl}">${item.subject}</a>
															</c:when>
															<c:otherwise>
																<td class="mypc-font-black" align="center"><a href="${viewUrl}">${item.subject}</a><span class="mypc-font-red">&nbsp;&nbsp;<strong>[${item.comment}]</strong></span></td>
															</c:otherwise>
														</c:choose>
											   		</c:otherwise>	
												</c:choose>												
											</c:otherwise>
										</c:choose>					
									<c:choose>
									<c:when test="${item.membno eq 1}">
										<td class="	mypc-font-grey" align="center">(알 수 없음)</td>
									</c:when>
									<c:otherwise>
										<td align="center">${item.user_id}</td>
									</c:otherwise>
									</c:choose>
									<td align="center">${item.reg_date}</td>
									<td align="center">${item.hits}</td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			<div class="mypc-pagination-center">
				<ul class="pagination">
					<!-- 이전 그룹 링크 -->
					<c:choose>
						<c:when test="${pageData.prevPage > 0 }">
							<c:url value="boardMain" var="prevPageUrl">
								<c:param name="page" value="${pageData.prevPage}" />
								<c:param name="keyword" value="${keyword}" />
								<c:param name="subKeyword" value="${subKeyword}" />
							</c:url>
						</c:when>
					</c:choose>
					<li><a href="${prevPageUrl}" aria-label="Previous"><span aria-hidden="true">&laquo;</span> </a></li>
					<!-- 페이지번호 -->
					<c:forEach var="i" begin="${pageData.startPage}" end="${pageData.endPage}" varStatus="status">
						<c:url value="boardMain" var="pageUrl">
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
							<c:url value="boardMain" var="nextPageUrl">
								<c:param name="page" value="${pageData.nextPage}" />
								<c:param name="keyword" value="${keyword}" />
								<c:param name="subKeyword" value="${subKeyword}" />
							</c:url>
						</c:when>
					</c:choose>
					<li><a href="${nextPageUrl}" aria-label="Next"><span aria-hidden="true">&raquo;</span> </a></li>
				</ul>
				<input type="hidden" id="session" value="${sessionUserId}">
				<button id="writebtn" type="button" class="mypc-writebtn pull-right form-group btn btn-primary">글 쓰기</button>
			</div>
		</div>
	</section>

	<%@ include file="./inc/footer.jsp"%>
	<%@ include file="./inc/userScript.jsp"%>
	<script src="./assets/js/boardMain.js"></script>
	<script type="text/javascript">
		$(function() {
			userCommon(); // 상단 메뉴바의 기능을 구현
			checkLogin(); // session을 통해 로그인 여부를 확인합니다.
		});
	</script>
</body>
</html>