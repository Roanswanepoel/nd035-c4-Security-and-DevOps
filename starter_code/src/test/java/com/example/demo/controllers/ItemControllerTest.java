package com.example.demo.controllers;
import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
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

public class ItemControllerTest {

    private ItemController itemController;
    private ItemRepository itemRepository = mock(ItemRepository.class);
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Before
    public void setUp(){
        itemController = new ItemController();
        TestUtils.injectObjects(itemController,"itemRepository",itemRepository);
    }

    @Test
    public void User_Controller_Get_Item_By_Id() {
        Item item = new Item();
        item.setDescription("Test Item");
        item.setId(1L);
        item.setName("Test Item");
        item.setPrice(BigDecimal.valueOf(10.25));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        final ResponseEntity<Item> response = itemController.getItemById(1L);
        Assert.assertNotNull(response);
        Assert.assertEquals(200,response.getStatusCodeValue());
        Item item2 = response.getBody();
        Assert.assertNotNull(item2);
        Assert.assertEquals(item.getId(),item2.getId());
        Assert.assertEquals(item.getPrice(),item2.getPrice());
        Assert.assertEquals(item.getDescription(),item.getDescription());
        Assert.assertEquals(item.getName(),item.getName());

    }

    @Test
    public void User_Controller_Get_Item_By_Name() {
        Item item = new Item();
        item.setDescription("Test Item");
        item.setId(1L);
        item.setName("Test Item");
        item.setPrice(BigDecimal.valueOf(10.25));
        List<Item> optionalList = new ArrayList<>();;
        optionalList.add(item);
        when(itemRepository.findByName("Test Item")).thenReturn(optionalList);
        final ResponseEntity<List<Item>> response = itemController.getItemsByName("Test Item");
        Assert.assertNotNull(response);
        Assert.assertEquals(200,response.getStatusCodeValue());
    }

    @Test
    public void User_Controller_Get_All_Items() {
        Item item = new Item();
        item.setDescription("Test Item");
        item.setId(1L);
        item.setName("Test Item");
        item.setPrice(BigDecimal.valueOf(10.25));
        List<Item> optionalList = new ArrayList<>();;
        optionalList.add(item);
        when(itemRepository.findAll()).thenReturn(optionalList);
        final ResponseEntity<List<Item>> response = itemController.getItems();
        Assert.assertNotNull(response);
        Assert.assertEquals(200,response.getStatusCodeValue());
    }

}
