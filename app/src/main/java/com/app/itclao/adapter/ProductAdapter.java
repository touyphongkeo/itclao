package com.app.itclao.adapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.itclao.image.Get_imageshow;
import com.app.itclao.pos.PosActivity;
import static com.app.itclao.ClassLibs.Tbname;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.app.itclao.Constant;
import com.app.itclao.database.DatabaseAccess;
import com.app.itclao.model.Product;
import com.app.itclao.utils.Utils;
import com.app.onlinesmartpos.R;
import com.bumptech.glide.Glide;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import static com.app.itclao.ClassLibs.Status;
import static com.app.itclao.ClassLibs.SALE_BILL;
import static com.app.itclao.ClassLibs.Image;
import static com.app.itclao.ClassLibs.Customer_id;
import static com.app.itclao.ClassLibs.UNit;
import static com.app.itclao.ClassLibs.Customer_type;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import static com.app.itclao.ClassLibs.shopEmail;
import static com.app.itclao.ClassLibs.Username;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.Tel;
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    private List<Product> customerData;
    private Context context;
    Utils utils;
    MediaPlayer player;
    DecimalFormat f;
    DatabaseAccess databaseAccess;

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String username ="";
    Integer cust =0;
    TextView table;


    public ProductAdapter(Context context, List<Product> customerData) {
    this.context = context;
    this.customerData = customerData;

    utils=new Utils();
    player = MediaPlayer.create(context, R.raw.delete_sound);
    databaseAccess = DatabaseAccess.getInstance(context);
    f = new DecimalFormat("#,###");

    sp = context.getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
    editor = sp.edit();
    username = sp.getString(Constant.SP_EMAIL, "");


    }
    @NonNull
    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pos2_product_item, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ProductAdapter.MyViewHolder holder, int position) {
        final String Id = customerData.get(position).getID();
        String stockin_id = customerData.get(position).gettransfer_id();
        String stockin_date = customerData.get(position).gettransfer_date();
        String stock_id = customerData.get(position).getstock_id();
        String product_id = customerData.get(position).getproduct_id();
        String product_lot_id = customerData.get(position).getproduct_lot_id();
        String price = customerData.get(position).getprice();
        String sell_price = customerData.get(position).getsell_price();
        String qty = customerData.get(position).getqty();
        String supplier_id = customerData.get(position).getsupplier_id();
        String staff_id = customerData.get(position).getstaff_id();
        String expert_date = customerData.get(position).getexpert_date();
        String Product_ID = customerData.get(position).getProduct_ID();
        String Product_Name = customerData.get(position).getProduct_Name();
        String Product_Name_EN = customerData.get(position).getProduct_Name_EN();
        String Bar_Code = customerData.get(position).getBar_Code();
        String Group_ID = customerData.get(position).getGroup_ID();
        String Unit = customerData.get(position).getUnit();
        String QTY = customerData.get(position).getQTY();
        String Price = customerData.get(position).getPrice();
        String ups = customerData.get(position).getups();
        String size = customerData.get(position).getsize();
        String version = customerData.get(position).getversion();
        String Remark = customerData.get(position).getRemark();
        String img = customerData.get(position).getimg();
        String img_name = customerData.get(position).getimg_name();
        String s1_price = customerData.get(position).gets1_price();
        String gets2_price = customerData.get(position).gets2_price();
        String gets3_price = customerData.get(position).gets3_price();



        String imageUrl= Constant.PRODUCT_IMAGE_URL+img;
        int bb = Integer.parseInt(qty);
        int aa = 5;
        if (bb>aa){
            holder.check_qty.setBackgroundColor(Color.parseColor("#1566e0"));
            holder.check_qty.setText("ຈຳນວນໃນສາງ: "+qty+" "+Unit);
        }else {
            holder.check_qty.setBackgroundColor(Color.parseColor("#FB4106"));
            holder.check_qty.setText("ຈຳນວນຍັງເຫຼື່ອ: "+qty+" "+Unit);
        }


        try {
            holder.txt_product_name.setText(Product_Name);


            if (Status.equals("001")){
                DecimalFormat df = new DecimalFormat(
                        "#,###",
                        new DecimalFormatSymbols(new Locale("pt", "BR")));
                BigDecimal value = new BigDecimal(s1_price);
                holder.txt_price.setText("ລາຄາ: "+f.format(value) +" ກິບ");
            }else if (Status.equals("002")){
                DecimalFormat df = new DecimalFormat(
                        "#,###",
                        new DecimalFormatSymbols(new Locale("pt", "BR")));
                BigDecimal value2 = new BigDecimal(gets2_price);
                holder.txt_price.setText("ລາຄາ: "+f.format(value2) +" ກິບ");
            }else {
                DecimalFormat df = new DecimalFormat(
                        "#,###",
                        new DecimalFormatSymbols(new Locale("pt", "BR")));
                BigDecimal value3 = new BigDecimal(gets3_price);
                holder.txt_price.setText("ລາຄາ: "+f.format(value3) +" ກິບ");
            }


        }catch (Exception e){

        }


      if (img != null) {
            if (img.length() < 3) {
                holder.images.setImageResource(R.drawable.image_placeholder);
            } else {
                Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.image_placeholder)
                .into(holder.images);
            }
        }

        holder.card_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // Toast.makeText(context, Status.toString(), Toast.LENGTH_SHORT).show();
                player.start();
                Intent intent=new Intent(context, Get_imageshow.class);
                intent.putExtra("product_id",product_id);
                intent.putExtra("stock_id",stock_id);

                UNit = Unit;
                context.startActivity(intent);
            }
        });

        int ab = Integer.parseInt(qty);
        int cus = 1;
        //int cuty = Integer.parseInt(cut_qty);
        holder.txt_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String qty1 = holder.txt_number.getText().toString();
                    int get_qty = Integer.parseInt(qty1);
                    if (get_qty>=ab) {
                    Toasty.error(context, "ຈຳນວນສີນຄ້າໃນສ້າງບໍພຽງພໍ!", Toast.LENGTH_SHORT).show();
                    } else {
                    get_qty++;
                    holder.txt_number.setText("" + get_qty);
                    cust = get_qty;
                    }
                }catch (Exception e){
                }
            }
        });



        holder.txt_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qty1 = holder.txt_number.getText().toString();
                int get_qty = Integer.parseInt(qty1);
                if (get_qty==0) {
                Toasty.error(context, "ຜິດຜາດ!", Toast.LENGTH_SHORT).show();
                } else {
                get_qty--;
                holder.txt_number.setText("" + get_qty);
                cust = get_qty;
                }
            }
        });




        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               player.start();
              //  Image =img_url;
                databaseAccess.open();
                long date = System.currentTimeMillis();
                SimpleDateFormat sdfs = new SimpleDateFormat("yyyy");
                String dateStrings = sdfs.format(date);
                String sale_bill = dateStrings+"000001";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String sale_date = sdf.format(new Date());
                String sale_status = "1";
                String edit_sale = "1";

                if (cust.equals(0)){
                    Toasty.error(context, "ກະລຸນາກຳນົດຈຳນວນກອນ", Toast.LENGTH_SHORT).show();
                }else {
                  String nb = String.valueOf(cust);
                  double vvqty = Double.parseDouble(nb);


                  if (Status.equals("001")){
                      double vprices = Double.parseDouble(s1_price);
                      double bbaount = vprices*vvqty;
                      databaseAccess.open();
                      //int check = databaseAccess.addToCart(sale_bill,sale_date,Tbname,product_id,product_name,price,cust.toString(),sale_status,edit_sale,username,Image,cut_qty);
                       int check = databaseAccess.addToCart(stock_id,product_id,s1_price,cust.toString(),bbaount,Product_Name,Unit,img);
                      databaseAccess.open();
                      int count=databaseAccess.getCartItemCount();
                      if (count==0) {
                          PosActivity.txtCount.setVisibility(View.INVISIBLE);
                      } else{
                          PosActivity. txtCount.setVisibility(View.VISIBLE);
                          PosActivity.txtCount.setText(String.valueOf(count));
                      }
                      if (check == 1) {
                          //   Toasty.success(context, "ສັງສຳເລັດ", Toast.LENGTH_SHORT).show();
                          player.start();
                      } else if (check == 2) {
                          Toasty.warning(context, "ສີນຄ້ານີ້ໃນຕາກາມີແລ້ວ", Toast.LENGTH_SHORT).show();
                      } else {
                          Toasty.error(context, "ຜິດຜາດ"+s1_price, Toast.LENGTH_SHORT).show();
                      }
                  }else if (Status.equals("002")){
                      double vprices = Double.parseDouble(gets2_price);
                      double bbaount = vprices*vvqty;


                      databaseAccess.open();
                      //int check = databaseAccess.addToCart(sale_bill,sale_date,Tbname,product_id,product_name,price,cust.toString(),sale_status,edit_sale,username,Image,cut_qty);
                      int check = databaseAccess.addToCart(stock_id,product_id,gets2_price,cust.toString(),bbaount,Product_Name,Unit,img);
                      databaseAccess.open();
                      int count=databaseAccess.getCartItemCount();
                      if (count==0) {
                          PosActivity.txtCount.setVisibility(View.INVISIBLE);
                      } else{
                          PosActivity. txtCount.setVisibility(View.VISIBLE);
                          PosActivity.txtCount.setText(String.valueOf(count));
                      }
                      if (check == 1) {
                          //   Toasty.success(context, "ສັງສຳເລັດ", Toast.LENGTH_SHORT).show();
                          player.start();
                      } else if (check == 2) {
                          Toasty.warning(context, "ສີນຄ້ານີ້ໃນຕາກາມີແລ້ວ", Toast.LENGTH_SHORT).show();
                      } else {
                          Toasty.error(context, "ຜິດຜາດ"+gets2_price, Toast.LENGTH_SHORT).show();
                      }


                  }else {
                      double vprices = Double.parseDouble(gets3_price);
                      double bbaount = vprices*vvqty;
                      databaseAccess.open();
                      //int check = databaseAccess.addToCart(sale_bill,sale_date,Tbname,product_id,product_name,price,cust.toString(),sale_status,edit_sale,username,Image,cut_qty);
                      int check = databaseAccess.addToCart(stock_id,product_id,gets3_price,cust.toString(),bbaount,Product_Name,Unit,img);
                      databaseAccess.open();
                      int count=databaseAccess.getCartItemCount();
                      if (count==0) {
                          PosActivity.txtCount.setVisibility(View.INVISIBLE);
                      } else{
                          PosActivity. txtCount.setVisibility(View.VISIBLE);
                          PosActivity.txtCount.setText(String.valueOf(count));
                      }
                      if (check == 1) {
                          //   Toasty.success(context, "ສັງສຳເລັດ", Toast.LENGTH_SHORT).show();
                          player.start();
                      } else if (check == 2) {
                          Toasty.warning(context, "ສີນຄ້ານີ້ໃນຕາກາມີແລ້ວ", Toast.LENGTH_SHORT).show();
                      } else {
                          Toasty.error(context, "ຜິດຜາດ"+gets3_price, Toast.LENGTH_SHORT).show();
                      }


                  }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return customerData.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_product_name,txt_price,txt_number,check_qty;
        CircleImageView images;
        CardView card_product;
        Button btnAddToCart,txt_plus,txt_minus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_price = itemView.findViewById(R.id.txt_price);
            images = itemView.findViewById(R.id.images);
            txt_number = itemView.findViewById(R.id.txt_number);
            btnAddToCart = itemView.findViewById(R.id.btn_add_cart);
            txt_plus = itemView.findViewById(R.id.txt_plus);
            txt_minus = itemView.findViewById(R.id.txt_minus);
            card_product = itemView.findViewById(R.id.card_product);
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
