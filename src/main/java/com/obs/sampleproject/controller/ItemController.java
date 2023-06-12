package com.obs.sampleproject.controller;

import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.obs.sampleproject.constants.ErrorCode;
import com.obs.sampleproject.entity.input.ItemCreateInput;
import com.obs.sampleproject.entity.input.ItemUpdateInput;
import com.obs.sampleproject.entity.model.Item;
import com.obs.sampleproject.entity.model.Response;
import com.obs.sampleproject.service.ItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {
	private final ItemService itemService;
	
	@GetMapping
	public Response<List<Item>> getAll(){
		Response<List<Item>> response = new Response<List<Item>>();
		response.setErrorCode(ErrorCode.SUCCESS);
		response.setOutput(itemService.getAllItem());
		return response;
	}
	
	@PostMapping
	public Response<Item> create(@Valid @RequestBody ItemCreateInput itemCreateInput) {
		Response<Item> response = new Response<Item>();
		try {
			Item item = itemService.saveItem(itemCreateInput);
			response.setErrorCode(ErrorCode.SUCCESS);
			response.setOutput(item);
		}catch (Exception e) {
			response.setErrorCode(ErrorCode.GENERAL_ERROR);
			response.setOutput(null);
		}
		
		return response;		
	}
	

	@PutMapping
	public Response<Item> update(@Valid @RequestBody ItemUpdateInput itemUpdateInput) {
		Response<Item> response = new Response<Item>();
		try {
			Item item = itemService.updateItem(itemUpdateInput);
			response.setErrorCode(ErrorCode.SUCCESS);
			response.setOutput(item);
		}catch (Exception e) {
			response.setErrorCode(ErrorCode.GENERAL_ERROR);
			response.setOutput(null);
		}
		
		return response;		
	}


	@DeleteMapping
	public Response<Item> delete(@PathParam("id") Integer id) {
		Response<Item> response = new Response<Item>();
		try {
			Item item = itemService.deleteItem(id);
			response.setErrorCode(ErrorCode.SUCCESS);
			response.setOutput(item);
		}catch (Exception e) {
			response.setErrorCode(ErrorCode.GENERAL_ERROR);
			response.setOutput(null);
		}
		
		return response;		
	}
}
