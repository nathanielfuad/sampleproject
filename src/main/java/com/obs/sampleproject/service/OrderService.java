package com.obs.sampleproject.service;

import java.util.List;

import com.obs.sampleproject.constants.ErrorCode;
import com.obs.sampleproject.model.exception.GeneralErrorException;
import org.springframework.stereotype.Service;

import com.obs.sampleproject.model.entity.Order;
import com.obs.sampleproject.model.input.OrderCreateInput;
import com.obs.sampleproject.model.input.OrderUpdateInput;
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
	
	public Order saveOrder(OrderCreateInput orderCreateInput) {
		Order order = new Order();
		order.setOrderNo(orderCreateInput.getOrderNo());
		order.setItem(itemService.getItem(orderCreateInput.getItemId()));
		order.setQty(orderCreateInput.getQty());
		return orderRepository.save(order);
	}


	public Order updateOrder(OrderUpdateInput orderUpdateInput) {
		Order order = getOrder(orderUpdateInput.getId());
		if(order==null) {
			throw new GeneralErrorException(ErrorCode.NOT_FOUND);
		}
		if(orderUpdateInput.getItemId() != null) {
			order.setItem(itemService.getItem(orderUpdateInput.getItemId()));
		}
		if(orderUpdateInput.getOrderNo() != null) {
			order.setOrderNo(orderUpdateInput.getOrderNo());
		}
		if(orderUpdateInput.getQty() != null) {
			order.setQty(orderUpdateInput.getQty());
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
