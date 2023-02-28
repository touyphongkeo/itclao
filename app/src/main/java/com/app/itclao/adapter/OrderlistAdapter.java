package com.app.itclao.adapter;


import static com.app.itclao.ClassLibs.finvoiceId;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.itclao.Constant;
import com.app.itclao.model.Order_list;
import com.app.itclao.model.payment;
import com.app.itclao.order_list.OrderlistActivity;
import com.app.itclao.print.PrinterActivity;
import com.app.itclao.utils.Utils;
import com.app.onlinesmartpos.R;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

import static com.app.itclao.ClassLibs.shopEmail;
import static com.app.itclao.ClassLibs.Username;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.Tel;
public class OrderlistAdapter extends RecyclerView.Adapter<OrderlistAdapter.MyViewHolder> {


    private List<Order_list> customerData;
    private Context context;
    Utils utils;
    DecimalFormat f;
    public OrderlistAdapter(Context context, List<Order_list> customerData) {
        this.context = context;
        this.customerData = customerData;
        utils=new Utils();

        f = new DecimalFormat("#,###");



    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final String proname = customerData.get(position).getpro_name();
        String totall = customerData.get(position).getamount();
        String price = customerData.get(position).getprice();
        String qty = customerData.get(position).getqty();
        String unit = customerData.get(position).getunit();
        String image = customerData.get(position).geturlimage();
        String imageUrl= Constant.PRODUCT_IMAGE_URL+image;
        holder.txt_customer_name.setText(proname);

        double ap = Double.parseDouble(price);
        holder.txt_email.setText("ລາຄາ: "+f.format(ap)+" ກິບ");

        double aqty = Double.parseDouble(qty);
        holder.txt_address.setText("ຈຳນວນ: "+f.format(aqty));

        double totalls = Double.parseDouble(totall);
        holder.txt_cell.setText("ເປັນເງີນ: "+f.format(totalls));


        if (image != null) {
            if (image.length() < 3) {
                holder.cart_product_image.setImageResource(R.drawable.image_placeholder);
            } else {
                Glide.with(context)
                        .load(imageUrl)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.image_placeholder)
                        .into(holder.cart_product_image);
            }
        }

   /*     holder.img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finvoiceId=bil_no;
                Intent i = new Intent(context, PrinterActivity.class);
                context.startActivity(i);
            }
        });*/

      /*  holder.view_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finvoiceId=bil_no;
                Intent i = new Intent(context, OrderlistActivity.class);
                context.startActivity(i);
            }
        });*/

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
            view_page = itemView.findViewById(R.id.view_page);
            cart_product_image = itemView.findViewById(R.id.cart_product_image);

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
