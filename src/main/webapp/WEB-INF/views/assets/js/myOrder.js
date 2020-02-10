$(function() {

	/** 필터링을 위한 변수 **/
	var MM = Number($("#MM").val());
	var date = [];
	var year = [];

	for (var i = 0; i < 5; i++) {
		date[i] = (MM - i);
		year[i] = $("#YY").val();
		if (date[i] < 1) {
			date[i] += 12;
			year[i] = $("#YY").val()-1;
		}
	}

	
	/** 주문취소 버튼 **/
	$('.order_cancel').click(function(e) {
		e.preventDefault();
		var queryString = $(this).parent().serialize();
		swal({
			text : '주문을 취소하시겠습니까?',
			type : 'question',
			confirmButtonText : 'Yes',
			showCancelButton : true,
			cancelButtonText : 'no',
		}).then(function(result) {
			if (result.value) {
				swal({
					text : '주문이 취소되었습니다.',
					type : 'success',
					confirmButtonText : 'Ok',
					showCancelButton : false
				}).then(function() {
					
					$.ajax({
						url : "myOrder_cancel",
						method : "PUT",
						data : queryString,
						success : function(json) {
							$("#date_form").submit();
						}
					}); // end ajax
				});
			} else {
				swal("취소하였습니다.");
			}
		});
	});

	
	/** 월별 필터링 버튼 **/
	$("#month0").click(function(e) {
		e.preventDefault();

		$("#MM").val(date[0]);
		$("#YY").val(year[0]);

		$("#date_form").submit();
	});

	$("#month1").click(function(e) {
		e.preventDefault();

		$("#MM").val(date[1]);
		$("#YY").val(year[1]);

		$("#date_form").submit();
	});

	$("#month2").click(function(e) {
		e.preventDefault();

		$("#MM").val(date[2]);
		$("#YY").val(year[2]);

		$("#date_form").submit();
	});

	$("#month3").click(function(e) {
		e.preventDefault();

		$("#MM").val(date[3]);
		$("#YY").val(year[3]);

		$("#date_form").submit();
	});

	$("#month4").click(function(e) {
		e.preventDefault();

		$("#MM").val(date[4]);
		$("#YY").val(year[4]);

		$("#date_form").submit();
	});

	
	/** 전체검색 버튼 **/
	$("#all_select").click(function(e) {
		e.preventDefault();

		$("#MM").val(13);

		$("#date_form").submit();
	});
	
	
	/** 세부 필터링 **/
	$("#lookup").click(function(e) {
		e.preventDefault();

		$("#MM").val(0);

		$("#date_form").submit();
	});
	
	
	/** 페이지 로드시 조회를 위한 value값 **/
	$("#MM").val(13);
	
	
	/** 상세보기 버튼 **/
	$('.modal_btn, #white_box').click(function(e){
		 e.stopPropagation();
		 $('#order_modal'+$(this).val()+'').css('display','block');
		 $('#fixed_box'+$(this).val()+'').css('display','block');
	 });
	 var i =0
	 $('.mypc-fixed-box').click(function(e){
		 i = $(this).find('input.modal_close').val()
		 $('#order_modal'+i+'').css('display','none');
		 $('#fixed_box'+i+'').css('display','none');
	 });
});