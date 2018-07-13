package com.example.higo.thuvien.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.higo.thuvien.DAO.AuthorDAO;
import com.example.higo.thuvien.DAO.BookDAO;
import com.example.higo.thuvien.DAO.SachMuonDAO;
import com.example.higo.thuvien.DAO.TheLoaiDAO;
import com.example.higo.thuvien.Model.Author;
import com.example.higo.thuvien.Model.Book;
import com.example.higo.thuvien.Model.QuyenSach;
import com.example.higo.thuvien.Model.TheLoai;
import com.example.higo.thuvien.Model.TheLoai;
import com.example.higo.thuvien.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReviewActivity extends AppCompatActivity {
    private String idBook;
    private TextView txtBookName;
    private TextView txtTacGia;
    private TextView txtTheLoai;
    private TextView txtSoLuong;
    private TextView txtDescription;
    private ImageView imgReviewBook;
    private Button btnMuonSach;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Button btnComment;
    private BookDAO bookDAO = new BookDAO();

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        addControls();
        getBook();
        addEvents();

    }
    private void addControls() {
        Intent intent = getIntent();
        idBook = intent.getStringExtra("idBook");
        txtBookName = findViewById(R.id.txtBookName);
        txtTacGia = findViewById(R.id.txtTacGia);
        txtTheLoai = findViewById(R.id.txtTheLoai);
        txtSoLuong = findViewById(R.id.txtSoLuong);
        txtDescription = findViewById(R.id.txtDescription);
        imgReviewBook = findViewById(R.id.imgReviewBook);
        btnMuonSach = findViewById(R.id.btnMuonSach);
        btnComment = findViewById(R.id.btnComment);
        collapsingToolbarLayout = findViewById(R.id.CollapsingToolbarLayout) ;
        kiemTraSach();//Kiểm tra sách đã được đăng kí mượn hay chưa
    }

    private void addEvents() {
        btnMuonSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user !=null){
                    
                    new SachMuonDAO().dangKyMuon(idBook);
                    Toast.makeText(ReviewActivity.this,"Đăng ký mượn sách thành công",Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(ReviewActivity.this,"Bạn phải đăng nhập để sử dụng chức năng này",Toast.LENGTH_LONG).show();
            }
        });

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReviewActivity.this,CommentActivity.class);
                intent.putExtra("idBook",idBook);
                startActivity(intent);
            }
        });
    }

    private void getBook() {
        final Query query = new BookDAO().searchByID(idBook);
        //Get Book
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    Book book = dataSnapshot.getValue(Book.class);
                    txtBookName.setText(book.getName());
                    collapsingToolbarLayout.setTitle(book.getName());
                    txtDescription.setText(book.getDescription());
                    //txtSoLuong.setText("Số lương : "+ book.getSlConLai());
                    //txtTheLoai.setText("Thể loại : " + book.getType());
                    Picasso.get().load(book.getImgURL().toString()).into(imgReviewBook);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //set TypeBook

        setTxtTheLoai(idBook);
        setTxtTacGia(idBook);
        setTxtSoLuong(idBook);
    }

    private void setTxtSoLuong(String idBook) {
        bookDAO.soLuongSachConLai(idBook).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    //txtSoLuong.setText("Số Lượng : "+dataSnapshot.getChildrenCount());
                    int soLuong=0;
                    for(DataSnapshot data:dataSnapshot.getChildren()){
                        QuyenSach quyenSach = data.getValue(QuyenSach.class);
                        if(quyenSach.getTinhTrang()!=3&&!quyenSach.isDangMuon()){
                            soLuong++;

                        }
                        txtSoLuong.setText("Số Lượng : "+ soLuong);
                    }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setTxtTheLoai(String idBook) {new TheLoaiDAO().searchByIdBook(idBook).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            txtTheLoai.setText("");
            for(DataSnapshot data:dataSnapshot.getChildren()){
                new TheLoaiDAO().searchTypeByidType(data.getKey()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String s = txtTheLoai.getText().toString();
                        if(TextUtils.isEmpty(s))
                            txtTheLoai.setText(dataSnapshot.getValue(TheLoai.class).getName());
                        else
                            txtTheLoai.setText(s + " , "+dataSnapshot.getValue(TheLoai.class).getName());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
    }

    private void setTxtTacGia(String idBook) {
        new AuthorDAO().searchByIdBook(idBook).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            txtTacGia.setText("");
            for(DataSnapshot data:dataSnapshot.getChildren()){
                new AuthorDAO().searchTacGiaById(data.getKey()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String s = txtTacGia.getText().toString();
                        if(TextUtils.isEmpty(s))
                            txtTacGia.setText(dataSnapshot.getValue(Author.class).getName());
                        else
                            txtTacGia.setText(s + " , "+dataSnapshot.getValue(Author.class).getName());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
    }

    private void kiemTraSach() {
        DatabaseReference rootSachMuon = FirebaseDatabase.getInstance().getReference().child("SachMuon");
//        rootSachMuon.child(user.getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.getChildrenCount()>2){
//                    btnMuonSach.setText("KHÔNG THỂ MUỌN THÊM");
//                    btnMuonSach.setEnabled(false);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        rootSachMuon.child(user.getUid()).orderByChild("idBook").equalTo(idBook)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            if (dataSnapshot.getValue()!=null){
                                btnMuonSach.setText("SÁCH ĐÃ MƯỢN");
                                btnMuonSach.setEnabled(false);
                            }

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        btnMuonSach.setText("MƯỢN SÁCH");
                        btnMuonSach.setEnabled(true);
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


    }
}
