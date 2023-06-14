package com.obs.sampleproject.service;

import java.util.ArrayList;
import java.util.List;

import com.obs.sampleproject.constants.ErrorCode;
import com.obs.sampleproject.dto.ItemDetailsDto;
import com.obs.sampleproject.dto.ItemDto;
import com.obs.sampleproject.entity.ItemOrderedDetailsInterface;
import com.obs.sampleproject.model.exception.GeneralErrorException;
import com.obs.sampleproject.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.obs.sampleproject.entity.Item;
import com.obs.sampleproject.repository.ItemRepository;

import lombok.RequiredArgsConstructor;
import org.modelmapper.TypeToken;
import java.lang.reflect.Type;


@Service
@RequiredArgsConstructor
public class ItemService {
	private final ItemRepository itemRepository;
	private final OrderRepository orderRepository;
	private final ModelMapper modelMapper;

	public List<ItemDto> getAllItem(){
		Type listItemDtoType = new TypeToken<List<ItemDto>>(){}.getType();
		return modelMapper.map(itemRepository.findAll(), listItemDtoType);
	}
	
	public ItemDto getItem(Integer id) {
		Item item = itemRepository.findById(id).orElse(null);
		if(item == null) return null;
		return modelMapper.map(item, ItemDto.class);
	}
	
	public ItemDto saveItem(ItemDto itemDto) {
		Item item = new Item();
		item.setName(itemDto.getName());
		item.setPrice(itemDto.getPrice());
		item = itemRepository.save(item);

		itemDto.setId(item.getId());
		return itemDto;
	}


	public ItemDto updateItem(int id, ItemDto itemDto){
		Item item = itemRepository.findById(id).orElse(null);
		if(item==null) {
			throw new GeneralErrorException(ErrorCode.NOT_FOUND);
		}
		if(itemDto.getName() != null) {
			item.setName(itemDto.getName());
		}
		if(itemDto.getPrice() != null) {
			item.setPrice(itemDto.getPrice());
		}

		itemRepository.save(item);

		return modelMapper.map(item, ItemDto.class);
	}
	

	public ItemDto deleteItem(Integer id) {
		Item item = itemRepository.findById(id).orElse(null);
		if(item==null) {
			throw new GeneralErrorException(ErrorCode.NOT_FOUND);
		}
		itemRepository.delete(item);
		return modelMapper.map(item, ItemDto.class);
	}

	public List<ItemDetailsDto> getAllItemWithOrderDetails() {
		List<ItemDetailsDto> itemDetailsDtoList = new ArrayList<>();
		List<ItemOrderedDetailsInterface> itemOrderedDetailsList = orderRepository.findSumQtyOfEachItem();
		List<Item> itemList = itemRepository.findAll();

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
