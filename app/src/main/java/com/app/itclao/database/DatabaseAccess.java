package com.app.itclao.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.itclao.Constant;

import java.util.ArrayList;
import java.util.HashMap;


public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;


    /**
     * Private constructor to avoid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {


        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }




    //get order type data
    public ArrayList<HashMap<String, String>> getPaymentMethod() {
        ArrayList<HashMap<String, String>> paymentMethod = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM payment_method ORDER BY payment_method_id ASC", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("payment_method_id", cursor.getString(0));
                map.put("payment_method_name", cursor.getString(1));
                paymentMethod.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return paymentMethod;
    }





    public ArrayList<HashMap<String, String>> getOrderType() {
        ArrayList<HashMap<String, String>> orderType = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM order_type ORDER BY order_type_id DESC", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();


                map.put("order_type_id", cursor.getString(0));
                map.put("order_type_name", cursor.getString(1));


                orderType.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return orderType;
    }





    public ArrayList<HashMap<String, String>> getOject() {
        ArrayList<HashMap<String, String>> orderType = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM statusrms ORDER BY p_id DESC", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("p_id", cursor.getString(0));
                map.put("pay", cursor.getString(1));
                orderType.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return orderType;
    }









    //get cart item count
    public int getCartItemCount() {
        Cursor cursor = database.rawQuery("SELECT * FROM item_product", null);
        int itemCount = cursor.getCount();
        cursor.close();
        database.close();
        return itemCount;
    }


    public int getitem_productorder() {
        Cursor cursor = database.rawQuery("SELECT * FROM item_productorder", null);
        int itemCounts = cursor.getCount();
        cursor.close();
        database.close();
        return itemCounts;
    }


    //delete product from cart
    public void updateProductQty(String id, String qty) {
        ContentValues values = new ContentValues();
        values.put("qty", qty);
        database.update("item_product", values, "Id=?", new String[]{id});

    }

    public boolean updatePro(String id,String qty) {
        ContentValues values = new ContentValues();
        values.put("sprice", 0);
        values.put("amount", 0);
        values.put("discount", qty);
        database.update("item_product", values, "Id=?", new String[]{id});

            return true;

    }


//    public void updateitem(String discount) {
//        ContentValues values = new ContentValues();
//        values.put("discount", discount);
//        database.update("item_product",null,null);
//    }




    //get product name
    public String getCurrency() {

        String currency = "n/a";
        Cursor cursor = database.rawQuery("SELECT * FROM shop", null);


        if (cursor.moveToFirst()) {
            do {


                currency = cursor.getString(5);


            } while (cursor.moveToNext());
        }


        cursor.close();
        database.close();
        return currency;
    }


    //calculate total price of product
    public double getTotalPrice() {
        double totalPrice = 0;
        Cursor cursor = database.rawQuery("SELECT * FROM item_product", null);
        if (cursor.moveToFirst()) {
            do {

                double price = Double.parseDouble(cursor.getString(cursor.getColumnIndex("sprice")));
                double qty = Double.parseDouble(cursor.getString(cursor.getColumnIndex("qty")));
              //  int qty = Integer.parseInt(cursor.getString(cursor.getColumnIndex("qty")));

                double subTotal = price * qty;
                totalPrice = totalPrice + subTotal;


            } while (cursor.moveToNext());
        } else {
            totalPrice = 0;
        }
        cursor.close();
        database.close();
        return totalPrice;
    }






    //calculate total price of product
    public double getTotalitem_productorder() {
        double totalPrice = 0;
        Cursor cursor = database.rawQuery("SELECT * FROM item_productorder", null);
        if (cursor.moveToFirst()) {
            do {

                double price = Double.parseDouble(cursor.getString(cursor.getColumnIndex("sprice")));
                double qty = Double.parseDouble(cursor.getString(cursor.getColumnIndex("qty")));
             //   int qty = Integer.parseInt(cursor.getString(cursor.getColumnIndex("qty")));
                double subTotal = price * qty;

                    totalPrice = totalPrice + subTotal;




            } while (cursor.moveToNext());
        } else {
            totalPrice = 0;
        }
        cursor.close();
        database.close();
        return totalPrice;
    }












    public String getTable(){
        String table = "n/a";
        Cursor cursor = database.rawQuery("SELECT * FROM tbsale_save_data", null);
        if (cursor.moveToFirst()) {
            do {
                table = String.valueOf(cursor.getString(cursor.getColumnIndex("tbname")));
            } while (cursor.moveToNext());
        } else {

        }
        cursor.close();
        database.close();
        return table;
    }



    public String getsum(){
        String aount = "n/a";
        Cursor cursor = database.rawQuery("SELECT sum(amount)as aount FROM item_product", null);
        if (cursor.moveToFirst()) {
            do {
                aount = String.valueOf(cursor.getString(cursor.getColumnIndex("aount")));
            } while (cursor.moveToNext());
        } else {
        }
        cursor.close();
        database.close();
        return aount;
    }



    //get shop information
    public ArrayList<HashMap<String, String>> getShopInformation() {
        ArrayList<HashMap<String, String>> shopInfo = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM shop", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("shop_name", cursor.getString(1));
                map.put("shop_contact", cursor.getString(2));
                map.put("shop_email", cursor.getString(3));
                map.put("shop_address", cursor.getString(4));
                map.put("shop_currency", cursor.getString(5));
                map.put("tax", cursor.getString(6));
                shopInfo.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return shopInfo;
    }




    //Add product into cart
    public int addToCart(String stock_id,String product_id,String pricesale,String qty,Double amount, String pro_name,String unit, String img) {
        Cursor result = database.rawQuery("SELECT * FROM item_product WHERE product_id='" + product_id + "'", null);
        if (result.getCount() >= 2) {
            return 2;
        } else {
            ContentValues values = new ContentValues();
            values.put(Constant.STOCK_ID,stock_id);
            values.put(Constant.PRODUCT_ID,product_id);
            values.put(Constant.SPRICE,pricesale);
            values.put(Constant.QTYS,qty);
            values.put(Constant.AMOUNTS,amount);
            values.put(Constant.PRO_NAME,pro_name);
            values.put(Constant.UNITS,unit);
            values.put(Constant.IMGS,img);
            long check = database.insert(Constant.item_product, null, values);
            result.close();
            database.close();
            //if data insert success, its return 1, if failed return -1
            if (check == -1) {
                return -1;
            } else {
                return 1;
            }
        }

    }




    //Add product into cart
    public int addToCart2(String stock_id,String product_id,String pricesale,String qty,Double amount,String pro_name,String unit,String img) {
        Cursor result = database.rawQuery("SELECT * FROM item_product WHERE product_id='" + product_id + "'", null);
        if (result.getCount() >= 2) {
            return 2;
        } else {
            ContentValues values = new ContentValues();
            values.put(Constant.STOCK_ID,stock_id);
            values.put(Constant.PRODUCT_ID,product_id);
            values.put(Constant.SPRICE,pricesale);
            values.put(Constant.QTYS,qty);
            values.put(Constant.AMOUNTS,amount);

            values.put(Constant.PRO_NAME,pro_name);
            values.put(Constant.UNITS,unit);
            values.put(Constant.IMGS,img);


          /*  values.put(Constant.QTY,qty);
              values.put(Constant.PRODUCT_NAME,Product_Name);
              values.put(Constant.UNIT,Unit);*/
            long check = database.insert(Constant.item_product, null, values);
            result.close();
            database.close();
            //if data insert success, its return 1, if failed return -1
            if (check == -1) {
                return -1;
            } else {
                return 1;
            }
        }

    }



    //Add item_productorder into cart
    public int additem_productorder(String stock_id,String product_id,String pricesale,String qty,Double amount, String pro_name,String unit, String img) {
        Cursor result = database.rawQuery("SELECT * FROM item_productorder WHERE product_id='" + product_id + "'", null);
        if (result.getCount() >= 2) {
            return 2;
        } else {
            ContentValues values = new ContentValues();
            values.put(Constant.STOCK_ID,stock_id);
            values.put(Constant.PRODUCT_ID,product_id);
            values.put(Constant.SPRICE,pricesale);
            values.put(Constant.QTYS,qty);
            values.put(Constant.AMOUNTS,amount);
            values.put(Constant.PRO_NAME,pro_name);
            values.put(Constant.UNITS,unit);
            values.put(Constant.IMGS,img);
            long check = database.insert(Constant.item_productorder, null, values);
            result.close();
            database.close();
            //if data insert success, its return 1, if failed return -1
            if (check == -1) {
                return -1;
            } else {
                return 1;
            }
        }

    }




    public int additem_productorder22(String stock_id,String product_id) {
        Cursor result = database.rawQuery("SELECT * FROM item_productorder WHERE product_id='" + product_id + "'", null);
        if (result.getCount() >= 1) {
            return 2;
        } else {
            ContentValues values = new ContentValues();
            values.put(Constant.STOCK_ID,stock_id);
            values.put(Constant.PRODUCT_ID,product_id);
//            values.put(Constant.SPRICE,pricesale);
//            values.put(Constant.QTYS,qty);
//            values.put(Constant.AMOUNTS,amount);
//            values.put(Constant.PRO_NAME,pro_name);
//            values.put(Constant.UNITS,unit);
//            values.put(Constant.IMGS,img);
            long check = database.insert(Constant.item_productorder, null, values);
            result.close();
            database.close();
            //if data insert success, its return 1, if failed return -1
            if (check == -1) {
                return -1;
            } else {
                return 1;
            }
        }

    }



    public ArrayList<HashMap<String, String>> getCartProduct() {
        ArrayList<HashMap<String, String>> product = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM item_product", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put(Constant.STOCK_ID, cursor.getString(cursor.getColumnIndex("stock_id")));
                map.put(Constant.PRODUCT_ID, cursor.getString(cursor.getColumnIndex("product_id")));
                map.put(Constant.SPRICE, cursor.getString(cursor.getColumnIndex("sprice")));
                map.put(Constant.QTYS, cursor.getString(cursor.getColumnIndex("qty")));
                map.put(Constant.PRO_NAME, cursor.getString(cursor.getColumnIndex("pro_name")));
                map.put(Constant.UNITS, cursor.getString(cursor.getColumnIndex("unit")));
                map.put(Constant.IMGS, cursor.getString(cursor.getColumnIndex("img")));
                map.put(Constant.EXTRA_QTY, cursor.getString(cursor.getColumnIndex("discount")));
                product.add(map);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return product;
    }




    public ArrayList<HashMap<String, String>> getCartProductordeer() {
        ArrayList<HashMap<String, String>> product = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM item_productorder", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put(Constant.STOCK_ID, cursor.getString(cursor.getColumnIndex("stock_id")));
                map.put(Constant.PRODUCT_ID, cursor.getString(cursor.getColumnIndex("product_id")));
                map.put(Constant.SPRICE, cursor.getString(cursor.getColumnIndex("sprice")));
                map.put(Constant.QTYS, cursor.getString(cursor.getColumnIndex("qty")));
                map.put(Constant.PRO_NAME, cursor.getString(cursor.getColumnIndex("pro_name")));
                map.put(Constant.UNITS, cursor.getString(cursor.getColumnIndex("unit")));
                map.put(Constant.IMGS, cursor.getString(cursor.getColumnIndex("img")));
                product.add(map);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return product;
    }





    public String getSumqty(){
        String sumqty = "n/a";
        Cursor cursor = database.rawQuery("SELECT sum(qty)as sumqty FROM item_productorder", null);
        if (cursor.moveToFirst()) {
            do {
                sumqty = String.valueOf(cursor.getString(cursor.getColumnIndex("sumqty")));
            } while (cursor.moveToNext());
        } else {

        }
        cursor.close();
        database.close();
        return sumqty;
    }



    //empty cart
    public void emptyCart() {
        database.delete(Constant.item_product, null, null);
        database.close();
    }

    public void emptyCarts() {
        database.delete(Constant.item_productorder, null, null);
        database.close();
    }



    public ArrayList<HashMap<String, String>> getCustomers2() {
        ArrayList<HashMap<String, String>> customer = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM item_product ORDER BY id DESC", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", cursor.getString(0));
                map.put("stock_id", cursor.getString(1));
                map.put("product_id", cursor.getString(2));
                map.put("sprice", cursor.getString(3));
                map.put("qty", cursor.getString(4));
                map.put("amount", cursor.getString(5));
                map.put("pro_name", cursor.getString(6));
                map.put("unit", cursor.getString(7));
                customer.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return customer;
    }






    public boolean deleteProductFromCart(String id) {
        long check = database.delete(Constant.item_product, "Id=?", new String[]{id});
        database.close();
        if (check == 1) {
            return true;
        } else {
            return false;
        }

    }





    public boolean deleteProductFromCartorder(String id) {
        long check = database.delete(Constant.item_productorder, "Id=?", new String[]{id});
        database.close();
        if (check == 1) {
            return true;
        } else {
            return false;
        }

    }





    //get product category data
    public ArrayList<HashMap<String, String>> getProductCategory() {
        ArrayList<HashMap<String, String>> productCategory = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM item_product", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("Id", cursor.getString(0));
                map.put("stock_id", cursor.getString(1));
                map.put("product_id", cursor.getString(2));
                map.put("sprice", cursor.getString(3));
                map.put("qty", cursor.getString(4));
                map.put("amount", cursor.getString(5));
                map.put("pro_name", cursor.getString(6));
                map.put("unit", cursor.getString(7));
                map.put("img", cursor.getString(8));

                productCategory.add(map);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        return productCategory;
    }





    //get product category data
    public ArrayList<HashMap<String, String>> getitem_productordersss() {
        ArrayList<HashMap<String, String>> productCategory = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM item_productorder", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("Id", cursor.getString(0));
                map.put("stock_id", cursor.getString(1));
                map.put("product_id", cursor.getString(2));
                map.put("sprice", cursor.getString(3));
                map.put("qty", cursor.getString(4));
                map.put("amount", cursor.getString(5));
                map.put("pro_name", cursor.getString(6));
                map.put("unit", cursor.getString(7));
                map.put("img", cursor.getString(8));

                productCategory.add(map);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        return productCategory;
    }




    //get product payment method
    public ArrayList<HashMap<String, String>> searchPaymentMethod(String s) {
        ArrayList<HashMap<String, String>> paymentMethod = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM payment_method WHERE payment_method_name LIKE '%" + s + "%' ORDER BY payment_method_id DESC ", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();


             //   map.put(Constant.PAYMENT_METHOD_ID, cursor.getString(0));
           //     map.put(Constant.PAYMENT_METHOD_NAME, cursor.getString(1));

                paymentMethod.add(map);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        return paymentMethod;
    }


    //get product supplier data
    public ArrayList<HashMap<String, String>> getProductSupplier() {
        ArrayList<HashMap<String, String>> productSuppliers = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM catgory", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();

                map.put("cat_id", cursor.getString(0));
                map.put("cat_name", cursor.getString(1));
                productSuppliers.add(map);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        return productSuppliers;
    }



    //get product supplier data
    public ArrayList<HashMap<String, String>> getPay() {
        ArrayList<HashMap<String, String>> productSuppliers = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM appay", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();

                map.put("p_id", cursor.getString(0));
                map.put("pay", cursor.getString(1));
                productSuppliers.add(map);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        return productSuppliers;
    }





    //get product supplier data
    public ArrayList<HashMap<String, String>> getWeightUnit() {
        ArrayList<HashMap<String, String>> productWeightUnit = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM product_weight", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();


                map.put("weight_id", cursor.getString(0));
                map.put("weight_unit", cursor.getString(1));

                productWeightUnit.add(map);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        return productWeightUnit;
    }

    //get product data
    public ArrayList<HashMap<String, String>> searchExpense(String s) {
        ArrayList<HashMap<String, String>> product = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM expense WHERE expense_name LIKE '%" + s + "%' ORDER BY expense_id DESC", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();

                map.put("expense_id", cursor.getString(cursor.getColumnIndex("expense_id")));
                map.put("expense_name", cursor.getString(cursor.getColumnIndex("expense_name")));
                map.put("expense_note", cursor.getString(cursor.getColumnIndex("expense_note")));
                map.put("expense_amount", cursor.getString(cursor.getColumnIndex("expense_amount")));
                map.put("expense_date", cursor.getString(cursor.getColumnIndex("expense_date")));
                map.put("expense_time", cursor.getString(cursor.getColumnIndex("expense_time")));


                product.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return product;
    }


    //get product data
    public ArrayList<HashMap<String, String>> getSearchProducts(String s) {
        ArrayList<HashMap<String, String>> product = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM products WHERE product_name LIKE '%" + s + "%' OR product_code LIKE '%" + s + "%' ORDER BY product_id DESC", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();

                map.put("product_id", cursor.getString(0));
                map.put("product_name", cursor.getString(1));
                map.put("product_code", cursor.getString(2));
                map.put("product_category", cursor.getString(3));
                map.put("product_description", cursor.getString(4));

                map.put("product_sell_price", cursor.getString(5));
                map.put("product_supplier", cursor.getString(6));
                map.put("product_image", cursor.getString(7));

                map.put("product_weight_unit_id", cursor.getString(8));
                map.put("product_weight", cursor.getString(9));


                product.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return product;
    }


    //get suppliers data
    public ArrayList<HashMap<String, String>> getSuppliers() {
        ArrayList<HashMap<String, String>> supplier = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM suppliers ORDER BY suppliers_id DESC", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();




                supplier.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return supplier;
    }


    //delete customer
    public boolean deleteCustomer(String customerId) {


        long check = database.delete("customers", "customer_id=?", new String[]{customerId});

        database.close();

        if (check == 1) {
            return true;
        } else {
            return false;
        }

    }


    //delete category
    public boolean deleteUser(String id) {


        long check = database.delete("users", "id=?", new String[]{id});

        database.close();

        if (check == 1) {
            return true;
        } else {
            return false;
        }

    }


    //delete category
    public boolean deleteCategory(String categoryId) {


        long check = database.delete("product_category", "category_id=?", new String[]{categoryId});

        database.close();

        if (check == 1) {
            return true;
        } else {
            return false;
        }


    }



}