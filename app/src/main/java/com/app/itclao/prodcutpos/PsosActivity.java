package com.app.itclao.prodcutpos;
import static com.app.itclao.ClassLibs.shopEmail;
import static com.app.itclao.ClassLibs.Username;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.Tel;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.Customer_type;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import com.app.itclao.adapter.CatgoryAdapter;
import com.app.itclao.adapter.CatgoryposAdapter;
import com.app.itclao.adapter.ProductAdapter;
import com.app.itclao.adapter.PsporoductAdapter;
import com.app.itclao.cartorder.ProductCartorder;
import com.app.itclao.database.DatabaseAccess;
import com.app.itclao.model.Catgory;
import com.app.itclao.model.Item;
import com.app.itclao.model.Product;
import com.app.itclao.model.Product2;
import com.app.itclao.networking.ApiClient;
import com.app.itclao.networking.ApiInterface;
import com.app.itclao.pos.PosActivity;
import com.app.itclao.pos.ProductCart;
import com.app.itclao.pos.ScannerposActivity;
import com.app.itclao.pos.SscannerposActivity;
import com.app.itclao.utils.BaseActivity;
import com.app.itclao.utils.Utils;
import com.app.onlinesmartpos.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import static com.app.itclao.ClassLibs.shopEmail;
import static com.app.itclao.ClassLibs.Username;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.Tel;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.app.itclao.ClassLibs.Customer_id;
public class PsosActivity extends BaseActivity {
    private TextView namess,table,txtNoProducts;
    private ImageView img_back,img_cart;
    private Context context;
    ProgressDialog loading;
    SharedPreferences sp;
    private RecyclerView recyclerView,recycler_views;
    ImageView imgNoProduct,imgScanner;
    private ShimmerFrameLayout mShimmerViewContainer;
    SwipeRefreshLayout swipeToRefreshs;

    SharedPreferences.Editor editor;
    public static TextView txtCount;
    DatabaseAccess databaseAccess;
    EditText txt_stocks,etxtProductCategory;
    public static EditText etxtSearch;

    String Id;

    List<Item> productCategory;
    ArrayAdapter<String> categoryAdapter;
    List<String> categoryNames;
    String selectedCategoryID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poss);
        getSupportActionBar().setHomeButtonEnabled(true); //ຄຳສັງກັບຄືນ
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//ຄຳສັງກັບຄືນຂອງປຸ້ມກັບ
        getSupportActionBar().hide();

        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        String email = sp.getString(Constant.SP_EMAIL, "");
        String password = sp.getString(Constant.SP_PASSWORD, "");



        getItem();

        String currentDate = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH).format(new Date());
        namess = findViewById(R.id.namess);
        img_back = findViewById(R.id.img_back);

        etxtSearch = findViewById(R.id.etxt_search);
        table = findViewById(R.id.table);
      //  txt_stocks = findViewById(R.id.txt_stock);
        etxtProductCategory = findViewById(R.id.etxt_product_category);


      //  txt_stocks.setText(stock_id);
       // namess.setText("ຂາຍ "+stock_id);
     //   table.setText(stock_id);
        getCatgory();
        getProduct("");
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        swipeToRefreshs =findViewById(R.id.swipeToRefreshs);
        txtCount = findViewById(R.id.txt_count);
        img_cart = findViewById(R.id.img_cart);
        recyclerView = findViewById(R.id.recycler_view);
        recycler_views = findViewById(R.id.recycler_views);
        imgNoProduct = findViewById(R.id.image_no_product);
        txtNoProducts = findViewById(R.id.txt_no_products);
        imgScanner = findViewById(R.id.img_scanner);
        databaseAccess = DatabaseAccess.getInstance(PsosActivity.this);
        databaseAccess.open();


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PsosActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });


        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(PsosActivity.this, ProductCartorder.class);
                startActivity(i);
                finish();
            }
        });


        imgScanner.setOnClickListener(v -> {
            Intent intent = new Intent(PsosActivity.this, SscannerposActivity.class);
            startActivity(intent);
        });



          GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
          recycler_views.setLayoutManager(gridLayoutManager);
          recycler_views.setHasFixedSize(true);


          LinearLayoutManager linerLayoutManager = new LinearLayoutManager(PsosActivity.this,LinearLayoutManager.HORIZONTAL,false);
          recyclerView.setLayoutManager(linerLayoutManager);
          recyclerView.setHasFixedSize(true);
          Utils utils=new Utils();



        databaseAccess.open();
        int count = databaseAccess.getitem_productorder();
        if (count == 0) {
            txtCount.setVisibility(View.INVISIBLE);
        } else {
            txtCount.setVisibility(View.VISIBLE);
            txtCount.setText(String.valueOf(count));
        }





        //ເລືອກລາຄາ
//        etxtProductCategory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                categoryAdapter = new ArrayAdapter<>(PosActivity.this, android.R.layout.simple_list_item_1);
//                categoryAdapter.addAll(categoryNames);
//
//                AlertDialog.Builder dialog = new AlertDialog.Builder(PosActivity.this);
//                View dialogView = getLayoutInflater().inflate(R.layout.dialog_list_search, null);
//                dialog.setView(dialogView);
//                dialog.setCancelable(false);
//
//                Button dialogButton = dialogView.findViewById(R.id.dialog_button);
//                EditText dialogInput = dialogView.findViewById(R.id.dialog_input);
//                TextView dialogTitle = dialogView.findViewById(R.id.dialog_title);
//                ListView dialogList = dialogView.findViewById(R.id.dialog_list);
//
//
//                dialogTitle.setText("ເລືອກລາຄາຂາຍ");
//                dialogList.setVerticalScrollBarEnabled(true);
//                dialogList.setAdapter(categoryAdapter);
//
//                dialogInput.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                        Log.d("data", s.toString());
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
//                        categoryAdapter.getFilter().filter(charSequence);
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//                        Log.d("data", s.toString());
//                    }
//                });
//
//
//                final AlertDialog alertDialog = dialog.create();
//
//                dialogButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        alertDialog.dismiss();
//                    }
//                });
//
//                alertDialog.show();
//
//
//                dialogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                        alertDialog.dismiss();
//                        final String selectedItem = categoryAdapter.getItem(position);
//
//                        String categoryId = "0";
//                        etxtProductCategory.setText(selectedItem);
//
//
//                        for (int i = 0; i < categoryNames.size(); i++) {
//                            if (categoryNames.get(i).equalsIgnoreCase(selectedItem)) {
//                                // Get the ID of selected Country
//                                categoryId = productCategory.get(i).getId();
//                            }
//                        }
//
//                        selectedCategoryID = categoryId;
//
//                    }
//                });
//            }
//        });







//====================================select product============================================================
        swipeToRefreshs.setOnRefreshListener(() -> {

            if (utils.isNetworkAvailable(PsosActivity.this)) {
                getProduct("");
            } else{
                Toasty.error(PsosActivity.this, R.string.no_network_connection, Toast.LENGTH_SHORT).show();
            }
            swipeToRefreshs.setRefreshing(false);
        });
        if (utils.isNetworkAvailable(PsosActivity.this)) {

            getProduct("");
        }else {
            getProduct("");
            recycler_views.setVisibility(View.GONE);
            imgNoProduct.setVisibility(View.VISIBLE);
            imgNoProduct.setImageResource(R.drawable.not_found);
            swipeToRefreshs.setVisibility(View.GONE);
            mShimmerViewContainer.stopShimmer();
            mShimmerViewContainer.setVisibility(View.GONE);
            Toasty.error(this, R.string.no_network_connection, Toast.LENGTH_SHORT).show();
        }




        etxtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("data", s.toString());
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 1) {
                    //search data from server
                    getProduct(s.toString());
                } else {
                    getProduct("");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                Log.d("data", s.toString());
            }

        });



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
                        CatgoryposAdapter catgoryposAdapter = new CatgoryposAdapter(PsosActivity.this, Catgorylist,recycler_views, imgNoProduct, txtNoProducts, mShimmerViewContainer);
                        recyclerView.setAdapter(catgoryposAdapter);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Catgory>> call, @NonNull Throwable t) {
                Toast.makeText(PsosActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                Log.d("Error : ", t.toString());
            }
        });
    }




    public void getProduct(String searchText) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Product2>> call;
        call = apiInterface.get_products2(searchText,stock_id.toString());
        call.enqueue(new Callback<List<Product2>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product2>> call, @NonNull Response<List<Product2>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product2> customerList;
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
                        PsporoductAdapter psporoductAdapter = new PsporoductAdapter(PsosActivity.this, customerList);
                        recycler_views.setAdapter(psporoductAdapter);
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Product2>> call, @NonNull Throwable t) {
                Toast.makeText(PsosActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                Log.d("Error : ", t.toString());
            }
        });
        //get count order id
    }







    public void getItem() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Item>> call;
        call = apiInterface.getItem();
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(@NonNull Call<List<Item>> call, @NonNull Response<List<Item>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productCategory = response.body();
                    String ID = productCategory.get(0).getId();
                    categoryNames = new ArrayList<>();
                    for (int i = 0; i < productCategory.size(); i++) {
                        categoryNames.add(productCategory.get(i).getName());


                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Item>> call, @NonNull Throwable t) {
                //write own action
            }
        });
    }

    //ກັບຄືນ


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
            return true;}
        return super.onOptionsItemSelected(item);
    }
}