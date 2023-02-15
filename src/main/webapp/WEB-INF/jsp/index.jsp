<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Tiger ERP Home</title>
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
  src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.0/angular-resource.min.js"></script>
<script
  src="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.8/angular-material.min.js"></script>
<script src="//unpkg.com/@uirouter/angularjs@1.0.7/release/angular-ui-router.min.js"></script>
<script src="<c:url value='/js/md-data-table.min.js' />"></script>
<script src="<c:url value='/js/fixed-table-header.js' />"></script>
<script src="<c:url value='/js/angular-translate.min.js' />"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.2/moment.min.js"></script>
<link rel="stylesheet"
  href="<c:url value='/css/md-data-table.min.css' />">
<link rel="stylesheet"
  href="<c:url value='/css/style.css' />">
<link rel="stylesheet"
  href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,400italic">
	
<script language="javascript">
	angular.module('homePage', [ 'ngMaterial', 'ngMessages' ]).controller('homePageController', inputController);

	function inputController($scope) {
		$scope.user = {
				
		};
	}
</script>

</head>
<body ng-app="homePage" ng-controller="homePageController">


<div layout="row" layout-wrap="">
  <div flex="10" class="navbar-topbar-background"> <!-- Login Registration Row -->
    &nbsp;
  </div>
  <div flex="80" class="navbar-topbar-background">
    <p class="right-text-align"><b><a class="tigererp-anchor" href="${pageContext.request.contextPath }/signup">Registration</a> | <a class="tigererp-anchor" href="${pageContext.request.contextPath }/login">Login</a></b></p>
  </div>
    <div flex="10" class="navbar-topbar-background">
    &nbsp;
  </div>
                 <!-- End Login Registration Row -->
  <div flex="10">
    &nbsp;
  </div>
  <div flex="80">
    <div layout="row" layout-align="space-between center">
      <div flex="30"><img src="images/logo.jpg"></div>
      <div flex="30">&nbsp;</div>
      <div flex="60"><p align="right" style="font-size: 25px; color:  #003366;">Hotline +880 1717565568</p></div>
    </div>
  </div>
  <div flex="10">
   &nbsp;
  </div>
</div>

<div layout="row">
    <md-toolbar md-scroll-shrink class="md-hue-2">
      <div class="md-toolbar-tools">
        <md-button class="md-icon-button" aria-label="Settings" ng-disabled="true">
          <md-icon></md-icon>
        </md-button>
        <div flex md-truncate>
          <md-button class="md-icon-button" ng-click="toggleLeft('left')"><md-icon>menu</md-icon></md-button>
        </div>
	    
        <!-- Drop down Menu user option start -->
        <md-menu md-position-mode="target-right target">
         <md-button aria-label="User profile" class="md-icon-button" ng-click="openMenu($mdMenu, $event)">
          <md-icon><i class="material-icons">more_vert</i></md-icon>
         </md-button>
         <md-menu-content width="4">
          <md-menu-item>
          <md-button ng-click="ctrl.redial($event)">
            <md-icon md-menu-align-target>settings</md-icon>
            Update profile
          </md-button>
         </md-menu-item>
         <md-menu-divider></md-menu-divider>
         <a href="${pageContext.request.contextPath}/logout">
         <md-menu-item>
         <md-button>
            <md-icon md-menu-align-target>exit_to_app</md-icon>
            Logout
          </md-button>
        </md-menu-item>
        </a>
        <md-menu-divider></md-menu-divider>
      </md-menu-content>
    </md-menu>
    <!-- Drop down menu user opt end -->
      </div>
    </md-toolbar>
</div>
<section style="text-align: center;"><h1>Welcome To TigerERP</h1></section>
<br/>    
  <footer>
    <md-toolbar class="md-theme-indigo">
      <div layout="row" layout-align="center center" flex>
        Contact at info@tigerslab.com
      </div>
    </md-toolbar>
  </footer>
  
  <script src="<c:url value='/js/tigererp.js' />"></script>
  <script src="<c:url value='/js/employeerole_controller.js' />"></script>

</body>
</html>