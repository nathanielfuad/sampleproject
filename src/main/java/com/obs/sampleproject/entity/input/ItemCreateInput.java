package com.obs.sampleproject.entity.input;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ItemCreateInput {
	@NotNull
	private String name;
	@NotNull
	private Integer price;
}
