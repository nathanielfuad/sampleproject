package com.obs.sampleproject.controller;

import javax.validation.Valid;

import com.obs.sampleproject.dto.ItemDto;
import com.obs.sampleproject.util.ResponseUtil;
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
		return responseUtil.generate(ErrorCode.SUCCESS, itemService.getAllItem());
	}

	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody ItemDto itemDto) {
		return responseUtil.generate(ErrorCode.SUCCESS,itemService.saveItem(itemDto));
	}

	@PutMapping
	public ResponseEntity<Object> update(@RequestParam("id") Integer id, @Valid @RequestBody ItemDto itemDto) {
		return responseUtil.generate(ErrorCode.SUCCESS, itemService.updateItem(id, itemDto));
	}

	@DeleteMapping
	public ResponseEntity<Object> delete(@RequestParam("id") Integer id) {
		return responseUtil.generate(ErrorCode.SUCCESS, itemService.deleteItem(id));
	}

	@GetMapping("/details")
	public ResponseEntity<Object> getAllWithOrderDetails(){
		return responseUtil.generate(ErrorCode.SUCCESS, itemService.getAllItemWithOrderDetails());
	}
}
