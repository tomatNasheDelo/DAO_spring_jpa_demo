package com.example;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.configuration.SpringConfiguration;
import com.example.dao.BidDao;
import com.example.dao.ItemDao;

@ExtendWith (SpringExtension.class)
//@ContextConfiguration(classes = {SpringConfiguration.class})
@ContextConfiguration("classpath:application-context.xml")
public class SpringJpaTest {



    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private BidDao bidDao;


    @BeforeEach 
    public void setUp(){
        databaseService.init();
    }

    @Test 
    public void testInsertItems(){
        List<Item> itemsList = itemDao.getAll();
        List<Bid> bidsList = bidDao.getAll();
        assertAll( 
             () -> assertNotNull(itemsList),
             () -> assertEquals(10, itemsList.size()),
             () -> assertNotNull(itemDao.findByName("Item 1")),
             () -> assertNotNull(bidsList),
             () -> assertEquals(20, bidsList.size()),
             () -> assertEquals(10, bidDao.findByAmount("1000.00").size())
        );
    }
    
}
