<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean id="member"
	scope="request"    
	class="spms.vo.Member"/>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원 정보</title>
</head>
<body>
<jsp:include page="/Header.jsp"/>

<div style="width:200px; height:500px;border-right:2px solid black; float:left;">
<a href="list.do" style="font-size:20px; line-height:50px; margin-left:5px;"><i>회원관리</i></a><br>
<a href="../board/list.do" style="font-size:20px; line-height:50px; margin-left:5px;"><i>게시판 관리</i></a><br>
</div>

<div style="width:700px; height:500px; float:left; margin-left:10px">
<h1>회원정보</h1>
<form action='update.do' method='post'>
번호 : <input type='text' name='no' value='<%=member.getNo()%>' style='width:200px;' readonly><br>
이름 : <input type='text' name='name' value='<%=member.getName()%>' style='margin-top:10px; width:200px;'><br>
이메일 : <input type='text' name='email' value='<%=member.getEmail()%>' style='margin-top:10px; width:180px;'><br>
ID : <input type='text' name='id' value='<%=member.getId()%>' style='margin-top:10px;width:210px;'><br>
Password : <input type='text' name='password' value='<%=member.getPassword()%>' style='margin-top:10px; width:160px;'><br>
가입일 : <%=member.getCreatedDate()%><br>
<input type='submit' value='저장' style='margin-top:10px'>
<input type='button' value='삭제' onclick='removeCheck()' style='margin-top:10px'>
<input type='button' value='취소' onclick='location.href="list.do"'>
</form>
</div>
<jsp:include page="/Tail.jsp"/>
</body>
</html>

<script type="text/javascript">
function removeCheck(){
	if(confirm("삭제하시겠습니까?") == true){ //'확인' 클릭 시
		location.href='delete.do?no=<%=member.getNo()%>';
	} else{  //'취소' 클릭 시
		return false;
	}
}
</script>