<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>

<!DOCTYPE html>
<html lang="ko">
<%@ include file="./inc/head.jsp"%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/boardView.css" />
</head>

<body>
	<%@ include file="./inc/userHeader.jsp"%>

	<!-- Content -->
	<section class="container content">
		<div class="row">
			<c:choose>
				<%-- 탈퇴한 회원의 게시물일 때 --%>
				<c:when test="${output.membno == 1}">
					<div class="page-header mypc-margin">
						<div class="mypc-icon pull-right" onclick="location.href='boardMain'"><span class="glyphicon glyphicon-list"></span></div>
						<h1 class="mypc-font-grey">자유 게시판</h1>
					</div>
					<div>
						<div class="col-md-10 mypc-font-grey">탈퇴한 회원의 게시글입니다.</div>
					</div>
					<div>
						<div class="col-md-12 mypc-font-grey">
							<h3><strong>${output.subject}</strong></h3>
						</div>
						<br />
						<div class="col-md-12 mypc-contentbox mypc-img-resizing mypc-font-grey">
							<c:out value="${output.content}" escapeXml="false" />
						</div>
					</div>
				</c:when>
				<%-- 일반 회원의 게시물일 때 --%>
				<c:otherwise>
					<div class="page-header mypc-margin">
						<div class="mypc-icon pull-right" onclick="location.href='boardMain'"><span class="glyphicon glyphicon-list"></span></div>
						<h1>자유 게시판</h1>
					</div>

					<div>
						<label for="" class="col-md-1 control-label">글 번호: </label>
						<div class="col-md-1">${output.docno}</div>
						<label for="" class="col-md-1 control-label">작성자: </label>
						<div class="col-md-2">${output.user_id}</div>
						<label for="" class="col-md-1 control-label">작성일자: </label>
						<div class="col-md-2">${output.reg_date}</div>
						<c:choose>
							<%-- (일반 회원의 게시물일 때)게시물 수정 이력이 있을 때 수정일자도 표기 --%>
							<c:when test="${output.edit_date != null}">
								<label for="" class="col-md-1 control-label">수정일자: </label>
								<div class="col-md-2">${output.edit_date}</div>
							</c:when>
							<%-- (일반 회원의 게시물일 때)게시물 수정 이력이 없으면 수정일자 미표기 --%>
							<c:otherwise>
							</c:otherwise>
						</c:choose>
					</div>

					<div>
						<div class="col-md-12">
							<h3><strong>${output.subject}</strong></h3>
						</div>
						<br />
						<div class="col-md-12 mypc-contentbox mypc-img-resizing">
							<c:out value="${output.content}" escapeXml="false" />
						</div>
					</div>

					<c:choose>
						<%-- (일반 회원의 게시물일 때) 로그인한 회원 pk와 게시물의 회원 pk가 일치할 시 = 본인 게시글 일 때 --%>
						<c:when test="${sessionMembno.equals(output.membno)}">
							<div class="col-md-offset-9 col-md-1">
								<button type="button" onclick="location.href='boardWrite?docno=${output.docno}'" class="btn btn-primary boardWrite-btn">수정하기</button>
							</div>
							<div class="col-md-1">
								<button type="button" id="boardDelete" data-docno="${output.docno}" data-user_id="${output.user_id}" class="btn btn-primary boardWrite-btn">삭제하기</button>
							</div>
						</c:when>
						<%-- (일반 회원의 게시물일 때) 로그인한 회원 pk와 게시물의 회원 pk가 불일치할 시 = 본인 게시글이 아닐 때 --%>
						<c:otherwise>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</div>
		<!-- 덧글창 -->
		<div class="row">
		<c:choose>
			<c:when test="${output.membno == 1}">
				<c:choose>
					<%-- (탈퇴한 회원의 게시물일 때)해당 게시물의 덧글이 존재할 경우 --%>
					<c:when test="${outputCount != 0}">
					<legend class="mypc-font-grey">덧글 ${outputCount}개</legend>
						<c:forEach var="item" items="${outputList}" varStatus="status">
							<div class="mypc-comment-margin01 col-md-offset-1 col-md-10">
								<c:choose>
									<%-- (탈퇴한 회원의 게시물일 때) (덧글 조회결과가 있을 경우) 덧글의 작성자가 membno = 1(탈퇴한 회원) 일 때 --%>
									<c:when test="${item.membno == 1}">
										<div class="mypc-font-grey col-md-2 mypc-comment-margin02">(알 수 없음)</div>
									</c:when>
									<%-- (탈퇴한 회원의 게시물일 때) (덧글 조회결과가 있을 경우) 덧글의 작성자가 membno != 1 일 때 --%>
									<c:otherwise>
										<div class="col-md-2 mypc-comment-margin02"><strong>${item.user_id}</strong></div>
									</c:otherwise>
								</c:choose>
								<div class="col-md-3 pull-right mypc-comment-margin02">${item.reg_date}</div>
								<div class="col-md-10">
									<span class="mypc-comment-padding">${item.comment}</span>
									<c:choose>
										<%-- (탈퇴한 회원의 게시물일 때) (덧글 조회결과가 있을 경우) 덧글의 작성자가 로그인한 회원의 membno와 일치할 때--%>
										<c:when test="${sessionMembno == item.membno}">
											<span id="commentDelete" data-commno="${item.commno}" data-docno="${output.docno}" style="cursor: pointer" class="commentDelete glyphicon glyphicon-remove"></span>
										</c:when>
										<%-- (일반 회원의 게시물일 때) (덧글 조회결과가 있을 경우) 덧글의 작성자가 로그인한 회원의 membno와 불일치할 때--%>
										<c:otherwise>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</c:forEach>
					</c:when>
					<%-- (탈퇴한 회원의 게시물일 때)해당 게시물의 덧글이 조회되지 않을 경우 --%>
					<c:otherwise>
						<legend class="mypc-font-grey">등록된 덧글이 없습니다.</legend>
					</c:otherwise>
				</c:choose>				
			</c:when>
			<%-- 일반 회원의 게시물일 때 --%>
			<c:otherwise>
				<form id="comment_form" class="join-form form-horizontal" role="form" action="${pageContext.request.contextPath}/boardView">
					<fieldset>
						<c:choose>
							<%-- (일반 회원의 게시물일 때)해당 게시물의 덧글이 존재할 경우 --%>
							<c:when test="${outputCount != 0}">
								<legend>덧글 ${outputCount}개</legend>
							</c:when>
							<%-- (일반 회원의 게시물일 때)해당 게시물의 덧글이 조회되지 않을 경우 --%>
							<c:otherwise>
								<legend>등록된 덧글이 없습니다.</legend>
							</c:otherwise>
						</c:choose>
						
						<c:choose>
						<%-- (일반 회원의 게시물일 때) 로그인이 되어 있을 경우 --%>
						<c:when test="${sessionUserId != null}">
							<div class="form-group">
								<input type="hidden" id="membno" name="membno" value="${sessionMembno}">
								<input type="hidden" id="docno" name="docno" value="${output.docno}">
								<span class="col-md-2"><label class="control-label" for="comment">${sessionUserId}</label></span>
								<span class="col-md-9"><input type="text" id="comment" class="form-control" name="comment" placeholder="최대 200글자까지 입력이 가능합니다." maxlength="200"></span>
								<div class="col-md-1">
									<button type="submit" class="btn btn-primary boardWrite-btn">등록</button>
								</div>
							</div>
						</c:when>
						<%-- (일반 회원의 게시물일 때) 로그인이 되어 있지 않을 경우 --%>
						<c:otherwise>
							<div>
								<div style="text-align: center;">
									<strong>로그인 후 덧글 쓰기가 가능합니다.</strong>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
				</fieldset>
			</form>
			
			<c:choose>
				<%-- (일반 회원의 게시물일 때) 덧글 조회결과가 없는 경우 --%>
				<c:when test="${outputList == null || fn:length(outputList) == 0}">
					<div class="mypc-comment-margin01 col-md-offset-1 col-md-10"
						style="text-align: center;">가장 먼저 덧글을 써 보세요!</div>
				</c:when>
				<%-- (일반 회원의 게시물일 때) 덧글 조회결과가 있을 경우 --%>
				<c:otherwise>
				
					<c:forEach var="item" items="${outputList}" varStatus="status">
						<div class="mypc-comment-margin01 col-md-offset-1 col-md-10">
							<c:choose>
								<%-- (일반 회원의 게시물일 때) (덧글 조회결과가 있을 경우) 덧글의 작성자가 membno = 1(탈퇴한 회원) 일 때 --%>
								<c:when test="${item.membno == 1}">
									<div class="mypc-font-grey col-md-2 mypc-comment-margin02">(알 수 없음)</div>
								</c:when>
								<%-- (일반 회원의 게시물일 때) (덧글 조회결과가 있을 경우) 덧글의 작성자가 membno != 1 일 때 --%>
								<c:otherwise>
									<div class="col-md-2 mypc-comment-margin02"><strong>${item.user_id}</strong></div>
								</c:otherwise>
							</c:choose>
							<div class="col-md-3 pull-right mypc-comment-margin02">${item.reg_date}</div>
							<div class="col-md-10">
								<span class="mypc-comment-padding">${item.comment}</span>
								<c:choose>
									<%-- (일반 회원의 게시물일 때) (덧글 조회결과가 있을 경우) 덧글의 작성자가 로그인한 회원의 membno와 일치할 때--%>
									<c:when test="${sessionMembno == item.membno}">
										<span id="commentDelete" data-commno="${item.commno}" data-docno="${output.docno}" style="cursor: pointer" class="commentDelete glyphicon glyphicon-remove"></span>
									</c:when>
									<%-- (일반 회원의 게시물일 때) (덧글 조회결과가 있을 경우) 덧글의 작성자가 로그인한 회원의 membno와 불일치할 때--%>
									<c:otherwise>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
			</c:otherwise>
		</c:choose>
		</div>
	</section>
	<!---------------Content 끝--------------->

	<%@ include file="./inc/footer.jsp"%>
	<%@ include file="./inc/userScript.jsp"%>
	<script src="./assets/js/boardView.js"></script>
	<script type="text/javascript">
		$(function() {
			var contextPath = "${pageContext.request.contextPath}";
			userCommon(); // 상단 메뉴바의 기능을 구현
			commentVail(); // 덧글 쓰기
			boardDelete(contextPath); // 게시글 삭제 기능을 구현
			commentDelete(contextPath) // 덧글 삭제 기능을 구현
		});
	</script>
</body>
</html>