$(".mypc-menu li").click(function() {
	var chartno = $(this).data("mypc");
	loadChart("adminStatsGet/" + chartno);
});

$(".mypc-menu li").hover(function() {
	$(this).css('cursor', 'pointer');
});

// datepicker Plug-In
function datePicker() {
	$("#sel_date1").datepicker({ // datepicker Plug-In
		autoHide : true, // 날짜 선택 후 자동 숨김
		format : 'yyyy-mm-dd', // 날짜 형식
		language : 'ko-KR', // 언어
		weekStart : 0, // 시작요일(0=일요일 ~ 6=토요일)
		trigger : '#mypc-showCal1' // 클릭시 달력을 표시할 요소의 id
	}).on('change', function() {

	});

	$("#sel_date2").datepicker({ // datepicker Plug-In
		autoHide : true, // 날짜 선택 후 자동 숨김
		format : 'yyyy-mm-dd', // 날짜 형식
		language : 'ko-KR', // 언어
		weekStart : 0, // 시작요일(0=일요일 ~ 6=토요일)
		trigger : '#mypc-showCal2' // 클릭시 달력을 표시할 요소의 id
	}).on('change', function() {

	});
}

$("#mypc-formChart").submit(function() {
	event.preventDefault();

	loadChart("adminStatsGet/3");
});

function loadChart(URL) {
	var startDate = $("#sel_date1").val();
	var endDate = $("#sel_date2").val();
	$.ajax({
		url : URL + "?stDate=" + startDate + "&edDate=" + endDate,
		method : "GET",
		success : function(json) {
			if (json.rt == "OK") {
				switch (json.chartno) {
				case 1:
					$(".mypc-chart1, .mypc-chart2").removeClass("common-hide");
					$(".mypc-chart3").addClass("common-hide");
					Chart1(json);
					break;
				case 2:
					$(".mypc-chart1, .mypc-chart2").removeClass("common-hide");
					$(".mypc-chart3").addClass("common-hide");
					Chart2(json);
					break;
				case 3:
					$(".mypc-chart1, .mypc-chart2").addClass("common-hide");
					$(".mypc-chart3").removeClass("common-hide");
					Chart3(json);
					break;
				}
			}
		}
	});
}

// billboard 플러그인 사용, 카테고리별 판매량
function Chart1(json) {
	// ex)
	// [ [ item[0].category, item[0].price ],
	// [ item[1].category, item[1].price ],
	// [ item[2].category, item[2].price ],
	// [ item[3].category, item[3].price ]]

	// 위와 같은 형식으로 data 배열에 데이터 저장하는 단계
	var item = json.item;

	/* Chart1의 pieChart 데이터 */
	var chart1Data1 = [];
	// 카테고리 - 판매량
	for (var i = 0; i < item.length; i++) {
		chart1Data1.push([ item[i].category, item[i].price ]);
	}

	/* Chart1의 barChart 데이터 */
	var chart1Data2 = [];
	var chart1Data2Sub1 = [ '카테고리' ];
	var chart1Data2Sub2 = [ '판매금액' ];
	// 카테고리 - 판매금액
	for (var i = 0; i < item.length; i++) {
		chart1Data2Sub1.push(item[i].category); // ex) [ '카테고리',
		// item[0].category,
		// item[1].category,
		// item[2].category ]
		chart1Data2Sub2.push(item[i].price); // ex) [ '판매금액', item[0].price,
		// item[1].price, item[2].price
		// ]
	}
	chart1Data2.push(chart1Data2Sub1);
	chart1Data2.push(chart1Data2Sub2);

	/* Chart1의 pieChart 그리기 */
	var chart = bb.generate({
		data : {
			columns : chart1Data1,
			type : "pie"
		},
		pie : {
			innerRadius : 30
		},
		bindto : "#mypc-chart1"
	});

	/* Chart1의 barChart 그리기 */
	var chart = bb.generate({
		data : {
			columns : chart1Data2,
			x : '카테고리',
			types : {
				"판매금액" : "bar"
			},
			labels : {
				colors : "red"
			},
			color : function(color, d) {
				return [ "rgb(31, 119, 180)", "rgb(255, 127, 14)",
						"rgb(44, 160, 44)", "rgb(214, 39, 40)",
						"rgb(148, 103, 189)", "rgb(140, 86, 75)" ][d.index];
			}
		},
		axis : {
			x : {
				type : "category"
			}
		},
		bindto : "#mypc-chart2"
	});
}

// billboard 플러그인 사용, 카테고리별 상품 등록 수
function Chart2(json) {
	// ex)
	// [ [ item[0].category, item[0].stock ],
	// [ item[1].category, item[1].stock ],
	// [ item[2].category, item[2].stock ],
	// [ item[3].category, item[3].stock ]]

	// 위와 같은 형식으로 data 배열에 데이터 저장하는 단계
	var item = json.item;

	/* Chart2의 pieChart 데이터 */
	var chart2Data1 = [];
	// 카테고리별 상품 비율
	for (var i = 0; i < item.length; i++) {
		chart2Data1.push([ item[i].category, item[i].stock ]);
	}

	/* Chart2의 barChart 데이터 */
	var chart2Data2 = [];
	var chart2Data2Sub1 = [ '카테고리' ];
	var chart2Data2Sub2 = [ '카테고리별 등록 수량' ];
	// 카테고리별 상품 수량
	for (var i = 0; i < item.length; i++) {
		chart2Data2Sub1.push(item[i].category); // ex) [ '카테고리',
		// item[0].category,
		// item[1].category,
		// item[2].category ]
		chart2Data2Sub2.push(item[i].stock); // ex) [ '카테고리별 등록 수량',
		// item[0].stock, item[1].stock,
		// item[2].stock ]
	}
	chart2Data2.push(chart2Data2Sub1);
	chart2Data2.push(chart2Data2Sub2);

	var chart = bb.generate({
		data : {
			columns : chart2Data1,
			type : "pie"
		},
		pie : {
			innerRadius : 30
		},
		bindto : "#mypc-chart1"
	});

	var chart = bb.generate({
		data : {
			columns : chart2Data2,
			x : '카테고리',
			types : {
				"카테고리별 등록 수량" : "bar"
			},
			labels : {
				colors : "red"
			},
			color : function(color, d) {
				return [ "rgb(31, 119, 180)", "rgb(255, 127, 14)",
						"rgb(44, 160, 44)", "rgb(214, 39, 40)",
						"rgb(148, 103, 189)", "rgb(140, 86, 75)" ][d.index];
			}
		},
		axis : {
			x : {
				type : "category"
			}
		},
		bindto : "#mypc-chart2"
	});
}

function Chart3(json) {
	am4core.ready(function() {
		console.log(json.item);
		am4core.useTheme(am4themes_animated);
		// 차트 객체 생성
		var chart = am4core.create("mypc-chartBox", am4charts.XYChart);

		// 데이터 추가
		chart.data = json.item;

		// 날짜 형식 지정
		chart.dateFormatter.inputDateFormat = "yyyy-MM-dd";

		// x,y 축 지정
		var dateAxis = chart.xAxes.push(new am4charts.DateAxis());
		var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());

		// 데이터 맵핑
		var series = chart.series.push(new am4charts.LineSeries());
		series.dataFields.valueY = "price";
		series.dataFields.dateX = "ordered_date";
		series.tooltipText = "{price}"
		series.strokeWidth = 2;
		series.minBulletDistance = 15;

		// tooltip 모양 지정
		series.tooltip.background.cornerRadius = 20;
		series.tooltip.background.strokeOpacity = 0;
		series.tooltip.pointerOrientation = "vertical";
		series.tooltip.label.minWidth = 40;
		series.tooltip.label.minHeight = 40;
		series.tooltip.label.textAlign = "middle";
		series.tooltip.label.textValign = "middle";

		// hover시 bullets 생성
		var bullet = series.bullets.push(new am4charts.CircleBullet());
		bullet.circle.strokeWidth = 2;
		bullet.circle.radius = 4;
		bullet.circle.fill = am4core.color("#fff");
		var bullethover = bullet.states.create("hover");
		bullethover.properties.scale = 1.3;

		// 커서 생성
		chart.cursor = new am4charts.XYCursor();
		chart.cursor.behavior = "panXY";
		chart.cursor.xAxis = dateAxis;
		chart.cursor.snapToSeries = series;

		// 가로 스크롤바 생성
		chart.scrollbarY = new am4core.Scrollbar();
		chart.scrollbarY.parent = chart.leftAxesContainer;
		chart.scrollbarY.toBack();

		// 세로 스크롤바 생성
		chart.scrollbarX = new am4charts.XYChartScrollbar();
		chart.scrollbarX.series.push(series);
		chart.scrollbarX.parent = chart.bottomAxesContainer;
		dateAxis.start = 0.79;
		dateAxis.keepSelection = true;

	});
}
