package com.example.activechild;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    Button registerButton, loginButton;
    TextView welcomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerButton = findViewById(R.id.registerButton);
        loginButton = findViewById(R.id.loginButton);
        welcomeTextView = findViewById(R.id.welcomeTextView);

        // Giriş veya kayıt sonrası gelen email ve isim bilgisi
        String username = getIntent().getStringExtra("USERNAME");
        String email = getIntent().getStringExtra("email");

        if (username != null && !username.isEmpty()) {
            welcomeTextView.setText("Hoş geldin, " + username + "!");
        }

        if (email != null && !email.isEmpty()) {
            // email varsa, kullanıcı giriş yapmış demektir → fragment başlat
            openAccountFragment(email);
        }

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void openAccountFragment(String email) {
        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        AccountFragment fragment = new AccountFragment();
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment) // XML'deki FrameLayout id'si
                .commit();
    }
}
