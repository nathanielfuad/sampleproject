package com.obs.sampleproject.controller;

import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.obs.sampleproject.constants.ErrorCode;
import com.obs.sampleproject.entity.input.OrderCreateInput;
import com.obs.sampleproject.entity.input.OrderUpdateInput;
import com.obs.sampleproject.entity.model.Order;
import com.obs.sampleproject.entity.model.Response;
import com.obs.sampleproject.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
	private final OrderService orderService;
	

	@GetMapping
	public Response<List<Order>> getAll(){
		Response<List<Order>> response = new Response<List<Order>>();
		response.setErrorCode(ErrorCode.SUCCESS);
		response.setOutput(orderService.getAllOrder());
		return response;
	}
	
	@PostMapping
	public Response<Order> create(@Valid @RequestBody OrderCreateInput orderCreateInput) {
		Response<Order> response = new Response<Order>();
		try {
			Order order = orderService.saveOrder(orderCreateInput);
			response.setErrorCode(ErrorCode.SUCCESS);
			response.setOutput(order);
		}catch (Exception e) {
			response.setErrorCode(ErrorCode.GENERAL_ERROR);
			response.setOutput(null);
		}
		
		return response;		
	}
	

	@PutMapping
	public Response<Order> update(@Valid @RequestBody OrderUpdateInput orderUpdateInput) {
		Response<Order> response = new Response<Order>();
		try {
			Order order = orderService.updateOrder(orderUpdateInput);
			response.setErrorCode(ErrorCode.SUCCESS);
			response.setOutput(order);
		}catch (Exception e) {
			response.setErrorCode(ErrorCode.GENERAL_ERROR);
			response.setOutput(null);
		}
		
		return response;		
	}


	@DeleteMapping
	public Response<Order> delete(@PathParam("id") Integer id) {
		Response<Order> response = new Response<Order>();
		try {
			Order order = orderService.deleteOrder(id);
			response.setErrorCode(ErrorCode.SUCCESS);
			response.setOutput(order);
		}catch (Exception e) {
			response.setErrorCode(ErrorCode.GENERAL_ERROR);
			response.setOutput(null);
		}
		
		return response;		
	}

}
