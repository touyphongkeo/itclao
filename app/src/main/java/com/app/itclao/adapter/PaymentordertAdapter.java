package com.app.itclao.adapter;
import static com.app.itclao.ClassLibs.shopEmail;
import static com.app.itclao.ClassLibs.Username;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.Tel;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.itclao.Constant;
import com.app.itclao.model.paymentorder;
import com.app.itclao.model.productqty;
import com.app.itclao.utils.Utils;
import com.app.onlinesmartpos.R;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

public class PaymentordertAdapter extends RecyclerView.Adapter<PaymentordertAdapter.MyViewHolder> {


    private List<paymentorder> customerData;
    private Context context;
    Utils utils;
    DecimalFormat f;
    public PaymentordertAdapter(Context context, List<paymentorder> customerData) {
        this.context = context;
        this.customerData = customerData;
        utils=new Utils();

        f = new DecimalFormat("#,###");



    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.paymentorder_item, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        String sale_id = customerData.get(position).getPsale_id();
        String customer_id = customerData.get(position).getPcustomer_id();
        String pproduct_id = customerData.get(position).getPproduct_id();
        String sale_time = customerData.get(position).getPsale_time();
        String sale_date = customerData.get(position).getPsale_date();
        String price = customerData.get(position).getPprice();
        String qty = customerData.get(position).getPqty();
        String amount = customerData.get(position).getPamount();
        String payment = customerData.get(position).getPpayment();
        String pro_name = customerData.get(position).getPpro_name();
        String bill_no = customerData.get(position).getPbill_no();
        String urlimage = customerData.get(position).geturlimage();
        String imageUrl= Constant.PRODUCT_IMAGE_URL+urlimage;
        if (bill_no==null){
            holder.txt_customer_name.setText(sale_id+" "+pproduct_id);
        }else {
            holder.txt_customer_name.setText(bill_no+" "+pro_name);
        }

        double ap = Double.parseDouble(amount);
        holder.txt_email.setText("ລວມ: "+f.format(ap)+" ກິບ");
        holder.txt_cell.setText("ຫົວຫນ່ວຍ: "+price+"/ຈຳນວນ: "+qty);
        holder.txt_address.setText("ວັນທີເດືອນປີ: "+sale_date);



    /*    holder.img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finvoiceId=bil_no;
                Intent i = new Intent(context, PrinterActivity.class);
                context.startActivity(i);
            }
        });*/

   /*     holder.view_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finvoiceId=bil_no;
                Intent i = new Intent(context, OrderlistActivity.class);
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
