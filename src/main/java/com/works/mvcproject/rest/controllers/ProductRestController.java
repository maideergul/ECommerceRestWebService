package com.works.mvcproject.rest.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.works.mvcproject.models.Product;
import com.works.mvcproject.repositories.ProductRepository;
import com.works.mvcproject.util.RestEnum;
import com.works.mvcproject.util.RestMessages;

@RestController
@RequestMapping("/product")
public class ProductRestController {

	final ProductRepository proRepo;

	public ProductRestController(ProductRepository proRepo) {
		this.proRepo = proRepo;		
	}

	@PostMapping("/insert")
	public Map<RestEnum, Object> insert(@RequestBody Product product) {
		Map<RestEnum, Object> hm = new LinkedHashMap<>();

		try {
			Product pro = proRepo.saveAndFlush(product);
			hm.put(RestEnum.status, true);
			hm.put(RestEnum.message, RestMessages.i_success);
			hm.put(RestEnum.result, pro);
			
		} catch (Exception e) {
			hm.put(RestEnum.status, false);
			hm.put(RestEnum.message, RestMessages.i_fail +e);
			hm.put(RestEnum.result, product);
		}

		return hm;
	}

	@GetMapping("/list")
	public Map<RestEnum, Object> list() {
		Map<RestEnum, Object> hm = new LinkedHashMap<>();

		hm.put(RestEnum.status, true);
		hm.put(RestEnum.message, RestMessages.list_success);
		hm.put(RestEnum.result, proRepo.findAll());
		return hm;
	}

	@GetMapping("/cartProductCount")
	public Map<RestEnum, Object> cartProductCount() {
		Map<RestEnum, Object> hm = new LinkedHashMap<>();

		hm.put(RestEnum.status, true);
		hm.put(RestEnum.message, RestMessages.list_success);
		hm.put(RestEnum.result, proRepo.countByStatu(1).size());
		return hm;
	}

	@GetMapping("/totalPriceOfProducts")
	public Map<RestEnum, Object> totalPriceOfProducts() {
		Map<RestEnum, Object> hm = new LinkedHashMap<>();

		hm.put(RestEnum.status, true);
		hm.put(RestEnum.message, RestMessages.list_success);
		hm.put(RestEnum.result, proRepo.findTotalPriceOfProduct(1));
		return hm;
	}

	@GetMapping("/cartTotalAmount")
	public Map<RestEnum, Object> cartTotalAmount() {
		Map<RestEnum, Object> hm = new LinkedHashMap<>();

		hm.put(RestEnum.status, true);
		hm.put(RestEnum.message, RestMessages.list_success);
		hm.put(RestEnum.result, proRepo.findByPstatuContains(1));
		return hm;
	}

	@GetMapping("/productsInCart") 
	public Map<RestEnum, Object> productsInCart() {
		Map<RestEnum, Object> hm = new LinkedHashMap<>();

		hm.put(RestEnum.status, true);
		hm.put(RestEnum.message, RestMessages.list_success);
		hm.put(RestEnum.result, proRepo.countByStatu(1));
		return hm;
	}

	@GetMapping("/findProductByCid")
	public Map<RestEnum, Object> findProductByCid(@RequestParam int cid) {
		Map<RestEnum, Object> hm = new LinkedHashMap<>();

		try {
			List<Product> product = proRepo.findByCid(cid);

			hm.put(RestEnum.status, true);
			hm.put(RestEnum.message, RestMessages.list_success);
			hm.put(RestEnum.result, product);

		} catch (NumberFormatException e) {
			hm.put(RestEnum.status, false);
			hm.put(RestEnum.message, RestMessages.list_fail +e);
		}

		return hm;
	}

	@GetMapping("/findProductById")
	public Map<RestEnum, Object> findProductById(@RequestParam int pid) {
		Map<RestEnum, Object> hm = new LinkedHashMap<>();

		try {
			Optional<Product> oproduct = proRepo.findById(pid);
			oproduct.ifPresent(item -> {

				hm.put(RestEnum.status, true);
				hm.put(RestEnum.message, RestMessages.list_success);
				hm.put(RestEnum.result, oproduct);

			});

			if (!oproduct.isPresent()) {
				hm.put(RestEnum.status, false);
				hm.put(RestEnum.message, RestMessages.list_fail);
			}
		} catch (NumberFormatException e) {
			hm.put(RestEnum.status, false);
			hm.put(RestEnum.message, RestMessages.list_fail +e);
		}

		return hm;
	}

	@DeleteMapping("/deleteSingle")
	public Map<RestEnum, Object> deleteSingle(@RequestBody Product p) {
		Map<RestEnum, Object> hm = new LinkedHashMap<>();

		try {			
			proRepo.deleteById(p.getPid());
			hm.put(RestEnum.status, true);
			hm.put(RestEnum.message, RestMessages.d_success);
			hm.put(RestEnum.result, p.getPid());

		} catch (Exception e) {
			hm.put(RestEnum.status, false);
			hm.put(RestEnum.message, RestMessages.d_fail +e);
			hm.put(RestEnum.result, p.getPid());
		}

		return hm;
	}
	
	@PutMapping("/updateSingle")
	public Map<RestEnum, Object> updateSingle(@RequestBody Product product) {
		Map<RestEnum, Object> hm = new LinkedHashMap<>();

		try {
			Optional<Product> oPro = proRepo.findById(product.getPid());
			if (oPro.isPresent()) {
				Product pro = proRepo.saveAndFlush(product);
				hm.put(RestEnum.status, true);
				hm.put(RestEnum.message, RestMessages.u_success);
				hm.put(RestEnum.result, pro);
			}

		} catch (Exception e) {
			hm.put(RestEnum.status, false);
			hm.put(RestEnum.message, RestMessages.u_fail +e);		
		}

		return hm;
	} 		
	
}