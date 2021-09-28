<%@ page import="spms.vo.Member" %>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원 목록</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">
    function page(idx){
        var pagenum = idx;            
        location.href="${pageContext.request.contextPath}/member/list.do?pagenum="+pagenum;
    }
</script>

</head>
<body>

<jsp:include page="/Header.jsp"/>

<div style="width:200px; height:500px;border-right:2px solid black; float:left;">
<a href="list.do" style="font-size:20px; line-height:50px; margin-left:5px;"><i>회원관리</i></a><br>
<a href="../board/list.do" style="font-size:20px; line-height:50px; margin-left:5px;"><i>게시판 관리</i></a><br>
</div>

<div style="width:1800px; height:500px; float:left; margin-left:15px">
<h1>회원목록</h1>

<hr style="width:1800px; border:3px solid black;"></hr>
<div style='font-size:20px; width:1800px; margin-left:10px;'>
<b>&emsp;
	<c:choose>
	<c:when test="${orderCond == 'MNO_ASC'}">
		<a href="list.do?orderCond=MNO_DESC">번호 ↑</a>
	</c:when>
	<c:when test="${orderCond == 'MNO_DESC'}">
		<a href="list.do?orderCond=MNO_ASC">번호↓</a>
	</c:when>
	<c:otherwise>
		<a href="list.do?orderCond=MNO_ASC">번호</a>
	</c:otherwise>
	</c:choose></b>  
<b>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; 
	<c:choose>
	<c:when test="${orderCond == 'MNAME_ASC'}">
		<a href="list.do?orderCond=MNAME_DESC">이름↑</a>
	</c:when>
	<c:when test="${orderCond == 'MNAME_DESC'}">
		<a href="list.do?orderCond=MNAME_ASC">이름↓</a>
	</c:when>
	<c:otherwise>
		<a href="list.do?orderCond=MNAME_ASC">이름</a>
	</c:otherwise>
	</c:choose> </b> 
<b>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; 
	<c:choose>
	<c:when test="${orderCond == 'EMAIL_ASC'}">
		<a href="list.do?orderCond=EMAIL_DESC">이메일↑</a>
	</c:when>
	<c:when test="${orderCond == 'EMAIL_DESC'}">
		<a href="list.do?orderCond=EMAIL_ASC">이메일↓</a>
	</c:when>
	<c:otherwise>
		<a href="list.do?orderCond=EMAIL_ASC">이메일</a>
	</c:otherwise>
	</c:choose></b>  
<b>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; 
	<c:choose>
	<c:when test="${orderCond == 'CREATEDATE_ASC'}">
		<a href="list.do?orderCond=CREATEDATE_DESC">생성일↑</a>
	</c:when>
	<c:when test="${orderCond == 'CREATEDATE_DESC'}">
		<a href="list.do?orderCond=CREATEDATE_ASC">생성일↓</a>
	</c:when>
	<c:otherwise>
		<a href="list.do?orderCond=CREATEDATE_ASC">생성일</a>
	</c:otherwise>
	</c:choose></b> 
<b>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</b>
</div>
<hr style="width:1800px; border:2px solid black;"></hr>


<%
ArrayList<Member> members = (ArrayList<Member>)request.
getAttribute("members");
for(Member member : members){
%>
<div style="margin-left:35px; width:30px; float:left; text-align:center;"><%=member.getNo()%></div> 
<div style="margin-left:70px; width:180px; float:left; text-align:center;"><a href='update.do?no=<%=member.getNo()%>'><%=member.getName()%></a></div>
<div style="margin-left:20px; width:180px; float:left; text-align:center;"><%=member.getEmail()%></div> 
<div style="margin-left:40px; width:180px; float:left; text-align:center;"><%=member.getCreatedDate()%></div> 
<a href="#" onclick="removeCheck(<%=member.getNo()%>)">[삭제]</a><br><br>
<%}%>
<script type="text/javascript">
function removeCheck(no){
	if(confirm("삭제하시겠습니까?") == true){ //'확인' 클릭 시
		location.href='delete.do?no=' + no;
	} else{  //'취소' 클릭 시
		return false;
	}
}
</script>

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


