$(function(){
	
	/** 회원탈퇴 기능 **/
	$('#secession-btn').click(function(){
		swal({
			text : "회원탈퇴를 하시겠습니까?",
			type : 'question',
			confirmButtonText : 'Yes',
			showCancelButton : true,
			cancelButtonText : 'No',
		}).then(function(result) {
			if (result.value) {
				swal({
					html : "<span style='font-size:0.9em'>탈퇴한 계정의 정보는 복구가 불가능합니다.<br/><span style='font-weight:bold'><span style='font-size:0.9em'><span style='color:red'> 정말 회원탈퇴를 하시겠습니까?</span></span></span></span>",
					type : 'question',
					confirmButtonText : 'Yes',
					showCancelButton : true,
					cancelButtonText : 'No',
				}).then(function(result) {
					if (result.value) {
						var queryString = $("#secession-form").serialize();
						$.ajax({
							url : "secession_ok",
							method : "DELETE",
							data : queryString,
							success : function(json) {
								$("#secession-form").submit();
							}
						}); // end ajax
					}
				});
			}
		});
	});
});