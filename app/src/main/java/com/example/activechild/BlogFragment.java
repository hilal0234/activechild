package com.example.activechild;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class BlogFragment extends Fragment {

    private RecyclerView recyclerView;
    private BlogAdapter adapter;
    private List<BlogPost> blogList;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blog, container, false);

        // RecyclerView setup
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        blogList = new ArrayList<>();
        adapter = new BlogAdapter(blogList, getContext());
        recyclerView.setAdapter(adapter);

        // Firestore bağlantısı
        db = FirebaseFirestore.getInstance();
        db.collection("blog_posts")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            BlogPost post = doc.toObject(BlogPost.class);
                            blogList.add(post);
                            Log.d("BLOG_FIREBASE", "Yüklenen başlık: " + post.getTitle());
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "Henüz blog verisi yok", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Hata: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("BLOG_FIREBASE", "Veri çekilemedi: ", e);
                });

        return view;
    }
}
