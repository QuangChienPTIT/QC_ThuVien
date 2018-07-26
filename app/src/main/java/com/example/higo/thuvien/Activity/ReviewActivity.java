package com.example.higo.thuvien.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.higo.thuvien.DAO.AuthorDAO;
import com.example.higo.thuvien.DAO.BookDAO;
import com.example.higo.thuvien.DAO.QuyenSachDAO;
import com.example.higo.thuvien.DAO.RateBookDAO;
import com.example.higo.thuvien.DAO.SachMuonDAO;
import com.example.higo.thuvien.DAO.TheLoaiDAO;
import com.example.higo.thuvien.DAO.UserDAO;
import com.example.higo.thuvien.Model.Author;
import com.example.higo.thuvien.Model.Book;
import com.example.higo.thuvien.Model.QuyenSach;
import com.example.higo.thuvien.Model.RateBook;
import com.example.higo.thuvien.Model.SachMuon;
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
    private TextView txtQuyenSach;
    private TextView txtTacGia;
    private TextView txtTheLoai;
    private TextView txtSoLuong;
    private TextView txtDescription;
    private TextView txtRating;
    private ImageView imgReviewBook;
    private Button btnMuonSach;
    private RatingBar ratingBarReview;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Button btnComment;
    private Button btnYeuThich;
    private BookDAO bookDAO = new BookDAO();
    private SachMuonDAO sachMuonDAO = new SachMuonDAO();
    private QuyenSachDAO quyenSachDAO = new QuyenSachDAO();
    private RateBookDAO rateBookDAO = new RateBookDAO();
    private TheLoaiDAO theLoaiDAO = new TheLoaiDAO();
    private UserDAO userDAO = new UserDAO();

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
        txtQuyenSach = findViewById(R.id.txtQuyenSach);
        txtTacGia = findViewById(R.id.txtTacGia);
        txtTheLoai = findViewById(R.id.txtTheLoai);
        txtSoLuong = findViewById(R.id.txtSoLuong);
        txtDescription = findViewById(R.id.txtDescription);
        txtRating = findViewById(R.id.txtRating);
        imgReviewBook = findViewById(R.id.imgReviewBook);
        btnMuonSach = findViewById(R.id.btnMuonSach);
        btnComment = findViewById(R.id.btnComment);
        btnYeuThich = findViewById(R.id.btnTheoDoi);
        ratingBarReview = findViewById(R.id.ratingBarReview);
        collapsingToolbarLayout = findViewById(R.id.CollapsingToolbarLayout) ;
        kiemTraSach();

        bookDAO.soLuongSachConLai(idBook).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    QuyenSach quyenSach = data.getValue(QuyenSach.class);
                    if(quyenSach.getTinhTrang()!=3&&!quyenSach.isDangMuon()){
                        txtQuyenSach.setText(data.getKey());
                        break;

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addEvents() {
        btnMuonSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kiemTraTaiKhoan();
            }
        });

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReviewActivity.this, CommentActivity.class);
                intent.putExtra("idBook", idBook);
                startActivity(intent);
            }
        });

        ratingBarReview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                loadDialogRating();
                return false;
            }
        });
        btnYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookDAO.addSachYeuThich(idBook,user.getUid());
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
                    theLoaiDAO.searchTypeByidType(book.getIdType()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            txtTheLoai.setText(dataSnapshot.child("name").getValue().toString());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    Picasso.get().load(book.getImgURL().toString()).into(imgReviewBook);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        setTxtTheLoai(idBook);
        setTxtTacGia(idBook);
        setTxtSoLuong(idBook);
        setRatingBarReview(idBook);
    }

    private void setRatingBarReview(String idBook) {
        rateBookDAO.getRateByIdBook(idBook).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                float rateCount = 0;
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    rateCount = rateCount + data.child("rate").getValue(float.class);
                }
                float rate = rateCount / dataSnapshot.getChildrenCount();
                rate = (float)Math.round(rate*10)/10;
                ratingBarReview.setRating(rate);
                txtRating.setText(rate+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void setTxtSoLuong(String idBook) {
        bookDAO.soLuongSachConLai(idBook).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    int soLuong=0;
                    for(DataSnapshot data:dataSnapshot.getChildren()){
                        QuyenSach quyenSach = data.getValue(QuyenSach.class);
                        if(quyenSach.getTinhTrang()!=3&&!quyenSach.isDangMuon()){
                            soLuong++;
                        }
                        txtSoLuong.setText("Số Lượng : "+ soLuong);
                    }
                    if (soLuong==0) {
                        btnMuonSach.setText("ĐÃ HẾT SÁCH");
                        btnMuonSach.setEnabled(false);
                    }
                    else{
                        btnMuonSach.setText("MƯỢN SÁCH");
                        btnMuonSach.setEnabled(true);
                    }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

//    private void setTxtTheLoai(String idBook) {
//        new TheLoaiDAO().searchByIdBook(idBook).addValueEventListener(new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            txtTheLoai.setText("");
//            for(DataSnapshot data:dataSnapshot.getChildren()){
//                new TheLoaiDAO().searchTypeByidType(data.getKey()).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        String s = txtTheLoai.getText().toString();
//                        if(TextUtils.isEmpty(s))
//                            txtTheLoai.setText(dataSnapshot.getValue(TheLoai.class).getName());
//                        else
//                            txtTheLoai.setText(s + " , "+dataSnapshot.getValue(TheLoai.class).getName());
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//
//        }
//    });
//    }

    private void setTxtTacGia(String idBook) {
        new AuthorDAO().searchByIdBook(idBook).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            txtTacGia.setText("");
            for(DataSnapshot data:dataSnapshot.getChildren()){
                String s = txtTacGia.getText().toString();
                if(TextUtils.isEmpty(s))
                    txtTacGia.setText(data.getValue(Author.class).getName());
                else
                    txtTacGia.setText(s + " , "+data.getValue(Author.class).getName());
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
    }

    public void kiemTraSach() {

    }

    private void kiemTraTaiKhoan() {
        userDAO.isBlocked().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(Boolean.class)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReviewActivity.this);
                    builder.setTitle("Mượn sách");
                    builder.setMessage("Tài khoản đã bị khóa");
                    builder.setCancelable(false);
                    builder.setNegativeButton("Đã hiểu", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else
                    demSoSachSachMuon();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void demSoSachSachMuon() {
        sachMuonDAO.getListSachMuonByUser(user.getUid()).orderByChild("ngayTra").equalTo(null).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount()>2){
//                    btnMuonSach.setText("KHÔNG THỂ MƯỢN THÊM");
//                    btnMuonSach.setEnabled(false);
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReviewActivity.this);
                    builder.setTitle("Mượn sách");
                    builder.setMessage("Bạn chỉ có thể mượn 3 cuốn sách!!!");
                    builder.setCancelable(false);
                    builder.setNegativeButton("Đã hiểu", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else muonSach();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void muonSach() {
        if (user != null) {
            sachMuonDAO.dangKyMuon(txtQuyenSach.getText().toString());
            Toast.makeText(ReviewActivity.this, "Đăng ký mượn sách thành công", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(ReviewActivity.this, "Bạn phải đăng nhập để sử dụng chức năng này", Toast.LENGTH_LONG).show();
    }


    private void loadDialogRating() {
        final Dialog dialog = new Dialog(ReviewActivity.this);
        dialog.setContentView(R.layout.layout_rating);
        final EditText edRating = dialog.findViewById(R.id.edRating);
        Button btnRating = dialog.findViewById(R.id.btnRating);
        final TextView txtRating = dialog.findViewById(R.id.txtRating);
        final RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if(v>4) txtRating.setText("Rất hay");
                else if (v>3) txtRating.setText("Hay");
                else if (v>2) txtRating.setText("Tạm được");
                else if (v>1) txtRating.setText("Tệ");
                else if (v>0) txtRating.setText("Rất tệ");
            }
        });
        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RateBook rateBook = new RateBook();
                rateBook.setIdBook(idBook);
                rateBook.setIdUser(user.getUid());
                rateBook.setRate(ratingBar.getRating());
                rateBook.setContent(edRating.getText().toString());
                rateBookDAO.insert(rateBook);
                dialog.cancel();
            }
        });

        dialog.show();
    }


}
