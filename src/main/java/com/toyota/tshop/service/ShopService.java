package com.toyota.tshop.service;

import com.toyota.tshop.dao.ShopDAO;
import com.toyota.tshop.entity.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
public class ShopService {

    @Autowired
    ShopDAO shopDAO;

    public void setShopDAO(ShopDAO shopDAO) {
        this.shopDAO = shopDAO;
    }

    @Transactional
    public void persistShop(Shop cse){
        shopDAO.save(cse);
    }

    @Transactional
    public void deleteShop(int id){
        shopDAO.delete(shopDAO.findByID(id));
    }

    @Transactional
    public Shop updateShop(Shop c){
        Shop temp = shopDAO.findByID(c.getId());
        temp.setName(c.getName());
        temp.setCode(c.getCode());
        temp.setUpdatedBy(c.getName());
        temp.setUpdatedDate(new Date());
        shopDAO.save(temp);
        return temp;
    }

    @Transactional
    public void mergeShop(Shop cse){
        shopDAO.merge(cse);
    }

    @Transactional
    public Shop getByID(int id){
        return shopDAO.findByID(id);
    }

    @Transactional
    public List<Shop> getAll(){
        return shopDAO.findAll();
    }

    @Transactional
    public List<Shop> getOnlines() {
        return shopDAO.findShopsOnline();
    }
}
