package com.example.activechild;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
        String isim = dbHelper.getUserNameByEmail(email);
        welcomeText.setText("Merhaba " + isim);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Başlangıçta HomeFragment yüklensin
        loadFragment(new HomeFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Fragment selectedFragment = null;

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
