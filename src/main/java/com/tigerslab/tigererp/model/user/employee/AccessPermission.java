package com.tigerslab.tigererp.model.user.employee;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.tigerslab.tigererp.model.AbstractData;

@Component
@Entity
@Table(name="accessPermission")
public class AccessPermission extends AbstractData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accessPermmissionidgenerator")
	@SequenceGenerator(initialValue = 1, name = "accessPermmissionidgenerator", sequenceName = "accessPermissionSeq")
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name="createRole", nullable = false, columnDefinition="bit default 0")
	private Boolean createRole=false;
	
	@Column(name="readRole", nullable = false, columnDefinition="bit default 0")
	private Boolean readRole=false;
	
	@Column(name="updateRole", nullable = false, columnDefinition="bit default 0")
	private Boolean updateRole=false;
	
	@Column(name="deleteRole", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteRole=false;
	
	@Column(name="createEmployeeCategory", nullable = false, columnDefinition="bit default 0")
	private Boolean createEmployeeCategory=false;
	
	@Column(name="readEmployeeCategory", nullable = false, columnDefinition="bit default 0")
	private Boolean readEmployeeCategory=false;
	
	@Column(name="updateEmployeeCategory", nullable = false, columnDefinition="bit default 0")
	private Boolean updateEmployeeCategory=false;
	
	@Column(name="deleteEmployeeCategory", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteEmployeeCategory=false;
	
	@Column(name="createEmployee", nullable = false, columnDefinition="bit default 0")
	private Boolean createEmployee=false;
	
	@Column(name="readEmployee", nullable = false, columnDefinition="bit default 0")
	private Boolean readEmployee=false;
	
	@Column(name="updateEmployee", nullable = false, columnDefinition="bit default 0")
	private Boolean updateEmployee=false;
	
	@Column(name="deleteEmployee", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteEmployee=false;
	
	@Column(name="createCompanySettings", nullable = false, columnDefinition="bit default 0")
	private Boolean createCompanySettings=false;
	
	@Column(name="readCompanySettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readCompanySettings=false;
	
	@Column(name="updateCompanySettings", nullable = false, columnDefinition="bit default 0")
	private Boolean updateCompanySettings=false;
	
	@Column(name="deleteCompanySettings", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteCompanySettings=false;
	
	@Column(name="createMediaAndDocSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean createMediaAndDocSettings=false;
	
	@Column(name="readMediaAndDocSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readMediaAndDocSettings=false;
	
	@Column(name="updateMediaAndDocSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean updateMediaAndDocSettings=false;
	
	@Column(name="deleteMediaAndDocSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteMediaAndDocSettings=false;

	@Column(name="createStockCategory", nullable = false, columnDefinition="bit default 0")
	private Boolean createStockCategory=false;
	
	@Column(name="readStockCategory", nullable = false, columnDefinition="bit default 0")
	private Boolean readStockCategory=false;
	
	@Column(name="updateStockCategory", nullable = false, columnDefinition="bit default 0")
	private Boolean updateStockCategory=false;
	
	@Column(name="deleteStockCategory", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteStockCategory=false;
	
	@Column(name="createUnitMeasure", nullable = false, columnDefinition="bit default 0")
	private Boolean createUnitMeasure=false;
	
	@Column(name="readUnitMeasure", nullable = false, columnDefinition="bit default 0")
	private Boolean readUnitMeasure=false;
	
	@Column(name="updateUnitMeasure", nullable = false, columnDefinition="bit default 0")
	private Boolean updateUnitMeasure=false;
	
	@Column(name="deleteUnitMeasure", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteUnitMeasure=false;

	@Column(name="createBranchSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean createBranchSettings=false;
	
	@Column(name="readBranchSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readBranchSettings=false;
	
	@Column(name="updateBranchSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean updateBranchSettings=false;
	
	@Column(name="deleteBranchSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteBranchSettings=false;

	@Column(name="createRoomSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean createRoomSettings=false;
	
	@Column(name="readRoomSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readRoomSettings=false;
	
	@Column(name="updateRoomSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean updateRoomSettings=false;
	
	@Column(name="deleteRoomSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteRoomSettings=false;
	
	// NEW
	
	//Ex 1
	@Column(name="createAllOrderSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean createAllOrderSettings=false;
	
	@Column(name="readAllOrderSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readAllOrderSettings=false;
	
	@Column(name="updateAllOrderSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean updateAllOrderSettings=false;
	
	@Column(name="deleteAllOrderSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteAllOrderSettings=false;
	
	//Ex 2
	@Column(name="readInvoiceSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readInvoiceSettings=false;
	
	//Ex 3
	@Column(name="createLedgerEntriesSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean createLedgerEntriesSettings=false;
	
	@Column(name="readLedgerEntriesSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readLedgerEntriesSettings=false;
	
	@Column(name="updateLedgerEntriesSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean updateLedgerEntriesSettings=false;
	
	@Column(name="deleteLedgerEntriesSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteLedgerEntriesSettings=false;

	//1
	@Column(name="createPurchaseOrderSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean createPurchaseOrderSettings=false;
	
	@Column(name="readPurchaseOrderSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readPurchaseOrderSettings=false;
	
	@Column(name="updatePurchaseOrderSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean updatePurchaseOrderSettings=false;
	
	@Column(name="deletePurchaseOrderSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean deletePurchaseOrderSettings=false;

	//2
	@Column(name="createSaleOrderSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean createSaleOrderSettings=false;
	
	@Column(name="readSaleOrderSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readSaleOrderSettings=false;
	
	@Column(name="updateSaleOrderSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean updateSaleOrderSettings=false;
	
	@Column(name="deleteSaleOrderSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteSaleOrderSettings=false;
	
	//3
	@Column(name="createCancelPurchaseOrderSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean createCancelPurchaseOrderSettings=false;
	
	@Column(name="readCancelPurchaseOrderSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readCancelPurchaseOrderSettings=false;
	
	@Column(name="updateCancelPurchaseOrderSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean updateCancelPurchaseOrderSettings=false;
	
	@Column(name="deleteCancelPurchaseOrderSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteCancelPurchaseOrderSettings=false;
	
	//4
	@Column(name="createCancelSaleOrderSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean createCancelSaleOrderSettings=false;
	
	@Column(name="readCancelSaleOrderSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readCancelSaleOrderSettings=false;
	
	@Column(name="updateCancelSaleOrderSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean updateCancelSaleOrderSettings=false;
	
	@Column(name="deleteCancelSaleOrderSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteCancelSaleOrderSettings=false;
	
	//5
	@Column(name="createRecepientVoucherSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean createRecepientVoucherSettings=false;
	
	@Column(name="readRecepientVoucherSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readRecepientVoucherSettings=false;
	
	@Column(name="updateRecepientVoucherSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean updateRecepientVoucherSettings=false;
	
	@Column(name="deleteRecepientVoucherSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteRecepientVoucherSettings=false;

	//6
	@Column(name="createPaymentVoucherSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean createPaymentVoucherSettings=false;
	
	@Column(name="readPaymentVoucherSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readPaymentVoucherSettings=false;
	
	@Column(name="updatePaymentVoucherSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean updatePaymentVoucherSettings=false;
	
	@Column(name="deletePaymentVoucherSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean deletePaymentVoucherSettings=false;

	//7
	@Column(name="createContraVoucherSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean createContraVoucherSettings=false;
	
	@Column(name="readContraVoucherSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readContraVoucherSettings=false;
	
	@Column(name="updateContraVoucherSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean updateContraVoucherSettings=false;
	
	@Column(name="deleteContraVoucherSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteContraVoucherSettings=false;

	//8
	@Column(name="createExpenseVoucherSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean createExpenseVoucherSettings=false;
	
	@Column(name="readExpenseVoucherSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readExpenseVoucherSettings=false;
	
	@Column(name="updateExpenseVoucherSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean updateExpenseVoucherSettings=false;
	
	@Column(name="deleteExpenseVoucherSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteExpenseVoucherSettings=false;

	//9
	@Column(name="createJournalVoucherSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean createJournalVoucherSettings=false;
	
	@Column(name="readJournalVoucherSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readJournalVoucherSettings=false;
	
	@Column(name="updateJournalVoucherSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean updateJournalVoucherSettings=false;
	
	@Column(name="deleteJournalVoucherSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteJournalVoucherSettings=false;

	//9.1
	@Column(name="createAllVoucherSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean createAllVoucherSettings=false;
	
	@Column(name="readAllVoucherSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readAllVoucherSettings=false;
	
	@Column(name="updateAllVoucherSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean updateAllVoucherSettings=false;
	
	@Column(name="deleteAllVoucherSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteAllVoucherSettings=false;

	//10
	@Column(name="createChartOfAccountingSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean createChartOfAccountingSettings=false;
	
	@Column(name="readChartOfAccountingSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readChartOfAccountingSettings=false;
	
	@Column(name="updateChartOfAccountingSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean updateChartOfAccountingSettings=false;
	
	@Column(name="deleteChartOfAccountingSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteChartOfAccountingSettings=false;

	//11
	@Column(name="createLedgerGroupSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean createLedgerGroupSettings=false;
	
	@Column(name="readLedgerGroupSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readLedgerGroupSettings=false;
	
	@Column(name="updateLedgerGroupSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean updateLedgerGroupSettings=false;
	
	@Column(name="deleteLedgerGroupSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteLedgerGroupSettings=false;

	//12
	@Column(name="createLedgerSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean createLedgerSettings=false;
	
	@Column(name="readLedgerSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readLedgerSettings=false;
	
	@Column(name="updateLedgerSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean updateLedgerSettings=false;
	
	@Column(name="deleteLedgerSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteLedgerSettings=false;

	//13
	@Column(name="createBalanceSheetSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean createBalanceSheetSettings=false;
	
	@Column(name="readBalanceSheetSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readBalanceSheetSettings=false;
	
	@Column(name="updateBalanceSheetSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean updateBalanceSheetSettings=false;
	
	@Column(name="deleteBalanceSheetSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteBalanceSheetSettings=false;

	//14
	@Column(name="createStockItemSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean createStockItemSettings=false;
	
	@Column(name="readStockItemSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readStockItemSettings=false;
	
	@Column(name="updateStockItemSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean updateStockItemSettings=false;
	
	@Column(name="deleteStockItemSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteStockItemSettings=false;

	//15
	@Column(name="createStockInSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean createStockInSettings=false;
	
	@Column(name="readStockInSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readStockInSettings=false;
	
	@Column(name="updateStockInSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean updateStockInSettings=false;
	
	@Column(name="deleteStockInSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteStockInSettings=false;

	//16
	@Column(name="createStockOutSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean createStockOutSettings=false;
	
	@Column(name="readStockOutSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readStockOutSettings=false;
	
	@Column(name="updateStockOutSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean updateStockOutSettings=false;
	
	@Column(name="deleteStockOutSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteStockOutSettings=false;

	//17
	@Column(name="createAccessPermissionSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean createAccessPermissionSettings=false;
	
	@Column(name="readAccessPermissionSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readAccessPermissionSettings=false;
	
	@Column(name="updateAccessPermissionSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean updateAccessPermissionSettings=false;
	
	@Column(name="deleteAccessPermissionSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteAccessPermissionSettings=false;

	//18
	@Column(name="createProductionSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean createProductionSettings=false;
	
	@Column(name="readProductionSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readProductionSettings=false;
	
	@Column(name="updateProductionSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean updateProductionSettings=false;
	
	@Column(name="deleteProductionSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteProductionSettings=false;

	//19
	@Column(name="createConsumptionSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean createConsumptionSettings=false;
	
	@Column(name="readConsumptionSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readConsumptionSettings=false;
	
	@Column(name="updateConsumptionSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean updateConsumptionSettings=false;
	
	@Column(name="deleteConsumptionSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean deleteConsumptionSettings=false;

	//20
	@Column(name="readProfitLossStatementSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readProfitLossStatementSettings=false;
	
	@Column(name="readStockRecordBookSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readStockRecordBookSettings=false;
	
	@Column(name="updateGlobalPasswordSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean updateGlobalPasswordSettings=false;
	
	@Column(name="readSmsSettings", nullable = false, columnDefinition="bit default 0")
	private Boolean readSmsSettings=false;
	
	@OneToOne(cascade=CascadeType.MERGE)
	@JoinTable(name="employeeAccessPermission", joinColumns=@JoinColumn(name="permission_id"), inverseJoinColumns=@JoinColumn(name="role_id"))
	private EmployeeRole employeeRole;

	public AccessPermission() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getCreateRole() {
		return createRole;
	}

	public void setCreateRole(Boolean createRole) {
		this.createRole = createRole;
	}

	public Boolean getReadRole() {
		return readRole;
	}

	public void setReadRole(Boolean readRole) {
		this.readRole = readRole;
	}

	public Boolean getUpdateRole() {
		return updateRole;
	}

	public void setUpdateRole(Boolean updateRole) {
		this.updateRole = updateRole;
	}

	public Boolean getDeleteRole() {
		return deleteRole;
	}

	public void setDeleteRole(Boolean deleteRole) {
		this.deleteRole = deleteRole;
	}

	public Boolean getCreateEmployeeCategory() {
		return createEmployeeCategory;
	}

	public void setCreateEmployeeCategory(Boolean createEmployeeCategory) {
		this.createEmployeeCategory = createEmployeeCategory;
	}

	public Boolean getReadEmployeeCategory() {
		return readEmployeeCategory;
	}

	public void setReadEmployeeCategory(Boolean readEmployeeCategory) {
		this.readEmployeeCategory = readEmployeeCategory;
	}

	public Boolean getUpdateEmployeeCategory() {
		return updateEmployeeCategory;
	}

	public void setUpdateEmployeeCategory(Boolean updateEmployeeCategory) {
		this.updateEmployeeCategory = updateEmployeeCategory;
	}

	public Boolean getDeleteEmployeeCategory() {
		return deleteEmployeeCategory;
	}

	public void setDeleteEmployeeCategory(Boolean deleteEmployeeCategory) {
		this.deleteEmployeeCategory = deleteEmployeeCategory;
	}

	public Boolean getCreateEmployee() {
		return createEmployee;
	}

	public void setCreateEmployee(Boolean createEmployee) {
		this.createEmployee = createEmployee;
	}

	public Boolean getReadEmployee() {
		return readEmployee;
	}

	public void setReadEmployee(Boolean readEmployee) {
		this.readEmployee = readEmployee;
	}

	public Boolean getUpdateEmployee() {
		return updateEmployee;
	}

	public void setUpdateEmployee(Boolean updateEmployee) {
		this.updateEmployee = updateEmployee;
	}

	public Boolean getDeleteEmployee() {
		return deleteEmployee;
	}

	public void setDeleteEmployee(Boolean deleteEmployee) {
		this.deleteEmployee = deleteEmployee;
	}

	public Boolean getCreateCompanySettings() {
		return createCompanySettings;
	}

	public void setCreateCompanySettings(Boolean createCompanySettings) {
		this.createCompanySettings = createCompanySettings;
	}

	public Boolean getReadCompanySettings() {
		return readCompanySettings;
	}

	public void setReadCompanySettings(Boolean readCompanySettings) {
		this.readCompanySettings = readCompanySettings;
	}

	public Boolean getUpdateCompanySettings() {
		return updateCompanySettings;
	}

	public void setUpdateCompanySettings(Boolean updateCompanySettings) {
		this.updateCompanySettings = updateCompanySettings;
	}

	public Boolean getDeleteCompanySettings() {
		return deleteCompanySettings;
	}

	public void setDeleteCompanySettings(Boolean deleteCompanySettings) {
		this.deleteCompanySettings = deleteCompanySettings;
	}

	public Boolean getCreateMediaAndDocSettings() {
		return createMediaAndDocSettings;
	}

	public void setCreateMediaAndDocSettings(Boolean createMediaAndDocSettings) {
		this.createMediaAndDocSettings = createMediaAndDocSettings;
	}

	public Boolean getReadMediaAndDocSettings() {
		return readMediaAndDocSettings;
	}

	public void setReadMediaAndDocSettings(Boolean readMediaAndDocSettings) {
		this.readMediaAndDocSettings = readMediaAndDocSettings;
	}

	public Boolean getUpdateMediaAndDocSettings() {
		return updateMediaAndDocSettings;
	}

	public void setUpdateMediaAndDocSettings(Boolean updateMediaAndDocSettings) {
		this.updateMediaAndDocSettings = updateMediaAndDocSettings;
	}

	public Boolean getDeleteMediaAndDocSettings() {
		return deleteMediaAndDocSettings;
	}

	public void setDeleteMediaAndDocSettings(Boolean deleteMediaAndDocSettings) {
		this.deleteMediaAndDocSettings = deleteMediaAndDocSettings;
	}

	public Boolean getCreateStockCategory() {
		return createStockCategory;
	}

	public void setCreateStockCategory(Boolean createStockCategory) {
		this.createStockCategory = createStockCategory;
	}

	public Boolean getReadStockCategory() {
		return readStockCategory;
	}

	public void setReadStockCategory(Boolean readStockCategory) {
		this.readStockCategory = readStockCategory;
	}

	public Boolean getUpdateStockCategory() {
		return updateStockCategory;
	}

	public void setUpdateStockCategory(Boolean updateStockCategory) {
		this.updateStockCategory = updateStockCategory;
	}

	public Boolean getDeleteStockCategory() {
		return deleteStockCategory;
	}

	public void setDeleteStockCategory(Boolean deleteStockCategory) {
		this.deleteStockCategory = deleteStockCategory;
	}

	public Boolean getCreateUnitMeasure() {
		return createUnitMeasure;
	}

	public void setCreateUnitMeasure(Boolean createUnitMeasure) {
		this.createUnitMeasure = createUnitMeasure;
	}

	public Boolean getReadUnitMeasure() {
		return readUnitMeasure;
	}

	public void setReadUnitMeasure(Boolean readUnitMeasure) {
		this.readUnitMeasure = readUnitMeasure;
	}

	public Boolean getUpdateUnitMeasure() {
		return updateUnitMeasure;
	}

	public void setUpdateUnitMeasure(Boolean updateUnitMeasure) {
		this.updateUnitMeasure = updateUnitMeasure;
	}

	public Boolean getDeleteUnitMeasure() {
		return deleteUnitMeasure;
	}

	public void setDeleteUnitMeasure(Boolean deleteUnitMeasure) {
		this.deleteUnitMeasure = deleteUnitMeasure;
	}

	public Boolean getCreateBranchSettings() {
		return createBranchSettings;
	}

	public void setCreateBranchSettings(Boolean createBranchSettings) {
		this.createBranchSettings = createBranchSettings;
	}

	public Boolean getReadBranchSettings() {
		return readBranchSettings;
	}

	public void setReadBranchSettings(Boolean readBranchSettings) {
		this.readBranchSettings = readBranchSettings;
	}

	public Boolean getUpdateBranchSettings() {
		return updateBranchSettings;
	}

	public void setUpdateBranchSettings(Boolean updateBranchSettings) {
		this.updateBranchSettings = updateBranchSettings;
	}

	public Boolean getDeleteBranchSettings() {
		return deleteBranchSettings;
	}

	public void setDeleteBranchSettings(Boolean deleteBranchSettings) {
		this.deleteBranchSettings = deleteBranchSettings;
	}

	public Boolean getCreateRoomSettings() {
		return createRoomSettings;
	}

	public void setCreateRoomSettings(Boolean createRoomSettings) {
		this.createRoomSettings = createRoomSettings;
	}

	public Boolean getReadRoomSettings() {
		return readRoomSettings;
	}

	public void setReadRoomSettings(Boolean readRoomSettings) {
		this.readRoomSettings = readRoomSettings;
	}

	public Boolean getUpdateRoomSettings() {
		return updateRoomSettings;
	}

	public void setUpdateRoomSettings(Boolean updateRoomSettings) {
		this.updateRoomSettings = updateRoomSettings;
	}

	public Boolean getDeleteRoomSettings() {
		return deleteRoomSettings;
	}

	public void setDeleteRoomSettings(Boolean deleteRoomSettings) {
		this.deleteRoomSettings = deleteRoomSettings;
	}

	public Boolean getCreateAllOrderSettings() {
		return createAllOrderSettings;
	}

	public void setCreateAllOrderSettings(Boolean createAllOrderSettings) {
		this.createAllOrderSettings = createAllOrderSettings;
	}

	public Boolean getReadAllOrderSettings() {
		return readAllOrderSettings;
	}

	public void setReadAllOrderSettings(Boolean readAllOrderSettings) {
		this.readAllOrderSettings = readAllOrderSettings;
	}

	public Boolean getUpdateAllOrderSettings() {
		return updateAllOrderSettings;
	}

	public void setUpdateAllOrderSettings(Boolean updateAllOrderSettings) {
		this.updateAllOrderSettings = updateAllOrderSettings;
	}

	public Boolean getDeleteAllOrderSettings() {
		return deleteAllOrderSettings;
	}

	public void setDeleteAllOrderSettings(Boolean deleteAllOrderSettings) {
		this.deleteAllOrderSettings = deleteAllOrderSettings;
	}

	public Boolean getReadInvoiceSettings() {
		return readInvoiceSettings;
	}

	public void setReadInvoiceSettings(Boolean readInvoiceSettings) {
		this.readInvoiceSettings = readInvoiceSettings;
	}

	public Boolean getCreatePurchaseOrderSettings() {
		return createPurchaseOrderSettings;
	}

	public void setCreatePurchaseOrderSettings(Boolean createPurchaseOrderSettings) {
		this.createPurchaseOrderSettings = createPurchaseOrderSettings;
	}

	public Boolean getReadPurchaseOrderSettings() {
		return readPurchaseOrderSettings;
	}

	public void setReadPurchaseOrderSettings(Boolean readPurchaseOrderSettings) {
		this.readPurchaseOrderSettings = readPurchaseOrderSettings;
	}

	public Boolean getUpdatePurchaseOrderSettings() {
		return updatePurchaseOrderSettings;
	}

	public void setUpdatePurchaseOrderSettings(Boolean updatePurchaseOrderSettings) {
		this.updatePurchaseOrderSettings = updatePurchaseOrderSettings;
	}

	public Boolean getDeletePurchaseOrderSettings() {
		return deletePurchaseOrderSettings;
	}

	public void setDeletePurchaseOrderSettings(Boolean deletePurchaseOrderSettings) {
		this.deletePurchaseOrderSettings = deletePurchaseOrderSettings;
	}

	public Boolean getCreateSaleOrderSettings() {
		return createSaleOrderSettings;
	}

	public void setCreateSaleOrderSettings(Boolean createSaleOrderSettings) {
		this.createSaleOrderSettings = createSaleOrderSettings;
	}

	public Boolean getReadSaleOrderSettings() {
		return readSaleOrderSettings;
	}

	public void setReadSaleOrderSettings(Boolean readSaleOrderSettings) {
		this.readSaleOrderSettings = readSaleOrderSettings;
	}

	public Boolean getUpdateSaleOrderSettings() {
		return updateSaleOrderSettings;
	}

	public void setUpdateSaleOrderSettings(Boolean updateSaleOrderSettings) {
		this.updateSaleOrderSettings = updateSaleOrderSettings;
	}

	public Boolean getDeleteSaleOrderSettings() {
		return deleteSaleOrderSettings;
	}

	public void setDeleteSaleOrderSettings(Boolean deleteSaleOrderSettings) {
		this.deleteSaleOrderSettings = deleteSaleOrderSettings;
	}

	public Boolean getCreateCancelPurchaseOrderSettings() {
		return createCancelPurchaseOrderSettings;
	}

	public void setCreateCancelPurchaseOrderSettings(Boolean createCancelPurchaseOrderSettings) {
		this.createCancelPurchaseOrderSettings = createCancelPurchaseOrderSettings;
	}

	public Boolean getReadCancelPurchaseOrderSettings() {
		return readCancelPurchaseOrderSettings;
	}

	public void setReadCancelPurchaseOrderSettings(Boolean readCancelPurchaseOrderSettings) {
		this.readCancelPurchaseOrderSettings = readCancelPurchaseOrderSettings;
	}

	public Boolean getUpdateCancelPurchaseOrderSettings() {
		return updateCancelPurchaseOrderSettings;
	}

	public void setUpdateCancelPurchaseOrderSettings(Boolean updateCancelPurchaseOrderSettings) {
		this.updateCancelPurchaseOrderSettings = updateCancelPurchaseOrderSettings;
	}

	public Boolean getDeleteCancelPurchaseOrderSettings() {
		return deleteCancelPurchaseOrderSettings;
	}

	public void setDeleteCancelPurchaseOrderSettings(Boolean deleteCancelPurchaseOrderSettings) {
		this.deleteCancelPurchaseOrderSettings = deleteCancelPurchaseOrderSettings;
	}

	public Boolean getCreateCancelSaleOrderSettings() {
		return createCancelSaleOrderSettings;
	}

	public void setCreateCancelSaleOrderSettings(Boolean createCancelSaleOrderSettings) {
		this.createCancelSaleOrderSettings = createCancelSaleOrderSettings;
	}

	public Boolean getReadCancelSaleOrderSettings() {
		return readCancelSaleOrderSettings;
	}

	public void setReadCancelSaleOrderSettings(Boolean readCancelSaleOrderSettings) {
		this.readCancelSaleOrderSettings = readCancelSaleOrderSettings;
	}

	public Boolean getUpdateCancelSaleOrderSettings() {
		return updateCancelSaleOrderSettings;
	}

	public void setUpdateCancelSaleOrderSettings(Boolean updateCancelSaleOrderSettings) {
		this.updateCancelSaleOrderSettings = updateCancelSaleOrderSettings;
	}

	public Boolean getDeleteCancelSaleOrderSettings() {
		return deleteCancelSaleOrderSettings;
	}

	public void setDeleteCancelSaleOrderSettings(Boolean deleteCancelSaleOrderSettings) {
		this.deleteCancelSaleOrderSettings = deleteCancelSaleOrderSettings;
	}

	public Boolean getCreateLedgerEntriesSettings() {
		return createLedgerEntriesSettings;
	}

	public void setCreateLedgerEntriesSettings(Boolean createLedgerEntriesSettings) {
		this.createLedgerEntriesSettings = createLedgerEntriesSettings;
	}

	public Boolean getReadLedgerEntriesSettings() {
		return readLedgerEntriesSettings;
	}

	public void setReadLedgerEntriesSettings(Boolean readLedgerEntriesSettings) {
		this.readLedgerEntriesSettings = readLedgerEntriesSettings;
	}

	public Boolean getUpdateLedgerEntriesSettings() {
		return updateLedgerEntriesSettings;
	}

	public void setUpdateLedgerEntriesSettings(Boolean updateLedgerEntriesSettings) {
		this.updateLedgerEntriesSettings = updateLedgerEntriesSettings;
	}

	public Boolean getDeleteLedgerEntriesSettings() {
		return deleteLedgerEntriesSettings;
	}

	public void setDeleteLedgerEntriesSettings(Boolean deleteLedgerEntriesSettings) {
		this.deleteLedgerEntriesSettings = deleteLedgerEntriesSettings;
	}

	public Boolean getCreateRecepientVoucherSettings() {
		return createRecepientVoucherSettings;
	}

	public void setCreateRecepientVoucherSettings(Boolean createRecepientVoucherSettings) {
		this.createRecepientVoucherSettings = createRecepientVoucherSettings;
	}

	public Boolean getReadRecepientVoucherSettings() {
		return readRecepientVoucherSettings;
	}

	public void setReadRecepientVoucherSettings(Boolean readRecepientVoucherSettings) {
		this.readRecepientVoucherSettings = readRecepientVoucherSettings;
	}

	public Boolean getUpdateRecepientVoucherSettings() {
		return updateRecepientVoucherSettings;
	}

	public void setUpdateRecepientVoucherSettings(Boolean updateRecepientVoucherSettings) {
		this.updateRecepientVoucherSettings = updateRecepientVoucherSettings;
	}

	public Boolean getDeleteRecepientVoucherSettings() {
		return deleteRecepientVoucherSettings;
	}

	public void setDeleteRecepientVoucherSettings(Boolean deleteRecepientVoucherSettings) {
		this.deleteRecepientVoucherSettings = deleteRecepientVoucherSettings;
	}

	public Boolean getCreatePaymentVoucherSettings() {
		return createPaymentVoucherSettings;
	}

	public void setCreatePaymentVoucherSettings(Boolean createPaymentVoucherSettings) {
		this.createPaymentVoucherSettings = createPaymentVoucherSettings;
	}

	public Boolean getReadPaymentVoucherSettings() {
		return readPaymentVoucherSettings;
	}

	public void setReadPaymentVoucherSettings(Boolean readPaymentVoucherSettings) {
		this.readPaymentVoucherSettings = readPaymentVoucherSettings;
	}

	public Boolean getUpdatePaymentVoucherSettings() {
		return updatePaymentVoucherSettings;
	}

	public void setUpdatePaymentVoucherSettings(Boolean updatePaymentVoucherSettings) {
		this.updatePaymentVoucherSettings = updatePaymentVoucherSettings;
	}

	public Boolean getDeletePaymentVoucherSettings() {
		return deletePaymentVoucherSettings;
	}

	public void setDeletePaymentVoucherSettings(Boolean deletePaymentVoucherSettings) {
		this.deletePaymentVoucherSettings = deletePaymentVoucherSettings;
	}

	public Boolean getCreateContraVoucherSettings() {
		return createContraVoucherSettings;
	}

	public void setCreateContraVoucherSettings(Boolean createContraVoucherSettings) {
		this.createContraVoucherSettings = createContraVoucherSettings;
	}

	public Boolean getReadContraVoucherSettings() {
		return readContraVoucherSettings;
	}

	public void setReadContraVoucherSettings(Boolean readContraVoucherSettings) {
		this.readContraVoucherSettings = readContraVoucherSettings;
	}

	public Boolean getUpdateContraVoucherSettings() {
		return updateContraVoucherSettings;
	}

	public void setUpdateContraVoucherSettings(Boolean updateContraVoucherSettings) {
		this.updateContraVoucherSettings = updateContraVoucherSettings;
	}

	public Boolean getDeleteContraVoucherSettings() {
		return deleteContraVoucherSettings;
	}

	public void setDeleteContraVoucherSettings(Boolean deleteContraVoucherSettings) {
		this.deleteContraVoucherSettings = deleteContraVoucherSettings;
	}

	public Boolean getCreateExpenseVoucherSettings() {
		return createExpenseVoucherSettings;
	}

	public void setCreateExpenseVoucherSettings(Boolean createExpenseVoucherSettings) {
		this.createExpenseVoucherSettings = createExpenseVoucherSettings;
	}

	public Boolean getReadExpenseVoucherSettings() {
		return readExpenseVoucherSettings;
	}

	public void setReadExpenseVoucherSettings(Boolean readExpenseVoucherSettings) {
		this.readExpenseVoucherSettings = readExpenseVoucherSettings;
	}

	public Boolean getUpdateExpenseVoucherSettings() {
		return updateExpenseVoucherSettings;
	}

	public void setUpdateExpenseVoucherSettings(Boolean updateExpenseVoucherSettings) {
		this.updateExpenseVoucherSettings = updateExpenseVoucherSettings;
	}

	public Boolean getDeleteExpenseVoucherSettings() {
		return deleteExpenseVoucherSettings;
	}

	public void setDeleteExpenseVoucherSettings(Boolean deleteExpenseVoucherSettings) {
		this.deleteExpenseVoucherSettings = deleteExpenseVoucherSettings;
	}

	public Boolean getCreateJournalVoucherSettings() {
		return createJournalVoucherSettings;
	}

	public void setCreateJournalVoucherSettings(Boolean createJournalVoucherSettings) {
		this.createJournalVoucherSettings = createJournalVoucherSettings;
	}

	public Boolean getReadJournalVoucherSettings() {
		return readJournalVoucherSettings;
	}

	public void setReadJournalVoucherSettings(Boolean readJournalVoucherSettings) {
		this.readJournalVoucherSettings = readJournalVoucherSettings;
	}

	public Boolean getUpdateJournalVoucherSettings() {
		return updateJournalVoucherSettings;
	}

	public void setUpdateJournalVoucherSettings(Boolean updateJournalVoucherSettings) {
		this.updateJournalVoucherSettings = updateJournalVoucherSettings;
	}

	public Boolean getDeleteJournalVoucherSettings() {
		return deleteJournalVoucherSettings;
	}

	public void setDeleteJournalVoucherSettings(Boolean deleteJournalVoucherSettings) {
		this.deleteJournalVoucherSettings = deleteJournalVoucherSettings;
	}

	public Boolean getCreateAllVoucherSettings() {
		return createAllVoucherSettings;
	}

	public void setCreateAllVoucherSettings(Boolean createAllVoucherSettings) {
		this.createAllVoucherSettings = createAllVoucherSettings;
	}

	public Boolean getReadAllVoucherSettings() {
		return readAllVoucherSettings;
	}

	public void setReadAllVoucherSettings(Boolean readAllVoucherSettings) {
		this.readAllVoucherSettings = readAllVoucherSettings;
	}

	public Boolean getUpdateAllVoucherSettings() {
		return updateAllVoucherSettings;
	}

	public void setUpdateAllVoucherSettings(Boolean updateAllVoucherSettings) {
		this.updateAllVoucherSettings = updateAllVoucherSettings;
	}

	public Boolean getDeleteAllVoucherSettings() {
		return deleteAllVoucherSettings;
	}

	public void setDeleteAllVoucherSettings(Boolean deleteAllVoucherSettings) {
		this.deleteAllVoucherSettings = deleteAllVoucherSettings;
	}

	public Boolean getCreateChartOfAccountingSettings() {
		return createChartOfAccountingSettings;
	}

	public void setCreateChartOfAccountingSettings(Boolean createChartOfAccountingSettings) {
		this.createChartOfAccountingSettings = createChartOfAccountingSettings;
	}

	public Boolean getReadChartOfAccountingSettings() {
		return readChartOfAccountingSettings;
	}

	public void setReadChartOfAccountingSettings(Boolean readChartOfAccountingSettings) {
		this.readChartOfAccountingSettings = readChartOfAccountingSettings;
	}

	public Boolean getUpdateChartOfAccountingSettings() {
		return updateChartOfAccountingSettings;
	}

	public void setUpdateChartOfAccountingSettings(Boolean updateChartOfAccountingSettings) {
		this.updateChartOfAccountingSettings = updateChartOfAccountingSettings;
	}

	public Boolean getDeleteChartOfAccountingSettings() {
		return deleteChartOfAccountingSettings;
	}

	public void setDeleteChartOfAccountingSettings(Boolean deleteChartOfAccountingSettings) {
		this.deleteChartOfAccountingSettings = deleteChartOfAccountingSettings;
	}

	public Boolean getCreateLedgerGroupSettings() {
		return createLedgerGroupSettings;
	}

	public void setCreateLedgerGroupSettings(Boolean createLedgerGroupSettings) {
		this.createLedgerGroupSettings = createLedgerGroupSettings;
	}

	public Boolean getReadLedgerGroupSettings() {
		return readLedgerGroupSettings;
	}

	public void setReadLedgerGroupSettings(Boolean readLedgerGroupSettings) {
		this.readLedgerGroupSettings = readLedgerGroupSettings;
	}

	public Boolean getUpdateLedgerGroupSettings() {
		return updateLedgerGroupSettings;
	}

	public void setUpdateLedgerGroupSettings(Boolean updateLedgerGroupSettings) {
		this.updateLedgerGroupSettings = updateLedgerGroupSettings;
	}

	public Boolean getDeleteLedgerGroupSettings() {
		return deleteLedgerGroupSettings;
	}

	public void setDeleteLedgerGroupSettings(Boolean deleteLedgerGroupSettings) {
		this.deleteLedgerGroupSettings = deleteLedgerGroupSettings;
	}

	public Boolean getCreateLedgerSettings() {
		return createLedgerSettings;
	}

	public void setCreateLedgerSettings(Boolean createLedgerSettings) {
		this.createLedgerSettings = createLedgerSettings;
	}

	public Boolean getReadLedgerSettings() {
		return readLedgerSettings;
	}

	public void setReadLedgerSettings(Boolean readLedgerSettings) {
		this.readLedgerSettings = readLedgerSettings;
	}

	public Boolean getUpdateLedgerSettings() {
		return updateLedgerSettings;
	}

	public void setUpdateLedgerSettings(Boolean updateLedgerSettings) {
		this.updateLedgerSettings = updateLedgerSettings;
	}

	public Boolean getDeleteLedgerSettings() {
		return deleteLedgerSettings;
	}

	public void setDeleteLedgerSettings(Boolean deleteLedgerSettings) {
		this.deleteLedgerSettings = deleteLedgerSettings;
	}

	public Boolean getCreateBalanceSheetSettings() {
		return createBalanceSheetSettings;
	}

	public void setCreateBalanceSheetSettings(Boolean createBalanceSheetSettings) {
		this.createBalanceSheetSettings = createBalanceSheetSettings;
	}

	public Boolean getReadBalanceSheetSettings() {
		return readBalanceSheetSettings;
	}

	public void setReadBalanceSheetSettings(Boolean readBalanceSheetSettings) {
		this.readBalanceSheetSettings = readBalanceSheetSettings;
	}

	public Boolean getUpdateBalanceSheetSettings() {
		return updateBalanceSheetSettings;
	}

	public void setUpdateBalanceSheetSettings(Boolean updateBalanceSheetSettings) {
		this.updateBalanceSheetSettings = updateBalanceSheetSettings;
	}

	public Boolean getDeleteBalanceSheetSettings() {
		return deleteBalanceSheetSettings;
	}

	public void setDeleteBalanceSheetSettings(Boolean deleteBalanceSheetSettings) {
		this.deleteBalanceSheetSettings = deleteBalanceSheetSettings;
	}

	public Boolean getCreateStockItemSettings() {
		return createStockItemSettings;
	}

	public void setCreateStockItemSettings(Boolean createStockItemSettings) {
		this.createStockItemSettings = createStockItemSettings;
	}

	public Boolean getReadStockItemSettings() {
		return readStockItemSettings;
	}

	public void setReadStockItemSettings(Boolean readStockItemSettings) {
		this.readStockItemSettings = readStockItemSettings;
	}

	public Boolean getUpdateStockItemSettings() {
		return updateStockItemSettings;
	}

	public void setUpdateStockItemSettings(Boolean updateStockItemSettings) {
		this.updateStockItemSettings = updateStockItemSettings;
	}

	public Boolean getDeleteStockItemSettings() {
		return deleteStockItemSettings;
	}

	public void setDeleteStockItemSettings(Boolean deleteStockItemSettings) {
		this.deleteStockItemSettings = deleteStockItemSettings;
	}

	public Boolean getCreateStockInSettings() {
		return createStockInSettings;
	}

	public void setCreateStockInSettings(Boolean createStockInSettings) {
		this.createStockInSettings = createStockInSettings;
	}

	public Boolean getReadStockInSettings() {
		return readStockInSettings;
	}

	public void setReadStockInSettings(Boolean readStockInSettings) {
		this.readStockInSettings = readStockInSettings;
	}

	public Boolean getUpdateStockInSettings() {
		return updateStockInSettings;
	}

	public void setUpdateStockInSettings(Boolean updateStockInSettings) {
		this.updateStockInSettings = updateStockInSettings;
	}

	public Boolean getDeleteStockInSettings() {
		return deleteStockInSettings;
	}

	public void setDeleteStockInSettings(Boolean deleteStockInSettings) {
		this.deleteStockInSettings = deleteStockInSettings;
	}

	public Boolean getCreateStockOutSettings() {
		return createStockOutSettings;
	}

	public void setCreateStockOutSettings(Boolean createStockOutSettings) {
		this.createStockOutSettings = createStockOutSettings;
	}

	public Boolean getReadStockOutSettings() {
		return readStockOutSettings;
	}

	public void setReadStockOutSettings(Boolean readStockOutSettings) {
		this.readStockOutSettings = readStockOutSettings;
	}

	public Boolean getUpdateStockOutSettings() {
		return updateStockOutSettings;
	}

	public void setUpdateStockOutSettings(Boolean updateStockOutSettings) {
		this.updateStockOutSettings = updateStockOutSettings;
	}

	public Boolean getDeleteStockOutSettings() {
		return deleteStockOutSettings;
	}

	public void setDeleteStockOutSettings(Boolean deleteStockOutSettings) {
		this.deleteStockOutSettings = deleteStockOutSettings;
	}

	public EmployeeRole getEmployeeRole() {
		return employeeRole;
	}

	public void setEmployeeRole(EmployeeRole employeeRole) {
		this.employeeRole = employeeRole;
	}

	public Boolean getCreateAccessPermissionSettings() {
		return createAccessPermissionSettings;
	}

	public void setCreateAccessPermissionSettings(Boolean createAccessPermissionSettings) {
		this.createAccessPermissionSettings = createAccessPermissionSettings;
	}

	public Boolean getReadAccessPermissionSettings() {
		return readAccessPermissionSettings;
	}

	public void setReadAccessPermissionSettings(Boolean readAccessPermissionSettings) {
		this.readAccessPermissionSettings = readAccessPermissionSettings;
	}

	public Boolean getUpdateAccessPermissionSettings() {
		return updateAccessPermissionSettings;
	}

	public void setUpdateAccessPermissionSettings(Boolean updateAccessPermissionSettings) {
		this.updateAccessPermissionSettings = updateAccessPermissionSettings;
	}

	public Boolean getDeleteAccessPermissionSettings() {
		return deleteAccessPermissionSettings;
	}

	public void setDeleteAccessPermissionSettings(Boolean deleteAccessPermissionSettings) {
		this.deleteAccessPermissionSettings = deleteAccessPermissionSettings;
	}

	public Boolean getCreateProductionSettings() {
		return createProductionSettings;
	}

	public void setCreateProductionSettings(Boolean createProductionSettings) {
		this.createProductionSettings = createProductionSettings;
	}

	public Boolean getReadProductionSettings() {
		return readProductionSettings;
	}

	public void setReadProductionSettings(Boolean readProductionSettings) {
		this.readProductionSettings = readProductionSettings;
	}

	public Boolean getUpdateProductionSettings() {
		return updateProductionSettings;
	}

	public void setUpdateProductionSettings(Boolean updateProductionSettings) {
		this.updateProductionSettings = updateProductionSettings;
	}

	public Boolean getDeleteProductionSettings() {
		return deleteProductionSettings;
	}

	public void setDeleteProductionSettings(Boolean deleteProductionSettings) {
		this.deleteProductionSettings = deleteProductionSettings;
	}

	public Boolean getCreateConsumptionSettings() {
		return createConsumptionSettings;
	}

	public void setCreateConsumptionSettings(Boolean createConsumptionSettings) {
		this.createConsumptionSettings = createConsumptionSettings;
	}

	public Boolean getReadConsumptionSettings() {
		return readConsumptionSettings;
	}

	public void setReadConsumptionSettings(Boolean readConsumptionSettings) {
		this.readConsumptionSettings = readConsumptionSettings;
	}

	public Boolean getUpdateConsumptionSettings() {
		return updateConsumptionSettings;
	}

	public void setUpdateConsumptionSettings(Boolean updateConsumptionSettings) {
		this.updateConsumptionSettings = updateConsumptionSettings;
	}

	public Boolean getDeleteConsumptionSettings() {
		return deleteConsumptionSettings;
	}

	public void setDeleteConsumptionSettings(Boolean deleteConsumptionSettings) {
		this.deleteConsumptionSettings = deleteConsumptionSettings;
	}

	public Boolean getReadProfitLossStatementSettings() {
		return readProfitLossStatementSettings;
	}

	public void setReadProfitLossStatementSettings(Boolean readProfitLossStatementSettings) {
		this.readProfitLossStatementSettings = readProfitLossStatementSettings;
	}

	public Boolean getReadStockRecordBookSettings() {
		return readStockRecordBookSettings;
	}

	public void setReadStockRecordBookSettings(Boolean readStockRecordBookSettings) {
		this.readStockRecordBookSettings = readStockRecordBookSettings;
	}

	public Boolean getUpdateGlobalPasswordSettings() {
		return updateGlobalPasswordSettings;
	}

	public void setUpdateGlobalPasswordSettings(Boolean updateGlobalPasswordSettings) {
		this.updateGlobalPasswordSettings = updateGlobalPasswordSettings;
	}

	public Boolean getReadSmsSettings() {
		return readSmsSettings;
	}

	public void setReadSmsSettings(Boolean readSmsSettings) {
		this.readSmsSettings = readSmsSettings;
	}

	@Override
	public String toString() {
		return "AccessPermission [id=" + id + ", createRole=" + createRole + ", readRole=" + readRole + ", updateRole="
				+ updateRole + ", deleteRole=" + deleteRole + ", createEmployeeCategory=" + createEmployeeCategory
				+ ", readEmployeeCategory=" + readEmployeeCategory + ", updateEmployeeCategory="
				+ updateEmployeeCategory + ", deleteEmployeeCategory=" + deleteEmployeeCategory + ", createEmployee="
				+ createEmployee + ", readEmployee=" + readEmployee + ", updateEmployee=" + updateEmployee
				+ ", deleteEmployee=" + deleteEmployee + ", createCompanySettings=" + createCompanySettings
				+ ", readCompanySettings=" + readCompanySettings + ", updateCompanySettings=" + updateCompanySettings
				+ ", deleteCompanySettings=" + deleteCompanySettings + ", createMediaAndDocSettings="
				+ createMediaAndDocSettings + ", readMediaAndDocSettings=" + readMediaAndDocSettings
				+ ", updateMediaAndDocSettings=" + updateMediaAndDocSettings + ", deleteMediaAndDocSettings="
				+ deleteMediaAndDocSettings + ", createStockCategory=" + createStockCategory + ", readStockCategory="
				+ readStockCategory + ", updateStockCategory=" + updateStockCategory + ", deleteStockCategory="
				+ deleteStockCategory + ", createUnitMeasure=" + createUnitMeasure + ", readUnitMeasure="
				+ readUnitMeasure + ", updateUnitMeasure=" + updateUnitMeasure + ", deleteUnitMeasure="
				+ deleteUnitMeasure + ", createBranchSettings=" + createBranchSettings + ", readBranchSettings="
				+ readBranchSettings + ", updateBranchSettings=" + updateBranchSettings + ", deleteBranchSettings="
				+ deleteBranchSettings + ", createRoomSettings=" + createRoomSettings + ", readRoomSettings="
				+ readRoomSettings + ", updateRoomSettings=" + updateRoomSettings + ", deleteRoomSettings="
				+ deleteRoomSettings + ", createAllOrderSettings=" + createAllOrderSettings + ", readAllOrderSettings="
				+ readAllOrderSettings + ", updateAllOrderSettings=" + updateAllOrderSettings
				+ ", deleteAllOrderSettings=" + deleteAllOrderSettings + ", readInvoiceSettings=" + readInvoiceSettings
				+ ", createLedgerEntriesSettings=" + createLedgerEntriesSettings + ", readLedgerEntriesSettings="
				+ readLedgerEntriesSettings + ", updateLedgerEntriesSettings=" + updateLedgerEntriesSettings
				+ ", deleteLedgerEntriesSettings=" + deleteLedgerEntriesSettings + ", createPurchaseOrderSettings="
				+ createPurchaseOrderSettings + ", readPurchaseOrderSettings=" + readPurchaseOrderSettings
				+ ", updatePurchaseOrderSettings=" + updatePurchaseOrderSettings + ", deletePurchaseOrderSettings="
				+ deletePurchaseOrderSettings + ", createSaleOrderSettings=" + createSaleOrderSettings
				+ ", readSaleOrderSettings=" + readSaleOrderSettings + ", updateSaleOrderSettings="
				+ updateSaleOrderSettings + ", deleteSaleOrderSettings=" + deleteSaleOrderSettings
				+ ", createCancelPurchaseOrderSettings=" + createCancelPurchaseOrderSettings
				+ ", readCancelPurchaseOrderSettings=" + readCancelPurchaseOrderSettings
				+ ", updateCancelPurchaseOrderSettings=" + updateCancelPurchaseOrderSettings
				+ ", deleteCancelPurchaseOrderSettings=" + deleteCancelPurchaseOrderSettings
				+ ", createCancelSaleOrderSettings=" + createCancelSaleOrderSettings + ", readCancelSaleOrderSettings="
				+ readCancelSaleOrderSettings + ", updateCancelSaleOrderSettings=" + updateCancelSaleOrderSettings
				+ ", deleteCancelSaleOrderSettings=" + deleteCancelSaleOrderSettings
				+ ", createRecepientVoucherSettings=" + createRecepientVoucherSettings
				+ ", readRecepientVoucherSettings=" + readRecepientVoucherSettings + ", updateRecepientVoucherSettings="
				+ updateRecepientVoucherSettings + ", deleteRecepientVoucherSettings=" + deleteRecepientVoucherSettings
				+ ", createPaymentVoucherSettings=" + createPaymentVoucherSettings + ", readPaymentVoucherSettings="
				+ readPaymentVoucherSettings + ", updatePaymentVoucherSettings=" + updatePaymentVoucherSettings
				+ ", deletePaymentVoucherSettings=" + deletePaymentVoucherSettings + ", createContraVoucherSettings="
				+ createContraVoucherSettings + ", readContraVoucherSettings=" + readContraVoucherSettings
				+ ", updateContraVoucherSettings=" + updateContraVoucherSettings + ", deleteContraVoucherSettings="
				+ deleteContraVoucherSettings + ", createExpenseVoucherSettings=" + createExpenseVoucherSettings
				+ ", readExpenseVoucherSettings=" + readExpenseVoucherSettings + ", updateExpenseVoucherSettings="
				+ updateExpenseVoucherSettings + ", deleteExpenseVoucherSettings=" + deleteExpenseVoucherSettings
				+ ", createJournalVoucherSettings=" + createJournalVoucherSettings + ", readJournalVoucherSettings="
				+ readJournalVoucherSettings + ", updateJournalVoucherSettings=" + updateJournalVoucherSettings
				+ ", deleteJournalVoucherSettings=" + deleteJournalVoucherSettings + ", createAllVoucherSettings="
				+ createAllVoucherSettings + ", readAllVoucherSettings=" + readAllVoucherSettings
				+ ", updateAllVoucherSettings=" + updateAllVoucherSettings + ", deleteAllVoucherSettings="
				+ deleteAllVoucherSettings + ", createChartOfAccountingSettings=" + createChartOfAccountingSettings
				+ ", readChartOfAccountingSettings=" + readChartOfAccountingSettings
				+ ", updateChartOfAccountingSettings=" + updateChartOfAccountingSettings
				+ ", deleteChartOfAccountingSettings=" + deleteChartOfAccountingSettings
				+ ", createLedgerGroupSettings=" + createLedgerGroupSettings + ", readLedgerGroupSettings="
				+ readLedgerGroupSettings + ", updateLedgerGroupSettings=" + updateLedgerGroupSettings
				+ ", deleteLedgerGroupSettings=" + deleteLedgerGroupSettings + ", createLedgerSettings="
				+ createLedgerSettings + ", readLedgerSettings=" + readLedgerSettings + ", updateLedgerSettings="
				+ updateLedgerSettings + ", deleteLedgerSettings=" + deleteLedgerSettings
				+ ", createBalanceSheetSettings=" + createBalanceSheetSettings + ", readBalanceSheetSettings="
				+ readBalanceSheetSettings + ", updateBalanceSheetSettings=" + updateBalanceSheetSettings
				+ ", deleteBalanceSheetSettings=" + deleteBalanceSheetSettings + ", createStockItemSettings="
				+ createStockItemSettings + ", readStockItemSettings=" + readStockItemSettings
				+ ", updateStockItemSettings=" + updateStockItemSettings + ", deleteStockItemSettings="
				+ deleteStockItemSettings + ", createStockInSettings=" + createStockInSettings
				+ ", readStockInSettings=" + readStockInSettings + ", updateStockInSettings=" + updateStockInSettings
				+ ", deleteStockInSettings=" + deleteStockInSettings + ", createStockOutSettings="
				+ createStockOutSettings + ", readStockOutSettings=" + readStockOutSettings
				+ ", updateStockOutSettings=" + updateStockOutSettings + ", deleteStockOutSettings="
				+ deleteStockOutSettings + ", createAccessPermissionSettings=" + createAccessPermissionSettings
				+ ", readAccessPermissionSettings=" + readAccessPermissionSettings + ", updateAccessPermissionSettings="
				+ updateAccessPermissionSettings + ", deleteAccessPermissionSettings=" + deleteAccessPermissionSettings
				+ ", createProductionSettings=" + createProductionSettings + ", readProductionSettings="
				+ readProductionSettings + ", updateProductionSettings=" + updateProductionSettings
				+ ", deleteProductionSettings=" + deleteProductionSettings + ", createConsumptionSettings="
				+ createConsumptionSettings + ", readConsumptionSettings=" + readConsumptionSettings
				+ ", updateConsumptionSettings=" + updateConsumptionSettings + ", deleteConsumptionSettings="
				+ deleteConsumptionSettings + ", readProfitLossStatementSettings=" + readProfitLossStatementSettings
				+ ", readStockRecordBookSettings=" + readStockRecordBookSettings + ", updateGlobalPasswordSettings="
				+ updateGlobalPasswordSettings + ", readSmsSettings=" + readSmsSettings + ", employeeRole=" + employeeRole + "]";
	}

}
