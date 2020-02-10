/** 유효성 검사 **/
function infoVali(e) {
	
	jQuery.validator.setDefaults({
		onkeyup : false,
		onclick : false,
		onfocusout : false,
		showErrors : function(errorMap, errorList) {
			if (this.numberOfInvalids()) {
				$(errorList[0].element).focus();
				swal({
					title : "에러",
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

	$.validator.addMethod("phone", function(value, element) {
		return this.optional(element)
				|| /^01(?:0|1|[6-9])(?:\d{3}|\d{4})\d{4}$/i.test(value)
				|| /^\d{2,3}\d{3,4}\d{4}$/i.test(value);
	});

	$("#edit_info").validate({
		rules : {
			mypc_now_pw : {
				required : true,
				equalTo : "#now-pw",
				minlength : 6,
				maxlength : 20
			},                                                                                                                                   
			mypc_ch_pw:{
				notEqualTo:'#mypc_now_pw'
			},
			mypc_ch_pw_re:{
				equalTo :function(){
					if($("#mypc_ch_pw").val() !=''){
						return $("#mypc_ch_pw_re").val();
					}
				}
			},
			mypc_ch_tel : "phone",
			mypc_ch_email : {
				email : true,
				notEqualTo:'#now_email',
				required : function(){
					if($('#mypc_ch_pw').val() == ""){
						if($('#mypc_ch_email').val() == ""){
							if($('#mypc_ch_tel').val() == null){
								if($('#sample6_postcode').val() == ""){
									return true;
								}
							}
						}
					}
					return false;
				}
			}
		},
		messages : {
			mypc_now_pw: {
				required : "비밀번호를 입력하세요.",
				equalTo:"현재 비밀번호가 다릅니다.",
				minlength : "비밀번호는 6글자 이상 입력하셔야 합니다.",
				maxlength : "비밀번호는 최대 20자까지 가능합니다."
			},
			mypc_ch_pw:{
				notEqualTo:'현재 비밀번호와 다르게 입력해주세요'
			},
			mypc_ch_pw_re:{
				equalTo :"변경할 비밀번호와 다릅니다."
			},
			mypc_ch_email : {
				email : "이메일 형식이 잘못되었습니다.",
				notEqualTo:'이메일을 다르게 입력해주세요.',
				required :'변동사항이 없습니다.'
			},
			mypc_ch_tel : "연락처 형식이 잘못되었습니다."
			
		},
		submitHandler : function(frm) {
			var queryString = $('#edit_info').serialize();
			$.ajax({
				url : "myInfo_ok",
				method : "PUT",
				data : queryString,
				success : function(json) {
					frm.submit();
				}
			}); // end ajax
		}
	});
}

/** 다음 주소 api **/
function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
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