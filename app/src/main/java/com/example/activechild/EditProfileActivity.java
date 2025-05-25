package com.example.activechild;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    EditText etName, etSurname, etEmail;
    EditText etChildName, etChildAge, etChildGender, etChildInterests;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // View bağlantıları
        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        etEmail = findViewById(R.id.etEmail);
        etChildName = findViewById(R.id.etChildName);
        etChildAge = findViewById(R.id.etChildAge);
        etChildGender = findViewById(R.id.etChildGender);
        etChildInterests = findViewById(R.id.etChildInterests);
        btnSave = findViewById(R.id.btnSaveProfile);

        // Genel prefs: aktif kullanıcıyı bul
        SharedPreferences globalPrefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String emailKey = globalPrefs.getString("activeUserEmail", "");

        if (emailKey.isEmpty()) {
            Toast.makeText(this, "Kullanıcı oturumu bulunamadı", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Kullanıcıya özel prefs
        SharedPreferences prefs = getSharedPreferences("UserPrefs_" + emailKey, MODE_PRIVATE);

        // Kullanıcı bilgilerini göster
        String isim = prefs.getString("isim", "");
        String soyisim = prefs.getString("soyisim", "");
        etName.setText(isim);
        etSurname.setText(soyisim);
        etEmail.setText(prefs.getString("email", ""));

        // Çocuk bilgilerini göster
        etChildName.setText(prefs.getString("childName_" + emailKey, ""));
        etChildAge.setText(prefs.getString("childAge_" + emailKey, ""));
        etChildGender.setText(prefs.getString("childGender_" + emailKey, ""));
        etChildInterests.setText(prefs.getString("childInterests_" + emailKey, ""));

        // Kaydet butonu
        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String surname = etSurname.getText().toString().trim();
            String newEmail = etEmail.getText().toString().trim();

            if (name.isEmpty() || surname.isEmpty() || newEmail.isEmpty()) {
                Toast.makeText(this, "Tüm alanlar doldurulmalı", Toast.LENGTH_SHORT).show();
                return;
            }

            // Veritabanını güncelle
            UserDatabaseHelper dbHelper = new UserDatabaseHelper(this);
            boolean updated = dbHelper.updateUser(emailKey, newEmail, name, surname);

            if (updated) {
                // Yeni SharedPreferences
                SharedPreferences.Editor editor = getSharedPreferences("UserPrefs_" + newEmail, MODE_PRIVATE).edit();
                editor.putString("isim", name);
                editor.putString("soyisim", surname);
                editor.putString("name", name + " " + surname); // istersen hem birleşik hem ayrı sakla
                editor.putString("email", newEmail);
                editor.putString("childName_" + newEmail, etChildName.getText().toString().trim());
                editor.putString("childAge_" + newEmail, etChildAge.getText().toString().trim());
                editor.putString("childGender_" + newEmail, etChildGender.getText().toString().trim());
                editor.putString("childInterests_" + newEmail, etChildInterests.getText().toString().trim());
                editor.apply();

                // Global prefs güncelle
                SharedPreferences.Editor globalEditor = globalPrefs.edit();
                globalEditor.putString("activeUserEmail", newEmail);
                globalEditor.apply();

                Toast.makeText(this, "Profil güncellendi", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Veritabanı güncellenemedi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
