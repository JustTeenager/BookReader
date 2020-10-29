package com.example.bookfinality;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class ContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        Fragment fragment=new ContentFragment();
        getSupportActionBar().setTitle(R.string.contents);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container,fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,BookActivity.class);
        startActivity(intent);
        this.finish();
    }
}
