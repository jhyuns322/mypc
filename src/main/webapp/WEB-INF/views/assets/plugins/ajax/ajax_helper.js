$(function() {
	// 로딩이미지를 표시하기 위한 div를 jquery로 추가하기
	var loader = $("<div>").addClass('ajax-loader');
	loader.hide();
	$("body").prepend(loader);

	$.ajaxSetup({
		/** ajax 기본 옵션 */
		cache : false, // 캐쉬 사용 금지 처리
		dataType : 'json', // 읽어올 내용 형식 (html,xml,json)
		timeout : 30000, // 타임아웃 (30초)

		// 통신 시작전 실행할 기능 (ex: 로딩바 표시)
		beforeSend : function() {
			// 현재 통신중인 대상 페이지를 로그로 출력함
			console.log(">> Ajax 통신 시작 " + this.url);
			loader.show();
		},
		// 통신 실패시 호출될 함수 (파라미터는 에러내용)
		error : function(error) {
			// 404 -> Page Not Found
			// 50x -> Server Error(웹 프로그램 에러)
			// 200, 0 -> 내용의 형식 에러(JSON,XML)
			console.log(">> 에러 >> " + error.status);

			// 서버에서 반환하는 json 데이터가 존재한다면?
			if (error.responseJSON) {
				// 서버에서 반환하는 json 데이터에 rt라는 key가 있다면?
				if (error.responseJSON.rt) {
					// 해당 내용을 메시지로 표시하고 처리 중단.
					alert(error.responseJSON.rt);
					console.log(error.responseJSON.rt);
					return;
				}
			}

			var error_msg = "[" + error.status + "] " + error.statusText;

			var code = parseInt(error.status / 100);
			if (code == 4) { // 400번대의 에러인 경우
				error_msg = "잘못된 요청입니다.\n" + error_msg;
			} else if (code == 5) {
				error_msg = "서버의 응답이 없습니다.\n" + error_msg;
			} else if (code == 2 || code == 0) {
				error_msg = "서버의 응답이 잘못되었습니다.\n" + error_msg;
			}

			alert(error_msg);
		},
		// 성공,실패에 상관 없이 맨 마지막에 무조건 호출됨 ex) 로딩바 닫기
		complete : function() {
			console.log(">> Ajax 통신 종료");
			loader.hide();
		}
	});
});

// jQuery Ajax가 restful을 처리할 수 있도록 기능 확장.
// 출처: https://stackoverflow.com/
jQuery.each([ "put", "delete" ], function(i, method) {
	jQuery[method] = function(url, data, callback, type) {
		if (jQuery.isFunction(data)) {
			type = type || callback;
			callback = data;
			data = undefined;
		}

		return jQuery.ajax({
			url : url,
			type : method,
			dataType : type,
			data : data,
			success : callback
		});
	};
});

// HTML 특수문자를 원래의 구문으로 되돌리는 함수.
function htmlspecialchar_decode(content) {
	// 임의의 HTML 태그를 생성하여 내용을 출력시킨다.
	// --> append 등의 출력 처리를 하지 않았으므로 화면에 표시되지는 않는
	var div = $("<div>").html(content);
	// 브라우저에 표시될 내용을 리턴한다.
	return div.text();
}