package com.grocery.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grocery.entity.GroceryItemEntity;
import com.grocery.model.GroceryItemResponse;
import com.grocery.model.GroceryOrderBook;
import com.grocery.service.GroceryService;

@RestController
@RequestMapping("/grocery/v1")
public class GroceryController {

	private final GroceryService groceryService;

	@Autowired
	public GroceryController(GroceryService groceryService) {
		this.groceryService = groceryService;
	}

	@GetMapping(path = "/status")
	public String getStatus() {

		return "Grocery App is Running";
	}

	@PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GroceryItemResponse> addNewGrocery(@RequestBody GroceryItemEntity groceryItemEntity) {

		groceryService.addNewGrocery(groceryItemEntity);

		GroceryItemResponse groceryItemResponse = new GroceryItemResponse();
		groceryItemResponse.setStatus("Item Added");

		List<GroceryItemEntity> allGroceryItems = groceryService.findAllGroceryItems();
		groceryItemResponse.setGroceryList(allGroceryItems);

		return new ResponseEntity<GroceryItemResponse>(groceryItemResponse, HttpStatus.OK);

	}

	@GetMapping(path = "/items")
	public ResponseEntity<List<GroceryItemEntity>> getAllGroceryItems() {

		List<GroceryItemEntity> allGroceryItems = groceryService.findAllGroceryItems();
		return new ResponseEntity<List<GroceryItemEntity>>(allGroceryItems, HttpStatus.OK);
	}

	@DeleteMapping(path = "/remove/{id}")
	public ResponseEntity<GroceryItemResponse> removeItem(@PathVariable("id") Long id) {
		groceryService.deleteItem(id);
		List<GroceryItemEntity> allGroceryItems = groceryService.findAllGroceryItems();
		return new ResponseEntity<GroceryItemResponse>(new GroceryItemResponse("Item Deleted", allGroceryItems),
				HttpStatus.OK);
	}

	@PutMapping(path = "/update")
	public ResponseEntity<GroceryItemResponse> updateGroceryItem(@RequestBody GroceryItemEntity groceryItemEntity) {

		groceryService.updateItem(groceryItemEntity);

		List<GroceryItemEntity> allGroceryItems = groceryService.findAllGroceryItems();

		return new ResponseEntity<GroceryItemResponse>(new GroceryItemResponse("Item Updated", allGroceryItems),
				HttpStatus.OK);

	}

	@GetMapping(path = "/inventory")
	public Map<String, Integer> getInventoryDetails() {

		List<GroceryItemEntity> allGroceryItems = groceryService.findAllGroceryItems();

		Map<String, Integer> collect = allGroceryItems.stream()
				.collect(Collectors.toMap(GroceryItemEntity::getItemName, GroceryItemEntity::getItemQuantity));

		return collect;
	}

	@PutMapping(path = "/order")
	public ResponseEntity<List<GroceryOrderBook>> orderGrocery(@RequestBody List<GroceryOrderBook> groceryOrderBook) {

		List<GroceryOrderBook> orderGrocery = groceryService.orderGrocery(groceryOrderBook);

		return new ResponseEntity<List<GroceryOrderBook>>(orderGrocery, HttpStatus.OK);

	}

}
