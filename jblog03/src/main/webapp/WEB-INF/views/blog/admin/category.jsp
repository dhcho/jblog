<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath }/assets/js/ejs/ejs.js" type="text/javascript"></script>
<script>	
	var listEJS = new EJS({
		url: "${pageContext.request.contextPath }/assets/js/ejs/list-template.ejs"
	});
	
	var listItemEJS = new EJS({
		url: "${pageContext.request.contextPath }/assets/js/ejs/listitem-template.ejs"
	});
	
	var fetch = function() {
		const id = "${authUser.id}";
		$.ajax({
			url: "${pageContext.request.contextPath }/${authUser.id }/admin/category/api/list/" + id,
			dataType: "json",
			type: "get",
			success: function(response) {
				/* response.data.forEach(function(vo) {
					html = "<li data-no='" + vo.no + "'>" + 
					"<strong>" + vo.name + "</strong>" +
					"<p>" + vo.message + "</p>" +
					"<strong></strong>" + 
					"<a href='' data-no='" + vo.no + "'>삭제</a>" + 
					"</li>";
					
					$("#list-guestbook").append(html);
				}); */
				
				response.data.forEach(function(vo, index) {
					console.log(vo.no);
					const del = (vo.count != 0 ? ("<td></td>") : ("<td><a href='${pageContext.request.contextPath }/${authUser.id }/admin/category/delete/'" + vo.no + ">" +
							"<img src='${pageContext.request.contextPath}/assets/images/delete.jpg'>" +
							"</a></td>"));
					html = "<tr>" +
								"<td>" + eval(index + 1) + "</td>" +
								"<td>" + vo.name + "</td>" +
								"<td>" + vo.count + "</td>" +
								"<td>" + vo.desc + "</td>" +
								del + 
							"</tr>";
							
					$("#admin-cat").append(html);
				});

				// var html = listEJS.render(response);
				// $("#admin-cat").append(html);
			}
		});
	}
	
	// alert dialog
	const valid = function(msg) {
		$("#dialog-message").dialog({
			title: msg,
			modal: true,
			buttons: {
				"확인": function() {
					$(this).dialog("close");
				}
			}
		});
	}
	
	$(function() {		
		// 최초 데이터 가져오기
		fetch();
		
		$("#admin-cat-add").submit(function(event) {
			event.preventDefault();
			
			vo = {};
			
			vo.name = $("#input-name").val();
			vo.desc = $("#input-desc").val();
			
			// validation
			if(vo.name == "" || vo.desc == "") {
				if(vo.name == "") {
					$("#dialog-message").text("카테고리명이 비어있습니다.");
				} else if(vo.password == "") {
					$("#dialog-message").text("설명이 비어있습니다.");
				}
				valid("알림");
				return;
			}
			
			// 데이터 등록
			$.ajax({
				url: "${pageContext.request.contextPath }/${authUser.id }/admin/category/api/add",
				dataType: "json",
				type: "post",
				contentType: "application/json",
				data: JSON.stringify(vo),
				success: function(response) {
					var vo = response.data;
					const del = (vo.count != 0 ? ("<td></td>") : ("<td><a href='${pageContext.request.contextPath }/${authUser.id }/admin/category/delete/'" + vo.no + ">" +
							"<img src='${pageContext.request.contextPath}/assets/images/delete.jpg'>" +
							"</a></td>"));
					
					html =  "<tr>" +
								"<td>" + vo.no + "</td>" +
								"<td>" + vo.name + "</td>" +
								"<td>" + vo.count + "</td>" +
								"<td>" + vo.desc + "</td>" +
								del + 
							"</tr>";
					// var html = listItemEJS.render(response.data);
							
					$("#admin-cat").append(html);
				}
			});
			
			// 데이터 등록
			$.ajax({
				url: "${pageContext.request.contextPath }/${authUser.id }/admin/category/delete/"+no,
				dataType: "json",
				type: "post",
				contentType: "application/json",
				data: JSON.stringify(vo),
				success: function(response) {
					$("#admin-cat tr[no=" + response.data + "]").remove();
				}
			});
		});
	});
</script>
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/blog/includes/header.jsp" />
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li><a href="${pageContext.request.contextPath }/${authUser.id }/admin/basic">기본설정</a></li>
					<li class="selected">카테고리</li>
					<li><a href="${pageContext.request.contextPath }/${authUser.id }/admin/write">글작성</a></li>
				</ul>
		      	<table class="admin-cat" id="admin-cat">
		      		<tr>
		      			<th>번호</th>
		      			<th>카테고리명</th>
		      			<th>포스트 수</th>
		      			<th>설명</th>
		      			<th>삭제</th>      			
		      		</tr>
				</table>
      			<h4 class="n-c">새로운 카테고리 추가</h4>
      			<form action="" method="post">
		      	<table id="admin-cat-add">
		      		<tr>
		      			<td class="t">카테고리명</td>
		      			<td><input type="text" id="input-name" name="name"></td>
		      		</tr>
		      		<tr>
		      			<td class="t">설명</td>
		      			<td><input type="text" id="input-desc" name="desc"></td>
		      		</tr>
		      		<tr>
		      			<td class="s">&nbsp;</td>
		      			<td><input type="submit" value="카테고리 추가"></td>
		      		</tr>      		      		
		      	</table> 
		      	</form>
			</div>
			<div id="dialog-message" title="" style="display:none">
  				<p></p>
			</div>	
		</div>
		<jsp:include page="/WEB-INF/views/blog/includes/footer.jsp" />
	</div>
</body>
</html>