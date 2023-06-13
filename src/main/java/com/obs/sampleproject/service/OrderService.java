package com.obs.sampleproject.service;

import java.util.List;

import com.obs.sampleproject.constants.ErrorCode;
import com.obs.sampleproject.dto.OrderDto;
import com.obs.sampleproject.model.exception.GeneralErrorException;
import org.springframework.stereotype.Service;

import com.obs.sampleproject.entity.Order;
import com.obs.sampleproject.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final ItemService itemService;
	
	public List<Order> getAllOrder(){
		return orderRepository.findAll();
	}
	
	public Order getOrder(Integer id) {
		return orderRepository.findById(id).orElse(null);
	}
	
	public Order saveOrder(com.obs.sampleproject.dto.OrderDto orderDto) {
		Order order = new Order();
		order.setOrderNo(orderDto.getOrderNo());
		order.setItem(itemService.getItem(orderDto.getItemId()));
		order.setQty(orderDto.getQty());
		return orderRepository.save(order);
	}


	public Order updateOrder(int id, OrderDto orderDto) {
		Order order = getOrder(id);
		if(order==null) {
			throw new GeneralErrorException(ErrorCode.NOT_FOUND);
		}
		if(orderDto.getItemId() != null) {
			order.setItem(itemService.getItem(orderDto.getItemId()));
		}
		if(orderDto.getOrderNo() != null) {
			order.setOrderNo(orderDto.getOrderNo());
		}
		if(orderDto.getQty() != null) {
			order.setQty(orderDto.getQty());
		}
		return orderRepository.save(order);
	}
	

	public Order deleteOrder(Integer id) {
		Order order = getOrder(id);
		if(order==null) {
			throw new GeneralErrorException(ErrorCode.NOT_FOUND);
		}
		orderRepository.delete(order);
		return order;
	}
}
