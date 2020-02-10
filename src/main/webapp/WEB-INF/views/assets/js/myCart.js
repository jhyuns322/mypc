$(function() {
	/** a 태그 hover 없애기 시작 * */
	$('#main_contents a').hover(function() {
		$('#main_contents a').css({
			'color' : 'black'
		});
	});

	/** 체크박스Function 시작 * */
	$('.mypc-all-checked').click(function() {
		if ($('.mypc-all-checked').is(':checked')) {
			$('input[type=checkbox]').prop("checked", true);
		} else {
			$('input[type=checkbox]').prop("checked", false);
		}
	});

	$('.mypc-one-check').click(function() {

		for (var i = 0; i < $('.mypc-one-check').length; i++) {
			if ($('.mypc-one-check').eq(i).is(':checked')) {

			} else {
				$('.mypc-all-checked').prop("checked", false);
			}
		}
	});

	/** 체크된_장바구니_삭제_Function 시작 * */
	$('.mypc-delete-btn').click(function() {
		if($('.mypc-one-check').is(':checked') == true){
			swal({
				text : '선택하신 상품들을 장바구니에서 삭제하시겠습니까?',
				type : 'question',
				confirmButtonText : 'Yes',
				showCancelButton : true,
				cancelButtonText : 'no',
			}).then(function(result) {
				if (result.value) {
					swal({
						title : '삭제',
						text : '선택된 상품들이 삭제되었습니다.',
						type : 'success',
						confirmButtonText : 'Ok',
						showCancelButton : false
					}).then(function() {
						var queryString = $('#cart_form').serialize();
						$.ajax({
							url : "myCart_del_list",
							method : "DELETE",
							data : queryString,
							success : function() {
								$('#cart_form').submit();
							}
						}); // end ajax
					});
				} else {
					swal("취소", "삭제를 취소했습니다.", "success");
				}
			});
		}else {
			swal({
				text:'선택된 상품이 없습니다.',
				type:'error'
			})
		}
	});

	/** 장바구니 상품 삭제 * */
	$('.del_num').click(function() {
		var nChild = $(this).val();
		swal({
			title : '확인',
			text : '본 상품을 장바구니에서 삭제하시겠습니까?',
			type : 'question',
			confirmButtonText : 'Yes',
			showCancelButton : true,
			cancelButtonText : 'no',
		}).then(function(result) {
			if (result.value) {
				$.ajax({
					url : "myCart_del/" + nChild,
					method : "DELETE",
					success : function() {
						swal({
							title : '삭제',
							text : '성공적으로 삭제되었습니다.',
							type : 'success',
							confirmButtonText : '확인',
							showCancelButton : false
						}).then(function(result) {
							$('#cart_form').submit();
						});
					}
				}); // end ajax

			} else {
				swal({
					title : '취소',
					text : '삭제가 취소되었습니다.',
					type : 'error',
					confirmButtonText : '확인',
					showCancelButton : false
				}).then(function(result) {
					$('#cart_form').submit();
				});
			}
			;
		});
	})

	/** 수량변경_Function 시작 * */
	$('.mypc-quantity-btn').click(
			function(e) {
				e.preventDefault();

				var num = Number($(this).val());
				if ($('#mypc_quantity' + num).prop('disabled') != true) {
					var queryString = $('#cart_form').serialize();
					$.ajax({
						url : "myCart_ok",
						method : "PUT",
						data : queryString,
						success : function() {
							$('#cart_form').submit();
						}
					}); // end ajax
				} else {
					$('#mypc_quantity_btn' + num).html('완료');
					$('#mypc_quantity' + num).prop('disabled', false);
					$('.mypc-quantity-btn').not('#mypc_quantity_btn' + num)
							.html('변경');
					$('.mypc-quantity-input').not('#mypc_quantity' + num).prop(
							'disabled', true);
				}
			});
	
	$(".mypc-quantity-input").on('mousewheel',function(e){
		e.preventDefault();
		var wheel = e.originalEvent.wheelDelta;
		var value = Number($(this).val());
		
		if(wheel>0 && $(this).is(':disabled') != true){
			if(value >=99){
				return false;
			}
			$(this).val(value+1);	
		} else if(wheel<=0 && $(this).is(':disabled') != true) {
			if(value <= 1){
				return false;
			}
			$(this).val(value-1);
		}
	});

	/** 선택 상품 구매 **/
	$('.mypc-select-order-btn').click(function(e) {
		var check_val = new Array();
		if ($('input:checkbox[name="mypc-chk-li"]').is(":checked") == true) {
			
			for (var i = 0; i < $('input[name="mypc-chk-li"]:checked').length; i++){
				check_val[i] = $('input[name="mypc-chk-li"]:checked').eq(i).val();
			}
			$('#cartnum').val(check_val);
			$('#pay_form').submit();
		} else{
			swal({
				text:'선택된 상품이 없습니다.',
				type:'error'
			})
		}
	});
	
	/** 단일 상품 구매 **/
	$('.mypc_order_btn').click(function() {
		$('#cartnum').val($(this).val());
		$('#pay_form').submit();
	});
});