package com.example.activechild;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class BlogFragment extends Fragment {

    private RecyclerView recyclerView;
    private BlogAdapter adapter;
    private List<BlogPost> blogPosts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blog, container, false);
        recyclerView = view.findViewById(R.id.recyclerBlog);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        blogPosts = new ArrayList<>();
        blogPosts.add(new BlogPost("Çocuklar İçin Etkinlik Önerileri – Yaz 2025",
                "Bu yaz çocuklar için doğa yürüyüşleri, yaratıcı sanat atölyeleri ve sportif faaliyetler öneriyoruz."));
        blogPosts.add(new BlogPost("Ebeveynler İçin Zaman Yönetimi",
                "Yoğun programlar arasında ebeveynlerin kendi zamanlarını da korumaları çok önemlidir."));
        blogPosts.add(new BlogPost("Çocuklarda Teknoloji Kullanımı",
                "Ekran süresini dengede tutmak için günlük sınırlar koyun ve eğitici içerikler tercih edin."));

        adapter = new BlogAdapter(blogPosts);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
