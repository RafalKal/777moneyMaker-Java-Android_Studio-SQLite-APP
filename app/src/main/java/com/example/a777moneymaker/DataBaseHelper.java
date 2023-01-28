package com.example.a777moneymaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.SimpleCursorAdapter;
import androidx.annotation.Nullable;
import com.example.a777moneymaker.adapters.MyAccountsAdapter;
import com.example.a777moneymaker.adapters.MyCategoriesAdapter;
import com.example.a777moneymaker.adapters.MyTransactionAdapter;
import com.example.a777moneymaker.models.AccountModel;
import com.example.a777moneymaker.models.CategoryModel;
import com.example.a777moneymaker.models.NotificationModel;
import com.example.a777moneymaker.models.TransactionModel;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.logging.Log;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    Context context_;
    Context context__;

    // _ACCOUNTS_ FINAL VARIABLES
    public static final String USER_ACC_TABLE = "USER_ACC_TABLE";
    public static final String COLUMN_ACCOUNT_ID = "_id";
    public static final String COLUMN_ACCOUNT_NAME = "ACCOUNT_NAME";
    public static final String COLUMN_MAIN_ACCOUNT = "MAIN_ACCOUNT";
    public static final String COLUMN_ACCOUNT_BALANCE = "ACCOUNT_BALANCE";

    // _CATEGORY_ FINAL VARIABLES
    public static final String CATEGORY_TABLE = "CATEGORY_TABLE";
    public static final String COLUMN_CATEGORY_ID = "_id";
    public static final String COLUMN_CATEGORY_NAME = "CATEGORY_NAME";

    // _TRANSACTION_ FINAL VARIABLES
    public static final String TRANSACTION_TABLE = "TRANSACTION_TABLE";
    public static final String COLUMN_TRANSACTION_ID = "_id";
    public static final String COLUMN_TRANSACTION_NAME = "TRANSACTION_NAME";
    public static final String COLUMN_TRANSACTION_DESCRIPTION = "TRANSACTION_DESCRIPTION";
    public static final String COLUMN_TRANSACTION_PRICE = "TRANSACTION_PRICE";
    public static final String COLUMN_TRANSACTION_CATEGORY = "TRANSACTION_CATEGORY";
    public static final String COLUMN_TRANSACTION_ACCOUNT = "TRANSACTION_ACCOUNT";
    public static final String COLUMN_TRANSACTION_TYPE = "TRANSACTION_TYPE";
    public static final String COLUMN_TRANSACTION_DAY = "TRANSACTION_DAY";
    public static final String COLUMN_TRANSACTION_MONTH = "TRANSACTION_MONTH";
    public static final String COLUMN_TRANSACTION_YEAR = "TRANSACTION_YEAR";

    // _NOTIFICATION_ FINAL VARIABLES
    public static final String NOTIFICATION_TABLE = "NOTIFICATION_TABLE";
    public static final String COLUMN_NOTIFICATION_ID = "_id";
    public static final String COLUMN_NOTIFICATION_STATE = "NOTIFICATION_STATE";
    public static final String COLUMN_NOTIFICATION_LIMIT = "NOTIFICATION_LIMIT";


    public DataBaseHelper(@Nullable Context context) {
        super(context, "productionProcessDB.db", null, 1);
        context_ = context;
        context__ = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // STATEMENT FOR CREATING NOTIFICATION TABLE IN DATABASE
        String createNotificationTableStatement = "CREATE TABLE " + NOTIFICATION_TABLE + " (" + COLUMN_NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NOTIFICATION_STATE + " INTEGER, " + COLUMN_NOTIFICATION_LIMIT + " REAL)";

        // STATEMENT FOR CREATING ACCOUNT TABLE IN DATABASE
        String createAccountTableStatement = "CREATE TABLE " + USER_ACC_TABLE + " (" + COLUMN_ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ACCOUNT_NAME + " TEXT, " + COLUMN_MAIN_ACCOUNT + " BOOL, " + COLUMN_ACCOUNT_BALANCE + " REAL)";

        // STATEMENT FOR CREATING CATEGORY TABLE IN DATABASE
        String createCategoryTableStatement = "CREATE TABLE " + CATEGORY_TABLE + " (" + COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CATEGORY_NAME + " TEXT)";

        // STATEMENT FOR CREATING TRANSACTION TABLE IN DATABASE
        String createTransactionTableStatement = "CREATE TABLE " + TRANSACTION_TABLE + " (" + COLUMN_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TRANSACTION_NAME + " TEXT, " + COLUMN_TRANSACTION_DESCRIPTION + " TEXT, " + COLUMN_TRANSACTION_PRICE + " REAL, " + COLUMN_TRANSACTION_CATEGORY + " TEXT, " + COLUMN_TRANSACTION_ACCOUNT + " TEXT, " + COLUMN_TRANSACTION_TYPE + " TEXT, " + COLUMN_TRANSACTION_DAY + " INTEGER, " + COLUMN_TRANSACTION_MONTH + " INTEGER, "+ COLUMN_TRANSACTION_YEAR + " INTEGER)";


        //  EXECUTION OF THE ABOVE
        db.execSQL(createTransactionTableStatement);
        db.execSQL(createAccountTableStatement);
        db.execSQL(createCategoryTableStatement);
        db.execSQL(createNotificationTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    // -----------------------------------------------------------------------------------\
    // _NOTIFICATION_ FUNCTIONS  |   _NOTIFICATION_ FUNCTIONS  |   _NOTIFICATION_ FUNCTIONS           |
    // -----------------------------------------------------------------------------------/

    public boolean addNotificationModel(NotificationModel notificationModel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NOTIFICATION_STATE, notificationModel.getState());
        cv.put(COLUMN_NOTIFICATION_LIMIT, notificationModel.getLowerLimit());

        long insert = db.insert(NOTIFICATION_TABLE, null, cv);

        if (insert == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean editNotificationState(int state, float limit){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "UPDATE " + NOTIFICATION_TABLE +
                " SET " + COLUMN_NOTIFICATION_STATE + " = " + state + " , " + COLUMN_NOTIFICATION_LIMIT + " = " + limit + " " +
                "WHERE " + COLUMN_CATEGORY_ID + " = " + 1;

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            return true;
        }else {
            return false;
        }
    }

    public int getNotificationState() {
        List<NotificationModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + NOTIFICATION_TABLE  ;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            do {
                int notificationID = cursor.getInt(0);
                int state = cursor.getInt(1);
                float limit = cursor.getFloat(2);

                NotificationModel newNotification = new NotificationModel(notificationID, state, limit);
                returnList.add(newNotification);
            }while (cursor.moveToNext());
        }else {
            // EMPTY SECTION
        }
        cursor.close();
        db.close();
        return returnList.get(0).getState();
    }

    public float getNotificationLimit() {
        List<NotificationModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + NOTIFICATION_TABLE  ;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            do {
                int notificationID = cursor.getInt(0);
                int state = cursor.getInt(1);
                float limit = cursor.getFloat(2);

                NotificationModel newNotification = new NotificationModel(notificationID, state, limit);
                returnList.add(newNotification);
            }while (cursor.moveToNext());
        }else {
            // EMPTY SECTION
        }
        cursor.close();
        db.close();
        return returnList.get(0).getLowerLimit();
    }

    public boolean ifNotificationExist(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String sql ="SELECT " + COLUMN_NOTIFICATION_STATE +
                    " FROM " + NOTIFICATION_TABLE +
                    " WHERE " + COLUMN_NOTIFICATION_STATE + " = " + 1 + " OR " + COLUMN_NOTIFICATION_STATE + " = " + 0 ;
        cursor = db.rawQuery(sql,null);

        if(cursor.getCount()>0){
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }
    }


    // -----------------------------------------------------------------------------------\
    // _CATEGORY_ FUNCTIONS  |   _CATEGORY_ FUNCTIONS  |   _CATEGORY_ FUNCTIONS           |
    // -----------------------------------------------------------------------------------/

    public boolean addCategoryModel(CategoryModel categoryModel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CATEGORY_NAME, categoryModel.getName());

        long insert = db.insert(CATEGORY_TABLE, null, cv);

        if (insert == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean deleteCategoryModel(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + CATEGORY_TABLE + " WHERE " + COLUMN_CATEGORY_ID + " = " + id;

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            return true;
        }else {
            return false;
        }
    }

    public boolean editCategoryModel(int id, String categoryName){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "UPDATE " + CATEGORY_TABLE +
                             " SET " + COLUMN_CATEGORY_NAME + " = \"" + categoryName + "\" " +
                             "WHERE " + COLUMN_CATEGORY_ID + " = " + id;

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            return true;
        }else {
            return false;
        }
    }

    public List<CategoryModel> getEveryCategory() {
        List<CategoryModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + CATEGORY_TABLE  ;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            do {
                int categoryID = cursor.getInt(0);
                String name = cursor.getString(1);

                CategoryModel newCategory = new CategoryModel(categoryID, name);
                returnList.add(newCategory);
            }while (cursor.moveToNext());
        }else {
            // EMPTY SECTION
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public CategoryModel getCategoryModelByPosition(int id){
        for (int i = 0; i < getEveryCategory().size(); i++){
            if(getEveryAccount().get(i).getId() == id){
                return getEveryCategory().get(i);
            }
        }
        return null;
    }

    public CategoryModel getCategoryModelByID(int id){
        for (int i = 0; i < getEveryCategory().size(); i++){
            if(getEveryAccount().get(i).getId() == id){
                return getEveryCategory().get(i);
            }
        }
        return null;
    }

    public CategoryModel getCategoryModelByName(String name){
        for (int i = 0; i < getEveryCategory().size(); i++){
            if(getEveryCategory().get(i).getName() == name){
                return getEveryCategory().get(i);
            }
        }
        return null;
    }

    public MyCategoriesAdapter categoryListViewFromDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = {COLUMN_CATEGORY_ID, COLUMN_CATEGORY_NAME};
        Cursor cursor = db.query(CATEGORY_TABLE, columns, null, null, null, null, null);
        String[] fromFieldNames = new String[]{COLUMN_CATEGORY_ID, COLUMN_CATEGORY_NAME};
        int[] toVievIDs = new int[]{R.id.categoryID, R.id.categoryName};
        if(context_!=null) {
            MyCategoriesAdapter accountAdapter = new MyCategoriesAdapter(
                    context_,
                    R.layout.row_in_category_list,
                    cursor,
                    fromFieldNames,
                    toVievIDs
            );
            return accountAdapter;
        }else return null;
    }


    // -----------------------------------------------------------------------------------\
    // _ACCOUNT_ FUNCTIONS  |   _ACCOUNT_ FUNCTIONS  |   _ACCOUNT_ FUNCTIONS              |
    // -----------------------------------------------------------------------------------/

    public boolean addAccountModel(AccountModel accountModel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ACCOUNT_NAME, accountModel.getName());
        cv.put(COLUMN_MAIN_ACCOUNT, accountModel.isMainAcc());
        cv.put(COLUMN_ACCOUNT_BALANCE, accountModel.getBalance());

        long insert = db.insert(USER_ACC_TABLE, null, cv);

        if (insert == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean deleteAccountModel(AccountModel accountModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + USER_ACC_TABLE + " WHERE " + COLUMN_ACCOUNT_ID + " = " + accountModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            return true;
        }else {
            return false;
        }
    }

    public boolean deleteAccountModelByID(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + USER_ACC_TABLE + " WHERE " + COLUMN_ACCOUNT_ID + " = " + id;

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            return true;
        }else {
            return false;
        }
    }

    public boolean editAccountModel(int id, String accountName, float balance){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_ACCOUNT_NAME, accountName);
        values.put(COLUMN_ACCOUNT_BALANCE, balance);

        db.update(USER_ACC_TABLE, values, "_id = " + id, null);
        db.close();

        return false;
    }

    public List<AccountModel> getEveryAccount() {
        List<AccountModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + USER_ACC_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            do {
                int accountID = cursor.getInt(0);
                String name = cursor.getString(1);
                boolean isMainAcc = cursor.getInt(2) == 1 ? true: false;
                float balance = cursor.getFloat(3);

                AccountModel newAccount = new AccountModel(accountID, name, isMainAcc, balance);
                returnList.add(newAccount);
            }while (cursor.moveToNext());
        }else {
            // EMPTY SECTION
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public AccountModel getAccountModelByID(int id){
        for (int i = 0; i < getEveryAccount().size(); i++){
            if(getEveryAccount().get(i).getId() == id){
                return getEveryAccount().get(i);
            }
        }
        return null;
    }

    public AccountModel getAccountModelByName(String name){
        for (int i = 0; i < getEveryAccount().size(); i++){
            if(getEveryAccount().get(i).getName().equals(name)){
                return getEveryAccount().get(i);
            }
        }
        return null;
    }

    public MyAccountsAdapter accountListViewFromDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = {COLUMN_ACCOUNT_ID, COLUMN_ACCOUNT_NAME, COLUMN_MAIN_ACCOUNT, COLUMN_ACCOUNT_BALANCE};
        Cursor cursor = db.query(USER_ACC_TABLE, columns, null, null, null, null, null);
        String[] fromFieldNames = new String[]{COLUMN_ACCOUNT_ID, COLUMN_ACCOUNT_NAME, COLUMN_MAIN_ACCOUNT, COLUMN_ACCOUNT_BALANCE};
        int[] toVievIDs = new int[]{R.id.accountID, R.id.accountName, R.id.accountIsMain, R.id.accountBalance};
        if(context_!=null) {
            MyAccountsAdapter accountAdapter = new MyAccountsAdapter(
                    context_,
                    R.layout.row_in_account_list,
                    cursor,
                    fromFieldNames,
                    toVievIDs
            );
            return accountAdapter;
        }else return null;
    }


    // -----------------------------------------------------------------------------------\
    // _TRANSACTION_ FUNCTIONS  |   _TRANSACTION_ FUNCTIONS  |   _TRANSACTION_ FUNCTIONS  |
    // -----------------------------------------------------------------------------------/

    public boolean addTransactionModel(TransactionModel transactionModel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TRANSACTION_NAME, transactionModel.getName());
        cv.put(COLUMN_TRANSACTION_DESCRIPTION, transactionModel.getDescription());
        cv.put(COLUMN_TRANSACTION_PRICE, transactionModel.getPrice());
        cv.put(COLUMN_TRANSACTION_CATEGORY, transactionModel.getCategory());
        cv.put(COLUMN_TRANSACTION_ACCOUNT, transactionModel.getAccount());
        cv.put(COLUMN_TRANSACTION_TYPE, transactionModel.getType());
        cv.put(COLUMN_TRANSACTION_DAY, transactionModel.getDay());
        cv.put(COLUMN_TRANSACTION_MONTH, transactionModel.getMonth());
        cv.put(COLUMN_TRANSACTION_YEAR, transactionModel.getYear());

        long insert = db.insert(TRANSACTION_TABLE, null, cv);

        if (insert == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean deleteTransactionModel(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + TRANSACTION_TABLE + " WHERE " + COLUMN_TRANSACTION_ID + " = " + id;

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            return true;
        }else {
            return false;
        }
    }

    public boolean editTransactionAfterEditingAccount(String accountNameOld, String newAccountName){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_TRANSACTION_ACCOUNT, newAccountName);

        db.update(TRANSACTION_TABLE, values, COLUMN_TRANSACTION_ACCOUNT + " = \"" + accountNameOld + "\"", null);
        db.close();

        return false;
    }

    public boolean editTransactionModel(int id, String expenseName, String description, float price, String category, String account, String type, int day, int month, int year){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_TRANSACTION_NAME, expenseName);
        values.put(COLUMN_TRANSACTION_DESCRIPTION, description);
        values.put(COLUMN_TRANSACTION_PRICE, price);
        values.put(COLUMN_TRANSACTION_CATEGORY, category);
        values.put(COLUMN_TRANSACTION_ACCOUNT, account);
        values.put(COLUMN_TRANSACTION_TYPE, type);
        values.put(COLUMN_TRANSACTION_DAY, day);
        values.put(COLUMN_TRANSACTION_MONTH, month);
        values.put(COLUMN_TRANSACTION_YEAR, year);

        db.update(TRANSACTION_TABLE, values, "_id = " + id, null);
        db.close();

        return false;
    }

    public TransactionModel getTransactionModelByID(int id){
        for (int i = 0; i < getEveryTransaction().size(); i++){
            if(getEveryTransaction().get(i).getId() == id){
                return getEveryTransaction().get(i);
            }
        }
        return null;
    }

    public TransactionModel getTransactionModelByName(String name){
        for (int i = 0; i < getEveryTransaction().size(); i++){
            if(getEveryTransaction().get(i).getName() == name){
                return getEveryTransaction().get(i);
            }
        }
        return null;
    }

    public List<TransactionModel> getEveryTransaction() {
        List<TransactionModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + TRANSACTION_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            do {
                int transactionID = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                float price = cursor.getFloat(3);
                String category = cursor.getString(4);
                String account = cursor.getString(5);
                String type = cursor.getString(6);
                int day = cursor.getInt(7);
                int month = cursor.getInt(8);
                int year = cursor.getInt(9);

                TransactionModel newTransaction = new TransactionModel(transactionID, name, description, price, category, account, type, day, month, year);
                returnList.add(newTransaction);
            }while (cursor.moveToNext());
        }else {
            // EMPTY SECTION
        }
        cursor.close();
        db.close();

        return returnList;
    }

    public MyTransactionAdapter getEveryTransactionBetweenDates(String account, int day1, int month1, int year1, int day2, int month2, int year2) {

        SQLiteDatabase db = this.getWritableDatabase();

        String columns[] = {
                COLUMN_TRANSACTION_ID,
                COLUMN_TRANSACTION_NAME,
                COLUMN_TRANSACTION_DESCRIPTION,
                COLUMN_TRANSACTION_PRICE,
                COLUMN_TRANSACTION_CATEGORY,
                COLUMN_TRANSACTION_ACCOUNT,
                COLUMN_TRANSACTION_TYPE,
                COLUMN_TRANSACTION_DAY,
                COLUMN_TRANSACTION_MONTH,
                COLUMN_TRANSACTION_YEAR
        };

        String query = "SELECT * FROM " + TRANSACTION_TABLE + " WHERE " + COLUMN_TRANSACTION_ACCOUNT + " = \"" + account + "\" ORDER BY "+ COLUMN_TRANSACTION_ID + " DESC";

        Cursor cursor = db.rawQuery(query, null);

        String[] fromFieldNames = new String[]{
                COLUMN_TRANSACTION_ID,
                COLUMN_TRANSACTION_NAME,
                COLUMN_TRANSACTION_DESCRIPTION,
                COLUMN_TRANSACTION_PRICE,
                COLUMN_TRANSACTION_CATEGORY,
                COLUMN_TRANSACTION_ACCOUNT,
                COLUMN_TRANSACTION_TYPE,
                COLUMN_TRANSACTION_DAY,
                COLUMN_TRANSACTION_MONTH,
                COLUMN_TRANSACTION_YEAR
        };

        int[] toVievIDs = new int[]{
                R.id.transactionID,
                R.id.transactionName,
                R.id.transactionDescription,
                R.id.transactionValue,
                R.id.transactionCategory,
                R.id.transactionAccount,
                R.id.transactionType,
                R.id.transactionDateDay,
                R.id.transactionDateMonth,
                R.id.transactionDateYear
        };

        if(context__ != null) {
            MyTransactionAdapter transactionAdapter = new MyTransactionAdapter(
                    context__,
                    R.layout.row_in_transaction_list,
                    cursor,
                    fromFieldNames,
                    toVievIDs
            );
            return transactionAdapter;
        }else return null;
    }

    public MyTransactionAdapter transactionListViewFromDB(String account){
        SQLiteDatabase db = this.getWritableDatabase();

        String columns[] = {
                            COLUMN_TRANSACTION_ID,
                            COLUMN_TRANSACTION_NAME,
                            COLUMN_TRANSACTION_DESCRIPTION,
                            COLUMN_TRANSACTION_PRICE,
                            COLUMN_TRANSACTION_CATEGORY,
                            COLUMN_TRANSACTION_ACCOUNT,
                            COLUMN_TRANSACTION_TYPE,
                            COLUMN_TRANSACTION_DAY,
                            COLUMN_TRANSACTION_MONTH,
                            COLUMN_TRANSACTION_YEAR
                            };

        String query = "SELECT * FROM " + TRANSACTION_TABLE + " WHERE " + COLUMN_TRANSACTION_ACCOUNT + " = \"" + account + "\" ORDER BY "+ COLUMN_TRANSACTION_ID + " DESC";

        Cursor cursor = db.rawQuery(query, null);

        String[] fromFieldNames = new String[]{
                                                COLUMN_TRANSACTION_ID,
                                                COLUMN_TRANSACTION_NAME,
                                                COLUMN_TRANSACTION_DESCRIPTION,
                                                COLUMN_TRANSACTION_PRICE,
                                                COLUMN_TRANSACTION_CATEGORY,
                                                COLUMN_TRANSACTION_ACCOUNT,
                                                COLUMN_TRANSACTION_TYPE,
                                                COLUMN_TRANSACTION_DAY,
                                                COLUMN_TRANSACTION_MONTH,
                                                COLUMN_TRANSACTION_YEAR
                                                };

        int[] toVievIDs = new int[]{
                            R.id.transactionID,
                            R.id.transactionName,
                            R.id.transactionDescription,
                            R.id.transactionValue,
                            R.id.transactionCategory,
                            R.id.transactionAccount,
                            R.id.transactionType,
                            R.id.transactionDateDay,
                            R.id.transactionDateMonth,
                            R.id.transactionDateYear
                            };

        if(context__ != null) {
            MyTransactionAdapter transactionAdapter = new MyTransactionAdapter(
                    context__,
                    R.layout.row_in_transaction_list,
                    cursor,
                    fromFieldNames,
                    toVievIDs
            );
            return transactionAdapter;
        }else return null;
    }

    public MyTransactionAdapter expenseListViewFromDB(String account){
        SQLiteDatabase db = this.getWritableDatabase();

        String type = "WYDATEK";

        String columns[] = {
                COLUMN_TRANSACTION_ID,
                COLUMN_TRANSACTION_NAME,
                COLUMN_TRANSACTION_DESCRIPTION,
                COLUMN_TRANSACTION_PRICE,
                COLUMN_TRANSACTION_CATEGORY,
                COLUMN_TRANSACTION_ACCOUNT,
                COLUMN_TRANSACTION_TYPE,
                COLUMN_TRANSACTION_DAY,
                COLUMN_TRANSACTION_MONTH,
                COLUMN_TRANSACTION_YEAR
        };

        String query = "SELECT * FROM " + TRANSACTION_TABLE + " WHERE " + COLUMN_TRANSACTION_ACCOUNT + " = \"" + account + "\" AND " + COLUMN_TRANSACTION_TYPE + " = \"" + type + "\" ORDER BY " + COLUMN_TRANSACTION_ID + " DESC";

        Cursor cursor = db.rawQuery(query, null);

        String[] fromFieldNames = new String[]{
                COLUMN_TRANSACTION_ID,
                COLUMN_TRANSACTION_NAME,
                COLUMN_TRANSACTION_DESCRIPTION,
                COLUMN_TRANSACTION_PRICE,
                COLUMN_TRANSACTION_CATEGORY,
                COLUMN_TRANSACTION_ACCOUNT,
                COLUMN_TRANSACTION_TYPE,
                COLUMN_TRANSACTION_DAY,
                COLUMN_TRANSACTION_MONTH,
                COLUMN_TRANSACTION_YEAR
        };

        int[] toVievIDs = new int[]{
                R.id.transactionID,
                R.id.transactionName,
                R.id.transactionDescription,
                R.id.transactionValue,
                R.id.transactionCategory,
                R.id.transactionAccount,
                R.id.transactionType,
                R.id.transactionDateDay,
                R.id.transactionDateMonth,
                R.id.transactionDateYear
        };

        if(context__!=null) {
            MyTransactionAdapter transactionAdapter = new MyTransactionAdapter(
                    context__,
                    R.layout.row_in_transaction_list,
                    cursor,
                    fromFieldNames,
                    toVievIDs
            );
            return transactionAdapter;
        }else return null;
    }

    public MyTransactionAdapter incomeListViewFromDB(String account){
        SQLiteDatabase db = this.getWritableDatabase();

        String type = "WPŁYW";

        String columns[] = {
                COLUMN_TRANSACTION_ID,
                COLUMN_TRANSACTION_NAME,
                COLUMN_TRANSACTION_DESCRIPTION,
                COLUMN_TRANSACTION_PRICE,
                COLUMN_TRANSACTION_CATEGORY,
                COLUMN_TRANSACTION_ACCOUNT,
                COLUMN_TRANSACTION_TYPE,
                COLUMN_TRANSACTION_DAY,
                COLUMN_TRANSACTION_MONTH,
                COLUMN_TRANSACTION_YEAR
        };

        String query = "SELECT * FROM " + TRANSACTION_TABLE + " WHERE " + COLUMN_TRANSACTION_ACCOUNT + " = \"" + account + "\" AND " + COLUMN_TRANSACTION_TYPE + " = \"" + type + "\" ORDER BY " + COLUMN_TRANSACTION_ID + " DESC";

        Cursor cursor = db.rawQuery(query, null);

        String[] fromFieldNames = new String[]{
                COLUMN_TRANSACTION_ID,
                COLUMN_TRANSACTION_NAME,
                COLUMN_TRANSACTION_DESCRIPTION,
                COLUMN_TRANSACTION_PRICE,
                COLUMN_TRANSACTION_CATEGORY,
                COLUMN_TRANSACTION_ACCOUNT,
                COLUMN_TRANSACTION_TYPE,
                COLUMN_TRANSACTION_DAY,
                COLUMN_TRANSACTION_MONTH,
                COLUMN_TRANSACTION_YEAR
        };

        int[] toVievIDs = new int[]{
                R.id.transactionID,
                R.id.transactionName,
                R.id.transactionDescription,
                R.id.transactionValue,
                R.id.transactionCategory,
                R.id.transactionAccount,
                R.id.transactionType,
                R.id.transactionDateDay,
                R.id.transactionDateMonth,
                R.id.transactionDateYear
        };

        if(context__!=null) {
            MyTransactionAdapter transactionAdapter = new MyTransactionAdapter(
                    context__,
                    R.layout.row_in_transaction_list,
                    cursor,
                    fromFieldNames,
                    toVievIDs
            );
            return transactionAdapter;
        }else return null;
    }

    public MyTransactionAdapter specialListViewFromDB(String account, String phrase){
        SQLiteDatabase db = this.getWritableDatabase();

        String columns[] = {
                COLUMN_TRANSACTION_ID,
                COLUMN_TRANSACTION_NAME,
                COLUMN_TRANSACTION_DESCRIPTION,
                COLUMN_TRANSACTION_PRICE,
                COLUMN_TRANSACTION_CATEGORY,
                COLUMN_TRANSACTION_ACCOUNT,
                COLUMN_TRANSACTION_TYPE,
                COLUMN_TRANSACTION_DAY,
                COLUMN_TRANSACTION_MONTH,
                COLUMN_TRANSACTION_YEAR
        };

        String query = "SELECT * FROM " + TRANSACTION_TABLE +
                " WHERE " + COLUMN_TRANSACTION_ACCOUNT + " = \"" + account + "\" " +
                "AND (" + COLUMN_TRANSACTION_TYPE + " = \"" + phrase + "\" " +
                    "OR " + COLUMN_TRANSACTION_NAME + " = \"" + phrase + "\" " +
                    "OR " + COLUMN_TRANSACTION_DESCRIPTION + " = \"" + phrase + "\" " +
                    "OR " + COLUMN_TRANSACTION_TYPE + " = \"" + phrase + "\" " +
                    "OR " + COLUMN_TRANSACTION_CATEGORY + " = \"" + phrase + "\") " +
                "ORDER BY " + COLUMN_TRANSACTION_ID + " DESC";

        Cursor cursor = db.rawQuery(query, null);

        String[] fromFieldNames = new String[]{
                COLUMN_TRANSACTION_ID,
                COLUMN_TRANSACTION_NAME,
                COLUMN_TRANSACTION_DESCRIPTION,
                COLUMN_TRANSACTION_PRICE,
                COLUMN_TRANSACTION_CATEGORY,
                COLUMN_TRANSACTION_ACCOUNT,
                COLUMN_TRANSACTION_TYPE,
                COLUMN_TRANSACTION_DAY,
                COLUMN_TRANSACTION_MONTH,
                COLUMN_TRANSACTION_YEAR
        };

        int[] toVievIDs = new int[]{
                R.id.transactionID,
                R.id.transactionName,
                R.id.transactionDescription,
                R.id.transactionValue,
                R.id.transactionCategory,
                R.id.transactionAccount,
                R.id.transactionType,
                R.id.transactionDateDay,
                R.id.transactionDateMonth,
                R.id.transactionDateYear
        };

        if(context__!=null) {
            MyTransactionAdapter transactionAdapter = new MyTransactionAdapter(
                    context__,
                    R.layout.row_in_transaction_list,
                    cursor,
                    fromFieldNames,
                    toVievIDs
            );
            return transactionAdapter;
        }else return null;
    }

    public MyTransactionAdapter specialListViewFromDB1(String account, String phrase){
        SQLiteDatabase db = this.getWritableDatabase();

        String columns[] = {
                COLUMN_TRANSACTION_ID,
                COLUMN_TRANSACTION_NAME,
                COLUMN_TRANSACTION_DESCRIPTION,
                COLUMN_TRANSACTION_PRICE,
                COLUMN_TRANSACTION_CATEGORY,
                COLUMN_TRANSACTION_ACCOUNT,
                COLUMN_TRANSACTION_TYPE,
                COLUMN_TRANSACTION_DAY,
                COLUMN_TRANSACTION_MONTH,
                COLUMN_TRANSACTION_YEAR
        };

        String query = "SELECT * FROM " + TRANSACTION_TABLE +
                " WHERE " + COLUMN_TRANSACTION_ACCOUNT + " = \"" + account + "\" " +
                "AND ( " + COLUMN_TRANSACTION_TYPE + " LIKE '" + phrase + "' " +
                "OR " + COLUMN_TRANSACTION_NAME + " LIKE '" + phrase + "' " +
                "OR " + COLUMN_TRANSACTION_DESCRIPTION + " LIKE '" + phrase + "' " +
                "OR " + COLUMN_TRANSACTION_TYPE + " LIKE '" + phrase + "' " +
                "OR " + COLUMN_TRANSACTION_CATEGORY + " LIKE '" + phrase + "' ) " +
                "ORDER BY " + COLUMN_TRANSACTION_ID + " DESC";

        Cursor cursor = db.rawQuery(query, null);

        String[] fromFieldNames = new String[]{
                COLUMN_TRANSACTION_ID,
                COLUMN_TRANSACTION_NAME,
                COLUMN_TRANSACTION_DESCRIPTION,
                COLUMN_TRANSACTION_PRICE,
                COLUMN_TRANSACTION_CATEGORY,
                COLUMN_TRANSACTION_ACCOUNT,
                COLUMN_TRANSACTION_TYPE,
                COLUMN_TRANSACTION_DAY,
                COLUMN_TRANSACTION_MONTH,
                COLUMN_TRANSACTION_YEAR
        };

        int[] toVievIDs = new int[]{
                R.id.transactionID,
                R.id.transactionName,
                R.id.transactionDescription,
                R.id.transactionValue,
                R.id.transactionCategory,
                R.id.transactionAccount,
                R.id.transactionType,
                R.id.transactionDateDay,
                R.id.transactionDateMonth,
                R.id.transactionDateYear
        };

        if(context__!=null) {
            MyTransactionAdapter transactionAdapter = new MyTransactionAdapter(
                    context__,
                    R.layout.row_in_transaction_list,
                    cursor,
                    fromFieldNames,
                    toVievIDs
            );
            return transactionAdapter;
        }else return null;
    }

    public MyTransactionAdapter specialListViewFromDB2(String account, String phrase){
        SQLiteDatabase db = this.getWritableDatabase();

        String columns[] = {
                COLUMN_TRANSACTION_ID,
                COLUMN_TRANSACTION_NAME,
                COLUMN_TRANSACTION_DESCRIPTION,
                COLUMN_TRANSACTION_PRICE,
                COLUMN_TRANSACTION_CATEGORY,
                COLUMN_TRANSACTION_ACCOUNT,
                COLUMN_TRANSACTION_TYPE,
                COLUMN_TRANSACTION_DAY,
                COLUMN_TRANSACTION_MONTH,
                COLUMN_TRANSACTION_YEAR
        };

        String query = "SELECT * FROM TRANSACTION_TABLE " +
                       "WHERE  TRANSACTION_ACCOUNT = \"" + account + "\" AND " +
                              "(TRANSACTION_CATEGORY    LIKE '%" + phrase + "%' OR " +
                              "TRANSACTION_ACCOUNT      LIKE '%" + phrase + "%' OR " +
                              //"TRANSACTION_PRICE         =     " + Float.parseFloat(phrase) + " OR " +
                              "TRANSACTION_TYPE         LIKE '%" + phrase + "%' OR " +
                              "TRANSACTION_DESCRIPTION  LIKE '%" + phrase + "%' OR " +
                              "TRANSACTION_NAME         LIKE '%" + phrase + "%')";


        Cursor cursor = db.rawQuery(query, null);

        String[] fromFieldNames = new String[]{
                COLUMN_TRANSACTION_ID,
                COLUMN_TRANSACTION_NAME,
                COLUMN_TRANSACTION_DESCRIPTION,
                COLUMN_TRANSACTION_PRICE,
                COLUMN_TRANSACTION_CATEGORY,
                COLUMN_TRANSACTION_ACCOUNT,
                COLUMN_TRANSACTION_TYPE,
                COLUMN_TRANSACTION_DAY,
                COLUMN_TRANSACTION_MONTH,
                COLUMN_TRANSACTION_YEAR
        };

        int[] toVievIDs = new int[]{
                R.id.transactionID,
                R.id.transactionName,
                R.id.transactionDescription,
                R.id.transactionValue,
                R.id.transactionCategory,
                R.id.transactionAccount,
                R.id.transactionType,
                R.id.transactionDateDay,
                R.id.transactionDateMonth,
                R.id.transactionDateYear
        };

        if(context__!=null) {
            MyTransactionAdapter transactionAdapter = new MyTransactionAdapter(
                    context__,
                    R.layout.row_in_transaction_list,
                    cursor,
                    fromFieldNames,
                    toVievIDs
            );
            return transactionAdapter;
        }else return null;
    }

    public boolean checkDateRange(int day1, int month1, int year1, int day2, int month2, int year2, int day3, int month3, int year3) {
        LocalDate date1 = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date1 = LocalDate.of(year1, month1, day1);
        }
        LocalDate date2 = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date2 = LocalDate.of(year2, month2, day2);
        }
        LocalDate date3 = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date3 = LocalDate.of(year3, month3, day3);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return (date3.isAfter(date1) && date3.isBefore(date2)) || date3.equals(date1) || date3.equals(date2);
        }
        return false;
    }

}
