package com.tigerslab.tigererp.service.user;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tigerslab.tigererp.model.ConstantFactory;
import com.tigerslab.tigererp.model.Role;
import com.tigerslab.tigererp.model.User;
import com.tigerslab.tigererp.model.UserExceptPassword;
import com.tigerslab.tigererp.model.httphelper.RequestParameter;
import com.tigerslab.tigererp.model.org.Branch;
import com.tigerslab.tigererp.model.user.employee.Employee;
import com.tigerslab.tigererp.repository.org.BranchRepository;
import com.tigerslab.tigererp.repository.user.RoleRepository;
import com.tigerslab.tigererp.repository.user.UserRepository;
import com.tigerslab.tigererp.service.org.BranchService;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BranchRepository branchRepository;
	
	private Pageable pagable;

	@Override
	public Optional<User> findById(long userId) {
		return userRepository.findById(userId);
	}

	@Override
	public User saveUser(User user, String roles) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(1);
		Role role = null;
		if(roles.equals(ConstantFactory.ROLE_CUSTOMER)) {
			 role = roleRepository.findByRole(ConstantFactory.ROLE_CUSTOMER);
		}
		else if(roles.equals(ConstantFactory.ROLE_EMPLOYEE)){
			role = roleRepository.findByRole(ConstantFactory.ROLE_EMPLOYEE);
		}
		else if(roles.equals(ConstantFactory.ROLE_ADMIN)){
			role = roleRepository.findByRole(ConstantFactory.ROLE_ADMIN);
		}

		user.setRoles( new HashSet<Role>(Arrays.asList(role)));
		System.out.println("FINAL USER VALUES ARE-------- "+user);
		return userRepository.save(user);
		
	}

	@Override
	public User updateUserPasswordOnly(Long id, String password) {
		userRepository.updatePasswordOnly(id, password);
		return userRepository.findById(id).get();
	}


	@Override
	public User findUserByEmail(String userEmail) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(userEmail);
	}

	@Override
	public Page<UserExceptPassword> findByRoles(String roles, RequestParameter requestParam) {
		// TODO Auto-generated method stub
		Set<Role> roleSet = new HashSet<Role>();
		User user = new User();
		Role role = roleRepository.findByRole(ConstantFactory.ROLE_EMPLOYEE);
		if(roles.equals(ConstantFactory.ROLE_EMPLOYEE)) {
			 roleSet =  new HashSet<Role>(Arrays.asList(role));
		}
		
		if(requestParam.getPage() >= 1) {
			requestParam.setPage(requestParam.getPage()-1);
		}
		if(requestParam.getOrder().charAt(0) == '-') {
			String sortOrder = requestParam.getOrder().substring(1);
			pagable = PageRequest.of(requestParam.getPage(), requestParam.getLimit(), Sort.by(sortOrder).descending());
		}
		else {
			pagable = PageRequest.of(requestParam.getPage(), requestParam.getLimit(), Sort.by(requestParam.getOrder()).ascending());
		}

		return userRepository.findByRoles(roleSet, pagable);
	}

	@Override
	public void saveUserWithEmployeeRole(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(1);
		Role role = null;
	    role = roleRepository.findByRole(ConstantFactory.ROLE_EMPLOYEE);
		
		user.setRoles( new HashSet<Role>(Arrays.asList(role)));
		userRepository.save(user);
	}

	@Override
	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	@Override
	public Page<UserExceptPassword> findByRoles(String roles, boolean onlyEmployee, RequestParameter requestParam) {
		// TODO Auto-generated method stub
		Set<Role> roleSet = new HashSet<Role>();
		User user = new User();
		Role role = roleRepository.findByRole(ConstantFactory.ROLE_EMPLOYEE);
		if(roles.equals(ConstantFactory.ROLE_EMPLOYEE)) {
			 roleSet =  new HashSet<Role>(Arrays.asList(role));
		}
		
		if(requestParam.getPage() >= 1) {
			requestParam.setPage(requestParam.getPage()-1);
		}
		if(requestParam.getOrder().charAt(0) == '-') {
			String sortOrder = requestParam.getOrder().substring(1);
			pagable = PageRequest.of(requestParam.getPage(), requestParam.getLimit(), Sort.by(sortOrder).descending());
		}
		else {
			pagable = PageRequest.of(requestParam.getPage(), requestParam.getLimit(), Sort.by(requestParam.getOrder()).ascending());
		}

		return userRepository.findByRoles(roleSet, true, pagable);
	}

	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		user.setPassword(userRepository.findById(user.getId()).get().getPassword());
		System.out.println("ID: "+user.getId()+" pass: "+user.getPassword());
		return userRepository.save(user);
	}

}
