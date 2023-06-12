package com.obs.sampleproject.entity.input;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class OrderCreateInput {
	@NotNull
	private String orderNo;
	@NotNull
	private String itemId;
	@NotNull
	private Integer qty;
}
