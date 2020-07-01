/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources.entities;

import java.util.List;

/**
 *
 * @author Chris
 */
public class Product {
    private int id;
    private String name;
    private String description;
    private int price;
    private int idShop;
    private byte[] mainImage;
    private List<byte[]> images;

    public Product() {
    }
    
    public Product(int id, String name, int price, int idShop, byte[] mainImage) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.idShop = idShop;
        this.mainImage = mainImage;
    }

    public Product(int id, String name, String description, int price, int idShop, List<byte[]> images) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.idShop = idShop;
        this.images = images;
    }
    
    
    
    
}
