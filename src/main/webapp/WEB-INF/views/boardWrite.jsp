<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="./inc/head.jsp"%>
</head>

<body>
	<%@ include file="./inc/userHeader.jsp"%>

	<section class="container content">
		<div class="row">
			<div class="page-header mypc-margin">
				<h1>자유 게시판</h1>
			</div>

			<c:choose>
				<c:when test="${output.docno != null}">
					<form id="board_up_form" class="form-horizontal boardWrite-form" role="form" action="${pageContext.request.contextPath}/boardMain">
						<fieldset>
							<input id="docno" name="docno" type="hidden" value="${output.docno}">
							<div class="form-group">
								<label for="boardWrite-input02" class="col-md-1 control-label">제목:</label>
								<div class="col-md-7">
									<input id="subject" name="subject" type="text" placeholder="제목을 입력해 주세요." class="form-control" value="${output.subject}" />
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-12">
									<textarea id="content" name="content">${output.content}</textarea>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-offset-10 col-md-1">
									<button type="button" onclick="location.href='boardView?docno=${output.docno}'" class="btn btn-primary boardWrite-btn">뒤로가기</button>
								</div>
								<div class="col-md-1">
									<button type="submit" class="btn btn-primary boardWrite-btn">수정하기</button>
								</div>
							</div>
						</fieldset>
					</form>
				</c:when>
				<c:otherwise>
					<form id="board_post_form" class="form-horizontal boardWrite-form" role="form" action="${pageContext.request.contextPath}/boardMain">
						<fieldset>
							<div class="form-group">
								<label for="boardWrite-input02" class="col-md-1 control-label">제목:</label>
								<div class="col-md-7">
									<input id="subject" name="subject" type="text" placeholder="제목을 입력해 주세요." class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-12">
									<textarea id="content" name="content"></textarea>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-offset-10 col-md-1">
									<button type="button" onclick="location.href='boardMain'" class="btn btn-primary boardWrite-btn">뒤로가기</button>
								</div>
								<div class="col-md-1">
									<button type="submit" class="btn btn-primary boardWrite-btn">등록하기</button>
								</div>
							</div>
						</fieldset>
					</form>
				</c:otherwise>
			</c:choose>
		</div>
	</section>

	<%@ include file="./inc/footer.jsp"%>
	<%@ include file="./inc/userScript.jsp"%>
	<script src="assets/plugins/ckeditor/ckeditor.js"></script>
	<script src="./assets/js/boardWrite.js"></script>
	<script type="text/javascript">
		$(function() {
			userCommon(); // 상단 메뉴바의 기능을 구현
			ckeditor();   // ck에디터
			boardPost();  // 게시글 쓰기 유효성 검사
		});
	</script>
</body>
</html>