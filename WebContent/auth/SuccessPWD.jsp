<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <jsp:useBean id="member"
	scope="request"    
	class="spms.vo.Member"/>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Refresh" content="5;url=login">
<title>Password 찾기 성공!</title>
</head>
<body>

<div style="background-color:#5D5D5D;color:#ffffff;font-size:40px;height:50px;padding:5px;">
Home Page
</div>

Password 찾기 성공입니다!<br>
<%=member.getName()%>님의 Password는 <%=member.getPassword()%>입니다.<br>
잠시후에 로그인 화면으로 다시 돌아갑니다.

<div style="background-color:#5D5D5D;height:50px;padding:5px;margin-top:200px">

</div>

</body>
</html>