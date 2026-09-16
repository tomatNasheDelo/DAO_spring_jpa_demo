package com.example;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.dao.ItemDao;

public class DatabaseService {

    @PersistenceContext (type = PersistenceContextType.EXTENDED)
    private EntityManager em;


    @Autowired
    private ItemDao itemDao;


    @Transactional
    public void init(){

        for(int i =0; i < 10; i++){
            String itemName = "Item " + (i + 1);
            Item item = new Item();
            item.setName(itemName);
            Bid bid1 = new Bid(new BigDecimal(1000.0), item);
            Bid bid2 = new Bid(new BigDecimal(1100.0), item);

            item.addBid(bid1);
            item.addBid(bid2);

            itemDao.insert(item);
        }

    }

    @Transactional
    public void clear(){

        em.createQuery("delete from Bid b").executeUpdate();
        em.createQuery("delete from Item i").executeUpdate();

    }
    
}
