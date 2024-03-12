package com.example.book_reviews.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
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
import com.example.book_reviews.api.wishlist.IWishlistApi;
import com.example.book_reviews.api.wishlist.WishlistApi;
import com.example.book_reviews.response.BaseRespose;
import com.example.book_reviews.response.LoginResponse;
import com.example.book_reviews.response.Review;
import com.example.book_reviews.response.WishlistItem;
import com.example.book_reviews.singleton.UserSingleton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private Context context;
    private List<Review> reviews;

    public BookAdapter(Context applicationContext, List<Review> reviews) {
        this.context = applicationContext;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.review_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review selectedReview = reviews.get(position);
        if (selectedReview.getByline().isEmpty()) {
            holder.tvNameReviwer.setText("Không có tên");
        } else {
            holder.tvNameReviwer.setText(selectedReview.getByline());

        }
        if (selectedReview.getSummary().isEmpty()) {
            holder.tvSummary.setText("Không có bản tóm tắt!");
        } else {
            holder.tvSummary.setText(selectedReview.getSummary());
        }
        holder.tvDateReview.setText(selectedReview.getFormattedPublicationDate());
        holder.tvBookName.setText(selectedReview.getBookTitle());
        holder.tvAuthor.setText(selectedReview.getBookAuthor());

//        holder.ivHeart.setOnClickListener(v -> {
//            Drawable currentDrawable = holder.ivHeart.getBackground();
//            Drawable redHeartDrawable = ContextCompat.getDrawable(context, R.drawable.heart_red);
//            if (currentDrawable != null && currentDrawable.getConstantState().equals(redHeartDrawable.getConstantState())) {
//                holder.ivHeart.setBackgroundResource(R.drawable.heart);
//            } else {
//                holder.ivHeart.setBackgroundResource(R.drawable.heart_red);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        if (reviews != null) {
            return reviews.size();
        }
        return 0;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNameReviwer, tvDateReview, tvSummary, tvAuthor, tvBookName;
        ImageView ivHeart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameReviwer = itemView.findViewById(R.id.tvNameReviwer);
            tvDateReview = itemView.findViewById(R.id.tvDateReview);
            tvSummary = itemView.findViewById(R.id.tvSummary);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvBookName = itemView.findViewById(R.id.tvBookName);
//            ivHeart = itemView.findViewById(R.id.ivHeart);
        }
    }



}
