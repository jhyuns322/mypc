<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<script src="//code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/animate/jquery.animatecss.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/userCommon.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/ajax/ajax_helper.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/validate/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/validate/additional-methods.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/handlebars/handlebars-v4.3.1.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/jqueryRedirect/jquery.redirect.js"></script>
<!-- template -->
	<!-- 상세 페이지에서 등록된 쿠키 값의 상품정보를 이용하여 사이드바 항목 구현-->
	<script id="mypc-cookie-list" type="text/x-handlebars-template">
		{{#each cookie_item}}
			<li>
				<a data-itemno="{{itemno}}" href="#" class="mypc-sideBar-img"><span data-remove="{{itemno}}" class="glyphicon glyphicon-remove mypc-cookieRemove" aria-hidden="true"></span><img src="${pageContext.request.contextPath}/download?file={{item_imgthumb}}" /></a>
				<a class="mypc-sideBar-name">{{item_name}}</a>
			<li>
		{{/each}}
	</script>
	
<!-- 쿠키 값들을 var 객체에 저장 -->
<script type="text/javascript">
	var contextPath = "${pageContext.request.contextPath}";
	var cookieValues = [];	
	<c:forEach items="${cookieValues}" var="item"> 
		cookieValues.push("${item}"); 
	</c:forEach>
	var path2 = "${pageContext.request.contextPath}";
	function ajaxRedirect(itemno) {
		$.ajax({
	        url : "itemListGet/"+itemno,
	        method : "GET",
	        success : function(json) {
	        	if (json.rt == "OK") {
					var json = JSON.stringify(json.item);
	        		$.redirect('itemView', {json} );
	        	}	
	        }
		});
	}
</script>
