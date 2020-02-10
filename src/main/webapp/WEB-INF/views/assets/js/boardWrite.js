function ckeditor() {

	CKEDITOR.replace('content', {
		width : '100%',
		height : '550px'
	});
	
    CKEDITOR.instances["content"].on("instanceReady", function () {

        //set keyup event
        this.document.on("keyup", function () { CKEDITOR.instances["content"].updateElement(); });
        //and paste event
        this.document.on("paste", function () { CKEDITOR.instances["content"].updateElement(); });
        //and cut event
        this.document.on("cut", function () { CKEDITOR.instances["content"].updateElement(); });

    });

	$("#subject").focus();
}

function boardPost(a) {

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

	
	$("#board_post_form").validate({
		ignore : [],
		rules : {
			subject : "required",
			content : {
				required : function(element) {
					if (CKEDITOR.instances.content.getData() < 1){
						return true;
					}
				}	
			},
		},
		messages : {
			subject : "제목을 입력해주세요.",
			content : "내용을 입력해주세요."
		},
		submitHandler : function(frm) {
			swal({
				text : "게시글 작성을 완료하시겠습니까?",
				type : 'question',
				confirmButtonText : 'Yes',
				showCancelButton : true,
				cancelButtonText : 'No',
			}).then(function(result) {
				if (result.value) {
					var queryString = $("#board_post_form").serialize();
					$.ajax({
						url : "boardPost",
						method : "POST",
						data : queryString,
						success : function(json) {
							if (json.rt == "OK") {
								swal({
									text : '게시글이 등록되었습니다.',
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

	$("#board_up_form").validate({
		ignore : [],
		rules : {
			subject : "required",
			content : {
				required : function(element) {
					if (CKEDITOR.instances.content.getData() < 1){
						return true;
					}
				}	
			},
		},
		messages : {
			subject : "제목을 입력해주세요.",
			content : "내용을 입력해주세요."
		},
		submitHandler : function(frm) {
			swal({
				text : "게시글을 수정하시겠습니까?",
				type : 'question',
				confirmButtonText : 'Yes',
				showCancelButton : true,
				cancelButtonText : 'No',
			}).then(function(result) {
				if (result.value) {
					var queryString = $("#board_up_form").serialize();
					$.ajax({
						url : "boardUpdate",
						method : "PUT",
						data : queryString,
						success : function(json) {
							if (json.rt == "OK") {
								swal({
									text : '게시글이 수정되었습니다.',
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