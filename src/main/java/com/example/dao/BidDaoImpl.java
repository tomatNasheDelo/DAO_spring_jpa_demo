package com.example.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.example.Bid;


@Repository 
@Transactional 
public class BidDaoImpl implements BidDao {


    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    public Bid getByid(long id){
        return em.find(Bid.class, id);
    }


    public List<Bid> getAll(){

        return em.createQuery("from Bid", Bid.class).getResultList();

    }

    public void insert(Bid bid){
        em.persist(bid);
    }


    public void update(long id, String amount){

        Bid bid = em.find(Bid.class, id);
        bid.setAmount(new BigDecimal(amount));
        em.persist(bid);
    }


    public void delete(Bid bid){
        em.remove(bid);

    }

    public List<Bid> findByAmount(String amount){

        return em.createQuery("from Bid where amount=:amount", Bid.class).setParameter("amount", new BigDecimal(amount)).getResultList();
    }
    
}
