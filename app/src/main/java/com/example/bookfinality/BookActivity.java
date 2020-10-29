package com.example.bookfinality;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class BookActivity extends AppCompatActivity {

    private static final String FACEBOOK_URL="https://ru-ru.facebook.com/timur.khudaibergenov";
    private static final String FACEBOOK_PAGE_ID = "timur.khudaibergenov";
    private static final String INSTAGRAM_ID="tkhudaibergenov";

    private static final String KEY_TO_NEW_PAGE="key_to_page";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        Fragment fragment;
        if (getIntent().getIntExtra(KEY_TO_NEW_PAGE,-1)>0) {
            fragment = BookFragment.newFragment(getIntent().getIntExtra(KEY_TO_NEW_PAGE,-1));
        }
        else  fragment = new BookFragment();
        getSupportActionBar().setTitle(R.string.to_author);
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container)==null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
        else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.social_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.facebook_btn:{
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                facebookIntent.setData(Uri.parse(getFacebookPageURL()));
                startActivity(facebookIntent);
            }
            break;
            case R.id.inst_btn:{
                openInst();
            }
            break;
            case R.id.policy_btn:{
                openPolicy();
            }
            break;
            case  R.id.content_btn:{
                Intent intent=new Intent(this,ContentActivity.class);
                startActivity(intent);
                this.finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void openPolicy() {
        Intent intent = new Intent(this,PolicyActivity.class);
        startActivity(intent);
    }

    private String getFacebookPageURL() {
        try {
            int versionCode = this.getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL;
        }
    }


    private void openInst(){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://instagram.com/_u/" + INSTAGRAM_ID));
            intent.setPackage("com.instagram.android");
            startActivity(intent);
        }
        catch (android.content.ActivityNotFoundException anfe)
        {
            Log.e("error","inst");
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/" + INSTAGRAM_ID)));
        }
    }

    public static Intent newIntent(Context context, int page){
        Intent intent=new Intent(context,BookActivity.class);
        intent.putExtra(KEY_TO_NEW_PAGE,page);
        return intent;
    }
}