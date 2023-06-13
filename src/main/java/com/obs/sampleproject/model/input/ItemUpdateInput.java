package com.obs.sampleproject.model.input;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ItemUpdateInput {
	@NotNull
	private int id;
	private String name;
	private Integer price;
}
