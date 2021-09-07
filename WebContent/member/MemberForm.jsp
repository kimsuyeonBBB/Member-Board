<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원 가입</title>
</head>
<body>

<div style="background-color:#5D5D5D;color:#ffffff;font-size:40px;height:50px;padding:5px;">
Home Page
</div>

<h1>회원가입</h1>
<form action='add' method='post'>
이름 : <input type='text' name='name' style='width:200px;'><br>
이메일 : <input type='text' name='email' style='margin-top:10px;width:180px;'><br>
ID : <input type='text' name='id' style='margin-top:10px;width:210px;'><br>
Password : <input type='password' name='password' style='margin-top:10px; width:160px;'><br>
<input type='submit' value='완료' style='margin-top:10px'>
<input type='button' value='취소' onclick='location.href="../auth/login"'>
</form>

<div style="background-color:#5D5D5D;height:50px;padding:5px;margin-top:200px">

</div>

</body>
</html>