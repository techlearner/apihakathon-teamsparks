package com.apihackathon.controller;

import com.apihackathon.model.Item;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class Router {

	@RequestMapping("/get_menu.json")
	public @ResponseBody String getMenu() {
		return "Hello World!";
	}
	
	@RequestMapping("/bigo.json")
	public @ResponseBody String bigo(HttpServletRequest request) {

		String body = request.getParameter("body");
		String[] values = body.split(",");
		String typeWithNo = values[0];
		String type = typeWithNo.split(" ")[1];
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

		return null;
	}

	private String processOrder(String[] values) {
		String store = values[1];
		int i = 2;
		Double total = 0.0;
		String result = "";

		while (i < values.length){
			String item = values[i];
			String iVals[] = item.split(" ");
			String code = iVals[0];
			String qty = iVals[1];
			Item itemObj = getItem(code, qty);
			result += itemObj.toString() + ", ";
			total = itemObj.getPrice();
			i++;
		}

		result = result.substring(0, result.length()-2);
		result = result + ", Total="+total;
		return result;
	}

	private Item getItem(String code, String qty){
		Item item = new Item(code, qty);
		return item;
	}
	
	
}
