<%@ page import="spms.vo.Board" %>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시글 목록</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">
    function page(idx){
        var pagenum = idx;            
        location.href="${pageContext.request.contextPath}/board/list?pagenum="+pagenum;
    }
</script>

</head>
<body>

<jsp:include page="/Header.jsp"/>

<div style="width:200px; height:500px;border-right:2px solid black; float:left;">
<a href="../member/list" style="font-size:20px; line-height:50px; margin-left:5px;"><i>회원관리</i></a><br>
<a href="list" style="font-size:20px; line-height:50px; margin-left:5px;"><i>게시판 관리</i></a><br>
</div>

<div style="width:1800px; height:500px; float:left; margin-left:15px">
<span><h1>게시글 목록</h1></span>  
<input type="button" value='게시글 작성' onclick='location.href="add"' style='width:90px; height:30px;'> 


<hr style="width:1800px; border:3px solid black;"></hr>
<div style='font-size:20px; width:1800px; margin-left:10px;'>
<b>&emsp;번호  &emsp;&emsp;&emsp;&emsp;&emsp;&emsp; 제목 &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; 등록일 &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;  작성자</b>
</div>
<hr style="width:1800px; border:2px solid black;"></hr>

<%
ArrayList<Board> boards = (ArrayList<Board>)request.
getAttribute("boards");
for(Board board : boards){
%>
<div style="margin-left:35px; width:30px; float:left; text-align:center;"><%=board.getNo()%></div>
<div style="margin-left:70px; width:180px; float:left; text-align:center;"><a href='update?no=<%=board.getNo()%>'><%=board.getTitle()%></a></div>
<div style="margin-left:20px; width:180px; float:left; text-align:center;"><%=board.getCreatedDate()%></div>
<div style="margin-left:35px; width:180px; float:left; text-align:center;"><%=board.getName()%></div><br><br>
<%}%>

<table>
    <tfoot>
         <tr>
             <td colspan="2">
                 <!-- 왼쪽 화살표 -->
                 <c:if test="${page.isPrev()}">
                     <a style="text-decoration:none;" href="javascript:page(${page.getStartPage()});">&laquo;</a>
                 </c:if>
                    
                 <!-- 페이지 숫자 표시 -->
                 
                 <c:forEach var = "idx" begin= "${page.getStartPage()}" end = "${page.getEndPage()}">
					<a href="javascript:page(${idx});" >${idx}</a>
				 </c:forEach>
				 
                 <!-- 오른쪽 화살표 -->
                 <c:if test="${page.isNext()}">
                     <a style="text-decoration:none;" href="javascript:page(${page.getEndPage()});">&raquo;</a>
                 </c:if>
                    
             </td>
         </tr>
    </tfoot>
</table>

</div>



<jsp:include page="/Tail.jsp"/>

</body>
</html>