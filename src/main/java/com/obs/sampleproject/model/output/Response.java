package com.obs.sampleproject.model.output;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Response<T> {
	private String errorCode;
	private T output;
}
