/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources.entities;

/**
 *
 * @author Chris
 */
public class Address {
    private String addressee;
    private String phone;
    private String country;
    private String province;
    private String city;
    private String street;
    private String number;
    private String zipCode;

    public Address() {
    }

    public Address(String addressee, String phone, String country, String province, String city, String street, String number, String zipCode) {
        this.addressee = addressee;
        this.phone = phone;
        this.country = country;
        this.province = province;
        this.city = city;
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
    }
    public String toSQL() {
        return "'" + addressee + "', '" + phone + "', '" + country + "', '" + province + "', '" + city + "', '" + street + "', '" + number
                + "', '" + zipCode + "'";
    }
    
    
    
}
