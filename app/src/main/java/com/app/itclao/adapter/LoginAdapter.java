package com.app.itclao.adapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.itclao.database.DatabaseAccess;
import com.app.itclao.model.Catgory;
import com.app.itclao.model.Login;
import com.app.itclao.model.Product;
import com.app.itclao.networking.ApiClient;
import com.app.itclao.networking.ApiInterface;
import com.app.itclao.utils.Utils;
import com.app.onlinesmartpos.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.itclao.ClassLibs.Userid;

import static com.app.itclao.ClassLibs.shopEmail;
import static com.app.itclao.ClassLibs.Username;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.Tel;
public class LoginAdapter extends RecyclerView.Adapter<LoginAdapter.MyViewHolder> {
    private List<Login> customerData;
    private Context context;
    Utils utils;

    RecyclerView recycler_views;
    ImageView imgNoProduct;
    TextView txtNoProducts;
    private ShimmerFrameLayout mShimmerViewContainer;


    public LoginAdapter(Context context, List<Login> customerData, RecyclerView recycler_views, ImageView imgNoProduct, TextView txtNoProducts, ShimmerFrameLayout mShimmerViewContainer) {
        this.context = context;
        this.customerData = customerData;
        utils=new Utils();
        this.recycler_views=recycler_views;
        this.imgNoProduct=imgNoProduct;
        this.txtNoProducts=txtNoProducts;
        this.mShimmerViewContainer=mShimmerViewContainer;



    }


    @NonNull
    @Override
    public LoginAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final LoginAdapter.MyViewHolder holder, int position) {
        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        final String userid = customerData.get(position).getUserid();
    }



    @Override
    public int getItemCount() {
        return customerData.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_category_name;
        CardView card_category;
        LinearLayout layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_category_name = itemView.findViewById(R.id.txt_category_name);
            card_category = itemView.findViewById(R.id.card_category);
            layout = itemView.findViewById(R.id.layout);


            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
//            Intent i = new Intent(context, EditCustomersActivity.class);
               Userid= customerData.get(getAdapterPosition()).getUserid();
//            i.putExtra("customer_name", customerData.get(getAdapterPosition()).getCustomerName());
//            i.putExtra("customer_cell", customerData.get(getAdapterPosition()).getCustomerCell());
//            i.putExtra("customer_email", customerData.get(getAdapterPosition()).getCustomerEmail());
//            i.putExtra("customer_address", customerData.get(getAdapterPosition()).getCustomerAddress());
//            context.startActivity(i);
        }
    }




//    public void getProductsData(String categoryId) {
//        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//        Call<List<Product>> call;
//        call = apiInterface.get_catgoryId(categoryId);
//
//        call.enqueue(new Callback<List<Product>>() {
//            @Override
//            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
//
//
//                if (response.isSuccessful() && response.body() != null) {
//                    List<Product> productsList;
//                    productsList = response.body();
//                    if (productsList.isEmpty()) {
//                        recycler_views.setVisibility(View.GONE);
//                        imgNoProduct.setVisibility(View.VISIBLE);
//                        imgNoProduct.setImageResource(R.drawable.not_found);
//                        //Stopping Shimmer Effects
//                        mShimmerViewContainer.stopShimmer();
//                        mShimmerViewContainer.setVisibility(View.GONE);
//
//
//                    } else {
//
//
//                        //Stopping Shimmer Effects
//                        mShimmerViewContainer.stopShimmer();
//                        mShimmerViewContainer.setVisibility(View.GONE);
//
//                        recycler_views.setVisibility(View.VISIBLE);
//                        imgNoProduct.setVisibility(View.GONE);
//                        ProductAdapter productAdapter = new ProductAdapter(context, productsList);
//                        recycler_views.setAdapter(productAdapter);
//
//                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
//
//                Toast.makeText(context, R.string.something_went_wrong +"OK", Toast.LENGTH_SHORT).show();
//                Log.d("Error : ", t.toString());
//            }
//        });
//
//
//    }



}
