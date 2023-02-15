<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<head>
<title>User Login</title>
<link rel="stylesheet"
	href="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.13/angular-material.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.7/angular.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.7/angular-animate.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.7/angular-aria.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.7/angular-messages.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.13/angular-material.min.js"></script>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,400italic">
<link rel="stylesheet"
  href="<c:url value='/css/style.css' />">
<script language="javascript">
	angular.module('loginPage', [ 'ngMaterial', 'ngMessages' ]).controller('loginController',
			inputController);

	function inputController($scope) {
		$scope.user = {
				
		};
	}
</script>
</head>
<body ng-app="loginPage" ng-controller="loginController">
  <div layout="row" layout-xs="column" layout-sm="column" layout-align="center center">
  <div flex="35">
    &nbsp;
  </div>
  <div flex>
    
	      <md-card>
        <md-card-header>
          <md-card-header-text>
            <span class="md-title">Tigers Lab User Login</span>
          </md-card-header-text>
        </md-card-header>
        <img src="images/login-background.jpg" class="md-card-image" alt="Login">

        <md-card-content>
        <form novalidate name="loginForm" ng-submit="loginForm.$valid" method="post" action="${pageContext.request.contextPath}/login">
          <c:if test="${param.error != null}">
          <div layout="row">
				<p class="error-color">Incorrect user name and password.</p>
          </div>
          </c:if>
          <div layout="row">
              <md-input-container class="md-block" flex="100">
              <label>User Name</label>
              <input name="email" ng-model="user.email" type="text" required autofocus>
              <div ng-messages="loginForm.email.$error">
                <div ng-message="required">This is required.</div>  
              </div>
              </md-input-container>
            </div>
            <div layout="row">
	          <md-input-container class="md-block" flex="100">
              <label>Password</label>
              <input name="password" ng-model="user.password" type="password" required>
              <div ng-messages="loginForm.password.$error">
                <div ng-message="required">This is required.</div>  
              </div>
              </md-input-container>
            </div>
            <div layout="row">
              <md-checkbox class="md-raised md-primary" name="remember-me" aria-label="Remember">
                Remember Me
             </md-checkbox>
            </div>
            <div layout="row">
               <md-button type="submit" class="md-raised md-primary" flex="100">Submit</md-button>
            </div>
            <div layout="row">
              New User ?  <a href="${pageContext.request.contextPath}/signup"> Sign-Up</a>
            </div>
        </form>
        </md-card-content>
      </md-card>

  </div>
  <div flex="35">
    &nbsp;
  </div>
</div>
</body>
</html>