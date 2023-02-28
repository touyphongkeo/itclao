package com.app.itclao.adapter;
import static com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype.Slidetop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.itclao.Constant;
import com.app.itclao.database.DatabaseAccess;
import com.app.itclao.pos.ProductCart;
import com.app.onlinesmartpos.R;
import com.bumptech.glide.Glide;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import static com.app.itclao.ClassLibs.shopEmail;
import static com.app.itclao.ClassLibs.Username;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.Tel;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private List<HashMap<String, String>> cartProduct;
    private Context context;
    MediaPlayer player;
    TextView txtNoProduct;
    TextView txtTotalPrice;
    public static Double totalPrice;
    ImageView imgNoProduct;
    Button btnSubmitOrder;
    SharedPreferences sp;
    String currency;
    Integer cust =0;

    public CartAdapter(Context context, List<HashMap<String, String>> cartProduct, TextView txtTotalPrice, Button btnSubmitOrder, ImageView imgNoProduct, TextView txtNoProduct) {
        this.context = context;
        this.cartProduct = cartProduct;
        player = MediaPlayer.create(context, R.raw.delete_sound);
        this.txtTotalPrice = txtTotalPrice;
        this.btnSubmitOrder = btnSubmitOrder;
        this.imgNoProduct = imgNoProduct;
        this.txtNoProduct = txtNoProduct;
        sp = context.getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        currency = sp.getString(Constant.SP_CURRENCY_SYMBOL, "");
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_product_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        final String Id = cartProduct.get(position).get("Id");
        String stock_id = cartProduct.get(position).get("stock_id");
        final String product_id = cartProduct.get(position).get("product_id");
        final String pro_name = cartProduct.get(position).get("pro_name");
        final String unit = cartProduct.get(position).get("unit");
        final String sprice = cartProduct.get(position).get("sprice");
        final String qty = cartProduct.get(position).get("qty");
        final String img = cartProduct.get(position).get("img");
        String imageUrl= Constant.PRODUCT_IMAGE_URL+img;
        final DecimalFormat f = new DecimalFormat("#,###");



         //  double dvqty = Double.parseDouble(qty);

            databaseAccess.open();
            totalPrice = databaseAccess.getTotalPrice();


            holder.txt_item_name.setText(pro_name);
            holder.txt_weight.setText("ຂະຫນາດ: "+unit);
            holder.status.setText("ຈຳນວນ: "+qty);

            DecimalFormat dfs = new DecimalFormat(
                    "#,###.00",
                    new DecimalFormatSymbols(new Locale("pt", "BR")));
            BigDecimal values = new BigDecimal(sprice);
            holder.txt_price.setText(f.format(values)+" ກີບ");


            txtTotalPrice.setText("ຈຳນວນເງີນລວມ: "+f.format(totalPrice)+" ກິບ");



        if (img != null) {
            if (img.isEmpty()) {
                holder.cart_product_image.setImageResource(R.drawable.image_placeholder);
                holder.cart_product_image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            } else {


                Glide.with(context)
                        .load(imageUrl)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.image_placeholder)
                        .into(holder.cart_product_image);

            }
        }

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
                    databaseAccess.open();
                    boolean deleteProduct = databaseAccess.deleteProductFromCart(Id);
                    if (deleteProduct) {
                        //  Toasty.success(context, "ລົບສຳເລັດ", Toast.LENGTH_SHORT).show();
                        player.start();
                        cartProduct.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());

                        databaseAccess.open();
                        totalPrice = databaseAccess.getTotalPrice();
                        txtTotalPrice.setText("ຈຳນວນເງີນລວມ: "+f.format(totalPrice)+" ກິບ");
                    } else {
                        Toasty.error(context,"ຜິດພາດ", Toast.LENGTH_SHORT).show();
                    }

                    databaseAccess.open();
                    int itemCount = databaseAccess.getCartItemCount();
                    Log.d("itemCount", "" + itemCount);
                    if (itemCount <= 0) {
                        txtTotalPrice.setVisibility(View.GONE);
                        btnSubmitOrder.setVisibility(View.GONE);
                        imgNoProduct.setVisibility(View.VISIBLE);
                        txtNoProduct.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){

                }

            }

        });








        holder.img_deleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(context);
                dialogBuilder
                        .withTitle("ຄຳເຕືອນ")
                        .withMessage("ທານຕ້ອງການແຖມສີນຄ້ານີ້ແທ້ ຫຼື ບໍ ?")
                        .withEffect(Slidetop)
                        .withDialogColor("#2979ff") //use color code for dialog
                        .withButton1Text(context.getString(R.string.yes))
                        .withButton2Text(context.getString(R.string.cancel))
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                player.start();
                                cartProduct.remove(holder.getAdapterPosition());
                                //notifyItemRemoved(holder.getAdapterPosition());

                                databaseAccess.open();
                                totalPrice = databaseAccess.getTotalPrice();
                                txtTotalPrice.setText("ຈຳນວນເງີນລວມ: "+f.format(totalPrice)+" ກິບ");

                                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
                                databaseAccess.open();
                                boolean deleteProduct = databaseAccess.updatePro(Id,qty);

                                if (deleteProduct){

                                    Intent i = new Intent(context, ProductCart.class);
                                    Toasty.success(context, "ແຖມສຳເລັດ", Toast.LENGTH_SHORT).show();
                                    context.startActivity(i);
                                }else {
                                    Intent i = new Intent(context, ProductCart.class);
                                    Toasty.error(context, "ຜິດພາດ", Toast.LENGTH_SHORT).show();
                                    context.startActivity(i);
                                }

                            }
                        })
                        .setButton2Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialogBuilder.dismiss();
                            }
                        })
                        .show();


            }
        });










        //ການເພີ້ມຈຳນວນໃນກະຕາ
      holder.txtPlus.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                try {
                        player.start();
                        String qty1 = holder.txtQtyNumber.getText().toString();
                        int get_qty = Integer.parseInt(qty1);
                        get_qty++;
                        holder.txtQtyNumber.setText("" + get_qty);
                        cust = get_qty;
                        int b = Integer.valueOf(qty);
                        double ab = get_qty+b;
                        String bv = String.valueOf(f.format(ab));
                        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
                        databaseAccess.open();
                        databaseAccess.updateProductQty(Id, "" + bv);
                        holder.status.setText("ຈຳນວນ:"+bv);
                        totalPrice = totalPrice + Double.valueOf(sprice);
                        txtTotalPrice.setText("ຈຳນວນເງີນລວມ: "+f.format(totalPrice)+" ກິບ");

                }catch (Exception e){
                }
             }
        });


        //ການລົດຈຳນວນລົງໃນກະຕາ
      holder.txt_minus.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                        player.start();
                        String qty1 = holder.txtQtyNumber.getText().toString();
                        int get_qty = Integer.parseInt(qty1);
                        if (get_qty==0){
                        Toasty.error(context,"ຜິດພາດ", Toast.LENGTH_SHORT).show();
                        }else {
                        get_qty--;
                        holder.txtQtyNumber.setText("" + get_qty);
                        cust = get_qty;
                        int b = Integer.valueOf(qty);
                        double ab = get_qty + b;
                        String bv = String.valueOf(f.format(ab));
                        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
                        databaseAccess.open();
                        databaseAccess.updateProductQty(Id, "" + bv);
                        holder.status.setText("ຈຳນວນ:" + bv);
                        totalPrice = totalPrice + Double.valueOf(sprice);
                        txtTotalPrice.setText("ຈຳນວນເງີນລວມ: "+f.format(totalPrice)+" ກິບ");
                    }

            }
        });



    }
    @Override
    public int getItemCount() {
    return cartProduct.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
    TextView txt_item_name,txt_weight,txtQtyNumber,txtPlus,txt_minus,txt_price,status;
    ImageView img_delete,img_deleted;
    CircleImageView cart_product_image;
    public MyViewHolder(View itemView) {
    super(itemView);
            txt_item_name = itemView.findViewById(R.id.txt_item_name);
            txt_item_name = itemView.findViewById(R.id.txt_item_name);
            txt_weight = itemView.findViewById(R.id.txt_weight);
            txt_price = itemView.findViewById(R.id.txt_price);
            cart_product_image = itemView.findViewById(R.id.cart_product_image);
            img_delete = itemView.findViewById(R.id.img_delete);
            txtQtyNumber = itemView.findViewById(R.id.txt_number);
            txt_minus = itemView.findViewById(R.id.txt_minus);
            txtPlus = itemView.findViewById(R.id.txt_plus);
            status = itemView.findViewById(R.id.status);
            img_deleted = itemView.findViewById(R.id.img_deleted);
        }
    }
}
