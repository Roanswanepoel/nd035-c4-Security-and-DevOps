package com.example.demo.controllers;

import java.io.IOException;
import java.util.List;

import com.example.demo.Splunk.splunk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private splunk splunkClient;
	
	@Autowired
	private OrderRepository orderRepository;
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	
	@PostMapping("/submit/{username}")
	public ResponseEntity<UserOrder> submit(@PathVariable String username) {
		log.debug("POST Request To Submit Order Using Username : " + username);
		User user = userRepository.findByUsername(username);
		if(user == null) {
			log.debug("POST Request FAILED!!! To Submit Order Using Username : " + username);
			try {
				splunkClient.sendLogsToSplunk("POST Request To Submit Order By Username Was Unsuccessful","Failed");
			} catch (IOException e) {
				log.error("Error sending logs to Splunk: {}", e.getMessage());
			}
			return ResponseEntity.notFound().build();
		}
		UserOrder order = UserOrder.createFromCart(user.getCart());
		orderRepository.save(order);
		log.info("POST Request SUCCESS!!!! To Submit Order Using Username : " + username);
		try {
			splunkClient.sendLogsToSplunk("POST Request To Submit Order By Username Was Successful","Success");
		} catch (IOException e) {
			log.error("Error sending logs to Splunk: {}", e.getMessage());
		}
		return ResponseEntity.ok(order);
	}
	
	@GetMapping("/history/{username}")
	public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) {
		log.debug("POST Request To View Order History Using Username : " + username);
		User user = userRepository.findByUsername(username);
		if(user == null) {
			log.debug("POST Request FAILED!!!!! To View Order History Using Username : " + username);
			try {
				splunkClient.sendLogsToSplunk("POST Request To View Order History By Username Was Unsuccessful","Failed");
			} catch (IOException e) {
				log.error("Error sending logs to Splunk: {}", e.getMessage());
			}
			return ResponseEntity.notFound().build();
		}
		log.info("POST Request SUCCESS!!!! To View Order History Using Username : " + username);
		try {
			splunkClient.sendLogsToSplunk("POST Request To View Order History By Username Was Successful","Success");
		} catch (IOException e) {
			log.error("Error sending logs to Splunk: {}", e.getMessage());
		}
		return ResponseEntity.ok(orderRepository.findByUser(user));
	}
}
