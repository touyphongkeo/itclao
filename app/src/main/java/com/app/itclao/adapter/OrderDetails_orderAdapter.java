package com.app.itclao.adapter;
import static com.app.itclao.ClassLibs.shopEmail;
import static com.app.itclao.ClassLibs.Username;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.Tel;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.itclao.database.DatabaseAccess;
import com.app.onlinesmartpos.R;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

public class OrderDetails_orderAdapter extends RecyclerView.Adapter<OrderDetails_orderAdapter.MyViewHolder> {
    Context context;
    private List<HashMap<String, String>> orderData;
    DecimalFormat f;
    DecimalFormat formatter = new DecimalFormat("#,###");
    public OrderDetails_orderAdapter(Context context, List<HashMap<String, String>> orderData) {
        this.context = context;
        this.orderData = orderData;
        f = new DecimalFormat("#0.00");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_details_item_bill, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        String id = orderData.get(position).get("id");
        String stock_id = orderData.get(position).get("stock_id");
        String product_id = orderData.get(position).get("product_id");
        String sprice = orderData.get(position).get("sprice");
        String qty = orderData.get(position).get("qty");
        String pro_name = orderData.get(position).get("pro_name");
        String unit = orderData.get(position).get("unit");
        String img = orderData.get(position).get("img");



        holder.txtProductName.setText(pro_name);

        double ss = Double.parseDouble(sprice);
        holder.txtProductPrice.setText(formatter.format(ss));

        holder.txt_qty.setText(qty);

        double vqty = Double.parseDouble(qty);
        double bbbaount = ss*vqty;

        holder.txtTotalCost.setText(formatter.format(bbbaount));
    }

    @Override
    public int getItemCount() {
        return orderData.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductName, txtProductPrice, txtTotalCost,txt_qty;


        public MyViewHolder(View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.txt_product_name);
            txtProductPrice = itemView.findViewById(R.id.txt_price);

            txtTotalCost = itemView.findViewById(R.id.txt_total_cost);
            txt_qty = itemView.findViewById(R.id.txt_qty);
        }
    }
}