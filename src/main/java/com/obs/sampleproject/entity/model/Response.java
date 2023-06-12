package com.obs.sampleproject.entity.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Response<T> {
	private String errorCode;
	private T output;
}
