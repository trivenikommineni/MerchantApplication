package com.merchant.service;

import java.math.BigInteger;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.RestTemplate;

import com.merchant.dto.Merchant;
import com.merchant.dto.MerchantReponse;
import com.merchant.dto.Payment;
import com.merchant.dto.PaymentReponse;
import com.merchant.exceptions.MerchantValidationException;

@Service
public class MerchantService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	public MerchantReponse regStatus(Merchant merchant) throws MerchantValidationException 
	{
		
			
			boolean flag=MerchantService.isValidPhoneNumber(merchant.getPhone().toString());
			if(!flag)
			{
				String msg = "please enter valid phone number";
				throw new MerchantValidationException(msg);
			}
		
		
		String url="http://localhost:9000/register_merchant";
		HttpEntity<Merchant> requestEntity=new HttpEntity<Merchant>(merchant);
		ResponseEntity<MerchantReponse> response=restTemplate.exchange(url, HttpMethod.POST, requestEntity, MerchantReponse.class);
		MerchantReponse merchantReponse = response.getBody();
	
		return merchantReponse;
		
	}
	
	public PaymentReponse paymentStatus(Payment paymentRequest)
	{
		String url="http://localhost:9000/payment";
		HttpEntity<Payment> requestEntity=new HttpEntity<Payment>(paymentRequest);
		ResponseEntity<PaymentReponse> response=restTemplate.exchange(url, HttpMethod.POST, requestEntity, PaymentReponse.class);
		PaymentReponse paymentReponse=response.getBody();
		return paymentReponse;
		
	}
	
	public PaymentReponse paymentStatusCheck(UUID paymentId)
	{ 
		//String url="http://localhost:9000/payment_status/{paymentId}";
		String url="http://localhost:9000/payment_status/"+paymentId;
		ResponseEntity<PaymentReponse> response = restTemplate.getForEntity(url, PaymentReponse.class);
		PaymentReponse paymentReponse=response.getBody();
		 return paymentReponse;
	}
	
	public static boolean isValidPhoneNumber(String phoneNumber) {
       
		String regexPattern = "^[6-9]\\d{9}$";

        // Compile the pattern
        Pattern pattern = Pattern.compile(regexPattern);

        // Create a Matcher object
        Matcher matcher = pattern.matcher(phoneNumber);

        // Check if the phone number matches the pattern
        return matcher.matches();
    }
	
	

}
