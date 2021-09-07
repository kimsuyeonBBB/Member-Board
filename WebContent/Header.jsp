<%@page import="spms.vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
Member member = (Member)session.getAttribute("member");
%>    

<div style="background-color:#5D5D5D;color:#ffffff;font-size:40px;height:50px;padding:5px;">
Home Page
<span style="float:right; font-size:20px; margin-top:20px">
<%=member.getName()%>
<a style="color:white;"
   href="<%=request.getContextPath()%>/auth/logout">로그아웃</a>
</span>
</div>    
