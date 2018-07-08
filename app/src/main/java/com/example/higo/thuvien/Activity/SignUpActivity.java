package com.example.higo.thuvien.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.higo.thuvien.Model.User;
import com.example.higo.thuvien.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edEmailLogin,edPasswordLogin;
    private Button btnSignUp;
    private TextView txtSignUp;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_signup);
        addControls();
        addEvents();
    }
    private void addEvents() {
        btnSignUp.setOnClickListener(this);


    }

    private void addControls() {
        edEmailLogin = findViewById(R.id.edEmailSignUp);
        edPasswordLogin = findViewById(R.id.edPasswordSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSignUp:
                String email,password;
                email = edEmailLogin.getText().toString();
                password = edPasswordLogin.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Mật khẩu phải dài hơn 6 kí tự", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser mUser = mAuth.getCurrentUser();
                                    User user = new User(mUser.getUid(),mUser.getEmail());
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference userRoot = database.getReference("User");
                                    userRoot.setValue(user);
                                    Toast.makeText(SignUpActivity.this,"Đăng kí thành công",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                    startActivity(intent);


                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignUpActivity.this,"Đăng kí thất bại",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
        }
    }
}
