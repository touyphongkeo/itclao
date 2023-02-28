package com.app.itclao.visit;
import static com.app.itclao.ClassLibs.shopEmail;
import static com.app.itclao.ClassLibs.Username;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.Tel;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import static com.app.itclao.ClassLibs.Customer_id;
import static com.app.itclao.ClassLibs.Customer_name;
import static com.app.itclao.ClassLibs.finvoiceId2;
import static com.app.itclao.ClassLibs.shopEmail;
import static com.app.itclao.ClassLibs.Username;

import com.app.itclao.Constant;
import com.app.itclao.HomeActivity;
import com.app.itclao.customer.EditCustActivity;
import com.app.itclao.custroute.CustrouteActivity;
import com.app.itclao.model.Item;
import com.app.itclao.model.Status;
import com.app.itclao.model.Visit;
import com.app.itclao.networking.ApiClient;
import com.app.itclao.networking.ApiInterface;
import com.app.itclao.routesale.TbroutsaleActivity;
import com.app.itclao.table.MtableActivity;
import com.app.itclao.tbroute.TbrouteActivity;
import com.app.itclao.utils.BaseActivity;
import com.app.onlinesmartpos.R;

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
public class AddvisitActivity extends BaseActivity {

    List<Status> productCategory;
    ArrayAdapter<String> categoryAdapter;
    List<String> categoryNames;
    String selectedCategoryID;

    ProgressDialog loading;
    EditText etxtCustomerName, same_as_before_check, etxtCustomerCell, etxtCustomerEmail,etxtProductCategory,usernaes,customers,note,red_calabash_in,same_as_before,same_as_before_in,coffeestrong,coffeestrong_check,coffeestrong_in,youngcoffee,youngcoffee_check,youngcoffee_in;
    TextView txtAddCustomer;
    ImageView img_back,edit_customer;
    SharedPreferences sp;
    SharedPreferences.Editor editor;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_visit);

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setTitle("ເກັບຂໍ້ມູນການຢຽມຍາມລູກຄ້າ");
        getSupportActionBar().hide();

        etxtCustomerName = findViewById(R.id.etxt_customer_name);
        etxtCustomerCell = findViewById(R.id.etxt_customer_cell);
        etxtCustomerEmail = findViewById(R.id.etxt_email);
        etxtProductCategory = findViewById(R.id.etxt_product_category);
        usernaes = findViewById(R.id.usernaes);
        customers = findViewById(R.id.customers);
        note = findViewById(R.id.note);
        red_calabash_in = findViewById(R.id.red_calabash_in);
        same_as_before = findViewById(R.id.same_as_before);
        same_as_before_check = findViewById(R.id.same_as_before_check);
        same_as_before_in = findViewById(R.id.same_as_before_in);
        coffeestrong = findViewById(R.id.coffeestrong);
        coffeestrong_check = findViewById(R.id.coffeestrong_check);
        coffeestrong_in = findViewById(R.id.coffeestrong_in);
        youngcoffee = findViewById(R.id.youngcoffee);
        youngcoffee_check = findViewById(R.id.youngcoffee_check);
        youngcoffee_in = findViewById(R.id.youngcoffee_in);
        img_back = findViewById(R.id.img_back);
        edit_customer = findViewById(R.id.edit_customer);

        usernaes.setText(shopEmail+""+Username);
        customers.setText(Customer_id+""+Customer_name);

        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        String email = sp.getString(Constant.SP_EMAIL, "");
     //   Toast.makeText(this, email, Toast.LENGTH_SHORT).show();


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddvisitActivity.this, CustrouteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        edit_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddvisitActivity.this, EditCustActivity.class);
                startActivity(intent);
                finish();
            }
        });


      //  Toast.makeText(AddvisitActivity.this, shopEmail+""+Username, Toast.LENGTH_SHORT).show();

        txtAddCustomer = findViewById(R.id.txt_add_customer);
        getItem();
        txtAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = usernaes.getText().toString().trim();
                String leader = etxtCustomerName.getText().toString().trim();
                String customer_name = customers.getText().toString().trim();
                String onc = etxtProductCategory.getText().toString().trim();
                String notes = note.getText().toString().trim();

                String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(new Date());
                String currentTime = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date()); //HH:mm:ss a
                String red_calabash = etxtCustomerCell.getText().toString().trim();
                String red_calabashqty_check = etxtCustomerEmail.getText().toString().trim();
                String red_calabash_ins = red_calabash_in.getText().toString().trim();
                String same_as_befored = same_as_before.getText().toString().trim();
                String same_as_before_checked = same_as_before_check.getText().toString().trim();
                String same_as_before_ined = same_as_before_in.getText().toString().trim();
                String coffeestronged = coffeestrong.getText().toString().trim();
                String coffeestrong_checked = coffeestrong_check.getText().toString().trim();
                String coffeestrong_ined = coffeestrong_in.getText().toString().trim();
                String youngcoffeeed = youngcoffee.getText().toString().trim();
                String youngcoffee_checked = youngcoffee_check.getText().toString().trim();
                String youngcoffee_ined = youngcoffee_in.getText().toString().trim();

                String currentYear = new SimpleDateFormat("yyyy", Locale.ENGLISH).format(new Date());
                Long tsLong = System.currentTimeMillis() / 1000;
                String timeStamp = tsLong.toString();
                Log.d("Time", timeStamp);
                //Invoice number=INV+StaffID+CurrentYear+timestamp
                String invoiceNumber=currentYear+timeStamp;
                finvoiceId2=invoiceNumber.toString();
                    addVisit(fname,leader,currentDate,customer_name,onc,notes,stock_id,currentTime,red_calabash,red_calabashqty_check,red_calabash_ins,same_as_befored,same_as_before_checked,same_as_before_ined,coffeestronged,coffeestrong_checked,coffeestrong_ined,youngcoffeeed,youngcoffee_checked,youngcoffee_ined,finvoiceId2);

            }
        });







        //ເລືອກລາຄາ
        etxtProductCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryAdapter = new ArrayAdapter<>(AddvisitActivity.this, android.R.layout.simple_list_item_1);
                categoryAdapter.addAll(categoryNames);

                AlertDialog.Builder dialog = new AlertDialog.Builder(AddvisitActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_list_search, null);
                dialog.setView(dialogView);
                dialog.setCancelable(false);

                Button dialogButton = dialogView.findViewById(R.id.dialog_button);
                EditText dialogInput = dialogView.findViewById(R.id.dialog_input);
                TextView dialogTitle = dialogView.findViewById(R.id.dialog_title);
                ListView dialogList = dialogView.findViewById(R.id.dialog_list);


                dialogTitle.setText("ເລືອກສະຖານະ");
                dialogList.setVerticalScrollBarEnabled(true);
                dialogList.setAdapter(categoryAdapter);

                dialogInput.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        Log.d("data", s.toString());
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                        categoryAdapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        Log.d("data", s.toString());
                    }
                });


                final AlertDialog alertDialog = dialog.create();

                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();


                dialogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        alertDialog.dismiss();
                        final String selectedItem = categoryAdapter.getItem(position);

                        String categoryId = "0";
                        etxtProductCategory.setText(selectedItem);


                        if (selectedItem.equals("close")){
                            etxtCustomerEmail.setEnabled(false);
                            same_as_before_check.setEnabled(false);
                            coffeestrong_check.setEnabled(false);
                            youngcoffee_check.setEnabled(false);
                        }else {
                            etxtCustomerEmail.setEnabled(true);
                            same_as_before_check.setEnabled(true);
                            coffeestrong_check.setEnabled(true);
                            youngcoffee_check.setEnabled(true);
                        }




                        for (int i = 0; i < categoryNames.size(); i++) {
                            if (categoryNames.get(i).equalsIgnoreCase(selectedItem)) {
                                // Get the ID of selected Country
                                categoryId = productCategory.get(i).getId();
                            }
                        }

                        selectedCategoryID = categoryId;

                    }
                });
            }
        });


    }




    public void getItem() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Status>> call;
        call = apiInterface.getStatus();
        call.enqueue(new Callback<List<Status>>() {
            @Override
            public void onResponse(@NonNull Call<List<Status>> call, @NonNull Response<List<Status>> response) {
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
            public void onFailure(@NonNull Call<List<Status>> call, @NonNull Throwable t) {
                //write own action
            }
        });
    }



    //for back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// app icon in action bar clicked; goto parent activity.
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }








    private void addVisit(String fname,String leader,String date,String customer_name,String onc,String notes,String User_ID, String time,String red_calabash,String red_calabashqty_check,String red_calabash_in,String same_as_before,String same_as_before_check,String same_as_before_in,String coffeestrong,String coffeestrong_check,String coffeestrong_in,String youngcoffee,String youngcoffee_check,String youngcoffee_in,String bill_noorder) {
        loading=new ProgressDialog(this);
        loading.setCancelable(false);
        loading.setMessage(getString(R.string.please_wait));
        loading.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Visit> call = apiInterface.addVisit(fname,leader,date,Customer_id,customer_name,onc,notes,User_ID,time,red_calabash,red_calabashqty_check,red_calabash_in,same_as_before,same_as_before_check,same_as_before_in,coffeestrong,coffeestrong_check,coffeestrong_in,youngcoffee,youngcoffee_check,youngcoffee_in,bill_noorder);
        call.enqueue(new Callback<Visit>() {
            @Override
            public void onResponse(@NonNull Call<Visit> call, @NonNull Response<Visit> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String value = response.body().getValue();

                    if (value.equals(Constant.KEY_SUCCESS)) {

                        loading.dismiss();
                        if (onc.equals("open")){
                            withCancelButtonListener();
                        }else {
                            Toasty.success(AddvisitActivity.this, R.string.customer_successfully_added, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddvisitActivity.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }


                      //  Toasty.success(AddvisitActivity.this, R.string.customer_successfully_added, Toast.LENGTH_SHORT).show();


                    }
                   else if (value.equals(Constant.KEY_FAILURE)) {

                        loading.dismiss();

                        Toasty.error(AddvisitActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
                        finish();

                    }

                    else {
                        loading.dismiss();
                        Toasty.error(AddvisitActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
                    }
                }

                else
                {
                    loading.dismiss();
                    Log.d("Error","Error");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Visit> call, @NonNull Throwable t) {
                loading.dismiss();
                Log.d("Error! ", t.toString());
                Toasty.error(AddvisitActivity.this, R.string.no_network_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }




    public void withCancelButtonListener() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Successfully!").setContentText("ທ່ານຕ້ອງການຂາຍສີນບໍ?").setCancelText("ຕົກລົງ!").setConfirmText("ກັບຄືນ").showCancelButton(true).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {@Override
        public void onClick(SweetAlertDialog sDialog) {
            // Showing simple toast message to user
            Intent intent = new Intent(AddvisitActivity.this, MtableActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            sDialog.cancel();
        }
        }).show();
    }



}
