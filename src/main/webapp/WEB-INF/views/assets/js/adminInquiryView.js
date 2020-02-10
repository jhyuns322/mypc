function buttonEvent(userId) { 
	var result = 0;
	$('.mypc-inquiry-button').click(function() {
		if("mypc" == userId) {
			swal({
				text : '탈퇴한 회원입니다.',
				type : 'error',
				confirmbuttonText : 'Yes'
			})
			return;
		}
		$('.mypc-inquiry-button').toggleClass('rotate');
		if (result == 0) {
			$('#hiddenForm').css('height', '400px');
			result++;
		} else {
			$('#hiddenForm').css('height', '0');
			result--;
		}
	});

	if ($(window).width() <= 992) {
		$('a.mypc-inquiry-button').css('left', '14.5%');
	} else if ($(window).width() <= 1200 && $(window).width() > 992) {
		$('a.mypc-inquiry-button').css('left', '11.5%');
	} else {
		$('a.mypc-inquiry-button').css('left', '9.5%');
	}

	function widthResize() {
		var windowWidth = $(window).width();
		if (windowWidth <= 992) {
			$('a.mypc-inquiry-button').css('left', '14.5%');
		} else if (windowWidth <= 1200 && windowWidth > 992) {
			$('a.mypc-inquiry-button').css('left', '11.5%');
		} else {
			$('a.mypc-inquiry-button').css('left', '9.5%');
		}
	}
	$(window).resize(function() {
		widthResize();
	});
};

function inquiryReplyVali() {

	jQuery.validator.setDefaults({
		onkeyup : false,
		onclick : false,
		onfocusout : false,
		showErrors : function(errorMap, errorList) {
			if (this.numberOfInvalids()) {
				$(errorList[0].element).focus();
				swal({
					text : errorList[0].message,
					type : "question"
				}).then(function(result) {
					setTimeout(function() {
						$(errorList[0].element).val('');
						$(errorList[0].element).focus();
					}, 100);
				});
			}
		}
	});

	$("#inquiryReplyForm").validate({
		rules : {
			mailSubject : "required",
			mailContent : "required"
		},
		messages : {
			mailSubject : "제목을 입력해주세요.",
			mailContent : "문의 내용을 입력해주세요."
		},
		submitHandler : function(frm) {
			swal({
				text : "문의글을 전달하시겠습니까?",
				type : 'question',
				confirmButtonText : 'Yes',
				showCancelButton : true,
				cancelButtonText : 'No',
			}).then(function(result) {
				if (result.value) {
					var queryString = $("#inquiryReplyForm").serialize();
					$.ajax({
						url : "inquiryReply",
						method : "POST",
						data : queryString,
						success : function(json) {
							if (json.rt == "OK") {
								swal({
									text : '메일 전송이 완료 되었습니다.',
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
		}
	});
}