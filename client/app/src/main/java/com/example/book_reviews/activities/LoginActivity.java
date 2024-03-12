package com.example.book_reviews.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.book_reviews.R;
import com.example.book_reviews.api.user.IUserApi;
import com.example.book_reviews.api.user.UserApi;
import com.example.book_reviews.dtos.LoginDTO;
import com.example.book_reviews.response.LoginResponse;
import com.example.book_reviews.singleton.UserSingleton;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Call;

public class LoginActivity extends AppCompatActivity {
    IUserApi userApi = new UserApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText username = findViewById(R.id.editTextUserName);
        EditText password = findViewById(R.id.editTextPassword);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener((v) -> {
            LoginDTO LoginDTO = new LoginDTO(String.valueOf(username.getText()), String.valueOf(password.getText()));
            callApi(LoginDTO);
        });
    }

    private void callApi(LoginDTO loginDTO) {
        userApi.login(loginDTO).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Toast.makeText(LoginActivity.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                LoginResponse loginResponse = response.body();
                if (loginResponse != null) {
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", loginResponse.getToken());
                    editor.apply();
                    UserSingleton userSingleton = UserSingleton.getInstance();
                    userSingleton.setUserId(loginResponse.getUser().getId());
                    userSingleton.setEmail(loginResponse.getUser().getEmail());
                    userSingleton.setCreatedAt(loginResponse.getUser().getCreatedAt());
                    userSingleton.setUpdatedAt(loginResponse.getUser().getUpdatedAt());
                    userSingleton.setUsername(loginResponse.getUser().getUsername());
                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Loi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}