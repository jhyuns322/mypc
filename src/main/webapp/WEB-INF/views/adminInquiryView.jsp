<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>

<!DOCTYPE html>
<html lang="ko">
<%@ include file="./inc/head.jsp"%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/adminInquiryView.css" />
</head>

<body>
	<%@ include file="./inc/adminHeader.jsp"%>

	<!-- Content 시작 -->
	<section class="container content">
		<div class="row">
			<div class="page-header mypc-margin"><div class="mypc-icon pull-right" onclick="location.href='adminInquiry'"><span class="glyphicon glyphicon-list"></span></div>
				<h1>문의 관리</h1>
			</div>
			
			<!-- 답변 메일 FORM -->
			<form id="inquiryReplyForm" class="form-horizontal" role="form" method="get" action="${pageContext.request.contextPath}/adminInquiry">
				<fieldset>
					<div class="form-group">
						<label for="" class="col-md-1 col-md-offset-1 control-label">문의번호: </label>
						<div class="col-md-1 mypc-padding-top">${output.docno}</div>
						<label for="" class="col-md-1 control-label">작성자: </label>
						<c:choose>
							<c:when test="${output.user_id.equals('mypc')}">
								<div class="mypc-font-color col-md-2 mypc-padding-top">(알 수 없음)</div>
							</c:when>
							<c:otherwise>
								<div class="col-md-2 mypc-padding-top">${output.user_id}</div>
							</c:otherwise>
						</c:choose>		
						<label for="" class="col-md-1 control-label">작성일자: </label>
						<div class="col-md-2 mypc-padding-top">${output.reg_date}</div>
					</div>

					<div class="form-group">
						<label for="" class="col-md-2 control-label">제목</label>
						<div class="col-md-9">
							<input id="subject" type="text" class="form-control" name="subject" value="${output.subject}" readonly>
						</div>
					</div>

					<div class="form-group">
						<label for="content" class="col-md-2 control-label">내용</label>
						<div class="col-md-9">
							<textarea id="content" class="form-control" rows="8" style="resize: none;" name="content" readonly>${output.content}</textarea>
						</div>
					</div>

					<!-- SENDING REPLY -->
					<div class="container">
						<div class="mypc-service-center">
							<h4>메일 회신</h4>
						</div>
						<a class="mypc-inquiry-button"><img src="${pageContext.request.contextPath}/download?file=plus.jpg"></a>
						<div id="hiddenForm">

							<!-- INQUIRY FORM -->
							<div class="form-group">
								<label for="email" class="col-md-2 control-label">수신 이메일</label>
							<c:choose>
								<c:when test="${output.user_id.equals('mypc')}">
									<div class="col-md-9">
										<input id="email" type="text" class="form-control" name="email" value="(알 수 없음)" readonly>
									</div>
								</c:when>
								<c:otherwise>
									<div class="col-md-9">
										<input id="email" type="text" class="form-control" name="email" value="${output.email}" readonly>
									</div>
								</c:otherwise>
							</c:choose>								
							</div>
							<div class="form-group">
								<label for="subject" class="col-md-2 control-label">글 제목</label>
								<div class="col-md-9">
									<input id="subject" type="text" class="form-control" name="mailSubject" placeholder="내용을 입력하세요.">
								</div>
							</div>
							<div class="form-group">
								<label for="content" class="col-md-2 control-label">내용</label>
								<div class="col-md-9">
									<textarea id="content" class="form-control" name="mailContent" rows="8" style="resize: none;" placeholder="내용을 입력하세요."></textarea>
								</div>
							</div>
							<input id="docno" type="hidden" name="docno" value="${output.docno}">
							<input id="reg_date" type="hidden" name="reg_date" value="${output.reg_date}">
							
							<!-- INQUIRY BOTTON -->
							<div class="form-group">
								<div class="col-md-offset-10 col-md-1">
									<button type="submit" class="btn btn-primary btn-block">전송</button>
								</div>
							</div>
						</div>
					</div>

				</fieldset>
			</form>
		</div>
	</section>
	<!---------------Content 끝--------------->

	<%@ include file="./inc/footer.jsp"%>
	<%@ include file="./inc/adminScript.jsp"%>
	<script src="./assets/js/adminInquiryView.js"></script>
	<script type="text/javascript">
		$(function() {
			var userId = "${output.user_id}"
			adminCommon(); // 로그아웃
			buttonEvent(userId); // 문의글 이메일 회신 영역 확장
			inquiryReplyVali(); // 문의글 이메일 답변에 대한 유효성 검사와 전송
		});
	</script>
</body>
</html>