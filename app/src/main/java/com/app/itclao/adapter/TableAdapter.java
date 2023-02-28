package com.app.itclao.adapter;
import static com.app.itclao.ClassLibs.shopEmail;
import static com.app.itclao.ClassLibs.Username;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.Tel;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.itclao.Constant;
import com.app.itclao.HomeActivity;
import com.app.itclao.model.Item;
import com.app.itclao.model.Table;
import com.app.itclao.networking.ApiClient;
import com.app.itclao.networking.ApiInterface;
import com.app.itclao.pos.Pos2Activity;
import com.app.itclao.pos.PosActivity;
import com.app.itclao.utils.Utils;
import com.app.onlinesmartpos.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.List;
import static com.app.itclao.ClassLibs.Status;
import static com.app.itclao.ClassLibs.SALE_BILL;
import static com.app.itclao.ClassLibs.Tbname;

import static com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype.Slidetop;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class TableAdapter extends RecyclerView.Adapter<TableAdapter.MyViewHolder> {

    private List<Item> customerData;
    private Context context;
    Utils utils;
    public TableAdapter(Context context, List<Item> customerData) {
        this.context = context;
        this.customerData = customerData;
        utils=new Utils();



    }


    @NonNull
    @Override
    public TableAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_item, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final TableAdapter.MyViewHolder holder, int position) {
        final String id = customerData.get(position).getId();
        String stock_name = customerData.get(position).getName();
        holder.txt_category_name.setText(stock_name);


        if (id.equals("001")){
            holder.imgStatus.setImageResource(R.drawable.priceone);
        }else if (id.equals("002")){
            holder.imgStatus.setImageResource(R.drawable.pricestwo);
        }else {
            holder.imgStatus.setImageResource(R.drawable.pricethree);
        }



        holder.card_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Status = id;
                SALE_BILL = stock_name;
                Intent i = new Intent(context, PosActivity.class);
                context.startActivity(i);
            }
        });
    }



    @Override
    public int getItemCount() {
        return customerData.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_category_name;
        CardView card_category;
        ImageView imgStatus;
        LinearLayout layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_category_name = itemView.findViewById(R.id.txt_category_name);
            imgStatus = itemView.findViewById(R.id.imgStatus);
            card_category = itemView.findViewById(R.id.card_category);
            layout = itemView.findViewById(R.id.layout);


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
