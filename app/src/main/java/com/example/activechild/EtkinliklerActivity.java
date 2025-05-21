package com.example.activechild;

import android.os.Bundle;
import android.content.res.AssetManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class EtkinliklerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Etkinlik> etkinlikList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etkinlikler); // XML dosyanın adı

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        etkinlikleriYukle();
    }

    private void etkinlikleriYukle() {
        try {
            InputStream is = getAssets().open("etkinlikler.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, StandardCharsets.UTF_8);

            Gson gson = new Gson();
            Type listType = new TypeToken<List<Etkinlik>>() {}.getType();
            etkinlikList = gson.fromJson(json, listType);

            EtkinlikAdapter adapter = new EtkinlikAdapter(this, etkinlikList);
            recyclerView.setAdapter(adapter);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
