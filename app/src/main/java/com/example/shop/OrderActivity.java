package com.example.shop;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderActivity extends AppCompatActivity {

    ListView listViewOrders;
    DBHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // Inicjalizacja DBHelper
        myDb = new DBHelper(this);

        // Inicjalizacja ListView
        listViewOrders = findViewById(R.id.listViewOrders);

        // Pobranie danych zamówień z bazy danych
        Cursor res = myDb.getAllData();

        // Utworzenie adaptera dla ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item);

        // Dodanie danych zamówień do adaptera
        while (res.moveToNext()) {
            String orderDetails = "Komputer: " + res.getString(1) + "\n" +
                    "Klawiatura: " + res.getString(2) + "\n" +
                    "Myszka: " + res.getString(3) + "\n" +
                    "Akcesorium: " + res.getString(4) + "\n" +
                    "Cena: " + res.getString(5) + "\n" +
                    "Data Zamówienia: " + res.getString(6) + "\n" +
                    "Zamawiający: " + res.getString(7) + "\n" +
                    "E-mail: " + res.getString(8); // Dodajemy e-mail
            adapter.add(orderDetails);
        }


        // Ustawienie adaptera dla ListView
        listViewOrders.setAdapter(adapter);
    }
}
