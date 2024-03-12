package com.example.book_reviews.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_reviews.R;
import com.example.book_reviews.api.user.IUserApi;
import com.example.book_reviews.api.user.UserApi;
import com.example.book_reviews.dtos.RegisterDTO;
import com.example.book_reviews.response.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    IUserApi userApi = new UserApi();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextView loginPage = (TextView) findViewById(R.id.btnRLogin);
        Button btnRegister = findViewById(R.id.btnRegister);
        EditText edtUserName = findViewById(R.id.edtUserName);
        EditText edtEmail = findViewById(R.id.edtEmail);
        EditText edtPassword = findViewById(R.id.edtPassword);
        EditText edtRetypePassword = findViewById(R.id.edtRetypePassword);

        loginPage.setOnClickListener((view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }));

        btnRegister.setOnClickListener((v -> {
            RegisterDTO registerDTO = new RegisterDTO(
                    edtUserName.getText().toString(),
                    edtEmail.getText().toString(),
                    edtPassword.getText().toString(),
                    edtRetypePassword.getText().toString());
            fetctApi(registerDTO);
        }));
    }

    private void fetctApi(RegisterDTO registerDTO) {
        userApi.register(registerDTO).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Toast.makeText(RegisterActivity.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                LoginResponse loginResponse = response.body();
                Toast.makeText(RegisterActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Loi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
