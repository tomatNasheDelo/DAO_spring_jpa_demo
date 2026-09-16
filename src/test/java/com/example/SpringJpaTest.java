package com.example;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.NoResultException;

import org.junit.jupiter.api.AfterEach;
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


    @Test 
    public void testDeleteItem(){

        itemDao.delete(itemDao.findByName("Item 2"));
        assertThrows(NoResultException.class, () -> itemDao.findByName("Item 2"));

    }

    @Test
    public void testUpdateItem(){
        Item item1 = itemDao.findByName("Item 1");
        itemDao.update(item1.getId(), "Item 1_updated");
        assertEquals("Item 1_updated", itemDao.getById(item1.getId()).getName());
    }

    // 
    @Test
    public void testInsertBid(){

        Item item3 = itemDao.findByName("Item 3");
        Bid newBid = new Bid(new BigDecimal("2000.00"), item3);
        bidDao.insert(newBid);
        assertAll( 
                 () -> assertEquals(new BigDecimal("2000.00"), bidDao.getById(newBid.getId()).getAmount()),
                 () -> assertEquals(21, bidDao.getAll().size()));
        

    }


    @Test 
    public void testUpdateBid(){

        Bid bid = bidDao.findByAmount("1000.00").get(0);
        bidDao.update(bid.getId(), "1200.00");
        assertEquals(new BigDecimal("1200.00"), bidDao.getById(bid.getId()).getAmount());

    }

    @Test 
    public void testDeleteBid(){

        bidDao.delete(bidDao.findByAmount("1000.00").get(0));
        assertEquals(19, bidDao.getAll().size());

    }

    @AfterEach
    public void dropDown(){

        databaseService.clear();

    }
    
}
