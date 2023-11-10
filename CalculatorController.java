package com.example.calculator.operate;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.calculator.beans.CalculatorReqBean;
import com.example.calculator.beans.Response;
import com.example.calculator.commons.Request;

@RestController
@RequestMapping("/calculator")
public class CalculatorController {

	private static final Logger logger = LoggerFactory.getLogger(CalculatorController.class);

	@GetMapping("/operate")
	public Response operate(@RequestBody Request request) {
		try {
			CalculatorReqBean req = request.getGenericRequestDataObject(CalculatorReqBean.class);
			BigDecimal result;
			if(req.getFirstNo() == null || req.getSecondNo() == null) {
				throw new IllegalArgumentException("Please enter the Inputs");
			}
			if(!req.getFirstNo().matches("^[-+]?[0-9]*\\.?[0-9]+$") || !req.getSecondNo().matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
				throw new IllegalArgumentException("Input data must be numercic");
			}
			BigDecimal number1 = new BigDecimal(req.getFirstNo());
			BigDecimal number2 = new BigDecimal(req.getSecondNo());
			switch (req.getOperator().toLowerCase()) {
			case "add":
				result = number1.add(number2);
				break;
			case "subtract":
				result = number1.subtract(number2);
				break;
			case "multiply":
				result =number1.multiply(number2);
				break;
			case "divide":
				if (number2.equals(BigDecimal.ZERO)) {
					throw new IllegalArgumentException("Cannot divide by zero");
				}
				result = number1.divide(number2);
				break;
			default:
				throw new IllegalArgumentException("Invalid operation: " + req.getOperator());
			}
			logger.info("Operation {} performed successfully", req.getOperator());
			return new Response("success", result);
		} catch (Exception e) {
			logger.error("Error performing operation", e);
			return new Response("error", e.getMessage());
		}
	}
}