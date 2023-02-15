package com.tigerslab.tigererp.service.user.employee;

import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tigerslab.tigererp.model.ConstantFactory;
import com.tigerslab.tigererp.model.User;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.user.employee.AccessPermission;
import com.tigerslab.tigererp.model.user.employee.EmployeeRole;
import com.tigerslab.tigererp.repository.user.employee.AccessPermissionRepository;
import com.tigerslab.tigererp.repository.user.employee.EmployeeRoleRepository;
import com.tigerslab.tigererp.service.user.UserService;

@Service("accessPermissionService")
public class AccessPermissionServiceImpl implements AccessPermissionService {
	
	Logger logger = LoggerFactory.getLogger(EmployeeRoleService.class);
	
	@Autowired
	private AccessPermissionRepository accessPermissionRepository;
	
	private Pageable pagable;
	
	@Autowired
	private UserService userService;


	@Override
	public Optional<AccessPermission> findById(Long id) {
		return accessPermissionRepository.findById(id);
	}
	
	@Override
	public AccessPermission save(AccessPermission accessPermission) {
		return accessPermissionRepository.save(accessPermission);
	}

	@Override
	public void deleteById(Long id) {
		accessPermissionRepository.deleteById(id);
	}

	@Override
	public Optional<AccessPermission> findByRoleId(Long roleId) {
		return accessPermissionRepository.findByEmployeeRoleId(roleId);
	}
	

	public Optional<AccessPermission> getLoggegUserAccessPermission(HttpSession session) {
		String userName = (String)session.getAttribute("userName");
		String role = (String)session.getAttribute("role");
		User user = userService.findUserByEmail(userName);
		Set<EmployeeRole> empRole = user.getEmpRoles();
		System.out.println(empRole+"\n Here emp role");
		if(!empRole.isEmpty()) {
			EmployeeRole employeeRoleObject = empRole.iterator().next();
			System.out.println(employeeRoleObject+"\n Here emp role employeeRoleObject");
			Optional<AccessPermission> accessPermission = this.findByRoleId(employeeRoleObject.getId());
			System.out.println(accessPermission+"\n Here emp access permission");
			if(accessPermission.isPresent()) {
				return accessPermission;
			}
		}
		return null;
	}
	

	@Override
	public Optional<AccessPermission> findByIdAndEmployeeRoleId(Long id, Long roleId) {
		return accessPermissionRepository.findByIdAndEmployeeRoleId(id, roleId);
	}

	@Override
	public void delete(AccessPermission accessPermission) {
		accessPermissionRepository.delete(accessPermission);
	}

	@Override
	public Optional<AccessPermission> findByEmployeeRoleUserEmail(String email) {
		//accessPermissionRepository.findByEmployeeRoleUserEmail(email)
		return null;
	}
}