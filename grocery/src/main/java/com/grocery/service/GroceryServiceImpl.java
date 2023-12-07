package com.grocery.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grocery.entity.GroceryItemEntity;
import com.grocery.model.GroceryOrderBook;
import com.grocery.repo.GroceryItemRepo;

@Service
public class GroceryServiceImpl implements GroceryService {

	private final GroceryItemRepo groceryItemRepo;

	@Autowired
	public GroceryServiceImpl(GroceryItemRepo groceryItemRepo) {
		this.groceryItemRepo = groceryItemRepo;
	}

	@Override
	@Transactional
	public void addNewGrocery(GroceryItemEntity groceryItemEntity) {

		groceryItemRepo.saveAndFlush(groceryItemEntity);

	}

	@Override
	public List<GroceryItemEntity> findAllGroceryItems() {

		return groceryItemRepo.findAll();

	}

	@Override
	public void deleteItem(Long id) {

		groceryItemRepo.deleteById(id);

	}

	@Override
	public void updateItem(GroceryItemEntity groceryItemEntity) {

		GroceryItemEntity itemEntity = groceryItemRepo.findByItemName(groceryItemEntity.getItemName().trim());
		if (Objects.nonNull(itemEntity)) {
			itemEntity.setItemName(groceryItemEntity.getItemName());
			itemEntity.setItemPrice(groceryItemEntity.getItemPrice());
			itemEntity.setItemQuantity(groceryItemEntity.getItemQuantity());
			groceryItemRepo.saveAndFlush(itemEntity);
		}

	}

	@Override
	@Transactional
	public List<GroceryOrderBook> orderGrocery(List<GroceryOrderBook> groceryOrderBook) {

		List<GroceryItemEntity> allGrocery = groceryItemRepo.findAll();

		Map<String, OrderDetails> map = new HashMap<String, GroceryServiceImpl.OrderDetails>();
		allGrocery.forEach(grocery -> {

			OrderDetails orderDetails = new OrderDetails();
			orderDetails.setItemPrice(grocery.getItemPrice());
			orderDetails.setItemQuantity(grocery.getItemQuantity());
			map.put(grocery.getItemName(), orderDetails);

		});

		List<GroceryOrderBook> list = new ArrayList<GroceryOrderBook>();

		groceryOrderBook.forEach(orderBook -> {

			GroceryOrderBook groceryBook = new GroceryOrderBook();

			if (map.containsKey(orderBook.getItemName())) {

				OrderDetails orderDetails = map.get(orderBook.getItemName());

				int itemQuantity = orderDetails.getItemQuantity();
				int itemPrice = orderDetails.getItemPrice();

				orderBook.setAmount(itemPrice * orderBook.getQuantity());

				GroceryItemEntity itemEntity = groceryItemRepo.findByItemName(orderBook.getItemName());
				itemEntity.setItemQuantity(itemQuantity - orderBook.getQuantity());
				groceryItemRepo.saveAndFlush(itemEntity);

				groceryBook.setAmount(orderBook.getAmount());
				groceryBook.setItemName(orderBook.getItemName());
				groceryBook.setQuantity(orderBook.getQuantity());

				list.add(groceryBook);
			}

		});

		return list;

	}

	private static class OrderDetails {

		private int itemQuantity;
		private int itemPrice;

		public int getItemQuantity() {
			return itemQuantity;
		}

		public void setItemQuantity(int itemQuantity) {
			this.itemQuantity = itemQuantity;
		}

		public int getItemPrice() {
			return itemPrice;
		}

		public void setItemPrice(int itemPrice) {
			this.itemPrice = itemPrice;
		}

	}

}
