package com.app.itclao.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.app.itclao.Constant;
import com.app.itclao.HomeActivity;
import com.app.itclao.pos.PosActivity;
import com.app.itclao.table.MtableActivity;
import com.app.onlinesmartpos.R;
import com.app.itclao.model.Login;
import com.app.itclao.networking.ApiClient;
import com.app.itclao.networking.ApiInterface;
import com.app.itclao.utils.BaseActivity;
import com.app.itclao.utils.Utils;
import static com.app.itclao.ClassLibs.shopEmail;
import static com.app.itclao.ClassLibs.Username;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.Tel;
import static com.app.itclao.ClassLibs.Fname;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    EditText etxtEmail, etxtPassword;
    TextView txtLogin;
    SharedPreferences sp;
    ProgressDialog loading;
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        etxtEmail = findViewById(R.id.etxt_email);
        etxtPassword = findViewById(R.id.etxt_password);
        txtLogin = findViewById(R.id.txt_login);
        utils = new Utils();

        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String email = sp.getString(Constant.SP_EMAIL, "");
        String password = sp.getString(Constant.SP_PASSWORD, "");

        etxtEmail.setText(email);
        etxtPassword.setText(password);


        if (email.length() >= 3 && password.length() >= 3) {
            if (utils.isNetworkAvailable(LoginActivity.this)) {
                login(email, password);
            }else {
                Toasty.error(this, R.string.no_network_connection, Toast.LENGTH_SHORT).show();
            }
        }


        txtLogin.setOnClickListener(v -> {
            String User_ID = etxtEmail.getText().toString().trim();
            String Password = etxtPassword.getText().toString().trim();
            if (User_ID.isEmpty()) {
                etxtEmail.setError("ກະລຸນາປ້ອນຊື່ຜູ້ໃຊ້");
                etxtEmail.requestFocus();
            } else if (Password.isEmpty()) {
                etxtPassword.setError("ກະລຸນາປ້ອນລະຫັດຜ່ານ");
                etxtPassword.requestFocus();
            } else {

                if (utils.isNetworkAvailable(LoginActivity.this)) {
                    login(User_ID, Password);
                } else {
                    Toasty.error(LoginActivity.this, "ກະລຸນາເຊື່ອມຕໍອີນເຕີເນັດ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    //login method
    private void login(String User_ID, String Password) {
        loading = new ProgressDialog(this);
        loading.setCancelable(false);
        loading.setMessage(getString(R.string.please_wait));
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Login> call = apiInterface.login(User_ID, Password);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(@NonNull Call<Login> call, @NonNull Response<Login> response) {

                if (response.body() != null && response.isSuccessful()) {
                    String value = response.body().getValue();
                    String message = response.body().getMassage();
                    shopEmail = response.body().getUserid();
                    stock_id = response.body().getStock_id();
                    Username = response.body().getUser_Name();
                    Fname = response.body().getFname();
                    Tel = response.body().getel();


                    if (shopEmail != null) {
                            loading.dismiss();
                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sp.edit();
                            //Adding values to editor
                            editor.putString(Constant.SP_EMAIL, User_ID);
                            editor.putString(Constant.SP_PASSWORD, Password);

                            //Saving values to Share preference
                            editor.apply();
                            Toasty.success(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                    } else {
                        loading.dismiss();
                        Toasty.error(LoginActivity.this, "ຊື່ຜູ້ໃຊ້ ແລະ ລະຫັດຜ່ານບໍຖືກຕ້ອງ!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    loading.dismiss();

                }
            }

            @Override
            public void onFailure(@NonNull Call<Login> call, @NonNull Throwable t) {

                loading.dismiss();

            }
        });
    }
}
