function loginBoxEvent() {

	$('*').not($("#user_id, #user_pw, #id_box span, #pw_box span")).click(
			function(e) {
				if ($("#user_id").val() == '' || $("#user_id").val() == null) {
					$("#id_box  span").removeClass('mypc-input-click');
				}
				if ($("#user_pw").val() == '' || $("#user_pw").val() == null) {
					$("#pw_box  span").removeClass('mypc-input-click');
				}
			});

	$("#id_box span, #user_id").click(function(e) {
		e.stopPropagation();
		$("#id_box span").addClass('mypc-input-click');
		if ($("#user_pw").val() == '' || $("#user_pw").val() == null) {
			$("#pw_box span").removeClass('mypc-input-click');
		}
		$("#user_id").focus();
	});

	$('#user_id').focus(function(e) {
		e.stopPropagation();
		$('#id_box span').addClass('mypc-input-click');
	});

	$("#pw_box span, #user_pw").click(function(e) {
		e.stopPropagation();
		$("#pw_box span").addClass('mypc-input-click');
		if ($("#user_id").val() == '' || $("#user_id").val() == null) {
			$("#id_box span").removeClass('mypc-input-click');
		}
		$("#user_pw").focus();
	});
	$('#user_pw').on('focus', function(e) {
		e.stopPropagation();
		$('#pw_box span').addClass('mypc-input-click');
	});
}

function loginVail() {
	jQuery.validator.setDefaults({
		onkeyup : false,
		onclick : false,
		onfocusout : false,
		showErrors : function(errorMap, errorList) {
			if (this.numberOfInvalids()) {
				swal({
					text : errorList[0].message,
					type : "error"
				}).then(function(result) {
					setTimeout(function() {
						$(errorList[0].element).val('');
						$(errorList[0].element).focus();
					}, 100)
				});
			}
		}
	});

	$("#login_form").validate({
		rules : {
			user_id : {
				required : true,
				alphanumeric : true
			},
			user_pw : {
				required : true,
				minlength : 6,
				maxlength : 30
			}
		},
		messages : {
			user_id : {
				required : "아이디를 입력하세요.",
				alphanumeric : "아이디는 영어, 숫자만 입력가능합니다."
			},
			user_pw : {
				required : "비밀번호를 입력하세요.",
				minlength : "비밀번호는 6글자 이상 입력하셔야 합니다.",
				maxlength : "비밀번호는 최대 30자까지 가능합니다."
			}
		},
		submitHandler : function(frm) {
			var queryString = $("#login_form").serialize();
			$.ajax({
				url : "sessionSaveAdmin",
				method : "POST",
				data : queryString,
				error : function(error) {
					swal({
						text : '아이디 또는 비밀번호가 일치하지 않습니다.',
						type : 'error',
						confirmbuttonText : 'Yes'
					})
				},
				success : function(json) {
					if (json.rt == "OK") {
						swal({
							text : '관리자 계정으로 로그인 되었습니다.',
							type : 'success',
							confirmbuttonText : 'Yes'
						}).then(function(result) {
							if (result.value) {
								frm.submit();
							}
						})
					}
				}
			});
		}
	});
};