package com.sist.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.web.service.FoodService;
import com.sist.web.vo.FoodVO;

import lombok.RequiredArgsConstructor;
/*
 *  1. Dockerfile
 *  	Spring-Boot
 *  		|
 *  	   Jar
 *  		|
 *  	Docker image
 *  		|
 *  	Container: 한개를 실행하는 프로그램 (환경 설정)
 *  
 *  2. docker-compose
 *  	
 *  3. nginx.conf
 *  4. Jenkinsfile
 */
@Controller
@RequiredArgsConstructor
public class FoodController {
	private final FoodService service;
	
	@GetMapping("/main")
	public String main_page(Model model)
	{
		model.addAttribute("main_html", "main/home");
		return "main/main";
	}
	
	@RequestMapping("/food/list")
	public String food_list(
		@RequestParam(value = "search",required = false) String search,
		@RequestParam(value = "page",required = false) String page,
		Model model
	)
	{
		if(page == null)
			page = "1";
		
		int curpage = Integer.parseInt(page);
		
		Map map = new HashMap<>();
		
		map.put("search", search);
		map.put("start", (curpage*12)-12);
		
		List<FoodVO> list = service.foodListData(map);
		int count = service.foodListTotalPage(search);
		
		//페이징
		int totalpage = (int)(Math.ceil(count/12.0));
		final int BLOCK = 10;
		int startPage = ((curpage-1)/BLOCK*BLOCK)+1;
		int endPage = ((curpage-1)/BLOCK*BLOCK)+BLOCK;
		if(endPage > totalpage)
			endPage = totalpage;
		
		model.addAttribute("list", list);
		model.addAttribute("curpage", curpage);
		model.addAttribute("totalpage", totalpage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("startPage", startPage);
		
		model.addAttribute("main_html", "food/list");
		return "main/main";
	}
}
