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
    private Integer ID;
    private String addressee;
    private String phone;
    private String country;
    private String province;
    private String city;
    private String street;
    private String number;
    private String zipCode;
    private Boolean primaryAddress;
    private Integer idUser;

    public Address() {
    }

    public Address(Integer ID, String addressee, String phone, String country, String province, String city, String street, 
            String number, String zipCode, Boolean primaryAddress, Integer idUser) {
        this.ID = ID;
        this.addressee = addressee;
        this.phone = phone;
        this.country = country;
        this.province = province;
        this.city = city;
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
        this.primaryAddress = primaryAddress;
        this.idUser = idUser;
    }
    public Address(String addressee, String phone, String country, String province, String city, String street, 
            String number, String zipCode, Boolean primaryAddress, Integer idUser) {
        ID = null;
        this.addressee = addressee;
        this.phone = phone;
        this.country = country;
        this.province = province;
        this.city = city;
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
        this.primaryAddress = primaryAddress;
        this.idUser = idUser;
    }
    public String toShortSQL() {
        return "'" + addressee + "', '" + phone + "', '" + country + "', '" + province + "', '" + city + "', '" + street + "', '" + number
                + "', '" + zipCode + "', '" + primaryAddress + "', '" + idUser + "'";
    }
    public String toSQL() {
        return "'" + ID + "', " + addressee + "', '" + phone + "', '" + country + "', '" + province + "', '" + city + "', '" + street + "', '" + number
                + "', '" + zipCode + "', '" + primaryAddress + "', '" + idUser + "'";
    }
    public Integer getID() {
        return ID;
    }
    public String getAddressee() {
        return addressee;
    }
    public String getPhone() {
        return phone;
    }
    public String getCountry() {
        return country;
    }
    public String getProvince() {
        return province;
    }
    public String getCity() {
        return city;
    }
    public String getStreet() {
        return street;
    }
    public String getNumber() {
        return number;
    }
    public String getZipCode() {
        return zipCode;
    }
    public Boolean getPrimaryAddress() {
        return primaryAddress;
    }
    public Integer getIdUser() {
        return idUser;
    }    
    
    
    
}
