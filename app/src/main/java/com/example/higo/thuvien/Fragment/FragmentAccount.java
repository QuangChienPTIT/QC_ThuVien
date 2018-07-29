package com.example.higo.thuvien.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.higo.thuvien.Activity.ActivityYeuThich;
import com.example.higo.thuvien.Activity.LoginActivity;
import com.example.higo.thuvien.Activity.MainActivity;
import com.example.higo.thuvien.DAO.UserDAO;
import com.example.higo.thuvien.Model.User;
import com.example.higo.thuvien.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentAccount extends Fragment {
    private CircleImageView imgTaiKhoan;
    private TextView txtTenTaiKhoan;
    private TextView txtEmail;
    private TextView txtCapNhat;
    private RelativeLayout layoutSetting;
    private RelativeLayout layoutLogout;
    private RelativeLayout layoutTaiKhoan;
    private RelativeLayout layoutSachYeuThich;
    private RelativeLayout layoutAbout;
    private RelativeLayout layoutThongTinTaiKhoan;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;
    private static final int PICK_IMAGE_REQUEST = 1;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    private String uIdAccount;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    UserDAO userDAO = new UserDAO();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_account,container,false);
        layoutLogout = view.findViewById(R.id.layoutLogout);
        TextView txtLogout = view.findViewById(R.id.txtLogout);
        if (user!=null){
            txtLogout.setText("Đăng xuất");
            uIdAccount = user.getUid();
            addControls(view);
            addEvents();
        }
        else {
            Toast.makeText(getContext(),"Vui lòng đăng nhập để sử dụng các chức năng",Toast.LENGTH_LONG);
            txtLogout.setText("Đăng nhập");
        }
        layoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user!=null) {
                    FirebaseAuth.getInstance().signOut();
                    Intent refresh = new Intent(getActivity(), MainActivity.class);
                    startActivity(refresh);
                    getActivity().finish(); //
                }
                else {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        return view;
    }

    private void addControls(View view) {
        imgTaiKhoan = view.findViewById(R.id.imgTaiKhoan);
        txtTenTaiKhoan = view.findViewById(R.id.txtTenTaiKhoan);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtCapNhat = view.findViewById(R.id.txtCapNhat);
        layoutAbout = view.findViewById(R.id.layoutAbout);
        layoutSetting = view.findViewById(R.id.layoutSetting);
        layoutLogout = view.findViewById(R.id.layoutLogout);
        layoutSachYeuThich = view.findViewById(R.id.layoutSachYeuThich);
        layoutThongTinTaiKhoan = view.findViewById(R.id.layoutThongTinTaiKhoan);
        mStorageRef = FirebaseStorage.getInstance().getReference("ImageUser");

        setTaiKhoan();
    }

    private void setTaiKhoan() {
        userDAO.searchByID(uIdAccount).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Picasso.get().load(user.getImgURL()).into(imgTaiKhoan);
                txtEmail.setText(user.getEmail());
                txtTenTaiKhoan.setText(user.getFirstName()+" "+user.getLastName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addEvents() {

        imgTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        txtCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });

        layoutThongTinTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDialogAccount();
            }
        });

        layoutSachYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActivityYeuThich.class);
                startActivity(intent);
            }
        });
        layoutAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Hoàn thành");
                builder.setMessage(R.string.about);
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
        });




    }

    private void uploadFile() {

        if (mImageUri != null) {
            Calendar calendar = Calendar.getInstance();
            StorageReference fileReference = mStorageRef.child("image" + calendar.getTimeInMillis() + ".png");

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            Toast.makeText(getContext(), "Upload successful", Toast.LENGTH_LONG).show();
                            //ImageAccount upload = new ImageAccount("Quang Chien", taskSnapshot.getDownloadUrl().toString());
                            String imgURL=taskSnapshot.getDownloadUrl().toString();



                            FirebaseDatabase.getInstance().getReference().child("User").child(uIdAccount).child("imgURL").setValue(imgURL);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "loi o day " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });
        } else {
            Toast.makeText(getContext(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(imgTaiKhoan);
            uploadFile();
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void loadDialogAccount() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setTitle("Account");
        dialog.setContentView(R.layout.dialog_account);
        final EditText edEmailAcc = dialog.findViewById(R.id.edEmailAcc);
        final EditText edFirstNameAcc = dialog.findViewById(R.id.edFirstNameAcc);
        final EditText edLastNameAcc = dialog.findViewById(R.id.edLastNameAcc);
        final EditText edAddressAcc = dialog.findViewById(R.id.edAddressAcc);
        final EditText edPhoneAcc = dialog.findViewById(R.id.edPhoneAcc);
        final EditText edTuoi = dialog.findViewById(R.id.edTuoi);
        final CircleImageView circleImageTaiKhoan = dialog.findViewById(R.id.circleImgTaiKhoan);
        Button btnDongY = dialog.findViewById(R.id.btnDongY);
        Button btnThoatAccount = dialog.findViewById(R.id.btnThoatAccount);
        final User user = new User();
        root.child("User").child(uIdAccount).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        edEmailAcc.setText(dataSnapshot.getValue(User.class).getEmail());
                        edFirstNameAcc.setText(dataSnapshot.getValue(User.class).getFirstName());
                        edLastNameAcc.setText(dataSnapshot.getValue(User.class).getLastName());
                        edTuoi.setText(dataSnapshot.getValue(User.class).getAge()+"");
                        edAddressAcc.setText(dataSnapshot.getValue(User.class).getAddress());
                        edPhoneAcc.setText(dataSnapshot.getValue(User.class).getPhoneNumber());

                        String imageUrl = dataSnapshot.getValue(User.class).getImgURL();
                        Picasso.get().load(imageUrl).into(circleImageTaiKhoan);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.child("User").child(uIdAccount).child("email").setValue(edEmailAcc.getText()+"");
                root.child("User").child(uIdAccount).child("firstName").setValue(edFirstNameAcc.getText()+"");
                root.child("User").child(uIdAccount).child("lastName").setValue(edLastNameAcc.getText()+"");
                root.child("User").child(uIdAccount).child("age").setValue(Integer.valueOf(edTuoi.getText().toString()));
                root.child("User").child(uIdAccount).child("address").setValue(edAddressAcc.getText()+"");
                root.child("User").child(uIdAccount).child("phoneNumber").setValue(edPhoneAcc.getText()+"");

                dialog.hide();
            }
        });
        btnThoatAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });

        dialog.show();
    }
}
