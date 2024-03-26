<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
  <%@ page import="model.Usuario"%>
  <%@ page import="model.DAO"%>
  
    
    <%   
    
   
    	session.invalidate();
    	
    %>
    
<jsp:forward page="login.jsp" />