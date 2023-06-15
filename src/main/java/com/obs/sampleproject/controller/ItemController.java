package com.obs.sampleproject.controller;

import javax.validation.Valid;

import com.obs.sampleproject.constants.ActivityName;
import com.obs.sampleproject.constants.Log;
import com.obs.sampleproject.dto.ItemDto;
import com.obs.sampleproject.util.ResponseUtil;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.obs.sampleproject.constants.ErrorCode;
import com.obs.sampleproject.service.ItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {
	private final ItemService itemService;
	private final ResponseUtil responseUtil;

	@GetMapping
	public ResponseEntity<Object> getAll(){
		MDC.put(Log.ACTIVITY_NAME, ActivityName.GET_ALL_ITEM);
		return responseUtil.generate(ErrorCode.SUCCESS, itemService.getAllItem());
	}

	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody ItemDto itemDto) {
		MDC.put(Log.ACTIVITY_NAME, ActivityName.CREATE_ITEM);
		return responseUtil.generate(ErrorCode.SUCCESS,itemService.saveItem(itemDto));
	}

	@PutMapping
	public ResponseEntity<Object> update(@RequestParam("id") Integer id, @Valid @RequestBody ItemDto itemDto) {
		MDC.put(Log.ACTIVITY_NAME, ActivityName.UPDATE_ITEM);
		return responseUtil.generate(ErrorCode.SUCCESS, itemService.updateItem(id, itemDto));
	}

	@DeleteMapping
	public ResponseEntity<Object> delete(@RequestParam("id") Integer id) {
		MDC.put(Log.ACTIVITY_NAME, ActivityName.DELETE_ITEM);
		return responseUtil.generate(ErrorCode.SUCCESS, itemService.deleteItem(id));
	}

	@GetMapping("/details")
	public ResponseEntity<Object> getAllWithOrderDetails(){
		MDC.put(Log.ACTIVITY_NAME, ActivityName.GET_ITEM_DETAILS);
		return responseUtil.generate(ErrorCode.SUCCESS, itemService.getAllItemWithOrderDetails());
	}
}
