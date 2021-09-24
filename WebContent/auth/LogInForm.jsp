<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그인</title>
</head>
<body>

<div style="background-color:#5D5D5D;color:#ffffff;font-size:40px;height:50px;padding:5px;">
Home Page
</div>

<h2>사용자 로그인</h2>
<form action="login.do" method="post">
ID : <input type="text" name="id" style="width:200px;"><br>
Password : <input type="password" name="password" style="width:150px;">
<input type="submit" value="로그인" style="width:50px; height:50px;">
</form>
<p><a href='../member/add.do'>회원 가입</a><a> / </a><a href='findid.do'>ID 찾기</a><a> / </a><a href='findpwd.do'>Password 찾기</a></p>

<div style="background-color:#5D5D5D;height:50px;padding:5px;margin-top:200px">

</div>

</body>
</html>