package com.app.itclao;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.app.itclao.about.AboutActivity;
import com.app.itclao.database.DatabaseAccess;
import com.app.itclao.invoice.InvoiceActivity;
import com.app.itclao.paymentorder.PamentorderActivity;
import com.app.itclao.pos.PosActivity;
import com.app.itclao.prductqty.ProductqtyActivity;
import com.app.itclao.print.PrinterActivity;
import com.app.itclao.prodcutpos.PsosActivity;
import com.app.itclao.routesale.TbroutsaleActivity;
import com.app.itclao.table.Mtable2Activity;
import com.app.itclao.table.MtableActivity;
import com.app.itclao.tbroute.TbrouteActivity;
import com.app.onlinesmartpos.R;
import static com.app.itclao.ClassLibs.stock_id;
import com.app.itclao.login.LoginActivity;
import static com.app.itclao.ClassLibs.Status;
import static com.app.itclao.ClassLibs.Tbname;
import static com.app.itclao.ClassLibs.SALE_BILL;
import com.app.itclao.utils.BaseActivity;
import com.app.itclao.utils.LocaleManager;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.stock_id;

import static com.app.itclao.ClassLibs.shopEmail;
import static com.app.itclao.ClassLibs.Username;
import static com.app.itclao.ClassLibs.Fname;
import static com.app.itclao.ClassLibs.shopEmail;
import static com.app.itclao.ClassLibs.Username;
import static com.app.itclao.ClassLibs.stock_id;
import static com.app.itclao.ClassLibs.Tel;

import es.dmoral.toasty.Toasty;

import static com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype.Slidetop;
import static com.app.itclao.ClassLibs.Userid;
public class HomeActivity extends BaseActivity {
    CardView  cardLogout,card_table,card_pos,view1,productqty,Cardviewpay,Invoicepay,nnnardvisit;
    //for double back press to exit
    private static final int TIME_DELAY = 2000;
    private static long backPressed;

    private Context context;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String userType;
    TextView txtShopName,txtSubText,use,stock_id;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        getSupportActionBar().hide();


        cardLogout = findViewById(R.id.card_logout);
        card_table = findViewById(R.id.card_table);
        use = findViewById(R.id.use);
        card_pos = findViewById(R.id.card_pos);
        view1 = findViewById(R.id.view1);
        stock_id = findViewById(R.id.stock_id);
        productqty = findViewById(R.id.productqty);
        Cardviewpay = findViewById(R.id.Cardviewpay);
        Invoicepay = findViewById(R.id.Invoicepay);
        nnnardvisit = findViewById(R.id.nnnardvisit);




        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();

        String email = sp.getString(Constant.SP_EMAIL, "");
        String username = sp.getString(Constant.USERNAMES, "");
        String stock_ids = sp.getString(Constant.STOCK_ID, "");
        use.setText("ລະຫັດຜູ້ໃຊ້ລະບົບ: "+email);
        stock_id.setText("ຊື່ຜູ້ໃຊ້ລະບົບ: "+Username);


        //  userType = sp.getString(Constant.SP_USER_TYPE, "");
     //   String shopName = sp.getString(Constant.SP_SHOP_NAME, "");
     //   String staffName = sp.getString(Constant.SP_STAFF_NAME, "");
     //   txtShopName.setText(shopName);
     //   txtSubText.setText("Hi "+staffName);

/*
        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(HomeActivity.this);
        databaseAccess.open();
        databaseAccess.emptyCart();*/


        card_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Userid = use.toString();
                Intent intent = new Intent(HomeActivity.this, Mtable2Activity.class);
                startActivity(intent);
            }
        });


        nnnardvisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TbrouteActivity.class);
                startActivity(intent);

            }
        });




        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });



        Invoicepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, InvoiceActivity.class);
                startActivity(intent);
            }
        });


        Cardviewpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PamentorderActivity.class);
                startActivity(intent);
            }
        });

        productqty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProductqtyActivity.class);
                startActivity(intent);
            }
        });

        card_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PsosActivity.class);
                startActivity(intent);
             //   Toasty.success(HomeActivity.this, "ກຳລັງພັດທະນາ", Toast.LENGTH_SHORT).show();
            }
        });



        if (Build.VERSION.SDK_INT >= 23) //Android MarshMellow Version or above
             {
            requestPermission();

        }





        cardLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(HomeActivity.this);
                dialogBuilder
                        .withTitle("ອອກຈາກລະບົບ")
                        .withMessage("ທ່ານຕ້ອງການອອກຈາກລະບົບບໍ?")
                        .withEffect(Slidetop)
                        .withDialogColor("#2979ff") //use color code for dialog
                        .withButton1Text("ຕົກລົງ")
                        .withButton2Text("ຍົກເລີກ")
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                editor.putString(Constant.SP_PASSWORD, "");
                                editor.apply();

                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                                dialogBuilder.dismiss();
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.language_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {


            case R.id.local_french:
                setNewLocale(this, LocaleManager.FRENCH);
                return true;


            case R.id.local_english:
                setNewLocale(this, LocaleManager.ENGLISH);
                return true;

            default:
                Log.d("Default", "default");

        }

        return super.onOptionsItemSelected(item);
    }


    private void setNewLocale(AppCompatActivity mContext, @LocaleManager.LocaleDef String language) {
        LocaleManager.setNewLocale(this, language);
        Intent intent = mContext.getIntent();
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    //double back press to exit
    @Override
    public void onBackPressed() {
        if (backPressed + TIME_DELAY > System.currentTimeMillis()) {

            finishAffinity();

        } else {
            Toasty.info(this, R.string.press_once_again_to_exit,
                    Toast.LENGTH_SHORT).show();
        }
        backPressed = System.currentTimeMillis();
    }


    private void requestPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {

                            //write your action if needed
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings

                        }
                    }


                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(error -> Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show())
                .onSameThread()
                .check();
    }
}
