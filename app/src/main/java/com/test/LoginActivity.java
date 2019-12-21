package com.test;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText etEmail, etPassword;
    private Button banContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            if (true) {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
        InItId();
        banContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etEmail.getText().toString().equals("") && etEmail.getText().toString() != null) {
                    if (!etPassword.getText().toString().equals("") && etPassword.getText().toString() != null) {
                        loginAuth(etEmail.getText().toString(), etPassword.getText().toString());
                    } else {
                        Toast.makeText(LoginActivity.this, getString(R.string.password_check), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.email_check), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void InItId() {
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        banContinue = findViewById(R.id.btn_continue);

    }

    private void loginAuth(String email, String password) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiClient api = retrofit.create(ApiClient.class);

//        Call<ResponseBody> call = api.login(new LoginModel("candidateapitest@gmail.com","Testing@123"));
        Call<ResponseBody> call = api.login(new LoginModel(email, password));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Toast.makeText(LoginActivity.this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(LoginActivity.this, ""+response.code(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });
    }
}
