// 카테고리 input에 옵션들을 itemRegist.json 파일로부터 읽어옵니다.
function getCategory() {
	$.get("assets/api/itemRegist.json", function(req) {
		// HTML 틀을 읽어온다.
		var template = Handlebars.compile($("#mypc-template1").html());
		// Ajax를 통해 읽어온 JSON을 템플릿에 병합
		var html = template(req);
		$("#category").append(html);
		// 선택 필드의 옵션을 업데이트하고 Chosen이 변경 사항을 선택하도록 하기 위함
		$("#category").trigger('chosen:updated');
		chosen();
	});

	// 카테고리 선택값이 변경된 경우
	$("#category").change(function() {
		// 선택된 제조사 초기화
		$("select#manufac option").remove();
		// 선택된 스펙 초기화
		$("select#spec option").remove();
		// 변경된 카테고리 값에 따른 제조사 옵션과 스펙 옵션들을 다시 읽어옵니다.
		getManufac();
		getSpec();
	});
}

// 선택된 카테고리 값에 따른 제조사 옵션을 추출합니다.
function getManufac(itemno, manufac) {
	// 핸들바 사용자 정의 헬퍼
	Handlebars.registerHelper('getManufac', function(manufac) {
		var selCategory = $("#category").val(); // 선택된 카테고리의 값
		var temp = [];
		var output = "";
		
		$.each(manufac[selCategory], function(index, item) {
			temp[index] = item; // ex) manufac["CPU"]의 값들(cpu1,cpu2...)을 저장
			output += "<option value='" + temp[index] + "'>" + temp[index]
					+ "</option>"
		});
		return new Handlebars.SafeString(output);
	});

	$.get("assets/api/itemRegist.json", function(req) {
		var template = Handlebars.compile($("#mypc-template2").html());
		// Ajax를 통해 읽어온 JSON을 템플릿에 병합
		var html = template(req);
		$("#manufac").append(html);
		
		// 상품 등록의 경우
		if(itemno==""){
			$("#manufac").trigger('chosen:updated');
		}
		// 상품 수정의 경우
		else{
			$('#manufac').val(manufac).trigger('chosen:updated');
		}
	});

}

//선택된 카테고리 값에 따른 스펙 옵션을 추출합니다.
function getSpec(itemno, spec) {
	// 핸들바 사용자 정의 헬퍼
	Handlebars.registerHelper('getSpec', function(spec) {
		var selCategory = $("#category").val(); // 선택된 카테고리의 값
		var temp = [];
		var output = "";

		$.each(spec[selCategory], function(index, item) {
			temp[index] = item; // ex) manufac["CPU"]의 값들(cpu1,cpu2...)을 저장
			// json 배열
			var jsonObj = temp[index];
			for(key in jsonObj) {
				output += "<optgroup label='"+key+"'>"
				for(var i=0; i<jsonObj[key].length; i++){
					output += "<option>"+jsonObj[key][i]+"</option>"
				}
				output += "</optgroup>"
			}
		});
		return new Handlebars.SafeString(output);
	});

	$.get("assets/api/itemRegist.json", function(req) {
		var template = Handlebars.compile($("#mypc-template3").html());
		// Ajax를 통해 읽어온 JSON을 템플릿에 병합
		var html = template(req);
		$("#spec").append(html);
		
		// 상품 등록의 경우
		if(itemno==""){
			$("#spec").trigger('chosen:updated');
		}
		// 상품 수정의 경우
		else{
			$('#spec').val(spec).trigger('chosen:updated');
		}
	});
}

// 상품 수정
function editItem(itemno) {
	// itemno가 존재하는 경우
	if (itemno != "") {
		$.ajax({
			url : "itemListGet/" + itemno,
			method : "POST",
			success : function(json) {
				if (json.rt == "OK") {
					var spec = json.item.spec.split(','); // 스펙을 구분자(',')로 자른 후 array에 담습니다.
					var img1 = json.item.item_img1;
					var img2 = json.item.item_img2;
					img1 = img1.substring(img1.lastIndexOf('/')+1);
					img2 = img2.substring(img2.lastIndexOf('/')+1);
					$('#category').val(json.item.category).trigger('chosen:updated');
					getManufac(itemno, json.item.manufac);
					getSpec(itemno, spec);
					$('#item_name').val(json.item.item_name);
					$('#price').val(json.item.price);
					$('#stock').val(json.item.stock);
					$('#rel_date').val(json.item.rel_date);
					$('#mypcImg1').attr("src",contextPath + "/download?file="+json.item.item_img1);
					$('#mypcImg2').attr("src",contextPath + "/download?file="+json.item.item_img2);	
					$('.mypc-hidden1').val(contextPath + json.item.item_img1);
					$('.mypc-hidden2').val(contextPath + json.item.item_img2);
					$("#photo1").filestyle('placeholder', img1);
					$("#photo2").filestyle('placeholder', img2);
				}
			}
		}); // end ajax
	}
}

// filestyle Plug-In 및 이미지 업로드
function imageUpload() {
	$("#photo1").filestyle('buttonText', ''); // filestyle Plug-In
	$("#photo2").filestyle('buttonText', ''); // filestyle Plug-In

	function readURL(input, output) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e) {
				output.attr('src', e.target.result);
			}
			reader.readAsDataURL(input.files[0]); // 파일의 URL을 읽어와서 img태그의 url로 반환합니다.
		}
	}
	
	// input type="file"의 값이 바뀔 때
	$("#photo1").on("change", function() {
		var output = $("#mypcImg1");
		readURL(this, output);
	});
	
	// input type="file"의 값이 바뀔 때
	$("#photo2").on("change", function() {
		var output = $("#mypcImg2");
		readURL(this, output);
	});
}

//chosen plug-in
function chosen() {
	$('.chosen-select').chosen();
	$('.chosen-select-deselect').chosen({
		allow_single_deselect : true
	});
}

// number Plug-In ex) #,###
function priceFormat() {
	$("#price").number(true); 
}

// datepicker Plug-In
function datePicker() {
	$("#rel_date").datepicker({ 		// datepicker Plug-In
		autoHide : true, 				// 날짜 선택 후 자동 숨김
		format : 'yyyy-mm-dd', 			// 날짜 형식
		language : 'ko-KR', 			// 언어
		weekStart : 0, 					// 시작요일(0=일요일 ~ 6=토요일)
		trigger : '#mypc-showCal' 		// 클릭시 달력을 표시할 요소의 id
	}).on('change', function() {
		$("#rel_date").valid();
	});
}

// validate Plug-In
function valiDation(itemno) {
	jQuery.validator.setDefaults({
		onkeyup : false,
		onclick : false,
		onfocusout : false,
		ignore : ":hidden:not(select)", // 카테고리와 제조사의 유효성 검사를 위한 구문(chosen Plug-In의 Select태그에 유효성 검사를 적용시키기 위함)
		showErrors : function(errorMap, errorList) {
			if (this.numberOfInvalids()) {				
				$(errorList[0].element).focus();
				swal({
					title : "에러",
					text : errorList[0].message,
					type : "question",
				}).then(function(result) {
					setTimeout(function() {
						$(errorList[0].element).val('');
						$(errorList[0].element).focus();
					}, 100);
					
					// 에러가 발생한 element data 속성을 가져온다.
					var imgId = $(errorList[0].element).data("img");
					// 해당 img 태그의 src path를 초기화
					$("#"+imgId).attr("src",contextPath+"/download?file=camera.png");
					// 선택한 이미지를 초기화
					$(errorList[0].element).filestyle('clear'); 
				});
			}
			
		}
	});

	/* input 파일사이즈 유효성 검사 */
	$.validator.addMethod('filesize', function(value, element, param) {
		// value -> 파일명.확장자
		// element -> input 태그 element
		// param -> 파일 사이즈 20000000
		return this.optional(element) || (element.files[0].size <= param)
	}, '{0} 크기 이하의 파일만 업로드 가능합니다.')

	/* input 파일사이즈 유효성 검사 */
	$.validator.addMethod('filesize', function(value, element, param) {
		// value -> 파일명.확장자
		// element -> input 태그 element
		// param -> 파일 사이즈 20000000
		return this.optional(element) || (element.files[0].size <= param)
	}, '{0} 크기 이하의 파일만 업로드 가능합니다.')
	$("#mypc-form").validate({
		rules : {
			category : {
				required : true
			},
			manufac : {
				required : true
			},
			item_name : {
				required : true
			},
			spec : {
				required : true
			},
			price : {
				required : true,
				digits : true,
				min : 1
			},
			stock : {
				required : true,
				number : true,
				max : 10000,
				min : 1
			},
			rel_date : {
				required : true,
				dateISO : true
			},
			photo1 : {
				required : function(element) {
					// itemno가 존재하는 경우 상품 수정이므로 이미지 유효성 검사 제외(이미지1,2 중 1개의 이미지만 수정하는 경우와 이미지를 모두 수정하지 않는 경우가 존재하기 때문에.)
					if (itemno != ""){
						return false;
					} else{
						return true;
					}
				},
				extension : "jpg|jpeg|png",
				// ex) 200000 -> Max File Size 200kb
				filesize : 20000000		
			},
			photo2 : {
				required : function(element) {
					// itemno가 존재하는 경우 상품 수정이므로 이미지 유효성 검사 제외(이미지1,2 중 1개의 이미지만 수정하는 경우와 이미지를 모두 수정하지 않는 경우가 존재하기 때문에.)
					if (itemno != ""){
						return false;
					} else{
						return true;
					}
				},
				extension : "jpg|jpeg|png",
				// ex) 200000 -> Max File Size 200kb
				filesize : 20000000
			}
		},
		messages : {
			category : {
				required : "상품 카테고리를 입력하세요."
			},
			manufac : {
				required : "상품 제조사를 입력하세요."
			},
			item_name : {
				required : "상품 이름을 입력해주세요.",
				maxlength: $.validator.format( "{0}자를 넘을 수 없습니다." ),
			},
			spec : {
				required : "상품 스펙을 입력해주세요."
			},
			price : {
				required : "상품 가격을 입력해주세요.",
				min : $.validator.format("상품 가격을 확인해주세요."),
				digits : "제품 가격을 확인해주세요."
			},
			stock : {
				required : "상품 재고를 입력해주세요.",
				number : "숫자만 입력해주세요.",
				max : $.validator.format("{0}개 이하의 수량을 입력해주세요."),
				min : $.validator.format("상품 재고를 확인해주세요.")
			},
			rel_date : {
				required : "상품 출시일을 입력해주세요.",
				dateISO : "상품 출시일의 형식이 잘못되었습니다."
			},
			photo1 : {
				required : "상품 이미지를 등록해주세요.",
				extension : "jpg, jpeg, png 확장자만 업로드 가능합니다."
			},
			photo2 : {
				required : "상품 설명 이미지를 등록해주세요.",
				extension : "jpg, jpeg, png 확장자만 업로드 가능합니다."
			}
		},
		submitHandler : function(frm) {
			swal({
				title : '확인',
				text : "상품을 등록 하시겠습니까?",
				type : 'question',
				confirmButtonText : 'Yes',
				showCancelButton : true,
				cancelButtonText : 'No',
			}).then(function(result) {
				if (result.value) {
					var formData = new FormData($('#mypc-form')[0]);
					var mypcURL = "";
					var mypcMethod = "";
					var editImg = "N";
					
					// item_img1의 변경 사항이 존재하는 경우
					if($('input[type=file]')[0].files[0]!=null){
						editImg = "imgFirst";
						//alert($('input[type=file]')[0].files[0].name); //파일이름
					}
					// item_img2의 변경 사항이 존재하는 경우
					if($('input[type=file]')[1].files[0]!=null){
						editImg = "imgSecond";
						//alert($('input[type=file]')[1].files[0].name); //파일이름
					}
					// item_img1, item_img2의 변경 사항이 모두 존재하는 경우
					if($('input[type=file]')[0].files[0]!=null && $('input[type=file]')[1].files[0]!=null){
						editImg = "imgAll";
					}
					// form의 데이터를 저장, validate 플러그인 사용을 위해 ajaxForm 대신 ajax 활용

					if(itemno==""){ 
						URL="addItem";
						mypcMethod="POST";
					}
					else{ 
						var hid1 = $('.mypc-hidden1').val();
						var hid2 = $('.mypc-hidden2').val();
						URL="editItem/"+itemno+"/"+editImg;
						mypcMethod="PUT";
					}
					
					$.ajax({
						url : URL,
						method : mypcMethod,
						data : formData,
						enctype : 'multipart/form-data', // 필수
						processData : false, // 필수
						contentType : false, // 필수
						success : function(json) {
							if (json.rt == "OK") {
								location.href="adminItem";
							}
						},
						error : function(error){
			        		swal({
			            		text: "상품 등록에 실패했습니다.",
			            		type : 'warning'
			            	});
						}
					}); // end ajax
				}
			});
		}
	});	
}