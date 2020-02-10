<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="./inc/head.jsp"%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/aos/aos.css" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/youcover/youCover.css" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/index.css" />
</head>

<body>
	<%@ include file="./inc/userHeader.jsp"%>

	<!-- content -->
	<section class="container-fluid">
		<div class="mypc-itemlogo1">
			<img class="img-responsive" alt="" src="${pageContext.request.contextPath}/download?file=index1.png">
		</div>
		
		<!-- fadeslide -->
		<div class="row mypc-indexcontainer1">
			<div id="mypc-slide" class="mypc-imgbox1">
				<!-- fadeslide 이미지 (1200x650) -->
				<img class="img-responsive" alt="" src="${pageContext.request.contextPath}/download?file=index2.png">
				<img class="img-responsive" alt="" src="${pageContext.request.contextPath}/download?file=index3.png">
				<img class="img-responsive" alt="" src="${pageContext.request.contextPath}/download?file=index4.png">
			</div>
		</div>
		<div class="row mypc-indexcontainer2">
			<div class="col-md-10 col-md-offset-1">
				<div class="col-md-4 mypc-youtube-wrapper">
					<div data-youcover data-id='bAketc8BCzw'></div>
					<div class="mypc-videoinfo1">
						<font color=><strong>P</strong></font>recision Boost 2 &amp; <br> Precision Boost Overdriv<font color=><strong>e</strong></font> 소개
					</div>
					<div class="mypc-videoinfo2">Precision Boost 2 accelerates performance for intense workloads, automatically boosting CPU clockspeeds on select AMD Ryzen 3000 Series processors.</div>
				</div>
				<div class="col-md-4 mypc-youtube-wrapper">
					<div data-youcover data-id='NZP4_gaZACc'></div>
					<div class="mypc-videoinfo1">
						<font color=><strong>R</strong></font>TX. IT’S ON. <br> Official GeForce RT<font color=><strong>X</strong></font> 트레일러 </div>
					<div class="mypc-videoinfo2">Ray tracing is here with GeForce RTX. Experience today’s biggest blockbusters like never before with the visual fidelity of real-time ray.</div>
				</div>
				<div class="col-md-4 mypc-youtube-wrapper">
					<div data-youcover data-id='zhQSrR0Mdwo'></div>
					<div class="mypc-videoinfo1">
						<font color=><strong>C</strong></font>OMPUTEX 2019 <br> Decades of Innovatio<font color=><strong>n</strong></font> | Intel </div>
					<div class="mypc-videoinfo2">Intel, the world leader in silicon innovation, develops technologies, products and initiatives to continually advance how people work and live.</div>
				</div>
			</div>
		</div>

		<!-- aos -->
		<div class="row mypc-indexcontainer3">
			<div class="mypc-imgbox3" style="position: relative;">
				<div class="a">
					<img class="img-responsive" src="${pageContext.request.contextPath}/download?file=index5.png">
				</div>
				<div class="b" data-aos="fade-zoom-in" data-aos-offset="500" data-aos-easing="ease-in-sine" data-aos-duration="700">
					<img class="img-responsive" src="${pageContext.request.contextPath}/download?file=index6.png">
				</div>
			</div>
		</div>
	</section>

	<%@ include file="./inc/footer.jsp"%>
	<%@ include file="./inc/userScript.jsp"%>
	<script src="${pageContext.request.contextPath}/assets/plugins/aos/aos.js"></script>
	<script src="${pageContext.request.contextPath}/assets/plugins/youcover/youCover.js"></script>
	<script src="assets/js/index.js"></script>
	<script type="text/javascript">
		$(function() {
			userCommon(); // 상단 메뉴바의 기능을 구현
			AOS.init();   // 하단 이미지 slide
		});
	</script>
</body>
</html>