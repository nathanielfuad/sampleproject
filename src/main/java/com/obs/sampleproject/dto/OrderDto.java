package com.obs.sampleproject.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {
	private Integer id;
	private String orderNo;
	private Integer itemId;
	private Integer qty;
}
