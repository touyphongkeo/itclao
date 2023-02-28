package com.app.itclao.adapter;
import static com.app.itclao.ClassLibs.shopEmail;
import static com.app.itclao.ClassLibs.Username;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.Tel;
import static com.app.itclao.ClassLibs.Route_id;
import static com.app.itclao.ClassLibs.Status;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.app.itclao.ClassLibs.stock_id;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.itclao.custroute.CustrouteActivity;
import com.app.itclao.model.Item;
import com.app.itclao.model.tbroute;
import com.app.itclao.pos.PosActivity;
import com.app.itclao.utils.Utils;
import com.app.onlinesmartpos.R;

import java.util.List;

public class TbrouteAdapter extends RecyclerView.Adapter<TbrouteAdapter.MyViewHolder> {

    private List<tbroute> customerData;
    private Context context;
    String stoc_id;
    Utils utils;
    public TbrouteAdapter(Context context, List<tbroute> customerData) {
        this.context = context;
        this.customerData = customerData;
        utils=new Utils();



    }


    @NonNull
    @Override
    public TbrouteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tbroute_item, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final TbrouteAdapter.MyViewHolder holder, int position) {
        final String id = customerData.get(position).getID();
        String route_id = customerData.get(position).getroute_id();
        String route_name = customerData.get(position).getroute_name();
        String User_ID = customerData.get(position).getUser_ID();
        holder.txt_category_name.setText(route_id+" "+route_name);


        if (id.equals("001")){
            holder.imgStatus.setImageResource(R.drawable.bvfd);
        }else if (id.equals("002")){
            holder.imgStatus.setImageResource(R.drawable.bvfd);
        }else {
            holder.imgStatus.setImageResource(R.drawable.bvfd);
        }



        holder.card_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, stock_id, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, CustrouteActivity.class);
                Route_id=route_id;
              /*  i.putExtra("product_id",route_id);
                i.putExtra(stock_id,stoc_id);*/
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
