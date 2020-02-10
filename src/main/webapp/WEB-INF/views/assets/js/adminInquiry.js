function allCheck() {
	// 상품 전체선택 기능입니다.
	$(".mypc-cbxAll").click(function() {
		if ($(".mypc-cbxAll").is(":checked")) {
			$(".mypc-cbx").prop("checked", true);
		} else {
			$(".mypc-cbx").prop("checked", false);
		}
	});
}

function removeInquiry(contextPath) {
	$(".mypc-btn").click(function() {
		var checkboxValues = [];
		// 선택된 체크박스를 배열에 넣습니다.
		$("input[name='mypc-cbx']:checked").each(function(i) {
			checkboxValues.push($(this).val());
		});
		
		if (checkboxValues.length == 0) {
			swal({
				text : "삭제할 글을 선택해주세요.",
				type : 'warning',
				confirmButtonText : 'Yes'
			});
		} else {
			swal({
				text : "선택한 문의글을 삭제 하시겠습니까?",
				type : 'question',
				confirmButtonText : 'Yes',
				showCancelButton : true,
				cancelButtonText : 'No',
			}).then(function(result){
				if (result.value){	
					// 문의글을 삭제하고 페이지를 새로고침합니다.
					$.ajax({
						url : contextPath +"/deleteInquiry/"+checkboxValues,
						type : 'DELETE',
						success : function(json) {
							if (json.rt == "OK") {
								swal({
									text : '문의글이 삭제되었습니다.',
									type : 'success',
									confirmbuttonText : 'Yes'
								}).then(function(result) {
									if (result.value) {
										location.reload();
									}
								})
							}
						}
					})
				}
			})
		}
	})
}