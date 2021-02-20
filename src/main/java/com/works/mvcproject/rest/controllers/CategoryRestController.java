package com.works.mvcproject.rest.controllers;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.works.mvcproject.models.Category;
import com.works.mvcproject.repositories.CategoryRepository;
import com.works.mvcproject.util.RestEnum;
import com.works.mvcproject.util.RestMessages;

@RestController
@RequestMapping("/category")
public class CategoryRestController {

	final CategoryRepository catRepo;
	
	public CategoryRestController(CategoryRepository catRepo) {
		this.catRepo = catRepo;
	}

	@PostMapping("/insert")
	public Map<RestEnum, Object> insert(@RequestBody Category category) {
		Map<RestEnum, Object> hm = new LinkedHashMap<>();

		try {
			Category cat = catRepo.saveAndFlush(category);
			hm.put(RestEnum.status, true);
			hm.put(RestEnum.message, RestMessages.i_success);
			hm.put(RestEnum.result, cat);
		} catch (Exception e) {
			hm.put(RestEnum.status, false);
			hm.put(RestEnum.message, RestMessages.i_fail);
			hm.put(RestEnum.result, category);
		}

		return hm;
	}

	@GetMapping("/list")
	public Map<RestEnum, Object> list() {
		Map<RestEnum, Object> hm = new LinkedHashMap<>();
		hm.put(RestEnum.status, true);
		hm.put(RestEnum.message, RestMessages.list_success);
		hm.put(RestEnum.result, catRepo.findAll());

		return hm;
	}
	
	@GetMapping("/findCategoryById")
	public Map<RestEnum, Object> findCategoryById(@RequestParam int cid)
	{
		Map<RestEnum, Object> hm = new LinkedHashMap<>();
		
		try {			
			Optional<Category> ocategory = catRepo.findById(cid);
			ocategory.ifPresent(item -> { 
				
				hm.put(RestEnum.status, true);
				hm.put(RestEnum.message,RestMessages.list_success);
				hm.put(RestEnum.result, ocategory);
			});
			
			if(!ocategory.isPresent())
			{
				hm.put(RestEnum.status, false);
				hm.put(RestEnum.message,RestMessages.list_fail);
			}
		} catch (NumberFormatException e) {	
			hm.put(RestEnum.status, false);
			hm.put(RestEnum.message,RestMessages.list_fail +e);
		}	
		
		return hm;
	}

	@GetMapping("/deleteSingle")
	public Map<RestEnum, Object> deleteSingle(@RequestParam int cid) {
		Map<RestEnum, Object> hm = new LinkedHashMap<>();
		
		try {
			catRepo.deleteById(cid);
			hm.put(RestEnum.status, true);
			hm.put(RestEnum.message, RestMessages.d_success);
			hm.put(RestEnum.result, cid);
		} catch (Exception e) {
			hm.put(RestEnum.status, false);
			hm.put(RestEnum.message, RestMessages.d_fail);
			hm.put(RestEnum.result, cid);
		}
		return hm;
	}

	@PostMapping("/updateSingle")
	public Map<RestEnum, Object> updateSingle(@RequestBody Category category) {
		
		Map<RestEnum, Object> hm = new LinkedHashMap<>();		

		try {
			Optional<Category> oCat = catRepo.findById(category.getCid());
			if (oCat.isPresent()) {
				Category cat = catRepo.saveAndFlush(category);
				hm.put(RestEnum.status, true);
				hm.put(RestEnum.message, RestMessages.u_success);
				hm.put(RestEnum.result, cat);
			}
		} catch (Exception e) {
			hm.put(RestEnum.status, false);
			hm.put(RestEnum.message, RestMessages.u_fail);
			hm.put(RestEnum.result, category);
		}

		return hm;
	}

	@GetMapping("/search")
	public Map<RestEnum, Object> search(@RequestParam String data) { 
		Map<RestEnum, Object> hm = new LinkedHashMap<>();
		
		if (data.equals("")) {
			hm.put(RestEnum.result, catRepo.findAll());
		}else
		{
			hm.put(RestEnum.status, true);
			hm.put(RestEnum.message, RestMessages.list_success);
			hm.put(RestEnum.result, catRepo.searchData("%"+data+"%"));			
		}		

		return hm;
	}			

}