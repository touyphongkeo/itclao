package com.app.itclao.customer;

import static com.app.itclao.ClassLibs.Date;
import static com.app.itclao.ClassLibs.Route_id;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.Customer_id;
import static com.app.itclao.ClassLibs.Customer_name;
import static com.app.itclao.ClassLibs.finvoiceId2;
import static com.app.itclao.ClassLibs.shopEmail;
import static com.app.itclao.ClassLibs.Username;
import static com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype.Slidetop;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.app.itclao.custroute.CustrouteActivity;
import com.app.itclao.model.Customer;
import com.app.itclao.model.Item;
import com.app.itclao.model.Visit;
import com.app.itclao.model.retail;
import com.app.itclao.model.size;
import com.app.itclao.networking.ApiClient;
import com.app.itclao.networking.ApiInterface;
import com.app.itclao.utils.BaseActivity;
import com.app.itclao.visit.AddvisitActivity;
import com.app.itclao.visit.EditvisitActivity;
import com.app.onlinesmartpos.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import static com.app.itclao.ClassLibs.shopEmail;
import static com.app.itclao.ClassLibs.Username;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.Tel;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCustActivity extends BaseActivity {

    private Context context;
    List<Customer> DatabCustomer;
    List<Item> productCategory;
    ArrayAdapter<String> categoryAdapter;
    List<String> categoryNames;
    String selectedCategoryID;



    List<retail> productRetail;
    ArrayAdapter<String> Retail;
    List<String> RetailNames;
    String selectedReID;


    List<size> dataSize;
    ArrayAdapter<String> SizeAdapter;
    List<String> SizeNames;
    String sizeID;




    ProgressDialog loading;
    EditText customer_id,etCategoryName,etxtProductCategory,OutletType,OutletSize,tcustomer_name,tvillage,tdistrict,tprovince,tphone,tfax,temail,tremark;
    TextView txtAddCategory;

    ImageView img_back,img_cart;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cust);

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button

        getSupportActionBar().hide();

        customer_id=findViewById(R.id.customer_id);
        etCategoryName=findViewById(R.id.et_category_name);
        txtAddCategory=findViewById(R.id.txt_add_category);
        etxtProductCategory = findViewById(R.id.etxt_product_category);

        OutletType = findViewById(R.id.OutletType);
        OutletSize = findViewById(R.id.OutletSize);
        tcustomer_name = findViewById(R.id.customer_name);
        tvillage = findViewById(R.id.village);
        tdistrict = findViewById(R.id.district);
        tprovince = findViewById(R.id.province);
        tphone = findViewById(R.id.phone);
        tfax = findViewById(R.id.fax);
        temail = findViewById(R.id.email);
        tremark = findViewById(R.id.remark);
        img_back = findViewById(R.id.img_back);
        img_cart = findViewById(R.id.img_cart);




        customer_id.setText(Customer_id);
        customer_id.setEnabled(false);
        etCategoryName.setText(stock_id);



        getItem();
        getRetail();
        getSize();

        get_datacustomer();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Toast.makeText(EditCustActivity.this, "sflsfsfskfkf", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditCustActivity.this, CustrouteActivity.class);
                startActivity(intent);
                finish();
            }
        });



        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              deleteCustomer();




            }
        });

        txtAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String customer_ids = customer_id.getText().toString().trim();
                String customer_type = etxtProductCategory.getText().toString().trim();
                String qutletType = OutletType.getText().toString().trim();
                String outletSize = OutletSize.getText().toString().trim();
                String customer_name = tcustomer_name.getText().toString().trim();
                String village = tvillage.getText().toString().trim();
                String district = tdistrict.getText().toString().trim();
                String province = tprovince.getText().toString().trim();
                String phone = tphone.getText().toString().trim();
                String fax = tfax.getText().toString().trim();
                String email = temail.getText().toString().trim();
                String remark = tremark.getText().toString().trim();



                    update_customer(customer_ids,customer_type,qutletType,outletSize,customer_name,village,district,province,phone,fax,email,remark,Route_id.toString());




            }
        });




        etxtProductCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryAdapter = new ArrayAdapter<>(EditCustActivity.this, android.R.layout.simple_list_item_1);
                categoryAdapter.addAll(categoryNames);

                AlertDialog.Builder dialog = new AlertDialog.Builder(EditCustActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_list_search, null);
                dialog.setView(dialogView);
                dialog.setCancelable(false);

                Button dialogButton = dialogView.findViewById(R.id.dialog_button);
                EditText dialogInput = dialogView.findViewById(R.id.dialog_input);
                TextView dialogTitle = dialogView.findViewById(R.id.dialog_title);
                ListView dialogList = dialogView.findViewById(R.id.dialog_list);


                dialogTitle.setText("ເລືອກລາຄາ");
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


        OutletType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retail = new ArrayAdapter<>(EditCustActivity.this, android.R.layout.simple_list_item_1);
                Retail.addAll(RetailNames);

                AlertDialog.Builder dialog = new AlertDialog.Builder(EditCustActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_list_search, null);
                dialog.setView(dialogView);
                dialog.setCancelable(false);

                Button dialogButton = dialogView.findViewById(R.id.dialog_button);
                EditText dialogInput = dialogView.findViewById(R.id.dialog_input);
                TextView dialogTitle = dialogView.findViewById(R.id.dialog_title);
                ListView dialogList = dialogView.findViewById(R.id.dialog_list);


                dialogTitle.setText("ເລືອກລາຄາ");
                dialogList.setVerticalScrollBarEnabled(true);
                dialogList.setAdapter(Retail);

                dialogInput.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        Log.d("data", s.toString());
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                        Retail.getFilter().filter(charSequence);
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
                        final String selectedItem = Retail.getItem(position);

                        String RetailId = "0";
                        OutletType.setText(selectedItem);






                        for (int i = 0; i < RetailNames.size(); i++) {
                            if (RetailNames.get(i).equalsIgnoreCase(selectedItem)) {
                                // Get the ID of selected Country
                                RetailId = productRetail.get(i).getId();
                            }
                        }

                        selectedReID = RetailId;

                    }
                });
            }
        });



        OutletSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SizeAdapter = new ArrayAdapter<>(EditCustActivity.this, android.R.layout.simple_list_item_1);
                SizeAdapter.addAll(SizeNames);

                AlertDialog.Builder dialog = new AlertDialog.Builder(EditCustActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_list_search, null);
                dialog.setView(dialogView);
                dialog.setCancelable(false);

                Button dialogButton = dialogView.findViewById(R.id.dialog_button);
                EditText dialogInput = dialogView.findViewById(R.id.dialog_input);
                TextView dialogTitle = dialogView.findViewById(R.id.dialog_title);
                ListView dialogList = dialogView.findViewById(R.id.dialog_list);


                dialogTitle.setText("ເລືອກຂະຫນາດຮ້ານ");
                dialogList.setVerticalScrollBarEnabled(true);
                dialogList.setAdapter(SizeAdapter);

                dialogInput.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        Log.d("data", s.toString());
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                        SizeAdapter.getFilter().filter(charSequence);
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
                        final String selectedItem = SizeAdapter.getItem(position);

                        String SizeId = "0";
                        OutletSize.setText(selectedItem);






                        for (int i = 0; i < SizeNames.size(); i++) {
                            if (SizeNames.get(i).equalsIgnoreCase(selectedItem)) {
                                // Get the ID of selected Country
                                SizeId = dataSize.get(i).getId();
                            }
                        }

                        sizeID = SizeId;

                    }
                });
            }
        });




    }


    private void update_customer(String customer_id,String customer_type,String OutletType,String OutletSize,String customer_name,String village,String district,String province,String phone,String fax,String email,String remark,String route_id) {
        loading = new ProgressDialog(this);
        loading.setCancelable(false);
        loading.setMessage(getString(R.string.please_wait));
        loading.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Customer> call = apiInterface.upadate_customer(customer_id,customer_type,OutletType,OutletSize,customer_name,village,district,province,phone,fax,email,remark,route_id);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(@NonNull Call<Customer> call, @NonNull Response<Customer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String value = response.body().getValue();



                    if (value.equals(Constant.KEY_SUCCESS)) {

                        loading.dismiss();

                        Toasty.success(EditCustActivity.this, R.string.successfully_added, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditCustActivity.this, CustrouteActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    } else if (value.equals(Constant.KEY_FAILURE)) {

                        loading.dismiss();

                        Toasty.error(EditCustActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
                        finish();

                    } else {
                        loading.dismiss();
                        Toasty.error(EditCustActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    loading.dismiss();
                    Log.d("Error", "Error");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Customer> call, @NonNull Throwable t) {
                loading.dismiss();
                Log.d("Error! ", t.toString());
                Toasty.error(EditCustActivity.this, R.string.no_network_connection, Toast.LENGTH_SHORT).show();
            }
        });
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





    public void getRetail() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<retail>> call;
        call = apiInterface.getRetail();
        call.enqueue(new Callback<List<retail>>() {
            @Override
            public void onResponse(@NonNull Call<List<retail>> call, @NonNull Response<List<retail>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productRetail = response.body();
                    String ID = productRetail.get(0).getId();
                    RetailNames = new ArrayList<>();
                    for (int i = 0; i < productRetail.size(); i++) {
                        RetailNames.add(productRetail.get(i).getName());
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<retail>> call, @NonNull Throwable t) {
                //write own action
            }
        });
    }




    public void getSize() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<size>> call;
        call = apiInterface.getSize();
        call.enqueue(new Callback<List<size>>() {
            @Override
            public void onResponse(@NonNull Call<List<size>> call, @NonNull Response<List<size>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dataSize = response.body();
                    String ID = dataSize.get(0).getId();
                    SizeNames = new ArrayList<>();
                    for (int i = 0; i < dataSize.size(); i++) {
                        SizeNames.add(dataSize.get(i).getName());
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<size>> call, @NonNull Throwable t) {
                //write own action
            }
        });
    }





    //select data customer
    public void get_datacustomer() {
        loading=new ProgressDialog(EditCustActivity.this);
        loading.setCancelable(false);
        loading.setMessage(getString(R.string.please_wait));
        loading.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Customer>> call;
        call = apiInterface.get_customer(Customer_id.toString());
        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(@NonNull Call<List<Customer>> call, @NonNull Response<List<Customer>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DatabCustomer = response.body();
                    loading.dismiss();

                    String customer_type = DatabCustomer.get(0).getCustomer_type();
                    String OutletTypes = DatabCustomer.get(0).getOutletType();
                    String OutletSizes = DatabCustomer.get(0).getOutletSize();
                    String customer_names = DatabCustomer.get(0).getCustomer_name();
                    String villages = DatabCustomer.get(0).getVillage();
                    String district = DatabCustomer.get(0).getDistrict();
                    String province = DatabCustomer.get(0).getprovince();
                    String phone = DatabCustomer.get(0).getphone();
                    String fax = DatabCustomer.get(0).getfax();
                    String email = DatabCustomer.get(0).getemail();
                    String remark = DatabCustomer.get(0).getremark();


                    if (customer_type.equals("001")){
                        etxtProductCategory.setText("ຂາຍຍົກ");
                    } else if (customer_type.equals("002")) {
                        etxtProductCategory.setText("ຂາຍລູກຄ້າປະຈຳ");
                    }else {
                        etxtProductCategory.setText("ຂາຍລູກຄ້າທົ່ວໄປ");
                    }
                    OutletType.setText(OutletTypes);
                    OutletSize.setText(OutletSizes);
                    tcustomer_name.setText(customer_names);
                    tvillage.setText(villages);
                    tdistrict.setText(district);
                    tprovince.setText(province);
                    tphone.setText(phone);
                    tfax.setText(fax);
                    temail.setText(email);
                    tremark.setText(remark);

                    //  getProductsDatas(finvoiceId);
                    //   Toasty.success(EditvisitActivity.this, "Successfuly", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Customer>> call, @NonNull Throwable t) {
                Toast.makeText(EditCustActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                Log.d("Error : ", t.toString());
            }
        });
    }





    private void deleteCustomer() {
        loading = new ProgressDialog(this);
        loading.setCancelable(false);
        loading.setMessage(getString(R.string.please_wait));
        loading.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Customer> call = apiInterface.delete_customer(Customer_id.toString());
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(@NonNull Call<Customer> call, @NonNull Response<Customer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String value = response.body().getValue();

                        if (value.equals(Constant.KEY_SUCCESS)) {
                            loading.dismiss();
                            Toasty.success(EditCustActivity.this, "Delete successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditCustActivity.this, CustrouteActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }catch (Exception e){

                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<Customer> call, Throwable t) {
                Toast.makeText(EditCustActivity.this, "Error! " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }





    //for back button
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {// app icon in action bar clicked; goto parent activity.
//            this.finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }



}