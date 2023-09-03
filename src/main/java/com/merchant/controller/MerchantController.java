package com.merchant.controller;

import java.math.BigInteger;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.merchant.dto.Merchant;
import com.merchant.dto.MerchantReponse;
import com.merchant.dto.Payment;
import com.merchant.dto.PaymentReponse;
import com.merchant.exceptions.MerchantValidationException;
import com.merchant.service.MerchantService;

import jakarta.validation.Valid;

@RestController
public class MerchantController {

	@Autowired
	private MerchantService service;

	@PostMapping("/register_merchant")
	public MerchantReponse registration(@Valid @RequestBody Merchant merchant) throws MerchantValidationException {
		
		MerchantReponse status=service.regStatus(merchant);
		
		return status;
		}
	
	@PostMapping("/payment")
	public PaymentReponse paymentRequest(@Valid @RequestBody Payment paymentRequest)
	{
		PaymentReponse status=service.paymentStatus(paymentRequest);
		return status;
	}
	
	@GetMapping("/payment_status/{paymentId}")
	public PaymentReponse paymentStatusCheck(@PathVariable UUID paymentId)
	{
		PaymentReponse paymentReponse=service.paymentStatusCheck(paymentId);
		return paymentReponse;
		
	}

}
