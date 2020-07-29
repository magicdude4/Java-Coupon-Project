package com.anton.api.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anton.api.dao.CompanyRepository;
import com.anton.api.dao.CustomerRepository;
import com.anton.api.models.Company;
import com.anton.api.models.Customer;

@Controller("/login")
public class LoginController {
	
	//private JSONObject credentials;
	
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private CustomerRepository customerRepository;
	
	public Customer getCustomer(String name) {
		return customerRepository.findByName(name);
	}

	public Company getCompany(String name) {
		return companyRepository.findByName(name);
	}
	
	@GetMapping(path = "/login", consumes = { "application/json" }, produces = { "application/json" })
	@ResponseBody
	public JSONObject login(JSONObject credentials) throws JSONException {
		
		JSONObject jsonAnswer = new JSONObject();
		jsonAnswer.put("isLoggedIn", false);
		jsonAnswer.put("type", "");
		String name = credentials.get("name").toString();
		String password = credentials.get("password").toString();
		String type = credentials.get("type").toString();
		
		switch(type) {
			case "COMPANY":
				if(name.equals(getCompany(name).getName())&&password.equals(getCompany(password).getPassword())) {
					jsonAnswer.put("isLoggedIn", "true");
					jsonAnswer.put("type", "COMPANY");
				}break;
			case "CUSTOMER":	
				if(name.equals(getCustomer(name).getName())&&password.equals(getCustomer(password).getPassword())) {
					jsonAnswer.put("isLoggedIn", "true");
					jsonAnswer.put("type", "CUSTOMER");
				}break;
			case "ADMIN":	
				if(name.equals("admin")&&password.equals("administrator")) {
					jsonAnswer.put("isLoggedIn", "true");
					jsonAnswer.put("type", "ADMIN");
				}break;
		}return jsonAnswer;
	}
}
