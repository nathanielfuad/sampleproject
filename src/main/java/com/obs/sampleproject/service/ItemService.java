package com.obs.sampleproject.service;

import java.util.List;

import com.obs.sampleproject.constants.ErrorCode;
import com.obs.sampleproject.model.exception.GeneralErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.obs.sampleproject.model.entity.Item;
import com.obs.sampleproject.model.input.ItemCreateInput;
import com.obs.sampleproject.model.input.ItemUpdateInput;
import com.obs.sampleproject.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {
	private final ItemRepository itemRepository;
	
	public List<Item> getAllItem(){
		return itemRepository.findAll();
	}
	
	public Item getItem(Integer id) {
		return itemRepository.findById(id).orElse(null);
	}
	
	public Item saveItem(ItemCreateInput itemCreateInput) {
		Item item = new Item();
		item.setName(itemCreateInput.getName());
		item.setPrice(itemCreateInput.getPrice());
		return itemRepository.save(item);
	}


	public Item updateItem(ItemUpdateInput itemUpdateInput){
		Item item = getItem(itemUpdateInput.getId());
		if(item==null) {
			throw new GeneralErrorException(ErrorCode.NOT_FOUND);
		}
		if(itemUpdateInput.getName() != null) {
			item.setName(itemUpdateInput.getName());
		}
		if(itemUpdateInput.getPrice() != null) {
			item.setPrice(itemUpdateInput.getPrice());
		}
		return itemRepository.save(item);
	}
	

	public Item deleteItem(Integer id) {
		Item item = getItem(id);
		if(item==null) {
			throw new GeneralErrorException(ErrorCode.NOT_FOUND);
		}
		itemRepository.delete(item);
		return item;
	}
}
