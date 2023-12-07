package com.grocery.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grocery.entity.PrivilegeEntity;

@Repository
public interface PrivilegeRepository extends JpaRepository<PrivilegeEntity, Long> {

	PrivilegeEntity findByName(String name);

}
