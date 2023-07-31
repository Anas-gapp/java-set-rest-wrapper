package org.springframework.samples.petclinic.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/petclinic")
public class PetClinicController {

	private static final Logger logger = LoggerFactory.getLogger(PetClinicController.class);

	@GetMapping("/hello")
	public String hello() {
		return "Hello, Pet Clinic!";
	}

	@GetMapping("/greet")
	public String greet() {
		return "Welcome to Pet Clinic!";
	}

	// Set Implementation

	private Set<Object> uniqueSet = new HashSet<>();

	@PostMapping("/addElement")
	public ResponseEntity<String> addElement(@RequestBody Object element) {
		if (uniqueSet.add(element)) {
			return new ResponseEntity<>("Element added successfully.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Element already exists.", HttpStatus.OK);
		}
	}

	@DeleteMapping("/removeElement")
	public ResponseEntity<String> removeElement(@RequestBody Object element) {
		if (uniqueSet.remove(element)) {
			return new ResponseEntity<>("Element removed successfully.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Element not found.", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/checkElement")
	public ResponseEntity<String> checkElement(@RequestBody Object element) {
		if (uniqueSet.contains(element)) {
			return new ResponseEntity<>("Element exists in the Set.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Element does not exist in the Set.", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getAllElements")
	public ResponseEntity<Set<Object>> getAllElements() {
		return new ResponseEntity<>(uniqueSet, HttpStatus.OK);
	}

	@DeleteMapping("/clearSet")
	public ResponseEntity<String> clearSet() {
		uniqueSet.clear();
		return new ResponseEntity<>("Set cleared successfully.", HttpStatus.OK);
	}

}