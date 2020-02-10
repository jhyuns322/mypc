function allCheck() {
	// 상품 전체선택 기능입니다.
	$(".mypc-cbxAll").click(function() {
		if ($(".mypc-cbxAll").is(":checked")) {
			// $("input[name='mypc-cbx']").attr("checked",true);
			$(".mypc-cbx").prop("checked", true);
		} else {
			// $("input[name='mypc-cbx']").attr("checked",false);
			$(".mypc-cbx").prop("checked", false);
		}
	});
}

function removeItem(path) {
	$(".mypc-btn").click(function() {
		var checkboxValues = [];
		// 선택된 체크박스를 배열에 넣습니다.
		$("input[name='mypc-cbx']:checked").each(function(i) {
			checkboxValues.push($(this).val());
		});
		
		if (checkboxValues.length == 0) {
			swal({
				text : "삭제할 상품을 선택해주세요.",
				type : 'warning'
			});
		} else {
			swal({
				text : "선택한 상품을 삭제 하시겠습니까?",
				type : 'question',
				confirmButtonText : 'Yes',
				showCancelButton : true,
				cancelButtonText : 'No',
			}).then(function(result){
				if (result.value){	
					// 상품을 삭제하고 페이지를 새로고침합니다.
					$.ajax({
						url : path + checkboxValues,
						type : 'DELETE',
						success : function(data) {
							location.reload();
						}
					});
				}
			})
		}
		
		
	});
}

function editItem() {
	$(".mypc-itemno").click(function() {
		var itemno = $(this).data("value");
		location.href = "adminItemRegist?itemno=" + itemno;
	});
}