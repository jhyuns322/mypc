<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>

<!DOCTYPE html>
<html lang="ko">
<%@ include file="./inc/head.jsp"%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/support.css" />
</head>

<body>
	<%@ include file="./inc/userHeader.jsp"%>

	<!-- Content 시작 -->
	<section class="container content">
		<div class="row">
			<div class="page-header mypc-margin">
				<h1>고객센터</h1>
			</div>
			<ul class="nav nav-tabs">
				<!-- 활성화 탭은 active 클래스를 추가 -->
				<li class="active"><a href="#questions" data-toggle="tab">일반 문의</a></li>
				<!-- 일반 탭 -->
				<c:choose>
					<c:when test="${sessionUserId eq null}">
						<li></li>
					</c:when>
					<c:otherwise>
						<li><a href="#contact" data-toggle="tab">1대1 문의</a></li>
					</c:otherwise>
				</c:choose>
			</ul>

			<div class="tab-content">
				<div class="tab-pane fade in active" id="questions">
					<div class="mypc-content-box">

						<!-- ACCORDION -->
						<div class="mypc-question-box panel-group" id="accordion">
							<h4>자주 찾는 질문</h4>
							<!-- QUESTION_1 -->
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapseOne"> Q1 | 배송은 얼마나 걸리나요? </a>
									</h4>
								</div>
								<div id="collapseOne" class="panel-collapse collapse">
									<div class="panel-body">
										<p>상품발송은 평일 오후 5시 이전 건에 한하여 당일 발송하며, 발송 후 배송완료까지 통상 1~2일 소요됩니다. (도서/산간지역은 며칠 더 소요될 수 있습니다.)</p>
										<p>토요일은 오후 12시 이전 건에 한하여 당일 발송하며, 통상 차주 월요일이나 화요일 수령 가능합니다. 휴일은 발송하지 않습니다.</p>
										<p>방문수령 또는 서울근교 지역은 퀵서비스를 이용하여 당일수령 가능합니다. 단, 퀵 비용은 고객님께서 부담하셔야 합니다.
									</div>
								</div>
							</div>
							<!-- QUESTION_2 -->
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo"> Q2 | 반품이 가능한가요? </a>
									</h4>
								</div>
								<div id="collapseTwo" class="panel-collapse collapse">
									<div class="panel-body">
										<p>- 반품접수 : 1대1 문의를 통하여 반품신청이 가능합니다..</p>
										<p>- 요청기간 : 상품수령 후 24시간 이내 반품 신청 가능합니다. (상품수령 확인은 택배사의 화물추적을 이용합니다.)</p>
										<p>- 반품기간 : 반품요청 후 반품요청일(공휴일제외)을 포함하여 3일 이내에 신청이 가능합니다. (별도의 요청없이 반품을 보내실 경우 반품 불가합니다.)</p>
										<p>- 반품배송료 : 기본적으로 왕복배송비는 고객부담 원칙으로 하고있습니다.</p>
										<p>- 환불 : 당사에서 반품상품을 수령후 3일(공휴일 제외)이내 환불처리 해드립니다.</p>
									</div>
								</div>
							</div>
							<!-- QUESTION_3 -->
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapseThree"> Q3 | 주문 후 상품 종류 및 배송지 등을 변경할 수 있나요? </a>
									</h4>
								</div>
								<div id="collapseThree" class="panel-collapse collapse">
									<div class="panel-body">
										<p>1대1 문의를 통해 상품 변경 및 배송지 변경 요청이 가능합니다. 상품 발송 전 상품 변경 요청 확인 시 변경된 상품 또는 배송지로 배송해드립니다.</p>
										<p>허나, 이미 배송이 진행 중인 상품인 경우 상품을 받아보신 후 교환 요청을 통해 상품 변경이 가능합니다.</p>
										<p>상품교환 시 발생하는 배송비는 구매자 부담입니다. 관련 문의는 1:1문의를 이용하시면 친절히 상담해 드리겠습니다.</p>
									</div>
								</div>
							</div>
							<!-- QUESTION_4 -->
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapseFour"> Q4 | 제주도(산간 및 도서지방)로 배송 시 배송비가 추가 되나요? </a>
									</h4>
								</div>
								<div id="collapseFour" class="panel-collapse collapse">
									<div class="panel-body">
										<p>제주도, 도서 산간 지역은 도선비 및 항공료 실비가 추가되오니 주문 시 이점 인지하시고 착오 없으시길 바랍니다.</p>
									</div>
								</div>
							</div>
							<!-- QUESTION_5 -->
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapsefive"> Q5 | 아이디와 비밀번호를 잊어버렸습니다. </a>
									</h4>
								</div>
								<div id="collapsefive" class="panel-collapse collapse">
									<div class="panel-body">
										<p>로그인 페이지 내 아이디/비밀번호 찾기를 이용해 주세요.</p>
										<p>아이디 찾기는 등록하신 이메일 주소를 입력하시면 해당 이메일로 아이디가 발송됩니다.</p>
										<p>비밀번호 찾기는 등록하신 아이디와 이메일 주소를 입력하시면 해당 이메일로 임시 비밀번호를 발급해 드립니다.</p>
									</div>
								</div>
							</div>
						</div>
						<!-- ACCORDION -->

						<!-- 서비스센터 맵 -->
						<h4>서비스 센터 찾기</h4>
						<form name="form" class="mypc-mapForm">
							<span class="col-md-2"> <select id="sido" name="sido" class="form-control"></select></span>
							<span class="col-md-2"> <select id="gugun" name="gugun" class="form-control"></select></span>
							<span class="col-md-2"> <select id='co' name="co" class="form-control"></select></span>
							<span class="col-md-1"><button type="submit" class="form-group btn btn-primary">검색</button></span>
						</form>
						<div id="map" class="col-md-12 mypc-map"></div>
						<!-- 서비스센터 맵 -->

					</div>
				</div>

				<!-- 1대1 문의글 작성 Form -->
				<div class="tab-pane fade" id="contact">
					<div class="mypc-content-box">
						<h4>문의글 작성</h4>
						<form id="inquiry_form" class="form-horizontal" role="form" action="${pageContext.request.contextPath}/support">
							<fieldset>
								<!-- INQUIRY FORM -->
								<div class="form-group">
									<label for="subject" class="col-md-2 control-label">글 제목</label>
									<div class="col-md-9">
										<input type="text" id="subject" class="form-control" name="subject" placeholder="제목을 입력하세요.">
									</div>
								</div>
								<div class="form-group">
									<label for="content" class="col-md-2 control-label">내용</label>
									<div class="col-md-9">
										<textarea id="content" class="form-control" name="content" rows="16" style="resize: none;" placeholder="내용을 입력하세요."></textarea>
									</div>
								</div>

								<!-- INQUIRY BOTTON -->
								<div class="form-group">
									<div class="col-md-offset-10 col-md-1">
										<button type="submit" class="btn btn-primary btn-block">전송</button>
									</div>
								</div>
							</fieldset>
						</form>
					</div>
				</div>
				<!-- 1대1 문의글 작성 Form -->

			</div>
		</div>
	</section>

	<%@ include file="./inc/footer.jsp"%>
	<%@ include file="./inc/userScript.jsp"%>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=17ffa9354e5f5f0e96dcf711f5b9c7d6&libraries=services"></script>
	<script src="./assets/js/support.js"></script>
	<script type="text/javascript">
		$(function() {
			new selectors('sido', 'gugun', 'co');
			var infowindow = new kakao.maps.InfoWindow({
				zIndex : 1
			});
			var mapContainer = document.getElementById('map'), 	   // 지도를 표시할 div
			mapOption = {
				center : new kakao.maps.LatLng(37.5023, 127.0244), // 지도의 중심좌표
				level : 3
			};
			var map = new kakao.maps.Map(mapContainer, mapOption); 	// 지도 생성
			
			userCommon(); // 상단 메뉴바의 기능을 구현
			supportVali();
			kakaoMap(map,mapContainer,mapOption,infowindow);
		});
	</script>
</body>
</html>