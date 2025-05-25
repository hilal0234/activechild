package com.example.activechild;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;

public class RegisterActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText, İsimEditText, SoyisimEditText;
    Button registerButton;
    CheckBox termsCheckbox;
    UserDatabaseHelper dbHelper;
    TextView loginRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // View tanımlamaları
        İsimEditText = findViewById(R.id.etİsim);
        SoyisimEditText = findViewById(R.id.etSoyisim);
        emailEditText = findViewById(R.id.etEmail);
        passwordEditText = findViewById(R.id.etPassword);
        registerButton = findViewById(R.id.btnRegister);
        termsCheckbox = findViewById(R.id.cbTerms);
        dbHelper = new UserDatabaseHelper(this);

        // Giriş ekranına yönlendirme
        loginRedirectText = findViewById(R.id.tvLoginRedirect);
        loginRedirectText.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Kayıt işlemi
        registerButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String isim = İsimEditText.getText().toString().trim();
            String soyisim = SoyisimEditText.getText().toString().trim();

            if (!termsCheckbox.isChecked()) {
                Toast.makeText(this, "Şartları kabul etmelisiniz!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (email.isEmpty() || password.isEmpty() || isim.isEmpty() || soyisim.isEmpty()) {
                Toast.makeText(this, "Boş bırakılmaz", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = dbHelper.insertUser(email, password, isim, soyisim);

            if (inserted) {
                // Her kullanıcı için kişisel SharedPreferences sakla
                SharedPreferences prefs = getSharedPreferences("UserPrefs_" + email, MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("isim", isim);
                editor.putString("soyisim", soyisim);
                editor.putString("email", email);
                editor.apply();

                Toast.makeText(this, "Kayıt başarılı!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(RegisterActivity.this, Homepage.class);
                intent.putExtra("email", email); // email'i MainActivity'ye gönder
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Kayıt başarısız, tekrar deneyin.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
