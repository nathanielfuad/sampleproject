package com.obs.sampleproject.model.input;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class OrderUpdateInput {
	@NotNull
	private int id;
	private String orderNo;
	private Integer itemId;
	private Integer qty;
}
