package com.works.mvcproject.rest.controllers;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.works.mvcproject.repositories.CartProcRepository;
import com.works.mvcproject.util.RestEnum;
import com.works.mvcproject.util.RestMessages;

@RestController
@RequestMapping("/cartProc")
public class CartProcRestController {
	
	final CartProcRepository cartProcRepo;
	
	public CartProcRestController(CartProcRepository cartProcRepo) {
		this.cartProcRepo = cartProcRepo;
	}
	
	@GetMapping("/getCart")
	public Map<RestEnum, Object> getCart()
	{
		Map<RestEnum, Object> hm = new LinkedHashMap<>();
		hm.put(RestEnum.status, true);
		hm.put(RestEnum.message, RestMessages.list_success);
		hm.put(RestEnum.result, cartProcRepo.getCart(1));
		
		return hm;
	}

}