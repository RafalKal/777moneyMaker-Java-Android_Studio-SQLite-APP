package com.example.a777moneymaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;
import androidx.annotation.Nullable;
import com.example.a777moneymaker.models.AccountModel;
import com.example.a777moneymaker.models.CategoryModel;
import com.example.a777moneymaker.models.ExpenseModel;
import com.example.a777moneymaker.models.IncomeModel;
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

    // _EXPENSE_ FINAL VARIABLES
    public static final String EXPENSE_TABLE = "EXPENSE_TABLE";
    public static final String COLUMN_EXPENSE_ID = "_id";
    public static final String COLUMN_EXPENSE_NAME = "EXPENSE_NAME";
    public static final String COLUMN_EXPENSE_DESCRIPTION = "EXPENSE_DESCRIPTION";
    public static final String COLUMN_EXPENSE_PRICE = "EXPENSE_PRICE";
    public static final String COLUMN_EXPENSE_CATEGORY = "EXPENSE_CATEGORY";
    public static final String COLUMN_EXPENSE_ACCOUNT = "EXPENSE_ACCOUNT";
    public static final String COLUMN_EXPENSE_TYPE = "EXPENSE_TYPE";
    public static final String COLUMN_EXPENSE_DAY = "EXPENSE_DAY";
    public static final String COLUMN_EXPENSE_MONTH = "EXPENSE_MONTH";
    public static final String COLUMN_EXPENSE_YEAR = "EXPENSE_YEAR";

    // _INCOME_ FINAL VARIABLES
    public static final String INCOME_TABLE = "INCOME_TABLE";
    public static final String COLUMN_INCOME_ID = "ID_INCOME";
    public static final String COLUMN_INCOME_NAME = "INCOME_NAME";
    public static final String COLUMN_INCOME_DESCRIPTION = "INCOME_DESCRIPTION";
    public static final String COLUMN_INCOME_PRICE = "INCOME_PRICE";
    public static final String COLUMN_INCOME_CATEGORY = "INCOME_CATEGORY";
    public static final String COLUMN_INCOME_ACCOUNT = "INCOME_ACCOUNT";
    public static final String COLUMN_INCOME_DAY = "INCOME_DAY";
    public static final String COLUMN_INCOME_MONTH = "INCOME_MONTH";
    public static final String COLUMN_INCOME_YEAR = "INCOME_YEAR";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "productionProcessDB9.db", null, 1);
        context_ = context;
        context__ = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // STATEMENT FOR CREATING ACCOUNT TABLE IN DATABASE
        String createAccountTableStatement = "CREATE TABLE " + USER_ACC_TABLE + " (" + COLUMN_ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ACCOUNT_NAME + " TEXT, " + COLUMN_MAIN_ACCOUNT + " BOOL, " + COLUMN_ACCOUNT_BALANCE + " REAL)";

        // STATEMENT FOR CREATING CATEGORY TABLE IN DATABASE
        String createCategoryTableStatement = "CREATE TABLE " + CATEGORY_TABLE + " (" + COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CATEGORY_NAME + " TEXT)";

        // STATEMENT FOR CREATING EXPENSE TABLE IN DATABASE
        String createExpenseTableStatement = "CREATE TABLE " + EXPENSE_TABLE + " (" + COLUMN_EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EXPENSE_NAME + " TEXT, " + COLUMN_EXPENSE_DESCRIPTION + " TEXT, " + COLUMN_EXPENSE_PRICE + " REAL, " + COLUMN_EXPENSE_CATEGORY + " TEXT, " + COLUMN_EXPENSE_ACCOUNT + " TEXT, " + COLUMN_EXPENSE_TYPE + " TEXT, " + COLUMN_EXPENSE_DAY + " INTEGER, " + COLUMN_EXPENSE_MONTH + " INTEGER, "+ COLUMN_EXPENSE_YEAR + " INTEGER)";

        // STATEMENT FOR CREATING INCOME TABLE IN DATABASE
        String createIncomeTableStatement = "CREATE TABLE " + INCOME_TABLE + " (" + COLUMN_INCOME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_INCOME_NAME + " TEXT, " + COLUMN_INCOME_DESCRIPTION + " TEXT, " + COLUMN_INCOME_PRICE + " REAL, " + COLUMN_INCOME_CATEGORY + " TEXT, " + COLUMN_INCOME_ACCOUNT + ", TEXT" + COLUMN_INCOME_DAY + " INTEGER, " + COLUMN_INCOME_MONTH + " INTEGER, "+ COLUMN_INCOME_YEAR + " INTEGER)";

        //  EXECUTION OF THE ABOVE
        db.execSQL(createExpenseTableStatement);
        db.execSQL(createIncomeTableStatement);
        db.execSQL(createAccountTableStatement);
        db.execSQL(createCategoryTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    // ---------------------\
    // _CATEGORY_ FUNCTIONS  |
    // ---------------------/
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

    public boolean deleteCategoryModel(CategoryModel categoryModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + CATEGORY_TABLE + " WHERE " + COLUMN_CATEGORY_ID + " = " + categoryModel.getId();

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

    public SimpleCursorAdapter categoryListViewFromDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = {COLUMN_CATEGORY_ID, COLUMN_CATEGORY_NAME};
        Cursor cursor = db.query(CATEGORY_TABLE, columns, null, null, null, null, null);
        String[] fromFieldNames = new String[]{COLUMN_CATEGORY_ID, COLUMN_CATEGORY_NAME};
        int[] toVievIDs = new int[]{R.id.categoryID, R.id.categoryName};
        if(context_!=null) {
            SimpleCursorAdapter accountAdapter = new SimpleCursorAdapter(
                    context_,
                    R.layout.row_in_category_list,
                    cursor,
                    fromFieldNames,
                    toVievIDs
            );
            return accountAdapter;
        }else return null;
    }


    // ---------------------\
    // _ACCOUNT_ FUNCTIONS  |
    // ---------------------/
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
            if(getEveryAccount().get(i).getName() == name){
                return getEveryAccount().get(i);
            }
        }
        return null;
    }

    public SimpleCursorAdapter accountListViewFromDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = {COLUMN_ACCOUNT_ID, COLUMN_ACCOUNT_NAME, COLUMN_MAIN_ACCOUNT, COLUMN_ACCOUNT_BALANCE};
        Cursor cursor = db.query(USER_ACC_TABLE, columns, null, null, null, null, null);
        String[] fromFieldNames = new String[]{COLUMN_ACCOUNT_ID, COLUMN_ACCOUNT_NAME, COLUMN_MAIN_ACCOUNT, COLUMN_ACCOUNT_BALANCE};
        int[] toVievIDs = new int[]{R.id.accountID, R.id.accountName, R.id.accountIsMain, R.id.accountBalance};
        if(context_!=null) {
            SimpleCursorAdapter accountAdapter = new SimpleCursorAdapter(
                    context_,
                    R.layout.row_in_account_list,
                    cursor,
                    fromFieldNames,
                    toVievIDs
            );
            return accountAdapter;
        }else return null;
    }

    // ---------------------\
    // _EXPENSE_ FUNCTIONS  |
    // ---------------------/
    public boolean addExpenseModel(ExpenseModel expenseModel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EXPENSE_NAME, expenseModel.getName());
        cv.put(COLUMN_EXPENSE_DESCRIPTION, expenseModel.getDescription());
        cv.put(COLUMN_EXPENSE_PRICE, expenseModel.getPrice());
        cv.put(COLUMN_EXPENSE_CATEGORY, expenseModel.getCategory());
        cv.put(COLUMN_EXPENSE_ACCOUNT, expenseModel.getAccount());
        cv.put(COLUMN_EXPENSE_TYPE, expenseModel.getType());
        cv.put(COLUMN_EXPENSE_DAY, expenseModel.getDay());
        cv.put(COLUMN_EXPENSE_MONTH, expenseModel.getMonth());
        cv.put(COLUMN_EXPENSE_YEAR, expenseModel.getYear());

        long insert = db.insert(EXPENSE_TABLE, null, cv);

        if (insert == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean deleteExpenseModel(ExpenseModel expenseModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + EXPENSE_TABLE + " WHERE " + COLUMN_EXPENSE_ID + " = " + expenseModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            return true;
        }else {
            return false;
        }
    }

    public List<ExpenseModel> getEveryExpense() {
        List<ExpenseModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + EXPENSE_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            do {
                int expenseID = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                float price = cursor.getFloat(3);
                String category = cursor.getString(4);
                String account = cursor.getString(5);
                String type = cursor.getString(6);
                int day = cursor.getInt(7);
                int month = cursor.getInt(8);
                int year = cursor.getInt(9);

                ExpenseModel newExpense = new ExpenseModel(expenseID, name, description, price, category, account, type, day, month, year);
                returnList.add(newExpense);
            }while (cursor.moveToNext());
        }else {
            // EMPTY SECTION
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public SimpleCursorAdapter transactionListViewFromDB(){
        SQLiteDatabase db = this.getWritableDatabase();

        String columns[] = {
                            COLUMN_EXPENSE_ID,
                            COLUMN_EXPENSE_NAME,
                            COLUMN_EXPENSE_DESCRIPTION,
                            COLUMN_EXPENSE_PRICE,
                            COLUMN_EXPENSE_CATEGORY,
                            COLUMN_EXPENSE_ACCOUNT,
                            COLUMN_EXPENSE_TYPE,
                            COLUMN_EXPENSE_DAY,
                            COLUMN_EXPENSE_MONTH,
                            COLUMN_EXPENSE_YEAR
                            };

        Cursor cursor = db.query(EXPENSE_TABLE, columns, null, null, null, null, null);

        String[] fromFieldNames = new String[]{
                                                COLUMN_EXPENSE_ID,
                                                COLUMN_EXPENSE_NAME,
                                                COLUMN_EXPENSE_DESCRIPTION,
                                                COLUMN_EXPENSE_PRICE,
                                                COLUMN_EXPENSE_CATEGORY,
                                                COLUMN_EXPENSE_ACCOUNT,
                                                COLUMN_EXPENSE_TYPE,
                                                COLUMN_EXPENSE_DAY,
                                                COLUMN_EXPENSE_MONTH,
                                                COLUMN_EXPENSE_YEAR
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
            SimpleCursorAdapter transactionAdapter = new SimpleCursorAdapter(
                    context__,
                    R.layout.row_in_transaction_list,
                    cursor,
                    fromFieldNames,
                    toVievIDs
            );
            return transactionAdapter;
        }else return null;
    }

    // ---------------------\
    // _INCOME_ FUNCTIONS  |
    // ---------------------/
    public boolean addIncomeModel(IncomeModel incomeModel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_INCOME_NAME, incomeModel.getName());
        cv.put(COLUMN_INCOME_DESCRIPTION, incomeModel.getDescription());
        cv.put(COLUMN_INCOME_PRICE, incomeModel.getPrice());
        cv.put(COLUMN_INCOME_CATEGORY, incomeModel.getCategory());
        cv.put(COLUMN_INCOME_ACCOUNT, incomeModel.getAccount());
        cv.put(COLUMN_INCOME_DAY, incomeModel.getDay());
        cv.put(COLUMN_INCOME_MONTH, incomeModel.getMonth());
        cv.put(COLUMN_INCOME_YEAR, incomeModel.getYear());

        long insert = db.insert(INCOME_TABLE, null, cv);

        if (insert == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean deleteIncomeModel(IncomeModel incomeModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + INCOME_TABLE + " WHERE " + COLUMN_INCOME_ID + " = " + incomeModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            return true;
        }else {
            return false;
        }
    }

    public List<IncomeModel> getEveryIncome() {
        List<IncomeModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + INCOME_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            do {
                int incomeID = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                float price = cursor.getFloat(3);
                String category = cursor.getString(4);
                String account = cursor.getString(5);
                int day = cursor.getInt(6);
                int month = cursor.getInt(7);
                int year = cursor.getInt(8);

                IncomeModel newIncome = new IncomeModel(incomeID, name, description, price, category, account, day, month, year);
                returnList.add(newIncome);
            }while (cursor.moveToNext());
        }else {
            // EMPTY SECTION
        }
        cursor.close();
        db.close();
        return returnList;
    }

}
