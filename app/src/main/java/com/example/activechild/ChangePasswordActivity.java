package com.example.activechild;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText etOldPassword, etNewPassword, etConfirmPassword;
    Button btnChangePassword;
    SharedPreferences prefs;
    UserDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        etOldPassword = findViewById(R.id.etOldPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        dbHelper = new UserDatabaseHelper(this);

        String email = prefs.getString("activeUserEmail", "");

        btnChangePassword.setOnClickListener(v -> {
            String oldPassword = etOldPassword.getText().toString();
            String newPassword = etNewPassword.getText().toString();
            String confirmPassword = etConfirmPassword.getText().toString();

            if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Tüm alanları doldurun", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!dbHelper.checkUser(email, oldPassword)) {
                Toast.makeText(this, "Eski şifre yanlış", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(this, "Yeni şifreler uyuşmuyor", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean updated = dbHelper.updatePassword(email, newPassword);
            if (updated) {
                Toast.makeText(this, "Şifre başarıyla değiştirildi", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Şifre güncellenemedi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
