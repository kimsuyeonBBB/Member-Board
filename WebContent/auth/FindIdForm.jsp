<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ID 찾기</title>
</head>
<body>


<div style="background-color:#5D5D5D;color:#ffffff;font-size:40px;height:50px;padding:5px;">
Home Page
</div>

<h1>ID 찾기</h1>
<form action='findid' method='post'>
이름 : <input type='text' name='name' style='width:200px;'><br>
이메일 : <input type='text' name='email' style='margin-top:10px;width:180px;'><br>
<input type='submit' value='확인' style='margin-top:10px;'>
<input type='button' value='취소' onclick='location.href="login"'>
</form>

<div style="background-color:#5D5D5D;height:50px;padding:5px;margin-top:200px">

</div>


</body>
</html>