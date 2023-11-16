package com.ssafy.enjoytrip.util.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
public class UtilController {

	@GetMapping("/validate-token")
	public ResponseEntity<Void> validJwt() {
		return ResponseEntity.ok(null);
	}
}
