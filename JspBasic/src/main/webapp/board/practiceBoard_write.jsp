<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>게시물 등록 페이지</h2>
	<form action="">
		<input type = "text" name = "writer" placeholder="작성자"> <br>
		<input type = "text" name = "title" placeholder="글제목"> <br>
		<textarea rows="5" cols="30" placeholder="게시글 내용을 입력하세요."></textarea>
		<button type = "submit" onclick = "return confirm('등록하시겠습니까?')">등록</button>
	</form>
</body>
</html>