package com.trendinganalysis.conetrading;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ken on 2/11/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {


    // Database Name
    public static final String DATABASE_NAME = "c_one_trading_trend_analysis_db";
    // Contacts Table Columns names
    public static final String PRIMARY_KEY_ID = "id";
    public static final String C_ONE_PRIMARY_KEY_SERVER_ID = "server_id";
    public static final String C_ONE_PRODUCT_ID = "product_id";
    public static final String C_ONE_UNIQUE_KEY_PRODUCT_MAKE = "product_make";
    public static final String C_ONE_UNIQUE_KEY_PRODUCT_TYPE = "product_type";
    public static final String C_ONE_UNIQUE_KEY_PRODUCT_SERIES = "product_series";
    public static final String C_ONE_UNIQUE_KEY_PRODUCT_MODEL = "product_model";
    public static final String C_ONE_UNIQUE_KEY_PRODUCT_ENGINE = "product_engine";
    public static final String C_ONE_UNIQUE_KEY_ADDITIONAL_DESCRIPTION = "additional_description";
    public static final String C_ONE_UNIQUE_KEY_PRODUCT_WEIGHT = "product_weight";
    public static final String C_ONE_UNIQUE_KEY_PRODUCT_SOURCE = "product_source";
    public static final String C_ONE_UNIQUE_KEY_PRODUCT_PRICE_PHP = "product_price_php";
    public static final String C_ONE_UNIQUE_KEY_PRODUCT_PRICE_JPY = "product_price_jpy";
    public static final String C_ONE_UNIQUE_KEY_SOURCE_DATE = "source_date";
    public static final String C_ONE_DATE_MODIFIED = "date_modified";
    //USER table names
    public static final String USER_PRIMARY_KEY_ID = "id";
    public static final String USER_PRIMARY_KEY_SERVER_ID = "server_id";
    public static final String USER_USERNAME = "username";
    public static final String USER_PASSWORD = "password";
    public static final String USER_FIRSTNAME = "firstname";
    public static final String USER_LASTNAME = "lastname";
    public static final String USER_USER_TYPE = "user_type";
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 21;
    // Contacts table name
    private static final String TABLE_PRODUCTS = "c_one_products";
    private static final String TABLE_FIRST_LEVEL = "c_one_firstlevel";
    private static final String TABLE_REPORTS = "c_one_reports";
    private static final String TABLE_USERS = "c_one_users";
    //comma
    private static final String COMMA = ",";
    private static MessageDigest digester;

    static {
        try {
            digester = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    DataHandler dataHandler = MainActivity.dataHandler;


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    protected static String crypt(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("String to encript cannot be null or zero length");
        }

        digester.update(str.getBytes());
        byte[] hash = digester.digest();
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
            } else {
                hexString.append(Integer.toHexString(0xFF & hash[i]));
            }
        }
        return hexString.toString();
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "(\n" +
                "\t " + PRIMARY_KEY_ID + " INTEGER PRIMARY KEY " + COMMA + "\n" +
                "\t " + C_ONE_PRIMARY_KEY_SERVER_ID + " INTEGER " + COMMA + "\n" +
                "\t " + C_ONE_PRODUCT_ID + " INTEGER " + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_PRODUCT_MAKE + " TEXT " + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_PRODUCT_TYPE + " TEXT" + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_PRODUCT_SERIES + " TEXT " + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_PRODUCT_MODEL + " TEXT" + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_PRODUCT_ENGINE + " TEXT " + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_ADDITIONAL_DESCRIPTION + " TEXT" + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_PRODUCT_WEIGHT + " TEXT " + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_PRODUCT_SOURCE + " TEXT" + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_PRODUCT_PRICE_PHP + " FLOAT " + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_PRODUCT_PRICE_JPY + " FLOAT " + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_SOURCE_DATE + " DATE" + COMMA + "" +
                "\t " + C_ONE_DATE_MODIFIED + " DATE" + COMMA + "" +
                "UNIQUE(" + C_ONE_UNIQUE_KEY_PRODUCT_MAKE + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_PRODUCT_TYPE + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_PRODUCT_SERIES + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_PRODUCT_MODEL + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_PRODUCT_ENGINE + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_ADDITIONAL_DESCRIPTION + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_PRODUCT_WEIGHT + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_PRODUCT_SOURCE + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_PRODUCT_PRICE_PHP + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_PRODUCT_PRICE_JPY + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_SOURCE_DATE + " ) ON CONFLICT REPLACE )";

        String CREATE_FIRST_LEVEL_TABLE = "CREATE TABLE " + TABLE_FIRST_LEVEL + "(\n" +
                "\t " + PRIMARY_KEY_ID + " INTEGER PRIMARY KEY " + COMMA + "\n" +
                "\t " + C_ONE_PRIMARY_KEY_SERVER_ID + " INTEGER " + COMMA + "\n" +
                "\t " + C_ONE_PRODUCT_ID + " INTEGER " + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_PRODUCT_MAKE + " TEXT " + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_PRODUCT_TYPE + " TEXT" + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_PRODUCT_SERIES + " TEXT " + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_PRODUCT_MODEL + " TEXT" + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_PRODUCT_ENGINE + " TEXT " + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_ADDITIONAL_DESCRIPTION + " TEXT" + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_PRODUCT_WEIGHT + " TEXT " + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_PRODUCT_SOURCE + " TEXT" + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_PRODUCT_PRICE_PHP + " FLOAT " + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_PRODUCT_PRICE_JPY + " FLOAT " + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_SOURCE_DATE + " DATE" + COMMA + "" +
                "UNIQUE(" + C_ONE_UNIQUE_KEY_PRODUCT_MAKE + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_PRODUCT_TYPE + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_PRODUCT_SERIES + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_PRODUCT_MODEL + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_PRODUCT_ENGINE + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_ADDITIONAL_DESCRIPTION + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_PRODUCT_WEIGHT + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_PRODUCT_SOURCE + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_PRODUCT_PRICE_PHP + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_PRODUCT_PRICE_JPY + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_SOURCE_DATE + " ) ON CONFLICT REPLACE )";


        String CREATE_REPORTS_TABLE = "CREATE TABLE " + TABLE_REPORTS + "(\n" +
                "\t " + PRIMARY_KEY_ID + " INTEGER PRIMARY KEY " + COMMA + "\n" +
                "\t " + C_ONE_PRIMARY_KEY_SERVER_ID + " INTEGER " + COMMA + "\n" +
                "\t " + C_ONE_PRODUCT_ID + " INTEGER " + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_PRODUCT_MAKE + " TEXT " + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_PRODUCT_TYPE + " TEXT" + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_PRODUCT_SERIES + " TEXT " + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_PRODUCT_MODEL + " TEXT" + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_PRODUCT_ENGINE + " TEXT " + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_ADDITIONAL_DESCRIPTION + " TEXT" + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_PRODUCT_WEIGHT + " TEXT " + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_PRODUCT_SOURCE + " TEXT" + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_PRODUCT_PRICE_PHP + " FLOAT " + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_PRODUCT_PRICE_JPY + " FLOAT " + COMMA + "\n" +
                "\t " + C_ONE_UNIQUE_KEY_SOURCE_DATE + " DATE" + COMMA + "" +
                "UNIQUE(" + C_ONE_UNIQUE_KEY_PRODUCT_MAKE + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_PRODUCT_TYPE + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_PRODUCT_SERIES + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_PRODUCT_MODEL + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_PRODUCT_ENGINE + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_ADDITIONAL_DESCRIPTION + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_PRODUCT_WEIGHT + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_PRODUCT_SOURCE + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_PRODUCT_PRICE_PHP + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_PRODUCT_PRICE_JPY + "" + COMMA + "" +
                "       " + C_ONE_UNIQUE_KEY_SOURCE_DATE + " ) ON CONFLICT REPLACE )";

        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USERS + "(\n" +
                "\t " + USER_PRIMARY_KEY_ID + " INTEGER PRIMARY KEY " + COMMA + "\n" +
                "\t " + USER_PRIMARY_KEY_SERVER_ID + " INTEGER " + COMMA + "\n" +
                "\t " + USER_USERNAME + " TEXT " + COMMA + "\n" +
                "\t " + USER_PASSWORD + " TEXT " + COMMA + "\n" +
                "\t " + USER_FIRSTNAME + " TEXT" + COMMA + "\n" +
                "\t " + USER_LASTNAME + " TEXT " + COMMA + "\n" +
                "\t " + USER_USER_TYPE + " TEXT" + COMMA + "" +
                "UNIQUE(" + USER_PRIMARY_KEY_SERVER_ID + "" + COMMA + "" +
                "       " + USER_USERNAME + "" + COMMA + "" +
                "       " + USER_PASSWORD + "" + COMMA + "" +
                "       " + USER_FIRSTNAME + "" + COMMA + "" +
                "       " + USER_LASTNAME + "" + COMMA + "" +
                "       " + USER_USER_TYPE + "" + COMMA + "" +
                "       " + USER_USER_TYPE + "" + " ) ON CONFLICT REPLACE )";


        db.execSQL(CREATE_PRODUCTS_TABLE);
        db.execSQL(CREATE_FIRST_LEVEL_TABLE);
        db.execSQL(CREATE_REPORTS_TABLE);
        db.execSQL(CREATE_USER_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FIRST_LEVEL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPORTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);


        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    public void truncatedb() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_FIRST_LEVEL);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_REPORTS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Create tables again
        onCreate(database);

    }

    // Adding new product
    void addProduct(Products product) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(C_ONE_PRIMARY_KEY_SERVER_ID, product.getServerID());
        values.put(C_ONE_PRODUCT_ID, product.getProductID());
        values.put(C_ONE_UNIQUE_KEY_PRODUCT_MAKE, product.getMake());
        values.put(C_ONE_UNIQUE_KEY_PRODUCT_TYPE, product.getType());
        values.put(C_ONE_UNIQUE_KEY_PRODUCT_SERIES, product.getSeries());
        values.put(C_ONE_UNIQUE_KEY_PRODUCT_MODEL, product.getModel());
        values.put(C_ONE_UNIQUE_KEY_PRODUCT_ENGINE, product.getEngine());
        values.put(C_ONE_UNIQUE_KEY_ADDITIONAL_DESCRIPTION, product.getDesc());
        values.put(C_ONE_UNIQUE_KEY_PRODUCT_WEIGHT, product.getSize());
        values.put(C_ONE_UNIQUE_KEY_PRODUCT_SOURCE, product.getCategory());
        values.put(C_ONE_UNIQUE_KEY_PRODUCT_PRICE_PHP, product.getPrice_php());
        values.put(C_ONE_UNIQUE_KEY_PRODUCT_PRICE_JPY, product.getPrice_jpy());
        if (product.getDate() != null)
            values.put(C_ONE_UNIQUE_KEY_SOURCE_DATE, new SimpleDateFormat("yyyy-MM-dd").format(product.getDate()));
        if (product.getDate_modified() != null)
            values.put(C_ONE_DATE_MODIFIED, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(product.getDate_modified()));


        // Inserting Row
        database.insert(TABLE_PRODUCTS, null, values);
        database.close(); // Closing database connection
    }

    void addUser(UserModel user) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(USER_PRIMARY_KEY_SERVER_ID, user.getServer_id());
        values.put(USER_USERNAME, user.getUsername());
        values.put(USER_PASSWORD, user.getPassword());
        values.put(USER_FIRSTNAME, user.getFirstname());
        values.put(USER_LASTNAME, user.getLastname());
        values.put(USER_USER_TYPE, user.getUser_type());
        // Inserting Row
        database.insert(TABLE_USERS, null, values);
        database.close(); // Closing database connection
    }

    public String getAllUsers() {
        SQLiteDatabase database = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USERS;

        Log.i("checkCredentials", "query:  " + selectQuery);


        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                Log.i("syncUsers", "SERVER ID: " + cursor.getColumnIndex(USER_PRIMARY_KEY_SERVER_ID) +
                        "\nUSERNAME: " + cursor.getColumnIndex(USER_USERNAME) +
                        "\nPASSWORD: " + cursor.getColumnIndex(USER_PASSWORD) +
                        "\nFIRSTNAME: " + cursor.getColumnIndex(USER_FIRSTNAME) +
                        "\nLASTNAME: " + cursor.getColumnIndex(USER_LASTNAME) +
                        "\nUSER_TYPE: " + cursor.getColumnIndex(USER_USER_TYPE));


            } while (cursor.moveToNext());
        } else {
            Log.i("checkCredentials", "ddatabaseHandler: db_password is = " + " ?");
//            dataHandler.setCredentialsPass(false);
        }
        return "";
    }

    public boolean checkCredentials(String username, String password) {

        SQLiteDatabase database = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " + USER_USERNAME + " = " + "'" + username + "'";

        Log.i("checkCredentials", "username:  " + username);
        Log.i("checkCredentials", "query:  " + selectQuery);


        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                Log.i("checkCredentials", "SERVER ID: " + cursor.getInt(cursor.getColumnIndex(USER_PRIMARY_KEY_SERVER_ID)) +
                        "\nUSERNAME: " + cursor.getString(cursor.getColumnIndex(USER_USERNAME)) +
                        "\nPASSWORD: " + cursor.getString(cursor.getColumnIndex(USER_PASSWORD)) +
                        "\nFIRSTNAME: " + cursor.getString(cursor.getColumnIndex(USER_FIRSTNAME)) +
                        "\nLASTNAME: " + cursor.getString(cursor.getColumnIndex(USER_LASTNAME)) +
                        "\nUSER_TYPE: " + cursor.getString(cursor.getColumnIndex(USER_USER_TYPE)));

                Log.i("checkCredentials", "ddatabaseHandler: db_password is = " + crypt(password) + " ?");


                Log.i("checkCredentials", "is " + password + " = " + cursor.getString(cursor.getColumnIndex(USER_PASSWORD)) + "?" + password.equals(cursor.getString(cursor.getColumnIndex(USER_PASSWORD))));


                return crypt(password).equals(cursor.getString(cursor.getColumnIndex(USER_PASSWORD)));

            } while (cursor.moveToNext());
        } else {
            Log.i("checkCredentials", "ddatabaseHandler: db_password is = " + crypt(password) + " ?");
            return false;
//            dataHandler.setCredentialsPass(false);
        }
    }

    public List<Products> getreports(String source) {
        SQLiteDatabase database = this.getWritableDatabase();

        List<Products> reportList = new ArrayList<Products>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_REPORTS + " WHERE " + C_ONE_UNIQUE_KEY_PRODUCT_SOURCE + " LIKE '%" + source + "%'";

        Cursor cursor = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Products products = new Products();

                products.setDbID(cursor.getInt(cursor.getColumnIndex("id")));
                products.setServerID(cursor.getInt(cursor.getColumnIndex("server_id")));
                products.setProductID(cursor.getInt(cursor.getColumnIndex("product_id")));
                products.setMake(cursor.getString(cursor.getColumnIndex("product_make")));
                products.setType(cursor.getString(cursor.getColumnIndex("product_type")));
                products.setSeries(cursor.getString(cursor.getColumnIndex("product_series")));
                products.setModel(cursor.getString(cursor.getColumnIndex("product_model")));
                products.setEngine(cursor.getString(cursor.getColumnIndex("product_engine")));
                products.setDesc(cursor.getString(cursor.getColumnIndex("additional_description")));
                products.setSize(cursor.getString(cursor.getColumnIndex("product_weight")));
                products.setCategory(cursor.getString(cursor.getColumnIndex("product_source")));
                products.setPrice_php(Float.parseFloat(cursor.getString(cursor.getColumnIndex("product_price_php"))));
                products.setPrice_jpy(Float.parseFloat(cursor.getString(cursor.getColumnIndex("product_price_jpy"))));

                try {
                    String string = cursor.getString(cursor.getColumnIndex("source_date"));
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = format.parse(string);
                    products.setDate(date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Adding contact to list
                reportList.add(products);

            } while (cursor.moveToNext());
        }

        database.close();

        // return contact list
        return reportList;


    }

    // Getting All Contacts
    public List<Products> firstLevelSearch(String keyword) {
        SQLiteDatabase database = this.getWritableDatabase();
        List<Products> productsList = new ArrayList<Products>();
        // Select All Query


        dataHandler.setInitialSearchQuery("SELECT  * FROM " + TABLE_PRODUCTS +
                " WHERE product_engine LIKE '%" + keyword + "%'\n" +
                "OR additional_description LIKE '%" + keyword + "%'\n" +
                "OR product_type LIKE '%" + keyword + "%'\n" +
                "OR product_make LIKE '%" + keyword + "%'\n" +
                "OR product_model LIKE '%" + keyword + "%'\n" +
                "OR product_source LIKE '%" + keyword + "%'\n" +
                "OR product_weight LIKE '%" + keyword + "%'\n" +
                "OR product_series LIKE '%" + keyword + "%'\n" +
                "ORDER BY source_date");

        String insertIntoReport = "INSERT INTO " + TABLE_REPORTS + "" +
                "(" + C_ONE_PRIMARY_KEY_SERVER_ID + COMMA +
                "" + C_ONE_PRODUCT_ID + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_MAKE + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_TYPE + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_SERIES + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_MODEL + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_ENGINE + COMMA +
                "" + C_ONE_UNIQUE_KEY_ADDITIONAL_DESCRIPTION + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_WEIGHT + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_SOURCE + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_PRICE_PHP + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_PRICE_JPY + COMMA +
                "" + C_ONE_UNIQUE_KEY_SOURCE_DATE + ")\n" +
                " SELECT "
                + C_ONE_PRIMARY_KEY_SERVER_ID + COMMA +
                "" + C_ONE_PRODUCT_ID + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_MAKE + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_TYPE + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_SERIES + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_MODEL + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_ENGINE + COMMA +
                "" + C_ONE_UNIQUE_KEY_ADDITIONAL_DESCRIPTION + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_WEIGHT + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_SOURCE + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_PRICE_PHP + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_PRICE_JPY + COMMA +
                "" + C_ONE_UNIQUE_KEY_SOURCE_DATE +
                " FROM " + TABLE_PRODUCTS + "\n" +
                " WHERE " +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_MAKE + " LIKE '%" + keyword + "%'\n" +
                "or " + C_ONE_UNIQUE_KEY_PRODUCT_TYPE + " LIKE '%" + keyword + "%'\n" +
                "or " + C_ONE_UNIQUE_KEY_PRODUCT_SERIES + " LIKE '%" + keyword + "%'\n" +
                "or " + C_ONE_UNIQUE_KEY_PRODUCT_MODEL + " LIKE '%" + keyword + "%'\n" +
                "or " + C_ONE_UNIQUE_KEY_PRODUCT_ENGINE + " LIKE '%" + keyword + "%'\n" +
                "or " + C_ONE_UNIQUE_KEY_ADDITIONAL_DESCRIPTION + " LIKE '%" + keyword + "%'\n" +
                "or " + C_ONE_UNIQUE_KEY_PRODUCT_WEIGHT + " LIKE '%" + keyword + "%'\n" +
                "or " + C_ONE_UNIQUE_KEY_PRODUCT_SOURCE + " LIKE '%" + keyword + "%'\n" +
                "ORDER BY " + C_ONE_UNIQUE_KEY_SOURCE_DATE + " DESC";

        String insertIntoFirstLevel = "INSERT INTO " + TABLE_FIRST_LEVEL + "" +
                "(" + C_ONE_PRIMARY_KEY_SERVER_ID + COMMA +
                "" + C_ONE_PRODUCT_ID + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_MAKE + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_TYPE + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_SERIES + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_MODEL + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_ENGINE + COMMA +
                "" + C_ONE_UNIQUE_KEY_ADDITIONAL_DESCRIPTION + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_WEIGHT + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_SOURCE + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_PRICE_PHP + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_PRICE_JPY + COMMA +
                "" + C_ONE_UNIQUE_KEY_SOURCE_DATE + ")\n" +
                " SELECT "
                + C_ONE_PRIMARY_KEY_SERVER_ID + COMMA +
                "" + C_ONE_PRODUCT_ID + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_MAKE + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_TYPE + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_SERIES + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_MODEL + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_ENGINE + COMMA +
                "" + C_ONE_UNIQUE_KEY_ADDITIONAL_DESCRIPTION + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_WEIGHT + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_SOURCE + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_PRICE_PHP + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_PRICE_JPY + COMMA +
                "" + C_ONE_UNIQUE_KEY_SOURCE_DATE +
                " FROM " + TABLE_PRODUCTS + "\n" +
                " WHERE " +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_MAKE + " LIKE '%" + keyword + "%'\n" +
                "or " + C_ONE_UNIQUE_KEY_PRODUCT_TYPE + " LIKE '%" + keyword + "%'\n" +
                "or " + C_ONE_UNIQUE_KEY_PRODUCT_SERIES + " LIKE '%" + keyword + "%'\n" +
                "or " + C_ONE_UNIQUE_KEY_PRODUCT_MODEL + " LIKE '%" + keyword + "%'\n" +
                "or " + C_ONE_UNIQUE_KEY_PRODUCT_ENGINE + " LIKE '%" + keyword + "%'\n" +
                "or " + C_ONE_UNIQUE_KEY_ADDITIONAL_DESCRIPTION + " LIKE '%" + keyword + "%'\n" +
                "or " + C_ONE_UNIQUE_KEY_PRODUCT_WEIGHT + " LIKE '%" + keyword + "%'\n" +
                "or " + C_ONE_UNIQUE_KEY_PRODUCT_SOURCE + " LIKE '%" + keyword + "%'\n" +
                "ORDER BY " + C_ONE_UNIQUE_KEY_SOURCE_DATE + " DESC";


        Cursor cursor = database.rawQuery(dataHandler.getInitialSearchQuery(), null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Products products = new Products();

                products.setDbID(cursor.getInt(cursor.getColumnIndex("id")));
                products.setServerID(cursor.getInt(cursor.getColumnIndex("server_id")));
                products.setProductID(cursor.getInt(cursor.getColumnIndex("product_id")));
                products.setMake(cursor.getString(cursor.getColumnIndex("product_make")));
                products.setType(cursor.getString(cursor.getColumnIndex("product_type")));
                products.setSeries(cursor.getString(cursor.getColumnIndex("product_series")));
                products.setModel(cursor.getString(cursor.getColumnIndex("product_model")));
                products.setEngine(cursor.getString(cursor.getColumnIndex("product_engine")));
                products.setDesc(cursor.getString(cursor.getColumnIndex("additional_description")));
                products.setSize(cursor.getString(cursor.getColumnIndex("product_weight")));
                products.setCategory(cursor.getString(cursor.getColumnIndex("product_source")));
                products.setPrice_php(Float.parseFloat(cursor.getString(cursor.getColumnIndex("product_price_php"))));
                products.setPrice_jpy(Float.parseFloat(cursor.getString(cursor.getColumnIndex("product_price_jpy"))));

                try {
                    String string = cursor.getString(cursor.getColumnIndex("source_date"));
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = format.parse(string);
                    products.setDate(date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Adding contact to list
                productsList.add(products);

            } while (cursor.moveToNext());
        }

        database.delete(TABLE_REPORTS, null, null);
        database.execSQL(insertIntoReport);
        database.delete(TABLE_FIRST_LEVEL, null, null);
        database.execSQL(insertIntoFirstLevel);

        database.close();


        // return contact list
        return productsList;
    }

    public List<Products> secondLevelSearch(String make, String type, String model, String engine, String weight, String dateFrom, String dateTo, String series, String source) {
        SQLiteDatabase database = this.getWritableDatabase();

        List<Products> filteredList = new ArrayList<Products>();

        String insertIntoReport = "INSERT INTO " + TABLE_REPORTS + "" +
                "(" + C_ONE_PRIMARY_KEY_SERVER_ID + COMMA +
                "" + C_ONE_PRODUCT_ID + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_MAKE + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_TYPE + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_SERIES + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_MODEL + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_ENGINE + COMMA +
                "" + C_ONE_UNIQUE_KEY_ADDITIONAL_DESCRIPTION + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_WEIGHT + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_SOURCE + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_PRICE_PHP + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_PRICE_JPY + COMMA +
                "" + C_ONE_UNIQUE_KEY_SOURCE_DATE + ")\n" +
                " SELECT "
                + C_ONE_PRIMARY_KEY_SERVER_ID + COMMA +
                "" + C_ONE_PRODUCT_ID + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_MAKE + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_TYPE + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_SERIES + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_MODEL + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_ENGINE + COMMA +
                "" + C_ONE_UNIQUE_KEY_ADDITIONAL_DESCRIPTION + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_WEIGHT + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_SOURCE + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_PRICE_PHP + COMMA +
                "" + C_ONE_UNIQUE_KEY_PRODUCT_PRICE_JPY + COMMA +
                "" + C_ONE_UNIQUE_KEY_SOURCE_DATE +
                " FROM " + TABLE_FIRST_LEVEL + "\n" +
                " WHERE " + C_ONE_UNIQUE_KEY_PRODUCT_TYPE + " LIKE '%" + type + "%' " +
                "AND " + C_ONE_UNIQUE_KEY_PRODUCT_MODEL + " LIKE '%" + model + "%' " +
                "AND " + C_ONE_UNIQUE_KEY_PRODUCT_ENGINE + " LIKE '%" + engine + "%' " +
                "AND " + C_ONE_UNIQUE_KEY_PRODUCT_WEIGHT + " LIKE '%" + weight + "%' " +
                "AND " + C_ONE_UNIQUE_KEY_PRODUCT_MAKE + " LIKE '%" + make + "%' " +
                "AND " + C_ONE_UNIQUE_KEY_PRODUCT_SERIES + " LIKE '%" + series + "%' " +
                "AND " + C_ONE_UNIQUE_KEY_PRODUCT_SOURCE + " LIKE '%" + source + "%' " +
                "AND (" + C_ONE_UNIQUE_KEY_SOURCE_DATE + " BETWEEN '" + dateFrom + "' and '" + dateTo + "' )";


//         Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FIRST_LEVEL +
                " WHERE " + C_ONE_UNIQUE_KEY_PRODUCT_TYPE + " LIKE '%" + type + "%' " +
                "AND " + C_ONE_UNIQUE_KEY_PRODUCT_MODEL + " LIKE '%" + model + "%' " +
                "AND " + C_ONE_UNIQUE_KEY_PRODUCT_ENGINE + " LIKE '%" + engine + "%' " +
                "AND " + C_ONE_UNIQUE_KEY_PRODUCT_WEIGHT + " LIKE '%" + weight + "%' " +
                "AND " + C_ONE_UNIQUE_KEY_PRODUCT_MAKE + " LIKE '%" + make + "%' " +
                "AND " + C_ONE_UNIQUE_KEY_PRODUCT_SERIES + " LIKE '%" + series + "%' " +
                "AND " + C_ONE_UNIQUE_KEY_PRODUCT_SOURCE + " LIKE '%" + source + "%' " +
                "AND (" + C_ONE_UNIQUE_KEY_SOURCE_DATE + " BETWEEN '" + dateFrom + "' and '" + dateTo + "' ) ;";


        database.delete(TABLE_REPORTS, null, null);
        database.execSQL(insertIntoReport);

        Log.i("Second Query", selectQuery);

        Cursor cursor = database.rawQuery(selectQuery, null);


        int temp = 0;

        Log.e("pila?", cursor.toString());

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            do {
                Products products = new Products();


                products.setDbID(cursor.getInt(cursor.getColumnIndex("id")));
                products.setServerID(cursor.getInt(cursor.getColumnIndex("server_id")));
                products.setProductID(cursor.getInt(cursor.getColumnIndex("product_id")));
                products.setMake(cursor.getString(cursor.getColumnIndex("product_make")));
                products.setType(cursor.getString(cursor.getColumnIndex("product_type")));
                products.setSeries(cursor.getString(cursor.getColumnIndex("product_series")));
                products.setModel(cursor.getString(cursor.getColumnIndex("product_model")));
                products.setEngine(cursor.getString(cursor.getColumnIndex("product_engine")));
                products.setDesc(cursor.getString(cursor.getColumnIndex("additional_description")));
                products.setSize(cursor.getString(cursor.getColumnIndex("product_weight")));
                products.setCategory(cursor.getString(cursor.getColumnIndex("product_source")));
                products.setPrice_php(Float.parseFloat(cursor.getString(cursor.getColumnIndex("product_price_php"))));
                products.setPrice_jpy(Float.parseFloat(cursor.getString(cursor.getColumnIndex("product_price_jpy"))));

                try {
                    String string = cursor.getString(cursor.getColumnIndex("source_date"));
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = format.parse(string);
                    products.setDate(date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Adding contact to list
                filteredList.add(products);

                temp++;

            } while (cursor.moveToNext());
        }

        database.close();


        return filteredList;
    }

    public int getHighestId() {
        int max = 0;
        SQLiteDatabase database = this.getWritableDatabase();

        List<Products> filteredList = new ArrayList<Products>();

//        String query = "SELECT MAX(" + C_ONE_PRIMARY_KEY_SERVER_ID + ") AS id FROM " + TABLE_PRODUCTS + "";


//        Cursor cursor = database.rawQuery(query, null);
//        Cursor cursor = database.query(TABLE_PRODUCTS, new String[]{"MAX(" + C_ONE_PRIMARY_KEY_SERVER_ID + ")"}, null, null, null, null, null);
        Cursor cursor = database.query(TABLE_PRODUCTS, new String[]{"MAX(" + C_ONE_PRIMARY_KEY_SERVER_ID + ")"}, null, null, null, null, null);


        if (cursor.moveToFirst()) {
            max = cursor.getInt(0);
        }
        database.close();
        return max;
    }

    public List<Integer> getAllServerID() {
        SQLiteDatabase database = this.getWritableDatabase();
        List<Integer> listID = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT " + C_ONE_PRIMARY_KEY_SERVER_ID + " FROM " + TABLE_PRODUCTS + " ORDER BY " + C_ONE_PRIMARY_KEY_SERVER_ID + " ASC", null);
        while (cursor.moveToNext()) {
            listID.add(cursor.getInt(0));
        }

        database.close();
        return listID;
    }

    public StringBuilder commaDelimited(List<Integer> numbers) {

        //The string builder used to construct the string
        StringBuilder commaSepValueBuilder = new StringBuilder();

        //Looping through the list
        for (int i = 0; i < numbers.size(); i++) {
            //append the value into the builder
            commaSepValueBuilder.append(numbers.get(i));

            //if the value is not the last element of the list
            //then append the comma(,) as well
            if (i != numbers.size() - 1) {
                commaSepValueBuilder.append(", ");
            }
        }
        return commaSepValueBuilder;
    }

    public List<String> getMenuList(String source) {
        SQLiteDatabase database = this.getWritableDatabase();
        List<String> productsList = new ArrayList<String>();
        // Select All Query

        String selectQuery = "SELECT DISTINCT " + source + " FROM " + TABLE_FIRST_LEVEL + " ORDER BY " + source;

        Cursor cursor = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String a = cursor.getString(cursor.getColumnIndex(source));

                // Adding contact to list
                productsList.add(a);

            } while (cursor.moveToNext());
        }


        database.close();


        // return contact list
        return productsList;
    }


//
//    // Updating single contact
//    public int updateContact(Contact contact) {
//        SQLiteDatabase ddatabaseHandler = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, contact.getName());
//        values.put(KEY_PH_NO, contact.getPhoneNumber());
//
//        // updating row
//        return ddatabaseHandler.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//    }
//
//    // Deleting single contact
//    public void deleteContact(Contact contact) {
//        SQLiteDatabase ddatabaseHandler = this.getWritableDatabase();
//        ddatabaseHandler.delete(TABLE_CONTACTS, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//        ddatabaseHandler.close();
//    }
//
//
//    // Getting contacts Count
//    public int getContactsCount() {
//        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
//        SQLiteDatabase ddatabaseHandler = this.getReadableDatabase();
//        Cursor cursor = ddatabaseHandler.rawQuery(countQuery, null);
//        cursor.close();
//
//        // return count
//        return cursor.getCount();
//    }

}
