package com.app.itclao.table;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.MotionTelltales;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.app.itclao.Constant;
import com.app.itclao.adapter.PaymentAdapter;
import com.app.itclao.model.payment;
import com.app.itclao.networking.ApiClient;
import com.app.itclao.networking.ApiInterface;
import com.app.itclao.pos.PosActivity;
import com.app.itclao.pos.ScannerActivity;
import com.app.itclao.tbroute.TbrouteActivity;
import com.app.itclao.utils.BaseActivity;
import com.app.itclao.utils.Utils;
import com.app.onlinesmartpos.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.app.itclao.ClassLibs.shopEmail;
import static com.app.itclao.ClassLibs.Username;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.Tel;
public class Mtable2Activity extends BaseActivity {
    private RecyclerView recyclerView;
    ImageView imgNoProduct,imgScanner;

    public static EditText etxtSearch;
    FloatingActionButton fabAdd;
    private ShimmerFrameLayout mShimmerViewContainer;
    SwipeRefreshLayout mSwipeRefreshLayout;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String User_ID;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table2);
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setTitle("ປະຫວັດການຂາຍ");


      // etxtSearch.setText(email);
      // getCustomerData();

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mSwipeRefreshLayout =findViewById(R.id.swipeToRefresh);
        //set color of swipe refresh
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        recyclerView = findViewById(R.id.recycler_view);
        imgNoProduct = findViewById(R.id.image_no_product);
        etxtSearch = findViewById(R.id.etxt_search);
        imgScanner = findViewById(R.id.img_scanner);

        fabAdd = findViewById(R.id.fab_add);


        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();

        String email = sp.getString(Constant.SP_EMAIL, "");
        User_ID = sp.getString(Constant.USERID, "");
        getCustomerData(email);
        //etxtSearch.setText(User_ID);

        imgScanner.setOnClickListener(v -> {
            Intent intent = new Intent(Mtable2Activity.this, ScannerActivity.class);
            startActivity(intent);
        });
        // set a GridLayoutManager with default vertical orientation and 3 number of columns
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
        recyclerView.setHasFixedSize(true);

        Utils utils=new Utils();
        getCustomerData("");
        //swipe refresh listeners
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            if (utils.isNetworkAvailable(Mtable2Activity.this)) {
                getCustomerData("");
            } else {
                Toasty.error(Mtable2Activity.this, R.string.no_network_connection, Toast.LENGTH_SHORT).show();
            }
            //after shuffle id done then swife refresh is off
            mSwipeRefreshLayout.setRefreshing(false);
        });


        if (utils.isNetworkAvailable(Mtable2Activity.this)) {
            //Load data from server
            getCustomerData("");
        } else {
            recyclerView.setVisibility(View.GONE);
            imgNoProduct.setVisibility(View.VISIBLE);
            imgNoProduct.setImageResource(R.drawable.not_found);
            mSwipeRefreshLayout.setVisibility(View.GONE);
            //Stopping Shimmer Effects
            mShimmerViewContainer.stopShimmer();
            mShimmerViewContainer.setVisibility(View.GONE);
            Toasty.error(this, R.string.no_network_connection, Toast.LENGTH_SHORT).show();
        }


        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mtable2Activity.this, TbrouteActivity.class);
                startActivity(intent);
            }
        });


        etxtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("data", s.toString());
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 1) {
                    //search data from server
                    getCustomerData(s.toString());
                } else {
                    getCustomerData("");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                Log.d("data", s.toString());
            }
        });


    }



    public void getCustomerData(String searchText) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<payment>> call;
        call = apiInterface.get_payment(searchText,User_ID.toString());
        call.enqueue(new Callback<List<payment>>() {
            @Override
            public void onResponse(@NonNull Call<List<payment>> call, @NonNull Response<List<payment>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<payment> customerList;
                    customerList = response.body();
                    if (customerList.isEmpty()) {
                        recyclerView.setVisibility(View.GONE);
                        imgNoProduct.setVisibility(View.VISIBLE);
                        imgNoProduct.setImageResource(R.drawable.not_found);
                        //Stopping Shimmer Effects
                        mShimmerViewContainer.stopShimmer();
                        mShimmerViewContainer.setVisibility(View.GONE);
                    } else {
                        //Stopping Shimmer Effects
                        mShimmerViewContainer.stopShimmer();
                        mShimmerViewContainer.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        imgNoProduct.setVisibility(View.GONE);
                        PaymentAdapter paymentAdapter = new PaymentAdapter(Mtable2Activity.this, customerList);
                        recyclerView.setAdapter(paymentAdapter);
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<payment>> call, @NonNull Throwable t) {
                Toast.makeText(Mtable2Activity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                Log.d("Error : ", t.toString());
            }
        });
    }





    //for back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
