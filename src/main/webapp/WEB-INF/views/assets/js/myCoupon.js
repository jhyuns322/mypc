$(function(){
	
	/** 쿠폰 삭제 **/
	$('.mypc-coupon-delete').click(function(e){
		e.preventDefault();
		
		var queryString = $(this).parents('form').serialize();
		swal({
			text : '쿠폰을 삭제하시겠습니까?',
			type : 'question',
			confirmButtonText : 'Yes',
			showCancelButton : true,
			cancelButtonText : 'no',
		}).then(function(result) {
			if (result.value) {
				swal({
					text : '쿠폰이 삭제되었습니다.',
					type : 'success',
					confirmButtonText : 'Ok',
					showCancelButton : false
				}).then(function() {
					$.ajax({
						url : "myCoupon_del",
						method : "PUT",
						data : queryString,
						success : function(json) {
							document.location.reload();
						}
					}); // end ajax
				});
			} else {
				swal("취소하였습니다.", "success");
			}
		});
	});
});