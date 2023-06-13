package com.obs.sampleproject.controller;

import javax.validation.Valid;

import com.obs.sampleproject.dto.OrderDto;
import com.obs.sampleproject.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.obs.sampleproject.constants.ErrorCode;
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
	public ResponseEntity<Object> create(@Valid @RequestBody OrderDto orderDto) {
		return responseUtil.generate(ErrorCode.SUCCESS,orderService.saveOrder(orderDto));
	}

	@PutMapping
	public ResponseEntity<Object> update(@RequestParam("id") Integer id, @Valid @RequestBody OrderDto orderDto) {
		return responseUtil.generate(ErrorCode.SUCCESS, orderService.updateOrder(id, orderDto));
	}

	@DeleteMapping
	public ResponseEntity<Object> delete(@RequestParam("id") Integer id) {
		System.out.println("delete");
		return responseUtil.generate(ErrorCode.SUCCESS, orderService.deleteOrder(id));
	}

}
