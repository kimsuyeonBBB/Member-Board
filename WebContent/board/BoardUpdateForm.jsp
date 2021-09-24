<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<jsp:useBean id="board"
	scope="request"
	class="spms.vo.Board"/>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시글 수정</title>
</head>
<body>
<jsp:include page="/Header.jsp"/>

<div style="width:200px; height:500px;border-right:2px solid black; float:left;">
<a href="../member/list.do" style="font-size:20px; line-height:50px; margin-left:5px;"><i>회원관리</i></a><br>
<a href="list.do" style="font-size:20px; line-height:50px; margin-left:5px;"><i>게시판 관리</i></a><br>
</div>

<div style="width:700px; height:500px; float:left; margin-left:10px">
<h1>게시글 수정</h1>
<form action='update.do' method='post'>
번호 : <input type='text' name='no' value='<%=board.getNo()%>' readonly><br><br>
<div style='float:left; font-size:15px;'> 제목 : &nbsp;</div> <div><textarea type='text' name='title' style='width:500px;height:30px; float:left;'><%=board.getTitle()%></textarea></div><br><br>
<div style='float:left; font-size:15px;'> 내용 : &nbsp;</div> <div><textarea type='text' name='story' style='margin-top:10px; width:500px; height:200px;'><%=board.getStory()%></textarea></div><br>
<input type='submit' value='완료' style='margin-top:10px;'>
<input type='button' value='삭제' onclick='removeCheck()'>
<input type='button' value='취소' onclick='location.href="list.do"'>
</form>
</div>
<jsp:include page="/Tail.jsp"/>

</body>
</html>

<script type="text/javascript">
function removeCheck(){
	if(confirm("삭제하시겠습니까?") == true){ //'확인' 클릭 시
		location.href='delete.do?no=<%=board.getNo()%>';
	} else{  //'취소' 클릭 시
		return false;
	}
}
</script>