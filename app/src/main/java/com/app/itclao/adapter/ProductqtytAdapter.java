package com.app.itclao.adapter;
import static com.app.itclao.ClassLibs.shopEmail;
import static com.app.itclao.ClassLibs.Username;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.Tel;
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

import com.app.itclao.model.payment;
import com.app.itclao.model.productqty;
import com.app.itclao.order_list.OrderlistActivity;
import com.app.itclao.print.PrinterActivity;
import com.app.itclao.utils.Utils;
import com.app.onlinesmartpos.R;

import java.text.DecimalFormat;
import java.util.List;
public class ProductqtytAdapter extends RecyclerView.Adapter<ProductqtytAdapter.MyViewHolder> {


    private List<productqty> customerData;
    private Context context;
    Utils utils;
    DecimalFormat f;
    public ProductqtytAdapter(Context context, List<productqty> customerData) {
        this.context = context;
        this.customerData = customerData;
        utils=new Utils();

        f = new DecimalFormat("#,###");



    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productqty_item, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        String qty = customerData.get(position).getproductqty();
        String stockin_date = customerData.get(position).getstockin_date();
        String Product_ID = customerData.get(position).getstockProduct_ID();
        String Product_Name = customerData.get(position).getstockProduct_Name();
        String Unit = customerData.get(position).getproductUnit();
        String Group_Name = customerData.get(position).getproductGroup_Name();
        String version = customerData.get(position).getproductversion();
        String ups = customerData.get(position).getproduups();
        String Price = customerData.get(position).getprosPrice();

        holder.txt_customer_name.setText(Product_ID+" "+Product_Name);
        holder.txt_cell.setText("ຫົວຫນ່ວຍ: "+Unit+"/ຈຳນວນ: "+qty);
        double ap = Double.parseDouble(Price);
        holder.txt_email.setText("ລາຄາຕົ້ນທືນ: "+f.format(ap)+" ກິບ");
        holder.txt_address.setText("ວັນທີເດືອນປີ: "+stockin_date);
       /*



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

    }



    @Override
    public int getItemCount() {
        return customerData.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_customer_name,txt_cell,txt_email,txt_address;
        ImageView img_call;
        CardView view_page;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_customer_name = itemView.findViewById(R.id.txt_customer_name);
            txt_cell = itemView.findViewById(R.id.txt_cell);
            txt_email = itemView.findViewById(R.id.txt_email);
            txt_address = itemView.findViewById(R.id.txt_address);
            img_call = itemView.findViewById(R.id.img_call);
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
