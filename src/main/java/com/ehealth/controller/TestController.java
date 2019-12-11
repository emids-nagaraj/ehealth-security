package com.ehealth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/test")
public class TestController {
	@RequestMapping(value = "/india", method = RequestMethod.GET)
	public ResponseEntity<?> india() {
		System.out.println("hit..");
		return null;
	}
}