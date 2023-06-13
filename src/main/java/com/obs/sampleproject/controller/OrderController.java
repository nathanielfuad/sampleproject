package com.obs.sampleproject.controller;

import javax.validation.Valid;

import com.obs.sampleproject.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.obs.sampleproject.constants.ErrorCode;
import com.obs.sampleproject.model.input.OrderCreateInput;
import com.obs.sampleproject.model.input.OrderUpdateInput;
import com.obs.sampleproject.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
	private final OrderService orderService;
	private final ResponseUtil responseUtil;

	@GetMapping
	public ResponseEntity<Object> getAll(){
		return responseUtil.generate(ErrorCode.SUCCESS, orderService.getAllOrder());
	}
	
	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody OrderCreateInput orderCreateInput) {
		return responseUtil.generate(ErrorCode.SUCCESS,orderService.saveOrder(orderCreateInput));
	}

	@PutMapping
	public ResponseEntity<Object> update(@Valid @RequestBody OrderUpdateInput orderUpdateInput) {
		return responseUtil.generate(ErrorCode.SUCCESS, orderService.updateOrder(orderUpdateInput));
	}

	@DeleteMapping
	public ResponseEntity<Object> delete(@RequestParam("id") Integer id) {
		System.out.println("delete");
		return responseUtil.generate(ErrorCode.SUCCESS, orderService.deleteOrder(id));
	}

}
