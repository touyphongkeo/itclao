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
import com.app.itclao.model.paymentorder;
import com.app.itclao.model.viewpaymoney;
import com.app.onlinesmartpos.R;
import static com.app.itclao.ClassLibs.shopEmail;
import static com.app.itclao.ClassLibs.Username;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.Tel;
import static com.app.itclao.ClassLibs.Fname;
import java.text.DecimalFormat;
import java.util.List;

public class Viewpayment2Adapter extends RecyclerView.Adapter<Viewpayment2Adapter.MyViewHolder> {


    Context context;
    private List<paymentorder> orderData;
    public static double subTotalPrice=0;
    SharedPreferences sp;
    String currency,total;
    DecimalFormat f;
    //final DecimalFormat f = new DecimalFormat("#,##0");
    DecimalFormat formatter = new DecimalFormat("#,###");

    TextView telphon,usernaes,idsssss,text_v2,text_v7,text_v9;



    public Viewpayment2Adapter(Context context, List<paymentorder> orderData, TextView telphon, TextView usernaes, TextView idsssss, TextView text_v2, TextView text_v7, TextView text_v9) {
        this.context = context;
        this.orderData = orderData;
        this.telphon = telphon;
        this.usernaes = usernaes;
        this.idsssss = idsssss;
        this.text_v2 = text_v2;
        this.text_v7 = text_v7;
        this.text_v9 = text_v9;

        sp = context.getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        currency = sp.getString(Constant.SP_CURRENCY_SYMBOL, "");
        f = new DecimalFormat("#,###,##0");

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpaymoney2, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.txtProductName.setText(orderData.get(position).getPsale_id());

        holder.txtProductPrice.setText(orderData.get(position).getcustomer_name());

        holder.txtTotalCost.setText(orderData.get(position).getPamount());

       // holder.txt_qty.setText(orderData.get(position).gettime());
        telphon.setText("ຜູ້ຂາຍ: "+shopEmail+"  "+orderData.get(position).getfnamesale());
       // usernaes.setText(orderData.get(position).getUser_id());
       // idsssss.setText("ລູກຄ້າ: "+orderData.get(position).getcustomer_id()+" "+orderData.get(position).getcus_name());

       // String amounprice = orderData.get(0).getamounprice();
      //  text_v2.setText(amounprice+ " ກິບ");


      //  text_v7.setText(amounprice+ " ກິບ");

      //  String payment = orderData.get(0).getpay_lak();
       // double onepay = Double.parseDouble(payment);
       // double onepay1 = Double.parseDouble(payment);
       // double totaill = onepay+onepay1;
       // text_v9.setText(f.format(totaill)+" ກິບ");

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