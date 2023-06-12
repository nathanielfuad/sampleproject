package com.obs.sampleproject.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.obs.sampleproject.entity.model.Item;
import com.obs.sampleproject.entity.model.Order;
import com.obs.sampleproject.entity.input.ItemCreateInput;
import com.obs.sampleproject.entity.input.ItemUpdateInput;
import com.obs.sampleproject.entity.input.OrderCreateInput;
import com.obs.sampleproject.entity.input.OrderUpdateInput;
import com.obs.sampleproject.repository.ItemRepository;
import com.obs.sampleproject.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;
	
	public List<Order> getAllOrder(){
		return orderRepository.findAll();
	}
	
	public Order getOrder(Integer id) {
		return orderRepository.findById(id).orElse(null);
	}
	
	public Order saveOrder(OrderCreateInput orderCreateInput) {
		Order order = new Order();
		order.setOrderNo(orderCreateInput.getOrderNo());
		order.setItemId(orderCreateInput.getItemId());
		order.setQty(orderCreateInput.getQty());
		return orderRepository.save(order);
	}


	public Order updateOrder(OrderUpdateInput orderUpdateInput) throws Exception {
		Order order = getOrder(orderUpdateInput.getId());
		if(order==null) {
			throw new Exception();
		}
		if(orderUpdateInput.getItemId() != null) {
			order.setItemId(orderUpdateInput.getItemId());
		}
		if(orderUpdateInput.getOrderNo() != null) {
			order.setOrderNo(orderUpdateInput.getOrderNo());
		}
		if(orderUpdateInput.getQty() != null) {
			order.setQty(orderUpdateInput.getQty());
		}
		return orderRepository.save(order);
	}
	

	public Order deleteOrder(Integer id) throws Exception {
		Order order = getOrder(id);
		if(order==null) {
			throw new Exception();
		}
		orderRepository.delete(order);
		return order;
	}
}
