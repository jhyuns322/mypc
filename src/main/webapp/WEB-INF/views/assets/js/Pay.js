$(function() {
	
	/** 기본, 신규 배송지 선택 버튼 **/
	$("#mypc_address_btn li:first-child a").click(function(e) {
		e.preventDefault();
		$("#sample6_postcode").val($("#postcode").val());
		$("#sample6_address").val($("#addr1").val());
		$("#sample6_detailAddress").val($("#addr2").val());
		$("#sample6_extraAddress").val($("#extra").val());
	});

	$("#mypc_address_btn li:last-child a").click(function(e) {
		e.preventDefault();
		$("#sample6_postcode").val("");
		$("#sample6_address").val("");
		$("#sample6_detailAddress").val("");
		$("#sample6_extraAddress").val("");
	});

	var coup_num = 0;

	
	/** 쿠폰 모달 **/
	$(".mypc-coupon-btn").click(function(e) {
		e.stopImmediatePropagation();
		$("#coupon_modal").css("display", "block");
		$("#fixed_box").css("display", "block");
		coup_num = $(this).val();
		$("#white_box").click(function(e) {
			e.stopImmediatePropagation();
			$("#coupon_modal").css("display", "block");
			$("#fixed_box").css("display", "block");
		});
	});

	$("#fixed_box").click(function() {
		$("#coupon_modal").css("display", "none");
		$("#fixed_box").css("display", "none");
		coup_num = 0;
	});
	
	
	/** 쿠폰 모달 -> 쿠폰 선택 **/
	var origin_price = [];
	for(var i=0; i<=$('tbody > tr:last-child() .mypc-coupon-btn').val(); i++){
		origin_price[i] = Number(String($('#single_sale_price'+i+'').html()).replace(/,/gi,''));
	}
	var coup_order = 0;

	/** 쿠폰 클릭 **/
	$(".mypc-coupon-box td").click(function(e) {
		
		/** 쿠폰의 할인율 제한 **/
		if(Number($(this).parent().find('td:last-child() span').html())<=90){
			
			/** 할인금액 **/
			var price_result = String((origin_price[coup_num] / 100)* Number($(this).parent().find('td:last-child() span').html())).replace(/(\d)(?=(?:\d{3})+(?!\d))/g,'$1,');
			$('#coup_table' + coup_num + '').find('#coup_price' + coup_num + '').html(price_result);
			$('#single_sale_price'+coup_num+'').html(String(origin_price[coup_num]-Number(price_result.replace(/,/gi,''))).replace(/(\d)(?=(?:\d{3})+(?!\d))/g,'$1,'));
			
			
			/** 최종 할인금액 **/
			var total_sale_price = 0;
			for (var i=0; i < origin_price.length; i++){
				if($('#coup_price'+i+'').html() != 0 && $('#coup_price'+i+'').html() != null && $('#coup_price'+i+'').html() != undefined){
					total_sale_price += Number($('#coup_price'+i+'').html().replace(/,/gi,''));
				}
			}
			$('#total_sale_price').html(String(total_sale_price).replace(/(\d)(?=(?:\d{3})+(?!\d))/g,'$1,'));
			
			
			/** 최종 결제금액 **/
			var total_result_price = 0;
			for(var i =0; i< origin_price.length; i++){
				total_result_price += origin_price[i];
			}
			total_result_price -= total_sale_price;
			$('#total_result_price').html(String(total_result_price).replace(/(\d)(?=(?:\d{3})+(?!\d))/g,'$1,'));
			
			
			/** 할인율 제한초과시 **/
		} else{
			alert("잘못된 쿠폰입니다.");
		}
		e.stopImmediatePropagation();
		
		
		/** 쿠폰명 출력 **/
		$('#coup_table' + coup_num + '').children().eq(0).html($(this).parent().children('td:first-child()').html());
		
		
		/** 쿠폰 번호 기입 **/
		$('#coup_table' + coup_num + '').find('input[name="coupno' + coup_num + '"]').val(Number($(this).parent().find('td:first-child() input:first-child()').val()));
		
		
		/** 쿠폰 모달 닫기 **/
		$("#coupon_modal").css("display", "none");
		$("#fixed_box").css("display", "none");
		
		
		/** 쿠폰 순서 **/
		coup_order = $(this).parent().find('td:first-child() input:nth-child(2)').val();
		
		
		/** 선택 쿠폰 숨김 처리 **/
		$('.mypc-coupon-box').find('input.' + coup_num + '').parents('.mypc-coupon-box').css('display','table-row')
		$('.mypc-coupon-box').find('input.' + coup_num + '').removeClass(coup_num);
		$(this).parent().find('.coup_order').addClass(coup_num);
		$(this).parent().css('display','none');
	});

	
	/** null 선택 버튼 **/
	$('#coupon_null_btn td').click(function(e) {
		e.stopImmediatePropagation();
		
		
		/** 쿠폰 번호 초기화 **/
		$('#coup_table' + coup_num + '').find('input[name="coupno' + coup_num + '"]').val(0);
		
		
		/** 쿠폰 선택지 출력 **/
		$('.mypc-coupon-box').find('input.' + coup_num + '').parents('.mypc-coupon-box').css('display','table-row')
		$('.mypc-coupon-box').find('input.' + coup_num + '').removeClass(coup_num);
		
		
		/** 쿠폰 미선택시 출력 **/
		$('#coup_table' + coup_num + '').children().eq(0).html("쿠폰을 선택해주세요");
		$('#coup_table' + coup_num + '').children().eq(3).html(0);
		
		
		/** 단일상품 할인금액 **/
		$('#coup_table' + coup_num + '').find('#coup_price' + coup_num + '').html(0);
		
		
		/** 단일상품 금액 **/
		$('#single_sale_price'+coup_num+'').html(String(origin_price[coup_num]).replace(/(\d)(?=(?:\d{3})+(?!\d))/g,'$1,'));
		
		
		/** 모달창 숨기기 **/
		$("#coupon_modal").css("display", "none");
		$("#fixed_box").css("display", "none");
		
		
		/** 최종 할인 금액 **/
		var total_sale_price = 0;
		for (var i=0; i < origin_price.length; i++){
			if($('#coup_price'+i+'').html() != 0 && $('#coup_price'+i+'').html() != null && $('#coup_price'+i+'').html() != undefined){
				total_sale_price += Number($('#coup_price'+i+'').html().replace(/,/gi,''));
			}
		}
		$('#total_sale_price').html(String(total_sale_price).replace(/(\d)(?=(?:\d{3})+(?!\d))/g,'$1,'));
		
		
		/** 최종 결제금액 **/
		var total_result_price = 0;
		for(var i =0; i< origin_price.length; i++){
			total_result_price += origin_price[i];
		}
		total_result_price -= total_sale_price;
		$('#total_result_price').html(String(total_result_price).replace(/(\d)(?=(?:\d{3})+(?!\d))/g,'$1,'));
	});

	
	/** 쿠폰 모달 -> 쿠폰 선택시 호버 효과 **/
	$(".mypc-coupon-box").mouseenter(function() {
		if ($(this).find('td:first-child() input:last-child()').val() != 1&& $(this).find('td:first-child() input:last-child()').val() != 2) {
			$(this).css({"background-color" : "rgb(46, 142, 208)","color" : "#e1e1e1"});
		}
	});

	$(".mypc-coupon-box").mouseleave(function() {
		if ($(this).find('td:first-child() input:last-child()').val() == 0) {
			$(this).css({
				"background-color" : "#fff",
				"color" : "black"
			});
		}
	});

	$(".mypc-coupon-box").mousedown(function() {
		$(this).css({
			"background-color" : "rgb(16, 112, 178)",
			"color" : "whtie"
		});
	});

	$(".mypc-coupon-box").mouseup(function() {
		$(this).css({
			"background-color" : "rgb(46, 142, 208)",
			"color" : "#e1e1e1"
		});
	});
	
	
	/** order로 값 넘기기 **/
	$('.order-btn').click(function(e){
		e.preventDefault();
		
		swal({
			text : "구매하시겠습니까?",
			type : 'question',
			confirmButtonText : 'Yes',
			showCancelButton : true,
			cancelButtonText : 'No',
		}).then(function(result) {

			if (result.value) {
				for (var i = 0; i <origin_price.length; i++ ){
					if(i == 0){
						var coupno=$('#coupno'+i+'').val();
					}else if(i != 0){
						coupno +="," + $('#coupno'+i+'').val();
					}
				}
				$('input[name="coupno"').val(coupno);
				var queryString = $("#pay_form").serialize();
				$.ajax({
					url : "myPay_ok",
					method : "POST",
					data : queryString,
					success : function(json) {
						swal("","결제가 완료되었습니다.", "success").then(function(result){
							$("#pay_form").submit();
						});
					}
				}); // end ajax
			}
		});
	});
});

/** 다음 주소 api * */
function sample6_execDaumPostcode() {
	new daum.Postcode(
			{
				oncomplete : function(data) {
					// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

					// 각 주소의 노출 규칙에 따라 주소를 조합한다.
					// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
					var addr = ''; // 주소 변수
					var extraAddr = ''; // 참고항목 변수

					// 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
					if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을
						// 경우
						addr = data.roadAddress;
					} else { // 사용자가 지번 주소를 선택했을 경우(J)
						addr = data.jibunAddress;
					}

					// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
					if (data.userSelectedType === 'R') {
						// 법정동명이 있을 경우 추가한다. (법정리는 제외)
						// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
						if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
							extraAddr += data.bname;
						}
						// 건물명이 있고, 공동주택일 경우 추가한다.
						if (data.buildingName !== '' && data.apartment === 'Y') {
							extraAddr += (extraAddr !== '' ? ', '
									+ data.buildingName : data.buildingName);
						}
						// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
						if (extraAddr !== '') {
							extraAddr = ' (' + extraAddr + ')';
						}
						// 조합된 참고항목을 해당 필드에 넣는다.
						document.getElementById("sample6_extraAddress").value = extraAddr;

					} else {
						document.getElementById("sample6_extraAddress").value = '';
					}

					// 우편번호와 주소 정보를 해당 필드에 넣는다.
					document.getElementById('sample6_postcode').value = data.zonecode;
					document.getElementById("sample6_address").value = addr;
					// 커서를 상세주소 필드로 이동한다.
					document.getElementById("sample6_detailAddress").focus();
				}
			}).open();
}