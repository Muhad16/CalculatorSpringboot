package com.example.calculator.commons;

import java.io.Serializable;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Request implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	private static final Logger LOGGER = LoggerFactory.getLogger(Request.class);
	private String source;
	private String requestDataType;
	private Object requestData;

	@JsonIgnore
	public <K> K getGenericRequestDataObject(@NonNull Class<K> clazz) {
		if (this.getRequestData() != null) {// && this.getRequestData().getClass() == clazz) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				return mapper.convertValue(this.getRequestData(), clazz);
			} catch (IllegalArgumentException e) {
				LOGGER.error("Exception:: {}", ExceptionUtils.getStackTrace(e));
				throw new IllegalArgumentException("Invalid request");
			}
		} else {
			String err = "RequestData is null";
			LOGGER.error(err);
			throw new IllegalArgumentException(err);
		}
	}

}
