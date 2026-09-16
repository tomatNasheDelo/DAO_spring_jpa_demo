package com.example.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.example.Bid;
import com.example.Item;





@Repository 
@Transactional 
public class ItemDaoImpl implements ItemDao{

    @PersistenceContext (type = PersistenceContextType.EXTENDED)
    private EntityManager em;


    public Item getById(long id){
        return em.find(Item.class, id);
    }


    public List<Item> getAll(){

        return em.createQuery("from Item", Item.class).getResultList();

    }


    public void insert(Item item){
        em.persist(item);

        for (Bid bid: item.getBids()){
            em.persist(bid);
        }
    }


    public void update(long id, String name){
        Item item = em.find(Item.class, id);
        item.setName(name);
        em.persist(item);
    }


    public void delete(Item item){
        for (Bid bid : item.getBids()){
            em.remove(bid);
        }

        em.remove(item);
    }


    public Item findByName(String name){
        return em.createQuery("from Item where name=:name", Item.class).setParameter("name", name).getSingleResult();
    }

    



    
}
