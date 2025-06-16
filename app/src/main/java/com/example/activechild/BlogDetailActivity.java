package com.example.activechild;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class BlogDetailActivity extends AppCompatActivity {

    TextView tvTitle, tvContent;
    ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_detail);

        // View bağlantıları
        tvTitle = findViewById(R.id.tvDetailTitle);
        tvContent = findViewById(R.id.tvDetailContent);
        ivImage = findViewById(R.id.ivDetailImage);

        // Intent ile gelen verileri al
        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");
        String imageUrl = getIntent().getStringExtra("imageUrl");

        // Verileri göster
        tvTitle.setText(title);
        tvContent.setText(content);

        // Glide ile görsel yükle
        Glide.with(this)
                .load(imageUrl)
                .into(ivImage);
    }
}
