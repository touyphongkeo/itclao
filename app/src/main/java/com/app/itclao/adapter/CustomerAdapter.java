package com.app.itclao.adapter;

import static com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype.Slidetop;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.itclao.model.Customer;
import com.app.itclao.utils.Utils;
import com.app.onlinesmartpos.R;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.List;

import es.dmoral.toasty.Toasty;
import static com.app.itclao.ClassLibs.shopEmail;
import static com.app.itclao.ClassLibs.Username;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.Tel;
public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {


    private List<Customer> customerData;
    private Context context;
    Utils utils;


    public CustomerAdapter(Context context, List<Customer> customerData) {
        this.context = context;
        this.customerData = customerData;
        utils=new Utils();

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final String id = customerData.get(position).getCId();
        String customer_id = customerData.get(position).getCcustomer_id();
        String customer_type = customerData.get(position).getCustomer_type();
        String customer_name = customerData.get(position).getCustomer_name();
        String village = customerData.get(position).getVillage();
        String district = customerData.get(position).getDistrict();
        String province = customerData.get(position).getprovince();
        String tel = customerData.get(position).getphone();
        String fax = customerData.get(position).getfax();
        String remark = customerData.get(position).getremark();
        String OutletSize = customerData.get(position).getOutletSize();
        String OutletType = customerData.get(position).getOutletType();
        String Rount_NB = customerData.get(position).getRount_NB();
        String stock_id = customerData.get(position).getstock_id();

        holder.txtCustomerName.setText(customer_name);
        holder.txtCell.setText(tel);
        holder.txtEmail.setText(fax);
        holder.txtAddress.setText(village);


        holder.imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                String phone = "tel:" + tel;
                callIntent.setData(Uri.parse(phone));
                context.startActivity(callIntent);
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(context);
                dialogBuilder
                        .withTitle(context.getString(R.string.delete))
                        .withMessage(context.getString(R.string.want_to_delete_customer))
                        .withEffect(Slidetop)
                        .withDialogColor("#2979ff") //use color code for dialog
                        .withButton1Text(context.getString(R.string.yes))
                        .withButton2Text(context.getString(R.string.cancel))
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                if (utils.isNetworkAvailable(context)) {
                                 //   deleteCustomer(customer_id);
                                    customerData.remove(holder.getAdapterPosition());
                                    dialogBuilder.dismiss();
                                }
                                else
                                {
                                    dialogBuilder.dismiss();
                                    Toasty.error(context, R.string.no_network_connection, Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setButton2Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialogBuilder.dismiss();
                            }
                        })
                        .show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return customerData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtCustomerName, txtCell, txtEmail, txtAddress;
        ImageView imgDelete, imgCall;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCustomerName = itemView.findViewById(R.id.txt_customer_name);
            txtCell = itemView.findViewById(R.id.txt_cell);
            txtEmail = itemView.findViewById(R.id.txt_email);
            txtAddress = itemView.findViewById(R.id.txt_address);

            imgDelete = itemView.findViewById(R.id.img_delete);
            imgCall = itemView.findViewById(R.id.img_call);


            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
//            Intent i = new Intent(context, EditCustomersActivity.class);
//            i.putExtra("customer_id", customerData.get(getAdapterPosition()).getCustomerId());
//            i.putExtra("customer_name", customerData.get(getAdapterPosition()).getCustomerName());
//            i.putExtra("customer_cell", customerData.get(getAdapterPosition()).getCustomerCell());
//            i.putExtra("customer_email", customerData.get(getAdapterPosition()).getCustomerEmail());
//            i.putExtra("customer_address", customerData.get(getAdapterPosition()).getCustomerAddress());
//            context.startActivity(i);
        }
    }


    //delete from server
//    private void deleteCustomer(String customerId) {
//
//        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//
//        Call<Customer> call = apiInterface.deleteCustomer(customerId);
//        call.enqueue(new Callback<Customer>() {
//            @Override
//            public void onResponse(@NonNull Call<Customer> call, @NonNull Response<Customer> response) {
//
//
//                if (response.isSuccessful() && response.body() != null) {
//
//                    String value = response.body().getValue();
//
//                    if (value.equals(Constant.KEY_SUCCESS)) {
//                        Toasty.error(context, R.string.customer_deleted, Toast.LENGTH_SHORT).show();
//                        notifyDataSetChanged();
//
//                    }
//
//                    else if (value.equals(Constant.KEY_FAILURE)){
//                        Toasty.error(context, R.string.error, Toast.LENGTH_SHORT).show();
//                    }
//
//                    else {
//                        Toast.makeText(context, R.string.no_network_connection, Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<Customer> call, Throwable t) {
//                Toast.makeText(context, "Error! " + t.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


}
