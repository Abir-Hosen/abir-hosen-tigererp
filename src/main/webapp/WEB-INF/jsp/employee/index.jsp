<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html ng-app="EmployeeApp">
<head>
<title>Employee Dashboard :: TigerERP</title>
<meta charset="UTF-8"> 
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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-chart.js/1.1.1/angular-chart.min.js "></script>
<link rel="stylesheet"
  href="<c:url value='/css/md-data-table.min.css' />">
<link rel="stylesheet"
  href="<c:url value='/css/style.css' />">
<link rel="stylesheet"
  href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,400italic">

<!-- For favicon -->
<link rel="apple-touch-icon" sizes="180x180" href="images/apple-touch-icon.png">
<link rel="icon" type="image/png" sizes="32x32" href="images/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="16x16" href="images/favicon-16x16.png">
<link rel="manifest" href="images/site.webmanifest">


<style>
</style>
<body ng-controller="EmployeeAppCtrl">

<div layout="column" layout-fill  ng-cloak>

  <section layout="row" flex>
    
    <md-sidenav  class="md-sidenav-left" md-component-id="left"  md-is-locked-open="$mdMedia('gt-md')" md-disable-backdrop md-whiteframe="4">
      <md-toolbar class="md-theme-indigo">
        <a ui-sref="{{urlMapping.helloState.name}}" ui-sref-active="active" class="tigererp-menu">
          <h1 class="md-toolbar-tools" style="color: #FFFFFF">{{'TIGER_ERP_MENU' | translate}}</h1>
        </a>
      </md-toolbar>
      <md-content layout-margin>
        &nbsp;
        <!-- Side menu start -->
       <md-list flex>
       
	       <md-list-item ng-click="toggle.list2 = !toggle.list2" ng-show="employeePrivileges.createPurchaseOrderSettings || 
       	employeePrivileges.updatePurchaseOrderSettings || employeePrivileges.readPurchaseOrderSettings || employeePrivileges.deletePurchaseOrderSettings ||
       	employeePrivileges.createSaleOrderSettings || 
       	employeePrivileges.updateSaleOrderSettings || employeePrivileges.readSaleOrderSettings || employeePrivileges.deleteSaleOrderSettings ||
       	employeePrivileges.createPurchaseOrderSettings || 
       	employeePrivileges.updatePurchaseOrderSettings || employeePrivileges.readPurchaseOrderSettings || employeePrivileges.deletePurchaseOrderSettings ||
       	employeePrivileges.createCancelSaleOrderSettings || 
       	employeePrivileges.updateCancelSaleOrderSettings || employeePrivileges.readCancelSaleOrderSettings || employeePrivileges.deleteCancelSaleOrderSettings">
	       <md-icon>list_alt</md-icon>
	       <span flex>{{'ORDER' | translate}}</span>
	       <md-icon ng-show="!toggle.list2">expand_more</md-icon>
	       <md-icon ng-show="toggle.list2">expand_less</md-icon>
	      </md-list-item>
	       <a ui-sref="{{urlMapping.purchaseOrderState.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.createPurchaseOrderSettings || 
       	employeePrivileges.updatePurchaseOrderSettings || employeePrivileges.readPurchaseOrderSettings || employeePrivileges.deletePurchaseOrderSettings">
	       <md-list-item  ng-show="toggle.list2" ng-click="null">
	       <span flex-offset="15"><md-icon>chevron_right</md-icon>{{'PURCHASE_ORDER' | translate}}</span>
	       </md-list-item>
	        </a>
	       <a ui-sref="{{urlMapping.saleOrderState.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.createSaleOrderSettings || 
       	employeePrivileges.updateSaleOrderSettings || employeePrivileges.readSaleOrderSettings || employeePrivileges.deleteSaleOrderSettings">
	       <md-list-item  ng-show="toggle.list2" ng-click="null">
	       <span flex-offset="15"><md-icon>chevron_right</md-icon>{{'SALE_ORDER' | translate}}</span>
	       </md-list-item>
	        </a>
	       <a ui-sref="{{urlMapping.cancelPurchaseOrderState.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.createPurchaseOrderSettings || 
       	employeePrivileges.updatePurchaseOrderSettings || employeePrivileges.readPurchaseOrderSettings || employeePrivileges.deletePurchaseOrderSettings">
	       <md-list-item  ng-show="toggle.list2" ng-click="null">
	       <span flex-offset="15"><md-icon>chevron_right</md-icon>{{'PURCHASE_CANCELATION' | translate}}</span>
	       </md-list-item>
	        </a>
	       <a ui-sref="{{urlMapping.cancelSaleOrderState.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.createCancelSaleOrderSettings || 
       	employeePrivileges.updateCancelSaleOrderSettings || employeePrivileges.readCancelSaleOrderSettings || employeePrivileges.deleteCancelSaleOrderSettings">
	       <md-list-item  ng-show="toggle.list2" ng-click="null">
	       <span flex-offset="15"><md-icon>chevron_right</md-icon>{{'SALE_CANCELATION' | translate}}</span>
	       </md-list-item>
	        </a>
       
	       <md-list-item ng-click="toggle.list5 = !toggle.list5" ng-show="employeePrivileges.createJournalVoucherSettings || 
       	employeePrivileges.updateJournalVoucherSettings || employeePrivileges.readJournalVoucherSettings || employeePrivileges.deleteJournalVoucherSettings || 
       	employeePrivileges.createContraVoucherSettings || 
       	employeePrivileges.updateContraVoucherSettings || employeePrivileges.readContraVoucherSettings || employeePrivileges.deleteContraVoucherSettings ||
       	employeePrivileges.createPaymentVoucherSettings || 
       	employeePrivileges.updatePaymentVoucherSettings || employeePrivileges.readPaymentVoucherSettings || employeePrivileges.deletePaymentVoucherSettings ||
       	employeePrivileges.createRecepientVoucherSettings || 
       	employeePrivileges.updateRecepientVoucherSettings || employeePrivileges.readRecepientVoucherSettings || employeePrivileges.deleteRecepientVoucherSettings ||
       	employeePrivileges.createAllVoucherSettings || 
       	employeePrivileges.updateAllVoucherSettings || employeePrivileges.readAllVoucherSettings || employeePrivileges.deleteAllVoucherSettings">
	       <md-icon>receipt</md-icon>
	       <span flex>{{'VOUCHER' | translate}}</span>
	       <md-icon ng-show="!toggle.list5">expand_more</md-icon>
	       <md-icon ng-show="toggle.list5">expand_less</md-icon>
	      </md-list-item>
	        <a ui-sref="{{urlMapping.journalVoucherSettingState.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.createJournalVoucherSettings || 
       	employeePrivileges.updateJournalVoucherSettings || employeePrivileges.readJournalVoucherSettings || employeePrivileges.deleteJournalVoucherSettings || 
       	employeePrivileges.createContraVoucherSettings || 
       	employeePrivileges.updateContraVoucherSettings || employeePrivileges.readContraVoucherSettings || employeePrivileges.deleteContraVoucherSettings ||
       	employeePrivileges.createPaymentVoucherSettings || 
       	employeePrivileges.updatePaymentVoucherSettings || employeePrivileges.readPaymentVoucherSettings || employeePrivileges.deletePaymentVoucherSettings ||
       	employeePrivileges.createRecepientVoucherSettings || 
       	employeePrivileges.updateRecepientVoucherSettings || employeePrivileges.readRecepientVoucherSettings || employeePrivileges.deleteRecepientVoucherSettings ||
       	employeePrivileges.createAllVoucherSettings || 
       	employeePrivileges.updateAllVoucherSettings || employeePrivileges.readAllVoucherSettings || employeePrivileges.deleteAllVoucherSettings">
	       <md-list-item  ng-show="toggle.list5" ng-click="null">
	       <span flex-offset="15"><md-icon>chevron_right</md-icon> {{'ALL_VOUCHER' | translate}} </span>
	       </md-list-item>
	        </a>
	        
	       <md-list-item ng-click="toggle.list6 = !toggle.list6" ng-show="employeePrivileges.createChartOfAccountingSettings || 
       	employeePrivileges.updateChartOfAccountingSettings || employeePrivileges.readChartOfAccountingSettings || employeePrivileges.deleteChartOfAccountingSettings ||
       	employeePrivileges.createLedgerGroupSettings || 
       	employeePrivileges.updateLedgerGroupSettings || employeePrivileges.readLedgerGroupSettings || employeePrivileges.deleteLedgerGroupSettings ||
       	employeePrivileges.createLedgerSettings || 
       	employeePrivileges.updateLedgerSettings || employeePrivileges.readLedgerSettings || employeePrivileges.deleteLedgerSettings ||
       	employeePrivileges.createBalanceSheetSettings || 
       	employeePrivileges.updateBalanceSheetSettings || employeePrivileges.readBalanceSheetSettings || employeePrivileges.deleteBalanceSheetSettings">
	       <md-icon>account_balance</md-icon>
	       <span flex>{{'ACCOUNTS' | translate}}</span>
	       <md-icon ng-show="!toggle.list6">expand_more</md-icon>
	       <md-icon ng-show="toggle.list6">expand_less</md-icon>
	      </md-list-item>
	       <a ui-sref="{{urlMapping.accountChartState.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.createChartOfAccountingSettings || 
       	employeePrivileges.updateChartOfAccountingSettings || employeePrivileges.readChartOfAccountingSettings || employeePrivileges.deleteChartOfAccountingSettings">
	       <md-list-item  ng-show="toggle.list6" ng-click="null">
	       <span flex-offset="15"><md-icon>chevron_right</md-icon>{{'CHART_OF_ACCOUNTING' | translate}}</span>
	       </md-list-item>
	        </a>
		    <a ui-sref="{{urlMapping.ledgerGroupSettingState.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.createLedgerGroupSettings || 
       	employeePrivileges.updateLedgerGroupSettings || employeePrivileges.readLedgerGroupSettings || employeePrivileges.deleteLedgerGroupSettings">
	       <md-list-item  ng-show="toggle.list6" ng-click="null">
	       <span flex-offset="15"><md-icon>chevron_right</md-icon> {{'LEDGER_GROUP' | translate}}</span>
	       </md-list-item>
	        </a>
		    <a ui-sref="{{urlMapping.ledgerState.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.createLedgerSettings || 
       	employeePrivileges.updateLedgerSettings || employeePrivileges.readLedgerSettings || employeePrivileges.deleteLedgerSettings">
	       <md-list-item  ng-show="toggle.list6" ng-click="null">
	       <span flex-offset="15"><md-icon>chevron_right</md-icon> {{'LEDGER' | translate}}</span>
	       </md-list-item>
	        </a>
		    <a ui-sref="{{urlMapping.ledgerBalanceSheet.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.createBalanceSheetSettings || 
       	employeePrivileges.updateBalanceSheetSettings || employeePrivileges.readBalanceSheetSettings || employeePrivileges.deleteBalanceSheetSettings">
	       <md-list-item  ng-show="toggle.list6" ng-click="null">
	       <span flex-offset="15"><md-icon>chevron_right</md-icon> {{'BALANCE_SHEET' | translate}}</span>
	       </md-list-item>
	        </a>
      
       <md-list-item ng-click="toggle.list1 = !toggle.list1" ng-show="employeePrivileges.createRole || 
       	employeePrivileges.updateRole || employeePrivileges.readRole || employeePrivileges.deleteRole ||
       	employeePrivileges.createEmployeeCategory || 
       	employeePrivileges.updateEmployeeCategory || employeePrivileges.readEmployeeCategory || employeePrivileges.deleteEmployeeCategory ||
       	employeePrivileges.createAccessPermissionSettings || 
       	employeePrivileges.updateAccessPermissionSettings || employeePrivileges.readAccessPermissionSettings || employeePrivileges.deleteAccessPermissionSettings ||
       	employeePrivileges.createEmployee || 
       	employeePrivileges.updateEmployee || employeePrivileges.readEmployee || employeePrivileges.deleteEmployee || employeePrivileges.updateGlobalPasswordSettings">
        <md-icon>people</md-icon>
        <span flex>{{'HR_PAYROLE' | translate}}</span>
        <md-icon ng-show="!toggle.list1">expand_more</md-icon>
        <md-icon ng-show="toggle.list1">expand_less</md-icon>
       </md-list-item>
       
       <a ui-sref="{{urlMapping.employeeRoleState.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.createRole || 
       	employeePrivileges.updateRole || employeePrivileges.readRole || employeePrivileges.deleteRole">
       <md-list-item  ng-show="toggle.list1"" ng-click="null">
       <span flex-offset="15"><md-icon>chevron_right</md-icon>{{'EMPLOYEE_ROLE' | translate}}</span>
      </md-list-item>
      </a>
      <a ui-sref="{{urlMapping.employeeCategoryState.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.createEmployeeCategory || 
       	employeePrivileges.updateEmployeeCategory || employeePrivileges.readEmployeeCategory || employeePrivileges.deleteEmployeeCategory">
       <md-list-item  ng-show="toggle.list1" ng-click="null">
       <span flex-offset="15"><md-icon>chevron_right</md-icon> {{'EMPLOYEE_CATEGORY' | translate}}</span>
      </md-list-item>
      </a>
      <a ui-sref="{{urlMapping.accessPermissionState.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.createAccessPermissionSettings || 
       	employeePrivileges.updateAccessPermissionSettings || employeePrivileges.readAccessPermissionSettings || employeePrivileges.deleteAccessPermissionSettings">
       <md-list-item  ng-show="toggle.list1" ng-click="null">
       <span flex-offset="15"><md-icon>chevron_right</md-icon>{{'ACCESS_PERMISSION' | translate}}</span>
      </md-list-item>
      </a>
      <a ui-sref="{{urlMapping.employeeManageState.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.createEmployee || 
       	employeePrivileges.updateEmployee || employeePrivileges.readEmployee || employeePrivileges.deleteEmployee">
       <md-list-item  ng-show="toggle.list1" ng-click="null">
       <span flex-offset="15"><md-icon>chevron_right</md-icon> {{'EMPLOYEE' | translate}}</span>
      </md-list-item>
      </a>
      <a ui-sref="{{urlMapping.passwordEmailResetRequestState.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.updateGlobalPasswordSettings">
       <md-list-item  ng-show="toggle.list1" ng-click="null">
       <span flex-offset="15"><md-icon>chevron_right</md-icon> {{'PASSWORD_EMAIL_RESET' | translate}}</span>
      </md-list-item>
      </a>
      
       <md-list-item ng-click="toggle.list4 = !toggle.list4" ng-show="employeePrivileges.createStockItemSettings || 
       	employeePrivileges.updateStockItemSettings || employeePrivileges.readStockItemSettings || employeePrivileges.deleteStockItemSettings ||
       	employeePrivileges.createStockInSettings || 
       	employeePrivileges.updateStockInSettings || employeePrivileges.readStockInSettings || employeePrivileges.deleteStockInSettings ||
       	employeePrivileges.createStockOutSettings || 
       	employeePrivileges.updateStockOutSettings || employeePrivileges.readStockOutSettings || employeePrivileges.deleteStockOutSettings ||
       	employeePrivileges.createStockCategory || 
       	employeePrivileges.updateStockCategory || employeePrivileges.readStockCategory || employeePrivileges.deleteStockCategory ||
       	employeePrivileges.createUnitMeasure || 
       	employeePrivileges.updateUnitMeasure || employeePrivileges.readUnitMeasure || employeePrivileges.deleteUnitMeasure ||
       	employeePrivileges.createBranchSettings || 
       	employeePrivileges.updateBranchSettings || employeePrivileges.readBranchSettings || employeePrivileges.deleteBranchSettings ||
       	employeePrivileges.createRoomSettings || 
       	employeePrivileges.updateRoomSettings || employeePrivileges.readRoomSettings || employeePrivileges.deleteRoomSettings ||
       	employeePrivileges.createStockRecordBookSettings || 
       	employeePrivileges.updateStockRecordBookSettings || employeePrivileges.readStockRecordBookSettings || employeePrivileges.deleteStockRecordBookSettings">
       <md-icon>store</md-icon>
       <span flex>{{'INVENTORY' | translate}}</span>
       <md-icon ng-show="!toggle.list4">expand_more</md-icon>
       <md-icon ng-show="toggle.list4">expand_less</md-icon>
      </md-list-item>
       <a ui-sref="{{urlMapping.stockItem.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.createStockItemSettings || 
       	employeePrivileges.updateStockItemSettings || employeePrivileges.readStockItemSettings || employeePrivileges.deleteStockItemSettings">
       <md-list-item  ng-show="toggle.list4" ng-click="null">
       <span flex-offset="15"><md-icon>chevron_right</md-icon> {{'STOCK_ITEM' | translate}}</span>
       </md-list-item>
        </a>
       <a ui-sref="{{urlMapping.stockRecordBookState.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.createStockRecordBookSettings || 
       	employeePrivileges.updateStockRecordBookSettings || employeePrivileges.readStockRecordBookSettings || employeePrivileges.deleteStockRecordBookSettings">
       <md-list-item  ng-show="toggle.list4" ng-click="null">
       <span flex-offset="15"><md-icon>chevron_right</md-icon> {{'STOCK_RECORD_BOOK' | translate}}</span>
       </md-list-item>
        </a>
       <a ui-sref="{{urlMapping.stockIn.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.createStockInSettings || 
       	employeePrivileges.updateStockInSettings || employeePrivileges.readStockInSettings || employeePrivileges.deleteStockInSettings">
       <md-list-item  ng-show="toggle.list4" ng-click="null">
       <span flex-offset="15"><md-icon>chevron_right</md-icon> {{'STOCK_IN' | translate}}</span>
       </md-list-item>
        </a>
       <a ui-sref="{{urlMapping.stockOut.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.createStockOutSettings || 
       	employeePrivileges.updateStockOutSettings || employeePrivileges.readStockOutSettings || employeePrivileges.deleteStockOutSettings">
       <md-list-item  ng-show="toggle.list4" ng-click="null">
       <span flex-offset="15"><md-icon>chevron_right</md-icon> {{'STOCK_OUT' | translate}}</span>
       </md-list-item>
        </a>
       <a ui-sref="{{urlMapping.stockCategoryState.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.createStockCategory || 
       	employeePrivileges.updateStockCategory || employeePrivileges.readStockCategory || employeePrivileges.deleteStockCategory">
       <md-list-item  ng-show="toggle.list4" ng-click="null">
       <span flex-offset="15"><md-icon>chevron_right</md-icon> {{'STOCK_CATEGORY' | translate}}</span>
       </md-list-item>
        </a>
       <a ui-sref="{{urlMapping.unitsState.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.createUnitMeasure || 
       	employeePrivileges.updateUnitMeasure || employeePrivileges.readUnitMeasure || employeePrivileges.deleteUnitMeasure">
       <md-list-item  ng-show="toggle.list4" ng-click="null">
       <span flex-offset="15"><md-icon>chevron_right</md-icon> {{'UNIT' | translate}}</span>
       </md-list-item>
        </a>
       <a ui-sref="{{urlMapping.branchSettingState.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.createBranchSettings || 
       	employeePrivileges.updateBranchSettings || employeePrivileges.readBranchSettings || employeePrivileges.deleteBranchSettings">
       <md-list-item  ng-show="toggle.list4" ng-click="null">
       <span flex-offset="15"><md-icon>chevron_right</md-icon> {{'BRANCH_SETTINGS' | translate}}</span>
       </md-list-item>
        </a>
       <a ui-sref="{{urlMapping.roomSettingState.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.createRoomSettings || 
       	employeePrivileges.updateRoomSettings || employeePrivileges.readRoomSettings || employeePrivileges.deleteRoomSettings">
       <md-list-item  ng-show="toggle.list4" ng-click="null">
       <span flex-offset="15"><md-icon>chevron_right</md-icon> {{'ROOM_SETTINGS' | translate}}</span>
       </md-list-item>
        </a>
        
       <md-list-item ng-click="toggle.list8 = !toggle.list8" ng-show="employeePrivileges.createProductionSettings || employeePrivileges.readProductionSettings || employeePrivileges.updateProductionSettings || employeePrivileges.deleteProductionSettings || employeePrivileges.createConsumptionSettings || employeePrivileges.readConsumptionSettings || employeePrivileges.updateConsumptionSettings || employeePrivileges.deleteConsumptionSettings">
        <md-icon>corporate_fare</md-icon>
        <span flex>{{'MENUFACTURE' | translate}}</span>
        <md-icon ng-show="!toggle.list8">expand_more</md-icon>
        <md-icon ng-show="toggle.list8">expand_less</md-icon>
       </md-list-item>
       
       <a ui-sref="{{urlMapping.productionState.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.createProductionSettings || employeePrivileges.readProductionSettings || employeePrivileges.updateProductionSettings || employeePrivileges.deleteProductionSettings">
       <md-list-item  ng-show="toggle.list8" ng-click="null">
       <span flex-offset="15"><md-icon>chevron_right</md-icon>{{'PRODUCTION' | translate}}</span>
      </md-list-item>
      </a>
       <a ui-sref="{{urlMapping.consumptionState.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.createConsumptionSettings || employeePrivileges.readConsumptionSettings || employeePrivileges.updateConsumptionSettings || employeePrivileges.deleteConsumptionSettings">
       <md-list-item  ng-show="toggle.list8" ng-click="null">
       <span flex-offset="15"><md-icon>chevron_right</md-icon>{{'CONSUMPTION' | translate}}</span>
      </md-list-item>
      </a>
        
       <md-list-item ng-click="toggle.list7 = !toggle.list7" ng-show="employeePrivileges.readProfitLossStatementSettings || employeePrivileges.readSmsSettings">
       <md-icon>report</md-icon>
       <span flex>{{'REPORTS' | translate}}</span>
       <md-icon ng-show="!toggle.list7">expand_more</md-icon>
       <md-icon ng-show="toggle.list7">expand_less</md-icon>
      </md-list-item>
      <a ui-sref="{{urlMapping.profitAndLossStatementState.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.readProfitLossStatementSettings">
       <md-list-item  ng-show="toggle.list7" ng-click="null">
       <span flex-offset="15"><md-icon>chevron_right</md-icon> {{'PROFIT_LOSS_STATEMENT' | translate}}</span>
       </md-list-item>
        </a>
       <a ui-sref="{{urlMapping.incomeStatementState.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.readProfitLossStatementSettings">
       <md-list-item  ng-show="toggle.list7" ng-click="null">
       <span flex-offset="15"><md-icon>chevron_right</md-icon> {{'INCOME' | translate}}</span>
       </md-list-item>
        </a>
       <a ui-sref="{{urlMapping.smsState.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.readSmsSettings">
       <md-list-item  ng-show="toggle.list7" ng-click="null">
       <span flex-offset="15"><md-icon>chevron_right</md-icon> {{'SMS_LOG' | translate}}</span>
       </md-list-item>
        </a>
      
      <md-list-item ng-click="toggle.list3 = !toggle.list3" ng-show="employeePrivileges.createCompanySettings || employeePrivileges.updateCompanySettings || employeePrivileges.readCompanySettings || employeePrivileges.deleteCompanySettings || employeePrivileges.createMediaAndDocSettings || employeePrivileges.updateMediaAndDocSettings || employeePrivileges.readMediaAndDocSettings || employeePrivileges.deleteMediaAndDocSettings">
       <md-icon>settings</md-icon>
       <span flex>{{'SETTINGS' | translate}}</span>
       <md-icon ng-show="!toggle.list3">expand_more</md-icon>
       <md-icon ng-show="toggle.list3">expand_less</md-icon>
      </md-list-item>
       <a ui-sref="{{urlMapping.companySettingState.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.createCompanySettings || employeePrivileges.updateCompanySettings || employeePrivileges.readCompanySettings || employeePrivileges.deleteCompanySettings">
       <md-list-item  ng-show="toggle.list3" ng-click="null">
       <span flex-offset="15"><md-icon>chevron_right</md-icon> {{'COMPANY_SETTINGS' | translate}}</span>
       </md-list-item>
        </a>
       <a ui-sref="{{urlMapping.imageAndLogoSettingState.name}}" ui-sref-active="active" class="tigererp-menu" ng-show="employeePrivileges.createMediaAndDocSettings || employeePrivileges.updateMediaAndDocSettings || employeePrivileges.readMediaAndDocSettings || employeePrivileges.deleteMediaAndDocSettings">
     	  <md-list-item  ng-show="toggle.list3"" ng-click="null">
       <span flex-offset="15"><md-icon>chevron_right</md-icon> {{'IMAGE_AND_LOGO_SETTINGS' | translate}}</span>
       </md-list-item>
        </a>
      
    </md-list>
        <!-- Side menu end -->
        <md-button ng-click="toggleLeft()" class="md-accent">
          {{'CLOSE_THE_SIDES' | translate}}
        </md-button>
      </md-content>

    </md-sidenav>
    
   <div layout="column" flex>
  <md-toolbar md-scroll-shrink class="md-hue-2">
      <div class="md-toolbar-tools">
        <md-button class="md-icon-button" aria-label="Settings" ng-disabled="true">
          <md-icon></md-icon>
        </md-button>
    <div flex md-truncate>
           <md-button class="md-icon-button" ng-click="toggleLeft()"><md-icon>menu</md-icon></md-button>
        </div>

	    <md-select placeholder="Change language" name="languageSelect" ng-model="languageSelect" style="min-width: 40px; color: #FFFFFF" ng-change="ChangeLang(languageSelect.name)">
	      <md-option ng-value="role" ng-repeat="role in language">{{role.name}}</md-option>
	    </md-select>
	    
        <!-- Drop down Menu user option start -->
        <md-menu md-position-mode="target-right target">
         <md-button aria-label="User profile" class="md-icon-button" ng-click="openMenu($mdMenu, $event)">
          <md-icon><i class="material-icons">more_vert</i></md-icon>
         </md-button>
         <md-menu-content width="4">
         <a ui-sref="{{urlMapping.changePasswordState.name}}" ui-sref-active="active" class="tigererp-menu">
          <md-menu-item>
          <md-button ng-click="ctrl.redial($event)">
            <md-icon md-menu-align-target>security</md-icon>
              {{'CHANGE_PASSWORD' | translate}}
          </md-button>
         </md-menu-item>
         </a>
         <md-menu-divider></md-menu-divider>
         <a href="${pageContext.request.contextPath}/logout" class="tigererp-menu">
         <md-menu-item>
         <md-button>
            <md-icon md-menu-align-target>exit_to_app</md-icon>
            {{'LOGOUT' | translate}}
          </md-button>
        </md-menu-item>
        </a>
        <md-menu-divider></md-menu-divider>
      </md-menu-content>
    </md-menu>
    <!-- Drop down menu user opt end -->
      </div>
    </md-toolbar>
    <md-progress-linear md-mode="indeterminate" ng-show="showProgressBar"></md-progress-linear>
        <div layout="column" flex>
             <md-content>
    		  <ui-view></ui-view>       
      		 </md-content>
        </div> 
      </div>
  </section>
</div>

<script src="<c:url value='/js/tigererp.js' />"></script>
<script src="<c:url value='/js/employeerole_controller.js' />"></script>

</body>
</html>