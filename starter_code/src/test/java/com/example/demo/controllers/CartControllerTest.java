package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {
    private CartController cartController;
    private ItemRepository itemRepository = mock(ItemRepository.class);
    private UserRepository userRepo = mock(UserRepository.class);
    private CartRepository cartRepo = mock(CartRepository.class);
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Before
    public void setUp(){
        cartController = new CartController();
        TestUtils.injectObjects(cartController,"userRepository",userRepo);
        TestUtils.injectObjects(cartController,"cartRepository",cartRepo);
        TestUtils.injectObjects(cartController,"itemRepository",itemRepository);
    }

    @Test
    public void User_Controller_Add_To_Cart(){
        User user = new User();
        user.setUsername("Test123");
        user.setCart(new Cart());
        Item item = new Item();
        item.setDescription("Test Item");
        item.setId(1L);
        item.setName("Test Item");
        item.setPrice(BigDecimal.valueOf(10.25));
        when(userRepo.findByUsername("Test123")).thenReturn(user);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("Test123");
        modifyCartRequest.setItemId(1);
        modifyCartRequest.setQuantity(1);
        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);
        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatusCodeValue());
        Cart cart = response.getBody();
        Assert.assertNotNull(cart);
        Assert.assertEquals(1, cart.getItems().size());
    }


    @Test
    public void User_Controller_Remove_From_Cart(){
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
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("Test123");
        modifyCartRequest.setItemId(1);
        modifyCartRequest.setQuantity(1);
        ResponseEntity<Cart> response = cartController.removeFromcart(modifyCartRequest);
        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatusCodeValue());
        Cart cart2 = response.getBody();
        Assert.assertNotNull(cart2);
        Assert.assertEquals(0, cart2.getItems().size());
    }



}
