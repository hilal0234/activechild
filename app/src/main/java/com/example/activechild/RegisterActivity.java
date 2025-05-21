package com.example.activechild;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText,İsimEditText,SoyisimEditText;
    Button registerButton;
    CheckBox termsCheckbox;
    UserDatabaseHelper dbHelper;
    TextView loginRedirectText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        İsimEditText=findViewById(R.id.etİsim);
        SoyisimEditText=findViewById(R.id.etSoyisim);
        emailEditText = findViewById(R.id.etEmail);
        passwordEditText = findViewById(R.id.etPassword);
        registerButton = findViewById(R.id.btnRegister);
        termsCheckbox = findViewById(R.id.cbTerms);
        dbHelper = new UserDatabaseHelper(this);

        // "Hesabınız var mı?" yazısına tıklanabilirlik ekliyoruz
        loginRedirectText = findViewById(R.id.tvLoginRedirect);
        loginRedirectText.setOnClickListener(v -> {

            // Giriş ekranına yönlendiriyoruz
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        registerButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String İsim=İsimEditText.getText().toString().trim();
            String Soyisim=SoyisimEditText.getText().toString().trim();

            if (!termsCheckbox.isChecked()) {
                Toast.makeText(this, "Şartları kabul etmelisiniz!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (email.isEmpty() || password.isEmpty() || İsim.isEmpty()||Soyisim.isEmpty()) {
                    Toast.makeText(this, "Boş Bırakılmaz", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = dbHelper.insertUser(email, password ,İsim, Soyisim);

            if (inserted) {
                Toast.makeText(this, "Kayıt başarılı!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.putExtra("USERNAME", İsim);
                startActivity(intent);
                finish(); // Bu aktiviteyi kapat
            }
            else {
                Toast.makeText(this, "Kayıt başarısız, tekrar deneyin.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
