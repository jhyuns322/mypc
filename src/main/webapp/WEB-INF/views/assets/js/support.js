function supportVali() {
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
	$("#inquiry_form").validate({
		rules : {
			subject : "required",
			content : "required"
		},
		messages : {
			subject : "제목을 입력해주세요.",
			content : "문의 내용을 입력해주세요."
		},
		submitHandler : function(frm) {
			swal({
				text : "문의글을 전달하시겠습니까?",
				type : 'question',
				confirmButtonText : 'Yes',
				showCancelButton : true,
				cancelButtonText : 'No',
			}).then(function(result) {
				if (result.value) {
					var queryString = $("#inquiry_form").serialize();
					$.ajax({
						url : "supportPost",
						method : "POST",
						data : queryString,
						success : function(json) {
							if (json.rt == "OK") {
								swal({
									text : '문의글이 전송 되었습니다.',
									type : 'success',
									confirmbuttonText : 'Yes'
								}).then(function(result) {
									if (result.value) {
										frm.submit();
									}
								})
							}
						}
					});
				}
			});
		}
	});
}

var selectors = function(sido, gugun, co) {
	var obj = this;
	obj.sido = document.getElementById(sido);
	obj.gugun = document.getElementById(gugun);
	obj.co = document.getElementById(co);
	obj.update_sido();
	obj.update_co();

	obj.sido.onchange = function() {
		obj.update_gugun.apply(obj);
	}
}

selectors.prototype = {
	update_co : function() {
		if (this.co == null)
			return;
		var co = this['제조사'];
		for (var i = 0; i < co.length; i++)
			this.co.options.add(new Option(co[i], co[i]));
	},
	update_gugun : function() {
		if (this.gugun == null)
			return;
		var gugun = this[this.sido.value];
		this.gugun.innerHTML = "";
		for (var i = 0; i < gugun.length; i++)
			this.gugun.options.add(new Option(gugun[i], gugun[i]));
	},
	update_sido : function() {
		if (this.sido == null)
			return;
		var sido = this['시도'];
		for (var i = 0; i < sido.length; i++)
			this.sido.options.add(new Option(sido[i], sido[i]));
		this.update_gugun();
	},

	"시도" : [ "서울특별시", "부산광역시", "대구광역시", "인천광역시", "광주광역시", "대전광역시", "울산광역시",
			"강원도", "경기도", "경상남도", "경상북도", "전라남도", "전라북도", "제주도", "충청남도", "충청남도" ],
	"서울특별시" : [ "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구",
			"도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구",
			"양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구" ],
	"부산광역시" : [ "강서구", "금정구", "남구", "동구", "동래구", "부산진구", "북구", "사상구", "사하구",
			"서구", "수영구", "연제구", "영도구", "중구", "해운대구", "기장군" ],
	"대구광역시" : [ "남구", "달서구", "동구", "북구", "서구", "수성구", "중구", "달성군" ],
	"인천광역시" : [ "계양구", "남구", "남동구", "동구", "부평구", "서구", "연수구", "중구", "강화군",
			"옹진군" ],
	"광주광역시" : [ "광산구", "남구", "동구", "북구", "서구" ],
	"대전광역시" : [ "대덕구", "동구", "서구", "유성구", "중구" ],
	"울산광역시" : [ "남구", "동구", "북구", "중구", "울주군" ],
	"강원도" : [ "강릉시", "동해시", "삼척시", "속초시", "원주시", "춘천시", "태백시", "고성군", "양구군",
			"양양군", "영월군", "인제군", "정선군", "철원군", "평창군", "홍천군", "화천군", "횡성군" ],
	"경기도" : [ "고양시 덕양구", "고양시 일산동구", "고양시 일산서구", "과천시", "광명시", "광주시", "구리시",
			"군포시", "김포시", "남양주시", "동두천시", "부천시 소사구", "부천시 오정구", "부천시 원미구",
			"성남시 분당구", "성남시 수정구", "성남시 중원구", "수원시 권선구", "수원시 영통구", "수원시 장안구",
			"수원시 팔달구", "시흥시", "안산시 단원구", "안산시 상록구", "안성시", "안양시 동안구",
			"안양시 만안구", "양주시", "오산시", "용인시 기흥구", "용인시 수지구", "용인시 처인구", "의왕시",
			"의정부시", "이천시", "파주시", "평택시", "포천시", "하남시", "화성시", "가평군", "양평군",
			"여주군", "연천군" ],
	"경상남도" : [ "거제시", "김해시", "마산시", "밀양시", "사천시", "양산시", "진주시", "진해시", "창원시",
			"통영시", "거창군", "고성군", "남해군", "산청군", "의령군", "창녕군", "하동군", "함안군",
			"함양군", "합천군" ],
	"경상북도" : [ "경산시", "경주시", "구미시", "김천시", "문경시", "상주시", "안동시", "영주시", "영천시",
			"포항시 남구", "포항시 북구", "고령군", "군위군", "봉화군", "성주군", "영덕군", "영양군",
			"예천군", "울릉군", "울진군", "의성군", "청도군", "청송군", "칠곡군" ],
	"전라남도" : [ "광양시", "나주시", "목포시", "순천시", "여수시", "강진군", "고흥군", "곡성군", "구례군",
			"담양군", "무안군", "보성군", "신안군", "영광군", "영암군", "완도군", "장성군", "장흥군",
			"진도군", "함평군", "해남군", "화순군" ],
	"전라북도" : [ "군산시", "김제시", "남원시", "익산시", "전주시 덕진구", "전주시 완산구", "정읍시", "고창군",
			"무주군", "부안군", "순창군", "완주군", "임실군", "장수군", "진안군" ],
	"제주도" : [ "제주시", "서귀포시" ],
	"충청남도" : [ "계룡시", "공주시", "논산시", "보령시", "서산시", "아산시", "천안시 동남구", "천안시 서북구",
			"금산군", "당진군", "부여군", "서천군", "연기군", "예산군", "청양군", "태안군", "홍성군" ],
	"충청북도" : [ "제천시", "청주시 상당구", "청주시 흥덕구", "충주시", "괴산군", "단양군", "보은군", "영동군",
			"옥천군", "음성군", "증평군", "진천군", "청원군" ],
	"제조사" : [ "삼성전자", "LG전자", "APPLE" ]
}

function kakaoMap(map, mapContainer, mapOption, infowindow) {
	$(".mypc-mapForm").submit(function(e) {
		e.preventDefault();
		searchPlaces(map, mapContainer, mapOption, infowindow);
	});
}

function searchPlaces(map, mapContainer, mapOption, infowindow) {
	// 지도를 생성합니다
	map = new kakao.maps.Map(mapContainer, mapOption);
	var obj2 = document.form;
	var key1 = obj2.sido.value;
	var key2 = obj2.gugun.value;
	var key3 = obj2.co.value;

	var key = key1 + " " + key2 + " " + key3 + "서비스 센터";

	// 장소 검색 객체를 생성합니다
	var ps = new kakao.maps.services.Places();

	// 키워드로 장소를 검색합니다
	ps.keywordSearch(key, placesSearchCB);

	// 키워드 검색 완료 시 호출되는 콜백함수 입니다
	function placesSearchCB(data, status, pagination) {
		if (status === kakao.maps.services.Status.OK) {

			// 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
			// LatLngBounds 객체에 좌표를 추가합니다
			var bounds = new kakao.maps.LatLngBounds();

			for (var i = 0; i < data.length; i++) {
				displayMarker(data[i]);
				bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
			}
			// 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
			map.setBounds(bounds);
		}
	}

	// 지도에 마커를 표시하는 함수입니다
	function displayMarker(place) {

		// 마커를 생성하고 지도에 표시합니다
		var marker = new kakao.maps.Marker({
			map : map,
			position : new kakao.maps.LatLng(place.y, place.x)
		});

		// 마커에 클릭이벤트를 등록합니다
		kakao.maps.event.addListener(marker, 'click', function() {
			// 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
			infowindow.setContent('<div style="padding:5px;font-size:11px;">'
					+ place.place_name + '</div>');
			infowindow.open(map, marker);
		});
	}
}