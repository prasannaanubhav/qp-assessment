package com.grocery.model;

import java.util.List;

import com.grocery.entity.GroceryItemEntity;

public class GroceryItemResponse {

	private String status;

	private List<GroceryItemEntity> groceryList;

	public GroceryItemResponse() {

	}

	public GroceryItemResponse(String status, List<GroceryItemEntity> groceryList) {
		this.status = status;
		this.groceryList = groceryList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<GroceryItemEntity> getGroceryList() {
		return groceryList;
	}

	public void setGroceryList(List<GroceryItemEntity> groceryList) {
		this.groceryList = groceryList;
	}

}
