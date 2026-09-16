package com.example.dao;

import java.util.List;

import com.example.Bid;



public interface BidDao {

    Bid getByid(long id);

    List<Bid> getAll();

    void insert(Bid bid);

    void update(long id, String amount);

    void delete(Bid bid);

    List<Bid>  findByAmount(String amount);
    
}
