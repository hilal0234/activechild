package com.example.activechild;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.content.Context;




import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button loginButton;
    UserDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);
        dbHelper = new UserDatabaseHelper(this);
        TextView tvLogin = findViewById(R.id.tvLogin);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean userExists = dbHelper.checkUser(email, password);

            if (userExists) {
                Toast.makeText(this, "Giriş başarılı!", Toast.LENGTH_SHORT).show();

                // Kullanıcı adını DB'den çek
                String isim = dbHelper.getUserNameByEmail(email);

                // SharedPreferences'e kayıt et
                // Her kullanıcı için kişisel prefs
                SharedPreferences prefs = getSharedPreferences("UserPrefs_" + email, MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("name", isim);
                editor.putString("email", email);
                editor.putString("activeUserEmail", email); // aktif kullanıcıyı da belirt
                editor.apply();

// Genel "aktif kullanıcı"yı ayrı bir yerde sakla
                SharedPreferences globalPrefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                SharedPreferences.Editor globalEditor = globalPrefs.edit();
                globalEditor.putString("activeUserEmail", email);
                globalEditor.apply();


                Intent intent = new Intent(LoginActivity.this, Homepage.class);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
            }

         else {
                Toast.makeText(this, "Geçersiz email veya şifre", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
