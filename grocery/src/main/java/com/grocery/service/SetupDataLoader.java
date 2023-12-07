package com.grocery.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.grocery.entity.PrivilegeEntity;
import com.grocery.entity.RoleEntity;
import com.grocery.entity.UserEntity;
import com.grocery.repo.PrivilegeRepository;
import com.grocery.repo.RoleRepository;
import com.grocery.repo.UserRepository;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	boolean alreadySetup = false;

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final PrivilegeRepository privilegeRepository;

	@Autowired
	public SetupDataLoader(UserRepository userRepository, RoleRepository roleRepository,
			PrivilegeRepository privilegeRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.privilegeRepository = privilegeRepository;
	}

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {

		if (alreadySetup)
			return;
		PrivilegeEntity readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
		PrivilegeEntity writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

		List<PrivilegeEntity> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);
		createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
		createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

		RoleEntity adminRole = roleRepository.findByName("ROLE_ADMIN");
		UserEntity user = new UserEntity();
		user.setFirstName("Test");
		user.setLastName("Test");
		user.setEmail("test@test.com");
		user.setRoles(Arrays.asList(adminRole));
		userRepository.save(user);

		alreadySetup = true;
	}

	@Transactional
	PrivilegeEntity createPrivilegeIfNotFound(String name) {

		PrivilegeEntity privilege = privilegeRepository.findByName(name);
		if (privilege == null) {
			privilege = new PrivilegeEntity(name);
			privilegeRepository.save(privilege);
		}
		return privilege;
	}

	@Transactional
	RoleEntity createRoleIfNotFound(String name, Collection<PrivilegeEntity> privileges) {

		RoleEntity role = roleRepository.findByName(name);
		if (role == null) {
			role = new RoleEntity(name);
			role.setPrivileges(privileges);
			roleRepository.save(role);
		}
		return role;
	}
}
