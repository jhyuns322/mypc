function checkLogin() {
	$("#writebtn").click(function() {
		var userId = $("#session").val();

		if (userId == "") {
			swal({
				text : '로그인 후 이용 가능합니다.',
				type : 'warning',
				confirmbuttonText : 'Yes'
			})
		} else {
			window.location = "boardWrite"
		}
	});
}