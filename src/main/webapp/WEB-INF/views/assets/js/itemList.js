var itemAlignment = "0"; // 상품 정렬 옵션
var minimumPrice = null;
var maximumPrice = null;
var checkedOption = null;

/* 컨트롤러에서 받은 show 값에 따라 View 화면 분기처리 */
function viewChange(show, path, keyword) {
	// 상품 카테고리 값이 있는 경우(keyword)
	if (show == "true") {
		getOptionList(keyword); // 해당 상품 옵션을 불러온다.
		getItemList(path); // 해당 상품 리스트를 불러온다.
	}
	// 상품 카테고리 값이 없는 경우(keyword)
	else {
		$("#mypc-div04").removeClass("common-hide");
	} // 상품 카테고리를 선택할 수 있는 태그 요소를 보여준다.

}

/* 상품 목록을 불러오는 function */
function getItemList(path) {
	$("#mypc-div01").removeClass("common-hide"); // 상품 옵션 div
	$("#mypc-div02").removeClass("common-hide"); // 상품 정렬 및 조건 검색 div
	$("#mypc-div03").removeClass("common-hide"); // 상품 리스트 div
	$(".mypc-input").number(true); // number Plug-In

	search = ""; // 상품 검색 시 search 값을 초기화하여 컨트롤러의 if(search=="") 조건을 만족시키게
					// 한다.
	var checkboxValues = [];
	// 체크박스의 값들을 배열에 담는다.
	$("input[name='mypc-cbx']:checked").each(function(i) {
		checkboxValues.push($(this).val());
	});

	// 상품 가격을 #,###와 같은 형식으로 표시
	Handlebars.registerHelper('formatNumber', function(price) {
		var formatPrice = price.toLocaleString(); // 세 자릿수마다 ,를 찍어주는
		// javascript 내장함수
		return formatPrice;
	});

	$.ajax({
		url : path,
		type : 'GET',
		data : {
			"itemAlignment" : itemAlignment,
			"checkArray" : checkboxValues,
			"minPri" : minimumPrice,
			"maxPri" : maximumPrice
		},
		success : function(data) {
			$(".mypc-ul03 div").remove();
			if (data.meta.totalCount == 0) {
				swal({ text : "조건에 일치하는 상품이 존재하지 않습니다.", type : 'warning' });
				$(".mypc-ul03").append("<div>조건에 일치하는 상품이 존재하지 않습니다.</div>");
			}
			var template = Handlebars.compile($("#mypc-template02").html());
			// Ajax를 통해 읽어온 JSON을 템플릿에 병합
			var html = template(data);
			$("#mypc-ul03 li").remove();
			$(".pagination li").remove();
			$("#mypc-ul03").append(html);
			if (path.indexOf("&page=") > -1) {
				path = path.substring(0, path.indexOf("&page="));
			}
			pagination(data, path);
		}
	});
}

// 상품 옵션 체크박스를 불러오는 function
function getOptionList(keyword) {
	Handlebars.registerHelper('getItemOption', function(spec) {
		var output = "";
		$.each(spec[keyword], function(index, item) {
			output += "<div class='col-md-2 col-xs-6 mypc-optionBox'> <h4>"
					+ Object.keys(item) + "</h3>"
					+ "<ul class='row mypc-ul01'>"
			for (var i = 0; i < item[Object.keys(item)].length; i++) {
				output += "<li><input id='mypc-cbx" + index + i
						+ "'name='mypc-cbx' type='checkbox' value='"
						+ item[Object.keys(item)][i]
						+ "' /> <label for='mypc-cbx" + index + i + "'>"
						+ item[Object.keys(item)][i] + "</label></li>"
			}
			output += "</ul></div>"
		});
		return new Handlebars.SafeString(output);
	});

	// 상품 옵션 정보의 json 파일을 읽어온다.
	$.get("assets/api/itemRegist.json", function(req) {
		// HTML 틀을 읽어온다.
		var template = Handlebars.compile($("#mypc-template01").html());
		// Ajax를 통해 읽어온 JSON을 템플릿에 병합
		var html = template(req);
		$(".mypc-clear").before(html);
	});
}

// 키워드가 없을 경우 보여지는 View에서 이미지를 클릭했을 시, itemList에 키워드를 전송하여 다른 View를 보여준다.
function categoryRedirect() {
	$("#mypc-div04 img").click(function(e) {
		var keyword = $(this).data("submenu");
		location.href = "itemList?keyword=" + keyword;
	});
}

// 상품 리스트에서 장바구니 버튼을 클릭할 경우 수행되는 유효성 검사
function addCart(sessionUserId) {
	$(document).on("click", ".mypc-cart", function() {
		var itemNo = $(this).data('itemno');
		if (sessionUserId.length > 0) {
			$.ajax({
				url : "addCart" + itemNo,
				method : "POST",
				success : function(){
					swal({
						text : "장바구니로 이동 하시겠습니까?",
						type : 'question',
						confirmButtonText : 'Yes',
						showCancelButton : true,
						cancelButtonText : 'No',
					}).then(function(result) {
						if (result.value) {
							location.href = "myCart";
						}
					});
				},
				error : function(error) {
					if (error.status = "500")
						swal({
							text : '장바구니 추가에 실패했습니다.',
							type : 'warning'
						})
				}
			}); // end ajax
		} else {
			swal({
				text : '로그인 후 이용 가능합니다.',
				type : 'warning'
			})
		}
	})
}

// 상품 리스트에서 구매하기 버튼을 클릭할 경우 수행되는 유효성 검사
function addItem(sessionUserId) {
	$(document).on("click", ".mypc-addItem", function() {
		var itemNo = $(this).data('itemno');
		swal({
			text : "상품을 구매하시겠습니까?",
			type : 'question',
			confirmButtonText : 'Yes',
			showCancelButton : true,
			cancelButtonText : 'No',
		}).then(function(result) {
			if (result.value) {
				console.log(sessionUserId);
				if (sessionUserId.length > 0) {
					window.location = contextPath + "/pay?itemno=" + itemNo;
				} else {
					swal({
						text : '로그인 후 이용 가능합니다.',
						type : 'warning'
					})
				}
			}
		});

	})
}

function cbxSelect(path) {
	$(document).on("change", "input[name='mypc-cbx']", function() {
		checkedOption = this;
		getItemList(path);
	});

	// 상품 전체선택 기능입니다.
	$(".mypc-clear").click(function() {
		if ($("input[name='mypc-cbx']").is(":checked")) {
			$("input[name='mypc-cbx']").prop("checked", false);
		}
		itemAlignment = "0"; // 상품 정렬 옵션
		minimumPrice = null;
		maximumPrice = null;
		checkedOption = null;
		getItemList(path);
	});
}

function alignSelect(path) {
	$(".mypc-ul02 li a").click(function(e) {
		e.preventDefault();
		itemAlignment = $(this).data("align"); // 선택한 상품리스트 정렬 값
		getItemList(path);
	});
}

/* 페이지 번호 구현 */
function pagination(data, path) {
	var page = data.meta;
	var endPage = page.endPage;
	var nextPage = page.nextPage;
	var nowPage = page.nowPage;
	var prevPage = page.prevPage;
	var startPage = page.startPage;

	var output = "";
	var pageUrl = path + "&page=";

	// 이전 그룹에 대한 링크
	if (prevPage > 0) {
		output += "<li><a href='#' onclick='getItemList(\"" + pageUrl
				+ prevPage + "\"); return false;'>&laquo;</a></li>"
	} else {
		output += "<li class='disabled'><a href='#'>&laquo;</a></li>"
	}

	// 페이지 번호 출력
	for (var i = startPage; i <= endPage; i++) {
		if (nowPage == i) {
			output += "<li class='active'><a>" + i + "</a></li>"
		} else {
			output += "<li><a href='#' onclick='getItemList(\"" + pageUrl + i
					+ "\"); return false;'>" + i + "</a><li>"
		}
	}

	// 다음 그룹에 대한 링크
	if (nextPage > 0) {
		output += "<li><a href='#' onclick='getItemList(\"" + pageUrl
				+ nextPage + "\"); return false;'>&raquo;</a></li>"
	} else {
		output += "<li class='disabled'><a href='#'>&raquo;</a></li>"
	}

	$(".pagination").append(output);
}

function itemPrice(path) {
	$(".mypc-form").submit(
			function(e) {
				e.preventDefault();
				minimumPrice = $(".mypc-input:first-child").val();
				maximumPrice = $(".mypc-input:nth-child(2)").val();
				// 최저가와 최고가 input value를 가져온다.
				if ($(".mypc-input:first-child").val() == ""
						|| $(".mypc-input:nth-child(2)").val() == "") {
					swal({
						text : '가격을 모두 입력해주세요.',
						type : 'warning'
					})
				}
				// 최저가와 최고가 input value가 모두 존재하는 경우
				else {
					// 만약 최저가가 최고가 이상일 경우
					if (Number(minimumPrice) >= Number(maximumPrice)) {
						swal({
							text : '올바른 값을 입력해주세요.',
							type : 'warning'
						})
					}
					// 조건을 모두 충족했을 경우 상품 검색
					else {
						getItemList(path);
					}
				}
			});
}
