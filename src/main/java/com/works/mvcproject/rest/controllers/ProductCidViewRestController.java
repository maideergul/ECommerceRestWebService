package com.works.mvcproject.rest.controllers;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.works.mvcproject.repositories.ProductCidViewRepository;
import com.works.mvcproject.util.RestEnum;
import com.works.mvcproject.util.RestMessages;

@RestController
@RequestMapping("/productCidView")
public class ProductCidViewRestController {
	
	final ProductCidViewRepository pcvRepo;
	
	public ProductCidViewRestController(ProductCidViewRepository pcvRepo) {
		this.pcvRepo = pcvRepo;
	}
	
	@GetMapping("/list")
	public Map<RestEnum, Object> list()
	{
		Map<RestEnum, Object> hm = new LinkedHashMap<>();
		hm.put(RestEnum.status, true);
		hm.put(RestEnum.message, RestMessages.list_success);
		hm.put(RestEnum.result, pcvRepo.findAll());
		
		return hm;
	}
	
	@GetMapping("/searchData")
	public Map<RestEnum, Object> searchData(@RequestParam String data)
	{
		Map<RestEnum, Object> hm = new LinkedHashMap<>();
		
		if(data.equals(""))
		{
			hm.put(RestEnum.result, pcvRepo.findAll());
		}else
		{
			hm.put(RestEnum.status, true);
			hm.put(RestEnum.message, RestMessages.list_success);
			hm.put(RestEnum.result, pcvRepo.searchData("%"+data+"%"));
		}		
			return hm;
		
	}
}