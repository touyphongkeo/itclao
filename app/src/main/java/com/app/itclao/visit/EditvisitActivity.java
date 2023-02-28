package com.app.itclao.visit;
import static com.app.itclao.ClassLibs.shopEmail;
import static com.app.itclao.ClassLibs.Username;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.Tel;
import static com.app.itclao.ClassLibs.Customer_id;
import static com.app.itclao.ClassLibs.Customer_name;
import static com.app.itclao.ClassLibs.Username;
import static com.app.itclao.ClassLibs.finvoiceId2;
import static com.app.itclao.ClassLibs.shopEmail;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.Date;
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

import com.app.itclao.Constant;
import com.app.itclao.HomeActivity;
import com.app.itclao.adapter.OrderDetailsAdapter2;
import com.app.itclao.customer.EditCustActivity;
import com.app.itclao.custroute.CustrouteActivity;
import com.app.itclao.model.OrderDetails;
import com.app.itclao.model.Status;
import com.app.itclao.model.Visit;
import com.app.itclao.networking.ApiClient;
import com.app.itclao.networking.ApiInterface;
import com.app.itclao.print.PrinterActivity;
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

public class EditvisitActivity extends BaseActivity {

    List<Visit> orderDetails;
    List<Status> productCategory;
    ArrayAdapter<String> categoryAdapter;
    List<String> categoryNames;
    String selectedCategoryID;

    ProgressDialog loading;
    EditText etxtCustomerName, same_as_before_check, etxtCustomerCell, etxtCustomerEmail,etxtProductCategory,usernaes,customers,note,red_calabash_in,same_as_before,same_as_before_in,coffeestrong,coffeestrong_check,coffeestrong_in,youngcoffee,youngcoffee_check,youngcoffee_in,date,txt_billnoorder;
    TextView txtAddCustomer;

    ImageView img_back,edit_customer;

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_visit);

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setTitle("ແກ້ໄຂຂໍ້ມູນການຢຽມຍາມລູກຄ້າ");
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
        txt_billnoorder = findViewById(R.id.txt_billnoorder);
        date = findViewById(R.id.date);
        img_back = findViewById(R.id.img_back);
        edit_customer = findViewById(R.id.edit_customer);

       // date.setText(Date+""+Customer_id);
        usernaes.setText(shopEmail+""+Username);
        customers.setText(Customer_id+""+Customer_name);


        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        String email = sp.getString(Constant.SP_EMAIL, "");
        //Toast.makeText(this, email, Toast.LENGTH_SHORT).show();



        getVsitDate();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditvisitActivity.this, CustrouteActivity.class);
                startActivity(intent);
                finish();
            }
        });


        edit_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditvisitActivity.this, EditCustActivity.class);
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
                String Id = txt_billnoorder.getText().toString().trim();
                String onc = etxtProductCategory.getText().toString().trim();
                String red_calabashqty_check = etxtCustomerEmail.getText().toString().trim();
                String same_as_before_checks = same_as_before_check.getText().toString().trim();
                String coffeestrong_checks = coffeestrong_check.getText().toString().trim();
                String youngcoffee_checks = youngcoffee_check.getText().toString().trim();
                String notess = note.getText().toString().trim();


                updatevsit(Id,onc,red_calabashqty_check,same_as_before_checks,coffeestrong_checks,youngcoffee_checks,notess,Customer_id.toString());
            }
        });







        //ເລືອກລາຄາ
        etxtProductCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryAdapter = new ArrayAdapter<>(EditvisitActivity.this, android.R.layout.simple_list_item_1);
                categoryAdapter.addAll(categoryNames);

                AlertDialog.Builder dialog = new AlertDialog.Builder(EditvisitActivity.this);
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



    public void getVsitDate() {
        loading=new ProgressDialog(EditvisitActivity.this);
        loading.setCancelable(false);
        loading.setMessage(getString(R.string.please_wait));
        loading.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Visit>> call;
        call = apiInterface.get_suet(Date.toString(),Customer_id.toString());
        call.enqueue(new Callback<List<Visit>>() {
            @Override
            public void onResponse(@NonNull Call<List<Visit>> call, @NonNull Response<List<Visit>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    orderDetails = response.body();
                    loading.dismiss();

                    String id = orderDetails.get(0).getId();
                    String visit_id = orderDetails.get(0).getvisit_id();
                    String leader = orderDetails.get(0).getleader();
                    String onc = orderDetails.get(0).getonc();
                    String notes = orderDetails.get(0).getnot();
                    String red_calabashqty_checks = orderDetails.get(0).getred_calabashqty_check();
                    String same_as_before_checks = orderDetails.get(0).getsame_as_before_check();
                    String coffeestrong_checks = orderDetails.get(0).getcoffeestrong_check();
                    String youngcoffee_checks = orderDetails.get(0).getyoungcoffee_check();
                    date.setText(visit_id);
                    etxtCustomerName.setText(leader);
                    etxtProductCategory.setText(onc);
                    note.setText(notes);
                    etxtCustomerEmail.setText(red_calabashqty_checks);
                    same_as_before_check.setText(same_as_before_checks);
                    coffeestrong_check.setText(coffeestrong_checks);
                    youngcoffee_check.setText(youngcoffee_checks);
                    txt_billnoorder.setText(id);


                    //  getProductsDatas(finvoiceId);
                 //   Toasty.success(EditvisitActivity.this, "Successfuly", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Visit>> call, @NonNull Throwable t) {
                Toast.makeText(EditvisitActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                Log.d("Error : ", t.toString());
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








    private void updatevsit(String Id,String onc,String red_calabashqty_check,String same_as_before_check,String coffeestrong_check,String youngcoffee_check,String note,String customer_id) {
        loading=new ProgressDialog(this);
        loading.setCancelable(false);
        loading.setMessage(getString(R.string.please_wait));
        loading.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Visit> call = apiInterface.updatevsit(Id,onc,red_calabashqty_check,same_as_before_check,coffeestrong_check,youngcoffee_check,note,customer_id);
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
                            Toasty.success(EditvisitActivity.this, R.string.customer_successfully_added, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditvisitActivity.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }


                      //  Toasty.success(AddvisitActivity.this, R.string.customer_successfully_added, Toast.LENGTH_SHORT).show();


                    }
                   else if (value.equals(Constant.KEY_FAILURE)) {

                        loading.dismiss();

                        Toasty.error(EditvisitActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
                        finish();

                    }

                    else {
                        loading.dismiss();
                        Toasty.error(EditvisitActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
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
                Toasty.error(EditvisitActivity.this, R.string.no_network_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }




    public void withCancelButtonListener() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Successfully!").setContentText("ທ່ານຕ້ອງການຂາຍສີນບໍ?").setCancelText("ຕົກລົງ!").setConfirmText("ກັບຄືນ").showCancelButton(true).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {@Override
        public void onClick(SweetAlertDialog sDialog) {
            // Showing simple toast message to user
            Intent intent = new Intent(EditvisitActivity.this, MtableActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            sDialog.cancel();
        }
        }).show();
    }



}
