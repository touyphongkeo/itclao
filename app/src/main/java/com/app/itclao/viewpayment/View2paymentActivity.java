package com.app.itclao.viewpayment;


import static com.app.itclao.ClassLibs.finvoiceId;
import static com.app.itclao.ClassLibs.Fname;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.print.PrintHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.itclao.Constant;
import com.app.itclao.HomeActivity;
import com.app.itclao.adapter.OrderDetails_orderAdapter;
import com.app.itclao.adapter.Viewpayment2Adapter;
import com.app.itclao.adapter.ViewpaymentAdapter;
import com.app.itclao.model.office;
import com.app.itclao.model.paymentorder;
import com.app.itclao.model.totaill;
import com.app.itclao.model.user;
import com.app.itclao.model.viewpaymoney;
import com.app.itclao.networking.ApiClient;
import com.app.itclao.networking.ApiInterface;
import com.app.itclao.pdf_report.TemplatePDF;
import com.app.onlinesmartpos.R;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.app.itclao.ClassLibs.stock_id;

public class View2paymentActivity extends AppCompatActivity {
    private RecyclerView cart_recyclerview;
    private Viewpayment2Adapter viewpayment2Adapter;
    String tt;
    TextView text_v2;
    String currency;
    TextView  txt_BillTotal;
    List<String> where = new ArrayList<String>();

    private TemplatePDF templatePDF;
    Bitmap bm = null;
    String customer;
    String users;

    RecyclerView recyclerView;

    List<user> DataUser;
    List<paymentorder> orderDetails;
   // List<totaill> orderDetailsss;
    ListView listView;
    View mView,mView1;
    CardView button1,button2;
    ProgressDialog loading;
    String CreateDate = "";
    String HH_YEAR = "";
    DecimalFormat f;
    public DecimalFormat f1;

    EditText txt_stocks;

    DecimalFormat formatter = new DecimalFormat("#,###,###");
    TextView textBill,text_v9,text_v7,textBillDate,usernaes,idsssss,barcode,telphon,onpay,text6;
    private OrderDetails_orderAdapter orderDetails_orderAdapter;

    String[] mobileArray = {"ທົດລອງ 01 2005 ","ທົດລອງ2","WindowsMobile","Blackberry","WebOS","Ubuntu","Windows7","Max OS X"};
    String[] listItem;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    ImageView imagebarcode,images;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view2payment);
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setTitle("ລາຍງານຕິດໜີ້");

        button1= findViewById(R.id.button1);
        button2= findViewById(R.id.button2);


        txt_stocks= findViewById(R.id.txt_stocks);
        f = new DecimalFormat("#,###,##0");


        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        String email = sp.getString(Constant.SP_EMAIL, "");

      //  Toast.makeText(this, email, Toast.LENGTH_SHORT).show();





        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View contentView1 = inflater.inflate(R.layout.activity_viewpay2, null);
        @SuppressLint("WrongViewCast") RelativeLayout view1 = (RelativeLayout) contentView1.findViewById(R.id.relatived);

        textBill = view1.findViewById(R.id.textBill);
        text_v2 = view1.findViewById(R.id.text_v2);
        text_v9 = view1.findViewById(R.id.text_v9);
        text_v7 = view1.findViewById(R.id.text_v7);
        textBillDate = view1.findViewById(R.id.textBillDate);
        usernaes = view1.findViewById(R.id.usernaes);
        idsssss = view1.findViewById(R.id.idsssss);
        barcode = view1.findViewById(R.id.barcode);
        telphon = view1.findViewById(R.id.telphon);
        imagebarcode = view1.findViewById(R.id.imagebarcode);
        images = view1.findViewById(R.id.images);
        onpay = view1.findViewById(R.id.onpay);
        text6 = view1.findViewById(R.id.text6);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(new Date());

        String currentTime = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date()); //HH:mm:ss a

        textBillDate.setText("ວັນທີ: "+currentDate+" ເວລາ: "+currentTime);
        onpay.setText("ວັນທີ: "+currentDate+"/"+currentTime);

        cart_recyclerview = view1.findViewById(R.id.cart_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(View2paymentActivity.this, LinearLayoutManager.VERTICAL, false);
        cart_recyclerview.setLayoutManager(linearLayoutManager);
        cart_recyclerview.setHasFixedSize(true);
        getProductsData(stock_id.toString());
        get_totaill(stock_id.toString());
     //   get_office();


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   getProductsData(finvoiceId.toString());
                get_office();
                textBill.setText("ເລກບິນ:"+finvoiceId.toString());
                barcode.setText(finvoiceId);



                view1.setDrawingCacheEnabled(true);
                view1.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                view1.layout(0, 0, view1.getMeasuredWidth(), view1.getMeasuredHeight());
                view1.buildDrawingCache(true);
                mView1 = view1;
                doPhotoPrint1();

            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(View2paymentActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });
    }



    private void doPhotoPrint1() {
        PrintHelper photoPrinter1 = new PrintHelper(this);
        photoPrinter1.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        Bitmap bitmap1 = mView1.getDrawingCache();
        photoPrinter1.printBitmap("image.png_test_print", bitmap1, new PrintHelper.OnPrintFinishCallback() {
            @Override
            public void onFinish() {
                changeOnCon();
            }
        });
    }



   public void getProductsData(String stock_id) {
       Log.d("stock_id",stock_id);
       loading=new ProgressDialog(View2paymentActivity.this);
       loading.setCancelable(false);
       loading.setMessage(getString(R.string.please_wait));
       loading.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<paymentorder>> call;
        call = apiInterface.get_dataorderpay(stock_id);
        call.enqueue(new Callback<List<paymentorder>>() {
            @Override
            public void onResponse(@NonNull Call<List<paymentorder>> call, @NonNull Response<List<paymentorder>> response) {
                if (response.isSuccessful() && response.body() != null) {
                   orderDetails = response.body();
                   loading.dismiss();
                   // get_office();
                    get_totaill(stock_id);
                    viewpayment2Adapter = new Viewpayment2Adapter(View2paymentActivity.this, orderDetails,telphon,usernaes,idsssss,text_v2,text_v7,text_v9);
                   cart_recyclerview.setAdapter(viewpayment2Adapter);
                   //  getProductsDatas(finvoiceId);
                   Toasty.success(View2paymentActivity.this, "Successfuly", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<paymentorder>> call, @NonNull Throwable t) {
                Toast.makeText(View2paymentActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                Log.d("Error : ", t.toString());
            }
        });
    }








    public void get_office() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<office>> call;
        call = apiInterface.get_office();

        call.enqueue(new Callback<List<office>>() {
            @Override
            public void onResponse(@NonNull Call<List<office>> call, @NonNull Response<List<office>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<office> productData;
                    productData = response.body();

                    if (productData.isEmpty()) {
                        Toasty.warning(View2paymentActivity.this, R.string.no_product_found, Toast.LENGTH_SHORT).show();
                    } else {


                        String productImage = productData.get(0).getpath2();
                        String Image_login = productData.get(0).getpath();
                        String nameoffice = productData.get(0).getsignature5();

                        text6.setText(nameoffice);
                        String imageUrl= Constant.PRODUCT_IMAGE_URL+productImage;
                        String imageUrls= Constant.PRODUCT_IMAGE_URL+Image_login;

                        if (productImage != null) {
                            if (productImage.length() < 3) {

                                imagebarcode.setImageResource(R.drawable.image_placeholder);
                            } else {
                                Glide.with(View2paymentActivity.this)
                                        .load(imageUrl)
                                        .placeholder(R.drawable.loading)
                                        .error(R.drawable.image_placeholder)
                                        .into(imagebarcode);
                            }
                        }



                        if (Image_login != null) {
                            if (Image_login.length() < 3) {

                                images.setImageResource(R.drawable.image_placeholder);
                            } else {
                                Glide.with(View2paymentActivity.this)
                                        .load(imageUrls)
                                        .placeholder(R.drawable.loading)
                                        .error(R.drawable.image_placeholder)
                                        .into(images);
                            }
                        }


                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<office>> call, @NonNull Throwable t) {

                loading.dismiss();
                Toast.makeText(View2paymentActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                Log.d("Error : ", t.toString());
            }
        });
    }


  public void get_totaill(String stock_id) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<paymentorder>> call;
        call = apiInterface.get_dataorderpay_totaill(stock_id);

        call.enqueue(new Callback<List<paymentorder>>() {
            @Override
            public void onResponse(@NonNull Call<List<paymentorder>> call, @NonNull Response<List<paymentorder>> response) {
                if (response.isSuccessful() && response.body() != null) {

                       List<paymentorder> totaillData;
                       totaillData = response.body();

                       if (totaillData.isEmpty()) {
                           Toasty.warning(View2paymentActivity.this, R.string.no_product_found, Toast.LENGTH_SHORT).show();
                       } else {
                          String totaill = totaillData.get(0).getamount2();
                           double amont = Double.parseDouble(totaill);
                           text_v9.setText(f.format(amont)+" ກິບ");
                       }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<paymentorder>> call, @NonNull Throwable t) {

                loading.dismiss();
                Toast.makeText(View2paymentActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                Log.d("Error : ", t.toString());
            }
        });
    }



    public void changeOnCon() {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Successfuly").setContentText("ພີມສຳເລັດ!").setConfirmText("ຕົກລົງ").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {@Override
        public void onClick(SweetAlertDialog sDialog) {
            try {
                Intent intent = new Intent(View2paymentActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }catch (Exception e){
            }
        }
        }).show();
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}