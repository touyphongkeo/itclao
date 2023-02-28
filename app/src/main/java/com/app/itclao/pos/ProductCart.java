package com.app.itclao.pos;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import static com.app.itclao.ClassLibs.finvoiceId;
import static com.app.itclao.ClassLibs.Customer_id;
import static com.app.itclao.ClassLibs.Customer_type;
import static com.app.itclao.ClassLibs.finvoiceId2;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.itclao.Constant;
import com.app.itclao.HomeActivity;
import com.app.itclao.adapter.CartAdapter;
import com.app.itclao.database.DatabaseAccess;
import com.app.itclao.model.Customer;
import com.app.itclao.model.user;
import com.app.itclao.networking.ApiClient;
import com.app.itclao.networking.ApiInterface;
import com.app.itclao.print.PrinterActivity;
import com.app.itclao.utils.BaseActivity;
import com.app.itclao.utils.Utils;
import com.app.onlinesmartpos.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.shopEmail;
import static com.app.itclao.ClassLibs.Username;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.Tel;
public class ProductCart extends BaseActivity {
    List<user> DataUser;
    CartAdapter CartAdapter;
    ImageView imgNoProduct,img_back;

    Button btnSubmitOrder;
    TextView txtNoProduct, txtTotalPrice,username,text_user,text_tel,text_customer;
    LinearLayout linearLayout;
    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(ProductCart.this);
    DecimalFormat f;
    DecimalFormat f2;
    String invoiceIds,customer_id;
    List<String> customerNames, orderTypeNames, paymentMethodNames;
    List<Customer> customerData;
    ArrayAdapter<String> customerAdapter, orderTypeAdapter, paymentMethodAdapter;
    SharedPreferences sp;
    String servedBy,staffId,shopTax,currency;
    SharedPreferences.Editor editor;

    String UserID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_cart);

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().hide();

        f = new DecimalFormat("#,###,##0");
        f2 = new DecimalFormat("####");

        getCustomers(stock_id);

        RecyclerView recyclerView = findViewById(R.id.cart_recyclerview);
        imgNoProduct = findViewById(R.id.image_no_product);
        btnSubmitOrder = findViewById(R.id.btn_submit_order);
        txtNoProduct = findViewById(R.id.txt_no_product);
        linearLayout = findViewById(R.id.linear_layout);
        txtTotalPrice = findViewById(R.id.txt_total_price);
        img_back = findViewById(R.id.img_back);
        txtNoProduct.setVisibility(View.GONE);
        username = findViewById(R.id.username);
        text_user = findViewById(R.id.text_user);
        text_tel = findViewById(R.id.text_tel);
        text_customer = findViewById(R.id.text_customer);

       // Toast.makeText(this, Customer_id.toString(), Toast.LENGTH_SHORT).show();

        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        String email = sp.getString(Constant.SP_EMAIL, "");

        getUsername(email);
       // Toast.makeText(this, UserID, Toast.LENGTH_SHORT).show();
        username.setText(email);

        // set a GridLayoutManager with default vertical orientation and 3 number of columns
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
        recyclerView.setHasFixedSize(true);



        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductCart.this,PosActivity.class);
                startActivity(intent);
                finish();
            }
        });



        databaseAccess.open();
        //get data from local database
        List<HashMap<String, String>> cartProductList;
        cartProductList = databaseAccess.getProductCategory();
        Log.d("CartSize", "" + cartProductList.size());
        if (cartProductList.isEmpty()) {
            imgNoProduct.setImageResource(R.drawable.not_found);
            imgNoProduct.setVisibility(View.VISIBLE);
            txtNoProduct.setVisibility(View.VISIBLE);
            btnSubmitOrder.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.GONE);
            txtTotalPrice.setVisibility(View.GONE);
        } else {
            imgNoProduct.setVisibility(View.GONE);
            CartAdapter = new CartAdapter(ProductCart.this, cartProductList, txtTotalPrice, btnSubmitOrder, imgNoProduct, txtNoProduct);
            recyclerView.setAdapter(CartAdapter);

        }
        btnSubmitOrder.setOnClickListener(v -> dialog());

    }




    public void proceedOrder(String pay_lak,String customerName, String bill_discount,String amounprice,String pay_name,String fname,String tel,String cus_name) {
        databaseAccess = DatabaseAccess.getInstance(ProductCart.this);
        databaseAccess.open();
        int itemCount = databaseAccess.getCartItemCount();

        if (itemCount > 0) {
            databaseAccess.open();
            //get data from local database
            final List<HashMap<String, String>> lines;
            lines = databaseAccess.getCartProduct();
            double qty2,pr2;
            if (lines.isEmpty()) {
                Toasty.error(ProductCart.this, "ບໍມີລາຍການອາຫານ", Toast.LENGTH_SHORT).show();
            } else {

                //get current timestamp
                String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(new Date());
                String currentYear = new SimpleDateFormat("yyyy", Locale.ENGLISH).format(new Date());
                //H denote 24 hours and h denote 12 hour hour format
                String currentTime = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date()); //HH:mm:ss a

                Long tsLong = System.currentTimeMillis() / 1000;
                String timeStamp = tsLong.toString();
                Log.d("Time", timeStamp);
                //Invoice number=INV+StaffID+CurrentYear+timestamp
                String invoiceNumber=currentYear+timeStamp;
                finvoiceId=invoiceNumber.toString();

                String user = username.getText().toString();

                final JSONObject obj = new JSONObject();
                try {
                    //ລາຍການແມ່ຂອງສີນຄ້າ
                    obj.put("pay_date", currentDate);
                    obj.put("bill_no", invoiceNumber);
                    obj.put("billnoorder", finvoiceId2);
                    obj.put("time", currentTime);
                    obj.put("pay_lak", String.valueOf(pay_lak));
                    obj.put("user_id", user);
                    obj.put("pay_name", pay_name);

                    JSONArray array = new JSONArray();
                    for (int i = 0; i < lines.size(); i++) {
//                        //ລາຍການລູກຄ້າຂອງສີນຄ້າ
                           databaseAccess.open();
                           String stock_id = lines.get(i).get("stock_id");
                           String product_id = lines.get(i).get("product_id");
                           String pro_name = lines.get(i).get("pro_name");
                           String unit = lines.get(i).get("unit");
                           String img = lines.get(i).get("img");


                          JSONObject objp = new JSONObject();
                          objp.put("stock_id", stock_id);
                          objp.put("product_id", product_id);
                          objp.put("price", lines.get(i).get("sprice"));
                          objp.put("qty", lines.get(i).get("qty"));
                          objp.put("extra_qty", lines.get(i).get("discount"));
                          objp.put("customer_id", customerName);
                          objp.put("bill_discount", bill_discount);
                          objp.put("pro_name", pro_name);
                          objp.put("unit", unit);
                          objp.put("amounprice", amounprice);
                          objp.put("urlimage", img);
                          objp.put("fnamesale", fname);
                          objp.put("telsale", tel);
                          objp.put("cus_name", cus_name);
                          array.put(objp);
//
                   }
                   obj.put("lines", array);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Utils utils=new Utils();
                if(utils.isNetworkAvailable(ProductCart.this)) {
                    orderSubmit(obj);
//                    finvoiceId=invoiceNumber.toString();
//
//                    Intent intent = new Intent(ProductCart.this, PrinterActivity.class);
//                    startActivity(intent);
//                    finish();
                } else {
                    Toasty.error(this, "ກະລຸນາເຊື່ອມຕໍອີນເຕີເນັດ", Toast.LENGTH_SHORT).show();
                }
            }
      } else {
            Toasty.error(ProductCart.this, "ບໍມີລາຍການອາຫານ", Toast.LENGTH_SHORT).show();
       }
    }


    //ບັນທືກລາຍການສີນຄ້າຜ່ານ ການເຊື່ອມຕໍ api mysql
    private void orderSubmit(final JSONObject obj){
        Log.d("Json",obj.toString());
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        RequestBody body2 = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(obj));
        Call<String> call = apiInterface.submitOrders(body2);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                   // databaseAccess.open();
                    //databaseAccess.emptyCart();
                 //   Toasty.success(ProductCart.this, R.string.order_successfully_done, Toast.LENGTH_SHORT).show();

                    Toasty.success(ProductCart.this, "ສັ່ງສຳເລັດ", Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.error(ProductCart.this, R.string.error, Toast.LENGTH_SHORT).show();
                    Log.d("error", response.toString());
                }
            }
            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

                Log.d("onFailure", t.toString());
            }
        });
    }
    //ແຈງເຕືອນສຳເລັດໃຫ້
 /*   public void dialogSuccess() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ProductCart.this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_success, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);
        ImageButton dialogBtnCloseDialog = dialogView.findViewById(R.id.btn_close_dialog);
        Button dialogBtnViewAllOrders = dialogView.findViewById(R.id.btn_view_all_orders);
        AlertDialog alertDialogSuccess = dialog.create();
        dialogBtnCloseDialog.setOnClickListener(v -> {
            alertDialogSuccess.dismiss();
            Intent intent = new Intent(ProductCart.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });
        dialogBtnViewAllOrders.setOnClickListener(v -> {
            alertDialogSuccess.dismiss();
            Intent intent = new Intent(ProductCart.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });
        alertDialogSuccess.show();
    }
*/

    public void dialog() {
        String shopCurrency = currency;
        String tax = "0";
        double getTax = Double.parseDouble(tax);

        AlertDialog.Builder dialog = new AlertDialog.Builder(ProductCart.this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_payment, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);
        final Button dialogBtnSubmit = dialogView.findViewById(R.id.btn_submit);
        final ImageButton dialogBtnClose = dialogView.findViewById(R.id.btn_close);
        final TextView dialogOrderPaymentMethod = dialogView.findViewById(R.id.dialog_order_status);
        final TextView dialogOrderType = dialogView.findViewById(R.id.dialog_order_type);
        final TextView dialogCustomer = dialogView.findViewById(R.id.dialog_customer);

        final TextView dialogTxtTotal = dialogView.findViewById(R.id.dialog_txt_total);
        final TextView dialogTxtTotalTax = dialogView.findViewById(R.id.dialog_txt_total_tax);
        final TextView dialogTxtLevelTax = dialogView.findViewById(R.id.dialog_level_tax);
        final TextView dialogTxtTotalCost = dialogView.findViewById(R.id.dialog_txt_total_cost);
        final EditText dialogEtxtDiscount = dialogView.findViewById(R.id.etxt_dialog_discount);


        final ImageButton dialogImgCustomer = dialogView.findViewById(R.id.img_select_customer);
        final ImageButton dialogImgOrderPaymentMethod = dialogView.findViewById(R.id.img_order_payment_method);
        final ImageButton dialogImgOrderType = dialogView.findViewById(R.id.img_order_type);


        dialogTxtLevelTax.setText("ອາກອນ" + "( " + tax + "%) : ");
        double totalCost = CartAdapter.totalPrice;
        dialogTxtTotal.setText(f.format(totalCost));

        double calculatedTax = (totalCost * getTax) / 100.0;
        dialogTxtTotalTax.setText(f.format(calculatedTax));


        double discount = 0;
        double calculatedTotalCost = totalCost + calculatedTax - discount;
        dialogTxtTotalCost.setText(f2.format(calculatedTotalCost));

        dialogEtxtDiscount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("data", s.toString());
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                double discount = 0;
                String getDiscount = s.toString();
                if (!getDiscount.isEmpty()) {
                    double calculatedTotalCost = totalCost + calculatedTax;
                    if (getDiscount.equals(".")) {
                        discount=0;
                    } else {
                        discount = Double.parseDouble(getDiscount);
                    }
                    if (discount > calculatedTotalCost) {
                        dialogEtxtDiscount.setError(getString(R.string.discount_cant_be_greater_than_total_price));
                        dialogEtxtDiscount.requestFocus();
                        dialogBtnSubmit.setVisibility(View.INVISIBLE);
                    } else {
                        dialogBtnSubmit.setVisibility(View.VISIBLE);
                        calculatedTotalCost = totalCost + calculatedTax - discount;
                        dialogTxtTotalCost.setText(f2.format(calculatedTotalCost));
                    }
                } else {
                    double calculatedTotalCost = totalCost + calculatedTax - discount;
                    dialogTxtTotalCost.setText(f2.format(calculatedTotalCost));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("data", s.toString());
            }
        });




        //payment methods
        paymentMethodNames = new ArrayList<>();
        databaseAccess.open();
        final List<HashMap<String, String>> paymentMethod;
        paymentMethod = databaseAccess.getPaymentMethod();
        for (int i = 0; i < paymentMethod.size(); i++) {
            paymentMethodNames.add(paymentMethod.get(i).get("payment_method_name"));
        }
        dialogImgOrderPaymentMethod.setOnClickListener(v -> {
            paymentMethodAdapter = new ArrayAdapter<>(ProductCart.this, android.R.layout.simple_list_item_1);
            paymentMethodAdapter.addAll(paymentMethodNames);
            AlertDialog.Builder dialog1 = new AlertDialog.Builder(ProductCart.this);
            View dialogView1 = getLayoutInflater().inflate(R.layout.dialog_list_search, null);
            dialog1.setView(dialogView1);
            dialog1.setCancelable(false);
            Button dialogButton = (Button) dialogView1.findViewById(R.id.dialog_button);
            EditText dialogInput = (EditText) dialogView1.findViewById(R.id.dialog_input);
            TextView dialogTitle = (TextView) dialogView1.findViewById(R.id.dialog_title);
            ListView dialogList = (ListView) dialogView1.findViewById(R.id.dialog_list);
            dialogTitle.setText("ຮູບແບບການຊຳລະ");
            dialogList.setVerticalScrollBarEnabled(true);
            dialogList.setAdapter(paymentMethodAdapter);
            dialogInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    Log.d("data", s.toString());
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                    paymentMethodAdapter.getFilter().filter(charSequence);
                }
                @Override
                public void afterTextChanged(Editable s) {
                    Log.d("data", s.toString());
                }
            });
            final AlertDialog alertDialog = dialog1.create();
            dialogButton.setOnClickListener(v1 -> alertDialog.dismiss());
            alertDialog.show();
            dialogList.setOnItemClickListener((parent, view, position, id) -> {
                alertDialog.dismiss();
                String selectedItem = paymentMethodAdapter.getItem(position);
                dialogOrderPaymentMethod.setText(selectedItem);
            });
        });




        //ການບໍລີການ
        orderTypeNames = new ArrayList<>();
        databaseAccess.open();
        final List<HashMap<String, String>> orderType;
        orderType = databaseAccess.getOrderType();
        for (int i = 0; i < orderType.size(); i++) {
            orderTypeNames.add(orderType.get(i).get("order_type_name"));
        }
        dialogImgOrderType.setOnClickListener(v -> {
            orderTypeAdapter = new ArrayAdapter<>(ProductCart.this, android.R.layout.simple_list_item_1);
            orderTypeAdapter.addAll(orderTypeNames);
            AlertDialog.Builder dialog12 = new AlertDialog.Builder(ProductCart.this);
            View dialogView12 = getLayoutInflater().inflate(R.layout.dialog_list_search, null);
            dialog12.setView(dialogView12);
            dialog12.setCancelable(false);
            Button dialogButton = (Button) dialogView12.findViewById(R.id.dialog_button);
            EditText dialogInput = (EditText) dialogView12.findViewById(R.id.dialog_input);
            TextView dialogTitle = (TextView) dialogView12.findViewById(R.id.dialog_title);
            ListView dialogList = (ListView) dialogView12.findViewById(R.id.dialog_list);
            dialogTitle.setText("ລາຍການລູກຄ້າ");
            dialogList.setAdapter(orderTypeAdapter);
            dialogList.setVerticalScrollBarEnabled(true);
            dialogInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    Log.d("data", s.toString());
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                    orderTypeAdapter.getFilter().filter(charSequence);
                }
                @Override
                public void afterTextChanged(Editable s) {
                    Log.d("data", s.toString());
                }
            });
            final AlertDialog alertDialog = dialog12.create();
            dialogButton.setOnClickListener(v13 -> alertDialog.dismiss());
            alertDialog.show();
            dialogList.setOnItemClickListener((parent, view, position, id) -> {
                alertDialog.dismiss();
                String selectedItem = orderTypeAdapter.getItem(position);
                dialogOrderType.setText(selectedItem);
            });
        });




      dialogImgCustomer.setOnClickListener(v -> {
            customerAdapter = new ArrayAdapter<>(ProductCart.this, android.R.layout.simple_list_item_1);
             customerAdapter.addAll(customerNames);

            AlertDialog.Builder dialog13 = new AlertDialog.Builder(ProductCart.this);
            View dialogView13 = getLayoutInflater().inflate(R.layout.dialog_list_search, null);
            dialog13.setView(dialogView13);
            dialog13.setCancelable(false);
            Button dialogButton = (Button) dialogView13.findViewById(R.id.dialog_button);
            EditText dialogInput = (EditText) dialogView13.findViewById(R.id.dialog_input);
            TextView dialogTitle = (TextView) dialogView13.findViewById(R.id.dialog_title);
            ListView dialogList = (ListView) dialogView13.findViewById(R.id.dialog_list);
          //  dialogTitle.setText(R.string.select_customer);
            dialogList.setVerticalScrollBarEnabled(true);
            dialogList.setAdapter(customerAdapter);
            dialogInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    Log.d("data", s.toString());
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                    customerAdapter.getFilter().filter(charSequence);
                }

                @Override
                public void afterTextChanged(Editable s) {
                    Log.d("data", s.toString());
                }
            });


            final AlertDialog alertDialog = dialog13.create();

            dialogButton.setOnClickListener(v12 -> alertDialog.dismiss());

            alertDialog.show();
            dialogList.setOnItemClickListener((parent, view, position, id) -> {

                alertDialog.dismiss();
                String selectedItem = customerAdapter.getItem(position);
                dialogCustomer.setText(selectedItem);


            });
        });


        final AlertDialog alertDialog = dialog.create();
        alertDialog.show();
        dialogBtnSubmit.setOnClickListener(v -> {

                String pay_name = dialogOrderPaymentMethod.getText().toString().trim();
                String amounprice = dialogTxtTotal.getText().toString().trim();
                String customerName = dialogCustomer.getText().toString().trim();
                String bill_discount = dialogEtxtDiscount.getText().toString().trim();
                String pay_laks = dialogTxtTotalCost.getText().toString().trim();
                String fanme = text_user.getText().toString().trim();
                String tel = text_tel.getText().toString().trim();
                String cus_name = text_customer.getText().toString().trim();
//            if (discount1.isEmpty()) {
//                discount1 = "0";
//            }

                proceedOrder(pay_laks,Customer_id,bill_discount,amounprice,pay_name,fanme,tel,cus_name);
                //  Toasty.success(ProductCart.this, R.string.order_successfully_done, Toast.LENGTH_SHORT).show();
                databaseAccess.open();
                databaseAccess.emptyCart();

                Intent intent = new Intent(ProductCart.this, PrinterActivity.class);
                startActivity(intent);
                finish();
                // alertDialog.dismiss();

        });

        dialogBtnClose.setOnClickListener(v -> alertDialog.dismiss());


    }



    public void getCustomers(String stock_id) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Customer>> call;
        call = apiInterface.getCustomers(stock_id);
        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(@NonNull Call<List<Customer>> call, @NonNull Response<List<Customer>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    customerData = response.body();
                    String UserID = customerData.get(0).getCustomer_name();
                    text_customer.setText(UserID);

                    customerNames = new ArrayList<>();
                    for (int i = 0; i < customerData.size(); i++) {
                        customerNames.add(customerData.get(i).getCcustomer_id()+" "+customerData.get(i).getCustomer_name());
                     //   customer_id = customerData.get(i).getCcustomer_id();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Customer>> call, @NonNull Throwable t) {
                //write own action
            }
        });
    }





    public void getUsername(String User_ID) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<user>> call;
        call = apiInterface.get_useername(User_ID);
        call.enqueue(new Callback<List<user>>() {
            @Override
            public void onResponse(@NonNull Call<List<user>> call, @NonNull Response<List<user>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    DataUser = response.body();
                    String UserID = DataUser.get(0).getUser_ID();
                    String name = DataUser.get(0).getUser_Name();
                    String fname = DataUser.get(0).getfname();
                    String tel = DataUser.get(0).getTel();

                    text_user.setText(fname);
                    text_tel.setText(tel);




                    //  getProductsDatas(finvoiceId);
                    //   Toasty.success(EditvisitActivity.this, "Successfuly", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<user>> call, @NonNull Throwable t) {
                Toast.makeText(ProductCart.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                Log.d("Error : ", t.toString());
            }
        });
    }


    //for back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}

