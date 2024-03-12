package com.example.book_reviews.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_reviews.R;
import com.example.book_reviews.adapters.BookAdapter;
import com.example.book_reviews.api.book.BookApi;
import com.example.book_reviews.api.book.IBookApi;
import com.example.book_reviews.api.user.IUserApi;
import com.example.book_reviews.api.user.UserApi;
import com.example.book_reviews.response.LoginResponse;
import com.example.book_reviews.response.Review;
import com.example.book_reviews.response.SearchResponse;
import com.example.book_reviews.singleton.UserSingleton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ImageView ivHome, ivWishlist, ivProfile;

    @SuppressLint({"MissingInflatedId", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivHome = findViewById(R.id.ivHome);
        ivWishlist = findViewById(R.id.ivWishlist);
        ivProfile = findViewById(R.id.ivProfile);
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction initialTransaction = fragmentManager.beginTransaction();
        SearchFragment fragmentMain = new SearchFragment();
        initialTransaction.replace(R.id.fragment_container, fragmentMain);
        initialTransaction.commit();

        ivHome.setBackground(getResources().getDrawable(R.drawable.oval));

        ivHome.setOnClickListener(v -> {
            setSelected(ivHome);
            FragmentTransaction homeTransaction = fragmentManager.beginTransaction();
            homeTransaction.replace(R.id.fragment_container, fragmentMain);
            homeTransaction.commit();
        });

        ivWishlist.setOnClickListener(v -> {
            setSelected(ivWishlist);
            WishlistFragment fragment = new WishlistFragment();
            FragmentTransaction wishlistTransaction = fragmentManager.beginTransaction();
            wishlistTransaction.replace(R.id.fragment_container, fragment);
            wishlistTransaction.commit();
        });

        ivProfile.setOnClickListener(v -> {
            setSelected(ivProfile);
            ProfileFragment fragment = new ProfileFragment();
            FragmentTransaction profileTransaction = fragmentManager.beginTransaction();
            profileTransaction.replace(R.id.fragment_container, fragment);
            profileTransaction.commit();
        });



    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setSelected(ImageView imageView) {
        imageView.setBackground(getResources().getDrawable(R.drawable.oval));
        ivHome.setBackground(imageView == ivHome ? getResources().getDrawable(R.drawable.oval) : null);
        ivWishlist.setBackground(imageView == ivWishlist ? getResources().getDrawable(R.drawable.oval) : null);
        ivProfile.setBackground(imageView == ivProfile ? getResources().getDrawable(R.drawable.oval) : null);
    }


}