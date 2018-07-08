package com.example.higo.thuvien.DAO;

import com.example.higo.thuvien.Model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class UserDAO {
    private DatabaseReference rootUser = FirebaseDatabase.getInstance().getReference().child("User");
    public Query searchByID(String idUser){
        Query query = rootUser.child(idUser);
        return query;
    }

    public void insertUser(User user){
        rootUser.setValue(user);
    }

    public void updateUser(User user){
        rootUser.setValue(user);
    }
}
