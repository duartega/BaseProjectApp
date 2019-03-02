package com.sp18.ssu370.baseprojectapp.ui.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sp18.ssu370.baseprojectapp.R;

import java.io.File;

public class ArticleActivity extends MainActivity {

    private String location;
    private ArticleAdapter mAdapter;

    public String getTags(boolean[] bool){
        StringBuffer tags = new StringBuffer();
        for (int i = 0; i < bool.length; i++){
            if (bool[i]){
                tags.append("1");
            }
            else{
                tags.append("0");
            }
        }
        return tags.toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.swapCursor(tagDB.getAllData());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_view);
        location = getIntent().getStringExtra("location");
        final String path = getIntent().getStringExtra("file");
        ImageView Iview = findViewById(R.id.article_img);

        File file = new File(path);
        Glide.with(this).load(Uri.fromFile(file)).into(Iview);
        Iview.setRotation(90);




        final RecyclerView taglist = findViewById(R.id.tag_recycle);
        taglist.setLayoutManager(new LinearLayoutManager(this));


        mAdapter = new ArticleAdapter(this, tagDB.getAllData());
        taglist.setAdapter(mAdapter);

        Button edittag = findViewById(R.id.edit_tag_db);
        edittag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArticleActivity.this, TagActivity.class );
                startActivity(intent);
            }

        });
        Button add = findViewById(R.id.add_article_db);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get information

                boolean[] tagbool = mAdapter.getChecked();
                String tags = getTags(tagbool);
                boolean inserted = articleDB.InsertData(tags, location, path);
                if (inserted){
                    Toast.makeText(ArticleActivity.this, "INSERT IS A SUCCESS", Toast.LENGTH_SHORT).show();

                }
                else
                    Toast.makeText(ArticleActivity.this, "INSERT IS NOT A SUCCESS", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
