package com.apihackathon.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.apihackathon.model.Item;

@Controller
public class Router {
	
	private static Map<String, Double> walletMap = new HashMap<>();
	
	static {
		walletMap.put("7299952009", new Double(500));
		walletMap.put("9884940292", new Double(2000));
		walletMap.put("9742381630", new Double(700));
	}

	@RequestMapping("/get_menu.json")
	public @ResponseBody String getMenu() {
		return "Hello World!";
	}
	
	@RequestMapping("/bigo.json")
	public @ResponseBody String bigo(HttpServletRequest request) {

		String bodyEn = request.getParameter("Body");
		String mobileNo = request.getParameter("From");
		bodyEn = bodyEn.substring(bodyEn.indexOf(" "));
		String body = new String(Base64.decodeBase64(bodyEn));
//		String body = bodyEn;
		String[] values = body.split(",");
		String type = values[0];
		String returnVal = "processed";
		returnVal = processOrder(values, mobileNo);

		
		return returnVal;
	}

	private String confirmOrder(String customerMobileNo, Double billAmount) {
		return processPayment(customerMobileNo, billAmount);
	}

	private String processPayment(String customerMobileNo, Double billAmount) {
		Double walletAmount = walletMap.get(customerMobileNo);
		String response = "";
		if (walletAmount != null) {
			walletAmount -= billAmount;
			walletMap.put(customerMobileNo, walletAmount);
			response = "Your order has been placed and processed successfully."
					+ " Your current wallet balance is Rs."+walletAmount+". Thank you.";
		} else {
			System.out.println("Not a registered customer");
			response = "Sorry you are not a registered customer. Please register.";
		}
		return response;
	}

	private String processOrder(String[] values, String mobileNo) {
		String store = values[1];
		int i = 2;
		Double total = 0.0;
		String result = "";

		while (i < values.length){
			String item = values[i];
			item = item.trim();
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

		return processPayment(mobileNo, total);
	}

	private Item getItem(String code, String qty){
		Item item = new Item(code, qty);
		return item;
	}
	
/*	
	private void sendSMS() {
		String url = "https://~exotel_sid~:~exotel_token~@twilix.exotel.in/v1/Accounts/~exotel_sid~/Sms/send";
		RestTemplate restTemplate = new RestTemplate();
		HttpRequestE
		restTemplate.postForEntity(url, request, responseType, null);
	}
	*/
	
}
