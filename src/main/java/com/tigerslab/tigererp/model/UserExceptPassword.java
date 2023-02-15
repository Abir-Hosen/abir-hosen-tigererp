package com.tigerslab.tigererp.model;

import java.util.Set;

import com.tigerslab.tigererp.model.user.employee.Employee;
import com.tigerslab.tigererp.model.user.employee.EmployeeRole;

public interface UserExceptPassword {
	
	public int getId();
	
	public String getCompanyId();

	public String getFirstName();

	public String getLastName();

	public String getEmail();

	public int getActive();

	public Set<Role> getRoles();
	
	public Set<EmployeeRole> getEmpRoles();
	
	public Employee getEmployee();


}
