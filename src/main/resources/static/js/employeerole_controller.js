
//               ********************** EMPLOYEE ROLE CONTROLLER PART STARTED **************************

app.controller('addEmployeeRoleController', ['$mdDialog', '$apiurl', '$scope', function ($mdDialog, $apiurl, $scope) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  $scope.employeeRole = { roleName : '', roleDescription: ''};
	  function success(response){
		  $mdDialog.hide(response);
	  }
	  this.addItem = function () {
	    $scope.item.form.$setSubmitted();
	    if($scope.item.form.$valid) {
	    	$apiurl.employeeRoles.save($scope.employeeRole, success);
	    }
	  };
	}]);

app.controller('updateEmployeeRoleController', ['$mdDialog', '$apiurl', '$scope', 'items', function ($mdDialog, $apiurl, $scope, items) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  $scope.employeeRole = {id : items.id, roleName : items.roleName, roleDescription: items.roleDescription};
	  function success(response){
			  $mdDialog.hide(response);
	  }
	  this.updateItem = function () {
	    $scope.item.form.$setSubmitted();
	    if($scope.item.form.$valid) {
	    	$apiurl.employeeRoles.update($scope.employeeRole, success);
	    }
	  };
	}]);

app.controller('deleteEmployeeRoleController', ['items', '$mdDialog', '$apiurl', '$scope', '$q', '$log','$rootScope', function (items, $mdDialog, $apiurl, $scope, $q, $log, $rootScope) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  function deleteSingleEntity(item, index) {
	    var deferred = $apiurl.employeeRoles.remove({id: item.id});
	    deferred.$promise.then(function (response) {
	    	items.splice(index, 1);
	    }, function(error){
	    	$rootScope.errorMessage = error.data.message;
	    	$rootScope.showAlert();
	    });
	    return deferred.$promise;
	  }
	  function onComplete(response) {
	    $mdDialog.hide();
	  }

	  this.deleteEntity = function() {
	    $q.all(items.forEach(deleteSingleEntity)).then(onComplete);
	  }
	}]);

app.controller('employeeRoleManageController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$rootScope', '$log', function ($timeout, $mdDialog, $apiurl, $scope, $rootScope, $log) {
	  'use strict';
	  var bookmark;
	  $scope.selected = [];
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: $rootScope.pageSize,
	    page: 1,
	    order: '-id'
	  };
	  function success(response) {
	    $scope.response = response;
	    
	  }
	  $scope.reLoadStuff = function () {
		    $scope.promise = $timeout(function(){
		    	$scope.getResponse();
		    }, 2000);
		  }
	  $scope.addItem = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'addEmployeeRoleController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      templateUrl: 'templates/employee/add_employeerole_dialog.html',
	    }).then($scope.getResponse);
	  };
	  $scope.updateItem = function (event) {
		    $mdDialog.show({
		      clickOutsideToClose: true,
		      controller: 'updateEmployeeRoleController',
		      controllerAs: 'ctrl',
		      focusOnOpen: false,
		      targetEvent: event,
		      locals:{ items : event },
		      templateUrl: 'templates/employee/update_employeerole_dialog.html',
		    }).then($scope.getResponse);
		  };
	  $scope.delete = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'deleteEmployeeRoleController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      locals: { items: $scope.selected },
	      templateUrl: 'templates/employee/delete_confirm_dialog.html',
	    }).then($scope.getResponse);
	  };
	  
	  $scope.deleteSingleRow = function (singleRowId) {
		    $scope.id = [{id: singleRowId}];
		    $mdDialog.show({
		      clickOutsideToClose: true,
		      controller: 'deleteEmployeeRoleController',
		      controllerAs: 'ctrl',
		      focusOnOpen: false,
		      targetEvent: event,
		      locals: { items: $scope.id },
		      templateUrl: 'templates/employee/delete_confirm_dialog.html',
		    }).then($scope.getResponse);
		  };
	  
	  $scope.getResponse = function () {
	    $scope.promise = $apiurl.employeeRoles.get($scope.query, success).$promise;
	    $scope.selected = [];
	  };
	  
	  $scope.removeFilter = function () {
	    $scope.filter.show = false;
	    $scope.query.filter = '';
	    if($scope.filter.form.$dirty) {
	      $scope.filter.form.$setPristine();
	    }
	  };
	  
	  $scope.$watch('query.filter', function (newValue, oldValue) {
	    if(!oldValue) {
	      bookmark = $scope.query.page;
	    }
	    if(newValue !== oldValue) {
	      $scope.query.page = 1;
	    }
	    if(!newValue) {
	      $scope.query.page = bookmark;
	    }
	    $scope.getResponse();
	  });
	}]);

// ********************** EMPLOYEE ROLE CONTROLLER PART ENDED
// **************************


// ********************** ACCESS PERMISSION CONTROLLER PART STARTED
// **************************

app.controller('accessPermissionManageController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$log', '$rootScope', function ($timeout, $mdDialog, $apiurl, $scope, $log, $rootScope) {
	  'use strict';
	  $scope.employeeRole = {};
	  $scope.accessPermission = {};
	  
	  $scope.query = {
	    filter: '',
	    limit: '1000',
	    page: 1,
	    order: 'id'
	  };
	  
	  function success(response) {
		$rootScope.showProgressBar = false;
	    $scope.response = response;
	  }
	  
	  function failed(response) {
			$rootScope.showProgressBar = false;
	  }
	  
	  $scope.loadRoleList = function(){
		  $rootScope.showProgressBar = true;
		  $scope.promise = $apiurl.employeeRoles.get($scope.query, success, failed).$promise;
	  }
	  $scope.loadRoleList();
	  
// var bookmark;
// $scope.selected = [];
// $scope.filter = {
// options: {
// debounce: 500
// }
// };

	  $scope.addItem = function (event) {
		  $rootScope.showProgressBar = true;
		  $scope.form.$setSubmitted();
		  if($scope.form.$valid) {
		    if($scope.accessPermission.id>0){
		    	$apiurl.accessPermission.update($scope.accessPermission).$promise.then(success, failed);
		    }
		    else{
		    	console.log("save");
		    	$scope.accessPermission.employeeRole = $scope.employeeRole;
		    	$apiurl.accessPermission.save($scope.accessPermission).$promise.then(success, failed);
		    }
		  }
		  
		  function success(response){
			  $rootScope.showProgressBar = false;
		  }
	  };
	  
	  $scope.onChange = function(id){
		  $rootScope.showProgressBar = true;
		  
		  $scope.promise = $apiurl.accessPermission.get({roleId : id}, success).$promise.then(success, failure);
		  
		  function failure(err){
			  $rootScope.showProgressBar = false;
			  $scope.accessPermission = {};
		  }
		  function success(response){
			  $rootScope.showProgressBar = false;
			  $scope.accessPermission = response;
		  } 
	  }
	  
	  
// $scope.deleteItem = function (event) {
// if($scope.accessPermission.id > 0){
// var deferred = $apiurl.accessPermission.remove({id:
// $scope.accessPermission.id, roleId:$scope.accessPermission.employeeRole.id});
// deferred.$promise.then(function () {
// $log.info('Deleted');
// });
// }
// $log.info('DEL');
// };
	  
// $scope.getResponse = function () {
// $scope.promise = $apiurl.employeeRoles.get($scope.query, success).$promise;
// };
	  
// $scope.removeFilter = function () {
// $scope.filter.show = false;
// $scope.query.filter = '';
// if($scope.filter.form.$dirty) {
// $scope.filter.form.$setPristine();
// }
// };
	  
// $scope.$watch('query.filter', function (newValue, oldValue) {
// if(!oldValue) {
// bookmark = $scope.query.page;
// }
// if(newValue !== oldValue) {
// $scope.query.page = 1;
// }
// if(!newValue) {
// $scope.query.page = bookmark;
// }
// // $scope.getResponse();
// $scope.loadRoleList();
// });
	}]);


// ********************** ACCESS PERMISSION CONTROLLER PART ENDED
// **************************


// ********************** EMPLOYEE MANAGEMENT CONTROLLER PART STARTED
// **************************


app.controller('addEmployeeController', ['$mdDialog', '$apiurl', '$scope', '$log', function ($mdDialog, $apiurl, $scope, $log) {
	  'use strict';
	  
	  // to fetch country
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '400',
	    page: 1,
	    order: 'id'
	  };
	  function successCountry(response) {
	    $scope.country = response;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.country.get($scope.query, successCountry).$promise;
	  };
	  $scope.getResponse();
	  // to fetch country
	  
	  // to fetch employee branch
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '400',
	    page: 1,
	    order: 'id'
	  };
	  function successBranch(response) {
	    $scope.branch = response;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.branches.get($scope.query, successBranch).$promise;
	  };
	  $scope.getResponse();
	  // to fetch employee branch
	  
	  // to fetch employee gender
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '400',
	    page: 1,
	    order: 'id'
	  };
	  function successGender(response) {
	    $scope.gender = response;
	  }
	  $scope.getGResponse = function () {
		  $scope.promise = $apiurl.genders.get($scope.query, successGender).$promise;
	  };
	  $scope.getGResponse();
	  // to fetch employee gender
	  
	  // to fetch employee Category
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '4000',
	    page: 1,
	    order: 'id'
	  };
	  function successEmpCat(response) {
	    $scope.empCat = response;
	  }
	  $scope.getGResponse = function () {
		  $scope.promise = $apiurl.employeeCategories.get($scope.query, successEmpCat).$promise;
	  };
	  $scope.getGResponse();
	  // to fetch employee category

	  
	  // to fetch account charts
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '1000',
	    page: 1,
	    order: 'id'
	  };
	  function successP(response) {
	    $scope.parents = response;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.accountchart.get($scope.query, successP).$promise;
	  };
	  $scope.getResponse();
	  // to fetch account charts
	  
	  // to fetch ledger group
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '1000',
	    page: 1,
	    order: 'id'
	  };
	  function successG(response) {
	    $scope.group= response;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.ledgergroups.get($scope.query, successG).$promise;
	  };
	  $scope.getResponse();
	  // to fetch ledger group
	  $scope.query = {
			    filter: '',
			    limit: '10000',
			    page: 1,
			    order: 'id'
			  };
	  this.cancel = $mdDialog.cancel;
	  $scope.user = {};
	  $scope.empRoleSingle = {};
	  
	  function success(response){
		  $mdDialog.hide(response);
	  }
	  
	  $scope.getEmpRoles = function(){
		  $apiurl.employeeRoles.get($scope.query, processEmp);
	  }
	  
	  $scope.getEmpRoles();
	  
	  function processEmp(response){
		  $scope.empRoles = response.content;
	  }
	  
	  $scope.onChange = function(userRole){
		 
		  $scope.user.empRoles = [userRole];
	  }
	  
	  this.addItem = function () {
	    $scope.item.form.$setSubmitted();
	    if($scope.item.form.$valid) {
	    	$apiurl.employee.save($scope.user, success);
	    }
	  };
	}]);
app.controller('viewEmployeeController',['$mdDialog', '$apiurl', '$scope', 'items', function ($mdDialog, $apiurl, $scope, items) {
	  'use strict';	  
	  
	  // to fetch country
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '9999',
	    page: 1,
	    order: 'id'
	  };
	  function successCountry(response) {
	    $scope.country = response;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.country.get($scope.query, successCountry).$promise;
	  };
	  $scope.getResponse();
	  // to fetch country
	  
	  // to fetch employee branch
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '999',
	    page: 1,
	    order: '-id'
	  };
	  function successBranch(response) {
	    $scope.branch = response;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.branches.get($scope.query, successBranch).$promise;
	  };
	  $scope.getResponse();
	  // to fetch employee branch
	  
	  // to fetch employee gender
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '400',
	    page: 1,
	    order: 'id'
	  };
	  function successGender(response) {
	    $scope.gender = response;
	  }
	  $scope.getGResponse = function () {
		  $scope.promise = $apiurl.genders.get($scope.query, successGender).$promise;
	  };
	  $scope.getGResponse();
	  // to fetch employee gender
	  
	  // to fetch employee Category
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '9999',
	    page: 1,
	    order: '-id'
	  };
	  function successEmpCat(response) {
	    $scope.empCat = response;
	  }
	  $scope.getGResponse = function () {
		  $scope.promise = $apiurl.employeeCategories.get($scope.query, successEmpCat).$promise;
	  };
	  $scope.getGResponse();
	  // to fetch employee category
	  
	  this.cancel = $mdDialog.cancel;
	  
	  $scope.query = {
			    filter: '',
			    limit: '10000',
			    page: 1,
			    order: 'id'
			  };
	  $scope.empRoleSingle = {};
	  
	  $scope.user = items; delete $scope.user.roles;
	  
	  $scope.getEmpRoles = function(){
		  $apiurl.employeeRoles.get($scope.query, processEmp);
	  }
	  
	  $scope.getEmpRoles();
	  
	  function processEmp(response){
		  $scope.empRoles = response.content;
	  }
	  
	  $scope.onChange = function(userRole){
		  $scope.user.empRoles = [userRole];
	  }
	  
	  function success(response){
			  $mdDialog.hide(response);
	  }
	  this.updateItem = function () {
	    $scope.item.form.$setSubmitted();
	    if($scope.item.form.$valid) {
	    	$scope.user.empRoles.user={};
	    	$apiurl.employee.update($scope.user, success);
	    }
	  };
	  
	}]);

app.controller('updateEmployeeController', ['$mdDialog', '$apiurl', '$scope', 'items', function ($mdDialog, $apiurl, $scope, items) {
	  'use strict';	  
	  
	  // to fetch country
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '400',
	    page: 1,
	    order: 'id'
	  };
	  function successCountry(response) {
	    $scope.country = response;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.country.get($scope.query, successCountry).$promise;
	  };
	  $scope.getResponse();
	  // to fetch country
	  
	  // to fetch employee branch
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '400',
	    page: 1,
	    order: 'id'
	  };
	  function successBranch(response) {
	    $scope.branch = response;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.branches.get($scope.query, successBranch).$promise;
	  };
	  $scope.getResponse();
	  // to fetch employee branch
	  
	  // to fetch employee gender
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '400',
	    page: 1,
	    order: 'id'
	  };
	  function successGender(response) {
	    $scope.gender = response;
	  }
	  $scope.getGResponse = function () {
		  $scope.promise = $apiurl.genders.get($scope.query, successGender).$promise;
	  };
	  $scope.getGResponse();
	  // to fetch employee gender
	  
	  // to fetch employee Category
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '400',
	    page: 1,
	    order: 'id'
	  };
	  function successEmpCat(response) {
	    $scope.empCat = response;
	  }
	  $scope.getGResponse = function () {
		  $scope.promise = $apiurl.employeeCategories.get($scope.query, successEmpCat).$promise;
	  };
	  $scope.getGResponse();
	  // to fetch employee category
	  
	  this.cancel = $mdDialog.cancel;
	  
	  $scope.query = {
			    filter: '',
			    limit: '10000',
			    page: 1,
			    order: 'id'
			  };
	  $scope.empRoleSingle = {};
	  
	  $scope.user = items; // delete $scope.user.roles;
	  
	  $scope.getEmpRoles = function(){
		  $apiurl.employeeRoles.get($scope.query, processEmp);
	  }
	  
	  $scope.getEmpRoles();
	  
	  function processEmp(response){
		  $scope.empRoles = response.content;
	  }
	  
	  $scope.onChange = function(userRole){
		  $scope.user.empRoles = [userRole];
	  }
	  
	  function success(response){
			  $mdDialog.hide(response);
	  }
	  this.updateItem = function () {
	    $scope.item.form.$setSubmitted();
	    if($scope.item.form.$valid) {
	    	$scope.user.empRoles.user={};
	    	$apiurl.employee.update($scope.user, success);
	    }
	  };
	}]);

app.controller('deleteEmployeeController', ['items', '$mdDialog', '$apiurl', '$scope', '$q', '$log','$rootScope', function (items, $mdDialog, $apiurl, $scope, $q, $log, $rootScope) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  function deleteSingleEntity(item, index) {
	    var deferred = $apiurl.employee.remove({id: item.id});
	    deferred.$promise.then(function (response) {
	    	items.splice(index, 1);
	    }, function(error){
	    	$rootScope.errorMessage = error.data.message;
	    	$rootScope.showAlert();
	    });
	    return deferred.$promise;
	  }
	  function onComplete(response) {
	    $mdDialog.hide();
	  }

	  this.deleteEntity = function() {
	    $q.all(items.forEach(deleteSingleEntity)).then(onComplete);
	  }
	}]);

app.controller('employeeManageController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$rootScope', '$log', function ($timeout, $mdDialog, $apiurl, $scope, $rootScope, $log) {
	  'use strict';
	  var bookmark;
	  $scope.selected = [];
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: $rootScope.pageSize,
	    page: 1,
	    order: '-id'
	  };
	  function success(response) {
	    $scope.response = response;
	    
	  }
	  $scope.reLoadStuff = function () {
		    $scope.promise = $timeout(function(){
		    	$scope.getResponse();
		    }, 2000);
		  }
	  $scope.addItem = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'addEmployeeController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      templateUrl: 'templates/employee/add_employee_dialog.html',
	    }).then($scope.getResponse);
	  };
	  $scope.viewItem = function (event) {
	    $mdDialog.show({
		      clickOutsideToClose: true,
		      controller: 'viewEmployeeController',
		      controllerAs: 'ctrl',
		      focusOnOpen: false,
		      targetEvent: event,
		      locals:{ items : event },
		      templateUrl: 'templates/employee/view_employee_dialog.html',
		    }).then($scope.getResponse);
		  };
	  $scope.updateItem = function (event) {
		    $mdDialog.show({
		      clickOutsideToClose: true,
		      controller: 'updateEmployeeController',
		      controllerAs: 'ctrl',
		      focusOnOpen: false,
		      targetEvent: event,
		      locals:{ items : event },
		      templateUrl: 'templates/employee/update_employee_dialog.html',
		    }).then($scope.getResponse);
		  };
	  $scope.delete = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'deleteEmployeeRoleController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      locals: { items: $scope.selected },
	      templateUrl: 'templates/employee/delete_confirm_dialog.html',
	    }).then($scope.getResponse);
	  };
	  
	  $scope.deleteSingleRow = function (singleRowId) {
		    $scope.id = [{id: singleRowId}];
		    $mdDialog.show({
		      clickOutsideToClose: true,
		      controller: 'deleteEmployeeController',
		      controllerAs: 'ctrl',
		      focusOnOpen: false,
		      targetEvent: event,
		      locals: { items: $scope.id },
		      templateUrl: 'templates/employee/delete_confirm_dialog.html',
		    }).then($scope.getResponse);
		  };
	  
	  $scope.getResponse = function () {
	    $scope.promise = $apiurl.employee.get($scope.query, success).$promise;
	    $scope.selected = [];
	  };
	  
	  $scope.removeFilter = function () {
	    $scope.filter.show = false;
	    $scope.query.filter = '';
	    if($scope.filter.form.$dirty) {
	      $scope.filter.form.$setPristine();
	    }
	  };
	  
	  $scope.$watch('query.filter', function (newValue, oldValue) {
	    if(!oldValue) {
	      bookmark = $scope.query.page;
	    }
	    if(newValue !== oldValue) {
	      $scope.query.page = 1;
	    }
	    if(!newValue) {
	      $scope.query.page = bookmark;
	    }
	    $scope.getResponse();
	  });
	}]);


// ********************** EMPLOYEE MANAGEMENT CONTROLLER PART ENDED
// **************************


// ********************** COMPANY PROFILE CONTROLLER PART STARTED
// **************************

app.controller('companySettingController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$log', '$rootScope', function ($timeout, $mdDialog, $apiurl, $scope, $log, $rootScope) {
	  'use strict';
	  
	  $scope.company = {};
	  $rootScope.showProgressBar = true;
	  
	  var bookmark;
	  $scope.selected = [];
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '1000',
	    page: 1,
	    order: 'id'
	  };
	  function success(response) {
		$rootScope.showProgressBar = false;
	    $scope.response = response;
	  }
	  function successP(response) {
		$rootScope.showProgressBar = false;
	    $scope.paperType = response;
	  }
	  function successCompany(response) {
		    $rootScope.showProgressBar = false;
		    $scope.company = response.content[0];
		    $scope.company.companyPhysicalAddress = response.content[0].companyPhysicalAddress[0];
		    $scope.company.companyPhysicalAddress.addressCountry = response.content[0].companyPhysicalAddress[0].addressCountry;
	  }
	  
	  function successCompanyUpdate(response) {
		    $rootScope.showProgressBar = false;
		    $scope.response = response;
	    	$scope.getResponse();
		    $rootScope.showProgressBar = false;
		  }
	  
	  function successCompanySave(response) {
		    $rootScope.showProgressBar = false;
		    $scope.response = response;
		    $scope.getResponse();
		    $rootScope.showProgressBar = false;
	  }
	  
	  function failed(){
		  $rootScope.showProgressBar = false;
	  }
	  
	  $scope.addCompany = function (event) {
		  $rootScope.showProgressBar = true;
		  $scope.companySettingsForm.$setSubmitted();
		  if($scope.companySettingsForm.$valid) {
		    if($scope.company.id>0){
		    	$apiurl.company.update($scope.company).$promise.then(successCompanyUpdate, failed);
		    }
		    else{
		    	$apiurl.company.save($scope.company).$promise.then(successCompanySave, failed);
		    }
		  }
		  
		  function success(response){
			  $rootScope.showProgressBar = false;
		  }
	  };
	  
	  $scope.SelectedCountryForPhone = function (phoneCode){
		  
	  }
	  
	  $scope.reLoadStuff = function () {
		    $scope.promise = $timeout(function(){
		    	$scope.getResponse();
		    }, 2000);
		  }
	  
	  $scope.getResponse = function () {
		$rootScope.showProgressBar = true;
		$scope.promise = $apiurl.company.get($scope.query, successCompany).$promise;
	    $scope.promise = $apiurl.country.get($scope.query, success).$promise;
	    $scope.promise = $apiurl.paperType.get($scope.query, successP).$promise;
	  };
	  
	  function failure(err){
		  $rootScope.showProgressBar = false;
	  }
	  
	  $scope.removeFilter = function () {
	    $scope.filter.show = false;
	    $scope.query.filter = '';
	    if($scope.filter.form.$dirty) {
	      $scope.filter.form.$setPristine();
	    }
	  };
	  
	  $scope.$watch('query.filter', function (newValue, oldValue) {
	    if(!oldValue) {
	      bookmark = $scope.query.page;
	    }
	    if(newValue !== oldValue) {
	      $scope.query.page = 1;
	    }
	    if(!newValue) {
	      $scope.query.page = bookmark;
	    }
	    $scope.getResponse();
	  });
	}]);


// ********************** COMPANY PROFILE CONTROLLER PART ENDED
// **************************


// ********************** COMPANY PROFILE IMAGE CONTROLLER PART STARTED
// **************************
app.directive('fileModel', ['$parse', function ($parse) {
    return { 
        restrict: 'A', 
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel); 
            var modelSetter = model.assign;
            element.bind('change', function(){ 
                scope.$apply(function(){
                  modelSetter(scope, element[0].files[0]);
                }); 
            }); 
        } 
    }; 
}]);
app.service('fileUpload', ['$http', function ($http) {
    this.uploadFileToUrl = function(file, uploadUrl){
    	 var config = {
    			 transformRequest: angular.identity,
    			 transformResponse: angular.identity,
    			 headers : {
    			 'Content-Type': undefined
    			 }
    		}
    	 
        var fd = new FormData();
        fd.append('file', file.file);
        $http.post(uploadUrl, fd, config) 
        .success(function(){ 
        }) 
        .error(function(){ 
        }); 
    } 
}]);

app.controller('companyImageAndLogoSettingController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$log', '$rootScope', 'fileUpload', function ($timeout, $mdDialog, $apiurl, $scope, $log, $rootScope, fileUpload) {
	  'use strict';

	  // GET DOC
	  $scope.query = {
	    filter: '',
	    limit: '400',
	    page: 1,
	    order: 'id'
	  };
	  function successDoc(response) {
	    $scope.response = response;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.document.get($scope.query, successDoc).$promise;
	  };
	  $scope.getResponse();
	  // GET DOC COMPLETE
	  
	  function success(response) {
		  $mdDialog.show(
		      $mdDialog.alert()
		        .parent(angular.element(document.querySelector('#popupContainer')))
		        .clickOutsideToClose(true)
		        .title('Upload successfull!')
		        .textContent('File uploaded Successfully!.')
		        .ariaLabel('Alert Dialog Demo')
		        .ok('Got it!')
		        .targetEvent(response)
		    );
		  $scope.reLoadStuff();
		  $scope.showProgressBar=false;
	  }
	  function fail(reason) {
		  $mdDialog.show(
		      $mdDialog.alert()
		        .parent(angular.element(document.querySelector('#popupContainer')))
		        .clickOutsideToClose(true)
		        .title('Upload failed!')
		        .textContent('Failed to upload file!')
		        .ariaLabel('Alert Dialog Demo')
		        .ok('Got it!')
		        .targetEvent(reason)
		    );
		  $scope.reLoadStuff();
		  $scope.showProgressBar=false;
	  }
	  $scope.reLoadStuff = function () {
		$scope.showProgressBar=true;
		$scope.promise = $timeout(function(){
			$scope.document={};
			console.log("hello reload");
			$scope.showProgressBar=false;
		}, 1000);
	  }
	  $scope.document = {
	  };
	  
	  $scope.addItem = function () {
		  $scope.showProgressBar=true;
		  if($scope.companyLogoAndImageSettingsForm.$valid) {
			  alert(JSON.stringify($scope.document));
			  // $apiurl.document.save($scope.document, success, fail);
			  fileUpload.uploadFileToUrl($scope.document, 'api/document/');
		  }
		  $scope.showProgressBar=false;
	  };
	  
	}]);


// ********************** COMPANY PROFILE IMAGE CONTROLLER PART ENDED
// **************************


// ********************** EMPLOYEE CATEGORY CONTROLLER PART STARTED
// **************************


app.controller('addEmployeeCategoryController', ['$mdDialog', '$apiurl', '$scope', function ($mdDialog, $apiurl, $scope) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  $scope.employeeCategory = { categoryName : '', categoryDescription: ''};
	  function success(response){
		  $mdDialog.hide(response);
	  }
	  this.addItem = function () {
	    $scope.item.form.$setSubmitted();
	    if($scope.item.form.$valid) {
	    	$apiurl.employeeCategories.save($scope.employeeCategory, success);
	    }
	  };
	}]);

app.controller('updateEmployeeCategoryController', ['$mdDialog', '$apiurl', '$scope', 'items', function ($mdDialog, $apiurl, $scope, items) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  $scope.employeeCategory = {id : items.id, categoryName : items.categoryName, categoryDescription: items.categoryDescription, isDeletable: items.isDeletable};
	  function success(response){
			  $mdDialog.hide(response);
	  }
	  this.updateItem = function () {
	    $scope.item.form.$setSubmitted();
	    if($scope.item.form.$valid) {
	    	$apiurl.employeeCategories.update($scope.employeeCategory, success);
	    }
	  };
	}]);

app.controller('deleteEmployeeCategoryController', ['items', '$mdDialog', '$apiurl', '$scope', '$q', '$log','$rootScope', function (items, $mdDialog, $apiurl, $scope, $q, $log, $rootScope) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  function deleteSingleEntity(item, index) {
	    var deferred = $apiurl.employeeCategories.remove({id: item.id});
	    deferred.$promise.then(function (response) {
	    	items.splice(index, 1);
	    }, function(error){
	    	$rootScope.errorMessage = error.data.message;
	    	$rootScope.showAlert();
	    });
	    return deferred.$promise;
	  }
	  function onComplete(response) {
	    $mdDialog.hide();
	  }

	  this.deleteEntity = function() {
	    $q.all(items.forEach(deleteSingleEntity)).then(onComplete);
	  }
	}]);

app.controller('employeeCategoryController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$rootScope', '$log', function ($timeout, $mdDialog, $apiurl, $scope, $rootScope, $log) {
	  'use strict';
	  var bookmark;
	  $scope.selected = [];
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: $rootScope.pageSize,
	    page: 1,
	    order: '-id'
	  };
	  function success(response) {
	    $scope.response = response;
	    
	  }
	  $scope.reLoadStuff = function () {
		    $scope.promise = $timeout(function(){
		    	$scope.getResponse();
		    }, 2000);
		  }
	  $scope.addItem = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'addEmployeeCategoryController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      templateUrl: 'templates/employee/add_employeecategory_dialog.html',
	    }).then($scope.getResponse);
	  };
	  $scope.updateItem = function (event) {
		    $mdDialog.show({
		      clickOutsideToClose: true,
		      controller: 'updateEmployeeCategoryController',
		      controllerAs: 'ctrl',
		      focusOnOpen: false,
		      targetEvent: event,
		      locals:{ items : event },
		      templateUrl: 'templates/employee/update_employeecategory_dialog.html',
		    }).then($scope.getResponse);
		  };
	  $scope.delete = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'deleteEmployeeCategoryController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      locals: { items: $scope.selected },
	      templateUrl: 'templates/employee/delete_confirm_dialog.html',
	    }).then($scope.getResponse);
	  };
	  
	  $scope.deleteSingleRow = function (singleRowId) {
		    $scope.id = [{id: singleRowId}];
		    $mdDialog.show({
		      clickOutsideToClose: true,
		      controller: 'deleteEmployeeCategoryController',
		      controllerAs: 'ctrl',
		      focusOnOpen: false,
		      targetEvent: event,
		      locals: { items: $scope.id },
		      templateUrl: 'templates/employee/delete_confirm_dialog.html',
		    }).then($scope.getResponse);
		  };
	  
	  $scope.getResponse = function () {
	    $scope.promise = $apiurl.employeeCategories.get($scope.query, success).$promise;
	    $scope.selected = [];
	  };
	  
	  $scope.removeFilter = function () {
	    $scope.filter.show = false;
	    $scope.query.filter = '';
	    if($scope.filter.form.$dirty) {
	      $scope.filter.form.$setPristine();
	    }
	  };
	  
	  $scope.$watch('query.filter', function (newValue, oldValue) {
	    if(!oldValue) {
	      bookmark = $scope.query.page;
	    }
	    if(newValue !== oldValue) {
	      $scope.query.page = 1;
	    }
	    if(!newValue) {
	      $scope.query.page = bookmark;
	    }
	    $scope.getResponse();
	  });
	}]);


// ********************** EMPLOYEE CATEGORY CONTROLLER PART ENDED
// **************************


// ********************** CATEGORY STOCK CONTROLLER PART STARTED
// **************************

app.controller('addStockCategoryController', ['$mdDialog', '$apiurl', '$scope', '$log', function ($mdDialog, $apiurl, $scope, $log) {

	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  $scope.queryCategory = {
			    filter: '',
			    limit: '1000',
			    page: 1,
			    order: 'id'
			  };
	  
	  $scope.stockCategorySubmit = { name : '', alius: '', parentId : 0};
	   function successParentResult(parentCategory){
			  var categoryWithoutParent = [];
			  for(var i=0 ; i<parentCategory.content.length ; i++){
				  if(parentCategory.content[i].parentId == 0){
					  categoryWithoutParent.push(parentCategory.content[i]);
				  }
			  }
			  $scope.parentCategories = categoryWithoutParent;
	   }
	  $scope.getParent = function(){
		 $apiurl.stockCategories.get($scope.queryCategory, successParentResult);
	   }
		  
	  $scope.getParent();
	  function success(response){
		  $mdDialog.hide(response);
	  }
	  $scope.onCategoryChange = function(cat){
			
		  $scope.stockCategorySubmit.parentId = cat.id;
	  }
	  this.addItem = function () {
	    $scope.item.form.$setSubmitted();
	    if($scope.item.form.$valid) {
	    	$apiurl.stockCategories.save($scope.stockCategorySubmit, success);
	    }

		  
	  };
	}]);

app.controller('updateStockCategoryController', ['$mdDialog', '$apiurl', '$scope', 'items', 'parent', function ($mdDialog, $apiurl, $scope, items, parent) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  $scope.parent = parent;
	  $scope.stockCategorySubmit = {id : items.id, name : items.name, alius : items.alius, parentId : items.parentId};
	  $scope.onCategoryChange = function(parentParam){
		  $scope.stockCategorySubmit.parentId = parentParam.id;
	  }
	  function success(response){
			  $mdDialog.hide(response);
	  }
	  this.updateItem = function () {
	    $scope.item.form.$setSubmitted();
	    if($scope.item.form.$valid) {
	    	$apiurl.stockCategories.update($scope.stockCategorySubmit, success);
	    }
	  };
	}]);

app.controller('deleteStockCategoryController', ['items', '$mdDialog', '$apiurl', '$scope', '$q', '$log','$rootScope', function (items, $mdDialog, $apiurl, $scope, $q, $log, $rootScope) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  function deleteSingleEntity(item, index) {
	    var deferred = $apiurl.stockCategories.remove({id: item.id});
	    deferred.$promise.then(function (response) {
	    	items.splice(index, 1);
	    }, function(error){
	    	$rootScope.errorMessage = error.data.message;
	    	$rootScope.showAlert();
	    });
	    return deferred.$promise;
	  }
	  function onComplete(response) {
	    $mdDialog.hide();
	  }

	  this.deleteEntity = function() {
	    $q.all(items.forEach(deleteSingleEntity)).then(onComplete);
	  }
	}]);

app.controller('stockCategoryManageController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$rootScope', '$log', function ($timeout, $mdDialog, $apiurl, $scope, $rootScope, $log) {
	  'use strict';
	  var bookmark;
	  $scope.selected = [];
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: $rootScope.pageSize,
	    page: 1,
	    order: '-id'
	  };
	  function success(response) {
		
		  var categoryWithoutParent = [];
		  for(var i=0 ; i<response.content.length ; i++){
			  if(response.content[i].parentId == 0){
				  categoryWithoutParent.push(response.content[i]);
			  }
		  }
		  makeCategoryTree(categoryWithoutParent, 0, '--', response.content);
	    $scope.response = response;
	    $scope.finalCategory.onlyParents = categoryWithoutParent;
	    
	  }
	  function makeCategoryTree(catArray, parentId, sub_mark = '', originalContent){
		  var categoryReArranged = [];
		  
		  if(catArray.length>0){
			  for(var j=0; j<catArray.length; j++){
				  // $log.info(catArray[j].name);
				  var categoryCustom = {};
				  categoryCustom = catArray[j];
				  categoryCustom.mark = '';
				  categoryReArranged.push(categoryCustom);
				  for(var k=0; k<originalContent.length; k++){
					  if(catArray[j].id == originalContent[k].parentId){
						  // $log.info('----'+originalContent[k].name);
						  categoryCustom = originalContent[k];
						  categoryCustom.mark = '      - - - - - ';
						  categoryReArranged.push(categoryCustom);
					  }
				  }
				  
			  }// end for
		  }
		  $scope.finalCategory = categoryReArranged;
	  }
	  
	  $scope.reLoadStuff = function () {
		    $scope.promise = $timeout(function(){
		    	$scope.getResponse();
		    }, 2000);
		  }
	  $scope.addItem = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'addStockCategoryController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      templateUrl: 'templates/org/stock/add_stockcategory_dialog.html',
	    }).then($scope.getResponse);
	  };
	  $scope.updateItem = function (event, parent) {
		    $mdDialog.show({
		      clickOutsideToClose: true,
		      controller: 'updateStockCategoryController',
		      controllerAs: 'ctrl',
		      focusOnOpen: false,
		      targetEvent: event,
		      locals:{ items : event, parent },
		      templateUrl: 'templates/org/stock/update_stock_category_dialog.html',
		    }).then($scope.getResponse);
		  };
	  $scope.delete = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'deleteStockCategoryController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      locals: { items: $scope.selected },
	      templateUrl: 'templates/employee/delete_confirm_dialog.html',
	    }).then($scope.getResponse);
	  };
	  
	  $scope.deleteSingleRow = function (singleRowId) {
		    $scope.id = [{id: singleRowId}];
		    $mdDialog.show({
		      clickOutsideToClose: true,
		      controller: 'deleteStockCategoryController',
		      controllerAs: 'ctrl',
		      focusOnOpen: false,
		      targetEvent: event,
		      locals: { items: $scope.id },
		      templateUrl: 'templates/employee/delete_confirm_dialog.html',
		    }).then($scope.getResponse);
		  };
	  
	  $scope.getResponse = function () {
	    $scope.promise = $apiurl.stockCategories.get($scope.query, success).$promise;
	    $scope.selected = [];
	  };
	  
	  $scope.removeFilter = function () {
	    $scope.filter.show = false;
	    $scope.query.filter = '';
	    if($scope.filter.form.$dirty) {
	      $scope.filter.form.$setPristine();
	    }
	  };
	  
	  $scope.$watch('query.filter', function (newValue, oldValue) {
	    if(!oldValue) {
	      bookmark = $scope.query.page;
	    }
	    if(newValue !== oldValue) {
	      $scope.query.page = 1;
	    }
	    if(!newValue) {
	      $scope.query.page = bookmark;
	    }
	    $scope.getResponse();
	  });
	}]);

// ********************** CATEGORY STOCK CONTROLLER PART STARTED
// **************************


// ********************** COMPANY BRANCH CONTROLLER PART STARTED
// **************************


app.controller('addBranchController', ['$mdDialog', '$apiurl', '$scope', function ($mdDialog, $apiurl, $scope) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  
	  // to fetch country
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '400',
	    page: 1,
	    order: 'id'
	  };
	  function successCountry(response) {
	    $scope.response = response;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.country.get($scope.query, successCountry).$promise;
	  };
	  $scope.getResponse();
	  // to fetch country
	  
  	  $scope.branch = {};
  	  
	  function success(response){
		  $mdDialog.hide(response);
	  }
	  this.addItem = function () {
	    $scope.item.form.$setSubmitted();
	    if($scope.item.form.$valid) {
	    	$apiurl.branches.save($scope.branch, success);
	    }
	  };
	}]);

app.controller('updateBranchController', ['$mdDialog', '$apiurl', '$scope', 'items', function ($mdDialog, $apiurl, $scope, items) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;

	  // to fetch country
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '400',
	    page: 1,
	    order: 'id'
	  };
	  function successCountry(response) {
	    $scope.response = response;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.country.get($scope.query, successCountry).$promise;
	  };
	  $scope.getResponse();
	  // to fetch country
	  
  	  $scope.branch = items;
	  function success(response){
			  $mdDialog.hide(response);
	  }
	  this.updateItem = function () {
	    $scope.item.form.$setSubmitted();
	    if($scope.item.form.$valid) {
	    	$apiurl.branches.update($scope.branch, success);
	    }
	  };
	}]);

app.controller('deleteBranchController', ['items', '$mdDialog', '$apiurl', '$scope', '$q', '$log','$rootScope', function (items, $mdDialog, $apiurl, $scope, $q, $log, $rootScope) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  function deleteSingleEntity(item, index) {
	    var deferred = $apiurl.branches.remove({id: item.id});
	    deferred.$promise.then(function (response) {
	    	items.splice(index, 1);
	    }, function(error){
	    	$rootScope.errorMessage = error.data.message;
	    	$rootScope.showAlert();
	    });
	    return deferred.$promise;
	  }
	  function onComplete(response) {
	    $mdDialog.hide();
	  }

	  this.deleteEntity = function() {
	    $q.all(items.forEach(deleteSingleEntity)).then(onComplete);
	  }
	}]);

app.controller('branchSettingController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$rootScope', '$log', function ($timeout, $mdDialog, $apiurl, $scope, $rootScope, $log) {
	  'use strict';
	  var bookmark;
	  $scope.selected = [];
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: $rootScope.pageSize,
	    page: 1,
	    order: '-id'
	  };
	  function success(response) {
	    $scope.response = response;
	  }
	  $scope.reLoadStuff = function () {
		    $scope.promise = $timeout(function(){
		    	$scope.getResponse();
		    }, 2000);
		  }
	  $scope.addItem = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'addBranchController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      templateUrl: 'templates/org/add_branch_dialog.html',
	    }).then($scope.getResponse);
	  };
	  $scope.updateItem = function (event) {
		    $mdDialog.show({
		      clickOutsideToClose: true,
		      controller: 'updateBranchController',
		      controllerAs: 'ctrl',
		      focusOnOpen: false,
		      targetEvent: event,
		      locals:{ items : event },
		      templateUrl: 'templates/org/update_branch_dialog.html',
		    }).then($scope.getResponse);
		  };
	  $scope.delete = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'deleteEmployeeRoleController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      locals: { items: $scope.selected },
	      templateUrl: 'templates/employee/delete_confirm_dialog.html',
	    }).then($scope.getResponse);
	  };
	  
	  $scope.deleteSingleRow = function (singleRowId) {
		    $scope.id = [{id: singleRowId}];
		    $mdDialog.show({
		      clickOutsideToClose: true,
		      controller: 'deleteBranchController',
		      controllerAs: 'ctrl',
		      focusOnOpen: false,
		      targetEvent: event,
		      locals: { items: $scope.id },
		      templateUrl: 'templates/employee/delete_confirm_dialog.html',
		    }).then($scope.getResponse);
		  };
	  
	  $scope.getResponse = function () {
	    $scope.promise = $apiurl.branches.get($scope.query, success).$promise;
	    $scope.selected = [];
	  };
	  
	  $scope.removeFilter = function () {
	    $scope.filter.show = false;
	    $scope.query.filter = '';
	    if($scope.filter.form.$dirty) {
	      $scope.filter.form.$setPristine();
	    }
	  };
	  
	  $scope.$watch('query.filter', function (newValue, oldValue) {
	    if(!oldValue) {
	      bookmark = $scope.query.page;
	    }
	    if(newValue !== oldValue) {
	      $scope.query.page = 1;
	    }
	    if(!newValue) {
	      $scope.query.page = bookmark;
	    }
	    $scope.getResponse();
	  });
	}]);


// ********************** COMPANY BRANCH CONTROLLER PART ENDED
// **************************

// ********************** BRANCH ROOM CONTROLLER PART STARTED
// **************************

app.controller('addRoomController', ['$mdDialog', '$apiurl', '$scope', '$rootScope', function ($mdDialog, $apiurl, $scope, $rootScope) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  
	  // to fetch branch
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '200',
	    page: 1,
	    order: 'id'
	  };
	  function successBranch(response) {
	    $scope.response = response;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.branches.get($scope.query, successBranch).$promise;
	  };
	  $scope.getResponse();
	  // to fetch branch
	  
	  $scope.room = {};
	  
	  function success(response){
		  $rootScope.showProgressBar = false;
		  $mdDialog.hide(response);
	  }
	  function failed(){
		  $rootScope.showProgressBar = false;
	  }
	  this.addItem = function () {
		  
	    $scope.item.form.$setSubmitted();
	    if($scope.item.form.$valid) {
	    	$apiurl.room.save($scope.room, success, failed);
	    	$rootScope.showProgressBar = true;
	    }
	  };
	}]);

app.controller('updateRoomController', ['$mdDialog', '$apiurl', '$scope', 'items', function ($mdDialog, $apiurl, $scope, items) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;

	  // to fetch branch
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '2000',
	    page: 1,
	    order: 'id'
	  };
	  function successBranch(response) {
	    $scope.response = response;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.branches.get($scope.query, successBranch).$promise;
	  };
	  $scope.getResponse();
	  // to fetch branch
	  
	  $scope.room = items;
	  function success(response){
			  $mdDialog.hide(response);
	  }
	  this.updateItem = function () {
	    $scope.item.form.$setSubmitted();
	    if($scope.item.form.$valid) {
	    	$apiurl.room.update($scope.room, success);
	    }
	  };
	}]);

app.controller('deleteRoomController', ['items', '$mdDialog', '$apiurl', '$scope', '$q', '$log','$rootScope', function (items, $mdDialog, $apiurl, $scope, $q, $log, $rootScope) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  function deleteSingleEntity(item, index) {
	    var deferred = $apiurl.room.remove({id: item.id});
	    deferred.$promise.then(function (response) {
	    	items.splice(index, 1);
	    }, function(error){
	    	$rootScope.errorMessage = error.data.message;
	    	$rootScope.showAlert();
	    });
	    return deferred.$promise;
	  }
	  function onComplete(response) {
	    $mdDialog.hide();
	  }

	  this.deleteEntity = function() {
	    $q.all(items.forEach(deleteSingleEntity)).then(onComplete);
	  }
	}]);

app.controller('roomSettingController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$rootScope', '$log', function ($timeout, $mdDialog, $apiurl, $scope, $rootScope, $log) {
	  'use strict';
	  var bookmark;
	  $scope.selected = [];
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: $rootScope.pageSize,
	    page: 1,
	    order: '-id'
	  };
	  function success(response) {
	    $scope.response = response;
	  }
	  $scope.reLoadStuff = function () {
		    $scope.promise = $timeout(function(){
		    	$scope.getResponse();
		    }, 2000);
		  }
	  $scope.addItem = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'addRoomController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      templateUrl: 'templates/org/add_room_dialog.html',
	    }).then($scope.getResponse);
	  };
	  $scope.updateItem = function (event) {
		    $mdDialog.show({
		      clickOutsideToClose: true,
		      controller: 'updateRoomController',
		      controllerAs: 'ctrl',
		      focusOnOpen: false,
		      targetEvent: event,
		      locals:{ items : event },
		      templateUrl: 'templates/org/update_room_dialog.html',
		    }).then($scope.getResponse);
		  };
	  $scope.delete = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'deleteRoomController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      locals: { items: $scope.selected },
	      templateUrl: 'templates/employee/delete_confirm_dialog.html',
	    }).then($scope.getResponse);
	  };
	  
	  $scope.deleteSingleRow = function (singleRowId) {
		    $scope.id = [{id: singleRowId}];
		    $mdDialog.show({
		      clickOutsideToClose: true,
		      controller: 'deleteRoomController',
		      controllerAs: 'ctrl',
		      focusOnOpen: false,
		      targetEvent: event,
		      locals: { items: $scope.id },
		      templateUrl: 'templates/employee/delete_confirm_dialog.html',
		    }).then($scope.getResponse);
		  };
	  
	  $scope.getResponse = function () {
	    $scope.promise = $apiurl.room.get($scope.query, success).$promise;
	    $scope.selected = [];
	  };
	  
	  $scope.removeFilter = function () {
	    $scope.filter.show = false;
	    $scope.query.filter = '';
	    if($scope.filter.form.$dirty) {
	      $scope.filter.form.$setPristine();
	    }
	  };
	  
	  $scope.$watch('query.filter', function (newValue, oldValue) {
	    if(!oldValue) {
	      bookmark = $scope.query.page;
	    }
	    if(newValue !== oldValue) {
	      $scope.query.page = 1;
	    }
	    if(!newValue) {
	      $scope.query.page = bookmark;
	    }
	    $scope.getResponse();
	  });
	}]);

// ********************** BRANCH ROOM CONTROLLER PART ENDED
// **************************

// ********************** UNIT MEASERMENT CONTROLLER PART STARTED
// ***********************


app.controller('addUnitController', ['$mdDialog', '$apiurl', '$scope', function ($mdDialog, $apiurl, $scope) {
	  'use strict';
	  
	  // to fetch units
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '100',
	    page: 1,
	    order: 'id'
	  };
	  function successUnits(response) {
	    $scope.response = response;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.units.get($scope.query, successUnits).$promise;
	  };
	  $scope.getResponse();
	  // to fetch units
	  
	  this.cancel = $mdDialog.cancel;
	  $scope.unit = {};
	  $scope.unitType = [{id : 1, name : 'SIMPLE'}, {id : 2, name : 'COMPOUND'}];
	  $scope.selectedUnitType = {};
	  $scope.onSelectUnitType = function(unitTypeSeleted){
		  $scope.selectedUnitType = unitTypeSeleted;
	  }
	  
	  function success(response){
		  $mdDialog.hide(response);
	  }
	  this.addItem = function () {
	    $scope.item.form.$setSubmitted();
	    if($scope.item.form.$valid) {
	    	$apiurl.units.save($scope.unit, success);
	    }
	  };
	  this.addCItem = function () {
		    $scope.item.formC.$setSubmitted();
		    if($scope.item.formC.$valid) {
		    	$apiurl.units.save($scope.unit, success);
		    }
		  };
	}]);

app.controller('updateUnitController', ['$mdDialog', '$apiurl', '$scope', 'items', function ($mdDialog, $apiurl, $scope, items) {
	  'use strict';

	  // to fetch units
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '10000',
	    page: 1,
	    order: 'id'
	  };
	  function successUnits(response) {
	    $scope.response = response;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.units.get($scope.query, successUnits).$promise;
	  };
	  $scope.getResponse();
	  // to fetch units
	  
	  this.cancel = $mdDialog.cancel;
	  $scope.unit = items;
	  $scope.unitType = [{id : 1, name : 'SIMPLE'}, {id : 2, name : 'COMPOUND'}];
	  $scope.selectedUnitType = {};
	 
	  if($scope.unit.value != null){
		  $scope.selectedUnitType = $scope.unitType[1];
	  }
	  else{
		  $scope.selectedUnitType = $scope.unitType[0];
	  }
	  $scope.onSelectUnitType = function(unitTypeSeleted){
		 
	  }
	  
	  function success(response){
			  $mdDialog.hide(response);
	  }
	  this.updateItem = function () {
	    $scope.item.form.$setSubmitted();
	    if($scope.item.form.$valid) {
	    	var tempUnit = {};
	    	tempUnit.id = $scope.unit.id;
	    	tempUnit.symbol = $scope.unit.symbol;
	    	tempUnit.formalName = $scope.unit.formalName;
	    	tempUnit.numberOfDecimalPlaces = $scope.unit.numberOfDecimalPlaces;
	    	tempUnit.description = $scope.unit.description;

	    	$apiurl.units.update(tempUnit, success);
	    }
	    else if($scope.item.formC.$valid) {
	    	var tempUnit2 = {firstUnit : {}, secondUnit : {}, value : ""};
	    	tempUnit2.id = $scope.unit.id;
	    	tempUnit2.firstUnit.id = $scope.unit.firstUnit;
	    	tempUnit2.secondUnit.id = $scope.unit.secondUnit;
	    	tempUnit2.value = $scope.unit.value.toString();
	    	tempUnit2.description = $scope.unit.description;
	    	
	    	$apiurl.units.update(tempUnit2, success);
	    }
	  };
	}]);

app.controller('deleteUnitController', ['items', '$mdDialog', '$apiurl', '$scope', '$q', '$log','$rootScope', function (items, $mdDialog, $apiurl, $scope, $q, $log, $rootScope) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  function deleteSingleEntity(item, index) {
	    var deferred = $apiurl.units.remove({id: item.id});
	    deferred.$promise.then(function (response) {
	    	items.splice(index, 1);
	    }, function(error){
	    	$rootScope.errorMessage = error.data.message;
	    	$rootScope.showAlert();
	    });
	    return deferred.$promise;
	  }
	  function onComplete(response) {
	    $mdDialog.hide();
	  }

	  this.deleteEntity = function() {
	    $q.all(items.forEach(deleteSingleEntity)).then(onComplete);
	  }
	}]);

app.controller('unitsManageController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$rootScope','$log', function ($timeout, $mdDialog, $apiurl, $scope, $rootScope, $log) {
	  'use strict';
	  var bookmark;
	  $scope.selected = [];
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: $rootScope.pageSize,
	    page: 1,
	    order: '-id'
	  };
	  function success(response) {
	    $scope.response = response;
	  }
	  $scope.reLoadStuff = function () {
		    $scope.promise = $timeout(function(){
		    	$scope.getResponse();
		    }, 2000);
		  }
	  $scope.addItem = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'addUnitController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      templateUrl: 'templates/org/stock/add_unit_dialog.html',
	    }).then($scope.getResponse);
	  };
	  $scope.updateItem = function (event) {
		    $mdDialog.show({
		      clickOutsideToClose: true,
		      controller: 'updateUnitController',
		      controllerAs: 'ctrl',
		      focusOnOpen: false,
		      targetEvent: event,
		      locals:{ items : event },
		      templateUrl: 'templates/org/stock/update_unit_dialog.html',
		    }).then($scope.getResponse);
		  };
		  
	  $scope.delete = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'deleteUnitController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      locals: { items: $scope.selected },
	      templateUrl: 'templates/employee/delete_confirm_dialog.html',
	    }).then($scope.getResponse);
	  };
	  
	  $scope.deleteSingleRow = function (singleRowId) {
		    $scope.id = [{id: singleRowId}];
		    $mdDialog.show({
		      clickOutsideToClose: true,
		      controller: 'deleteUnitController',
		      controllerAs: 'ctrl',
		      focusOnOpen: false,
		      targetEvent: event,
		      locals: { items: $scope.id },
		      templateUrl: 'templates/employee/delete_confirm_dialog.html',
		    }).then($scope.getResponse);
		  };
	  
	  $scope.getResponse = function () {
	    $scope.promise = $apiurl.units.get($scope.query, success).$promise;
	    $scope.selected = [];
	  };
	  
	  $scope.removeFilter = function () {
	    $scope.filter.show = false;
	    $scope.query.filter = '';
	    if($scope.filter.form.$dirty) {
	      $scope.filter.form.$setPristine();
	    }
	  };
	  
	  $scope.$watch('query.filter', function (newValue, oldValue) {
	    if(!oldValue) {
	      bookmark = $scope.query.page;
	    }
	    if(newValue !== oldValue) {
	      $scope.query.page = 1;
	    }
	    if(!newValue) {
	      $scope.query.page = bookmark;
	    }
	    $scope.getResponse();
	  });
	}]);

// ********************** UNIT MEASERMENT CONTROLLER PART STARTED
// ***********************

// ********************** LEDGER GROUP CONTROLLER PART STARTED
// ************************

app.controller('addledgerGroupController', ['$mdDialog', '$apiurl', '$scope', function ($mdDialog, $apiurl, $scope) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  
	  $scope.ledgergroup = {};
	  
	  function success(response){
		  $mdDialog.hide(response);
	  }
	  this.addItem = function () {
	    $scope.item.form.$setSubmitted();
	    if($scope.item.form.$valid) {
	    	$apiurl.ledgergroups.save($scope.ledgergroup, success);
	    }
	  };
	}]);

app.controller('updateLedgerGroupController', ['$mdDialog', '$apiurl', '$scope', 'items', function ($mdDialog, $apiurl, $scope, items) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;

	  $scope.ledgergroup = items;
	  function success(response){
			  $mdDialog.hide(response);
	  }
	  this.updateItem = function () {
	    $scope.item.form.$setSubmitted();
	    if($scope.item.form.$valid) {
	    	$apiurl.ledgergroups.update($scope.ledgergroup, success);
	    }
	  };
	}]);

app.controller('deleteLedgerGroupController', ['items', '$mdDialog', '$apiurl', '$scope', '$q', '$log','$rootScope', function (items, $mdDialog, $apiurl, $scope, $q, $log, $rootScope) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  function deleteSingleEntity(item, index) {
	    var deferred = $apiurl.ledgergroups.remove({id: item.id});
	    deferred.$promise.then(function (response) {
	    	items.splice(index, 1);
	    }, function(error){
	    	$rootScope.errorMessage = error.data.message;
	    	$rootScope.showAlert();
	    });
	    return deferred.$promise;
	  }
	  function onComplete(response) {
	    $mdDialog.hide();
	  }

	  this.deleteEntity = function() {
	    $q.all(items.forEach(deleteSingleEntity)).then(onComplete);
	  }
	}]);

app.controller('ledgerGroupController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$rootScope', '$log', function ($timeout, $mdDialog, $apiurl, $scope, $rootScope, $log) {
	  'use strict';
	  var bookmark;
	  $scope.selected = [];
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: $rootScope.pageSize,
	    page: 1,
	    order: '-id'
	  };
	  function success(response) {
	    $scope.response = response;
	  }
	  $scope.reLoadStuff = function () {
		    $scope.promise = $timeout(function(){
		    	$scope.getResponse();
		    }, 2000);
		  }
	  $scope.addItem = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'addledgerGroupController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      templateUrl: 'templates/org/add_ledger_group_dialog.html',
	    }).then($scope.getResponse);
	  };
	  $scope.updateItem = function (event) {
		    $mdDialog.show({
		      clickOutsideToClose: true,
		      controller: 'updateLedgerGroupController',
		      controllerAs: 'ctrl',
		      focusOnOpen: false,
		      targetEvent: event,
		      locals:{ items : event },
		      templateUrl: 'templates/org/update_ledger_group.html',
		    }).then($scope.getResponse);
		  };
	  $scope.delete = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'deleteLedgerGroupController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      locals: { items: $scope.selected },
	      templateUrl: 'templates/employee/delete_confirm_dialog.html',
	    }).then($scope.getResponse);
	  };
	  
	  $scope.deleteSingleRow = function (singleRowId) {
		    $scope.id = [{id: singleRowId}];
		    $mdDialog.show({
		      clickOutsideToClose: true,
		      controller: 'deleteRoomController', /* will need to change */
		      controllerAs: 'ctrl',
		      focusOnOpen: false,
		      targetEvent: event,
		      locals: { items: $scope.id },
		      templateUrl: 'templates/employee/delete_confirm_dialog.html',
		    }).then($scope.getResponse);
		  };
	  
	  $scope.getResponse = function () {
	    $scope.promise = $apiurl.ledgergroups.get($scope.query, success).$promise;
	    $scope.selected = [];
	  };
	  
	  $scope.removeFilter = function () {
	    $scope.filter.show = false;
	    $scope.query.filter = '';
	    if($scope.filter.form.$dirty) {
	      $scope.filter.form.$setPristine();
	    }
	  };
	  
	  $scope.$watch('query.filter', function (newValue, oldValue) {
	    if(!oldValue) {
	      bookmark = $scope.query.page;
	    }
	    if(newValue !== oldValue) {
	      $scope.query.page = 1;
	    }
	    if(!newValue) {
	      $scope.query.page = bookmark;
	    }
	    $scope.getResponse();
	  });
	}]);

// ********************** LEDGER GROUP CONTROLLER PART ENDED
// **************************

// ********************** LEDGER CONTROLLER PART **************************
app.controller('addledgerController', ['$mdDialog', '$apiurl', '$scope', function ($mdDialog, $apiurl, $scope) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  
	  // to fetch country
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '1000',
	    page: 1,
	    order: 'id'
	  };
	  function successCountry(response) {
	    $scope.country = response;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.country.get($scope.query, successCountry).$promise;
	  };
	  $scope.getResponse();
	  // to fetch country
	  
	  // to fetch account charts
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '1000',
	    page: 1,
	    order: 'id'
	  };
	  function successP(response) {
	    $scope.parents = response;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.accountchart.get($scope.query, successP).$promise;
	  };
	  $scope.getResponse();
	  // to fetch account charts
	  
	  // to fetch ledger group
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '1000',
	    page: 1,
	    order: 'id'
	  };
	  function successG(response) {
	    $scope.group= response;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.ledgergroups.get($scope.query, successG).$promise;
	  };
	  $scope.getResponse();
	  // to fetch ledger group

	  // to fetch gender
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '400',
	    page: 1,
	    order: 'id'
	  };
	  function successGender(response) {
	    $scope.gender = response;
	  }
	  $scope.getGResponse = function () {
		  $scope.promise = $apiurl.genders.get($scope.query, successGender).$promise;
	  };
	  $scope.getGResponse();
	  // to fetch gender
	  
	  $scope.ledger= {};
	  $scope.ledger.openingBalance= '0.00';
	  
	  function success(response){
		  $mdDialog.hide(response);
	  }
	  this.addItem = function () {
	    $scope.item.form.$setSubmitted();
	    if($scope.item.form.$valid) {
	    	$apiurl.ledger.save($scope.ledger, success);
	    }
	  };
	}]);

app.controller('updateLedgerController', ['$mdDialog', '$apiurl', '$scope', 'items', function ($mdDialog, $apiurl, $scope, items) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  
	// to fetch country
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '400',
	    page: 1,
	    order: 'id'
	  };
	  function successCountry(response) {
	    $scope.country = response;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.country.get($scope.query, successCountry).$promise;
	  };
	  $scope.getResponse();
	  // to fetch country
	  
	  // to fetch account charts
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '100',
	    page: 1,
	    order: 'id'
	  };
	  function successP(response) {
	    $scope.parents = response;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.accountchart.get($scope.query, successP).$promise;
	  };
	  $scope.getResponse();
	  // to fetch account charts
	  
	  // to fetch ledger group
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '100',
	    page: 1,
	    order: 'id'
	  };
	  function successG(response) {
	    $scope.group= response;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.ledgergroups.get($scope.query, successG).$promise;
	  };
	  $scope.getResponse();
	  // to fetch ledger group

	  // to fetch gender
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '400',
	    page: 1,
	    order: 'id'
	  };
	  function successGender(response) {
	    $scope.gender = response;
	  }
	  $scope.getGResponse = function () {
		  $scope.promise = $apiurl.genders.get($scope.query, successGender).$promise;
	  };
	  $scope.getGResponse();
	  // to fetch gender
	  
	  $scope.ledger= items;
	  function success(response){
			  $mdDialog.hide(response);
	  }
	  this.updateItem = function () {
	    $scope.item.form.$setSubmitted();
	    if($scope.item.form.$valid) {
	    	$apiurl.ledger.update($scope.ledger, success);
	    }
	  };
	}]);

app.filter('absolute', function(){
	return function(x){
		if(x<0){
			return (-1)*x;
		}
		return x;
	};
});

app.controller('viewLedgerController', ['$timeout','$mdDialog', '$apiurl', '$scope', 'items', function ($timeout,$mdDialog, $apiurl, $scope, items) {
	  'use strict';
	  var bookmark;
	  var count=0;
	  this.cancel = $mdDialog.cancel;
	  $scope.ledger= items;
	  // to fetch individual account transaction

// $scope.filter = {
// options: {
// debounce: 500
// }
// };
	  $scope.startDate = new Date();
	  $scope.startDate.setFullYear(2000);
	  $scope.endDate = new Date();
	  $scope.endDate.setHours(48);
	  $scope.query = {
		startDate: $scope.startDate,
		endDate: $scope.endDate,
		type: 'account',
		id : $scope.ledger.id,
	    filter: '',
	    limit: '20',
	    page: 1,
	    order: '-id'
	  };
	  $scope.reLoadStuff = function () {
		count=0;
		$scope.promise = $timeout(function(){
			$scope.getResponse();
		}, 2000);
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.accounttransaction.get($scope.query, success).$promise;
	  };
	  function success(response) {
		count=count+1;
		$scope.s=response.numberOfElements-1;
	    $scope.response = response;
		if(count==1){
			$scope.query.page=response.totalPages;
			$scope.getResponse();
			count=count+1;
		}
	  }
	  
	  $scope.fetchTransactions = function(){
		  if($scope.response==null){
			  $scope.getResponse();
		  }
	  }

	  $scope.onChangeDate= function(){
		  $scope.startDate.setHours(0);
		  $scope.endDate.setHours(24);
		  $scope.query.startDate = $scope.startDate;
		  $scope.query.endDate = $scope.endDate;
		  $scope.getResponse();
	  }
	  
// $scope.removeFilter = function () {
// $scope.filter.show = false;
// $scope.query.filter = '';
// if($scope.filter.form.$dirty) {
// $scope.filter.form.$setPristine();
// }
// };
	  
//
// $scope.$watch('query.filter', function (newValue, oldValue) {
// if(!oldValue) {
// bookmark = $scope.query.page;
// }
// if(newValue !== oldValue) {
// $scope.query.page = 1;
// }
// if(!newValue) {
// $scope.query.page = bookmark;
// }
// $scope.getResponse();
// });

	}]);

app.controller('deleteLedgerController', ['items', '$mdDialog', '$apiurl', '$scope', '$q', '$log','$rootScope', function (items, $mdDialog, $apiurl, $scope, $q, $log, $rootScope) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  function deleteSingleEntity(item, index) {
	    var deferred = $apiurl.ledger.remove({id: item.id});
	    deferred.$promise.then(function (response) {
	    	items.splice(index, 1);
	    }, function(error){
	    	$rootScope.errorMessage = error.data.message;
	    	$rootScope.showAlert();
	    });
	    return deferred.$promise;
	  }
	  function onComplete(response) {
	    $mdDialog.hide();
	  }

	  this.deleteEntity = function() {
	    $q.all(items.forEach(deleteSingleEntity)).then(onComplete);
	  }
	}]);


app.controller('ledgerController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$rootScope', '$log', function ($timeout, $mdDialog, $apiurl, $scope, $rootScope, $log) {
	  'use strict';
	  var bookmark;
	  $scope.selected = [];
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: $rootScope.pageSize,
	    page: 1,
	    order: '-id'
	  };
	  function success(response) {
	    $scope.response = response;
	  }
	  $scope.reLoadStuff = function () {
		    $scope.promise = $timeout(function(){
		    	$scope.getResponse();
		    }, 2000);
		  }
	  $scope.addItem = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'addledgerController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      templateUrl: 'templates/org/add_ledger_dialog.html',
	    }).then($scope.getResponse);
	  };
	  $scope.updateItem = function (event) {
		    $mdDialog.show({
		      clickOutsideToClose: true,
		      controller: 'updateLedgerController',
		      controllerAs: 'ctrl',
		      focusOnOpen: false,
		      targetEvent: event,
		      locals:{ items : event },
		      templateUrl: 'templates/org/update_ledger.html',
		    }).then($scope.getResponse);
		  };
	  $scope.viewItem = function (event) {
		    $mdDialog.show({
		      clickOutsideToClose: true,
		      controller: 'viewLedgerController',
		      controllerAs: 'ctrl',
		      focusOnOpen: false,
		      targetEvent: event,
		      locals:{ items : event },
		      templateUrl: 'templates/org/view_ledger.html',
		    }).then($scope.getResponse);
		  };

	  $scope.delete = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'deleteLedgerController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      locals: { items: $scope.selected },
	      templateUrl: 'templates/employee/delete_confirm_dialog.html',
	    }).then($scope.getResponse);
	  };
	  
	  $scope.deleteSingleRow = function (singleRowId) {
		    $scope.id = [{id: singleRowId}];
		    $mdDialog.show({
		      clickOutsideToClose: true,
		      controller: 'deleteLedgerController',
		      controllerAs: 'ctrl',
		      focusOnOpen: false,
		      targetEvent: event,
		      locals: { items: $scope.id },
		      templateUrl: 'templates/employee/delete_confirm_dialog.html',
		    }).then($scope.getResponse);
		  };
	  
	  $scope.getResponse = function () {
	    $scope.promise = $apiurl.ledger.get($scope.query, success).$promise;
	    $scope.selected = [];
	  };
	  
	  $scope.removeFilter = function () {
	    $scope.filter.show = false;
	    $scope.query.filter = '';
	    if($scope.filter.form.$dirty) {
	      $scope.filter.form.$setPristine();
	    }
	  };
	  
	  $scope.$watch('query.filter', function (newValue, oldValue) {
	    if(!oldValue) {
	      bookmark = $scope.query.page;
	    }
	    if(newValue !== oldValue) {
	      $scope.query.page = 1;
	    }
	    if(!newValue) {
	      $scope.query.page = bookmark;
	    }
	    $scope.getResponse();
	  });
	}]);

// ********************** LEDGER CONTROLLER PART ENDED
// **************************

// ********************** LEDGER CONTROLLER PART ENDED
// **************************

app.controller('ledgerBalanceSheetController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$rootScope', '$log', function ($timeout, $mdDialog, $apiurl, $scope, $rootScope, $log) {
	  'use strict';
	  var bookmark;
	  $scope.selected = [];
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
   	    balance: 'balance',
	    filter: '',
	    limit: $rootScope.pageSize,
	    page: 1,
	    order: 'id'
	  };
	  function success(response) {
	    $scope.response = response;
	  }
	  $scope.reLoadStuff = function () {
		    $scope.promise = $timeout(function(){
		    	$scope.getResponse();
		    }, 2000);
		  }
// $scope.addItem = function (event) {
// $mdDialog.show({
// clickOutsideToClose: true,
// controller: 'addledgerController',
// controllerAs: 'ctrl',
// focusOnOpen: false,
// targetEvent: event,
// templateUrl: 'templates/org/add_ledger_dialog.html',
// }).then($scope.getResponse);
// };
// $scope.updateItem = function (event) {
// $mdDialog.show({
// clickOutsideToClose: true,
// controller: 'updateLedgerController',
// controllerAs: 'ctrl',
// focusOnOpen: false,
// targetEvent: event,
// locals:{ items : event },
// templateUrl: 'templates/org/update_ledger.html',
// }).then($scope.getResponse);
// };
// $scope.viewItem = function (event) {
// $mdDialog.show({
// clickOutsideToClose: true,
// controller: 'viewLedgerController',
// controllerAs: 'ctrl',
// focusOnOpen: false,
// targetEvent: event,
// locals:{ items : event },
// templateUrl: 'templates/org/view_ledger.html',
// }).then($scope.getResponse);
// };
//
// $scope.delete = function (event) {
// $mdDialog.show({
// clickOutsideToClose: true,
// controller: 'deleteLedgerController',
// controllerAs: 'ctrl',
// focusOnOpen: false,
// targetEvent: event,
// locals: { items: $scope.selected },
// templateUrl: 'templates/employee/delete_confirm_dialog.html',
// }).then($scope.getResponse);
// };
//	  
// $scope.deleteSingleRow = function (singleRowId) {
// $scope.id = [{id: singleRowId}];
// $mdDialog.show({
// clickOutsideToClose: true,
// controller: 'deleteLedgerController',
// controllerAs: 'ctrl',
// focusOnOpen: false,
// targetEvent: event,
// locals: { items: $scope.id },
// templateUrl: 'templates/employee/delete_confirm_dialog.html',
// }).then($scope.getResponse);
// };
	  
	  $scope.getResponse = function () {
	    $scope.promise = $apiurl.ledger.get($scope.query, success).$promise;
	    $scope.selected = [];
	  };
	  
	  $scope.removeFilter = function () {
	    $scope.filter.show = false;
	    $scope.query.filter = '';
	    if($scope.filter.form.$dirty) {
	      $scope.filter.form.$setPristine();
	    }
	  };
	  
	  $scope.$watch('query.filter', function (newValue, oldValue) {
	    if(!oldValue) {
	      bookmark = $scope.query.page;
	    }
	    if(newValue !== oldValue) {
	      $scope.query.page = 1;
	    }
	    if(!newValue) {
	      $scope.query.page = bookmark;
	    }
	    $scope.getResponse();
	  });
	}]);

// ********************** LEDGER CONTROLLER PART ENDED
// **************************

// ********************** ACCOUNT CHART CONTROLLER PART
// **************************

app.controller('addAccountChartController', ['$mdDialog', '$apiurl', '$scope', function ($mdDialog, $apiurl, $scope) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  
	  // to fetch account equation
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '400',
	    page: 1,
	    order: 'id'
	  };
	  function successAccountEquation(response) {
	    $scope.response = response;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.accountequation.get($scope.query, successAccountEquation).$promise;
	  };
	  $scope.getResponse();
	  // to fetch account equation
	  
  	  $scope.accountC = {};
  	  
	  function success(response){
		  $mdDialog.hide(response);
	  }
	  this.addItem = function () {
	    $scope.item.form.$setSubmitted();
	    if($scope.item.form.$valid) {
	    	$apiurl.accountchart.save($scope.accountC, success);
	    }
	  };
	}]);

app.controller('updateAccountChartController', ['$mdDialog', '$apiurl', '$scope', 'items', function ($mdDialog, $apiurl, $scope, items) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;

	  // to fetch account equation
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '400',
	    page: 1,
	    order: 'id'
	  };
	  function successAccountEquation(response) {
	    $scope.response = response;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.accountequation.get($scope.query, successAccountEquation).$promise;
	  };
	  $scope.getResponse();
	  // to fetch account equation
	  
  	  $scope.accountC = items;
	  function success(response){
			  $mdDialog.hide(response);
	  }
	  this.updateItem = function () {
	    $scope.item.form.$setSubmitted();
	    if($scope.item.form.$valid) {
	    	$apiurl.accountchart.update($scope.accountC, success);
	    }
	  };
	}]);

app.controller('deleteAccountChartController', ['items', '$mdDialog', '$apiurl', '$scope', '$q', '$log','$rootScope', function (items, $mdDialog, $apiurl, $scope, $q, $log, $rootScope) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  function deleteSingleEntity(item, index) {
	    var deferred = $apiurl.accountchart.remove({id: item.id});
	    deferred.$promise.then(function (response) {
	    	items.splice(index, 1);
	    }, function(error){
	    	$rootScope.errorMessage = error.data.message;
	    	$rootScope.showAlert();
	    });
	    return deferred.$promise;
	  }
	  function onComplete(response) {
	    $mdDialog.hide();
	  }

	  this.deleteEntity = function() {
	    $q.all(items.forEach(deleteSingleEntity)).then(onComplete);
	  }
	}]);

app.controller('accountChartController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$rootScope', '$log', function ($timeout, $mdDialog, $apiurl, $scope, $rootScope, $log) {
	  'use strict';
	  var bookmark;
	  $scope.selected = [];
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: $rootScope.pageSize,
	    page: 1,
	    order: '-id'
	  };
	  function success(response) {
	    $scope.response = response;
	  }
	  $scope.reLoadStuff = function () {
		    $scope.promise = $timeout(function(){
		    	$scope.getResponse();
		    }, 2000);
		  }
	  $scope.addItem = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'addAccountChartController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      templateUrl: 'templates/org/add_account_chart.html',
	    }).then($scope.getResponse);
	  };
	  $scope.updateItem = function (event) {
		    $mdDialog.show({
		      clickOutsideToClose: true,
		      controller: 'updateAccountChartController',
		      controllerAs: 'ctrl',
		      focusOnOpen: false,
		      targetEvent: event,
		      locals:{ items : event },
		      templateUrl: 'templates/org/update_account_chart.html',
		    }).then($scope.getResponse);
		  };
	  $scope.delete = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'deleteAccountChartController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      locals: { items: $scope.selected },
	      templateUrl: 'templates/employee/delete_confirm_dialog.html',
	    }).then($scope.getResponse);
	  };
	  
	  $scope.deleteSingleRow = function (singleRowId) {
		    $scope.id = [{id: singleRowId}];
		    $mdDialog.show({
		      clickOutsideToClose: true,
		      controller: 'deleteAccountChartController',
		      controllerAs: 'ctrl',
		      focusOnOpen: false,
		      targetEvent: event,
		      locals: { items: $scope.id },
		      templateUrl: 'templates/employee/delete_confirm_dialog.html',
		    }).then($scope.getResponse);
		  };
	  
	  $scope.getResponse = function () {
	    $scope.promise = $apiurl.accountchart.get($scope.query, success).$promise;
	    $scope.selected = [];
	  };
	  
	  $scope.removeFilter = function () {
	    $scope.filter.show = false;
	    $scope.query.filter = '';
	    if($scope.filter.form.$dirty) {
	      $scope.filter.form.$setPristine();
	    }
	  };
	  
	  $scope.$watch('query.filter', function (newValue, oldValue) {
	    if(!oldValue) {
	      bookmark = $scope.query.page;
	    }
	    if(newValue !== oldValue) {
	      $scope.query.page = 1;
	    }
	    if(!newValue) {
	      $scope.query.page = bookmark;
	    }
	    $scope.getResponse();
	  });
	}]);

// ********************** ACCOUNT CHART CONTROLLER PART ENDED
// **************************


// ********************** JOURNAL VOUCHER CONTROLLER PART
// **************************

app.controller('addJournalVoucherController', ['$mdDialog', '$apiurl', '$scope', '$timeout', '$q', '$log', function ($mdDialog, $apiurl, $scope, $timeout, $q, $log) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  $scope.showProgressBar = true;
	  // to fetch account charts
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '10000',
	    page: 1,
	    order: 'id'
	  };
	  function successLedgerAccounts(response) {
	    $scope.ledgerAccounts = response;
	    $scope.showProgressBar = false;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.ledger.get($scope.query, successLedgerAccounts).$promise;
	  };
	  $scope.getResponse();
	  // to fetch account charts
	  
	  // to fetch voucher type
	  function successVoucherType(response) {
	    $scope.voucherType = response;
	    $scope.showProgressBar = false;
	  }
	  $scope.getVoucherType = function () {
		  $scope.promise = $apiurl.voucherType.get($scope.query, successVoucherType).$promise;
	  };
	  $scope.getVoucherType();
	  // to fetch voucher type
	  
	  var debitLedgerAccount;
	  this.selectedDebitLedgerAccountChanged = selectedDebitLedgerAccountChanged;
	  function selectedDebitLedgerAccountChanged(){
		  this.debitLedgerAccount= this.selectedDebitLedgerAccount;
	  }

	  var creditLedgerAccount;
	  this.selectedCreditLedgerAccountChanged = selectedCreditLedgerAccountChanged;
	  function selectedCreditLedgerAccountChanged(){
		  this.creditLedgerAccount= this.selectedCreditLedgerAccount;
	  }
	  
	  $scope.transactionDate = new Date();
	  $scope.minDate = new Date(
			  $scope.transactionDate.getFullYear()-1,
			  $scope.transactionDate.getMonth(),
			  $scope.transactionDate.getDate()
	  );
	  $scope.maxDate = new Date(
			  $scope.transactionDate.getFullYear(),
			  $scope.transactionDate.getMonth(),
			  $scope.transactionDate.getDate()
	  );
  	  
	  function success(response){
		  $mdDialog.hide(response);
		  $scope.showProgressBar = false;
	  }
	  var accountDebitCredit = [];
	  this.addItem = function () {
			$scope.showProgressBar = true;
		$scope.item.form.$setSubmitted();
		accountDebitCredit = [];
		
		$scope.dabitLedgerEntries = {ledgerAccounts:this.debitLedgerAccount, secondLedgerAccounts:this.creditLedgerAccount,transactions:{amount: $scope.debit_transection_ammount, description:$scope.debit_transection_description, originalDate: $scope.transactionDate}, voucherType:{id:$scope.voucherTypeId}};
		$scope.creditLedgerEntries = {ledgerAccounts:this.creditLedgerAccount,transactions:{amount: $scope.credit_transection_ammount, description:$scope.credit_transection_description, originalDate: $scope.transactionDate}, voucherType:{id:$scope.voucherTypeId}};
		
		accountDebitCredit.push($scope.dabitLedgerEntries);
		accountDebitCredit.push($scope.creditLedgerEntries);
		console.log(accountDebitCredit);
		// accountDebitCredit.push(this.creditAccountChart);
		if($scope.item.form.$valid) {
			$apiurl.accounttransaction.save({type:'account'}, accountDebitCredit, success);
		}
	  };
		  
}]);

app.controller('viewJournalVoucherController', ['$mdDialog', '$apiurl', '$scope', 'items', function ($mdDialog, $apiurl, $scope, items) {
	  'use strict';
	  $scope.query = {
	    filter: '',
	    limit: '10000000',
	    page: 1,
	    order: 'id'
	  };
	  
	  // to fetch voucher type
	  function successVoucherType(response) {
	    $scope.voucherType = response;
	    $scope.showProgressBar = false;
	  }
	  $scope.getVoucherType = function () {
		  $scope.promise = $apiurl.voucherType.get($scope.query, successVoucherType).$promise;
	  };
	  $scope.getVoucherType();
	  // to fetch voucher type
	  
	  this.cancel = $mdDialog.cancel;
	  console.log(items);
	  $scope.data = items;
	  for(var i=0; i<items.length; i=i+1){
		  if(items[i].transactions.amount < 0){
			  $scope.data[i].transactions.amount = items[i].transactions.amount*-1;
		  }
	  }
	  
	}]);

app.controller('updateJournalVoucherController', ['$mdDialog', '$apiurl', '$scope', 'items', function ($mdDialog, $apiurl, $scope, items) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  
	  console.log(items);
	  $scope.data = items;
	  console.log($scope.data);
	  
	  $scope.showProgressBar = true;
	  // to fetch account charts
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '10000',
	    page: 1,
	    order: 'id'
	  };
	  function successLedgerAccounts(response) {
	    $scope.ledgerAccounts = response;
	    $scope.showProgressBar = false;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.ledger.get($scope.query, successLedgerAccounts).$promise;
	  };
	  $scope.getResponse();
	  // to fetch account charts
	  
	  // to fetch voucher type
	  function successVoucherType(response) {
	    $scope.voucherType = response;
	    $scope.showProgressBar = false;
	  }
	  $scope.getVoucherType = function () {
		  $scope.promise = $apiurl.voucherType.get($scope.query, successVoucherType).$promise;
	  };
	  $scope.getVoucherType();
	  // to fetch voucher type
	  
	  this.selectedDebitLedgerAccount = items[0].ledgerAccounts;
	  this.selectedCreditLedgerAccount = items[1].ledgerAccounts;
	  
	  var debitLedgerAccount;
	  this.selectedDebitLedgerAccountChanged = selectedDebitLedgerAccountChanged;
	  function selectedDebitLedgerAccountChanged(){
		  this.debitLedgerAccount= this.selectedDebitLedgerAccount;
	  }

	  var creditLedgerAccount;
	  this.selectedCreditLedgerAccountChanged = selectedCreditLedgerAccountChanged;
	  function selectedCreditLedgerAccountChanged(){
		  this.creditLedgerAccount= this.selectedCreditLedgerAccount;
	  }
	  
	  $scope.transactionDate = new Date();
	  $scope.minDate = new Date(
			  $scope.transactionDate.getFullYear()-1,
			  $scope.transactionDate.getMonth(),
			  $scope.transactionDate.getDate()
	  );
	  $scope.maxDate = new Date(
			  $scope.transactionDate.getFullYear(),
			  $scope.transactionDate.getMonth(),
			  $scope.transactionDate.getDate()
	  );
  	  
	  function success(response){
		  $mdDialog.hide(response);
		  $scope.showProgressBar = false;
	  }
	  var accountDebitCredit = [];
	  this.addItem = function () {
			$scope.showProgressBar = true;
			$scope.item.form.$setSubmitted();
			accountDebitCredit = [];
			
			$scope.dabitLedgerEntries = {id: items[0].id, ledgerAccounts:this.debitLedgerAccount, secondLedgerAccounts:this.creditLedgerAccount, transactions:{id: items[0].transactions.id, amount: $scope.debit_transection_ammount, description:$scope.debit_transection_description, originalDate: $scope.transactionDate}, voucherType:{id:$scope.voucherTypeId}};
			$scope.creditLedgerEntries = {id: items[1].id, ledgerAccounts:this.creditLedgerAccount,transactions:{id: items[1].transactions.id, amount: $scope.credit_transection_ammount, description:$scope.credit_transection_description, originalDate: $scope.transactionDate}, voucherType:{id:$scope.voucherTypeId}};
			
			accountDebitCredit.push($scope.dabitLedgerEntries);
			accountDebitCredit.push($scope.creditLedgerEntries);
			console.log(accountDebitCredit);
			// accountDebitCredit.push(this.creditAccountChart);
			if($scope.item.form.$valid) {
				
				$apiurl.accounttransaction.update({type:'account'}, accountDebitCredit, success);
			}
	  };
	  
	}]);


app.controller('revartJournalVoucherController', ['$mdDialog', '$apiurl', '$scope', 'items', function ($mdDialog, $apiurl, $scope, items) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  console.log(items);
	  function success(response){
		  $mdDialog.hide(response);
		  $scope.showProgressBar = false;
	  }
	  var accountDebitCredit = [];
	  $scope.revartEntity = function () {
		  console.log("hello");
			$scope.showProgressBar = true;
			accountDebitCredit = [];
			if(items[1].transactions.amount<0){
				items[1].transactions.amount=items[1].transactions.amount*(-1);
			}
			$scope.dabitLedgerEntries  = {ledgerAccounts:items[1].ledgerAccounts, secondLedgerAccounts:items[0].ledgerAccounts, transactions:{amount: items[1].transactions.amount, description:items[1].transactions.description, originalDate: items[1].transactions.originalDate}, voucherType:{id:items[1].voucherType.id}};
			$scope.creditLedgerEntries = {ledgerAccounts:items[0].ledgerAccounts, transactions:{amount: items[1].transactions.amount, description:items[0].transactions.description, originalDate: items[0].transactions.originalDate}, voucherType:{id:items[0].voucherType.id}};
			
			accountDebitCredit.push($scope.dabitLedgerEntries);
			accountDebitCredit.push($scope.creditLedgerEntries);
			console.log(accountDebitCredit);

			$apiurl.accounttransaction.save({type:'account'}, accountDebitCredit, success);
	  };
	  
	}]);

app.controller('journalVoucherController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$rootScope' ,'$log', function ($timeout, $mdDialog, $apiurl, $scope, $rootScope, $log) {
	  'use strict';
	  var bookmark;
	  
	  // to fetch voucher type
	  $scope.queryVoucher = {
	    filter: '',
	    limit: '10000',
	    page: 1,
	    order: 'id'
	  };
	  function successVoucherType(response) {
	    $scope.voucherType = response;
	    $scope.showProgressBar = false;
	  }
	  $scope.getVoucherType = function () {
		  $scope.promise = $apiurl.voucherType.get($scope.queryVoucher, successVoucherType).$promise;
	  };
	  $scope.getVoucherType();
	  // to fetch voucher type
	  
	  $scope.selected = [];
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.startDate = new Date();
	  $scope.startDate.setFullYear(2000);
	  $scope.endDate = new Date();
	  $scope.endDate.setHours(24);
	  $scope.query = {
		startDate: $scope.startDate,
		endDate: $scope.endDate,
		type: 'voucher',
		id: 0,
	    filter: '',
	    limit: $rootScope.pageSize,
	    page: 1,
	    order: '-id'
	  };
	  function success(response) {
	    $scope.response = response;
	  }
	  $scope.reLoadStuff = function () {
		    $scope.promise = $timeout(function(){
		    	$scope.getResponse();
		    }, 2000);
		  }
	  $scope.addItem = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'addJournalVoucherController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      templateUrl: 'templates/org/voucher/add_journal_voucher.html',
	    }).then($scope.getResponse);
	  };
	  $scope.viewItem = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'viewJournalVoucherController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      locals:{ items : event },
	      templateUrl: 'templates/org/voucher/view_journal_voucher.html',
	    }).then($scope.getResponse);
	  };
	  $scope.updateItem = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'updateJournalVoucherController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      locals:{ items : event },
	      templateUrl: 'templates/org/voucher/update_journal_voucher.html',
	    }).then($scope.getResponse);
	  };
	  $scope.revartItem = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'revartJournalVoucherController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      locals:{ items : event },
	      templateUrl: 'templates/employee/revart_confirm_dialog.html',
	    }).then($scope.getResponse);
	  };
	  
	  $scope.getResponse = function () {
	    $scope.promise = $apiurl.accounttransaction.get($scope.query, success).$promise;
	    $scope.selected = [];
	  };
	  $scope.changeVoucher = function(voucherId){
		  $scope.startDate.setHours(0);
		  $scope.endDate.setHours(24);
		  $scope.query.id = voucherId;
		  $scope.query.startDate = $scope.startDate;
		  $scope.query.endDate = $scope.endDate;
		  $scope.getResponse();
	  }
	  
	  $scope.removeFilter = function () {
	    $scope.filter.show = false;
	    $scope.query.filter = '';
	    if($scope.filter.form.$dirty) {
	      $scope.filter.form.$setPristine();
	    }
	  };
	  
	  $scope.$watch('query.filter', function (newValue, oldValue) {
	    if(!oldValue) {
	      bookmark = $scope.query.page;
	    }
	    if(newValue !== oldValue) {
	      $scope.query.page = 1;
	    }
	    if(!newValue) {
	      $scope.query.page = bookmark;
	    }
	    $scope.getResponse();
	  });
	}]);

// ********************** JOURNAL VOUCHER CONTROLLER PART ENDED
// **************************


// ********************** STOCK ITEM CONTROLLER PART **************************

app.controller('addStockItemController', ['$mdDialog', '$apiurl', '$scope', function ($mdDialog, $apiurl, $scope) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;

	  // to fetch categories
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '4000',
	    page: 1,
	    order: '-id'
	  };
	  function successCat(response) {
	    $scope.categories = response;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.stockCategories.get($scope.query, successCat).$promise;
	  };
	  $scope.getResponse();
	  // to fetch categories

	  // to fetch units
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '4000',
	    page: 1,
	    order: '-id'
	  };
	  function successUnit(response) {
	    $scope.units = response;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.units.get($scope.query, successUnit).$promise;
	  };
	  $scope.getResponse();
	  // to fetch units
	  
	  function success(response){
		  $mdDialog.hide(response);
	  }
	  this.addItem = function () {
		  $scope.items.unit={id:$scope.items.unit.id};
	    $scope.item.form.$setSubmitted();
	    if($scope.item.form.$valid) {
	    	$apiurl.stockItems.save($scope.items, success);
	    }
	  };
}]);

app.controller('updateStockItemController', ['$mdDialog', '$apiurl', '$scope', 'items', function ($mdDialog, $apiurl, $scope, items) {

	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  // to fetch categories
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '4000',
	    page: 1,
	    order: '-id'
	  };
	  function successCat(response) {
	    $scope.categories = response;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.stockCategories.get($scope.query, successCat).$promise;
	  };
	  $scope.getResponse();
	  // to fetch categories

	  // to fetch units
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '4000',
	    page: 1,
	    order: '-id'
	  };
	  function successUnit(response) {
	    $scope.units = response;
	  }
	  $scope.getResponse = function () {
		  $scope.promise = $apiurl.units.get($scope.query, successUnit).$promise;
	  };
	  $scope.getResponse();
	  // to fetch units

	  $scope.items = items;
	  
	  function success(response){
			  $mdDialog.hide(response);
	  }
	  this.updateItem = function () {
		$scope.items.unit={id:$scope.items.unit.id};
	    $scope.item.form.$setSubmitted();
	    if($scope.item.form.$valid) {
	    	$apiurl.stockItems.update($scope.items, success);
	    }
	  };
}]);

app.controller('deleteStockItemController', ['items', '$mdDialog', '$apiurl', '$scope', '$q', '$log','$rootScope', function (items, $mdDialog, $apiurl, $scope, $q, $log, $rootScope) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  function deleteSingleEntity(item, index) {
	    var deferred = $apiurl.stockItems.remove({id: item.id});
	    deferred.$promise.then(function (response) {
	    	items.splice(index, 1);
	    }, function(error){
	    	$rootScope.errorMessage = error.data.message;
	    	$rootScope.showAlert();
	    });
	    return deferred.$promise;
	  }
	  function onComplete(response) {
	    $mdDialog.hide();
	  }

	  this.deleteEntity = function() {
	    $q.all(items.forEach(deleteSingleEntity)).then(onComplete);
	  }
}]);

app.controller('stockItemController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$rootScope', '$log', function ($timeout, $mdDialog, $apiurl, $scope, $rootScope, $log) {
	  'use strict';
	  var bookmark;
	  $scope.selected = [];
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: $rootScope.pageSize,
	    page: 1,
	    order: '-id'
	  };
	  function success(response) {
	    $scope.response = response;
	  }
	  $scope.reLoadStuff = function () {
		    $scope.promise = $timeout(function(){
		    	$scope.getResponse();
		    }, 2000);
		  }
	  $scope.addItem = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'addStockItemController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      templateUrl: 'templates/org/stock/add_stock_item.html',
	    }).then($scope.getResponse);
	  };
	  $scope.updateItem = function (event) {
		    $mdDialog.show({
		      clickOutsideToClose: true,
		      controller: 'updateStockItemController',
		      controllerAs: 'ctrl',
		      focusOnOpen: false,
		      targetEvent: event,
		      locals:{ items : event },
		      templateUrl: 'templates/org/stock/update_stock_item.html',
		    }).then($scope.getResponse);
		  };
	  $scope.delete = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'deleteStockItemController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      locals: { items: $scope.selected[0] },
	      templateUrl: 'templates/employee/delete_confirm_dialog.html',
	    }).then($scope.getResponse);
	  };
	  
	  $scope.deleteSingleRow = function (singleRowId) {
		    $scope.id = [{id: singleRowId}];
		    $mdDialog.show({
		      clickOutsideToClose: true,
		      controller: 'deleteStockItemController',
		      controllerAs: 'ctrl',
		      focusOnOpen: false,
		      targetEvent: event,
		      locals: { items: $scope.id },
		      templateUrl: 'templates/employee/delete_confirm_dialog.html',
		    }).then($scope.getResponse);
		  };
//	  
	  $scope.getResponse = function () {
	    $scope.promise = $apiurl.stockItems.get($scope.query, success).$promise;
	    $scope.selected = [];
	  };
	  
	  $scope.removeFilter = function () {
	    $scope.filter.show = false;
	    $scope.query.filter = '';
	    if($scope.filter.form.$dirty) {
	      $scope.filter.form.$setPristine();
	    }
	  };
	  
	  $scope.$watch('query.filter', function (newValue, oldValue) {
	    if(!oldValue) {
	      bookmark = $scope.query.page;
	    }
	    if(newValue !== oldValue) {
	      $scope.query.page = 1;
	    }
	    if(!newValue) {
	      $scope.query.page = bookmark;
	    }
	    $scope.getResponse();
	  });
}]);

// ********************** STOCK ITEM CONTROLLER PART
// ENDED**************************


// ********************** PURCHASE ORDER CONTROLLER PART
// **************************

app.controller('addPurchaseOrderController', ['$mdDialog', '$apiurl', '$scope', function ($mdDialog, $apiurl, $scope) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
//
	  // to fetch ledger accounts
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '400',
	    page: 1,
	    order: 'id'
	  };
	  function successLedger(response) {
	    $scope.partyLedger = response;
		  $scope.showProgressBar=false;
	  }
	  $scope.getLedgerResponse = function () {
		  $scope.showProgressBar=true;
		  $scope.promise = $apiurl.ledger.get($scope.query, successLedger).$promise;
	  };
	  $scope.getLedgerResponse();
	  // to fetch ledger accounts
	  
	  // to fetch balance
	  function successBalance(response) {
		  if(response.content.length==0 ){
			  $scope.balance = 0.00;
			  $scope.showProgressBar=false;
		  }
		  $scope.balance= response.content[response.totalElements-1][4];
		  $scope.updateBill();
		  $scope.showProgressBar=false;
	  }
	  $scope.getTransactionResponse = function () {
		  $scope.showProgressBar=true;
		  $scope.promise = $apiurl.accounttransaction.get($scope.tquery, successBalance).$promise;
	  };
	  $scope.balanceOf = function(tId){
		  $scope.tquery = {
			type: 'account',
			id : tId,
			filter: '',
			limit: '10000',
			page: 1,
			order: 'id'
		  };
		  $scope.getTransactionResponse();
	  }
	  // to fetch balance
	  
	  // to fetch item
	  function successStockItems(response) {
	    $scope.Items= response;
	  }
	  $scope.getStockItems= function () {
		  $scope.promise = $apiurl.stockItems.get($scope.query, successStockItems).$promise;
	  };
	  $scope.getStockItems();
	  // to fetch item
	  
	  // to fetch branch
	  function successBranches(response) {
	    $scope.branches= response;
	  }
	  $scope.getBranches= function () {
		  $scope.promise = $apiurl.branches.get($scope.query, successBranches).$promise;
	  };
	  $scope.getBranches();
	  // to fetch branch
	  
	  // to fetch room
	  function successRoom(response) {
	    $scope.rooms= response;
	  }
	  $scope.getRooms= function(id) {
		  $scope.queryRoom = {
		    branch: 'branch',
		    id: id,
		    filter: '',
		    limit: '400',
		    page: 1,
		    order: 'id'
		  };
		  $scope.promise = $apiurl.room.get($scope.queryRoom, successRoom).$promise;
	  };
	  // to fetch room

	  
	  // to fetch pteType
	  function successPTEType(response) {
	    $scope.pteType= response;
	  }
	  $scope.getPTEType= function () {
		  $scope.promise = $apiurl.pteType.get($scope.query, successPTEType).$promise;
	  };
	  $scope.getPTEType();
	  // to fetch pteType
	  
	  // Calculation with item
	  $scope.totalQty = 0;
	  $scope.totalDiscount = 0;
	  $scope.totalAmount = 0;
	  $scope.total = 0;
	  $scope.subTotal = 0;
	  $scope.balance = 0;
	  $scope.grandTotal = 0;
	  $scope.dueAmount = 0;
	  $scope.paidAmount = 0;
	  
	  $scope.stock = [];
	  $scope.addStockItemField = function(){
		  $scope.stock.push($scope.stock.length+1);
		  
		  if($scope.stock[1]==null){
			  $scope.p_order.stockItems = [];
		  }
		  $scope.updateBill();
	  }
	  $scope.subtractStockItemField = function(){
		  $scope.p_order.stockItems.pop($scope.stock.length);
		  $scope.stock.pop($scope.stock.length);
		  $scope.updateBill();
	  }
	  $scope.subtractStockItemField = function(index){
		  console.log(index);
		  console.log($scope.p_order.stockItems[index]);
		  $scope.p_order.stockItems.splice(index, 1);
		  $scope.stock.pop(index+1);
		  $scope.updateBill();
	  }
	  $scope.updateTotalQty = function(){
		  $scope.totalQty = 0;
		  var i;
		  for(i=0; i<$scope.stock.length; i++){
			  $scope.totalQty = $scope.totalQty + $scope.p_order.stockItems[i].quantity;
		  }
	  }
	  $scope.updateTotalDiscount = function(){
		  $scope.totalDiscount = 0;
		  var i;
		  for(i=0; i<$scope.stock.length; i++){
			  $scope.totalDiscount = $scope.totalDiscount + ($scope.p_order.stockItems[i].rate*($scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;
		  }
	  }
	  $scope.updateTotalAmount= function(){
		  $scope.totalAmount = 0;
		  var i;
		  for(i=0; i<$scope.stock.length; i++){
			  $scope.totalAmount = $scope.totalAmount + ($scope.p_order.stockItems[i].rate*(1-$scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;
		  }
	  }
	  $scope.updateTotal= function(){
		  $scope.total = 0;
		  var i;
		  for(i=0; i<$scope.stock.length; i++){
			  $scope.total = $scope.total + $scope.p_order.stockItems[i].rate*$scope.p_order.stockItems[i].quantity;
		  }
	  }
	  $scope.updateSubTotal= function(){
		  $scope.subTotal = 0;
		  var i;
		  for(i=0; i<$scope.stock.length; i++){
			  $scope.subTotal = $scope.subTotal + ($scope.p_order.stockItems[i].rate*(1-$scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;
		  }
		  $scope.subTotal = $scope.subTotal + $scope.balance;
	  }
	  $scope.updateGrandTotal= function(){
		  $scope.grandTotal = 0;
		  var i;
		  for(i=0; i<$scope.stock.length; i++){
			  $scope.grandTotal = $scope.grandTotal + ($scope.p_order.stockItems[i].rate*(1-$scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;// Problem,
																																												// -
																																												// $scope.totalDiscount
		  }
		  $scope.grandTotal = $scope.grandTotal + $scope.balance;
	  }
	  $scope.updateDueAmount= function(){
		  $scope.dueAmount = 0;
		  if($scope.p_order.paidAmount==null){
			  $scope.p_order.paidAmount = 0;
		  }
		  var i;
		  for(i=0; i<$scope.stock.length; i++){
			  $scope.dueAmount = $scope.dueAmount + ($scope.p_order.stockItems[i].rate*(1-$scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;
		  }
		  $scope.dueAmount = $scope.dueAmount + $scope.balance - $scope.p_order.paidAmount;
	  }
	  
	  $scope.qType = function(value){
		  if(value==null){
			  $scope.step=0.001;
		  }else{
			  $scope.step=0.001;
		  }
	  }
	  $scope.updateBill= function(){
		  $scope.updateTotalQty();
		  $scope.updateTotalDiscount();
		  $scope.updateTotalAmount();
		  $scope.updateTotal();
		  $scope.updateSubTotal();
		  $scope.updateGrandTotal();
		  $scope.updateDueAmount();
	  }
	// Calculation with item
	  
//	  

	  $scope.viewPdf = function(id){
		  window.open('api/order/pdfVoucher/'+id); 
		  return false;
	  }
	  $scope.p_order = {orderDate: new Date()};
	  function success(response){
		  $mdDialog.hide(response);
		  console.log(response);
		  $scope.viewPdf(response.id);
	  }
	  this.addItem = function () {
		  $scope.p_order.totalAmount = $scope.totalAmount;
		  console.log($scope.p_order);
		  $scope.item.form.$setSubmitted();
		  if($scope.item.form.$valid) {
			  $apiurl.order.save($scope.p_order, success);
		  }
	  };
}]);

app.controller('viewPurchaseOrderController', ['$mdDialog', '$apiurl', '$scope', 'items', function ($mdDialog, $apiurl, $scope, items) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  console.log(items);
	  $scope.p_order = items;

	  // to fetch ledger accounts
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '400',
	    page: 1,
	    order: 'id'
	  };
	  function successLedger(response) {
	    $scope.partyLedger = response;
		  $scope.showProgressBar=false;
	  }
	  $scope.getLedgerResponse = function () {
		  $scope.showProgressBar=true;
		  $scope.promise = $apiurl.ledger.get($scope.query, successLedger).$promise;
	  };
	  $scope.getLedgerResponse();
	  // to fetch ledger accounts

	  // to fetch balance
	  function successBalance(response) {
		  if(response.content.length==0 ){
			  $scope.balance = 0.00;
			  $scope.showProgressBar=false;
		  }
		  for(var p=0;p<response.content.length;p++){
			  if(response.content[p][6]<items.id){
				  $scope.balance = $scope.balance+response.content[p][3];
			  }else if(response.content[p][6]==null){
				  $scope.balance = $scope.balance+response.content[p][3];
			  }
		  }
		  //$scope.balance= response.content[response.totalElements-1][4];
		  $scope.updateBill();
		  $scope.showProgressBar=false;
	  }
	  $scope.getTransactionResponse = function () {
		  $scope.showProgressBar=true;
		  $scope.promise = $apiurl.accounttransaction.get($scope.tquery, successBalance).$promise;
	  };
	  $scope.balanceOf = function(){
		  $scope.tquery = {
			type: 'account',
			id : $scope.p_order.party.id,
			filter: '',
			limit: '10000',
			page: 1,
			order: 'id'
		  };
		  $scope.getTransactionResponse();
	  }
	  // to fetch balance
	  
	  // to fetch item
	  function successStockItems(response) {
	    $scope.Items= response;
	  }
	  $scope.getStockItems= function () {
		  $scope.promise = $apiurl.stockItems.get($scope.query, successStockItems).$promise;
	  };
	  $scope.getStockItems();
	  // to fetch item
	  
	  // Calculation with item
	  $scope.totalQty = 0;
	  $scope.totalDiscount = 0;
	  $scope.totalAmount = 0;
	  $scope.total = 0;
	  $scope.subTotal = 0;
	  $scope.balance = 0;
	  $scope.grandTotal = 0;
	  $scope.dueAmount = 0;
	  $scope.paidAmount = 0;
	  
	  $scope.updateTotalQty = function(){
		  $scope.totalQty = 0;
		  var i;
		  for(i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.totalQty = $scope.totalQty + $scope.p_order.stockItems[i].quantity;
		  }
	  }
	  $scope.updateTotalDiscount = function(){
		  $scope.totalDiscount = 0;
		  var i;
		  for(i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.totalDiscount = $scope.totalDiscount + ($scope.p_order.stockItems[i].rate*($scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;
		  }
	  }
	  $scope.updateTotalAmount= function(){
		  $scope.totalAmount = 0;
		  var i;
		  for(i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.totalAmount = $scope.totalAmount + ($scope.p_order.stockItems[i].rate*(1-$scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;
		  }
	  }
	  $scope.updateTotal= function(){
		  $scope.total = 0;
		  var i;
		  for(i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.total = $scope.total + $scope.p_order.stockItems[i].rate*$scope.p_order.stockItems[i].quantity;
		  }
	  }
	  $scope.updateSubTotal= function(){
		  $scope.subTotal = 0;
		  var i;
		  for(i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.subTotal = $scope.subTotal + ($scope.p_order.stockItems[i].rate*(1-$scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;
		  }
		  $scope.subTotal = $scope.subTotal + $scope.balance;
	  }
	  $scope.updateGrandTotal= function(){
		  $scope.grandTotal = 0;
		  var i;
		  for(i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.grandTotal = $scope.grandTotal + ($scope.p_order.stockItems[i].rate*(1-$scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;// Problem,
																																												// -
																																												// $scope.totalDiscount
		  }
		  $scope.grandTotal = $scope.grandTotal + $scope.balance;
	  }
	  $scope.updateDueAmount= function(){
		  $scope.dueAmount = 0;
		  if($scope.p_order.paidAmount==null){
			  $scope.p_order.paidAmount = 0;
		  }
		  var i;
		  for(i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.dueAmount = $scope.dueAmount + ($scope.p_order.stockItems[i].rate*(1-$scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;
		  }
		  $scope.dueAmount = $scope.dueAmount + $scope.balance - $scope.p_order.paidAmount;
	  }
	  $scope.updateBill= function(){
		  $scope.updateTotalQty();
		  $scope.updateTotalDiscount();
		  $scope.updateTotalAmount();
		  $scope.updateTotal();
		  $scope.updateSubTotal();
		  $scope.updateGrandTotal();
		  $scope.updateDueAmount();
	  }
	// Calculation with item
	  $scope.balanceOf();
	  $scope.updateBill();
	  
}]);


app.controller('purchaseOrderController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$rootScope' ,'$log', function ($timeout, $mdDialog, $apiurl, $scope, $rootScope, $log) {
	  'use strict';
	  var bookmark;
	  $scope.selected = [];
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.itemCounter = 0;
	  $scope.startDate = new Date();
	  $scope.startDate.setFullYear(2000);
	  $scope.endDate = new Date();
	  $scope.query = {
		startDate: $scope.startDate,
		endDate: $scope.endDate,
		by: 'productTransactionType',
		id: 1,
	    filter: '',
	    limit: $rootScope.pageSize,
	    page: 1,
	    order: '-id',
	    filter: ''
	};
	  function success(response) {
	    $scope.response = response;
	  }
	  $scope.reLoadStuff = function () {
		$scope.promise = $timeout(function(){
			$scope.getResponse();
		}, 2000);
	  }
	  $scope.addItem = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'addPurchaseOrderController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      templateUrl: 'templates/org/order/add_purchase_order.html',
	    }).then($scope.getResponse);
	  };
	  $scope.viewItem = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'viewPurchaseOrderController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      locals:{ items : event },
	      templateUrl: 'templates/org/order/view_order.html',
	    }).then($scope.getResponse);
	  };
	  $scope.viewPdf = function(id){
		  window.open('api/order/pdfVoucher/'+id); 
		  return false;
	  }
	  
	  $scope.getResponse = function () {
	    $scope.promise = $apiurl.order.get($scope.query, success).$promise;
	    $scope.selected = [];
	  };

	  $scope.onChangeDate= function(){
		  $scope.startDate.setHours(0);
		  $scope.endDate.setHours(24);
		  $scope.query.startDate = $scope.startDate;
		  $scope.query.endDate = $scope.endDate;
		  $scope.getResponse();
	  };
		  
	 $scope.removeFilter = function () {
		 $scope.filter.show = false;
		 $scope.query.filter = '';
		 if($scope.filter.form.$dirty) {
			 $scope.filter.form.$setPristine();
		 }
	 };
			  
	 $scope.$watch('query.filter', function (newValue, oldValue) {
		 if(!oldValue) {
			 bookmark = $scope.query.page;
		 }
		 if(newValue !== oldValue) {
			 $scope.query.page = 1;
		 }
		 if(!newValue) {
			 $scope.query.page = bookmark;
		 }
		 $scope.getResponse();
	 });
}]);

// ********************** PURCHASE ORDER CONTROLLER PART
// ENDED**************************

// ********************** SALE ORDER CONTROLLER PART
// ENDED**************************

app.controller('addSaleOrderController', ['$mdDialog', '$apiurl', '$scope', function ($mdDialog, $apiurl, $scope) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
//
	  // to fetch ledger accounts
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '400',
	    page: 1,
	    order: 'id'
	  };
	  function successLedger(response) {
	    $scope.partyLedger = response;
		  $scope.showProgressBar=false;
	  }
	  $scope.getLedgerResponse = function () {
		  $scope.showProgressBar=true;
		  $scope.promise = $apiurl.ledger.get($scope.query, successLedger).$promise;
	  };
	  $scope.getLedgerResponse();
	  // to fetch ledger accounts
	  
	  // to fetch balance
	  function successBalance(response) {
		  if(response.content.length==0 ){
			  $scope.balance = 0.00;
			  $scope.showProgressBar=false;
		  }
		  $scope.balance= response.content[response.totalElements-1][4];
		  $scope.updateBill();
		  $scope.showProgressBar=false;
	  }
	  $scope.getTransactionResponse = function () {
		  $scope.showProgressBar=true;
		  $scope.promise = $apiurl.accounttransaction.get($scope.tquery, successBalance).$promise;
	  };
	  $scope.balanceOf = function(tId){
		  $scope.tquery = {
			type: 'account',
			id : tId,
			filter: '',
			limit: '10000',
			page: 1,
			order: 'id'
		  };
		  $scope.getTransactionResponse();
	  }
	  // to fetch balance
	  
	  // to fetch item
	  function successStockItems(response) {
	    $scope.Items= response;
	  }
	  $scope.getStockItems= function () {
		  $scope.promise = $apiurl.stockItems.get($scope.query, successStockItems).$promise;
	  };
	  $scope.getStockItems();
	  // to fetch item
	  
	  // to fetch branch
	  function successBranches(response) {
	    $scope.branches= response;
	  }
	  $scope.getBranches= function () {
		  $scope.promise = $apiurl.branches.get($scope.query, successBranches).$promise;
	  };
	  $scope.getBranches();
	  // to fetch branch
	  
	  // to fetch room
	  function successRoom(response) {
	    $scope.rooms= response;
	  }
	  $scope.getRooms= function(id) {
		  $scope.queryRoom = {
		    branch: 'branch',
		    id: id,
		    filter: '',
		    limit: '400',
		    page: 1,
		    order: 'id'
		  };
		  $scope.promise = $apiurl.room.get($scope.queryRoom, successRoom).$promise;
	  };
	  // to fetch room

	  
	  // to fetch pteType
	  function successPTEType(response) {
	    $scope.pteType= response;
	  }
	  $scope.getPTEType= function () {
		  $scope.promise = $apiurl.pteType.get($scope.query, successPTEType).$promise;
	  };
	  $scope.getPTEType();
	  // to fetch pteType
	  
	  // Calculation with item
	  $scope.totalQty = 0;
	  $scope.totalDiscount = 0;
	  $scope.totalAmount = 0;
	  $scope.total = 0;
	  $scope.subTotal = 0;
	  $scope.balance = 0;
	  $scope.grandTotal = 0;
	  $scope.dueAmount = 0;
	  $scope.paidAmount = 0;
	  
	  $scope.stock = [];
	  $scope.addStockItemField = function(){
		  $scope.stock.push($scope.stock.length+1);
		  
		  if($scope.stock[1]==null){
			  $scope.p_order.stockItems = [];
			  console.log("work");
		  }
		  $scope.updateBill();
	  }
	  $scope.subtractStockItemField = function(){
		  $scope.p_order.stockItems.pop($scope.stock.length);
		  $scope.stock.pop($scope.stock.length);
		  $scope.updateBill();
	  }
	  $scope.subtractStockItemField = function(index){
		  console.log(index);
		  console.log($scope.p_order.stockItems[index]);
		  $scope.p_order.stockItems.splice(index, 1);
		  $scope.stock.pop(index+1);
		  $scope.updateBill();
	  }
	  $scope.updateTotalQty = function(){
		  $scope.totalQty = 0;
		  var i;
		  for(i=0; i<$scope.stock.length; i++){
			  $scope.totalQty = $scope.totalQty + $scope.p_order.stockItems[i].quantity;
		  }
	  }
	  $scope.updateTotalDiscount = function(){
		  $scope.totalDiscount = 0;
		  var i;
		  for(i=0; i<$scope.stock.length; i++){
			  $scope.totalDiscount = $scope.totalDiscount + ($scope.p_order.stockItems[i].rate*($scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;
		  }
	  }
	  $scope.updateTotalAmount= function(){
		  $scope.totalAmount = 0;
		  var i;
		  for(i=0; i<$scope.stock.length; i++){
			  $scope.totalAmount = $scope.totalAmount + ($scope.p_order.stockItems[i].rate*(1-$scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;
		  }
	  }
	  $scope.updateTotal= function(){
		  $scope.total = 0;
		  var i;
		  for(i=0; i<$scope.stock.length; i++){
			  $scope.total = $scope.total + $scope.p_order.stockItems[i].rate*$scope.p_order.stockItems[i].quantity;
		  }
	  }
	  $scope.updateSubTotal= function(){
		  $scope.subTotal = 0;
		  var i;
		  for(i=0; i<$scope.stock.length; i++){
			  $scope.subTotal = $scope.subTotal + ($scope.p_order.stockItems[i].rate*(1-$scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;
		  }
		  $scope.subTotal = $scope.subTotal + $scope.balance;
	  }
	  $scope.updateGrandTotal= function(){
		  $scope.grandTotal = 0;
		  var i;
		  for(i=0; i<$scope.stock.length; i++){
			  $scope.grandTotal = $scope.grandTotal + ($scope.p_order.stockItems[i].rate*(1-$scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;// Problem,
																																												// -
																																												// $scope.totalDiscount
		  }
		  $scope.grandTotal = $scope.grandTotal + $scope.balance;
	  }
	  $scope.updateDueAmount= function(){
		  $scope.dueAmount = 0;
		  if($scope.p_order.paidAmount==null){
			  $scope.p_order.paidAmount = 0;
		  }
		  var i;
		  for(i=0; i<$scope.stock.length; i++){
			  $scope.dueAmount = $scope.dueAmount + ($scope.p_order.stockItems[i].rate*(1-$scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;
		  }
		  $scope.dueAmount = $scope.dueAmount + $scope.balance - $scope.p_order.paidAmount;
	  }
	  
	  $scope.qType = function(value){
		  if(value==null){
			  $scope.step=0.001;

		  }else{
			  $scope.step=0.001;

		  }
	  }
	  $scope.updateBill= function(){
		  $scope.updateTotalQty();
		  $scope.updateTotalDiscount();
		  $scope.updateTotalAmount();
		  $scope.updateTotal();
		  $scope.updateSubTotal();
		  $scope.updateGrandTotal();
		  $scope.updateDueAmount();
	  }
	// Calculation with item
	  
//	  

	  $scope.p_order = {orderDate: new Date()};
	  function success(response){
		  $mdDialog.hide(response);
	  }
	  this.addItem = function () {
		  $scope.p_order.totalAmount = $scope.totalAmount;
		  console.log($scope.p_order);
		  $scope.item.form.$setSubmitted();
		  if($scope.item.form.$valid) {
			  $apiurl.order.save($scope.p_order, success);
		  }
	  };
}]);

app.controller('viewSaleOrderController', ['$mdDialog', '$apiurl', '$scope', 'items', function ($mdDialog, $apiurl, $scope, items) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;
	  $scope.p_order = items;
	  
	  // to fetch ledger accounts
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '400',
	    page: 1,
	    order: 'id'
	  };
	  function successLedger(response) {
	    $scope.partyLedger = response;
		  $scope.showProgressBar=false;
	  }
	  $scope.getLedgerResponse = function () {
		  $scope.showProgressBar=true;
		  $scope.promise = $apiurl.ledger.get($scope.query, successLedger).$promise;
	  };
	  $scope.getLedgerResponse();
	  // to fetch ledger accounts

	  // to fetch balance
	  function successBalance(response) {
		  if(response.content.length==0 ){
			  $scope.balance = 0.00;
			  $scope.showProgressBar=false;
		  }
		  for(var p=0;p<response.content.length;p++){
			  if(response.content[p][6]<items.id){
				  $scope.balance = $scope.balance+response.content[p][3];
			  }else if(response.content[p][6]==null){
				  $scope.balance = $scope.balance+response.content[p][3];
			  }
		  }
		  //$scope.balance= response.content[response.totalElements-1][4];
		  $scope.updateBill();
		  $scope.showProgressBar=false;
	  }
	  $scope.getTransactionResponse = function () {
		  $scope.showProgressBar=true;
		  $scope.promise = $apiurl.accounttransaction.get($scope.tquery, successBalance).$promise;
	  };
	  $scope.balanceOf = function(){
		  $scope.tquery = {
			type: 'account',
			id : $scope.p_order.party.id,
			filter: '',
			limit: '10000',
			page: 1,
			order: 'id'
		  };
		  $scope.getTransactionResponse();
	  }
	  // to fetch balance
	  
	  // to fetch item
	  function successStockItems(response) {
	    $scope.Items= response;
	  }
	  $scope.getStockItems= function () {
		  $scope.promise = $apiurl.stockItems.get($scope.query, successStockItems).$promise;
	  };
	  $scope.getStockItems();
	  // to fetch item
	  
	  // Calculation with item
	  $scope.totalQty = 0;
	  $scope.totalDiscount = 0;
	  $scope.totalAmount = 0;
	  $scope.total = 0;
	  $scope.subTotal = 0;
	  $scope.balance = 0;
	  $scope.grandTotal = 0;
	  $scope.dueAmount = 0;
	  $scope.paidAmount = 0;
	  
	  $scope.updateTotalQty = function(){
		  $scope.totalQty = 0;
		  var i;
		  for(i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.totalQty = $scope.totalQty + $scope.p_order.stockItems[i].quantity*(-1);
		  }
		  console.log($scope.totalQty);
	  }
	  $scope.updateTotalDiscount = function(){
		  $scope.totalDiscount = 0;
		  var i;
		  for(i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.totalDiscount = $scope.totalDiscount + ($scope.p_order.stockItems[i].rate*($scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;
		  }
	  } 
	  $scope.updateTotalAmount= function(){
		  $scope.totalAmount = 0;
		  var i;
		  for(i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.totalAmount = $scope.totalAmount + ($scope.p_order.stockItems[i].rate*(1-$scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;
		  }
	  }
	  $scope.updateTotal= function(){
		  $scope.total = 0;
		  var i;
		  for(i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.total = $scope.total + $scope.p_order.stockItems[i].rate*$scope.p_order.stockItems[i].quantity;
		  }
	  }
	  $scope.updateSubTotal= function(){
		  $scope.subTotal = 0;
		  var i;
		  for(i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.subTotal = $scope.subTotal + ($scope.p_order.stockItems[i].rate*(1-$scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;
		  }
		  $scope.subTotal = $scope.subTotal + $scope.balance;
	  }
	  $scope.updateGrandTotal= function(){
		  $scope.grandTotal = 0;
		  var i;
		  for(i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.grandTotal = $scope.grandTotal + ($scope.p_order.stockItems[i].rate*(1-$scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;// Problem,
																																												// -
																																												// $scope.totalDiscount
		  }
		  $scope.grandTotal = $scope.grandTotal + $scope.balance;
	  }
	  $scope.updateDueAmount= function(){
		  $scope.dueAmount = 0;
		  if($scope.p_order.paidAmount==null){
			  $scope.p_order.paidAmount = 0;
		  }
		  var i;
		  for(i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.dueAmount = $scope.dueAmount + ($scope.p_order.stockItems[i].rate*(1-$scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;
		  }
		  $scope.dueAmount = $scope.dueAmount + $scope.balance - $scope.p_order.paidAmount;
	  }
	  $scope.updateBill= function(){
		  $scope.updateTotalQty();
		  $scope.updateTotalDiscount();
		  $scope.updateTotalAmount();
		  $scope.updateTotal();
		  $scope.updateSubTotal();
		  $scope.updateGrandTotal();
		  $scope.updateDueAmount();
	  }
	// Calculation with item

	  $scope.balanceOf();
	  $scope.updateBill();
	  
}]);

app.controller('saleOrderController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$log', function ($timeout, $mdDialog, $apiurl, $scope, $log) {
	  'use strict';
	  var bookmark;
	  $scope.selected = [];
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.startDate = new Date();
	  $scope.startDate.setFullYear(2000);
	  $scope.endDate = new Date();
	  $scope.endDate.setHours(24);
	  $scope.query = {
		startDate: $scope.startDate,
		endDate: $scope.endDate,
		by: 'productTransactionType',
		id: 2,
	    filter: '',
	    limit: '10',
	    page: 1,
	    order: '-id'
	  };
	  function success(response) {
	    $scope.response = response;
	  }
	  $scope.reLoadStuff = function () {
		    $scope.promise = $timeout(function(){
		    	$scope.getResponse();
		    }, 2000);
		  }
	  $scope.addItem = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'addSaleOrderController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      templateUrl: 'templates/org/order/add_sale_order.html',
	    }).then($scope.getResponse);
	  };
	  $scope.viewItem = function (event) {
		    $mdDialog.show({
		      clickOutsideToClose: true,
		      controller: 'viewSaleOrderController',
		      controllerAs: 'ctrl',
		      focusOnOpen: false,
		      targetEvent: event,
		      locals:{ items : event },
		      templateUrl: 'templates/org/order/view_order.html',
		    }).then($scope.getResponse);
		  };

	  $scope.viewPdf = function(id){
		  window.open('api/order/pdfVoucher/'+id);
		  return false;
	  }
	  $scope.viewChalan = function(id){
		  window.open('api/order/pdfVoucher/'+id+"?ship=true"); 
		  return false;
	  }
	  $scope.getResponse = function () {
	    $scope.promise = $apiurl.order.get($scope.query, success).$promise;
	    $scope.selected = [];
	  };
	  $scope.getResponse();

	  $scope.onChangeDate= function(){
		  $scope.startDate.setHours(0);
		  $scope.endDate.setHours(24);
		  $scope.query.startDate = $scope.startDate;
		  $scope.query.endDate = $scope.endDate;
		  $scope.getResponse();
	  }

	 $scope.removeFilter = function () {
		 $scope.filter.show = false;
		 $scope.query.filter = '';
		 if($scope.filter.form.$dirty) {
			 $scope.filter.form.$setPristine();
		 }
	 };
			  
	 $scope.$watch('query.filter', function (newValue, oldValue) {
		 if(!oldValue) {
			 bookmark = $scope.query.page;
		 }
		 if(newValue !== oldValue) {
			 $scope.query.page = 1;
		 }
		 if(!newValue) {
			 $scope.query.page = bookmark;
		 }
		 $scope.getResponse();
	 });
}]);

app.controller('addCancelPurchaseOrderController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$log', function ($timeout, $mdDialog, $apiurl, $scope, $log) {
	  'use strict';
	  console.log("Hello");
	  var bookmark;
	  $scope.selected = [];
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.typeQuery = {
		by: 'productTransactionType',
		id: 1,
	    filter: '',
	    limit: '999999999',
	    page: 1,
	    order: 'id'
	  };
	  function success(response) {
	    $scope.response = response;
	  }
	  $scope.reLoadStuff = function () {
		    $scope.promise = $timeout(function(){
		    	$scope.getResponse();
		    }, 2000);
		  }

	  $scope.getResponse = function () {
	    $scope.promise = $apiurl.order.get($scope.typeQuery, success).$promise;
	  };
	  $scope.getResponse();
	  
	  $scope.orderDate= new Date();
	  console.log($scope.orderDate);
	  
	  
	  $scope.onItemClick = function(items){
		  console.log(items)
		  $scope.p_order = items;
		  $scope.balanceOf();
		  $scope.updateBill();
	  }

// ///////////////////////////////////// Save Order

	  'use strict';

	  // to fetch ledger accounts
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '400',
	    page: 1,
	    order: 'id'
	  };
	  function successLedger(response) {
	    $scope.partyLedger = response;
		  $scope.showProgressBar=false;
	  }
	  $scope.getLedgerResponse = function () {
		  $scope.showProgressBar=true;
		  $scope.promise = $apiurl.ledger.get($scope.query, successLedger).$promise;
	  };
	  $scope.getLedgerResponse();
	  // to fetch ledger accounts
	  
	  // to fetch balance
	  function successBalance(response) {
		  if(response.content.length==0 ){
			  $scope.balance = 0.00;
			  $scope.showProgressBar=false;
		  }
		  $scope.balance= response.content[response.totalElements-1][4];
		  $scope.updateBill();
		  $scope.showProgressBar=false;
	  }
	  $scope.getTransactionResponse = function () {
		  $scope.showProgressBar=true;
		  $scope.promise = $apiurl.accounttransaction.get($scope.tquery, successBalance).$promise;
	  };
	  $scope.balanceOf = function(tId){
		  $scope.tquery = {
			type: 'account',
			id : $scope.p_order.party.id,
			filter: '',
			limit: '10000',
			page: 1,
			order: 'id'
		  };
		  $scope.getTransactionResponse();
	  }
	  // to fetch balance
	  
	  // to fetch item
	  function successStockItems(response) {
	    $scope.Items= response;
	  }
	  $scope.getStockItems= function () {
		  $scope.promise = $apiurl.stockItems.get($scope.query, successStockItems).$promise;
	  };
	  $scope.getStockItems();
	  // to fetch item
	  
	  // to fetch branch
	  function successBranches(response) {
	    $scope.branches= response;
	  }
	  $scope.getBranches= function () {
		  $scope.promise = $apiurl.branches.get($scope.query, successBranches).$promise;
	  };
	  $scope.getBranches();
	  // to fetch branch
	  
	  // to fetch room
	  function successRoom(response) {
	    $scope.rooms= response;
	  }
	  $scope.getRooms= function(id) {
		  $scope.queryRoom = {
		    filter: '',
		    limit: '400',
		    page: 1,
		    order: 'id'
		  };
		  $scope.promise = $apiurl.room.get($scope.queryRoom, successRoom).$promise;
	  };
	  $scope.getRooms();
	  // to fetch room

	  
	  // to fetch pteType
	  function successPTEType(response) {
	    $scope.pteType= response;
	  }
	  $scope.getPTEType= function () {
		  $scope.promise = $apiurl.pteType.get($scope.query, successPTEType).$promise;
	  };
	  $scope.getPTEType();
	  // to fetch pteType
	  
	  // Calculation with item
	  $scope.totalQty = 0;
	  $scope.totalDiscount = 0;
	  $scope.totalAmount = 0;
	  $scope.total = 0;
	  $scope.subTotal = 0;
	  $scope.balance = 0;
	  $scope.grandTotal = 0;
	  $scope.dueAmount = 0;
	  $scope.paidAmount = 0;
	  
	  $scope.updateTotalQty = function(){
		  $scope.totalQty = 0;
		  var i;
		  for(i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.totalQty = $scope.totalQty + $scope.p_order.stockItems[i].quantity;
		  }
	  }
	  $scope.updateTotalDiscount = function(){
		  $scope.totalDiscount = 0;
		  var i;
		  for(i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.totalDiscount = $scope.totalDiscount + ($scope.p_order.stockItems[i].rate*($scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;
		  }
	  }
	  $scope.updateTotalAmount= function(){
		  $scope.totalAmount = 0;
		  var i;
		  for(i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.totalAmount = $scope.totalAmount + ($scope.p_order.stockItems[i].rate*(1-$scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;
		  }
	  }
	  $scope.updateTotal= function(){
		  $scope.total = 0;
		  var i;
		  for(i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.total = $scope.total + $scope.p_order.stockItems[i].rate*$scope.p_order.stockItems[i].quantity;
		  }
	  }
	  $scope.updateSubTotal= function(){
		  $scope.subTotal = 0;
		  var i;
		  for(i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.subTotal = $scope.subTotal + ($scope.p_order.stockItems[i].rate*(1-$scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;
		  }
		  $scope.subTotal = $scope.subTotal + $scope.balance;
	  }
	  $scope.updateGrandTotal= function(){
		  $scope.grandTotal = 0;
		  var i;
		  for(i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.grandTotal = $scope.grandTotal + ($scope.p_order.stockItems[i].rate*(1-$scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;// Problem,
																																												// -
																																												// $scope.totalDiscount
		  }
		  $scope.grandTotal = $scope.grandTotal + $scope.balance;
	  }
	  $scope.updateDueAmount= function(){
		  $scope.dueAmount = 0;
		  if($scope.p_order.paidAmount==null){
			  $scope.p_order.paidAmount = 0;
		  }
		  var i;
		  for(i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.dueAmount = $scope.dueAmount + ($scope.p_order.stockItems[i].rate*(1-$scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;
		  }
		  $scope.dueAmount = $scope.dueAmount + $scope.balance - $scope.p_order.paidAmount;
	  }
	  
	  $scope.qType = function(value){
		  if(value==null){
			  $scope.step=1;

		  }else{
			  $scope.step=0.001;
	
		  }
	  }
	  $scope.updateBill= function(){
		  $scope.updateTotalQty();
		  $scope.updateTotalDiscount();
		  $scope.updateTotalAmount();
		  $scope.updateTotal();
		  $scope.updateSubTotal();
		  $scope.updateGrandTotal();
		  $scope.updateDueAmount();
	  }
	// Calculation with item

	  $scope.cancelOrderDate =  new Date();
	  function successAdd(response){
		  $mdDialog.hide(response);
// $mdDialog.show(
// $mdDialog.alert()
// .parent(angular.element(document.querySelector('#popupContainer')))
// .clickOutsideToClose(true)
// .title('Purchase order canceled!')
// .textContent('Purchase order canceled successfull!.')
// .ariaLabel('Alert Dialog Demo')
// .ok('Got it!')
// .targetEvent(response)
// );
// $scope.reLoadStuff();
// console.log("success!")
	  }

	  function fail(reason){
		  $mdDialog.show(
		      $mdDialog.alert()
		        .parent(angular.element(document.querySelector('#popupContainer')))
		        .clickOutsideToClose(true)
		        .title('Purchase order isn\'t canceled!')
		        .textContent('Failed to cancel Purchase order!')
		        .ariaLabel('Alert Dialog Demo')
		        .ok('Got it!')
		        .targetEvent(reason)
		    );
	  }

	  $scope.addItem = function () {
		  for(var i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.p_order.stockItems[i].stockItem = {id: $scope.p_order.stockItems[i].stockItem.id};
			  $scope.p_order.stockItems[i].id = null;
			  if($scope.p_order.stockItems[i].batch){
				  $scope.p_order.stockItems[i].batch.id = null;
			  }
			  
		  }
		  $scope.p_order.orderDate = $scope.cancelOrderDate;
		  $scope.p_order.product = null;

		  $scope.p_order.id = null;
		  $scope.p_order.totalAmount = $scope.totalAmount-$scope.totalDiscount;
		  console.log($scope.p_order);
		  $scope.item.form.$setSubmitted();
			  $apiurl.order.save($scope.p_order, successAdd, fail);
	  };
	  
}]);

app.controller('cancelPurchaseOrderController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$log', function ($timeout, $mdDialog, $apiurl, $scope, $log) {
	  'use strict';
	  var bookmark;
	  $scope.selected = [];
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
		by: 'productTransactionType',
		id: 5,
	    filter: '',
	    limit: '10',
	    page: 1,
	    order: '-id'
	  };
	  function success(response) {
	    $scope.response = response;
	  }
	  $scope.reLoadStuff = function () {
		    $scope.promise = $timeout(function(){
		    	$scope.getResponse();
		    }, 2000);
		  }
	  $scope.addItem = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'addCancelPurchaseOrderController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      templateUrl: 'templates/org/order/add_cancel_purchase_order.html',
	    }).then($scope.getResponse);
	  };
	  $scope.viewItem = function (event) {
		    $mdDialog.show({
		      clickOutsideToClose: true,
		      controller: 'viewSaleOrderController',
		      controllerAs: 'ctrl',
		      focusOnOpen: false,
		      targetEvent: event,
		      locals:{ items : event },
		      templateUrl: 'templates/org/order/view_order.html',
		    }).then($scope.getResponse);
		  };

	  $scope.getResponse = function () {
	    $scope.promise = $apiurl.order.get($scope.query, success).$promise;
	    $scope.selected = [];
	  };
	  $scope.getResponse();

}]);
app.controller('addCancelSaleOrderController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$log', function ($timeout, $mdDialog, $apiurl, $scope, $log) {
	'use strict';
	  console.log("Hello");
	  var bookmark;
	  $scope.selected = [];
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.typeQuery = {
		by: 'productTransactionType',
		id: 2,
	    filter: '',
	    limit: '999999999',
	    page: 1,
	    order: 'id'
	  };
	  function success(response) {
	    $scope.response = response;
	  }
	  $scope.reLoadStuff = function () {
		    $scope.promise = $timeout(function(){
		    	$scope.getResponse();
		    }, 2000);
		  }

	  $scope.getResponse = function () {
	    $scope.promise = $apiurl.order.get($scope.typeQuery, success).$promise;
	  };
	  $scope.getResponse();
	  
	  $scope.orderDate= new Date();
	  console.log($scope.orderDate);
	  
	  
	  $scope.onItemClick = function(items){
		  $scope.p_order = items;
		  $scope.balanceOf();
		  $scope.updateBill();
	  }

// ///////////////////////////////////// Save Order
	  
	  // to fetch ledger accounts
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '400',
	    page: 1,
	    order: 'id'
	  };
	  function successLedger(response) {
	    $scope.partyLedger = response;
		  $scope.showProgressBar=false;
	  }
	  $scope.getLedgerResponse = function () {
		  $scope.showProgressBar=true;
		  $scope.promise = $apiurl.ledger.get($scope.query, successLedger).$promise;
	  };
	  $scope.getLedgerResponse();
	  // to fetch ledger accounts
	  
	  // to fetch balance
	  function successBalance(response) {
		  if(response.content.length==0 ){
			  $scope.balance = 0.00;
			  $scope.showProgressBar=false;
		  }
		  $scope.balance= response.content[response.totalElements-1][4];
		  $scope.updateBill();
		  $scope.showProgressBar=false;
	  }
	  $scope.getTransactionResponse = function () {
		  $scope.showProgressBar=true;
		  $scope.promise = $apiurl.accounttransaction.get($scope.tquery, successBalance).$promise;
	  };
	  $scope.balanceOf = function(tId){
		  $scope.tquery = {
			type: 'account',
			id : $scope.p_order.party.id,
			filter: '',
			limit: '10000',
			page: 1,
			order: 'id'
		  };
		  $scope.getTransactionResponse();
	  }
	  // to fetch balance
	  
	  // to fetch item
	  function successStockItems(response) {
	    $scope.Items= response;
	  }
	  $scope.getStockItems= function () {
		  $scope.promise = $apiurl.stockItems.get($scope.query, successStockItems).$promise;
	  };
	  $scope.getStockItems();
	  // to fetch item
	  
	  // to fetch branch
	  function successBranches(response) {
	    $scope.branches= response;
	  }
	  $scope.getBranches= function () {
		  $scope.promise = $apiurl.branches.get($scope.query, successBranches).$promise;
	  };
	  $scope.getBranches();
	  // to fetch branch
	  
	  // to fetch room
	  function successRoom(response) {
	    $scope.rooms= response;
	  }
	  $scope.getRooms= function(id) {
		  $scope.queryRoom = {
		    filter: '',
		    limit: '400',
		    page: 1,
		    order: 'id'
		  };
		  $scope.promise = $apiurl.room.get($scope.queryRoom, successRoom).$promise;
	  };
	  $scope.getRooms();
	  // to fetch room

	  
	  // to fetch pteType
	  function successPTEType(response) {
	    $scope.pteType= response;
	  }
	  $scope.getPTEType= function () {
		  $scope.promise = $apiurl.pteType.get($scope.query, successPTEType).$promise;
	  };
	  $scope.getPTEType();
	  // to fetch pteType
	  
	  // Calculation with item
	  $scope.totalQty = 0;
	  $scope.totalDiscount = 0;
	  $scope.totalAmount = 0;
	  $scope.total = 0;
	  $scope.subTotal = 0;
	  $scope.balance = 0;
	  $scope.grandTotal = 0;
	  $scope.dueAmount = 0;
	  $scope.paidAmount = 0;
	  
	  $scope.updateTotalQty = function(){
		  $scope.totalQty = 0;
		  console.log($scope.totalQty);
		  var i;
		  for(i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.totalQty = $scope.totalQty + $scope.p_order.stockItems[i].quantity;
		  }
		  console.log($scope.totalQty);
	  }
	  $scope.updateTotalDiscount = function(){
		  $scope.totalDiscount = 0;
		  var i;
		  for(i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.totalDiscount = $scope.totalDiscount + ($scope.p_order.stockItems[i].rate*($scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;
		  }
	  }
	  $scope.updateTotalAmount= function(){
		  $scope.totalAmount = 0;
		  var i;
		  for(i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.totalAmount = $scope.totalAmount + ($scope.p_order.stockItems[i].rate*(1-$scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;
		  }
	  }
	  $scope.updateTotal= function(){
		  $scope.total = 0;
		  var i;
		  for(i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.total = $scope.total + $scope.p_order.stockItems[i].rate*$scope.p_order.stockItems[i].quantity;
		  }
	  }
	  $scope.updateSubTotal= function(){
		  $scope.subTotal = 0;
		  var i;
		  for(i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.subTotal = $scope.subTotal + ($scope.p_order.stockItems[i].rate*(1-$scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;
		  }
		  $scope.subTotal = $scope.subTotal + $scope.balance;
	  }
	  $scope.updateGrandTotal= function(){
		  $scope.grandTotal = 0;
		  var i;
		  for(i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.grandTotal = $scope.grandTotal + ($scope.p_order.stockItems[i].rate*(1-$scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;// Problem,
																																												// -
																																												// $scope.totalDiscount
		  }
		  $scope.grandTotal = $scope.grandTotal + $scope.balance;
	  }
	  $scope.updateDueAmount= function(){
		  $scope.dueAmount = 0;
		  if($scope.p_order.paidAmount==null){
			  $scope.p_order.paidAmount = 0;
		  }
		  var i;
		  for(i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.dueAmount = $scope.dueAmount + ($scope.p_order.stockItems[i].rate*(1-$scope.p_order.stockItems[i].discount/100))*$scope.p_order.stockItems[i].quantity;
		  }
		  $scope.dueAmount = $scope.dueAmount + $scope.balance - $scope.p_order.paidAmount;
	  }
	  
	  $scope.qType = function(value){
		  if(value==null){
			  $scope.step=1;

		  }else{
			  $scope.step=0.001;
	
		  }
	  }
	  $scope.updateBill= function(){
		  for(var i=0; i<$scope.p_order.stockItems.length; i++){
			  if($scope.p_order.stockItems[i].quantity<1){
				  $scope.p_order.stockItems[i].quantity = $scope.p_order.stockItems[i].quantity*(-1);
			  }
		  }
		  $scope.updateTotalQty();
		  $scope.updateTotalDiscount();
		  $scope.updateTotalAmount();
		  $scope.updateTotal();
		  $scope.updateSubTotal();
		  $scope.updateGrandTotal();
		  $scope.updateDueAmount();
	  }
	// Calculation with item

	  $scope.cancelOrderDate =  new Date();
	  function successAdd(response){
		  $mdDialog.hide(response);
// $mdDialog.show(
// $mdDialog.alert()
// .parent(angular.element(document.querySelector('#popupContainer')))
// .clickOutsideToClose(true)
// .title('Purchase order canceled!')
// .textContent('Purchase order canceled successfull!.')
// .ariaLabel('Alert Dialog Demo')
// .ok('Got it!')
// .targetEvent(response)
// );
// $scope.reLoadStuff();
// console.log("success!")
	  }

	  function fail(reason){
		  $mdDialog.show(
		      $mdDialog.alert()
		        .parent(angular.element(document.querySelector('#popupContainer')))
		        .clickOutsideToClose(true)
		        .title('Sale order isn\'t canceled!')
		        .textContent('Failed to cancel Sale order!')
		        .ariaLabel('Alert Dialog Demo')
		        .ok('Got it!')
		        .targetEvent(reason)
		    );
	  }

	  $scope.addItem = function () {
		  for(var i=0; i<$scope.p_order.stockItems.length; i++){
			  $scope.p_order.stockItems[i].stockItem = {id: $scope.p_order.stockItems[i].stockItem.id};
			  $scope.p_order.stockItems[i].id = null;
			  // $scope.p_order.stockItems[i].batch.id = null;
		  }
		  $scope.p_order.orderDate = $scope.cancelOrderDate;
		  $scope.p_order.product = null;

		  $scope.p_order.id = null;
		  $scope.p_order.totalAmount = $scope.totalAmount-$scope.totalDiscount;
		  console.log($scope.p_order);
		  $scope.item.form.$setSubmitted();
			  $apiurl.order.save($scope.p_order, successAdd, fail);
	  };
}]);

app.controller('cancelSaleOrderController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$log', function ($timeout, $mdDialog, $apiurl, $scope, $log) {
	  'use strict';
	  var bookmark;
	  $scope.selected = [];
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
		by: 'productTransactionType',
		id: 6,
	    filter: '',
	    limit: '10',
	    page: 1,
	    order: '-id'
	  };
	  function success(response) {
	    $scope.response = response;
	  }
	  $scope.reLoadStuff = function () {
		    $scope.promise = $timeout(function(){
		    	$scope.getResponse();
		    }, 2000);
		  }
	  $scope.addItem = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'addCancelSaleOrderController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      templateUrl: 'templates/org/order/add_cancel_sale_order.html',
	    }).then($scope.getResponse);
	  };
	  $scope.viewItem = function (event) {
		    $mdDialog.show({
		      clickOutsideToClose: true,
		      controller: 'viewSaleOrderController',
		      controllerAs: 'ctrl',
		      focusOnOpen: false,
		      targetEvent: event,
		      locals:{ items : event },
		      templateUrl: 'templates/org/order/view_order.html',
		    }).then($scope.getResponse);
		  };

	  $scope.getResponse = function () {
	    $scope.promise = $apiurl.order.get($scope.query, success).$promise;
	    $scope.selected = [];
	  };
	  $scope.getResponse();

}]);
app.controller('addStockInController', ['$mdDialog', '$apiurl', '$scope', function ($mdDialog, $apiurl, $scope) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;

	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '400',
	    page: 1,
	    order: 'id'
	  };
	  
	  // to fetch branch
	  function successBranches(response) {
	    $scope.branches= response;
	  }
	  $scope.getBranches= function () {
		  $scope.promise = $apiurl.branches.get($scope.query, successBranches).$promise;
	  };
	  $scope.getBranches();
	  // to fetch ledger
	  function successLedger(response) {
			$scope.partyLedger = response;
			$scope.showProgressBar=false;
	  }
	  $scope.getLedgerResponse = function () {
		  $scope.showProgressBar=true;
		  $scope.promise = $apiurl.ledger.get($scope.query, successLedger).$promise;
	  };
	  $scope.getLedgerResponse();

	  // to fetch pteType
	  function successPTEType(response) {
	    $scope.pteType= response;
	  }
	  $scope.getPTEType= function () {
		  $scope.promise = $apiurl.pteType.get($scope.query, successPTEType).$promise;
	  };
	  $scope.getPTEType();
	  // to fetch pteType

	  // to fetch item
	  function successStockItems(response) {
	    $scope.Items= response;
	  }
	  $scope.getStockItems= function () {
		  $scope.promise = $apiurl.stockItems.get($scope.query, successStockItems).$promise;
	  };
	  $scope.getStockItems();
	  // to fetch item

	  $scope.qType = function(value){
		  if(value==null){
			  $scope.step=0.01;

		  }else{
			  $scope.step=0.001;
	
		  }
	  }

	  $scope.stock = [];
	  $scope.addStockItemField = function(){
		  $scope.stock.push($scope.stock.length+1);
		  
		  if($scope.stock[1]==null){
			  $scope.p_order.stockItems = [];
			  console.log("work");
		  }
	  }
	  $scope.subtractStockItemField = function(){
		  console.log($scope.stock.length);
		  $scope.p_order.stockItems.pop($scope.stock.length);
		  $scope.stock.pop($scope.stock.length);
	  }
	  $scope.subtractStockItemFieldById = function(index){
		  console.log(index);
		  console.log($scope.p_order.stockItems[index]);
		  $scope.p_order.stockItems.splice(index, 1);
		  $scope.stock.pop(index+1);
	  }
	  $scope.p_order = {orderDate: new Date()};

	  $scope.updateTotalAmount= function(){
		  $scope.totalAmount = 0;
		  var i;
		  for(i=0; i<$scope.stock.length; i++){
			  $scope.totalAmount = $scope.totalAmount + ($scope.p_order.stockItems[i].rate*$scope.p_order.stockItems[i].quantity);
		  }
		  $scope.p_order.totalAmount = $scope.totalAmount;
	  }
	  
	  function success(response){
		  $mdDialog.hide(response);
	  }
	  this.addItem = function () {
		  $scope.updateTotalAmount();
		  $scope.p_order.paidAmount = 0;
		  console.log($scope.totalAmount);
		  console.log($scope.p_order);
		  $scope.item.form.$setSubmitted();
		  if($scope.item.form.$valid) {
			  $apiurl.order.save($scope.p_order, success);
		  }
	  };
}]);

app.controller('updateStockInController', ['$mdDialog', '$apiurl', '$scope', 'items', function ($mdDialog, $apiurl, $scope, items) {

}]);

app.controller('deleteStockInController', ['items', '$mdDialog', '$apiurl', '$scope', '$q', '$log','$rootScope', function (items, $mdDialog, $apiurl, $scope, $q, $log, $rootScope) {

}]);
app.controller('stockInController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$log', function ($timeout, $mdDialog, $apiurl, $scope, $log) {
	  'use strict';
	  var bookmark;
	  $scope.selected = [];
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
		by: 'productTransactionType',
		id: 3,
	    filter: '',
	    limit: '10',
	    page: 1,
	    order: '-id'
	};
	  function success(response) {
	    $scope.response = response;
	  }
	  $scope.reLoadStuff = function () {
		    $scope.promise = $timeout(function(){
		    	$scope.getResponse();
		    }, 2000);
		  }
	  $scope.addItem = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'addStockInController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      templateUrl: 'templates/org/order/add_stock_in.html',
	    }).then($scope.getResponse);
	  };
// $scope.updateItem = function (event) {
// $mdDialog.show({
// clickOutsideToClose: true,
// controller: 'updateStockItemController',
// controllerAs: 'ctrl',
// focusOnOpen: false,
// targetEvent: event,
// locals:{ items : event },
// templateUrl: 'templates/org/stock/update_stock_item.html',
// }).then($scope.getResponse);
// };
// $scope.delete = function (event) {
// $mdDialog.show({
// clickOutsideToClose: true,
// controller: 'deleteStockItemController',
// controllerAs: 'ctrl',
// focusOnOpen: false,
// targetEvent: event,
// locals: { items: $scope.selected },
// templateUrl: 'templates/employee/delete_confirm_dialog.html',
// }).then($scope.getResponse);
// };
//	  
// $scope.deleteSingleRow = function (singleRowId) {
// $scope.id = [{id: singleRowId}];
// $mdDialog.show({
// clickOutsideToClose: true,
// controller: 'deleteStockItemController',
// controllerAs: 'ctrl',
// focusOnOpen: false,
// targetEvent: event,
// locals: { items: $scope.id },
// templateUrl: 'templates/employee/delete_confirm_dialog.html',
// }).then($scope.getResponse);
// };
//
	  $scope.getResponse = function () {
		    $scope.promise = $apiurl.order.get($scope.query, success).$promise;
		    $scope.selected = [];
		  };

// $scope.removeFilter = function () {
// $scope.filter.show = false;
// $scope.query.filter = '';
// if($scope.filter.form.$dirty) {
// $scope.filter.form.$setPristine();
// }
// };
//	  
// $scope.$watch('query.filter', function (newValue, oldValue) {
// if(!oldValue) {
// bookmark = $scope.query.page;
// }
// if(newValue !== oldValue) {
// $scope.query.page = 1;
// }
// if(!newValue) {
// $scope.query.page = bookmark;
// }
// $scope.getResponse();
// });
}]);

app.controller('addStockOutController', ['$mdDialog', '$apiurl', '$scope', function ($mdDialog, $apiurl, $scope) {
	  'use strict';
	  this.cancel = $mdDialog.cancel;

	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: '400',
	    page: 1,
	    order: 'id'
	  };
	  
	  // to fetch branch
	  function successBranches(response) {
	    $scope.branches= response;
	  }
	  $scope.getBranches= function () {
		  $scope.promise = $apiurl.branches.get($scope.query, successBranches).$promise;
	  };
	  $scope.getBranches();
	  // to fetch ledger
	  function successLedger(response) {
			$scope.partyLedger = response;
			$scope.showProgressBar=false;
	  }
	  $scope.getLedgerResponse = function () {
		  $scope.showProgressBar=true;
		  $scope.promise = $apiurl.ledger.get($scope.query, successLedger).$promise;
	  };
	  $scope.getLedgerResponse();

	  // to fetch pteType
	  function successPTEType(response) {
	    $scope.pteType= response;
	  }
	  $scope.getPTEType= function () {
		  $scope.promise = $apiurl.pteType.get($scope.query, successPTEType).$promise;
	  };
	  $scope.getPTEType();
	  // to fetch pteType

	  // to fetch item
	  function successStockItems(response) {
	    $scope.Items= response;
	  }
	  $scope.getStockItems= function () {
		  $scope.promise = $apiurl.stockItems.get($scope.query, successStockItems).$promise;
	  };
	  $scope.getStockItems();
	  // to fetch item

	  $scope.qType = function(value){
		  if(value==null){
			  $scope.step=0.01;
		
		  }else{
			  $scope.step=0.001;
			
		  }
	  }

	  $scope.stock = [];
	  $scope.addStockItemField = function(){
		  $scope.stock.push($scope.stock.length+1);
		  
		  if($scope.stock[1]==null){
			  $scope.p_order.stockItems = [];
			  console.log("work");
		  }
	  }
	  $scope.subtractStockItemField = function(){
		  console.log($scope.stock.length);
		  $scope.p_order.stockItems.pop($scope.stock.length);
		  $scope.stock.pop($scope.stock.length);
	  }
	  $scope.subtractStockItemFieldById = function(index){
		  console.log(index);
		  console.log($scope.p_order.stockItems[index]);
		  $scope.p_order.stockItems.splice(index, 1);
		  $scope.stock.pop(index+1);
	  }
	  $scope.p_order = {orderDate: new Date()};

	  $scope.updateTotalAmount= function(){
		  $scope.totalAmount = 0;
		  var i;
		  for(i=0; i<$scope.stock.length; i++){
			  $scope.totalAmount = $scope.totalAmount + ($scope.p_order.stockItems[i].rate*$scope.p_order.stockItems[i].quantity);
		  }
		  $scope.p_order.totalAmount = $scope.totalAmount;
	  }
	  
	  function success(response){
		  $mdDialog.hide(response);
	  }
	  this.addItem = function () {
		  $scope.updateTotalAmount();
		  $scope.p_order.paidAmount = 0;
		  console.log($scope.totalAmount);
		  console.log($scope.p_order);
		  $scope.item.form.$setSubmitted();
		  if($scope.item.form.$valid) {
			  $apiurl.order.save($scope.p_order, success);
		  }
	  };
}]);

app.controller('updateStockOutController', ['$mdDialog', '$apiurl', '$scope', 'items', function ($mdDialog, $apiurl, $scope, items) {

}]);

app.controller('deleteStockOutController', ['items', '$mdDialog', '$apiurl', '$scope', '$q', '$log','$rootScope', function (items, $mdDialog, $apiurl, $scope, $q, $log, $rootScope) {

}]);

app.controller('stockOutController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$log', function ($timeout, $mdDialog, $apiurl, $scope, $log) {
	  'use strict';
	  var bookmark;
	  $scope.selected = [];
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
		by: 'productTransactionType',
		id: 4,
	    filter: '',
	    limit: '10',
	    page: 1,
	    order: '-id'
	};
	  function success(response) {
	    $scope.response = response;
	  }
	  $scope.reLoadStuff = function () {
		    $scope.promise = $timeout(function(){
		    	$scope.getResponse();
		    }, 2000);
		  }

	  $scope.addItem = function (event) {
	    $mdDialog.show({
	      clickOutsideToClose: true,
	      controller: 'addStockOutController',
	      controllerAs: 'ctrl',
	      focusOnOpen: false,
	      targetEvent: event,
	      templateUrl: 'templates/org/order/add_stock_out.html',
	    }).then($scope.getResponse);
	  };

	  $scope.getResponse = function () {
		    $scope.promise = $apiurl.order.get($scope.query, success).$promise;
		    $scope.selected = [];
		  };
}]);

app.controller('employeeDashboardController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$rootScope', '$log', function ($timeout, $mdDialog, $apiurl, $scope,$rootScope, $log) {
	  'use strict';

	  $scope.startDate = new Date();
	  $scope.endDate = new Date();
	  $scope.startDate.setDate($scope.startDate.getDate()-7);
	  
	  $scope.pieLabels = [];
	  $scope.pieData = [];
	  
	  $scope.barLabels=[];
	  $scope.barData = [];
	  
	  $scope.labels = [];
	  $scope.pdata = [];
	  $scope.sdata = [];
	  

	  $scope.qtyByDateQuery = {
		startDate: new Date(new Date().setDate(new Date().getDate()-30)),
		endDate: new Date(new Date().setDate(new Date().getDate()+1)),
		type: 'quantityByDate',
		filter: '',
		limit: '10000',
		page: 1,
		order: 'id'
	  };
	  $scope.pPromise = $apiurl.dashboard.get({id: 1, ...$scope.qtyByDateQuery}, fetchPurchaseQty).$promise;
	  $scope.sPromise = $apiurl.dashboard.get({id: 2, ...$scope.qtyByDateQuery}, fetchSaleQty).$promise;
	  
	  for(var i=30; i>=0; i--){
		  $scope.labels.push(new Date(new Date().setDate(new Date().getDate()-i)).toDateString())
	  }
	  function fetchPurchaseQty(response) {
		  for(var i=30; i>=0; i--){
			  $scope.pdata[30-i] = 0;
			  var sD = new Date(new Date().setDate(new Date().getDate()-i-1))
			  var eD = new Date(new Date().setDate(new Date().getDate()-i))
			  response.content.forEach(element=>{
				  let d = new Date(element[1])
				  if(d<=eD && d>sD){
					  $scope.pdata[30-i] = $scope.pdata[30-i]+(element[0]*element[2]*(1-(element[3]/100)))
				  }
			  })
		  }
	  }
	  function fetchSaleQty(response) {
		  for(var i=30; i>=0; i--){
			  $scope.sdata[30-i] = 0;
			  var sD = new Date(new Date().setDate(new Date().getDate()-i-1))
			  var eD = new Date(new Date().setDate(new Date().getDate()-i))
			  response.content.forEach(element=>{
				  let d = new Date(element[1])
				  if(d<=eD && d>sD){
					  $scope.sdata[30-i] = $scope.sdata[30-i]-(element[0]*element[2]*(1-(element[3]/100)))
				  }
			  })
			  
		  }
	  }
	  
	  // to fetch balance
	  function successPurchaseBalance(response) {
		  $scope.purchase_balance=0.0;
		  $scope.purchase_balance = response.content[response.totalElements-1][4];
		  $scope.pieLabels.push("Purchase");
		  $scope.pieData.push($scope.purchase_balance);
	  }
	  function successSaleBalance(response) {
		  $scope.sale_balance=0.0;
		  $scope.sale_balance = response.content[response.totalElements-1][4];
		  $scope.pieLabels.push("Sale");
		  $scope.pieData.push($scope.sale_balance);
	  }
	  function successExpenceBalance(response) {
		  $scope.expence_balance=0.0;
		  $scope.expence_balance = response.content[response.totalElements-1][4];
		  $scope.pieLabels.push("Expence");
		  $scope.pieData.push($scope.expence_balance);
	  }
	  $scope.getTransactionResponse = function (type) {
		  if(type=="purchase"){
			  $scope.promise = $apiurl.dashboard.get($scope.tquery, successPurchaseBalance).$promise;
		  }if(type=="sale"){
			  $scope.promise = $apiurl.dashboard.get($scope.tquery, successSaleBalance).$promise;
		  }if(type=="expence"){
			  $scope.promise = $apiurl.dashboard.get($scope.tquery, successExpenceBalance).$promise;
		  }
	  };
	  $scope.balanceOf = function(tId, type){
		  $scope.tquery = {
			type: 'account',
			id : tId,
			startDate: $scope.startDate,
			endDate: $scope.endDate,
			filter: '',
			limit: '10000',
			page: 1,
			order: 'id'
		  };
		  $scope.getTransactionResponse(type);
	  }
	  // to fetch balance
	  $scope.getBalance = function(){
		  $scope.pieLabels=[];
		  $scope.pieData=[];
		  $scope.balanceOf(3, "sale");
		  $scope.balanceOf(1, "purchase");
		  $scope.balanceOf(4, "expence");
		  $scope.paidOf(1, "purchase");
		  $scope.paidOf(3, "sale");
		  $scope.showProgressBar=false;
	  }
	  
	  
	  $scope.today = () =>{
		  $scope.startDate = new Date();
		  $scope.startDate.setDate($scope.startDate.getDate()-1);
		  $scope.getBalance();
		  console.log($scope.labels)
	  }
	  $scope.week = () =>{
		  $scope.startDate = new Date();
		  $scope.startDate.setDate($scope.startDate.getDate()-7);
		  $scope.getBalance();
	  }
	  $scope.month = () =>{
		  $scope.startDate = new Date();
		  $scope.startDate.setMonth($scope.startDate.getMonth()-1);
		  $scope.getBalance();
	  }
	  $scope.year = () =>{
		  $scope.startDate = new Date();
		  $scope.startDate.setFullYear($scope.startDate.getFullYear()-1);
		  $scope.getBalance();
	  }
	  
	  $scope.q_query = {
	    type: 'paidAmount',
		id: 6,
		startDate: $scope.startDate,
		endDate: $scope.endDate,
	    filter: '',
	    limit: 1000,
	    page: 1,
	    order: '-id'
	};
	  function success_p_paid(response) {
		  if(response.content.length==0 ){
			  $scope.p_paid = 0.00;
		  }
		  $scope.p_paid = response.content[0][1];
		  $scope.showProgressBar=false;
	  }
	  function success_s_paid(response) {
		  if(response.content.length==0 ){
			  $scope.s_paid = 0.00;
		  }
		  $scope.s_paid = response.content[0][1];
		  $scope.showProgressBar=false;
	  }
	  $scope.getPaidResponse = function (type) {
		  if(type=="purchase"){
			  $scope.promise = $apiurl.dashboard.get($scope.q_query, success_p_paid).$promise;
		  }
		  if(type=="sale"){
			  $scope.promise = $apiurl.dashboard.get($scope.q_query, success_s_paid).$promise;
		  }
	  };
	  $scope.paidOf = function(tId, type){
		  $scope.q_query.id=tId;
		  $scope.getPaidResponse(type);
	  }
	  $scope.getBalance();

		$scope.l_query = {
			type: 'parent',
			id: 3,
			filter: '',
			limit: '400',
			page: 1,
			order: 'id'
		};
		function successCustomerLedger(response) {
			$scope.customer = response.content;
		}
		function successSupplierLedger(response) {
			$scope.supplier = response.content;
		}
		$scope.getCustomerLedgerResponse = function () {
			$scope.promise = $apiurl.dashboard.get($scope.l_query, successCustomerLedger).$promise;
		};
		$scope.getCustomerLedgerResponse();
		
		$scope.getSupplierLedgerResponse = function () {
			$scope.l_query.id= 9;
			$scope.promise = $apiurl.dashboard.get($scope.l_query, successSupplierLedger).$promise;
		};
		$scope.getSupplierLedgerResponse();
	  
	  
		  $scope.st_query = {
			type: 'totalSell',
			startDate: $scope.startDate,
			endDate: $scope.endDate,
		    filter: '',
		    limit: $rootScope.pageSize,
		    page: 1,
		    order: '-id'
		};
		$scope.myDate = new Date();
		$scope.myEndDate = new Date();
		$scope.myEndDate.setDate($scope.myDate.getDate()-2000);
		$scope.st_query_custom = {
					type: 'totalSell',
					startDate: $scope.myEndDate,
					endDate: $scope.myDate,
				    filter: '',
				    limit: $rootScope.pageSize,
				    page: 1,
				    order: '-id'
				};
		  function success(response) {
			  $scope.barLabels=[];
			  $scope.barData=[];
		    $scope.response = response;
		    for(var i=0; i<response.content.length; i++){
		    	$scope.barLabels.push(response.content[i][0]);
		    	if(response.content[i][1]<0){
		    		response.content[i][1]=response.content[i][1]*(-1);
		    	}
		    	$scope.barData.push(response.content[i][1]);
		    }

		  }
		  $scope.getResponse = function () {
		    $scope.promise = $apiurl.dashboard.get($scope.st_query_custom, success).$promise;
		  };
		  $scope.getResponse();
	  
	  
	  
	  
	  
	  
//	  $scope.labels = ["01-08-2020", "02-08-2020", "03-08-2020", "04-08-2020", "05-08-2020"
//		  , "06-08-2020", "07-08-2020", "08-08-2020", "09-08-2020", "10-08-2020", "11-08-2020"
//		  , "12-08-2020", "13-08-2020", "14-08-2020", "15-08-2020", "16-08-2020", "17-08-2020"
//		  , "18-08-2020", "19-08-2020", "20-08-2020", "21-08-2020", "22-08-2020", "23-08-2020"
//		  , "24-08-2020", "25-08-2020", "26-08-2020", "27-08-2020", "28-08-2020", "29-08-2020"
//		  , "30-08-2020"];
	  $scope.series = ['Series A'];
//	  $scope.data = [
//	    [65, 59, 80, 81, 56, 55, 40, 45, 50, 60,70, 80, 80, 80, 70, 70, 75, 80, 85, 70
//	    	, 65, 65, 65, 65, 60, 60, 55,30, 20, 5]
//	  ];
	  $scope.onClick = function (points, evt) {
	    console.log(points, evt);
	  };
	  $scope.datasetOverride = [{ yAxisID: 'y-axis-1' }];
	  $scope.options = {
	    scales: {
	      yAxes: [
	        {
	          id: 'y-axis-1',
	          type: 'linear',
	          display: true,
	          position: 'left'
	        }
	      ]
	    }
	  };
	  
	  // Bar diagram data
	  // $scope.barLabels = ['Basmoti', 'Miniket', 'Kathari', 'Chinigura',
		// 'Sompa', 'Haski', 'Sorna', 'Hybrid', 'Parija', 'Tamri', 'Khud',
		// 'Machi'];
	  $scope.barSeries = ['Series A'];

// $scope.barData = [
// [4000, 3500, 3200, 2800, 2000, 1500, 1500, 1300, 1220, 1200, 500, 100, 50,
// 10]
// ];
	  
	  // Pie Data
	  // $scope.pieLabels = ["Sales", "Purchase", "Expense"];
	  // $scope.pieData = [200000, 400000, 30000];

	}]);


app.controller('changePasswordController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$log', function ($timeout, $mdDialog, $apiurl, $scope, $log) {
	
	console.log("Hello console");

	  function success(response) {
		  $mdDialog.show(
		      $mdDialog.alert()
		        .parent(angular.element(document.querySelector('#popupContainer')))
		        .clickOutsideToClose(true)
		        .title('Update successfull!')
		        .textContent('Password updated Successfully!.')
		        .ariaLabel('Alert Dialog Demo')
		        .ok('Got it!')
		        .targetEvent(response)
		    );
		  $scope.reLoadStuff();
		  console.log("success!")
	  }
	  function fail(reason) {
		  $mdDialog.show(
		      $mdDialog.alert()
		        .parent(angular.element(document.querySelector('#popupContainer')))
		        .clickOutsideToClose(true)
		        .title('Update failed!')
		        .textContent('Failed to update user password!')
		        .ariaLabel('Alert Dialog Demo')
		        .ok('Got it!')
		        .targetEvent(reason)
		    );
	  }
	  $scope.reLoadStuff = function () {
	    $scope.promise = $timeout(function(){
	    	console.log("hello reload");
	    	$scope.user = {};
	    }, 2000);
	  }

	  $scope.erase = function () {
	    	console.log("hello erase");
	    	$scope.user.newPassword = null;
	    	$scope.user.confirmNewPassword= null;
	  }
	  
	  $scope.updateItem = function () {
		  if($scope.item.form.$valid) {
			  $apiurl.user.update({type:'updatePassword'}, $scope.user, success, fail);
		  }
	  };
	
}]);



app.controller('passwordEmailResetrequestcontroller', ['$timeout','$mdDialog', '$apiurl', '$scope', '$log', function ($timeout, $mdDialog, $apiurl, $scope, $log) {
	
	console.log("Hello console");

	  function success(response) {
		  $mdDialog.show(
		      $mdDialog.alert()
		        .parent(angular.element(document.querySelector('#popupContainer')))
		        .clickOutsideToClose(true)
		        .title('Update successfull!')
		        .textContent('Password updated Successfully!.')
		        .ariaLabel('Alert Dialog Demo')
		        .ok('Got it!')
		        .targetEvent(response)
		    );
		  $scope.reLoadStuff();
		  console.log("success!")
	  }
	  function fail(reason) {
		  $mdDialog.show(
		      $mdDialog.alert()
		        .parent(angular.element(document.querySelector('#popupContainer')))
		        .clickOutsideToClose(true)
		        .title('Update failed!')
		        .textContent('Failed to update user password!')
		        .ariaLabel('Alert Dialog Demo')
		        .ok('Got it!')
		        .targetEvent(reason)
		    );
	  }
	  $scope.reLoadStuff = function () {
	    $scope.promise = $timeout(function(){
	    	console.log("hello reload");
	    	$scope.user = {};
	    }, 2000);
	  }

	  $scope.erase = function () {
	    	console.log("hello erase");
	    	$scope.user.newPassword = null;
	    	$scope.user.confirmNewPassword= null;
	  }
	  
	  $scope.updateItem = function () {
		  if($scope.item.form.$valid) {
			  $apiurl.user.update({type:'updateEmailPassword'}, $scope.user, success, fail);
		  }
	  };
	
}]);


app.controller('smsController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$log', '$rootScope', function ($timeout, $mdDialog, $apiurl, $scope, $log, $rootScope) {
	  'use strict';
	  var bookmark;
	  $scope.selected = [];
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: $rootScope.pageSize,
	    page: 1,
	    order: '-id'
	  };
	  function success(response) {
	    $scope.response = response;
	    
	  }
	  $scope.reLoadStuff = function () {
	    $scope.promise = $timeout(function(){
	    	$scope.getResponse();
	    }, 2000);
	  }
	  
	  $scope.getResponse = function () {
	    $scope.promise = $apiurl.sms.get($scope.query, success).$promise;
	    $scope.selected = [];
	  };
	  
	  $scope.removeFilter = function () {
	    $scope.filter.show = false;
	    $scope.query.filter = '';
	    if($scope.filter.form.$dirty) {
	      $scope.filter.form.$setPristine();
	    }
	  };
	  
	  $scope.$watch('query.filter', function (newValue, oldValue) {
	    if(!oldValue) {
	      bookmark = $scope.query.page;
	    }
	    if(newValue !== oldValue) {
	      $scope.query.page = 1;
	    }
	    if(!newValue) {
	      $scope.query.page = bookmark;
	    }
	    $scope.getResponse();
	  });

}]);


// ********************** PRODUCTION CONTROLLER PART **************************

app.controller('addProductionController', ['$mdDialog', '$apiurl', '$scope', function ($mdDialog, $apiurl, $scope) {
	'use strict';
	this.cancel = $mdDialog.cancel;
	//
	// to fetch ledger accounts
	$scope.filter = {
		options: {
			debounce: 500
		}
	};
	$scope.query = {
		filter: '',
		limit: '400',
		page: 1,
		order: 'id'
	};
	function successLedger(response) {
		$scope.partyLedger = response;
		$scope.balanceOf(5);
		$scope.showProgressBar=false;
	}
	$scope.getLedgerResponse = function () {
		$scope.showProgressBar=true;
		$scope.promise = $apiurl.ledger.get($scope.query, successLedger).$promise;
	};
	$scope.getLedgerResponse();
	// to fetch ledger accounts
	
	// to fetch balance
	function successBalance(response) {
		if(response.content.length==0 ){
			$scope.balance = 0.00;
			$scope.showProgressBar=false;
		}
		$scope.balance= response.content[response.totalElements-1][4];
		$scope.updateBill();
		$scope.showProgressBar=false;
	}
	$scope.getTransactionResponse = function () {
		$scope.showProgressBar=true;
		$scope.promise = $apiurl.accounttransaction.get($scope.tquery, successBalance).$promise;
	};
	$scope.balanceOf = function(tId){
		$scope.tquery = {
			type: 'account',
			id : tId,
			filter: '',
			limit: '10000',
			page: 1,
			order: 'id'
		};
		$scope.getTransactionResponse();
	}
	// to fetch balance
	
	// to fetch item
	function successStockItems(response) {
		$scope.Items= response;
	}
	$scope.getStockItems= function () {
		$scope.promise = $apiurl.stockItems.get($scope.query, successStockItems).$promise;
	};
	$scope.getStockItems();
	// to fetch item
	
	// to fetch branch
	function successBranches(response) {
		$scope.branches= response;
	}
	$scope.getBranches= function () {
		$scope.promise = $apiurl.branches.get($scope.query, successBranches).$promise;
	};
	$scope.getBranches();
	// to fetch branch
	
	// to fetch room
	function successRoom(response) {
		$scope.rooms= response;
	}
	$scope.getRooms= function(id) {
		$scope.queryRoom = {
			branch: 'branch',
			id: id,
			filter: '',
			limit: '400',
			page: 1,
			order: 'id'
		};
		$scope.promise = $apiurl.room.get($scope.queryRoom, successRoom).$promise;
	};
	// to fetch room
	
	
	// to fetch pteType
	function successPTEType(response) {
		$scope.pteType= response;
	}
	$scope.getPTEType= function () {
		$scope.promise = $apiurl.pteType.get($scope.query, successPTEType).$promise;
	};
	$scope.getPTEType();
	// to fetch pteType
	
	// Calculation with item
	$scope.totalQty = 0;
	$scope.totalAmount = 0;
	$scope.total = 0;
	$scope.subTotal = 0;
	$scope.balance = 0;
	$scope.grandTotal = 0;
	$scope.dueAmount = 0;
	$scope.paidAmount = 0;
	
	$scope.stock = [];
	$scope.addStockItemField = function(){
		$scope.stock.push($scope.stock.length+1);
		
		if($scope.stock[1]==null){
			$scope.p_order.stockItems = [];
		}
		$scope.updateBill();
	}
	$scope.subtractStockItemField = function(){
		$scope.p_order.stockItems.pop($scope.stock.length);
		$scope.stock.pop($scope.stock.length);
		$scope.updateBill();	
	}
	$scope.subtractStockItemField = function(index){
		console.log(index);
		console.log($scope.p_order.stockItems[index]);
		$scope.p_order.stockItems.splice(index, 1);
		$scope.stock.pop(index+1);
		$scope.updateBill();
	}
	$scope.updateTotalQty = function(){
		$scope.totalQty = 0;
		var i;
		for(i=0; i<$scope.stock.length; i++){
			$scope.totalQty = $scope.totalQty + $scope.p_order.stockItems[i].quantity;
		}
	}
	$scope.updateTotalAmount= function(){
		$scope.totalAmount = 0;
		var i;
		for(i=0; i<$scope.stock.length; i++){
			$scope.totalAmount = $scope.totalAmount + $scope.p_order.stockItems[i].rate*$scope.p_order.stockItems[i].quantity;
		}
	}
	$scope.updateTotal= function(){
		$scope.total = 0;
		var i;
		for(i=0; i<$scope.stock.length; i++){
			$scope.total = $scope.total + $scope.p_order.stockItems[i].rate*$scope.p_order.stockItems[i].quantity;
		}
	}
	$scope.updateSubTotal= function(){
		$scope.subTotal = 0;
		var i;
		for(i=0; i<$scope.stock.length; i++){
			$scope.subTotal = $scope.subTotal + $scope.p_order.stockItems[i].rate*$scope.p_order.stockItems[i].quantity;
		}
		$scope.subTotal = $scope.subTotal + $scope.balance;
	}
	$scope.updateGrandTotal= function(){
		$scope.grandTotal = 0;
		var i;
		for(i=0; i<$scope.stock.length; i++){
			$scope.grandTotal = $scope.grandTotal + $scope.p_order.stockItems[i].rate*$scope.p_order.stockItems[i].quantity;// Problem,
																															// -
																															// $scope.totalDiscount
		}
		$scope.grandTotal = $scope.grandTotal + $scope.balance;
	}
	$scope.updateDueAmount= function(){
		$scope.dueAmount = 0;
		if($scope.p_order.paidAmount==null){
			$scope.p_order.paidAmount = 0;
		}
		var i;
		for(i=0; i<$scope.stock.length; i++){
			$scope.dueAmount = $scope.dueAmount + $scope.p_order.stockItems[i].rate*$scope.p_order.stockItems[i].quantity;
		}
		$scope.dueAmount = $scope.dueAmount + $scope.balance - $scope.p_order.paidAmount;
	}
	
	$scope.qType = function(value){
		if(value==null){
			$scope.step=0.001;
		}else{
			$scope.step=0.001;
		}
	}
	$scope.updateBill= function(){
		$scope.updateTotalQty();
		$scope.updateTotalAmount();
		$scope.updateTotal();
		$scope.updateSubTotal();
		$scope.updateGrandTotal();
		$scope.updateDueAmount();
	}
	// Calculation with item
	
	$scope.p_order = {orderDate: new Date()};
	function success(response){
		$mdDialog.hide(response);
		console.log(response);
	}
	this.addItem = function () {
		$scope.p_order.totalAmount = $scope.totalAmount;
		console.log($scope.p_order);
		$scope.item.form.$setSubmitted();
		if($scope.item.form.$valid) {
			$apiurl.order.save($scope.p_order, success);
		}
	};
}]);

app.controller('viewProductionController', ['$mdDialog', '$apiurl', '$scope', 'items', function ($mdDialog, $apiurl, $scope, items) {
	'use strict';
	this.cancel = $mdDialog.cancel;
	console.log(items);
	$scope.p_order = items;
	
	// to fetch ledger accounts
	$scope.filter = {
		options: {
			debounce: 500
		}
	};
	$scope.query = {
		filter: '',
		limit: '400',
		page: 1,
		order: 'id'
	};
	function successLedger(response) {
		$scope.partyLedger = response;
		$scope.showProgressBar=false;
	}
	$scope.getLedgerResponse = function () {
		$scope.showProgressBar=true;
		$scope.promise = $apiurl.ledger.get($scope.query, successLedger).$promise;
	};
	$scope.getLedgerResponse();
	// to fetch ledger accounts
	
	// to fetch balance
	function successBalance(response) {
		if(response.content.length==0 ){
			$scope.balance = 0.00;
			$scope.showProgressBar=false;
		}
		$scope.balance= response.content[response.totalElements-1][4];
		$scope.updateBill();
		$scope.showProgressBar=false;
	}
	$scope.getTransactionResponse = function () {
		$scope.showProgressBar=true;
		$scope.promise = $apiurl.accounttransaction.get($scope.tquery, successBalance).$promise;
	};
	$scope.balanceOf = function(){
		$scope.tquery = {
			type: 'account',
			id : $scope.p_order.party.id,
			filter: '',
			limit: '10000',
			page: 1,
			order: 'id'
		};
		$scope.getTransactionResponse();
	}
	// to fetch balance
	
	// to fetch item
	function successStockItems(response) {
		$scope.Items= response;
	}
	$scope.getStockItems= function () {
		$scope.promise = $apiurl.stockItems.get($scope.query, successStockItems).$promise;
	};
	$scope.getStockItems();
	// to fetch item
	
	// Calculation with item
	$scope.totalQty = 0;
	$scope.totalAmount = 0;
	$scope.total = 0;
	$scope.subTotal = 0;
	$scope.balance = 0;
	$scope.grandTotal = 0;
	$scope.dueAmount = 0;
	$scope.paidAmount = 0;
	
	$scope.updateTotalQty = function(){
		$scope.totalQty = 0;
		var i;
		for(i=0; i<$scope.p_order.stockItems.length; i++){
			$scope.totalQty = $scope.totalQty + $scope.p_order.stockItems[i].quantity;
		}
	}
	$scope.updateTotalDiscount = function(){
		$scope.totalDiscount = 0;
		var i;
		for(i=0; i<$scope.p_order.stockItems.length; i++){
			$scope.totalDiscount = $scope.totalDiscount + ($scope.p_order.stockItems[i].rate)*$scope.p_order.stockItems[i].quantity;
		}
	}
	$scope.updateTotalAmount= function(){
		$scope.totalAmount = 0;
		var i;
		for(i=0; i<$scope.p_order.stockItems.length; i++){
			$scope.totalAmount = $scope.totalAmount + ($scope.p_order.stockItems[i].rate)*$scope.p_order.stockItems[i].quantity;
		}
	}
	$scope.updateTotal= function(){
		$scope.total = 0;
		var i;
		for(i=0; i<$scope.p_order.stockItems.length; i++){
			$scope.total = $scope.total + $scope.p_order.stockItems[i].rate*$scope.p_order.stockItems[i].quantity;
		}
	}
	$scope.updateSubTotal= function(){
		$scope.subTotal = 0;
		var i;
		for(i=0; i<$scope.p_order.stockItems.length; i++){
			$scope.subTotal = $scope.subTotal + ($scope.p_order.stockItems[i].rate)*$scope.p_order.stockItems[i].quantity;
		}
		$scope.subTotal = $scope.subTotal + $scope.balance;
	}
	$scope.updateGrandTotal= function(){
		$scope.grandTotal = 0;
		var i;
		for(i=0; i<$scope.p_order.stockItems.length; i++){
		$scope.grandTotal = $scope.grandTotal + ($scope.p_order.stockItems[i].rate)*$scope.p_order.stockItems[i].quantity;// Problem,
																															// -
																															// $scope.totalDiscount
		}
		$scope.grandTotal = $scope.grandTotal + $scope.balance;
	}
	$scope.updateDueAmount= function(){
		$scope.dueAmount = 0;
		if($scope.p_order.paidAmount==null){
			$scope.p_order.paidAmount = 0;
		}
		var i;
		for(i=0; i<$scope.p_order.stockItems.length; i++){
			$scope.dueAmount = $scope.dueAmount + ($scope.p_order.stockItems[i].rate)*$scope.p_order.stockItems[i].quantity;
		}
		$scope.dueAmount = $scope.dueAmount + $scope.balance - $scope.p_order.paidAmount;
	}
	$scope.updateBill= function(){
		$scope.updateTotalQty();
		$scope.updateTotalAmount();
		$scope.updateTotal();
		$scope.updateSubTotal();
		$scope.updateGrandTotal();
		$scope.updateDueAmount();
	}
	// Calculation with item
	$scope.balanceOf();
	$scope.updateBill();

}]);

app.controller('productionStateController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$rootScope' ,'$log', function ($timeout, $mdDialog, $apiurl, $scope, $rootScope, $log) {
	'use strict';
	var bookmark;
	$scope.selected = [];
	$scope.filter = {
		options: {
			debounce: 500
		}
	};
	$scope.startDate = new Date();
	$scope.startDate.setFullYear(2000);
	$scope.endDate = new Date();
	$scope.endDate.setHours(24);
	$scope.query = {
		startDate: $scope.startDate,
		endDate: $scope.endDate,
		by: 'productTransactionType',
		id: 7,
		filter: '',
		limit: $rootScope.pageSize,
		page: 1,
		order: '-id'
	};
	function success(response) {
		$scope.response = response;
	}
	$scope.reLoadStuff = function () {
		$scope.promise = $timeout(function(){
			$scope.getResponse();
		}, 2000);
	}
	$scope.addItem = function (event) {
		$mdDialog.show({
			clickOutsideToClose: true,
			controller: 'addProductionController',
			controllerAs: 'ctrl',
			focusOnOpen: false,
			targetEvent: event,
			templateUrl: 'templates/org/order/add_production.html',
		}).then($scope.getResponse);
	};
	$scope.viewItem = function (event) {
		$mdDialog.show({
			clickOutsideToClose: true,
			controller: 'viewProductionController',
			controllerAs: 'ctrl',
			focusOnOpen: false,
			targetEvent: event,
			locals:{ items : event },
			templateUrl: 'templates/org/order/view_production_consumption.html',
		}).then($scope.getResponse);
	};
	
	$scope.getResponse = function () {
		$scope.promise = $apiurl.order.get($scope.query, success).$promise;
		$scope.selected = [];
	};
	
	$scope.onChangeDate= function(){
		$scope.startDate.setHours(0);
		$scope.endDate.setHours(24);
		$scope.query.startDate = $scope.startDate;
		$scope.query.endDate = $scope.endDate;
		$scope.getResponse();
	}
	
}]);

// ********************** PRODUCTION CONTROLLER PART
// ENDED**************************

// ********************** CONSUMPTION CONTROLLER PART**************************

app.controller('addConsumptionController', ['$mdDialog', '$apiurl', '$scope', function ($mdDialog, $apiurl, $scope) {
	'use strict';
	this.cancel = $mdDialog.cancel;
	//
	// to fetch ledger accounts
	$scope.filter = {
		options: {
			debounce: 500
		}
	};
	$scope.query = {
		filter: '',
		limit: '400',
		page: 1,
		order: 'id'
	};
	function successLedger(response) {
		$scope.partyLedger = response;
		$scope.balanceOf(5);
		$scope.showProgressBar=false;
	}
	$scope.getLedgerResponse = function () {
		$scope.showProgressBar=true;
		$scope.promise = $apiurl.ledger.get($scope.query, successLedger).$promise;
	};
	$scope.getLedgerResponse();
	// to fetch ledger accounts
	
	// to fetch balance
	function successBalance(response) {
		if(response.content.length==0 ){
			$scope.balance = 0.00;
			$scope.showProgressBar=false;
		}
		$scope.balance= response.content[response.totalElements-1][4];
		$scope.updateBill();
		$scope.showProgressBar=false;
	}
	$scope.getTransactionResponse = function () {
		$scope.showProgressBar=true;
		$scope.promise = $apiurl.accounttransaction.get($scope.tquery, successBalance).$promise;
	};
	$scope.balanceOf = function(tId){
		$scope.tquery = {
			type: 'account',
			id : tId,
			filter: '',
			limit: '10000',
			page: 1,
			order: 'id'
		};
		$scope.getTransactionResponse();
	}
	// to fetch balance
	
	// to fetch item
	function successStockItems(response) {
		$scope.Items= response;
	}
	$scope.getStockItems= function () {
		$scope.promise = $apiurl.stockItems.get($scope.query, successStockItems).$promise;
	};
	$scope.getStockItems();
	// to fetch item
	
	// to fetch branch
	function successBranches(response) {
		$scope.branches= response;
	}
	$scope.getBranches= function () {
		$scope.promise = $apiurl.branches.get($scope.query, successBranches).$promise;
	};
	$scope.getBranches();
	// to fetch branch
	
	// to fetch room
	function successRoom(response) {
		$scope.rooms= response;
	}
	$scope.getRooms= function(id) {
		$scope.queryRoom = {
			branch: 'branch',
			id: id,
			filter: '',
			limit: '400',
			page: 1,
			order: 'id'
		};
		$scope.promise = $apiurl.room.get($scope.queryRoom, successRoom).$promise;
	};
	// to fetch room
	
	
	// to fetch pteType
	function successPTEType(response) {
		$scope.pteType= response;
	}
	$scope.getPTEType= function () {
		$scope.promise = $apiurl.pteType.get($scope.query, successPTEType).$promise;
	};
	$scope.getPTEType();
	// to fetch pteType
	
	// Calculation with item
	$scope.totalQty = 0;
	$scope.totalAmount = 0;
	$scope.total = 0;
	$scope.subTotal = 0;
	$scope.balance = 0;
	$scope.grandTotal = 0;
	$scope.dueAmount = 0;
	$scope.paidAmount = 0;
	
	$scope.stock = [];
	$scope.addStockItemField = function(){
		$scope.stock.push($scope.stock.length+1);
		
		if($scope.stock[1]==null){
			$scope.p_order.stockItems = [];
		}
		$scope.updateBill();
	}
	$scope.subtractStockItemField = function(){
		$scope.p_order.stockItems.pop($scope.stock.length);
		$scope.stock.pop($scope.stock.length);
		$scope.updateBill();	
	}
	$scope.subtractStockItemField = function(index){
		console.log(index);
		console.log($scope.p_order.stockItems[index]);
		$scope.p_order.stockItems.splice(index, 1);
		$scope.stock.pop(index+1);
		$scope.updateBill();
	}
	$scope.updateTotalQty = function(){
		$scope.totalQty = 0;
		var i;
		for(i=0; i<$scope.stock.length; i++){
			$scope.totalQty = $scope.totalQty + $scope.p_order.stockItems[i].quantity;
		}
	}
	$scope.updateTotalAmount= function(){
		$scope.totalAmount = 0;
		var i;
		for(i=0; i<$scope.stock.length; i++){
			$scope.totalAmount = $scope.totalAmount + $scope.p_order.stockItems[i].rate*$scope.p_order.stockItems[i].quantity;
		}
	}
	$scope.updateTotal= function(){
		$scope.total = 0;
		var i;
		for(i=0; i<$scope.stock.length; i++){
			$scope.total = $scope.total + $scope.p_order.stockItems[i].rate*$scope.p_order.stockItems[i].quantity;
		}
	}
	$scope.updateSubTotal= function(){
		$scope.subTotal = 0;
		var i;
		for(i=0; i<$scope.stock.length; i++){
			$scope.subTotal = $scope.subTotal + $scope.p_order.stockItems[i].rate*$scope.p_order.stockItems[i].quantity;
		}
		$scope.subTotal = $scope.subTotal + $scope.balance;
	}
	$scope.updateGrandTotal= function(){
		$scope.grandTotal = 0;
		var i;
		for(i=0; i<$scope.stock.length; i++){
			$scope.grandTotal = $scope.grandTotal + $scope.p_order.stockItems[i].rate*$scope.p_order.stockItems[i].quantity;// Problem,
																															// -
																															// $scope.totalDiscount
		}
		$scope.grandTotal = $scope.grandTotal + $scope.balance;
	}
	$scope.updateDueAmount= function(){
		$scope.dueAmount = 0;
		if($scope.p_order.paidAmount==null){
			$scope.p_order.paidAmount = 0;
		}
		var i;
		for(i=0; i<$scope.stock.length; i++){
			$scope.dueAmount = $scope.dueAmount + $scope.p_order.stockItems[i].rate*$scope.p_order.stockItems[i].quantity;
		}
		$scope.dueAmount = $scope.dueAmount + $scope.balance - $scope.p_order.paidAmount;
	}
	
	$scope.qType = function(value){
		if(value==null){
			$scope.step=0.001;
		}else{
			$scope.step=0.001;
		}
	}
	$scope.updateBill= function(){
		$scope.updateTotalQty();
		$scope.updateTotalAmount();
		$scope.updateTotal();
		$scope.updateSubTotal();
		$scope.updateGrandTotal();
		$scope.updateDueAmount();
	}
	// Calculation with item
	
	$scope.p_order = {orderDate: new Date()};
	function success(response){
		$mdDialog.hide(response);
		console.log(response);
	}
	this.addItem = function () {
		$scope.p_order.totalAmount = $scope.totalAmount;
		console.log($scope.p_order);
		$scope.item.form.$setSubmitted();
		if($scope.item.form.$valid) {
			$apiurl.order.save($scope.p_order, success);
		}
	};
}]);

app.controller('viewConsumptionController', ['$mdDialog', '$apiurl', '$scope', 'items', function ($mdDialog, $apiurl, $scope, items) {
	'use strict';
	this.cancel = $mdDialog.cancel;
	console.log(items);
	$scope.p_order = items;
	
	// to fetch ledger accounts
	$scope.filter = {
		options: {
			debounce: 500
		}
	};
	$scope.query = {
		filter: '',
		limit: '400',
		page: 1,
		order: 'id'
	};
	function successLedger(response) {
		$scope.partyLedger = response;
		$scope.showProgressBar=false;
	}
	$scope.getLedgerResponse = function () {
		$scope.showProgressBar=true;
		$scope.promise = $apiurl.ledger.get($scope.query, successLedger).$promise;
	};
	$scope.getLedgerResponse();
	// to fetch ledger accounts
	
	// to fetch balance
	function successBalance(response) {
		if(response.content.length==0 ){
			$scope.balance = 0.00;
			$scope.showProgressBar=false;
		}
		$scope.balance= response.content[response.totalElements-1][4];
		$scope.updateBill();
		$scope.showProgressBar=false;
	}
	$scope.getTransactionResponse = function () {
		$scope.showProgressBar=true;
		$scope.promise = $apiurl.accounttransaction.get($scope.tquery, successBalance).$promise;
	};
	$scope.balanceOf = function(){
		$scope.tquery = {
			type: 'account',
			id : $scope.p_order.party.id,
			filter: '',
			limit: '10000',
			page: 1,
			order: 'id'
		};
		$scope.getTransactionResponse();
	}
	// to fetch balance
	
	// to fetch item
	function successStockItems(response) {
		$scope.Items= response;
	}
	$scope.getStockItems= function () {
		$scope.promise = $apiurl.stockItems.get($scope.query, successStockItems).$promise;
	};
	$scope.getStockItems();
	// to fetch item
	
	// Calculation with item
	$scope.totalQty = 0;
	$scope.totalAmount = 0;
	$scope.total = 0;
	$scope.subTotal = 0;
	$scope.balance = 0;
	$scope.grandTotal = 0;
	$scope.dueAmount = 0;
	$scope.paidAmount = 0;
	
	$scope.updateTotalQty = function(){
		$scope.totalQty = 0;
		var i;
		for(i=0; i<$scope.p_order.stockItems.length; i++){
			$scope.totalQty = $scope.totalQty + $scope.p_order.stockItems[i].quantity;
		}
	}
	$scope.updateTotalDiscount = function(){
		$scope.totalDiscount = 0;
		var i;
		for(i=0; i<$scope.p_order.stockItems.length; i++){
			$scope.totalDiscount = $scope.totalDiscount + ($scope.p_order.stockItems[i].rate)*$scope.p_order.stockItems[i].quantity;
		}
	}
	$scope.updateTotalAmount= function(){
		$scope.totalAmount = 0;
		var i;
		for(i=0; i<$scope.p_order.stockItems.length; i++){
			$scope.totalAmount = $scope.totalAmount + ($scope.p_order.stockItems[i].rate)*$scope.p_order.stockItems[i].quantity;
		}
	}
	$scope.updateTotal= function(){
		$scope.total = 0;
		var i;
		for(i=0; i<$scope.p_order.stockItems.length; i++){
			$scope.total = $scope.total + $scope.p_order.stockItems[i].rate*$scope.p_order.stockItems[i].quantity;
		}
	}
	$scope.updateSubTotal= function(){
		$scope.subTotal = 0;
		var i;
		for(i=0; i<$scope.p_order.stockItems.length; i++){
			$scope.subTotal = $scope.subTotal + ($scope.p_order.stockItems[i].rate)*$scope.p_order.stockItems[i].quantity;
		}
		$scope.subTotal = $scope.subTotal + $scope.balance;
	}
	$scope.updateGrandTotal= function(){
		$scope.grandTotal = 0;
		var i;
		for(i=0; i<$scope.p_order.stockItems.length; i++){
		$scope.grandTotal = $scope.grandTotal + ($scope.p_order.stockItems[i].rate)*$scope.p_order.stockItems[i].quantity;// Problem,
																															// -
																															// $scope.totalDiscount
		}
		$scope.grandTotal = $scope.grandTotal + $scope.balance;
	}
	$scope.updateDueAmount= function(){
		$scope.dueAmount = 0;
		if($scope.p_order.paidAmount==null){
			$scope.p_order.paidAmount = 0;
		}
		var i;
		for(i=0; i<$scope.p_order.stockItems.length; i++){
			$scope.dueAmount = $scope.dueAmount + ($scope.p_order.stockItems[i].rate)*$scope.p_order.stockItems[i].quantity;
		}
		$scope.dueAmount = $scope.dueAmount + $scope.balance - $scope.p_order.paidAmount;
	}
	$scope.updateBill= function(){
		$scope.updateTotalQty();
		$scope.updateTotalAmount();
		$scope.updateTotal();
		$scope.updateSubTotal();
		$scope.updateGrandTotal();
		$scope.updateDueAmount();
	}
	// Calculation with item
	$scope.balanceOf();
	$scope.updateBill();

}]);


app.controller('consumptionStateController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$rootScope' ,'$log', function ($timeout, $mdDialog, $apiurl, $scope, $rootScope, $log) {
	'use strict';
	var bookmark;
	$scope.selected = [];
	$scope.filter = {
		options: {
			debounce: 500
		}
	};
	$scope.startDate = new Date();
	$scope.startDate.setFullYear(2000);
	$scope.endDate = new Date();
	$scope.endDate.setHours(24);
	$scope.query = {
		startDate: $scope.startDate,
		endDate: $scope.endDate,
		by: 'productTransactionType',
		id: 8,
		filter: '',
		limit: $rootScope.pageSize,
		page: 1,
		order: '-id'
	};
	function success(response) {
		$scope.response = response;
	}
	$scope.reLoadStuff = function () {
		$scope.promise = $timeout(function(){
			$scope.getResponse();
		}, 2000);
	}
	$scope.addItem = function (event) {
		$mdDialog.show({
			clickOutsideToClose: true,
			controller: 'addConsumptionController',
			controllerAs: 'ctrl',
			focusOnOpen: false,
			targetEvent: event,
			templateUrl: 'templates/org/order/add_consumption.html',
		}).then($scope.getResponse);
	};
	$scope.viewItem = function (event) {
		$mdDialog.show({
			clickOutsideToClose: true,
			controller: 'viewConsumptionController',
			controllerAs: 'ctrl',
			focusOnOpen: false,
			targetEvent: event,
			locals:{ items : event },
			templateUrl: 'templates/org/order/view_production_consumption.html',
		}).then($scope.getResponse);
	};
	
	$scope.getResponse = function () {
		$scope.promise = $apiurl.order.get($scope.query, success).$promise;
		$scope.selected = [];
	};
	
	$scope.onChangeDate= function(){
		$scope.startDate.setHours(0);
		$scope.endDate.setHours(24);
		$scope.query.startDate = $scope.startDate;
		$scope.query.endDate = $scope.endDate;
		$scope.getResponse();
	}
	

}]);
// ********************** CONSUMPTION CONTROLLER PART ENDED**************************

// ******************* PROFIT AND LOSS Statement start
app.controller('incomeStatementController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$rootScope', '$log', function ($timeout, $mdDialog, $apiurl, $scope, $rootScope, $log) {

	'use strict';
	$scope.asset = 0;
	$scope.liabelity= 0;
	$scope.equility = 0;
	$scope.revenue = 0;
	$scope.expense = 0;
	$scope.purchase = 0;
	$scope.sale = 0;
	
	function successAsset(response) {
		response.content.forEach(element=>{
			$scope.asset = $scope.asset + element.transactions.amount;
		})
	}
	function successLiabelity(response) {
		response.content.forEach(element=>{
			$scope.liabelity = $scope.liabelity + element.transactions.amount;
		})
	}
	function successEqility(response) {
		response.content.forEach(element=>{
			$scope.equility = $scope.equility + element.transactions.amount;
		})
	}
	function successRevenue(response) {
		response.content.forEach(element=>{
			$scope.revenue = $scope.revenue + element.transactions.amount;
		})
	}
	function successExpense(response) {
		response.content.forEach(element=>{
			$scope.expense = $scope.expense + element.transactions.amount;
		})
	}
	function successPurchase(response) {
		response.content.forEach(element=>{
			$scope.purchase = $scope.purchase + element.transactions.amount;
		})
	}
	function successSale(response) {
		response.content.forEach(element=>{
			$scope.sale = $scope.sale + element.transactions.amount;
		})
	}
	$scope.query = {
	  filter: '',
	  limit: 100000000,
	  page: 1,
	  order: 'id'
	};
	$scope.getResponse = function () {
	  $apiurl.profitloss.get({id:5, startDate: new Date(new Date().setDate(new Date().getDate()-30)), endDate: new Date(new Date().setDate(new Date().getDate()+1)),  ...$scope.query}).$promise;
	  $apiurl.profitloss.get({id:1, type: 'superParent', ...$scope.query}, successAsset).$promise;
	  $apiurl.profitloss.get({id:2, type: 'superParent', ...$scope.query}, successLiabelity).$promise;
	  $apiurl.profitloss.get({id:3, type: 'superParent', ...$scope.query}, successEqility).$promise;
	  $apiurl.profitloss.get({id:4, type: 'superParent', ...$scope.query}, successRevenue).$promise;
	  $apiurl.profitloss.get({id:5, type: 'superParent', ...$scope.query}, successExpense).$promise;
	  $apiurl.profitloss.get({id:1, type: 'transactionType', ...$scope.query}, successPurchase).$promise;
	  $apiurl.profitloss.get({id:2, type: 'transactionType', ...$scope.query}, successSale).$promise;
	};
	$scope.getResponse();
	  

	  $scope.getResponse = function () {
	    $scope.promise = $apiurl.stockItems.get($scope.query, success).$promise;
	    $scope.selected = [];
	  };
	 
	  function successProfit(response) {
		    $scope.profitResponse = response;
		    
		    var i;
		    var j;
		    for(i=0; i<response.content.length; i++){
		    	//console.log(response.content[i]);
		    	for(j=0; j<response.content[i].length; j++){
		    		if(j == 3){
		    			$scope.totalSaleAmt = $scope.totalSaleAmt + response.content[i][3];
			    		
		    		}
		    		
		    	}
		    }// end outer loop
		   // console.log($scope.totalSaleAmt);
	  }
	  
	  $scope.getProfitLoss = function () {
		    $scope.promise = $apiurl.profitloss.get($scope.queryWithFilter, successProfit).$promise;
	  };

	  
	  
	  
//	  var bookmark;
//	  $scope.selected = [];
//	  $scope.filter = {
//	    options: {
//	      debounce: 500
//	    }
//	  };
//	  $scope.query = {
//	    filter: '',
//	    limit: 100000000,
//	    page: 1,
//	    order: 'id'
//	  };
//	  
//	  $scope.startDate = new Date();
//	  $scope.startDate.setFullYear(2000);
//	  $scope.endDate = new Date();
//	  $scope.endDate.setHours(24);
//	  $scope.queryWithFilter = {
//		startDate: $scope.startDate,
//		endDate: $scope.endDate,
//		id: 0,
//	    filter: '',
//	    limit: 1000000,
//	    page: 1,
//	    order: 'id'
//	  };
//	  
//	  function success(response) {
//	    $scope.response = response;
//	    
//	  }
//	  $scope.reLoadStuff = function () {
//		    $scope.promise = $timeout(function(){
//		    	$scope.getResponse();
//		    }, 2000);
//		  }
//
//	  
//	  $scope.getResponse = function () {
//	    $scope.promise = $apiurl.stockItems.get($scope.query, success).$promise;
//	    $scope.selected = [];
//	  };
//	  $scope.getProfitLoss = function () {
//		    $scope.promise = $apiurl.profitloss.get($scope.queryWithFilter, success).$promise;
//		  };
//	  
//	  $scope.removeFilter = function () {
//	    $scope.filter.show = false;
//	    $scope.query.filter = '';
//	    if($scope.filter.form.$dirty) {
//	      $scope.filter.form.$setPristine();
//	    }
//	  };
//	  
//	  $scope.$watch('query.filter', function (newValue, oldValue) {
//	    if(!oldValue) {
//	      bookmark = $scope.query.page;
//	    }
//	    if(newValue !== oldValue) {
//	      $scope.query.page = 1;
//	    }
//	    if(!newValue) {
//	      $scope.query.page = bookmark;
//	    }
//	    $scope.getResponse();
//	    $scope.getProfitLoss();
//	  });
	}]);
// ************************ END profit and loss ctrl *****************

// start profit and lossstatement
app.controller('profitAndLossStatementController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$rootScope', '$log', function ($timeout, $mdDialog, $apiurl, $scope, $rootScope, $log) {
	  'use strict';

	  var bookmark;
	  $scope.selected = [];
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  $scope.query = {
	    filter: '',
	    limit: 100000000,
	    page: 1,
	    order: 'id'
	  };

	  $scope.startDate = new Date();
	  $scope.startDate.setFullYear(2000);
	  $scope.endDate = new Date();
	  $scope.endDate.setHours(24);
	  $scope.queryWithFilter = {
		startDate: $scope.startDate,
		endDate: $scope.endDate,
		id: 0,
	    filter: '',
	    limit: 1000000,
	    page: 1,
	    order: 'id'
	  };

	  function success(response) {
	    $scope.response = response;
	  }
	  
	  function successProfit(response) {
		    $scope.profit = response;
		  }

	  $scope.reLoadStuff = function () {
		    $scope.promise = $timeout(function(){
		    	$scope.getResponse();
		    }, 2000);
		  }
	  $scope.getResponse = function () {
		    $scope.promise = $apiurl.stockItems.get($scope.query, success).$promise;
		    $scope.selected = [];
		  };
	  $scope.getProfitLoss = function () {
			    $scope.promise = $apiurl.profitloss.get($scope.queryWithFilter, successProfit).$promise;
		  };
			  
	  $scope.getResponse();
	  $scope.getProfitLoss();
	}]);
// end profit loss statement


// ************************ start stock record book ctrl *************
app.controller('stockRecordBookController', ['$timeout','$mdDialog', '$apiurl', '$scope', '$rootScope', '$log', function ($timeout, $mdDialog, $apiurl, $scope, $rootScope, $log) {
	'use strict';
	  var bookmark;
	  var count=0;
	  

	  $scope.stock_query = {
	    filter: '',
	    limit: 99999999,
	    page: 1,
	    order: 'id'
	  };

	  function stock_success(response) {
	    $scope.stock_response = response;
	  }

	  $scope.getStockResponse = function () {
	    $scope.promise = $apiurl.stockItems.get($scope.stock_query, stock_success).$promise;
	  };
	  $scope.getStockResponse();
	  
	  $scope.selected = [];
	  $scope.filter = {
	    options: {
	      debounce: 500
	    }
	  };
	  
	  $scope.itemCounter = 0;
	  $scope.startDate = new Date();
	  $scope.startDate.setFullYear(2000);
	  $scope.endDate = new Date();
	  $scope.endDate.setHours(24);
	  
	  $scope.query = {
		startDate: $scope.startDate,
		endDate: $scope.endDate,
		by: 'quantity',
		id: 0,
	    filter: '',
	    limit: $rootScope.pageSize,
	    page: 1,
	    order: '-id'
	};
	  function success(response) {
			count=count+1;
			$scope.s=response.numberOfElements-1;
		    $scope.response = response;
			if(count==1){
				$scope.query.page=response.totalPages;
				$scope.getResponse();
				count=count+1;
			}
	  }
	  $scope.reLoadStuff = function () {
	    $scope.promise = $timeout(function(){
	    	$scope.getResponse();
	    }, 2000);
	  }
	  $scope.getResponse = function () {
	    $scope.promise = $apiurl.order.get($scope.query, success).$promise;
	    $scope.selected = [];
	  };

	  $scope.fetchResults = function(){
		  if($scope.response==null){
			  $scope.getResponse();
		  }
	  }
	  $scope.fetchResults();
	  
	  $scope.onChangeDate= function(){
		  $scope.startDate.setHours(0);
		  $scope.endDate.setHours(24);
		  $scope.query.startDate = $scope.startDate;
		  $scope.query.endDate = $scope.endDate;
		  $scope.getResponse();
	  }
	  
	  $scope.showItem=function(id){
		  $scope.query.id=id;
		  $scope.getResponse();
	  }

	}]);

