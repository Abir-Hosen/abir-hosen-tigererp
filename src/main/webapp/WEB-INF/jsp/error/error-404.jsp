<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
  <head>
   <title>Error 404</title>
  </head>
  <body>
	<h1>Something went wrong! </h1>
	<h2>The resources you looking for is not in our server</h2>
	<h2>Error Code 404</h2>
	<ul>
	  <li>May be URL is wrong.</li>
	  <li>May be the resource has been deleted.</li>
	  <li>May be you are trying old book marks URL.</li>
	</ul>
	<a href="${pageContext.request.contextPath }/">Go Home</a>
  </body>
</html>