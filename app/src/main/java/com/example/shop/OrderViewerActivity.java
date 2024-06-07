package com.example.shop;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class OrderViewerActivity extends AppCompatActivity {

    ListView listViewOrders;
    DBHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_viewer);

        // Inicjalizacja DBHelper
        myDb = new DBHelper(this);

        // Inicjalizacja ListView
        listViewOrders = findViewById(R.id.listViewOrders);

        // Pobranie danych zamówień z bazy danych
        Cursor res = myDb.getAllData();

        // Utworzenie listy zamówień
        ArrayList<String> orderList = new ArrayList<>();

        // Dodanie danych zamówień do listy
        while (res.moveToNext()) {
            String produkty = res.getString(res.getColumnIndex("PRODUKTY"));
            double cena = res.getDouble(res.getColumnIndex("CENA"));
            String customerName = res.getString(res.getColumnIndex("NAZWA_ZAMAWIAJACEGO"));
            String email = res.getString(res.getColumnIndex("EMAIL")); // Pobierz adres e-mail
            String orderDetails = "Produkty: " + produkty + "\nCena: " + cena + "\nEmail: " + customerName; // Dodaj adres e-mail do szczegółów zamówienia
            orderList.add(orderDetails);
        }


        // Utworzenie adaptera dla ListView
        CustomOrderAdapter adapter = new CustomOrderAdapter(this, orderList);
        listViewOrders.setAdapter(adapter);


        // Ustawienie adaptera dla ListView
        listViewOrders.setAdapter(adapter);
    }
}
