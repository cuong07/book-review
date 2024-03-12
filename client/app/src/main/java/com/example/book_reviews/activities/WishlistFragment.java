package com.example.book_reviews.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.book_reviews.R;
import com.example.book_reviews.adapters.BookAdapter;
import com.example.book_reviews.adapters.ProfileAdapter;
import com.example.book_reviews.api.book.BookApi;
import com.example.book_reviews.api.book.IBookApi;
import com.example.book_reviews.response.Book;
import com.example.book_reviews.response.BookResponse;
import com.example.book_reviews.response.SearchResponse;
import com.example.book_reviews.singleton.UserSingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WishlistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WishlistFragment extends Fragment {
    RecyclerView rcvHistory;
    List<Book> books = new ArrayList<>();
    IBookApi bookApi = new BookApi();

    private int numberResult = 0;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public WishlistFragment() {
    }

    public static WishlistFragment newInstance(String param1, String param2) {
        WishlistFragment fragment = new WishlistFragment();
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
            getAllBooks(userId);
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wishlist, container, false);

        rcvHistory = rootView.findViewById(R.id.rcvHistory);
        rcvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        ProfileAdapter adapter = new ProfileAdapter(getActivity(), books);
        rcvHistory.setAdapter(adapter);
        rcvHistory.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        return rootView;
    }

    private void getAllBooks(Long userId) {
        bookApi.getAllBookSearch(userId).enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                Toast.makeText(getActivity().getApplicationContext(), "Thành công", Toast.LENGTH_SHORT).show();
                BookResponse searchResponse = response.body();
                if (searchResponse != null && searchResponse.getData() != null) {
                    books.clear();
                    books.addAll(searchResponse.getData());
                    numberResult = searchResponse.getData().size();
                    Objects.requireNonNull(rcvHistory.getAdapter()).notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Có lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }

}