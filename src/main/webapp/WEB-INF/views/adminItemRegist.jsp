<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>

<!DOCTYPE html>
<html lang="ko">
<%@ include file="./inc/head.jsp"%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/animate/animate.css" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/chosen/chosen.css" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/datepicker/datepicker.min.css" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/adminItemRegist.css" />
</head>

<!-- Head -->
<body>
	<%@ include file="./inc/adminHeader.jsp"%>

	<section class="container content">
		<div class="row">
			<div class="page-header mypc-margin">
				<div class="mypc-icon pull-right"
					onclick="location.href='adminItem'">
					<span class="glyphicon glyphicon-list"></span>
				</div>
				<h1>상품 등록</h1>
			</div>
			<!-- 상품 등록 -->
			<div class="col-md-12">
				<div class="well">
					<form id="mypc-form" method="post" class="form-inline mypc-form" role="form" enctype="multipart/form-data">
						<fieldset>
							<div class="row">
								<div class="form-group col-md-2 col-xs-4 text-center mypc-div">
									<label for="category" class="mypc-label">카테고리</label>
								</div>

								<div class="form-group col-md-4 col-xs-8 mypc-div">
									<select data-placeholder="카테고리를 선택해주세요." name="category"
										id="category"
										class="chosen-select-deselect mypc-select chosen-control"></select>
								</div>

								<div class="form-group col-md-2 col-xs-4 text-center mypc-div">
									<label for="manufac" class="mypc-label">제조사</label>
								</div>
								<div class="form-group col-md-4 col-xs-8 mypc-div">
									<select data-placeholder="제조사를 선택해주세요." name="manufac"
										id="manufac" class="chosen-select-deselect mypc-select"></select>
								</div>

								<div class="form-group col-md-2 col-xs-4 text-center mypc-div">
									<label for="item_name" class="mypc-label">상품이름</label>
								</div>
								<div class="form-group col-md-4 col-xs-8 mypc-div">
									<input type="text" name="item_name" id="item_name"
										class="form-control mypc-input" maxlength="70" />
								</div>

								<div class="form-group col-md-2 col-xs-4 text-center mypc-div">
									<label for="spec" class="mypc-label">스펙</label>
								</div>
								<div class="form-group col-md-4 col-xs-8 mypc-div">
									<select data-placeholder=" 상품 스펙을 선택해주세요." name="spec"
										id="spec" class="chosen-select mypc-input" multiple>
									</select>
								</div>

								<div class="form-group col-md-2 col-xs-4 text-center mypc-div">
									<label for="price" class="mypc-label">가격</label>
								</div>
								<div class="form-group col-md-4 col-xs-8 mypc-div">
									<input type="text" name="price" id="price"
										class="form-control mypc-input" placeholder="단위: 원"
										maxlength="20" />
								</div>

								<div class="form-group col-md-2 col-xs-4 text-center mypc-div">
									<label for="stock" class="mypc-label">재고</label>
								</div>
								<div class="form-group col-md-4 col-xs-8 mypc-div">
									<input type="number" name="stock" id="stock"
										class="form-control mypc-input" placeholder="수량을 입력해주세요"
										min="1" max="10000" />
								</div>

								<div class="form-group col-md-2 col-xs-2 text-center mypc-div">
									<label for="rel_date" class="mypc-label">출시일</label>
								</div>
								<div class="form-group col-md-10 col-xs-10 mypc-div">
									<div class="input-group">
										<input type="text" name="rel_date" id="rel_date"
											class="form-control" readonly /> <span id="mypc-showCal"
											class="input-group-addon"><span
											class="glyphicon glyphicon-calendar"></span></span>
									</div>
								</div>

								<div
									class="form-group col-md-2 col-xs-2 text-center mypc-div-image">
									<label for="mypc-photo1" class="mypc-label">상품 이미지</label>
								</div>
								<div class="form-group col-md-4 col-xs-10 mypc-div-image">
									<div class="mypc-div-imageWrap">
										<a><img id="mypcImg1"
											src="${pageContext.request.contextPath}/download?file=camera.png" /></a>
									</div>
									<input type="file" class="filestyle" name="photo1" id="photo1"
										data-img="mypcImg1" /> <input type="hidden"
										class="mypc-hidden1" name="hidden1" />
								</div>

								<div
									class="form-group col-md-2 col-xs-2 text-center mypc-div-image">
									<label for="mypc-photo2" class="mypc-label">상품 설명 이미지</label>
								</div>
								<div class="form-group col-md-4 col-xs-10 mypc-div-image">
									<div class="mypc-div-imageWrap">
										<a><img id="mypcImg2"
											src="${pageContext.request.contextPath}/download?file=camera.png" /></a>
									</div>
									<input type="file" class="filestyle" name="photo2" id="photo2"
										data-img="mypcImg2" /> <input type="hidden"
										class="mypc-hidden2" name="hidden2" />
								</div>

								<div class="col-md-12 col-xs-12">
									<button type="submit" class="btn btn-default pull-right">상품등록</button>
									<button type="button" class="btn btn-default pull-right">직접입력</button>
								</div>
							</div>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</section>

	<%@ include file="./inc/footer.jsp"%>
	<%@ include file="./inc/adminScript.jsp"%>
	<script src="${pageContext.request.contextPath}/assets/plugins/handlebars/handlebars-v4.3.1.js"></script>
	<script src="${pageContext.request.contextPath}/assets/plugins/ajax-form/jquery.form.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/plugins/chosen/chosen.js"></script>
	<script src="${pageContext.request.contextPath}/assets/plugins/filestyle/filestyle.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/plugins/number/jquery.number.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/plugins/datepicker/datepicker.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/plugins/datepicker/i18n/datepicker.ko-KR.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/adminItemRegist.js"></script>
	<script id="mypc-template1" type="text/x-handlebars-template">
		{{#each item.0.category}}
			<option value="{{this}}">{{this}}</option>
		{{/each}}
	</script>

	<script id="mypc-template2" type="text/x-handlebars-template">
		{{#each item.1.manufac}}
			{{getManufac this}}
		{{/each}}
	</script>

	<script id="mypc-template3" type="text/x-handlebars-template">
		{{#each item.2.spec}}
			{{getSpec this}}
		{{/each}}
	</script>

	<script type="text/javascript">
		var contextPath = "${pageContext.request.contextPath}";
		var itemno = '<c:out value="${itemno}"/>';
		$(function() {
			adminCommon(); 		// 로그아웃
			getCategory(); 		// ajax로 카테고리와 그에 맞는 제조사 항목을 불러옴
			imageUpload(); 		// filestyle Plug-In	(이미지 업로드 input 스타일 변경 및 이미지 업로드 관련 Js)
			priceFormat();		// number Plug-In		(가격 3자리 단위마다 ,입력)
			datePicker(); 		// datepicker Plug-In	(날짜 선택 위젯)
			valiDation(itemno); // validate Plug-In		(유효성 검사)
			editItem(itemno);   // 상품 수정
		});
	</script>
</body>
</html>