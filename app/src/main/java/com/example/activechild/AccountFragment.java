package com.example.activechild;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AccountFragment extends Fragment {

    private TextView tvName, tvEmail, tvChildInfo;
    private EditText etChildName, etChildAge, etChildGender, etChildInterests;
    private Button btnSaveChild, btnEditProfile, btnChangePassword, btnLogout;

    private SharedPreferences prefs;
    private String currentEmailKey;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        // XML view bağlantıları
        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvChildInfo = view.findViewById(R.id.tvChildInfo);
        etChildName = view.findViewById(R.id.etChildName);
        etChildAge = view.findViewById(R.id.etChildAge);
        etChildGender = view.findViewById(R.id.etChildGender);
        etChildInterests = view.findViewById(R.id.etChildInterests);
        btnSaveChild = view.findViewById(R.id.btnSaveChild);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        btnChangePassword = view.findViewById(R.id.btnChangePassword);
        btnLogout = view.findViewById(R.id.btnLogout);

        // Önce genel UserPrefs'ten aktif kullanıcıyı al
        SharedPreferences globalPrefs = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        currentEmailKey = globalPrefs.getString("activeUserEmail", "default");

// Sonra o kullanıcıya özel UserPrefs oku
        prefs = requireActivity().getSharedPreferences("UserPrefs_" + currentEmailKey, Context.MODE_PRIVATE);

// Hesap bilgilerini göster
        // Hesap bilgilerini göster
        String name = prefs.getString("isim", "Ad");
        String surname = prefs.getString("soyisim", "Soyad");
        String email = prefs.getString("email", "E-posta bilgisi yok");

        tvName.setText("Ad Soyad: " + name + " " + surname);
        tvEmail.setText("E-posta: " + email);



        // Çocuk bilgilerini yükle
        String savedName = prefs.getString("childName_" + currentEmailKey, "");
        String savedAge = prefs.getString("childAge_" + currentEmailKey, "");
        String savedGender = prefs.getString("childGender_" + currentEmailKey, "");
        String savedInterests = prefs.getString("childInterests_" + currentEmailKey, "");

        if (!savedName.isEmpty()) {
            // Bilgiler varsa: göster
            showChildInfo(savedName, savedAge, savedGender, savedInterests);
        } else {
            // Giriş ekranı gösterilsin
            tvChildInfo.setVisibility(View.GONE);
        }

        // Kaydet butonu
        btnSaveChild.setOnClickListener(v -> {
            String nameChild = etChildName.getText().toString();
            String age = etChildAge.getText().toString();
            String gender = etChildGender.getText().toString();
            String interests = etChildInterests.getText().toString();

            if (nameChild.isEmpty() || age.isEmpty() || gender.isEmpty() || interests.isEmpty()) {
                Toast.makeText(getContext(), "Tüm alanlar doldurulmalı", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("childName_" + currentEmailKey, nameChild);
            editor.putString("childAge_" + currentEmailKey, age);
            editor.putString("childGender_" + currentEmailKey, gender);
            editor.putString("childInterests_" + currentEmailKey, interests);
            editor.apply();

            showChildInfo(nameChild, age, gender, interests);
            Toast.makeText(getContext(), "Çocuk bilgileri kaydedildi", Toast.LENGTH_SHORT).show();
        });

        // Diğer butonlar
        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            startActivity(intent);
        });


        btnChangePassword.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            // activeUserEmail bilgisini temizle
            SharedPreferences.Editor globalEditor = globalPrefs.edit();
            globalEditor.remove("activeUserEmail");
            globalEditor.apply();

            // Giriş ekranına yönlendir
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            Toast.makeText(getContext(), "Çıkış yapıldı", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    private void showChildInfo(String name, String age, String gender, String interests) {
        etChildName.setVisibility(View.GONE);
        etChildAge.setVisibility(View.GONE);
        etChildGender.setVisibility(View.GONE);
        etChildInterests.setVisibility(View.GONE);
        btnSaveChild.setVisibility(View.GONE);

        String info = "👶\n" +  "Çocuk Bilgileri\n"
                + "Ad Soyad: " + name + "\n"
                + "Yaş: " + age + "\n"
                + "Cinsiyet: " + gender + "\n"
                + "İlgi Alanları: " + interests;

        tvChildInfo.setText(info);
        tvChildInfo.setVisibility(View.VISIBLE);
    }
}
