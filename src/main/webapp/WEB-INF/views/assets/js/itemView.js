// 상품 리스트에서 장바구니 버튼을 클릭할 경우 수행되는 유효성 검사
function addCart(sessionUserId) {
	$(document).on("click", ".mypc-cart", function() {
		var itemNo = $(this).data('itemno');
		if (sessionUserId.length > 0) {
			$.ajax({
				url : "addCart" + itemNo,
				method : "POST",
				success : function(){
					swal({
						text : "장바구니로 이동 하시겠습니까?",
						type : 'question',
						confirmButtonText : 'Yes',
						showCancelButton : true,
						cancelButtonText : 'No',
					}).then(function(result) {
						if (result.value) {
							location.href = "myCart";
						}
					});
				},
				error : function(error) {
					if (error.status = "500")
						swal({
							text : '장바구니 추가에 실패했습니다.',
							type : 'warning'
						})
				}
			}); // end ajax
		} else {
			swal({
				text : '로그인 후 이용 가능합니다.',
				type : 'warning'
			})
		}
	})
}

// 상품 리스트에서 구매하기 버튼을 클릭할 경우 수행되는 유효성 검사
function addItem(sessionUserId) {
	$(document).on("click", ".mypc-addItem", function() {
		var itemNo = $(this).data('itemno');
		swal({
			text : "상품을 구매하시겠습니까?",
			type : 'question',
			confirmButtonText : 'Yes',
			showCancelButton : true,
			cancelButtonText : 'No',
		}).then(function(result) {
			if (result.value) {
				console.log(sessionUserId);
				if (sessionUserId.length > 0) {
					window.location = contextPath + "/pay?itemno=" + itemNo;
				} else {
					swal({
						text : '로그인 후 이용 가능합니다.',
						type : 'warning'
					})
				}
			}
		});

	})
}

// 상품을 불러오는 Ajax 구문
function get_item() {
	$.get("api/mypcItem.json", function(req) { // HTML 틀을 읽어온다.
		var template = Handlebars.compile($("#itemView-template01")
				.html()); // Ajax를 통해 읽어온 JSON을 템플릿에 병합
		Handlebars.registerHelper("checks", function(option) {
			if (this.No == "3") {
				return option.fn(this);
			} else {
				return option.inverse(this);
			}
		});
		var html = template(req);
		$("#item_view-section").append(html);
	});
}