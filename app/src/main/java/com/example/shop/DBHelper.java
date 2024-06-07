package com.example.shop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Orders.db"; // Nazwa bazy danych
    public static final String TABLE_NAME = "orders_table"; // Nazwa tabeli
    public static final String COL_1 = "ID";
    public static final String COL_2 = "PRODUKTY";
    public static final String COL_3 = "CENA";
    public static final String COL_4 = "NAZWA_ZAMAWIAJACEGO";
    public static final String COL_5 = "EMAIL"; // Dodane pole EMAIL

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 2); // Zwiększ wersję bazy danych
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, PRODUKTY TEXT, CENA REAL, NAZWA_ZAMAWIAJACEGO TEXT, EMAIL TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String produkty, double cena, String nazwaZamawiajacego, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, produkty);
        contentValues.put(COL_3, cena);
        contentValues.put(COL_4, nazwaZamawiajacego);
        contentValues.put(COL_5, email);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public void deleteDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
