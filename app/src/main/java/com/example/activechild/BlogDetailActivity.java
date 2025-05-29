package com.example.activechild;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BlogDetailActivity extends AppCompatActivity {

    TextView tvTitle, tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_detail);

        tvTitle = findViewById(R.id.tvBlogDetailTitle);
        tvContent = findViewById(R.id.tvBlogDetailContent);

        // Intent ile gelen başlık ve içerik
        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");

        tvTitle.setText(title);
        tvContent.setText(content);
    }
}
