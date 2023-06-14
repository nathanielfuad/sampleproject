package com.obs.sampleproject.service;

import java.lang.reflect.Type;
import java.util.List;

import com.obs.sampleproject.constants.ErrorCode;
import com.obs.sampleproject.dto.OrderDto;
import com.obs.sampleproject.model.exception.GeneralErrorException;
import com.obs.sampleproject.repository.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.modelmapper.TypeToken;

import com.obs.sampleproject.entity.Order;
import com.obs.sampleproject.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final ItemRepository itemRepository;
	private final ModelMapper modelMapper;
	
	public List<OrderDto> getAllOrder(){
		Type orderDtoListType = new TypeToken<List<OrderDto>>(){}.getType();
		return modelMapper.map(orderRepository.findAll(), orderDtoListType);
	}
	
	public OrderDto getOrder(Integer id) {
		Order order = orderRepository.findById(id).orElse(null);
		if(order == null) return null;
		return modelMapper.map(order, OrderDto.class);
	}
	
	public OrderDto saveOrder(OrderDto orderDto) {
		Order order = new Order();
		order.setOrderNo(orderDto.getOrderNo());
		order.setItem(itemRepository.findById(orderDto.getItemId()).orElse(null));
		order.setQty(orderDto.getQty());
		order = orderRepository.save(order);

		orderDto.setId(order.getId());
		return orderDto;
	}


	public OrderDto updateOrder(int id, OrderDto orderDto) {
		Order order = orderRepository.findById(id).orElse(null);
		if(order==null) {
			throw new GeneralErrorException(ErrorCode.NOT_FOUND);
		}
		if(orderDto.getItemId() != null) {
			order.setItem(itemRepository.findById(orderDto.getItemId()).orElse(null));
		}
		if(orderDto.getOrderNo() != null) {
			order.setOrderNo(orderDto.getOrderNo());
		}
		if(orderDto.getQty() != null) {
			order.setQty(orderDto.getQty());
		}

		orderRepository.save(order);

		return modelMapper.map(order, OrderDto.class);
	}
	

	public OrderDto deleteOrder(Integer id) {
		Order order = orderRepository.findById(id).orElse(null);
		if(order==null) {
			throw new GeneralErrorException(ErrorCode.NOT_FOUND);
		}
		orderRepository.delete(order);
		return modelMapper.map(order, OrderDto.class);
	}
}
