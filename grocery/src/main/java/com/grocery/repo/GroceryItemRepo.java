package com.grocery.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.grocery.entity.GroceryItemEntity;

@Repository
@Transactional
public interface GroceryItemRepo extends JpaRepository<GroceryItemEntity, Long> {

	public GroceryItemEntity findByItemName(String name);

	public GroceryItemEntity findByItemId(Long id);
}
