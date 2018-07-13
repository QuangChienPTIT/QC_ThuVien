package com.example.higo.thuvien.Model;

public class QuyenSach {
    private String id;
    private String idBook;
    private int gia;
    private boolean dangMuon=false;
    private int tinhTrang=1;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdBook() {
        return idBook;
    }

    public void setIdBook(String idBook) {
        this.idBook = idBook;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public boolean isDangMuon() {
        return dangMuon;
    }

    public void setDangMuon(boolean dangMuon) {
        this.dangMuon = dangMuon;
    }

    public int getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public QuyenSach() {

    }
}
