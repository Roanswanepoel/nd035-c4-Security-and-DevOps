package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {
    private OrderController orderController;
    private UserRepository userRepo = mock(UserRepository.class);
    private OrderRepository orderRepo = mock(OrderRepository.class);
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Before
    public void setUp(){
        orderController = new OrderController();
        TestUtils.injectObjects(orderController,"userRepository",userRepo);
        TestUtils.injectObjects(orderController,"orderRepository",orderRepo);
    }

    @Test
    public void User_Controller_Submit_Order(){
        log.info("Started Test For Submit Order");
        User user = new User();
        user.setUsername("Test123");
        Cart cart = new Cart();
        Item item = new Item();
        item.setDescription("Test Item");
        item.setId(1L);
        item.setName("Test Item");
        item.setPrice(BigDecimal.valueOf(10.25));
        List<Item> items = new ArrayList<>();
        items.add(item);
        cart.setItems(items);
        user.setCart(cart);
        when(userRepo.findByUsername("Test123")).thenReturn(user);
        ResponseEntity<UserOrder> response =  orderController.submit(user.getUsername());
        Assert.assertNotNull(response);
        Assert.assertEquals(200,response.getStatusCodeValue());
        UserOrder userOrder = response.getBody();
        Assert.assertNotNull(userOrder);
    }

    @Test
    public void User_Controller_View_Orders(){
        User user = new User();
        user.setUsername("Test123");
        Cart cart = new Cart();
        Item item = new Item();
        item.setDescription("Test Item");
        item.setId(1L);
        item.setName("Test Item");
        item.setPrice(BigDecimal.valueOf(10.25));
        List<Item> items = new ArrayList<>();
        items.add(item);
        cart.setItems(items);
        user.setCart(cart);
        when(userRepo.findByUsername("Test123")).thenReturn(user);
        ResponseEntity<List<UserOrder>> response =  orderController.getOrdersForUser(user.getUsername());
        Assert.assertNotNull(response);
        Assert.assertEquals(200,response.getStatusCodeValue());
        List<UserOrder> userOrder = response.getBody();
        Assert.assertNotNull(userOrder);
    }

}
