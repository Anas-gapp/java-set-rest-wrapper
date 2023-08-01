package org.springframework.samples.petclinic.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.example.request.SetElementRequest;

import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;

@RestController
@RequestMapping("/petclinic")
public class PetClinicController<T> {

	private static final Logger logger = LoggerFactory.getLogger(PetClinicController.class);

	@GetMapping("/hello")
	public String hello() {
		logger.info("This is an INFO level message.");
		return "Hello, Pet Clinic!";
	}

	@GetMapping("/greet")
	public String greet() {
		return "Welcome to Pet Clinic!";
	}

	// Set Implementation

	private Map<String, Set<T>> setMap = new HashMap<>();

	@PostMapping("/{setName}/addElement")
	public ResponseEntity<String> addElement(@PathVariable String setName, @RequestBody SetElementRequest<T> request) {
		Set<T> set = setMap.computeIfAbsent(setName, k -> new HashSet<>());
		T elementToAdd = request.getElement();
		if (set.add(elementToAdd)) {
			return new ResponseEntity<>("Element added to the set '" + setName + "' successfully.", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Element already exists in the set '" + setName + "'.", HttpStatus.OK);
		}
	}

	@DeleteMapping("/{setName}/removeElement")
	public ResponseEntity<String> removeElement(@PathVariable String setName,
			@RequestBody SetElementRequest<T> request) {
		Set<T> set = setMap.get(setName);
		if (set == null) {
			return new ResponseEntity<>("Set '" + setName + "' not found.", HttpStatus.NOT_FOUND);
		}

		T elementToRemove = request.getElement();
		if (set.remove(elementToRemove)) {
			return new ResponseEntity<>("Element removed from the set '" + setName + "' successfully.", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Element not found in the set '" + setName + "'.", HttpStatus.OK);
		}
	}

	@GetMapping("/{setName}/checkElement")
	public ResponseEntity<String> checkElement(@PathVariable String setName,
			@RequestBody SetElementRequest<T> request) {
		Set<T> set = setMap.get(setName);
		if (set == null) {
			return new ResponseEntity<>("Set '" + setName + "' not found.", HttpStatus.NOT_FOUND);
		}

		T elementToCheck = request.getElement();
		if (set.contains(elementToCheck)) {
			return new ResponseEntity<>("Element exists in the set '" + setName + "'.", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Element does not exist in the set '" + setName + "'.", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{setName}/getAllElements")
	public ResponseEntity<Set<T>> getAllElements(@PathVariable String setName) {
		Set<T> set = setMap.get(setName);
		if (set != null) {
			return new ResponseEntity<>(set, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{setName}/clearSet")
	public ResponseEntity<String> clearSet(@PathVariable String setName) {
		Set<T> set = setMap.get(setName);
		if (set != null) {
			set.clear();
			return new ResponseEntity<>("Set '" + setName + "' cleared successfully.", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Set '" + setName + "' not found.", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getAllSets")
	public ResponseEntity<Map<String, Set<T>>> getAllSets() {
		if (!setMap.isEmpty()) {
			return new ResponseEntity<>(setMap, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

}
