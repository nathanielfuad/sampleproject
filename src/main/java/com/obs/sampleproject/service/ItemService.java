package com.obs.sampleproject.service;

import java.util.ArrayList;
import java.util.List;

import com.obs.sampleproject.constants.ErrorCode;
import com.obs.sampleproject.dto.ItemDetailsDto;
import com.obs.sampleproject.dto.ItemDto;
import com.obs.sampleproject.entity.ItemOrderedDetailsInterface;
import com.obs.sampleproject.model.exception.GeneralErrorException;
import com.obs.sampleproject.repository.OrderRepository;
import org.springframework.stereotype.Service;

import com.obs.sampleproject.entity.Item;
import com.obs.sampleproject.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {
	private final ItemRepository itemRepository;
	private final OrderRepository orderRepository;

	public List<Item> getAllItem(){
		return itemRepository.findAll();
	}
	
	public Item getItem(Integer id) {
		return itemRepository.findById(id).orElse(null);
	}
	
	public Item saveItem(ItemDto itemDto) {
		Item item = new Item();
		item.setName(itemDto.getName());
		item.setPrice(itemDto.getPrice());
		return itemRepository.save(item);
	}


	public Item updateItem(int id, ItemDto itemDto){
		Item item = getItem(id);
		if(item==null) {
			throw new GeneralErrorException(ErrorCode.NOT_FOUND);
		}
		if(itemDto.getName() != null) {
			item.setName(itemDto.getName());
		}
		if(itemDto.getPrice() != null) {
			item.setPrice(itemDto.getPrice());
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

	public List<ItemDetailsDto> getAllItemWithOrderDetails() {
		List<ItemDetailsDto> itemDetailsDtoList = new ArrayList<>();
		List<ItemOrderedDetailsInterface> itemOrderedDetailsList = orderRepository.findSumQtyOfEachItem();
		List<Item> itemList = getAllItem();

		for(Item item : itemList){
			ItemDetailsDto itemDetailsDto = new ItemDetailsDto();
			itemDetailsDto.setId(item.getId());
			itemDetailsDto.setName(item.getName());
			itemDetailsDto.setPrice(item.getPrice());
			for (ItemOrderedDetailsInterface itemOrderDetails: itemOrderedDetailsList) {
				if(itemOrderDetails.getItemId().equals(item.getId())) {
					ItemDetailsDto.Details details = new ItemDetailsDto.Details();
					details.setNumberOfOrders(itemOrderDetails.getNumberOfOrders());
					details.setTotalQtyOrdered(itemOrderDetails.getTotalQtyOrdered());
					itemDetailsDto.setDetails(details);
					itemOrderedDetailsList.remove(itemOrderDetails);
					break;
				}
			}

			itemDetailsDtoList.add(itemDetailsDto);
		}


		return itemDetailsDtoList;
	}
}
