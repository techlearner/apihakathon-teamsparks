package com.apihackathon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Router {

	@RequestMapping("/get_menu.json")
	public @ResponseBody String getMenu() {
		return "Hello World!";
	}
	
	@RequestMapping("/bigo.json")
	public @ResponseBody String bigo(@RequestParam(name="bigo", required=true)String bigO) {
		String[] values = bigO.split(" ");
		String type = values[0];
		String returnVal = "processed";
		switch(type){
		case "PO":
			returnVal = processOrder(values);
			break;
		case "CO":
			returnVal = confirmOrder(values);
			break;
		}
		
		return returnVal;
	}

	private String confirmOrder(String[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	private String processOrder(String[] values) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
