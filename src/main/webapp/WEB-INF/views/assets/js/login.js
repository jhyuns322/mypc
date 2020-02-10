$(function() {
	$('*')
			.not(
					$("#user_id, #user_pw, #id_box span, #pw_box span, #mypc-find-id-box span,"
							+ " #mypc_find_id, #mypc-find-pw-box span, #mypc_find_pw,"
							+ " #mypc-find-pw-box01 span, #mypc_find_pw01"))
			.click(
					function() {
						if ($("#user_id").val() == ''
								|| $("#user_id").val() == null) {
							$("#id_box  span").removeClass('mypc-input-click');
						}
						if ($("#user_pw").val() == ''
								|| $("#user_pw").val() == null) {
							$("#pw_box  span").removeClass('mypc-input-click');
						}
						if ($("#mypc_find_id").val() == ''
								|| $("#mypc_find_id").val() == null) {
							$("#mypc-find-id-box  span").removeClass(
									'mypc-input-click');
						}
						if ($("#mypc_find_pw").val() == ''
								|| $("#mypc_find_pw").val() == null) {
							$("#mypc-find-pw-box  span").removeClass(
									'mypc-input-click');
						}
						if ($("#mypc_find_pw01").val() == ''
								|| $("#mypc_find_pw01").val() == null) {
							$("#mypc-find-pw-box01  span").removeClass(
									'mypc-input-click');
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

	if ($("#user_pw").val() == '' || $("#user_pw").val() == null) {
		$("#pw_box span").removeClass('mypc-input-click');
	}

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

	$("#mypc-find-id-box  span, #mypc_find_id").click(function(e) {
		e.stopPropagation();
		$("#mypc-find-id-box  span").addClass('mypc-input-click');
		$("#mypc_find_id").focus();
	});
	$('#mypc_find_id').on('focus', function(e) {
		e.stopPropagation();
		$('#mypc-find-id-box span').addClass('mypc-input-click');
	});

	$("#mypc-find-pw-box  span, #mypc_find_pw").click(function(e) {
		e.stopPropagation();
		$("#mypc-find-pw-box  span").addClass('mypc-input-click');
		$("#mypc_find_pw").focus();
	});
	$('#mypc_find_pw').on('focus', function(e) {
		e.stopPropagation();
		$('#mypc-find-pw-box span').addClass('mypc-input-click');
	});

	$("#mypc-find-pw-box01 span, #mypc_find_pw01").click(function(e) {
		e.stopPropagation();
		$("#mypc-find-pw-box01 span").addClass('mypc-input-click');
		$("#mypc_find_pw01").focus();
	});
	$('#mypc_find_pw01').on('focus', function(e) {
		e.stopPropagation();
		$('#mypc-find-pw-box01 span').addClass('mypc-input-click');
	});

	$('#mypc-find-info').children().eq(0).click(function(e) {
		e.preventDefault();

		$('.mypc-slide-box').eq(0).css({
			'left' : '-498px'
		});
		$('.mypc-slide-box').eq(1).css({
			'left' : '0'
		});

		$('#user_id').val('');
		$('#user_pw').val('');
	});

	$('#mypc-find-info').children().eq(1).click(function(e) {
		e.preventDefault();

		$('.mypc-slide-box').eq(0).css({
			'left' : '-498px'
		});
		$('.mypc-slide-box').eq(2).css({
			'left' : '0'
		});

		$('#user_id').val('');
		$('#user_pw').val('');
	});

	$('.mypc-slide-box:nth-child(3) .mypc-back-btn a').click(function(e) {
		e.preventDefault();

		$('.mypc-slide-box').eq(0).css({
			'left' : '0px'
		});
		$('.mypc-slide-box').eq(1).css({
			'left' : '498px'
		});

		$('#findId_form').val('');
	});

	$('.mypc-slide-box:nth-child(4) .mypc-back-btn a').click(function(e) {
		e.preventDefault();

		$('.mypc-slide-box').eq(0).css({
			'left' : '0px'
		});
		$('.mypc-slide-box').eq(2).css({
			'left' : '498px'
		});

		$('#findPwCI_form').val('');
	});

	$('.mypc-slide-box:nth-child(5) .mypc-back-btn a').click(function(e) {
		e.preventDefault();

		$('.mypc-slide-box').eq(2).css({
			'left' : '0px'
		});
		$('.mypc-slide-box').eq(3).css({
			'left' : '498px'
		});
		$('.mypc-slide-box').eq(1).css({
			'left' : '498px'
		});

		$('#findPwSE_form').val('');
	});

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

	$("#login_form").validate(
			{
				rules : {
					user_id : {
						required : true,
						alphanumeric : true,
						minlength : 6,
						maxlength : 16
					},
					user_pw : {
						required : true,
						minlength : 6,
						maxlength : 20
					}
				},
				messages : {
					user_id : {
						required : "아이디를 입력하세요.",
						alphanumeric : "아이디는 영어, 숫자만 입력가능합니다.",
						minlength : "아이디는 6글자 이상 입력하셔야 합니다.",
						maxlength : "아이디는 최대 16자까지 가능합니다."
					},
					user_pw : {
						required : "비밀번호를 입력하세요.",
						minlength : "비밀번호는 6글자 이상 입력하셔야 합니다.",
						maxlength : "비밀번호는 최대 20자까지 가능합니다."
					}
				},
				submitHandler : function(frm) {
					var queryString = $("#login_form").serialize();
					$.ajax({
						url : "sessionSave",
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
								var name = json.item.name;
								swal(
										{
											html : '<strong>' + name
													+ '</strong>' + '님 환영합니다.',
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

	$("#findId_form").validate({
		rules : {
			email : {
				required : true,
				email : true
			}
		},
		messages : {
			email : {
				required : "이메일을 입력해주세요",
				email : "이메일 형식이 잘못되었습니다."
			}
		},
		submitHandler : function(frm) {
			var queryString = $("#findId_form").serialize();
			$.ajax({
				url : "findUserId",
				method : "POST",
				data : queryString,
				error : function(error) {
					swal({
						text : '등록된 이메일 주소가 아닙니다.',
						type : 'error',
						confirmbuttonText : 'Yes'
					})
				},
				success : function(json) {
					if (json.rt == "OK") {
						swal({
							text : '등록된 메일로 아이디가 발송되었습니다.',
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

	$("#findPwCI_form")
			.validate(
					{
						rules : {
							user_id : {
								required : true,
								alphanumeric : true
							}
						},
						messages : {
							user_id : {
								required : "아이디를 입력하세요.",
								alphanumeric : "아이디는 영어, 숫자만 입력가능합니다.",
								minlength : "아이디는 6글자 이상 입력하셔야 합니다.",
								maxlength : "아이디는 최대 16자까지 가능합니다."
							}
						},
						submitHandler : function(frm) {
							var queryString = $("#findPwCI_form").serialize();
							$
									.ajax({
										url : "findPwCheckId",
										method : "POST",
										data : queryString,
										error : function(error) {
											swal({
												text : '등록된 아이디가 아닙니다.',
												type : 'error',
												confirmbuttonText : 'Yes'
											})
										},
										success : function(json) {
											if (json.rt == "OK") {
												document.getElementById(
														"mypc_find_pw")
														.setAttribute('value',
																'');
												document
														.getElementById("findPwCI_form").style.left = "-498px";
												document
														.getElementById("findPwSE_form").style.left = "0px";
											}
										}
									});
						}
					});

	$("#findPwSE_form").validate({
		rules : {
			email : {
				required : true,
				email : true
			}
		},
		messages : {
			email : {
				required : "이메일을 입력해주세요",
				email : "이메일 형식이 잘못되었습니다."
			}
		},
		submitHandler : function(frm) {
			var queryString = $("#findPwSE_form").serialize();
			$.ajax({
				url : "findPwSendEmail",
				method : "POST",
				data : queryString,
				error : function(error) {
					swal({
						text : '등록된 아이디와 이메일 주소가 일치하지 않습니다.',
						type : 'error',
						confirmbuttonText : 'Yes'
					})
				},
				success : function(json) {
					if (json.rt == "OK") {
						swal({
							text : '등록된 메일로 임시비밀번호가 발송되었습니다.',
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

	/** tab키 방지 */
	$("*").keydown(function(e) {
		if (e.keyCode == 9) {
			e.preventDefault();
		}
	});

	$("#user_id").keydown(function(e) {
		if (e.keyCode == 9) {
			$("#user_pw").focus();
		}
	});

	$("#user_pw").keydown(function(e) {
		if (e.keyCode == 9) {
			$("#user_id").focus();
		}
	});

	$('#user_id').on("propertychange change keyup paste input", function() {
		if (!$('#user_id, #user_pw').val().equal('')) {
			$('#id_box span').addClass('mypc-input-click')
		}
	});
});