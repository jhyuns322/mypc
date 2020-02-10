function boardDelete(contextPath) {
	$("#boardDelete").click(function(e) {
		e.preventDefault();
		
		let current = $(this);
		let docno = current.data('docno');
		let user_id = current.data('user_id');

		swal({
			text : "게시글을 삭제하시겠습니까?",
			type : 'question',
			confirmButtonText : 'Yes',
			showCancelButton : true,
			cancelButtonText : 'No',
		}).then(function(result) {
			if (result.value) {
				$.delete(contextPath + "/boardDelete", {"docno": docno, "user_id": user_id }, 
						function(json) {
							if (json.rt == "OK") {
								swal({
									text : '게시글이 삭제되었습니다.',
									type : 'success',
									confirmbuttonText : 'Yes'
								}).then(function(result) {
									if (result.value) {
										window.location = contextPath + '/boardMain';
							}
						})
					}
				})
			}
		})
	})
}

function commentDelete(contextPath) {
	$(".commentDelete").click(function() { 
		
		let current = $(this);
		let commno = current.data('commno');
		let docno = current.data('docno');
		
		swal({
			text : "덧글을 삭제하시겠습니까?",
			type : 'question',
			confirmButtonText : 'Yes',
			showCancelButton : true,
			cancelButtonText : 'No',
		}).then(function(result) {
			if (result.value) {
				$.delete(contextPath + "/commentDelete", {"commno": commno, "docno": docno}, 
						function(json) {
							if (json.rt == "OK") {
								swal({
									text : '덧글이 삭제되었습니다.',
									type : 'success',
									confirmbuttonText : 'Yes'
								}).then(function(result) {
									if (result.value) {
										location.reload();
							}
						})
					}
				})
			}
		})
	})
}

function commentVail() {
	
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
	
	$("#comment_form").validate({
		ignore: [],
		rules : {
			comment : "required",
			membno : "required"
		},
		messages : {
			comment : "덧글 내용을 입력해주세요.",
			membno : "로그인 후 이용가능합니다.."
		},
		submitHandler : function(frm) {
			swal({
				text : "덧글 작성을 완료하시겠습니까?",
				type : 'question',
				confirmButtonText : 'Yes',
				showCancelButton : true,
				cancelButtonText : 'No',
			}).then(function(result) {
				if (result.value) {
					var queryString = $("#comment_form").serialize();
					$.ajax({
						url : "commentPost",
						method : "POST",
						data : queryString,
						success : function(json) {
							if (json.rt == "OK") {
								swal({
									text : '덧글 작성이 완료되었습니다.',
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