package com.example.dao;

import java.util.List;

import com.example.Item;

public interface ItemDao {

    Item getById(long id);

    List<Item> getAll();

    void insert(Item item);

    void update(long id, String name);

    void delete(Item item);

    Item findByName(String name);

    
}
