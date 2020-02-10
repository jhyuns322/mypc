function couponVali() {
	
	jQuery.validator.setDefaults({
		onkeyup : false,
		onclick : false,
		onfocusout : false,
		showErrors : function(errorMap, errorList) {
			if (this.numberOfInvalids()) {
				$(errorList["전체", 0].element).focus();
				swal({
					text : errorList["전체", 0].message,
					type : "question"
				}).then(function(result) {
					setTimeout(function() {
						$(errorList["전체", 0].element).val('');
						$(errorList["전체", 0].element).focus();
					}, 100);
				});
			}
		}
	});
	$("#coupon_form").validate({
		rules : {
			coupon_name : {
				required : true,
				maxlength : 20
			},
			coupon_off : {
				required : true,
				number : true,
				range : [1, 100]
			},
			expired_date : {
				required : true,
				date : true
			},
		},
		messages : {
			coupon_name : {
				required : "쿠폰명을 입력하세요.",
				maxlength : "쿠폰명을 최대 20자까지 가능합니다."
			},
			coupon_off : {
				required : "쿠폰 할인율을 입력하세요.",
				number : "할인율은 숫자로만 입력 가능합니다.",
				range : "할인율은 1부터 100 사이의 숫자로 입력하세요."
			},
			expired_date : {
				required : "쿠폰 만료일을 입력하세요.",
				date : "만료일 형식이 잘못되었습니다."
			},
		},
		submitHandler : function(frm) {
			swal({
				text : "쿠폰 지급을 완료하시겠습니까?",
				type : 'question',
				confirmButtonText : 'Yes',
				showCancelButton : true,
				cancelButtonText : 'No',
			}).then(function(result) {
				if (result.value) {
					var queryString = $("#coupon_form").serialize();
					$.ajax({
						url : "couponSet",
						method : "POST",
						data : queryString,
						error : function(json) {
							swal({
								text : '쿠폰 지급 대상이 아닙니다.',
								type : 'error',
								confirmbuttonText : 'Yes'
							})
						},
						success : function(json) {
							if (json.rt == "OK") {
								swal({
									text : '쿠폰 지급이 완료되었습니다.',
									type : 'success',
									confirmbuttonText : 'Yes'
								}).then(function(result) {
									if (result.value) {
										frm.submit();
									}
								})
							}
						},
					});
				}
			});
		}
	});
}

function couponModal() {
	$("#open_modal_btn").click(function(e) {
		$("#couponModal").modal('show');
	});

	$("#member_all").click(function() {
		var chk = $(this).is(":checked");
		if (chk)
			$("#user_id").attr("readonly", true).val('');
		else
			$("#user_id").attr("readonly", false).val('');
	})
	
	$('.modal').on('hidden.bs.modal', function (e) {
		// 모달창 내의 내용을 강제로 지움.
		$(this).removeData('bs.modal');
	});
}

function nowDate() {
	var d = new Date();
	var currentDate = d.getFullYear() + " - " + (d.getMonth() + 1) + " - "
			+ d.getDate();
	var result = document.getElementById("nowDate");
	result.innerHTML = currentDate + " ~ ";
}
