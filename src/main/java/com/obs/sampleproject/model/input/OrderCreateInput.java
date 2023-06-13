package com.obs.sampleproject.model.input;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class OrderCreateInput {
	@NotNull
	private String orderNo;
	@NotNull
	private Integer itemId;
	@NotNull
	private Integer qty;
}
