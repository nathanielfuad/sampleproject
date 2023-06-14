package com.obs.sampleproject.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {
	@NotNull
	private String orderNo;
	@NotNull
	private Integer itemId;
	@NotNull
	private Integer qty;
}
