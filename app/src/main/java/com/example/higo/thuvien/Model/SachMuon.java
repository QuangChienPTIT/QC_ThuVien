package com.example.higo.thuvien.Model;

import java.util.Date;

public class SachMuon {
    private String idQuyenSach;
    private String idUser;
    private String ngayMuon;
    private String ngayDangKy;
    private String ngayTra;
    private String id;
    public SachMuon() {
    }

    public String getNgayDangKy() {
        return ngayDangKy;
    }

    public void setNgayDangKy(String ngayDangKy) {
        this.ngayDangKy = ngayDangKy;
    }

    public String getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(String ngayTra) {
        this.ngayTra = ngayTra;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdQuyenSach() {
        return idQuyenSach;
    }

    public void setIdQuyenSach(String idQuyenSach) {
        this.idQuyenSach = idQuyenSach;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(String ngayMuon) {
        this.ngayMuon = ngayMuon;
    }
}
