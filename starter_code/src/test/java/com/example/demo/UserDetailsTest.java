package com.example.demo;

import com.example.demo.controllers.CartController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.security.UserDetailsServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserDetailsTest {

    private UserDetailsServiceImpl userDetailsService;
    private UserRepository userRepo  = mock(UserRepository.class);

    @Before
    public void setUp(){
        userDetailsService = new UserDetailsServiceImpl();
        TestUtils.injectObjects(userDetailsService,"userRepository",userRepo);
    }

    @Test
    public void UserDetailsTest(){
        User user = new User();
        user.setUsername("Test");
        Cart cart = new Cart();
        user.setCart(cart);
        user.setPassword("Hashed");
        userRepo.save(user);
        when(userRepo.findByUsername("Test")).thenReturn(user);
        UserDetails userDetails =  userDetailsService.loadUserByUsername(user.getUsername());
        Assert.assertNotNull(userDetails);
    }
}
