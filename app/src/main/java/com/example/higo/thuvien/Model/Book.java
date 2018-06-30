package com.example.higo.thuvien.Model;

public class Book {
    private String bookName;
    private String imgBookURL;
    private String idBook;
    private String idAuthor;
    private String idType;
    private String description;
    private int slTong;
    private int slConLai;
    public Book() {

    }

    public Book(String bookName, String imgBookURL, String idBook, String idAuthor, String idType, String description, int slTong, int slConLai) {
        this.bookName = bookName;
        this.imgBookURL = imgBookURL;
        this.idBook = idBook;
        this.idAuthor = idAuthor;
        this.idType = idType;
        this.description = description;
        this.slTong = slTong;
        this.slConLai = slConLai;
    }

    public Book(String bookName, String imgURL) {
        this.bookName = bookName;
        this.imgBookURL = imgURL;
    }

    public String getImgBookURL() {
        return imgBookURL;
    }

    public void setImgBookURL(String imgBookURL) {
        this.imgBookURL = imgBookURL;
    }

    public String getIdBook() {
        return idBook;
    }

    public void setIdBook(String idBook) {
        this.idBook = idBook;
    }

    public String getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(String idAuthor) {
        this.idAuthor = idAuthor;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
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

    public String getImgURL() {
        return imgBookURL;
    }

    public void setImgURL(String imgURL) {
        this.imgBookURL = imgURL;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }




}
