package com.app.itclao.adapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.itclao.Constant;
import com.app.itclao.model.totaill;
import com.app.itclao.model.viewpaymoney;
import com.app.onlinesmartpos.R;

import java.text.DecimalFormat;
import java.util.List;

public class TotaillAdapter extends RecyclerView.Adapter<TotaillAdapter.MyViewHolder> {


    Context context;
    private List<totaill> orderData;
    public static double subTotalPrice=0;
    SharedPreferences sp;
    String currency,total;
    DecimalFormat f;
    //final DecimalFormat f = new DecimalFormat("#,##0");
    DecimalFormat formatter = new DecimalFormat("#,###");

    TextView text_v9;



    public TotaillAdapter(Context context, List<totaill> orderData, TextView text_v9) {
        this.context = context;
        this.orderData = orderData;

        this.text_v9 = text_v9;

        sp = context.getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        currency = sp.getString(Constant.SP_CURRENCY_SYMBOL, "");
        f = new DecimalFormat("#,###,##0");

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpaymoney, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        //holder.text_v9.setText(orderData.get(position).getpaymoney());


       // holder.txt_qty.setText(orderData.get(position).gettime());
       // telphon.setText("ຜູ້ຂາຍ: "+orderData.get(position).getfnamesale()+"ເບິໂທ: "+orderData.get(position).gettelsale());
       // usernaes.setText(orderData.get(position).getUser_id());
       // idsssss.setText("ລູກຄ້າ: "+orderData.get(position).getcustomer_id()+" "+orderData.get(position).getcus_name());

       // String amounprice = orderData.get(0).getamounprice();
      //  text_v2.setText(amounprice+ " ກິບ");


      //  text_v7.setText(amounprice+ " ກິບ");

        String payment = orderData.get(0).getpaymoney();
        double onepay = Double.parseDouble(payment);

        text_v9.setText(f.format(onepay)+" ກິບ");

       // String billdiscoun = orderData.get(0).getbill_discount();
       // double billdiscouns = Double.parseDouble(billdiscoun);
       // text_v7.setText(f.format(billdiscouns)+" ກິບ");


       // String amount = orderData.get(position).getamount();
      //  double price = Double.parseDouble(amount);

       // if (price==0){
       //     holder.txtTotalCost.setText("ແຖມ");
       // }else {
       //     holder.txtTotalCost.setText(formatter.format(price));
       // }


      //  String pre = orderData.get(position).getprice();
      //  double ss = Double.parseDouble(pre);
      //  holder.txtProductPrice.setText(formatter.format(ss));

    }

    @Override
    public int getItemCount() {
        return orderData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

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