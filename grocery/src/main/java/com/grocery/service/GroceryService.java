package com.grocery.service;

import java.util.List;

import com.grocery.entity.GroceryItemEntity;
import com.grocery.model.GroceryOrderBook;

public interface GroceryService {

	void addNewGrocery(GroceryItemEntity groceryItemEntity);
	
	List<GroceryItemEntity> findAllGroceryItems();
	
	void deleteItem(Long id);
	
	void updateItem(GroceryItemEntity groceryItemEntity);
	
	List<GroceryOrderBook> orderGrocery(List<GroceryOrderBook> GroceryOrderBook);

}
