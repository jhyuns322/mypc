<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="inc/head.jsp"%>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/myOrder.css" />
</head>
<body>
	<%@ include file="./inc/userHeader.jsp"%>

	<!-- 컨텐츠 -->
	<section class="container content">
	
		<!-- 페이지 헤더 -->
		<div class="page-header mypc-margin"><div class="mypc-icon pull-right" onclick="location.href='myPage'"><span class="glyphicon glyphicon-home"></span></div>
			<h1>주문관리</h1>
		</div>
		
		<!-- 데이트 박스 -->
		<div class="mypc-total-date-box row">
			<div class="col-md-12">
				<form id="date_form" action="myOrder">
					<input type="hidden" name="select_month" id="MM" value="${MM[0]}" />
					<input type="hidden" name="select_year" id="YY" value="${YY[0]}" />
					
					<!-- 월별 필터링 -->
					<ul class="pagination pull-left">
						<li><a href="#" class="mypc-btn" id="month4">${MM[4]}월</a></li>
						<li><a href="#" class="mypc-btn" id="month3">${MM[3]}월</a></li>
						<li><a href="#" class="mypc-btn" id="month2">${MM[2]}월</a></li>
						<li><a href="#" class="mypc-btn" id="month1">${MM[1]}월</a></li>
						<li><a href="#" class="mypc-btn" id="month0">${MM[0]}월</a></li>
					</ul>

					<!-- 전체검색 -->
					<button type="button" id="all_select" class="mypc-all-select-btn mypc-btn">전체검색</button>
					
					<!-- 세부 필터링 -->
					<fieldset class="mypc-detail-date pull-right">
						<label for="ordered_date">날짜 검색</label>
						<input type="date" class="mypc-ordered-date" name="ordered_date" id="ordered_date" value="${ordered_date}"/>
						<input type="date" class="mypc-ordered-date" name="ordered_date2" id="ordered_date2" value="${ordered_date2}"/>
						<button type="submit" id="lookup" name="lookup" class="mypc-select-btn mypc-btn pull-right">조회</button>
					</fieldset>
				</form>
			</div>
			
			<!-- 주문정보 헤드 -->
			<table class="mypc-top-table-head col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<tr>
				<th class="mypc-table-sort1 col-xs-6 col-sm-6 col-md-6 col-lg-6">상품정보</th>
				<th class="col-xs-2 col-sm-2 col-md-2 col-lg-2">구매 날짜</th>
				<th class="col-xs-2 col-sm-2 col-md-2 col-lg-2">구매금액</th>
				<th class="col-xs-2 col-sm-2 col-md-2 col-lg-2">추가사항</th>
			</tr>
		</table>
	</div>
		<c:choose>
			<c:when test="${length == 0}">
				<div>조회할 주문내역이 없습니다.</div>
			</c:when>
			<c:otherwise>
				<c:forEach var="i" begin="0" end="${length-1}" step="1">
				
					<%-- 현재 상품이 앞선 상품과 번호가 다를경우 새 박스 생성 --%>
					<c:if test="${output[length-i-1].ordered_num != output[length-i].ordered_num}">
						<c:choose>
						
							<%-- 첫 인덱스일시 무조건 생성 --%>
							<c:when test="${i==0 }">
								<div class="mypc-date-box col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
 									<div class="mypc-sell-date">${fn:substring(output[length-i-1].ordered_date,0,4)}. 
 										${fn:substring(output[length-i-1].ordered_date,5,7)}</div>
 								</div>
							</c:when>
							
							<%-- 년도를 비교하여 다를시 새 박스 생성--%>
							<c:when test="${fn:substring(output[length-i-1].ordered_date,0,4) != fn:substring(output[length-i].ordered_date,0,4)}">
								<div class="mypc-date-box col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
									<div class="mypc-sell-date">${fn:substring(output[length-i-1].ordered_date,0,4)}. 
										${fn:substring(output[length-i-1].ordered_date,5,7)}</div>
								</div>
							</c:when>
							
							<%-- 년도를 비교하여 같을시 --%>
							<c:when test="${fn:substring(output[length-i-1].ordered_date,0,4) == fn:substring(output[length-i].ordered_date,0,4)}">
								<c:choose>
									
									<%-- 월을 비교하여 다를시 새 박스 생성 --%>
									<c:when test="${fn:substring(output[length-i-1].ordered_date,5,7) != fn:substring(output[length-i].ordered_date,5,7)}">
										<div class="mypc-date-box col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
											<div class="mypc-sell-date">${fn:substring(output[length-i-1].ordered_date,0,4)}. 
												${fn:substring(output[length-i-1].ordered_date,5,7)}</div>
										</div>
									</c:when>
								</c:choose>
							</c:when>
						</c:choose>
						
						<!-- 주문정보 바디 -->
						<table class="mypc-top-table col-xs-12 col-sm-12 col-md-12 col-lg-12">
							<tr>
								<td class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
									<!-- 썸네일 -->
									<c:url value="/download" var="item_img">
										<c:param name="file" value="${output[length-i-1].item_imgthumb}" />
									</c:url>
									<div class="mypc-top-img-box">
										<img src="${item_img }"/>
									</div>
									
									<!-- 주문에 관한 첫번째 상품명 -->
									<div class="mypc-top-name-box">
										<span>${output[length-i-1].item_name}</span>  
										<c:set var="doneloop" value="false"/>
										
										<!-- 추가 상품에대한 간략한 정보 -->
										<c:if test="${output[length-i-1].ordered_num == output[length-i-2].ordered_num }">
											 <span>외
											 <c:forEach var="j" begin="0" end="${length-i-1}" step="1">
												<c:if test="${not doneloop}">
													<c:choose>
														<c:when test="${output[length-i-j-1].ordered_num != output[length-i-j-2].ordered_num }">
															<span>${j}</span>
															<c:set var="doneloop" value="true"/>
														</c:when>
													</c:choose>
												</c:if>
											</c:forEach>
											종류의 상품</span>
										</c:if>
									</div>
								</td>
								
								<!-- 주문 날짜 -->
								<td class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
									<div>${fn:substring(output[length-i-1].ordered_date,0,10)}</div>
								</td>
								<td class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
									<div>
										<span>
										
										
											<!-- 구매금액에 대한 처리 -->
											<c:set var="result" value="0"/>
												<c:set var="totalPrice" value="0"/>
												<c:set var="sale" value="0"/>
												<c:forEach var="j" begin="0" end="${length-i}" step="1" >
													<c:choose>
													
														<%-- 앞선 상품과 주문번호가 같거나 첫 인덱스일때 --%>
														<c:when test="${(j==0 || output[length-i-j-1].ordered_num == output[length-i-j].ordered_num) && result==0}">
															<c:set var="totalPrice" value="${totalPrice+output[length-i-j-1].price*output[length-i-j-1].selected_count}"/>
															
															<%-- 쿠폰이 있을때 --%>
															<c:if test="${coup_len >0 }">
																<c:forEach var="c" begin="0" end="${coup_len-1}" step="1">
																
																	<%-- 주문번호와 쿠폰이 매칭될때 --%>
																	<c:if test="${output[length-i-j-1].orderno == coup_output[c].enabled}">
																		<c:set var="sale" value="${totalPrice/100*coup_output[c].coupon_off}"/>
																	</c:if>
																</c:forEach>
															</c:if>
														</c:when>
														
														<%-- 앞선 상품과 주문번호가 다르다면 for문을 마친다. --%>
														<c:otherwise>
															<c:set var="result" value="1"/>
														</c:otherwise>
													</c:choose>
												</c:forEach>
												<!-- 구매금액 -->
											<fmt:formatNumber value="${totalPrice-sale}" type="int" pattern="#,###" />원
										</span>
									</div>
								</td>
								
								<!-- 버튼 모음 -->
								<td class="mypc-button-set col-xs-2 col-sm-2 col-md-2 col-lg-2">
								
									<!-- 상세보기 버튼 -->
									<button id="modal_btn${i}" class="modal_btn mypc-btn btn btn-primary" value="${i}">상세보기</button><br/>
									<c:set var="orderno" value="0"/>
									<c:set var="result" value="0"/>
									<c:forEach var="j" begin="0" end="${length-i-1}"  step="1">
										<c:choose>
											<%-- 첫 인데스 --%>
											<c:when test="${j==0 }">
												<c:set var="orderno" value="${output[length-i-j-1].orderno}"/>
											</c:when>
											
											<%-- 앞선 상품과 주문번호가 같다면 Orderno를 ","를 사이에 두고 합친다. --%>
											<c:when test="${output[length-i-j-1].ordered_num == output[length-i-j].ordered_num && result==0}">
												<c:set var="orderno" value="${orderno},${output[length-i-j-1].orderno}"/>
											</c:when>
											
											<%-- 앞선 상품과 주문번호가 다르다면 for문을 마친다. --%>
											<c:otherwise>
												<c:set var="result" value="1"/>
											</c:otherwise>
										</c:choose>
									</c:forEach>
									
									<!-- 주문취소 버튼 -->
									<form id="cancel_form${i}" >
										<button class="order_cancel mypc-btn btn btn-primary">주문취소</button><br/>
										<input type="hidden" class="number${i}" name="orderno" value="${orderno}"/>
									</form>
									
									<!-- 문의하기 버튼 -->
									<a href="support" class="mypc-support-btn mypc-btn btn btn-primary">문의하기</a>
								</td>
							</tr>
						</table>
						
						
						<!-- 상세보기 모달 -->
						<div id="order_modal${i}" class="mypc-order-modal col-xs-12 col-sm-12 col-ms-12 col-lg-12"></div>
						<div id="fixed_box${i}" class="mypc-fixed-box">
							<input class="modal_close" value="${i}" type="hidden"/>
							<div class="mypc-hidden-box hidden-xs col-sm-1 col-ms-1 col-lg-1"></div>
							<div id="white_box" class="mypc-white-box col-xs-12 col-sm-10 col-ms-10col-lg-10">
							
								<!-- 스크롤 박스 -->
								<div class="mypc-scroll">
								
									<!-- 주문번호, 주문일자 -->
									<div>주문번호 : ${output[length-i-j-1].ordered_num}</div>
									<div>주문일자 : ${fn:substring(output[length-i-j-1].ordered_date,0,10)}</div>
									<table class="mypc-modal-order-table col-xs-12 col-sm-12 col-ms-12 col-lg-12">
										<thead>
										
											<!-- 모달 상품 헤드 -->
											<tr class="mypc-modal-order-head">
												<th class="col-xs-6 col-sm-6 col-ms-6 col-lg-6">상품 정보</th>
												<th class="col-xs-2 col-sm-2 col-ms-2 col-lg-2">수량</th>
												<th class="col-xs-4 col-sm-4 col-ms-4 col-lg-4">구매금액</th>
											</tr>
										</thead>
										<tbody >
										
											<!-- 모달 상품 바디 -->
											<c:set var="result" value="0"/>
											<c:forEach var="j" begin="0" end="${length-i}" step="1">
												<c:choose>
												
													<%-- 앞선 상품과 주문번호가 같을시 --%>
													<c:when test="${(j == 0 || output[length-i-j-1].ordered_num == output[length-i-j].ordered_num ) && result == 0}">
														<tr class="mypc-modal-order-body">
															<td class="mypc-modal-order-info">
																<%-- 모달 썸네일 --%>
																<div class="mypc-modal-order-img">
																	<img src="${pageContext.request.contextPath}/download?file=${output[length-i-j-1].item_imgthumb}"/>
																</div>
																
																<%-- 모달 상품명, 회사명, 스펙 --%>
																<div>${output[length-i-j-1].item_name}</div>
																<div>${output[length-i-j-1].manufac}</div>
																<div>${output[length-i-j-1].spec}</div>
															</td>
															
															<%-- 모달 수량 --%>
															<td>
																<div>수량 : ${output[length-i-j-1].selected_count}</div>
															</td>
															
															<%-- 모달 각 주문금액 --%>
															<td class="mypc-modal-order-price">
																<c:set var="price" value="#{output[length-i-j-1].price*output[length-i-j-1].selected_count}"/>
																<c:set var="sale" value="0"/>
																<c:if test="${coup_len>0 }">
																	<c:forEach var="c" begin="0" end="${coup_len-1}" step="1">
																		<c:if test="${output[length-i-j-1].orderno == coup_output[c].enabled }">
																			<c:set var="sale" value="${price/100*coup_output[c].coupon_off }"/>
																		</c:if>
																	</c:forEach>
																</c:if>
																<div>
																	가격 : <span><fmt:formatNumber value="${price}" type="int" pattern="#,###" />원</span>
																</div>
																<div>
																	쿠폰 할인액 : (-)<span><fmt:formatNumber value="${sale}" type="int" pattern="#,###" />원</span>
																</div>
																<div>
																	구매 금액 : <span><fmt:formatNumber value="${price-sale}" type="int" pattern="#,###" /></span><span>원</span>
																</div>
															</td>
														</tr>
													</c:when>
													<c:otherwise>
														<c:set var="result" value="1"/>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</tbody>
									</table>
									
									<!-- 모달 수령인 -->
									<table class="mypc-modal-recipient col-xs-12 col-sm-6 col-ms-6 col-lg-6">
										<thead>
											<tr>
												<th colspan="2">수령인 정보</th>
											</tr>
										</thead>
										<tbody>
										
											<!-- 수령인 이름 -->
											<tr>
												<td class="col-xs-3 col-sm-3 col-ms-3 col-lg-3">이름</td>
												<td>${output[length-i-1].name }</td>
											</tr>
											
											<!-- 배송지 -->
											<tr>
												<td>배송지</td>
												<td>
													<c:set var="idx1" value="${fn:split(output[length-i-1].addr1,',') }" />
													<c:set var="idx2" value="${fn:split(output[length-i-1].addr2,',') }" />
													<div>${idx1[0]}</div>
													<div>${idx1[1]}</div>
													<div>${idx2[0]}</div>
													<c:if test="${idx2[1] != 'null'}">
														<div>${idx2[1]}</div>
													</c:if>
												</td>
											</tr>
											
											<!-- 연락처 -->
											<tr>
												<td>연락처</td>
												<td>${fn:substring(output[length-i-1].tel,0,3)}-${fn:substring(output[length-i-1].tel,3,7)}-${fn:substring(output[length-i-1].tel,7,11)}</td>
											</tr>
										</tbody>
									</table>
									
									<!-- 모달 결제 정보 -->
									<table class="mypc-modal-pay col-xs-12 col-sm-5 col-ms-5 col-lg-5 pull-right">
										<thead>
											<tr>
												<th colspan="2">결제 정보</th>
											</tr>
										</thead>
										<tbody>
											<c:set var="result" value="0"/>
												<c:set var="totalPrice" value="0"/>
												<c:set var="sale" value="0"/>
												<c:forEach var="j" begin="0" end="${length-i}" step="1" >
													<c:choose>
														
														<%-- 앞선 상품과 주문번호가 같을시 --%>
														<c:when test="${(j==0 || output[length-i-j-1].ordered_num == output[length-i-j].ordered_num) && result==0}">
															<c:set var="totalPrice" value="${totalPrice+output[length-i-j-1].price*output[length-i-j-1].selected_count}"/>
															
															<%-- 쿠폰 有 --%>
															<c:if test="${coup_len >0 }">
																<c:forEach var="c" begin="0" end="${coup_len-1}" step="1">
																
																	<%-- 주문번호와 일치하는 쿠폰이 있을시 할인율 적용 --%>
																	<c:if test="${output[length-i-j-1].orderno == coup_output[c].enabled}">
																		<c:set var="sale" value="${totalPrice/100*coup_output[c].coupon_off}"/>
																	</c:if>
																</c:forEach>
															</c:if>
														</c:when>
														<c:otherwise>
															<c:set var="result" value="1"/>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											<tr>
												<td class="col-xs-3 col-sm-3 col-ms-3 col-lg-3">가격</td>
												<td><fmt:formatNumber value="${totalPrice}" pattern="#,###" type="int"/>원</td>
											</tr>
											<tr>
												<td>할인금액</td>
												<td>(-)<fmt:formatNumber value="${sale}" pattern="#,###" type="int"/>원</td>
											</tr>
											<tr>
												<td>구매금액</td>
												<td><fmt:formatNumber value="${totalPrice-sale}" pattern="#,###" type="int"/>원</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</c:if>						
				</c:forEach>
			</c:otherwise>
		</c:choose>

	<div id="order_count" class="hide">${length}</div>
	</section>

	<%@ include file="./inc/footer.jsp"%>
	<%@ include file="./inc/userScript.jsp"%>
	<script src="assets/js/myOrder.js"></script>
	<script type="text/javascript">
		$(function() {
			userCommon(); // 상단 메뉴바의 기능을 구현
		});
	</script>
</body>
</html>