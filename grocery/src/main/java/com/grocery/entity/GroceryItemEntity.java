package com.grocery.entity;

import java.io.Serializable;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "groceryitem")
@DynamicUpdate(true)
@DynamicInsert(true)
public class GroceryItemEntity implements Serializable {

	private static final long serialVersionUID = -7272735951635207647L;

	@Id
	@GeneratedValue
	private Long itemId;

	@Column(nullable = false)
	private String itemName;

	private int itemPrice;

	private int itemQuantity;

	public GroceryItemEntity() {

	}

	public GroceryItemEntity(Long itemId, String itemName, int itemPrice, int itemQuantity) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.itemQuantity = itemQuantity;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}

	public int getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
