package com.example.higo.thuvien.Model;

public class Book {
    private String name;
    private String imgURL;
    private String id;
    private String idAuthor;
    private String idType;
    private String description;
    private int slTong;
    private int slConLai;
    public Book() {

    }

    public Book(String name, String imgURL, String id, String idAuthor, String type, String description, int slTong, int slConLai) {
        this.name = name;
        this.imgURL = imgURL;
        this.id = id;
        this.idAuthor = idAuthor;
        this.description = description;
        this.slTong = slTong;
        this.slConLai = slConLai;
    }

    public Book(String name, String imgURL) {
        this.name = name;
        this.imgURL = imgURL;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(String idAuthor) {
        this.idAuthor = idAuthor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSlTong() {
        return slTong;
    }

    public void setSlTong(int slTong) {
        this.slTong = slTong;
    }

    public int getSlConLai() {
        return slConLai;
    }

    public void setSlConLai(int slConLai) {
        this.slConLai = slConLai;
    }
}
