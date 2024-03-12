package com.example.book_reviews.adapters;

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
import com.example.book_reviews.api.wishlist.IWishlistApi;
import com.example.book_reviews.api.wishlist.WishlistApi;
import com.example.book_reviews.response.BaseRespose;
import com.example.book_reviews.response.Book;
import com.example.book_reviews.response.WishlishResponse;
import com.example.book_reviews.response.WishlistItem;
import com.example.book_reviews.singleton.UserSingleton;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {
    private Context context;
    List<WishlishResponse.WishlistData> wishlistData;
    IWishlistApi wishlistApi = new WishlistApi();

    public WishlistAdapter(Context applicationContext, List<WishlishResponse.WishlistData> wishlistData) {
        this.context = applicationContext;
        this.wishlistData = wishlistData;
    }

    @NonNull
    @Override
    public WishlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
        return new WishlistAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdapter.ViewHolder holder, int position) {
        WishlishResponse.WishlistData wishlistDataSelected = wishlistData.get(position);

        if (wishlistDataSelected.getBook().getName().length() >= 20) {
            String bookTitle = (wishlistDataSelected.getBook().getName().substring(0, 20));
            holder.tvBookTitle.setText(bookTitle);
        } else {
            holder.tvBookTitle.setText(wishlistDataSelected.getBook().getName());
        }

        String imageUrl = wishlistDataSelected.getBook().getImage();
        new WishlistAdapter.DownloadImageTask(holder.ivBookCover).execute(imageUrl);
        holder.tvPubDate.setText(wishlistDataSelected.getBook().getFormattedPublicationDate());
        holder.ivHeart.setOnClickListener(v -> {
            Drawable currentDrawable = holder.ivHeart.getBackground();
            Drawable redHeartDrawable = ContextCompat.getDrawable(context, R.drawable.heart_red);
            UserSingleton userSingleton = UserSingleton.getInstance();
            Long userId = userSingleton.getUserId();
            if (currentDrawable != null && currentDrawable.getConstantState().equals(redHeartDrawable.getConstantState())) {
                holder.ivHeart.setBackgroundResource(R.drawable.heart);
                if (userId != null) {
                    addToWishlist(wishlistDataSelected.getBook().getId(), userId);
                } else {
                    Intent intent = new Intent(context.getApplicationContext(), LoginActivity.class);
                    context.startActivity(intent);
                }
            } else {
                holder.ivHeart.setBackgroundResource(R.drawable.heart_red);
                if (userId != null) {
                    addToWishlist(wishlistDataSelected.getBook().getId(), userId);
                } else {
                    Intent intent = new Intent(context.getApplicationContext(), LoginActivity.class);
                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return wishlistData.size();
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

}
