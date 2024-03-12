package com.example.book_reviews.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_IMAGE_GALLERY = 2;
    private List<Review> reviews = new ArrayList<>();
    private int numberResult = 0;
    private String typeSelected;
    IBookApi bookApi = new BookApi();
    IUserApi userApi = new UserApi();
    RecyclerView rcvListReview;
    ImageView ivFileSearch, ivHome, ivWishlist, ivProfile, ivTextSearch;
    TextView tvTotalReview;
    EditText edtSearchName;
    Spinner spinnerOptions;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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


    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void dispatchPickPictureIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, REQUEST_IMAGE_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == getActivity().RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = null;
            if (selectedImage != null) {
                cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            }
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = 0;
                columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                File file = new File(picturePath);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

                UserSingleton userSingleton = UserSingleton.getInstance();
                Long userId = userSingleton.getUserId();
                if (userId != null) {
                    fetchData(typeSelected, userId, filePart);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }


            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchPickPictureIntent();
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "CAMERA permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        // Khởi tạo RecyclerView từ rootView
        rcvListReview = rootView.findViewById(R.id.rcvListReview);
        ivFileSearch = rootView.findViewById(R.id.ivFileSearch);
        tvTotalReview = rootView.findViewById(R.id.tvTotalReview);
        ivTextSearch = rootView.findViewById(R.id.ivTextSearch);
        edtSearchName = rootView.findViewById(R.id.edtSearchName);
        spinnerOptions = rootView.findViewById(R.id.spinner_options);

        if (rcvListReview != null) {
            rcvListReview.setLayoutManager(new LinearLayoutManager(getActivity()));
            BookAdapter adapter = new BookAdapter(getActivity(), reviews);
            rcvListReview.setAdapter(adapter);
        } else {
            Log.e("SearchFragment", "RecyclerView not found in layout");
        }


        ivFileSearch.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            } else {
                dispatchPickPictureIntent();
            }
        });

        if (numberResult > 0) {
            if (tvTotalReview != null) {
                tvTotalReview.setText(String.valueOf(numberResult));
            }
        } else {
            tvTotalReview.setText("Không tìm thấy kết quả!");
        }


        String[] options = {"title", "author"};
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.spinner_item, options);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOptions.setAdapter(stringArrayAdapter);

        ivTextSearch.setOnClickListener(v -> {
            String name = edtSearchName.getText().toString();
            if (name.isEmpty()) {
                Toast.makeText(getActivity().getApplicationContext(), "Không được để trống!", Toast.LENGTH_SHORT).show();
                return;
            }
            UserSingleton userSingleton = UserSingleton.getInstance();
            Long userId = userSingleton.getUserId();
            if (userId != null) {
                getDataByName(typeSelected, userId, name);
            } else {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", getActivity().getApplicationContext().MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);
        UserSingleton userSingleton = UserSingleton.getInstance();
        String email = userSingleton.getEmail();


        if (token != null) {
            getCurrentUser(token);
            Toast.makeText(getActivity().getApplicationContext(), email, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity().getApplicationContext(), email, Toast.LENGTH_SHORT).show();
        }

        spinnerOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeSelected = options[position];
                Toast.makeText(getActivity().getApplicationContext(), "Tìm kiến bằng: " + typeSelected, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity().getApplicationContext(), "Phải chọn loại tìm kiếm", Toast.LENGTH_SHORT).show();
            }
        });


        return rootView;
    }

    private void fetchData(String type, Long userId, MultipartBody.Part file) {
        bookApi.getBookByImage(type, userId, file).enqueue(new Callback<SearchResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                Toast.makeText(getActivity().getApplicationContext(), "Thành công", Toast.LENGTH_SHORT).show();
                SearchResponse searchResponse = response.body();
                if (searchResponse != null && searchResponse.getData() != null) {
                    reviews.clear();
                    if (searchResponse.getData().getResults() != null) {
                        reviews.addAll(searchResponse.getData().getResults());
                    }
                    numberResult = Integer.parseInt(searchResponse.getData().getNumResults().toString());
                    tvTotalReview.setText(String.valueOf(numberResult));
                    Objects.requireNonNull(rcvListReview.getAdapter()).notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Lỗi", Toast.LTENGTH_SHORT).show();
            }
        });
    }

    private void getDataByName(String type, Long userId, String name) {
        bookApi.getBookByTitle(type, userId, name).enqueue(new Callback<SearchResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                Toast.makeText(getActivity().getApplicationContext(), "Thành công", Toast.LENGTH_SHORT).show();
                SearchResponse searchResponse = response.body();
                if (searchResponse != null && searchResponse.getData() != null) {
                    reviews.clear();
                    reviews.addAll(searchResponse.getData().getResults());
                    numberResult = Integer.parseInt(searchResponse.getData().getNumResults().toString());
                    tvTotalReview.setText(String.valueOf(numberResult));
                    Objects.requireNonNull(rcvListReview.getAdapter()).notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCurrentUser(String token) {
        userApi.getCurenntUser("Bearer " + token).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (loginResponse != null) {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", getActivity().getApplicationContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", loginResponse.getToken());
                    editor.apply();
                    UserSingleton userSingleton = UserSingleton.getInstance();
                    userSingleton.setUserId(loginResponse.getUser().getId());
                    userSingleton.setEmail(loginResponse.getUser().getEmail());
                    userSingleton.setCreatedAt(loginResponse.getUser().getCreatedAt());
                    userSingleton.setUpdatedAt(loginResponse.getUser().getUpdatedAt());
                    userSingleton.setUsername(loginResponse.getUser().getUsername());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Có lỗi khi lấy thông tin người dùng", Toast.LENGTH_SHORT).show();
            }
        });
    }

}