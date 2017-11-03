package com.example.gamze.loginformdeneme01;

import java.io.Serializable;

/**
 * Created by Gamze on 7/23/2017.
 */

public class Post implements Serializable {
    String menucontent;
    String cost;
    String takingtime;
    String sellerkey;
    String ownerphone;
    String ownerlocation;

    String ownertoken;
  //  String key;
    String buyerid;
    String status;
    String addingdate;

//    public Post(String icerik, String fiyat, String date, String owner, String ownerPhone, String ownerLocation,int postId) {
//        this.icerik = icerik;
//        this.fiyat = fiyat;
//        this.date = date;
//        this.owner = owner;
//        this.ownerPhone = ownerPhone;
//        this.ownerLocation = ownerLocation;
//        this.postId = postId;
//    }
    public Post(){

    }
    public String getOwnertoken() {
        return ownertoken;
    }

    public void setOwnertoken(String ownertoken) {
        this.ownertoken = ownertoken;
    }

    public String getMenucontent() {
        return menucontent;
    }

    public void setMenucontent(String menucontent) {
        this.menucontent = menucontent;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getTakingtime() {
        return takingtime;
    }

    public void setTakingtime(String takingtime) {
        this.takingtime = takingtime;
    }

    public String getSellerkey() {
        return sellerkey;
    }

    public void setSellerkey(String sellerkey) {
        this.sellerkey = sellerkey;
    }

    public String getOwnerphone() {
        return ownerphone;
    }

    public void setOwnerphone(String ownerphone) {
        this.ownerphone = ownerphone;
    }

    public String getOwnerlocation() {
        return ownerlocation;
    }

    public void setOwnerlocation(String ownerlocation) {
        this.ownerlocation = ownerlocation;
    }

//    public String getKey() {
//        return key;
//    }
//
//    public void setKey(String key) {
//        this.key = key;
//    }

    public String getBuyerid() {
        return buyerid;
    }

    public void setBuyerid(String buyerid) {
        this.buyerid = buyerid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddingdate() {
        return addingdate;
    }

    public void setAddingdate(String addingdate) {
        this.addingdate = addingdate;
    }
}
