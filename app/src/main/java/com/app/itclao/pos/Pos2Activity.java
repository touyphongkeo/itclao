package com.app.itclao.pos;

import static com.app.itclao.ClassLibs.Tbname;
import static com.app.itclao.ClassLibs.SALE_BILL;
import static com.app.itclao.ClassLibs.stock_id;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.itclao.Constant;
import com.app.itclao.HomeActivity;
import com.app.itclao.adapter.Catgory2Adapter;
import com.app.itclao.adapter.Product2Adapter;
import com.app.itclao.database.DatabaseAccess;
import com.app.itclao.model.Catgory;
import com.app.itclao.model.Product;
import com.app.itclao.networking.ApiClient;
import com.app.itclao.networking.ApiInterface;
import com.app.itclao.utils.BaseActivity;
import com.app.itclao.utils.Utils;
import com.app.onlinesmartpos.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.app.itclao.ClassLibs.shopEmail;
import static com.app.itclao.ClassLibs.Username;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.Tel;
public class Pos2Activity extends BaseActivity {
    private TextView namess,table,txtNoProducts;
    private ImageView img_back,img_cart;
    private Context context;

    SharedPreferences sp;
    private RecyclerView recyclerView,recycler_views;
    ImageView imgNoProduct;
    private ShimmerFrameLayout mShimmerViewContainer;
    SwipeRefreshLayout swipeToRefreshs;

    public static TextView txtCount;
    DatabaseAccess databaseAccess;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos2);
        getSupportActionBar().setHomeButtonEnabled(true); //ຄຳສັງກັບຄືນ
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//ຄຳສັງກັບຄືນຂອງປຸ້ມກັບ
        getSupportActionBar().hide();

        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        namess = findViewById(R.id.namess);
        img_back = findViewById(R.id.img_back);
        namess.setText("ເລກໂຕະ: "  +Tbname);
        table = findViewById(R.id.table);
        table.setText(SALE_BILL);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        swipeToRefreshs =findViewById(R.id.swipeToRefreshs);
        txtCount = findViewById(R.id.txt_count);
        img_cart = findViewById(R.id.img_cart);
        recyclerView = findViewById(R.id.recycler_view);
        recycler_views = findViewById(R.id.recycler_views);
        imgNoProduct = findViewById(R.id.image_no_product);
        txtNoProducts = findViewById(R.id.txt_no_products);
        getCatgory();
        databaseAccess = DatabaseAccess.getInstance(Pos2Activity.this);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Pos2Activity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });


        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Pos2Activity.this, Product2Cart.class);
                startActivity(i);
                finish();
            }
        });



          GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
          recycler_views.setLayoutManager(gridLayoutManager);
          recycler_views.setHasFixedSize(true);


          LinearLayoutManager linerLayoutManager = new LinearLayoutManager(Pos2Activity.this,LinearLayoutManager.HORIZONTAL,false);
          recyclerView.setLayoutManager(linerLayoutManager);
          recyclerView.setHasFixedSize(true);
          Utils utils=new Utils();



        databaseAccess.open();
        int count = databaseAccess.getCartItemCount();
        if (count == 0) {
            txtCount.setVisibility(View.INVISIBLE);
        } else {
            txtCount.setVisibility(View.VISIBLE);
            txtCount.setText(String.valueOf(count));
        }

//====================================select product============================================================
        swipeToRefreshs.setOnRefreshListener(() -> {
            if (utils.isNetworkAvailable(Pos2Activity.this)) {
            //    getProduct();
            } else{
                Toasty.error(Pos2Activity.this, R.string.no_network_connection, Toast.LENGTH_SHORT).show();
            }
            swipeToRefreshs.setRefreshing(false);
        });
        if (utils.isNetworkAvailable(Pos2Activity.this)) {
         //   getProduct();
        }else {
            recycler_views.setVisibility(View.GONE);
            imgNoProduct.setVisibility(View.VISIBLE);
            imgNoProduct.setImageResource(R.drawable.not_found);
            swipeToRefreshs.setVisibility(View.GONE);
            mShimmerViewContainer.stopShimmer();
            mShimmerViewContainer.setVisibility(View.GONE);
            Toasty.error(this, R.string.no_network_connection, Toast.LENGTH_SHORT).show();
        }
    }


    public void getCatgory() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Catgory>> call;
        call = apiInterface.get_catgory();
        call.enqueue(new Callback<List<Catgory>>() {
            @Override
            public void onResponse(@NonNull Call<List<Catgory>> call, @NonNull Response<List<Catgory>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Catgory> Catgorylist;
                    Catgorylist = response.body();
                    if (Catgorylist.isEmpty()) {
                        imgNoProduct.setImageResource(R.drawable.not_found);
                    } else {
                        Catgory2Adapter catgory2Adapter = new Catgory2Adapter(Pos2Activity.this, Catgorylist,recycler_views, imgNoProduct, txtNoProducts, mShimmerViewContainer);
                        recyclerView.setAdapter(catgory2Adapter);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Catgory>> call, @NonNull Throwable t) {
                Toast.makeText(Pos2Activity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                Log.d("Error : ", t.toString());
            }
        });
    }



/*

    public void getProduct() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Product>> call;
        call = apiInterface.get_products();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> customerList;
                    customerList = response.body();
                    if (customerList.isEmpty()) {
                        recycler_views.setVisibility(View.GONE);
                        imgNoProduct.setVisibility(View.VISIBLE);
                        imgNoProduct.setImageResource(R.drawable.not_found);
                        //Stopping Shimmer Effects
                        mShimmerViewContainer.stopShimmer();
                        mShimmerViewContainer.setVisibility(View.GONE);
                    } else {
                        //Stopping Shimmer Effects
                        mShimmerViewContainer.stopShimmer();
                        mShimmerViewContainer.setVisibility(View.GONE);
                        recycler_views.setVisibility(View.VISIBLE);
                        imgNoProduct.setVisibility(View.GONE);
                        Product2Adapter product2Adapter = new Product2Adapter(Pos2Activity.this, customerList);
                        recycler_views.setAdapter(product2Adapter);
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                Toast.makeText(Pos2Activity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                Log.d("Error : ", t.toString());
            }
        });






        //get count order id



    }//ກັບຄືນ
*/


    //login method

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
            return true;}
        return super.onOptionsItemSelected(item);
    }
}
