package com.obs.sampleproject.controller;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import com.obs.sampleproject.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.obs.sampleproject.constants.ErrorCode;
import com.obs.sampleproject.model.input.ItemCreateInput;
import com.obs.sampleproject.model.input.ItemUpdateInput;
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
	public ResponseEntity<Object> create(@Valid @RequestBody ItemCreateInput itemCreateInput) {
		return responseUtil.generate(ErrorCode.SUCCESS,itemService.saveItem(itemCreateInput));
	}

	@PutMapping
	public ResponseEntity<Object> update(@Valid @RequestBody ItemUpdateInput itemUpdateInput) {
		return responseUtil.generate(ErrorCode.SUCCESS, itemService.updateItem(itemUpdateInput));
	}

	@DeleteMapping
	public ResponseEntity<Object> delete(@PathParam("id") Integer id) {
		return responseUtil.generate(ErrorCode.SUCCESS, itemService.deleteItem(id));
	}
}
