package com.example.book_reviews.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_reviews.R;
import com.example.book_reviews.activities.LoginActivity;
import com.example.book_reviews.api.book.BookApi;
import com.example.book_reviews.api.book.IBookApi;
import com.example.book_reviews.api.wishlist.IWishlistApi;
import com.example.book_reviews.api.wishlist.WishlistApi;
import com.example.book_reviews.response.BaseRespose;
import com.example.book_reviews.response.Book;
import com.example.book_reviews.response.Review;
import com.example.book_reviews.response.SearchResponse;
import com.example.book_reviews.response.WishlistItem;
import com.example.book_reviews.singleton.UserSingleton;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
    private Context context;
    List<Book> books = new ArrayList<>();
    IWishlistApi wishlistApi = new WishlistApi();


    public ProfileAdapter(Context applicationContext, List<Book> books) {
        this.context = applicationContext;
        this.books = books;
    }

    @NonNull
    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
        return new ProfileAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book selectedbook = books.get(position);
        if (selectedbook.getName().length() >= 20) {
            String bookTitle = selectedbook.getName().substring(0, 20);
            holder.tvBookTitle.setText(bookTitle);
        } else {
            holder.tvBookTitle.setText(selectedbook.getName());
        }

        String imageUrl = selectedbook.getImage();
        new DownloadImageTask(holder.ivBookCover).execute(imageUrl);
        holder.tvPubDate.setText(selectedbook.getFormattedPublicationDate());
        holder.ivHeart.setOnClickListener(v -> {
            Drawable currentDrawable = holder.ivHeart.getBackground();
            Drawable redHeartDrawable = ContextCompat.getDrawable(context, R.drawable.heart_red);
            UserSingleton userSingleton = UserSingleton.getInstance();
            Long userId = userSingleton.getUserId();
            if (currentDrawable != null && currentDrawable.getConstantState().equals(redHeartDrawable.getConstantState())) {
                holder.ivHeart.setBackgroundResource(R.drawable.heart);
                if (userId != null) {
                    addToWishlist(selectedbook.getId(), userId);
                } else {
                    Intent intent = new Intent(context.getApplicationContext(), LoginActivity.class);
                    context.startActivity(intent);
                }
            } else {
                holder.ivHeart.setBackgroundResource(R.drawable.heart_red);
                if (userId != null) {
                    addToWishlist(selectedbook.getId(), userId);
                } else {
                    Intent intent = new Intent(context.getApplicationContext(), LoginActivity.class);
                    context.startActivity(intent);
                }

            }
        });

    }


    @Override
    public int getItemCount() {
        if (books != null) {
            return books.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvBookTitle, tvPubDate;
        ImageView ivBookCover, ivHeart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookTitle = itemView.findViewById(R.id.tvBookTitle);
            tvPubDate = itemView.findViewById(R.id.tvPubDate);
            ivBookCover = itemView.findViewById(R.id.ivBookCover);
            ivHeart = itemView.findViewById(R.id.ivHeart);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;

        public DownloadImageTask(ImageView imageView) {
            imageViewReference = new WeakReference<>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String imageUrl = params[0];
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (imageViewReference != null && result != null) {
                ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(result);
                }
            }
        }
    }


    private void addToWishlist(Long bookId, Long userId) {
        wishlistApi.addToWishlist(bookId, userId).enqueue(new Callback<BaseRespose<WishlistItem>>() {
            @Override
            public void onResponse(Call<BaseRespose<WishlistItem>> call, Response<BaseRespose<WishlistItem>> response) {
                BaseRespose<WishlistItem> res = response.body();
                if (res != null) {
                    Toast.makeText(context.getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseRespose<WishlistItem>> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
