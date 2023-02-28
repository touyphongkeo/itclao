package com.app.itclao.imagetwo;

import static com.app.itclao.ClassLibs.Customer_type;
import static com.app.itclao.ClassLibs.UNit;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.itclao.Constant;
import com.app.itclao.database.DatabaseAccess;
import com.app.itclao.model.showimage;
import com.app.itclao.networking.ApiClient;
import com.app.itclao.networking.ApiInterface;
import com.app.itclao.pos.PosActivity;
import com.app.itclao.prodcutpos.PsosActivity;
import com.app.itclao.utils.BaseActivity;
import com.app.onlinesmartpos.R;
import com.bumptech.glide.Glide;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.app.itclao.ClassLibs.shopEmail;
import static com.app.itclao.ClassLibs.Username;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.Tel;
public class Get_imagestwoshow extends BaseActivity {
    CircleImageView imgProduct;
    String productID,stock_id;
    ProgressDialog loading;
    TextView pro_name,price,qty,txt_saved,check_qty;
    ProgressBar progressBar;
    DatabaseAccess databaseAccess;
    String prices1,prices2,prices3,productName,images;
    EditText txt_qty;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showimagetwo);
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setTitle("ຂໍ້ມູນສີນຄ້າ");

        imgProduct = findViewById(R.id.image_product);
        pro_name = findViewById(R.id.pro_name);
        price = findViewById(R.id.price);
        qty = findViewById(R.id.qty);
        txt_saved = findViewById(R.id.txt_saved);
        check_qty = findViewById(R.id.check_qty);
        txt_qty = findViewById(R.id.txt_qty);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        productID = getIntent().getExtras().getString(Constant.PRODUCT_ID);
        stock_id = getIntent().getExtras().getString(Constant.STOCK_ID);
        getProductsData(productID,stock_id);

        databaseAccess = DatabaseAccess.getInstance(Get_imagestwoshow.this);

        txt_saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           insertorder();
                //Toast.makeText(Get_imagestwoshow.this, "sdfsfdsf", Toast.LENGTH_SHORT).show();
            }
        });
    }




    public void getProductsData(String product_id, String stock_id) {
        Log.d("ProductID",product_id);
        Log.d("stock_id",stock_id);
        loading=new ProgressDialog(Get_imagestwoshow.this);
        loading.setCancelable(false);
        loading.setMessage(getString(R.string.please_wait));
        loading.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<showimage>> call;
        call = apiInterface.getProductById2(product_id,stock_id);

        call.enqueue(new Callback<List<showimage>>() {
            @Override
            public void onResponse(@NonNull Call<List<showimage>> call, @NonNull Response<List<showimage>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<showimage> productData;
                    productData = response.body();
                    loading.dismiss();
                    if (productData.isEmpty()) {
                        Toasty.warning(Get_imagestwoshow.this, R.string.no_product_found, Toast.LENGTH_SHORT).show();
                    } else {
                        productName = productData.get(0).getProduct_Namesssss();
                        prices1 = productData.get(0).getss1_price();
                        prices2 = productData.get(0).getsss2_price();
                        prices3 = productData.get(0).gets3_prices();
                        images = productData.get(0).getimgs();
                        String qtys = productData.get(0).getqtyds();
                        String productImage = productData.get(0).getimgs();
                        String imageUrl= Constant.PRODUCT_IMAGE_URL+productImage;

                        if (productImage != null) {
                            if (productImage.length() < 3) {

                                imgProduct.setImageResource(R.drawable.image_placeholder);
                            } else {


                                Glide.with(Get_imagestwoshow.this)
                                        .load(imageUrl)
                                        .placeholder(R.drawable.loading)
                                        .error(R.drawable.image_placeholder)
                                        .into(imgProduct);
                            }
                        }

                        pro_name.setText(productName);

                        if (Customer_type.equals("001")){
                            DecimalFormat dfs = new DecimalFormat(
                                    "#,###.00",
                                    new DecimalFormatSymbols(new Locale("pt", "BR")));
                            BigDecimal values1 = new BigDecimal(prices1);
                            price.setText("ລາຄາ: "+values1+" ກິບ");

                        }else if (Customer_type.equals("002")){
                            DecimalFormat dfs = new DecimalFormat(
                                    "#,###.00",
                                    new DecimalFormatSymbols(new Locale("pt", "BR")));
                            BigDecimal values2 = new BigDecimal(prices2);
                            price.setText("ລາຄາ: "+values2+" ກິບ");

                        }else {
                            DecimalFormat dfs = new DecimalFormat(
                                    "#,###.00",
                                    new DecimalFormatSymbols(new Locale("pt", "BR")));
                            BigDecimal values3 = new BigDecimal(prices3);
                            price.setText("ລາຄາ: "+values3+" ກິບ");

                        }


                        qty.setText("ຈຳນວນໃນສາງ: "+qtys);
                        int bb = Integer.parseInt(qtys);
                        int aa = 5;
                        if (bb>aa){
                            check_qty.setText("ຈຳນວນ: "+bb);
                            check_qty.setBackgroundColor(Color.parseColor("#FB4106"));
                        }else {
                            check_qty.setText(" ຈຳນວນໃນສາງ: "+qtys);
                            check_qty.setBackgroundColor(Color.parseColor("#FB4106"));
                        }



                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<showimage>> call, @NonNull Throwable t) {

                loading.dismiss();
                Toast.makeText(Get_imagestwoshow.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                Log.d("Error : ", t.toString());
            }
        });
    }

    private void insertorder() {
        String txtqty = txt_qty.getText().toString();
        double vvqty = Double.parseDouble(txtqty);

       // if (Customer_type.equals("001")){

        try {
            double vprices = Double.parseDouble(prices1);
            double bbaount1 = vprices*vvqty;
            databaseAccess.open();
            //int check = databaseAccess.addToCart(sale_bill,sale_date,Tbname,product_id,product_name,price,cust.toString(),sale_status,edit_sale,username,Image,cut_qty);
            int check = databaseAccess.additem_productorder(stock_id,productID,prices1,txtqty,bbaount1,productName,UNit,images);

            if (check == 1) {
                Toasty.success(Get_imagestwoshow.this, "ເພີ່ມເຂົ້າໃນຕະກາສຳເລັດ", Toast.LENGTH_SHORT).show();
                  Intent i = new Intent(Get_imagestwoshow.this, PsosActivity.class);
                  startActivity(i);
            } else if (check == 2) {
                Toasty.warning(Get_imagestwoshow.this, "ສີນຄ້ານີ້ໃນຕາກາມີແລ້ວ", Toast.LENGTH_SHORT).show();
            } else {
                Toasty.error(Get_imagestwoshow.this, "ຜິດຜາດ", Toast.LENGTH_SHORT).show();
            }

            databaseAccess.open();
            int count=databaseAccess.getitem_productorder();
            if (count==0) {
                PosActivity.txtCount.setVisibility(View.INVISIBLE);
            } else{
                PosActivity. txtCount.setVisibility(View.VISIBLE);
                PosActivity.txtCount.setText(String.valueOf(count));
            }
        }catch (Exception e){

        }




   /*
        }else if (Customer_type.equals("002")){
            double vprices = Double.parseDouble(prices2);
            double bbaount2 = vprices*vvqty;
            databaseAccess.open();
            //int check = databaseAccess.addToCart(sale_bill,sale_date,Tbname,product_id,product_name,price,cust.toString(),sale_status,edit_sale,username,Image,cut_qty);
            int check = databaseAccess.additem_productorder(stock_id,productID,prices2,txtqty,bbaount2,productName,UNit,images);
            databaseAccess.open();
            int count=databaseAccess.getitem_productorder();
            if (count==0) {
                PosActivity.txtCount.setVisibility(View.INVISIBLE);
            } else{
                PosActivity. txtCount.setVisibility(View.VISIBLE);
                PosActivity.txtCount.setText(String.valueOf(count));
            }
            if (check == 1) {
                Toasty.success(Get_imagestwoshow.this, "ເພີ່ມເຂົ້າໃນຕະກາສຳເລັດ", Toast.LENGTH_SHORT).show();
             //   Intent i = new Intent(Get_imageshow.this,PosActivity.class);
             //   startActivity(i);
            } else if (check == 2) {
                Toasty.warning(Get_imagestwoshow.this, "ສີນຄ້ານີ້ໃນຕາກາມີແລ້ວ", Toast.LENGTH_SHORT).show();
            } else {
                Toasty.error(Get_imagestwoshow.this, "ຜິດຜາດ", Toast.LENGTH_SHORT).show();
            }
        }else {
            double vprices = Double.parseDouble(prices3);
            double bbaount3 = vprices*vvqty;
            databaseAccess.open();
            //int check = databaseAccess.addToCart(sale_bill,sale_date,Tbname,product_id,product_name,price,cust.toString(),sale_status,edit_sale,username,Image,cut_qty);
            int check = databaseAccess.additem_productorder(stock_id,productID,prices3,txtqty,bbaount3,productName,UNit,images);
            databaseAccess.open();
            int count=databaseAccess.getitem_productorder();
            if (count==0) {
                PosActivity.txtCount.setVisibility(View.INVISIBLE);
            } else{
                PosActivity. txtCount.setVisibility(View.VISIBLE);
                PosActivity.txtCount.setText(String.valueOf(count));
            }
            if (check == 1) {
                Toasty.success(Get_imagestwoshow.this, "ເພີ່ມເຂົ້າໃນຕະກາສຳເລັດ", Toast.LENGTH_SHORT).show();
              //  Intent i = new Intent(Get_imageshow.this,PosActivity.class);
              //  startActivity(i);
            } else if (check == 2) {
                Toasty.warning(Get_imagestwoshow.this, "ສີນຄ້ານີ້ໃນຕາກາມີແລ້ວ", Toast.LENGTH_SHORT).show();
            } else {
                Toasty.error(Get_imagestwoshow.this, "ຜິດຜາດ", Toast.LENGTH_SHORT).show();
            }
        }*/


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
