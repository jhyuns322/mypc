function emailCheck() {
	// 중복검사 버튼 클릭 시 이벤트 발동
	$("#email_uniq_check").click(function() {

		// valid() 함수를 사용하여 id="email"인 input의 값만 유효성 검사 실시
		// 통과 = true / 실패 = false
		var emailVaild = $("#email").valid();

		// valid()의 return값이 true면 ajax 요청 시작
		if (emailVaild == true) {
			var email = $('#email').val();

			// 중복 이메일 검사를 위한 controller로 ajax 요청
			$.ajax({
				url : "emailCheckForUpdate?email=" + email,
				method : "GET",
				success : function(json) {
					// 0 == 중복된 이메일이 DB에 존재하지 않음
					if (json.item == 0) {
						swal({
							text : '사용 가능한 이메일 주소입니다.',
							type : 'success',
							confirmbuttonText : 'Yes'
						})
						$('#email').prop('readonly', true);
						// 버튼(중복검사 <-> 수정하기) 교체
						$('#email_uniq_check').css("display", "none");
						$('#edit_email').css("display", "inline");
						$('#ckd_email').val(json.item);
					} else if (json.item == 1) {
						swal({
							text : '현재 사용 중인 이메일 주소입니다.',
							type : 'success',
							confirmbuttonText : 'Yes'
						})
						$('#email').prop('readonly', true);
						// 버튼(중복검사 <-> 수정하기) 교체
						$('#email_uniq_check').css("display", "none");
						$('#edit_email').css("display", "inline");
						$('#ckd_email').val(0);
					} else {
						swal({
							text : '이미 등록된 이메일 주소입니다.',
							type : 'warning',
							confirmbuttonText : 'Yes'
						})
						setTimeout(function() {
							$("#email").val('');
							$("#email").focus();
						}, 100);
					}
				}
			});
		}
	});
	// 수정하기 버튼 클릭 이벤트
	$('#edit_id').click(function() {
		$('#edit_id').css("display", "none");
		$('#id_uniq_check').css("display", "inline");
		$('#user_id').prop('readonly', false).val('').focus();
		$('#ckd_id').val(1);
	});
	// 수정하기 버튼 클릭 이벤트
	$('#edit_email').click(function() {
		$('#edit_email').css("display", "none");
		$('#email_uniq_check').css("display", "inline");
		$('#email').prop('readonly', false).focus();
		$('#ckd_email').val(1);
	});
}

function memberInfoVali() {

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

	$.validator.addMethod("kor", function(value, element) {
		return this.optional(element) || /^[ㄱ-ㅎ가-힣]*$/i.test(value);
	});
	$.validator.addMethod("phone", function(value, element) {
		return this.optional(element)
				|| /^01(?:0|1|[6-9])(?:\d{3}|\d{4})\d{4}$/i.test(value)
				|| /^\d{2,3}\d{3,4}\d{4}$/i.test(value);
	});

	$("#memberInfo_form").validate({
		rules : {
			name : {
				required : true,
				kor : true
			},
			birthdate : {
				required : true,
				date : true
			},
			gender : "required",
			email : {
				required : true,
				email : true
			},
			tel : "phone"
		},
		messages : {
			name : {
				required : "이름을 입력하세요.",
				kor : "이름은 한글만 입력 가능합니다."
			},
			birthdate : {
				required : "생년월일을 입력하세요.",
				date : "생년월일의 형식이 잘못되었습니다."
			},
			gender : "성별을 선택해 주세요.",
			email : {
				required : "이메일을 입력하세요.",
				email : "이메일 형식이 잘못되었습니다."
			},
			tel : "연락처 형식이 잘못되었습니다."
		},
		// validation 통과 후 실시되는 submit 막기
		submitHandler : function(frm) {
			if ($('#ckd_email').val() == 1) {
				swal("", "이메일 중복검사가 필요합니다.", "warning");
				return;
			}
			swal({
				text : "회원 정보를 저장하시겠습니까",
				type : 'question',
				confirmButtonText : 'Yes',
				showCancelButton : true,
				cancelButtonText : 'No',
			}).then(function(result) {
				if (result.value) {
					var queryString = $("#memberInfo_form").serialize();
					// queryString에 값을 담고 ajax 요청
					$.ajax({
						url : "memberEdit",
						method : "POST",
						data : queryString,
						// controller에서 중단되지 않을 경우 success
						success : function(json) {
							if (json.rt == "OK") {
								swal({
									text : '저장되었습니다.',
									type : 'success',
									confirmbuttonText : 'Yes'
								}).then(function(result) {
									if (result.value) {
										frm.submit();
									}
								})
							}
						}
					}); // end ajax
				}
			});
		}
	});
}

function memberDelete(contextPath) {
	$("#memberDelete").click(function(e) {
		e.preventDefault();
		
		let current = $(this);
		let membno = current.data('membno');

		swal({
			text : "회원정보를 삭제하시겠습니까?",
			type : 'question',
			confirmButtonText : 'Yes',
			showCancelButton : true,
			cancelButtonText : 'No',
		}).then(function(result) {
			if (result.value) {
				$.delete(contextPath + "/memberDelete", {"membno": membno}, 
						function(json) {
							if (json.rt == "OK") {
								swal({
									text : '회원 정보가 삭제되었습니다.',
									type : 'success',
									confirmbuttonText : 'Yes'
								}).then(function(result) {
									if (result.value) {
										window.location = contextPath + '/adminMember';
							}
						})
					}
				})
			}
		})
	})
}

function daumPostcode() {
	new daum.Postcode({
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
					extraAddr += (extraAddr !== '' ? ', ' + data.buildingName
							: data.buildingName);
				}
				// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
				if (extraAddr !== '') {
					extraAddr = '(' + extraAddr + ')';
				}
				// 조합된 참고항목을 해당 필드에 넣는다.
				document.getElementById("extraAddress").value = extraAddr;

			} else {
				document.getElementById("extraAddress").value = '';
			}

			// 상세주소 readonly 해제
			$("#detailAddress").attr("placeholder", "상세주소를 입력하세요.").attr("readonly", false)
			// 우편번호와 주소 정보를 해당 필드에 넣는다.
			document.getElementById('postcode').value = data.zonecode;
			document.getElementById("address").value = addr;
			// 커서를 상세주소 필드로 이동한다.
			document.getElementById("detailAddress").focus();
		}
	}).open();
}