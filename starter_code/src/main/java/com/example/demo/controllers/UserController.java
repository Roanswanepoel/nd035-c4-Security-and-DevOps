package com.example.demo.controllers;

import com.example.demo.Splunk.splunk;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private splunk splunkClient;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/id/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) throws Exception {
		log.info("GET Request To Find User By ID Using : " + id);
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()){
			log.debug("User Found By ID Using : " + id);
			try {
				splunkClient.sendLogsToSplunk("POST Request To Find User By ID Was Successful","Success");
			} catch (IOException e) {
				log.error("Error sending logs to Splunk: {}", e.getMessage());
			}
			return ResponseEntity.of(userRepository.findById(id));
		}else{
			log.debug("User Not Found With ID Provided : " + id);
			try {
				splunkClient.sendLogsToSplunk("POST Request To Find User By ID Was Unsuccessful","Failed");
			} catch (IOException e) {
				log.error("Error sending logs to Splunk: {}", e.getMessage());
			}
			throw new Exception("User Not Found");
		}
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> findByUserName(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		log.info("User Found By The Username Of " + username);
		try {
			splunkClient.sendLogsToSplunk("POST Request To Find User By Username Was Successful","Success");
		} catch (IOException e) {
			log.error("Error sending logs to Splunk: {}", e.getMessage());
		}
		return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
	}
	
	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
		try {
			splunkClient.sendLogsToSplunk("POST Request To Create User","Started");
		} catch (IOException e) {
			log.error("Error sending logs to Splunk: {}", e.getMessage());
		}
		User user = new User();
		user.setUsername(createUserRequest.getUsername());
		log.info("Username Set With " + createUserRequest.getUsername());
		Cart cart = new Cart();
		user.setCart(cart);
		log.info("Cart Set For User With " + cart);
		if(createUserRequest.getPassword().length()<7 ||
				!createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())){
			//System.out.println("Error - Either length is less than 7 or pass and conf pass do not match. Unable to create ",
			//		createUserRequest.getUsername());
			log.debug("User Password Failed To Pass Validation With The Following");
			try {
				splunkClient.sendLogsToSplunk("POST Request To Create User Failed","Failed");
			} catch (IOException e) {
				log.error("Error sending logs to Splunk: {}", e.getMessage());
			}
			return ResponseEntity.badRequest().build();
		}
		user.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));
		log.info("User Is Saved With The Following Data : " + user);
		userRepository.save(user);
		log.info("Cart Is Saved With " + cart);
		cartRepository.save(cart);
		try {
			splunkClient.sendLogsToSplunk("POST Request To Create User Was Successful","Success");
		} catch (IOException e) {
			log.error("Error sending logs to Splunk: {}", e.getMessage());
		}
		return ResponseEntity.ok(user);
	}
	
}
