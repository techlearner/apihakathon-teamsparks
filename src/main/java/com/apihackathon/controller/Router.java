package com.apihackathon.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apihackathon.model.Item;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

@Controller
public class Router {
	
	private static Map<String, Double> walletMap = new HashMap<>();
	
	static {
		walletMap.put("07299952009", new Double(500));
		walletMap.put("09884940292", new Double(2000));
		walletMap.put("09742381630", new Double(700));
		walletMap.put("07259926494", new Double(700));
	}

	@RequestMapping("/get_menu.json")
	public @ResponseBody String getMenu() {
		return "Hello World!";
	}
	
	@RequestMapping("/bigo.json")
	public @ResponseBody String bigo(HttpServletRequest request) {

		String bodyEn = request.getParameter("Body");
		String mobileNo = request.getParameter("From");
		if (mobileNo == null)
			mobileNo = "9884940292";
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
			response = "Your order has been placed."
					+ " Your wallet balance is Rs."+walletAmount+". Thank you.";
			sendEmail(response, "Order Detail From BigO", "senthil@m2p.in", "senthilkumar");
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
	
	private void sendEmail(String body, String subject, String toMail, String toName) {
		/*RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String params = "to="+toMail+"&toname="+toName+"text="+body+"from=ssenthilkumar.cs@gmail.com&api_user=ssenthilkumar.cs&api_key=Open@123";
        String url = "https://api.sendgrid.com/api/mail.send.json";
               
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> entity = new HttpEntity<String>("POST Data="+params,headers);
        ResponseEntity<String> response =  restTemplate.postForEntity(url, entity,String.class,
                new Object[]{url});
        response.getBody();*/
		
	    SendGrid sendgrid = new SendGrid("ssenthilkumar.cs", "Open@123");
	    SendGrid.Email email = new SendGrid.Email();
	    email.addTo(toMail);
	    email.setFrom("ssenthilkumar.cs@gmail.com");
	    email.setSubject("Order Detail");
	    email.setText(body);
	    try {
			SendGrid.Response response = sendgrid.send(email);
			System.out.println(response);
		} catch (SendGridException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
