package com.anton.api.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anton.api.dao.CompanyRepository;
import com.anton.api.dao.CustomerRepository;
import com.anton.api.models.Company;
import com.anton.api.models.Customer;

@Controller("/login")
public class LoginController {
	
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
		System.out.println(credentials.isEmpty() +"-----------------------");
		System.out.println("------@@@---------//---------@@@-----");
		
		JSONObject jsonAnswer = new JSONObject();
		jsonAnswer.put("isLoggedIn", false);
		jsonAnswer.put("type", "");
		String name = credentials.getString("name");
		String password = credentials.getString("password");
		String type = credentials.getString("type");
		
		System.out.println("-----------------------");
		System.out.println(name);
		System.out.println(password);
		System.out.println(type);
		System.out.println("-----------------------");
		
		switch(type) {
			case "COMPANY":
				if(name.equals(getCompany(name).getName())&&password.equals(getCompany(name).getPassword())) {
					jsonAnswer.put("isLoggedIn", true);
					jsonAnswer.put("type", "COMPANY");
				}break;
			case "CUSTOMER":	
				if(name.equals(getCustomer(name).getName())&&password.equals(getCustomer(name).getPassword())) {
					jsonAnswer.put("isLoggedIn", true);
					jsonAnswer.put("type", "CUSTOMER");
				}break;
			case "ADMIN":	
				if(name.equals("admin")&&password.equals("administrator")) {
					jsonAnswer.put("isLoggedIn", true);
					jsonAnswer.put("type", "ADMIN");
				}break;
		}return jsonAnswer;
	}
}
