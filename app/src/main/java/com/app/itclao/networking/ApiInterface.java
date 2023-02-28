package com.app.itclao.networking;
import com.app.itclao.Constant;
import com.app.itclao.model.Catgory;
import com.app.itclao.model.Customer;
import com.app.itclao.model.Invoince;
import com.app.itclao.model.Item;
import com.app.itclao.model.Login;
import com.app.itclao.model.OrderDetails;
import com.app.itclao.model.Order_list;
import com.app.itclao.model.Product;
import com.app.itclao.model.Product2;
import com.app.itclao.model.Sale;
import com.app.itclao.model.Staff;
import com.app.itclao.model.Status;
import com.app.itclao.model.Table;
import com.app.itclao.model.Visit;
import com.app.itclao.model.custroute;
import com.app.itclao.model.iteminvoice;
import com.app.itclao.model.office;
import com.app.itclao.model.payment;
import com.app.itclao.model.paymentorder;
import com.app.itclao.model.productqty;
import com.app.itclao.model.quotation_transfer;
import com.app.itclao.model.retail;
import com.app.itclao.model.showimage;
import com.app.itclao.model.size;
import com.app.itclao.model.tbroute;
import com.app.itclao.model.totaill;
import com.app.itclao.model.user;
import com.app.itclao.model.viewpaymoney;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    public static final String SEARCH_TEXT = "search_text";
    //for login
    @FormUrlEncoded
    @POST("login.php")
    Call<Login> login(
            @Field(Constant.USERID) String User_ID,
            @Field(Constant.USERPASS) String Password

    );
    //get table data

    @GET("get_item.php")
    Call<List<Item>> get_table();


    @POST("orders_submitv2.php")
    Call<String> submitOrders(
            @Body RequestBody ordersData
    );


    @POST("orders_submit2.php")
    Call<String> submitOrders2(
            @Body RequestBody ordersData
    );

    @POST("orders_submits.php")
    Call<String> submitOrderss(
            @Body RequestBody ordersData
    );





    @GET("get_table2.php")
    Call<List<Table>> get_table2(
            @Query(Constant.SEARCH_TEXT) String searchText
    );


    @GET("get_payment.php")
    Call<List<payment>> get_payment(
     @Query(Constant.SEARCH_TEXT) String searchText,
     @Query(Constant.USERID) String User_ID
    );


    @GET("get_tb_route.php")
    Call<List<tbroute>> get_tbroute(
            @Query(Constant.SEARCH_TEXT) String searchText,
            @Query(Constant.STOCK_ID) String stock_id
    );

    @GET("get_customerrms.php")
    Call<List<custroute>> get_custroute(
            @Query(Constant.SEARCH_TEXT) String searchText,
            @Query(Constant.STOCK_ID) String stock_id,
            @Query(Constant.ROUTE_ID) String route_id
    );

    @GET("getordersale.php")
    Call<List<Order_list>> get_orderlist(
            // @Query(Constant.BILL_NO) String bill_no,
            @Query(Constant.BILL_NO) String bill_no
    );


    //get order data
    @GET("order_details_by_invoice.php")
    Call<List<OrderDetails>> OrderDetailsByInvoice(
     @Query(Constant.BILL_NO) String bill_no
    );


    //get order data
    @GET("get_invoicepay.php")
    Call<List<viewpaymoney>> get_invoicepay(
            @Query(Constant.SP_EMAIL) String user_id

    );



    @GET("get_totaill.php")
    Call<List<totaill>> get_totails(
            @Query(Constant.SP_EMAIL) String user_id

    );


    @GET("get_user.php")
    Call<List<user>> get_useername(
            @Query(Constant.SP_EMAIL) String User_ID
    );


    @GET("get_productsqty.php")
    Call<List<productqty>> get_productqty(
            @Query(Constant.SEARCH_TEXT) String searchText,
            @Query(Constant.STOCK_ID) String stock_id

    );


    @GET("get_paymentorder.php")
    Call<List<paymentorder>> get_paymentorder(
            @Query(Constant.SEARCH_TEXT) String searchText,
            @Query(Constant.STOCK_ID) String stock_id

    );


    @GET("get_dataorderpay.php")
    Call<List<paymentorder>> get_dataorderpay(
            @Query(Constant.STOCK_ID) String stock_id

    );


    @GET("get_dataorderpay2.php")
    Call<List<quotation_transfer>> get_datproduct(
            @Query(Constant.BILL_NO) String bill_no


    );


    @GET("get_dataorderpay_totaill.php")
    Call<List<paymentorder>> get_dataorderpay_totaill(
            @Query(Constant.STOCK_ID) String stock_id

    );


    @GET("get_invoice.php")
    Call<List<Invoince>> get_invoice(
            @Query(Constant.SEARCH_TEXT) String searchText,
            @Query(Constant.STOCK_ID) String stock_id

    );

    @GET("get_iteminvoice.php")
    Call<List<iteminvoice>> get_teiminvoice(
            @Query(Constant.SEARCH_TEXT) String searchText,
            @Query(Constant.SALE_ID) String sale_id

    );



    //get order data
    @GET("get_visit.php")
    Call<List<Visit>> get_suet(
            @Query(Constant.DATE) String date,
            @Query(Constant.CUSTOMER_ID) String customer_id
    );


    @GET("get_vcustomer.php")
    Call<List<Customer>> get_customer(
            @Query(Constant.CUSTOMER_ID) String customer_id
    );


    //get ORDER
    @POST("get_order.php")
    Call<List<Sale>> get_order(
            @Query(Constant.SALE_TABLE) String searchText
    );

    //get catgory
    @GET("get_catgory.php")
    Call<List<Catgory>> get_catgory();


    //get catgory
    @POST("get_office.php")
    Call<List<office>> get_office();


    //get product
    @GET("get_products.php")
    Call<List<Product>> get_products(
            @Query(Constant.SEARCH_TEXT) String search_text,
            @Query(Constant.STOCK_ID) String stock_id

    );


    @GET("get_products2.php")
    Call<List<Product2>> get_products2(
            @Query(Constant.SEARCH_TEXT) String search_text,
            @Query(Constant.STOCK_ID) String stock_id

    );


    //get product data
    @GET("get_showimage.php")
    Call<List<showimage>> getProductById(
            @Query(Constant.PRODUCT_ID) String product_id,
            @Query(Constant.STOCK_ID) String stock_id
    );

    @GET("get_showimage2.php")
    Call<List<showimage>> getProductById2(
            @Query(Constant.PRODUCT_ID) String product_id,
            @Query(Constant.STOCK_ID) String stock_id
    );

    @GET("get_item.php")
    Call<List<Item>> getItem();


    @GET("get_retail.php")
    Call<List<retail>> getRetail();



    @GET("get_size.php")
    Call<List<size>> getSize();



    @GET("get_status.php")
    Call<List<Status>> getStatus();

    @FormUrlEncoded
    @POST("update_table1.php")
    Call<Table> update_table1(
            @Field(Constant.TBNAME) String tbname
    );

    //for product data
    @GET("get_catgoryId.php")
    Call<List<Product>> get_catgoryId(
            @Query(Constant.CATEGORY_ID) String Group_ID,
             @Query(Constant.STOCK_ID) String stock_id

    );



    //for product data
    @GET("get_catgoryId2.php")
    Call<List<Product2>> get_catgoryId2(
            @Query(Constant.CATEGORY_ID) String Group_ID,
            @Query(Constant.STOCK_ID) String stock_id

    );



    //update table  status =2
    @FormUrlEncoded
    @POST("update_table2.php")
    Call<Table> update_table2(
            @Field(Constant.TBNAME) String tbname
    );

    //get User
    @GET("get_user.php")
    Call<List<user>> get_User(
            @Query(Constant.USERID) String user_id
    );



    //add sale data to server
    @FormUrlEncoded
    @POST("add_sale.php")
    Call<Sale> addsale(
            @Field(Constant.SALE_BILL) String sale_bill,
            @Field(Constant.SALE_DTE) String sale_date,
            @Field(Constant.SALE_TABLE) String sale_table,
            @Field(Constant.SALE_PROID) String sale_proid,
            @Field(Constant.SALE_NAME) String sale_name,
            @Field(Constant.SALE_PRICE) String sale_price,
            @Field(Constant.SALE_QTY) String sale_qty,
            @Field(Constant.SALE_STATUS) String sale_status,
            @Field(Constant.EDIT_SALE) String edit_sale,
//            @Field(Constant.SENDID) String sendid,
//            @Field(Constant.SENDTIME) String sendtime,
//            @Field(Constant.PRINT) String print,
            @Field(Constant.USERNAME) String username
//            @Field(Constant.STATUSORDER) String statusorder,
//            @Field(Constant.REMARK) String remark
            );


    //add visit
    @FormUrlEncoded
    @POST("add_visitapi.php")
    Call<Visit> addVisit(
            @Field(Constant.FNAME) String fname,
            @Field(Constant.LEADER) String leader,
            @Field(Constant.DATE) String date,
            @Field(Constant.CUSTOMER_ID) String customer_id,
            @Field(Constant.CUSTOMER_NAME) String customer_name,
            @Field(Constant.ONC) String onc,
            @Field(Constant.NOTE) String note,
            @Field(Constant.USERID) String User_ID,
            @Field(Constant.TIME) String time,
            @Field(Constant.RED_CALABASH) String red_calabash,
            @Field(Constant.RED_CALABASHQTY_CHECK) String red_calabashqty_check,
            @Field(Constant.RED_CALABASH_IN) String red_calabash_in,
            @Field(Constant.SAME_AS_BEFORE) String same_as_before,
            @Field(Constant.SAME_AS_BEFORE_CHECK) String same_as_before_check,
            @Field(Constant.SAME_AS_BEFORE_IN) String same_as_before_in,
            @Field(Constant.COFFEESTRONG) String coffeestrong,
            @Field(Constant.COFFEESTRONG_CHECK) String coffeestrong_check,
            @Field(Constant.COFFEESTRONG_IN) String coffeestrong_in,
            @Field(Constant.YOUNGCOFFEE) String youngcoffee,
            @Field(Constant.YOUNGCOFFEE_CHECK) String youngcoffee_check,
            @Field(Constant.YOUNGCOFFEE_IN) String youngcoffee_in,
            @Field(Constant.BILL_NOORDER) String bill_noorder
    );




    @FormUrlEncoded
    @POST("delete_customerss.php")
    Call<Customer> delete_customer(
            @Field(Constant.CUSTOMER_ID) String customer_id
    );



    //add visit
    @FormUrlEncoded
    @POST("get_updsatevist.php")
    Call<Visit> updatevsit(
            @Field(Constant.ID) String Id,

            @Field(Constant.ONC) String onc,

            @Field(Constant.RED_CALABASHQTY_CHECK) String red_calabashqty_check,

            @Field(Constant.SAME_AS_BEFORE_CHECK) String same_as_before_check,

            @Field(Constant.COFFEESTRONG_CHECK) String coffeestrong_check,

            @Field(Constant.YOUNGCOFFEE_CHECK) String youngcoffee_check,
            @Field(Constant.NOTE) String note,
            @Field(Constant.CUSTOMER_ID) String customer_id

    );



    //add visit

    @FormUrlEncoded
    @POST("add_customerapi.php")
    Call<Customer> addCustomer(
            @Field(Constant.CUSTOMER_ID) String customer_id,
            @Field(Constant.CUSTOMER_TYPE) String customer_type,
            @Field(Constant.OUTLETYPE) String OutletType,
            @Field(Constant.OUTLETSIZE) String OutletSize,
            @Field(Constant.CUSTOMER_NAME) String customer_name,
            @Field(Constant.VILLAGE) String village,
            @Field(Constant.DISTRICT) String district,
            @Field(Constant.PROVICE) String province,
            @Field(Constant.PHONE) String phone,
            @Field(Constant.FAX) String fax,
            @Field(Constant.EMAIL) String email,
            @Field(Constant.REMARK) String remark,
            @Field(Constant.ROUTE_ID) String route_id
    );




    @FormUrlEncoded
    @POST("updateapi_customeri.php")
    Call<Customer> upadate_customer(
            @Field(Constant.CUSTOMER_ID) String customer_id,
            @Field(Constant.CUSTOMER_TYPE) String customer_type,
            @Field(Constant.OUTLETYPE) String OutletType,
            @Field(Constant.OUTLETSIZE) String OutletSize,
            @Field(Constant.CUSTOMER_NAME) String customer_name,
            @Field(Constant.VILLAGE) String village,
            @Field(Constant.DISTRICT) String district,
            @Field(Constant.PROVICE) String province,
            @Field(Constant.PHONE) String phone,
            @Field(Constant.FAX) String fax,
            @Field(Constant.EMAIL) String email,
            @Field(Constant.REMARK) String remark,
            @Field(Constant.ROUTE_ID) String route_id
    );


    //update cut_qty
    @FormUrlEncoded
    @POST("sale_cut_qty.php")
    Call<Product> update_cut_qty(
            @Field(Constant.PRODUCT_ID) String product_id,
            @Field(Constant.QTY) String qty

    );


    //for get count id order
    @GET("get_countorder.php")
    Call<List<Sale>> get_countorder(
            @Query(Constant.TBNAME) String tbname
    );

    //get customers data
    @GET("get_customer.php")
    Call<List<Customer>> getCustomers(
            @Query(Constant.STOCK_ID) String stock_id
    );


    //get customers data
    @GET("get_customer_copy.php")
    Call<List<Staff>> getStaff(
            @Query(Constant.STOCK_ID) String stock_id
    );

}