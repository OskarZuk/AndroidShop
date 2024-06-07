// MainActivity.java
package com.example.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DBHelper myDb;
    CheckBox checkboxKomputer, checkboxKlawiatura, checkboxMyszka, checkboxAkcesorium;
    EditText editCena, editNazwaZamawiajacego, editEmail;
    Button btnZapisz, btnSendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(findViewById(R.id.toolbar));

        myDb = new DBHelper(this);

        checkboxKomputer = findViewById(R.id.checkboxKomputer);
        checkboxKlawiatura = findViewById(R.id.checkboxKlawiatura);
        checkboxMyszka = findViewById(R.id.checkboxMyszka);
        checkboxAkcesorium = findViewById(R.id.checkboxAkcesorium);
        editCena = findViewById(R.id.editTextCena);
        editNazwaZamawiajacego = findViewById(R.id.editTextEmail);
        editEmail = findViewById(R.id.editTextEmail); // New email EditText
        btnZapisz = findViewById(R.id.buttonZapisz);
        btnSendEmail = findViewById(R.id.buttonSendEmail);

        CheckBox.OnCheckedChangeListener checkBoxListener = new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotalCost();
            }
        };

        checkboxKomputer.setOnCheckedChangeListener(checkBoxListener);
        checkboxKlawiatura.setOnCheckedChangeListener(checkBoxListener);
        checkboxMyszka.setOnCheckedChangeListener(checkBoxListener);
        checkboxAkcesorium.setOnCheckedChangeListener(checkBoxListener);

        editCena.setFocusable(false);
        editCena.setClickable(false);

        btnZapisz.setOnClickListener(v -> {
            if (validateFields()) {
                double suma = calculateTotalCost();

                editCena.setText(String.valueOf(suma));

                StringBuilder zamowienie = new StringBuilder();
                if (checkboxKomputer.isChecked()) zamowienie.append("Komputer, ");
                if (checkboxKlawiatura.isChecked()) zamowienie.append("Klawiatura, ");
                if (checkboxMyszka.isChecked()) zamowienie.append("Myszka, ");
                if (checkboxAkcesorium.isChecked()) zamowienie.append("Akcesorium, ");

                // Pobierz adres e-mail z pola EditText
                String email = editEmail.getText().toString();

                boolean isInserted = myDb.insertData(
                        zamowienie.toString(),
                        suma,
                        editNazwaZamawiajacego.getText().toString(),
                        email
                );

                if (isInserted)
                    Toast.makeText(MainActivity.this, "Dane zostały zapisane", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Błąd przy zapisie danych", Toast.LENGTH_LONG).show();
            }
        });
        btnZapisz.setOnClickListener(v -> {
            if (validateFields()) {
                double suma = calculateTotalCost();

                editCena.setText(String.valueOf(suma));

                StringBuilder zamowienie = new StringBuilder();
                if (checkboxKomputer.isChecked()) zamowienie.append("Komputer, ");
                if (checkboxKlawiatura.isChecked()) zamowienie.append("Klawiatura, ");
                if (checkboxMyszka.isChecked()) zamowienie.append("Myszka, ");
                if (checkboxAkcesorium.isChecked()) zamowienie.append("Akcesorium, ");

                // Pobierz nazwę zamawiającego z pola EditText
                String nazwaZamawiajacego = editNazwaZamawiajacego.getText().toString();

                boolean isInserted = myDb.insertData(
                        zamowienie.toString(),
                        suma,
                        nazwaZamawiajacego, // Użyj nazwy zamawiającego zamiast adresu e-mail
                        ""
                );

                if (isInserted)
                    Toast.makeText(MainActivity.this, "Dane zostały zapisane", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Błąd przy zapisie danych", Toast.LENGTH_LONG).show();
            }
        });



        btnSendEmail.setOnClickListener(v -> {
            String recipient = editEmail.getText().toString().trim(); // Get email from EditText
            if (recipient.isEmpty()) {
                editEmail.setError("Pole jest wymagane");
                return;
            }

            String title = ((EditText)findViewById(R.id.tytulzamowienia)).getText().toString(); // Get title from EditText
            if (title.isEmpty()) {
                ((EditText)findViewById(R.id.tytulzamowienia)).setError("Pole jest wymagane");
                return;
            }

            // Utwórz treść wiadomości e-mail, zawierającą wybrane produkty
            StringBuilder bodyBuilder = new StringBuilder();
            bodyBuilder.append("Zamówione produkty:\n");

            if (checkboxKomputer.isChecked()) bodyBuilder.append("- Komputer\n");
            if (checkboxKlawiatura.isChecked()) bodyBuilder.append("- Klawiatura\n");
            if (checkboxMyszka.isChecked()) bodyBuilder.append("- Myszka\n");
            if (checkboxAkcesorium.isChecked()) bodyBuilder.append("- Akcesorium\n");

            String subject = title; // Ustaw tytuł na podstawie EditText tytulzamowienia
            String body = bodyBuilder.toString();

            EmailHelper.sendEmail(MainActivity.this, recipient, subject, body);
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.main) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            return true;
        }
         else if (id == R.id.order_viewer) {
            Intent intent = new Intent(getApplicationContext(), OrderViewerActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    private boolean validateFields() {
        if (!checkboxKomputer.isChecked() && !checkboxKlawiatura.isChecked() && !checkboxMyszka.isChecked() && !checkboxAkcesorium.isChecked()) {
            Toast.makeText(MainActivity.this, "Wybierz przynajmniej jeden produkt", Toast.LENGTH_LONG).show();
            return false;
        }
        if (editNazwaZamawiajacego.getText().toString().trim().isEmpty()) {
            editNazwaZamawiajacego.setError("Pole jest wymagane");
            return false;
        }
        if (editEmail.getText().toString().trim().isEmpty()) {
            editEmail.setError("Pole jest wymagane");
            return false;
        }
        return true;
    }

    private void updateTotalCost() {
        double suma = calculateTotalCost();
        editCena.setText(String.valueOf(suma));
    }

    private double calculateTotalCost() {
        double suma = 0;

        if (checkboxKomputer.isChecked()) suma += Double.parseDouble(checkboxKomputer.getTag().toString());
        if (checkboxKlawiatura.isChecked()) suma += Double.parseDouble(checkboxKlawiatura.getTag().toString());
        if (checkboxMyszka.isChecked()) suma += Double.parseDouble(checkboxMyszka.getTag().toString());
        if (checkboxAkcesorium.isChecked()) suma += Double.parseDouble(checkboxAkcesorium.getTag().toString());

        return suma;
    }
}
