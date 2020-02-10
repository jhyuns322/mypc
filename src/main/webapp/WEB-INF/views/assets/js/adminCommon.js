function adminCommon() {
	// 관리자 로그아웃
	$("#logout").click(function(e) {
		e.preventDefault();
		swal({
			text : "로그아웃 하시겠습니까?",
			type : 'question',
			confirmButtonText : 'Yes',
			showCancelButton : true,
			cancelButtonText : 'No',
		}).then(function(result) {
			if (result.value) {			
				$.ajax({
					url : "sessionRemove",
					success : function() {
							swal({
								text : '로그아웃 되었습니다.',
								type : 'success',
								confirmbuttonText : 'Yes'
							}).then(function(result) {
								if (result.value) {
									location.href = "index";
							}
						})
					}	
				})
			}
		})
	})					
};