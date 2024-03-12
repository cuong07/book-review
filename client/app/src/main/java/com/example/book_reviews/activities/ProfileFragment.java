package com.example.book_reviews.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.book_reviews.R;
import com.example.book_reviews.adapters.ProfileAdapter;
import com.example.book_reviews.adapters.WishlistAdapter;
import com.example.book_reviews.api.book.BookApi;
import com.example.book_reviews.api.book.IBookApi;
import com.example.book_reviews.api.wishlist.IWishlistApi;
import com.example.book_reviews.api.wishlist.WishlistApi;
import com.example.book_reviews.response.BaseRespose;
import com.example.book_reviews.response.Book;
import com.example.book_reviews.response.BookResponse;
import com.example.book_reviews.response.Review;
import com.example.book_reviews.response.SearchResponse;
import com.example.book_reviews.response.WishlishResponse;
import com.example.book_reviews.singleton.UserSingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    Button btnGoToLogin, btnGoToRegister;
    RecyclerView rcvWishlist;
    LinearLayout layoutBtn, layoutData;

    private IWishlistApi wishlistApi = new WishlistApi();
    private List<WishlishResponse.WishlistData> wishlistData = new ArrayList<>();
    private int numberResult = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        UserSingleton userSingleton = UserSingleton.getInstance();
        Long userId = userSingleton.getUserId();
        if (userId != null) {
            getWishlist(userId);
        } else {
            Toast.makeText(getActivity(), "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        btnGoToLogin = rootView.findViewById(R.id.btnGoToLogin);
        btnGoToRegister = rootView.findViewById(R.id.btnGoToRegister);
        rcvWishlist = rootView.findViewById(R.id.rcvWishlist);

        layoutBtn = rootView.findViewById(R.id.layoutBtn);
        layoutData = rootView.findViewById(R.id.layoutData);

        UserSingleton userSingleton = UserSingleton.getInstance();
        String email = userSingleton.getEmail();
        if (email == null) {
            layoutBtn.setVisibility(View.VISIBLE);
            layoutData.setVisibility(View.INVISIBLE);
        } else {
            layoutBtn.setVisibility(View.INVISIBLE);
            layoutData.setVisibility(View.VISIBLE);
        }

        btnGoToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });

        btnGoToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), RegisterActivity.class);
            startActivity(intent);
        });


        return rootView;
    }

    private void getWishlist(Long userId) {
        wishlistApi.getWishlistByUser(userId).enqueue(new Callback<BaseRespose<WishlishResponse>>() {
            @Override
            public void onResponse(Call<BaseRespose<WishlishResponse>> call, Response<BaseRespose<WishlishResponse>> response) {
                BaseRespose<WishlishResponse> res = response.body();
                if (res != null) {
                    wishlistData = res.getData().getWishlistItems();
                    WishlistAdapter adapter = new WishlistAdapter(getActivity(), wishlistData);
                    rcvWishlist.setAdapter(adapter);
                    rcvWishlist.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    Toast.makeText(getActivity().getApplicationContext(), "thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseRespose<WishlishResponse>> call, Throwable t) {
                Toast.makeText(getActivity(), "Xóa thất bại", Toast.LENGTH_SHORT).show();

            }
        });
    }


}