package com.example.activechild;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventsFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Etkinlik> etkinlikList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_events, container, false);

        recyclerView = view.findViewById(R.id.recyclerEtkinlikler); // veya recyclerEtkinlikler kullanıyorsan o id'yi yaz
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        etkinlikleriYukle();

        return view;
    }

    private void etkinlikleriYukle() {
        try {
            AssetManager assetManager = getContext().getAssets();
            InputStream is = assetManager.open("etkinlikler.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, StandardCharsets.UTF_8);

            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Map<String, Etkinlik>>>(){}.getType();
            Map<String, Map<String, Etkinlik>> data = gson.fromJson(json, type);

            if (data.containsKey("etkinlikler")) {
                Map<String, Etkinlik> etkinlikMap = data.get("etkinlikler");
                etkinlikList.addAll(etkinlikMap.values());
            }

            EtkinlikAdapter adapter = new EtkinlikAdapter(getContext(), etkinlikList);
            recyclerView.setAdapter(adapter);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
