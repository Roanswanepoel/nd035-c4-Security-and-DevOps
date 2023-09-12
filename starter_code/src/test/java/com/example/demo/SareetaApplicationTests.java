package com.example.demo;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SareetaApplicationTests {


	@Before
	public void init(){
	}

	@Test
	public void contextLoads() {
	}

	/*@Test
	public void User_Controller_Create_User_Test(){
		CreateUserRequest newUserRequest = new CreateUserRequest();
		newUserRequest.setPassword("Test123");
		newUserRequest.setConfirmPassword("Test123");
		newUserRequest.setUsername("Rtest");
		/*ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
		modifyCartRequest.setUsername(newUserRequest.getUsername());
		modifyCartRequest.setItemId(1);
		modifyCartRequest.setQuantity(1);
		Cart cart = cartController.addTocart(modifyCartRequest).getBody();
		User user = userController.createUser(newUserRequest).getBody();
		Assert.assertEquals(newUserRequest.getUsername(),user.getUsername());
		Assert.assertEquals(bCryptPasswordEncoder.encode(newUserRequest.getPassword()),user.getPassword());
	}

	@Test
	public void User_Controller_Get_User_By_Username(){
		User_Controller_Create_User_Test();
		User user = new User();
		user.setUsername("Test123");
		Cart cart = new Cart();
		cartRepository.save(cart);
		user.setCart(cart);
		user.setPassword(bCryptPasswordEncoder.encode("Test123"));
		userRepository.save(user);
		List<User> userFound = userRepository.findAll();
		Assert.assertEquals(user.getUsername(),userFound);
	}*/

}
