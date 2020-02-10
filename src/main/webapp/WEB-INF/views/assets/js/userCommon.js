var json = {
	"menu" : [ {
		"sub01" : []
	}, {
		"sub02" : [ "CPU", "Mainboard", "RAM", "VGA", "HDD", "SSD" ]
	}, {
		"sub03" : []
	}, {
		"sub04" : []
	} ]
};

/** -------------- 사이드바 기능 -------------- */

function checkEmptyCookie(){
	if(cookieValues.length==0){
		$("ul#cookie_list").append("<li class='empty-item'>오늘 본 상품이 없습니다.</li>");
	}

}
// 최근 본 상품에 출력되는 값들은 쿠키를 활용,
// 해당 function으로 cookieitem+itemno의 형식으로 저장된 쿠키 값들을 불러와 사이드바에 추가합니다.
function getCookieList() {
	$.ajax({
		url : "cookieList",
		type : 'POST',
		data : {
			"cookieValues" : cookieValues
		}, // userCommJs.jsp 에서 선언한 변수
		success : function(json) {
			$("#cookie_list li").remove();										// 최근 본 상품이 남아있을 수 있기 때문에 모든 상품을 모두 지우고,	
			checkEmptyCookie();													// 최근 본 항목이 없는지 체크한 후에
			var template = Handlebars.compile($("#mypc-cookie-list").html());	// 최근 본 항목이 존재한다면 최근 본 항목 리스트에 상품을 추가합니다.
			var html = template(json);
			// 사이드바 영역 추가
			$("#cookie_list").append(html);
		}
	});
}

// 최근 본 상품 삭제 기능
function removeCookie(itemno) {
	function ajax1() {
		// new Promise() 추가
		return new Promise(function(resolve, reject) {
			$.ajax({
				url : "removeCookie/cookieItem" + itemno,
				type : 'DELETE',
				success : function(response) {
					// 페이지 로드시 cookieValues 변수에 쿠키값들이 저장됩니다.
					// 페이지 새로고침시 cookieValues 변수에 삭제된 쿠키값이 적용되어있지만,
					// ajax 통신을 하였기 때문에 cookieValues array에서 해당 상품번호를 삭제합니다.
					for (var i = 0; i < cookieValues.length; i++) {
						if (cookieValues[i] == itemno) {
							cookieValues.splice(i, 1);
						}
					}
					console.log("삭제"+cookieValues);
					// 데이터를 받으면 resolve() 호출
					resolve(response);

					// location.reload();
				}
			});
		});
	}
	// getData()의 실행이 끝나면 호출되는 then()
	ajax1().then(function(response) {
		// resolve()의 결과 값이 여기로 전달됨
		// console.log(response); // $.get()의 reponse 값이 전달됨
		getCookieList();
	});
}

function userCommon() {
	/** ------------ 사이드바 --------------* */
	$(".mypc-sideBar").mouseenter(function() {
		$(".mypc-sideBar").css({
			'right' : '-43px',
			'height' : '453px'
		});
	});

	$(".mypc-sideBar").mouseleave(function() {
		$(".mypc-sideBar").css({
			'right' : '-180px',
			'height' : '453px'
		});
	});

	$(".bottom-btn").click(function(e) {
		e.preventDefault();
		$('html, body').scrollTop($(document).height());
	});

	var url_index = window.location.pathname.lastIndexOf('/');
	var url = window.location.pathname.substring(url_index + 1);

	/** -------------- 아이콘 클릭시 이벤트 -------------- */
	$("body").click(function(e) {
		if (!$(e.target).hasClass("common-serach")) {
			if (!$(e.target).hasClass("common-input")) {
				$("#common-input").val("");
				$("#common-nav-ul01").fadeIn();
				$("#common-form").hide();
			}
		}
	});

	/** -------------- 검색창 기능 -------------- */
	$("#common-search").click(function(e) {
		e.preventDefault();
		if ($("#common-nav-ul01").is(":visible")) {
			$("#common-nav-ul01").hide();
			$("#common-form").fadeIn();
		} else {
			if ($("#common-input").val() == "") {
				$("#common-nav-ul01").fadeIn();
				$("#common-form").hide();

			} else {
				$("#common-form").submit();
			}
		}
	});

	$("#common-nav-ul01 li").hover(
			function(e) {
				var menu = json.menu;
				var keyList = []; // 배열에 sub01, sub02, sub03, sub04 저장
				var valueList = []; // submenu안에 하위 배열이 들어있음 ex)valueList[1][1]
				// => Mainboard
				$("#common-submenu-ul01").html("");
				// json 파싱
				for (var i = 0; i < json.menu.length; i++) {
					$.each(menu[i], function(key, value) {
						keyList.push(key);
						valueList.push(value);
					});
				}
				// 서브메뉴 동적 생성
				for (var i = 0; i < keyList.length; i++) {
					if ($(e.target).data("submenu") === keyList[i]) {
						// AboutUs,상품목록,게시판,고객센터의 해당하는 각각의 서브메뉴 항목을 동적 생성
						for (var j = 0; j < valueList[i].length; j++) {
							$("#common-submenu-ul01").append(
									"<li class='animated fadeIn'><a href='itemList?keyword="
											+ valueList[i][j] + "'>"
											+ valueList[i][j] + "</a></li>");
							$("#common-submenu-ul01 li").addClass(keyList[i]);
						}
					}
				}
				$("#common-submenu-ul01").fadeIn();
			}, function(e) {
				$("#common-submenu").hover(function() {
				}, function() {
					$("#common-submenu-ul01").html("");
				});
			});

	/** -------------- 사이드바 기능 -------------- */
	getCookieList();	// 쿠키 값을 읽어와서 최근 본 상품 항목 추가
	checkEmptyCookie();	// 최근 본 상품이 비어있는지 확인하고 요소 추가
	// 사이드바 최근 본 상품 항목 클릭 시 해당 상품 view로 이동
	$(document).on("click", ".mypc-sideBar-img", function(e) {
		var itemno = $(this).data("itemno");
		e.preventDefault();
		ajaxRedirect(itemno);
	});

	// 사이드바 최근 본 상품 항목 제거
	$(document).on("click", ".mypc-cookieRemove", function(e) {
		e.preventDefault(); // 부모 요소인 a태그의 href 페이지 이동 방지
		e.stopPropagation(); // 부모 요소의 이벤트 실행 방지
		var itemno = $(this).data("remove");
		removeCookie(itemno);
	});

	/** -------------- 로그아웃 기능 -------------- */
	$("#logout").click(function(e) {
		e.preventDefault();
		swal({
			text : "로그아웃 하시겠습니까?",
			type : 'question',
			confirmButtonText : 'Yes',
			showCancelButton : true,
			cancelButtonText : 'No',
		}).then(function(result) {
			if (result.value) {
				$.ajax({
					url : "sessionRemove",
					success : function() {
						swal({
							text : '로그아웃 되었습니다.',
							type : 'success',
							confirmbuttonText : 'Yes'
						}).then(function(result) {
							if (result.value) {
								location.href = "index";
							}
						})
					}
				})
			}
		})
	})
};