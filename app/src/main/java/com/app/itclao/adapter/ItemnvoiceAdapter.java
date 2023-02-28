package com.app.itclao.adapter;

import static com.app.itclao.ClassLibs.finvoiceId;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.itclao.Constant;
import com.app.itclao.invoice.ItemnvoiceActivity;
import com.app.itclao.model.Invoince;
import com.app.itclao.model.iteminvoice;
import com.app.itclao.utils.Utils;
import com.app.onlinesmartpos.R;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;
import static com.app.itclao.ClassLibs.shopEmail;
import static com.app.itclao.ClassLibs.Username;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.Tel;
public class ItemnvoiceAdapter extends RecyclerView.Adapter<ItemnvoiceAdapter.MyViewHolder> {


    private List<iteminvoice> customerData;
    private Context context;
    Utils utils;
    DecimalFormat f;
    public ItemnvoiceAdapter(Context context, List<iteminvoice> customerData) {
        this.context = context;
        this.customerData = customerData;
        utils=new Utils();

        f = new DecimalFormat("#,###");



    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemnvoice_item, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        String bill_no = customerData.get(position).getIbill_nos();
        String pro_name = customerData.get(position).getIpro_name();
        String unit = customerData.get(position).getIunit();
        String price = customerData.get(position).getIprice();
        String qty = customerData.get(position).getIqty();
        String amount = customerData.get(position).getIamount();
        String payment = customerData.get(position).getIpayment();
        String urlimage = customerData.get(position).getIurlimage();
        String pay_name = customerData.get(position).getIpay_name();

        String imageUrl= Constant.PRODUCT_IMAGE_URL+urlimage;

        holder.txt_customer_name.setText(pro_name);
        holder.txt_cell.setText("ລາຄາ: "+price+"x"+qty);
        double ap = Double.parseDouble(amount);
        holder.txt_email.setText("ລວມ: "+f.format(ap)+" ກິບ");

    /*    if (pay_name.equals("ຕິດໜີ")){
            holder.img_call.setImageResource(R.drawable.ic_baseline_payment_24);
            holder.txt_address.setBackgroundColor(Color.parseColor("#FB4106"));
            holder.txt_address.setText("ຮູບແບບການຈ່າຍ: "+payment);
        }else {
            holder.img_call.setImageResource(R.drawable.ic_baseline_payment_24s);
            holder.txt_address.setBackgroundColor(Color.parseColor("#1566e0"));
           */
        if (pay_name.equals("ຕິດໜີ")){
            holder.txt_address.setBackgroundColor(Color.parseColor("#FB4106"));
            holder.txt_address.setText("ຍັງບໍຊຳລະເງີນ: 0,00 ກິບ");
        }else {
            holder.txt_address.setBackgroundColor(Color.parseColor("#1566e0"));
            holder.txt_address.setText("ຊຳລະເງີນແລ້ວ: "+payment+" ກິບ");
        }




    /*    holder.img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finvoiceId=bil_no;
                Intent i = new Intent(context, PrinterActivity.class);
                context.startActivity(i);
            }
        });*/

    /*    holder.view_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finvoiceId=bill_no;
                Intent i = new Intent(context, ItemnvoiceActivity.class);
                context.startActivity(i);
            }
        });*/


       if (urlimage != null) {
            if (urlimage.length() < 3) {
                holder.cart_product_image.setImageResource(R.drawable.image_placeholder);
            } else {
                Glide.with(context)
                        .load(imageUrl)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.image_placeholder)
                        .into(holder.cart_product_image);
            }
        }

    }



    @Override
    public int getItemCount() {
        return customerData.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_customer_name,txt_cell,txt_email,txt_address;
        ImageView img_call,cart_product_image;
        CardView view_page;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_customer_name = itemView.findViewById(R.id.txt_customer_name);
            txt_cell = itemView.findViewById(R.id.txt_cell);
            txt_email = itemView.findViewById(R.id.txt_email);
            txt_address = itemView.findViewById(R.id.txt_address);
            img_call = itemView.findViewById(R.id.img_call);
            cart_product_image = itemView.findViewById(R.id.cart_product_image);
            view_page = itemView.findViewById(R.id.view_page);

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






}
