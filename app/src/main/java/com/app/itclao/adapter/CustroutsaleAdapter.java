package com.app.itclao.adapter;

import static com.app.itclao.ClassLibs.Customer_id;
import static com.app.itclao.ClassLibs.Customer_type;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.itclao.model.custroute;
import com.app.itclao.pos.PosActivity;
import com.app.itclao.prodcutpos.PsosActivity;
import com.app.itclao.table.MtableActivity;
import com.app.itclao.utils.Utils;
import com.app.onlinesmartpos.R;

import java.util.List;
import static com.app.itclao.ClassLibs.shopEmail;
import static com.app.itclao.ClassLibs.Username;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.Tel;
public class CustroutsaleAdapter extends RecyclerView.Adapter<CustroutsaleAdapter.MyViewHolder> {

    private List<custroute> customerData;
    private Context context;
    Utils utils;
    public CustroutsaleAdapter(Context context, List<custroute> customerData) {
        this.context = context;
        this.customerData = customerData;
        utils=new Utils();



    }


    @NonNull
    @Override
    public CustroutsaleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custroute_item, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final CustroutsaleAdapter.MyViewHolder holder, int position) {
        final String customer_id = customerData.get(position).getcustomer_id();
        String customer_type = customerData.get(position).getcustomer_type();
        String customer_name = customerData.get(position).getcustomer_name();
        String stock_id = customerData.get(position).getstock_id();
        String route_id = customerData.get(position).getroute_id();
        holder.txt_category_name.setText("ລະຫັດ "+customer_id);
        holder.check_qty.setText("ຮ້ານ "+customer_name);

        holder.imgStatus.setImageResource(R.drawable.cxz);




        holder.card_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, MtableActivity.class);
                Customer_type=customer_type;
                Customer_id=customer_id;
                context.startActivity(i);
            }
        });
    }



    @Override
    public int getItemCount() {
        return customerData.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_category_name,check_qty;
        CardView card_category;
        ImageView imgStatus;
        LinearLayout layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_category_name = itemView.findViewById(R.id.txt_category_name);
            imgStatus = itemView.findViewById(R.id.imgStatus);
            card_category = itemView.findViewById(R.id.card_category);
            layout = itemView.findViewById(R.id.layout);
            check_qty = itemView.findViewById(R.id.check_qty);


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
