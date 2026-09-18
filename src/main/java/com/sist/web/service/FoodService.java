package com.sist.web.service;

import org.springframework.stereotype.Service;
import java.util.*;

import com.sist.web.mapper.FoodMapper;
import com.sist.web.vo.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoodService {
	private final FoodMapper mapper;
	
	public List<FoodVO> foodListData(Map map)
	{
		List<FoodVO> list = mapper.foodListData(map);
		
		for(FoodVO vo : list)
		{
			String temp[] = vo.getTheme().split(",");
			vo.setTheme(temp[0]+","+temp[1]);
		}
		
		return list;
	}
	
	
	public int foodListTotalPage(String serch)
	{
		return mapper.foodListTotalPage(serch);
	}
}
