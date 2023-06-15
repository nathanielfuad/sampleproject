package com.obs.sampleproject.controller;

import javax.validation.Valid;

import com.obs.sampleproject.constants.ActivityName;
import com.obs.sampleproject.constants.Log;
import com.obs.sampleproject.dto.OrderDto;
import com.obs.sampleproject.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.obs.sampleproject.constants.ErrorCode;
import com.obs.sampleproject.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
@Slf4j
public class OrderController {
	private final OrderService orderService;
	private final ResponseUtil responseUtil;

	@GetMapping
	public ResponseEntity<Object> getAll(){
		MDC.put(Log.ACTIVITY_NAME, ActivityName.GET_ALL_ORDER);
		return responseUtil.generate(ErrorCode.SUCCESS, orderService.getAllOrder());
	}

	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody OrderDto orderDto) {
		MDC.put(Log.ACTIVITY_NAME, ActivityName.CREATE_ORDER);
		return responseUtil.generate(ErrorCode.SUCCESS,orderService.saveOrder(orderDto));
	}

	@PutMapping
	public ResponseEntity<Object> update(@RequestParam("id") Integer id, @Valid @RequestBody OrderDto orderDto) {
		MDC.put(Log.ACTIVITY_NAME, ActivityName.UPDATE_ORDER);
		return responseUtil.generate(ErrorCode.SUCCESS, orderService.updateOrder(id, orderDto));
	}

	@DeleteMapping
	public ResponseEntity<Object> delete(@RequestParam("id") Integer id) {
		MDC.put(Log.ACTIVITY_NAME, ActivityName.DELETE_ORDER);
		return responseUtil.generate(ErrorCode.SUCCESS, orderService.deleteOrder(id));
	}

}
