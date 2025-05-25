package com.example.activechild;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Homepage extends AppCompatActivity {

    TextView welcomeText;
    UserDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        welcomeText = findViewById(R.id.tvWelcome);
        dbHelper = new UserDatabaseHelper(this);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        // 🔐 Giriş yapan kullanıcının email'ini kaydet (çocuk bilgileri için gerekli)
        if (email != null) {
            SharedPreferences prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("activeUserEmail", email);
            editor.apply();
        }

        // Kullanıcının adı gösteriliyor
        String isim = dbHelper.getUserNameByEmail(email);
        welcomeText.setText("Merhaba " + isim);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Başlangıçta HomeFragment yüklensin
        loadFragment(new HomeFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_events) {
                selectedFragment = new EventsFragment();
            } else if (itemId == R.id.nav_search) {
                selectedFragment = new SearchFragment();
            } else if (itemId == R.id.nav_blog) {
                selectedFragment = new BlogFragment();
            } else if (itemId == R.id.nav_account) {
                selectedFragment = new AccountFragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                return true;
            }

            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment);
        ft.commit();
    }
}
