<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html>
<html xmlns:layout="http://www.w3.org/1999/xhtml"  xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="ISO-8859-1">
<title>User Registration</title>
<link rel="stylesheet"
	href="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.8/angular-material.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.0/angular.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.0/angular-animate.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.0/angular-aria.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.0/angular-messages.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.8/angular-material.min.js"></script>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,400italic">

<script language="javascript">
	angular.module('signupPage', [ 'ngMaterial', 'ngMessages' ]).controller('signupController',
			inputController);

	function inputController($scope) {
		$scope.user = {
				
		};
		$scope.createUser = function(){
			alert('dfd');
		}
	}
</script>
</head>
<body ng-app="signupPage" ng-controller="signupController">


<div layout="row" layout-xs="column" layout-sm="column" layout-align="center center">
  <div flex="25">
    &nbsp;
  </div>
  <div flex>
    
	      <md-card>
        <md-card-header>
          <md-card-header-text>
            <span class="md-title">Tigers Lab</span>
          </md-card-header-text>
        </md-card-header>
        <img ng-src="{{imagePath}}" class="md-card-image" alt="">
        <md-card-title>
          <md-card-title-text>
            <span class="md-headline">Sign Up</span>
          </md-card-title-text>
        </md-card-title>
        <md-card-content>
        <form novalidate name="signUpForm" ng-submit="signUpForm.$valid" method="post" action="${pageContext.request.contextPath}/signup">
          <div layout="row">
            <div th:if="${param.error}">
      		  <div class="alert alert-danger">Invalid username or password.</div>
     		</div>
          </div>
          <div layout="row">
              <md-input-container class="md-block" flex="100">
              <label>First Name</label>
              <input name="firstName" ng-model="user.firstName" type="text" required autofocus>
              <div ng-messages="signUpForm.firstName.$error">
                <div ng-message="required">This is required.</div>  
              </div>
              </md-input-container>
              <md-input-container class="md-block" flex="100">
              <label>Last Name</label>
              <input name="lastName" ng-model="user.lastName" type="text" required>
              <div ng-messages="signUpForm.lasttName.$error">
                <div ng-message="required">This is required.</div>  
              </div>
              </md-input-container>
            </div>
            <div layout="row">
               <md-input-container class="md-block" flex="100">
              <label>Email</label>
              <input name="email" ng-model="user.email" type="email" required>
              <div ng-messages="signUpForm.email.$error">
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
	          <md-input-container class="md-block" flex="100">
              <label>Confirm Password</label>
              <input name="passwordConfirm" ng-model="user.passwordConfirm" type="password" required>
              <div ng-messages="loginForm.passwordConfirm.$error">
                <div ng-message="required">This is required.</div>  
              </div>
              </md-input-container>

            </div>
            <div layout="row">
               <md-button type="submit" class="md-raised md-primary" flex="100">SIGN UP</md-button>
            </div>
        </form>
        </md-card-content>
      </md-card>

  </div>
  <div flex="25">
    &nbsp;
  </div>
</div>
</body>
</html>