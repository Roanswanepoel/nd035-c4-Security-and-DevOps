package com.example.demo.controllers;

import com.example.demo.Splunk.splunk;
import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    private UserController userController;
    private BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
    private UserRepository userRepo = mock(UserRepository.class);
    private CartRepository cartRepo = mock(CartRepository.class);
    private splunk splunkClient = mock(splunk.class);
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Before
    public void setUp(){
        userController = new UserController();
        TestUtils.injectObjects(userController,"userRepository",userRepo);
        TestUtils.injectObjects(userController,"cartRepository",cartRepo);
        TestUtils.injectObjects(userController,"bCryptPasswordEncoder",bCryptPasswordEncoder);
        TestUtils.injectObjects(userController,"splunkClient",splunkClient);
    }

    @Test
    public void User_Controller_Create_User_Test(){
        log.info("Started Test For Create User");
        when(bCryptPasswordEncoder.encode("Test123")).thenReturn("This Is Hashed");
        CreateUserRequest newUserRequest = new CreateUserRequest();
        newUserRequest.setPassword("Test123");
        newUserRequest.setConfirmPassword("Test123");
        newUserRequest.setUsername("Rtest");
        log.debug("Created New User Request : " + newUserRequest.getUsername());
        final ResponseEntity<User> response = userController.createUser(newUserRequest);
        log.warn("Assertions Start Here For Create User");
        Assert.assertNotNull(response);
        Assert.assertEquals(200,response.getStatusCodeValue());
        User user = response.getBody();
        Assert.assertNotNull(user);
        Assert.assertEquals(0,user.getId());
        Assert.assertEquals("Rtest",user.getUsername());
        Assert.assertEquals("This Is Hashed",user.getPassword());
        log.info("Create User Was Tested Successfully");
    }


    @Test
    public void User_Controller_Get_User_By_Id(){
        log.info("Started Test For Get User By Id");
        when(bCryptPasswordEncoder.encode("Test123")).thenReturn("This Is Hashed");
        CreateUserRequest newUserRequest = new CreateUserRequest();
        newUserRequest.setPassword("Test123");
        newUserRequest.setConfirmPassword("Test123");
        newUserRequest.setUsername("Rtest");
        final ResponseEntity<User> response = userController.createUser(newUserRequest);
        Assert.assertNotNull(response);
        Assert.assertEquals(200,response.getStatusCodeValue());
        User user = response.getBody();
        Assert.assertNotNull(user);
        Assert.assertEquals(0,user.getId());
        Assert.assertEquals("Rtest",user.getUsername());
        Assert.assertEquals("This Is Hashed",user.getPassword());

        when(userRepo.findById(0L)).thenReturn(Optional.of(user));
        ResponseEntity<User> response2 = null;
        try {
            response2 = userController.findById(0L);
        } catch (Exception e) {
            log.error("Failed To Find User By ID");
            throw new RuntimeException(e);
        }
        Assert.assertNotNull(response2);
        Assert.assertEquals(200,response2.getStatusCodeValue());
        User userFound = response2.getBody();
        Assert.assertNotNull(user);
        Assert.assertEquals(0,userFound.getId());
        Assert.assertEquals("Rtest",userFound.getUsername());
        Assert.assertEquals("This Is Hashed",userFound.getPassword());
        log.info("Find User By Id Was Tested Successfully");
    }


    @Test
    public void User_Controller_Get_User_By_Username() {
        log.info("Started Test For Get User By Username");
        when(bCryptPasswordEncoder.encode("Test123")).thenReturn("This Is Hashed");
        CreateUserRequest newUserRequest = new CreateUserRequest();
        newUserRequest.setPassword("Test123");
        newUserRequest.setConfirmPassword("Test123");
        newUserRequest.setUsername("Rtest");
        final ResponseEntity<User> response = userController.createUser(newUserRequest);
        Assert.assertNotNull(response);
        Assert.assertEquals(200,response.getStatusCodeValue());
        User user = response.getBody();
        Assert.assertNotNull(user);
        Assert.assertEquals(0,user.getId());
        Assert.assertEquals("Rtest",user.getUsername());
        Assert.assertEquals("This Is Hashed",user.getPassword());

        when(userRepo.findByUsername("Rtest")).thenReturn(user);
        ResponseEntity<User> response2 =  userController.findByUserName("Rtest");
        Assert.assertNotNull(response2);
        Assert.assertEquals(200,response2.getStatusCodeValue());
        User userFound = response2.getBody();
        Assert.assertNotNull(user);
        Assert.assertEquals(0,userFound.getId());
        Assert.assertEquals("Rtest",userFound.getUsername());
        Assert.assertEquals("This Is Hashed",userFound.getPassword());
        log.info("Find User By Username Was Tested Successfully");
    }

}
