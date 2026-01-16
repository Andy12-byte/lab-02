package com.example.listycity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> datalist;

    Button addCityButton;

    Button deleteCityButton;

    int selectedIndex = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cityList = findViewById(R.id.city_list);
        addCityButton = findViewById(R.id.add_city_button);
        deleteCityButton = findViewById(R.id.delete_city_button);





        String []cities ={"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};

        datalist = new ArrayList<>();
        datalist.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, datalist);
        cityList.setAdapter(cityAdapter);           //important for merging two; use cityList to connect
        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedIndex = position;
            cityList.setItemChecked(position, true);
        });

        /*ADD Button listener*/

        addCityButton.setOnClickListener(view -> {

            EditText inputBox = new EditText(MainActivity.this);
            inputBox.setHint("City name");

            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle("New City");
            dialog.setView(inputBox);

            dialog.setPositiveButton("Add", (d, i) -> {

                String city = inputBox.getText().toString().trim();

                if (city.length()== 0) {
                    Toast.makeText(MainActivity.this, "You didn't type anything", Toast.LENGTH_SHORT).show();
                    return;
                }

                datalist.add(city);
                cityAdapter.notifyDataSetChanged();
            });

            dialog.setNegativeButton("Back", (d, i) -> d.dismiss());

            dialog.show();
        });

        /*DELETE Button listener*/
        deleteCityButton.setOnClickListener(view -> {

            // if no city has been tapped yet
            if (selectedIndex < 0) {
                Toast.makeText(MainActivity.this, "Select a city first", Toast.LENGTH_SHORT).show();
            } else {

                // optional: store the name (just for clarity/debugging)
                String removedCity = datalist.get(selectedIndex);

                // remove the selected city
                datalist.remove(selectedIndex);
                cityAdapter.notifyDataSetChanged();

                // reset selection
                selectedIndex = -1;
                cityList.clearChoices();

                Toast.makeText(MainActivity.this, removedCity + " removed", Toast.LENGTH_SHORT).show();
            }
        });


    }
}