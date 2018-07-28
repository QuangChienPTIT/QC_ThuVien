package com.example.higo.thuvien.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.service.autofill.Dataset;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.example.higo.thuvien.Adapter.BookAuthorAdapter;
import com.example.higo.thuvien.Adapter.RecyclerViewAdapter;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {
    private String idBook;
    private TextView txtBookName;
    private TextView txtQuyenSach;
    private TextView txtTacGia;
    private TextView txtTheLoai;
    private TextView txtSoLuong;
    private TextView txtDescription;
    private TextView txtXemDanhGia;
    private TextView txtRating;
    private ImageView imgReviewBook;
    private Button btnMuonSach;
    private RatingBar ratingBarReview;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Button btnComment;
    private Button btnYeuThich;
    RecyclerView mRecyclerView;
    BookAuthorAdapter mRecyclerViewAdapter;
    List<Book> listBook;
    private BookDAO bookDAO = new BookDAO();
    private SachMuonDAO sachMuonDAO = new SachMuonDAO();
    private QuyenSachDAO quyenSachDAO = new QuyenSachDAO();
    private RateBookDAO rateBookDAO = new RateBookDAO();
    private TheLoaiDAO theLoaiDAO = new TheLoaiDAO();
    private UserDAO userDAO = new UserDAO();
    private AuthorDAO authorDAO = new AuthorDAO();

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
        mRecyclerView = findViewById(R.id.recycleViewBookAuthor);
        txtBookName = findViewById(R.id.txtBookName);
        txtQuyenSach = findViewById(R.id.txtQuyenSach);
        txtXemDanhGia = findViewById(R.id.txtXemDanhGia);
        txtTacGia = findViewById(R.id.txtTacGia);
        txtTheLoai = findViewById(R.id.txtTheLoai);
        txtSoLuong = findViewById(R.id.txtSoLuong);
        txtDescription = findViewById(R.id.txtDescription);
        txtRating = findViewById(R.id.txtRating);
        imgReviewBook = findViewById(R.id.imgReviewBook);
        btnMuonSach = findViewById(R.id.btnMuonSach);
        btnComment = findViewById(R.id.btnComment);
        btnYeuThich = findViewById(R.id.btnYeuThich);
        ratingBarReview = findViewById(R.id.ratingBarReview);
        collapsingToolbarLayout = findViewById(R.id.CollapsingToolbarLayout);

        setmRecyclerView();

//        bookDAO.getQuyenSachByBook(idBook).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot data : dataSnapshot.getChildren()) {
//                    final QuyenSach quyenSach = data.getValue(QuyenSach.class);
//                    quyenSach.setId(data.getKey());
//                    if(quyenSach.getTinhTrang() != 3){ //kiem tra tinh trang sách
//                                                        //kiem tra sách đang được mượn
//                        new SachMuonDAO().searchByQuyenSach(quyenSach.getId()).addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                if(dataSnapshot.getChildrenCount()==0) txtQuyenSach.setText(quyenSach.getId());
//                                else{
//                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
//
//                                        if (data.child("ngayTra").getValue() != null ) {
//                                            txtQuyenSach.setText(quyenSach.getId());
//
//                                        }
//
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
//                    }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    private void addEvents() {
        btnMuonSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user == null) {
                    Toast.makeText(ReviewActivity.this, "Bạn vui lòng đăng nhập để mượn sách", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ReviewActivity.this, LoginActivity.class);
                    startActivity(intent);

                } else {
                    kiemTraTaiKhoan();

                }
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
                if (user != null)
                    loadDialogRating();
                else
                    Toast.makeText(ReviewActivity.this, "Bạn vui lòng đăng nhập để đánh giá sách", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        btnYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user != null)
                    bookDAO.addSachYeuThich(idBook, user.getUid());
                else
                    Toast.makeText(ReviewActivity.this, "Bạn vui lòng đăng nhập để sử dụng chức năng này", Toast.LENGTH_SHORT).show();

            }
        });

        txtXemDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReviewActivity.this, DanhGiaActivity.class);
                intent.putExtra("idBook", idBook);
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
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    rateCount = rateCount + data.child("rate").getValue(float.class);
                }
                float rate = rateCount / dataSnapshot.getChildrenCount();
                rate = (float) Math.round(rate * 10) / 10;
                ratingBarReview.setRating(rate);
                txtRating.setText(rate + "");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    long soLuongSach;
    private void setTxtSoLuong(String idBook) {
        bookDAO.getQuyenSachByBook(idBook).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                soLuongSach = dataSnapshot.getChildrenCount();
                final ArrayList<String> sachKhaDung = new ArrayList<>();
                for(final DataSnapshot data:dataSnapshot.getChildren()){
//                    sachKhaDung.add(data.getKey().toString());
                    new SachMuonDAO().searchByQuyenSach(data.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            sachKhaDung.remove(data.getKey());
                            boolean khaDung = true;
                            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                if(dataSnapshot1.child("ngayTra").getValue()==null){
                                    sachKhaDung.remove(data.getKey());
                                    khaDung = false;
                                    break;
                                }
                            }
                            if(khaDung){
                                sachKhaDung.add(data.getKey().toString());
                                txtQuyenSach.setText(data.getKey().toString());
                            }

                            txtSoLuong.setText(sachKhaDung.size()+"");
                            if(sachKhaDung.size()==0) btnMuonSach.setEnabled(false);
                            else btnMuonSach.setEnabled(true);
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
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String s = txtTacGia.getText().toString();
                    if (TextUtils.isEmpty(s))
                        txtTacGia.setText(data.getValue(Author.class).getName());
                    else
                        txtTacGia.setText(s + " , " + data.getValue(Author.class).getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void kiemTraTaiKhoan() {
        userDAO.isBlocked().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(Boolean.class)) {
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
                } else
                    demSoSachSachMuon();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void demSoSachSachMuon() {
        sachMuonDAO.getListSachMuonByUser(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = 0;
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    if (dataSnapshot1.child("ngayTra").getValue() == null) count++;
                }
                if (count>2) {
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
                } else muonSach();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void muonSach() {
        if (user != null) {
            sachMuonDAO.dangKyMuon(txtQuyenSach.getText().toString());
            AlertDialog.Builder builder = new AlertDialog.Builder(ReviewActivity.this);
            builder.setTitle("Hoàn thành");
            builder.setMessage("Bạn đã mượn sách thành công. Vui lòng đến thư viện để nhận sách!!!");
            builder.setCancelable(false);
            builder.setNegativeButton("Đã hiểu", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
//                    finish();
//                    startActivity(getIntent());
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

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
                if (v > 4) txtRating.setText("Rất hay");
                else if (v > 3) txtRating.setText("Hay");
                else if (v > 2) txtRating.setText("Tạm được");
                else if (v > 1) txtRating.setText("Tệ");
                else if (v > 0) txtRating.setText("Rất tệ");
            }
        });
        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RateBook rateBook = new RateBook();

                rateBook.setIdUser(user.getUid());
                rateBook.setRate(ratingBar.getRating());
                rateBook.setContent(edRating.getText().toString());
                rateBookDAO.insert(rateBook,idBook);
                dialog.cancel();
            }
        });

        dialog.show();
    }


    public void setmRecyclerView() {
        listBook = new ArrayList<>();

        mRecyclerViewAdapter = new BookAuthorAdapter(listBook);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerViewAdapter.notifyDataSetChanged();
//        new BookDAO().getAllBook().addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                listBook.clear();
//                for (DataSnapshot data:dataSnapshot.getChildren()){
//                    Book book = data.getValue(Book.class);
//                    book.setId(data.getKey().toString());
//                    listBook.add(book);
//                }
//                mRecyclerViewAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        authorDAO.searchByIdBook(idBook).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listBook.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    authorDAO.searchBookByAuthor(data.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot data : dataSnapshot.getChildren())
                                bookDAO.searchByID(data.getKey()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Book book = dataSnapshot.getValue(Book.class);
                                        book.setId(dataSnapshot.getKey().toString());
                                        listBook.add(book);
                                        mRecyclerViewAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
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
}
