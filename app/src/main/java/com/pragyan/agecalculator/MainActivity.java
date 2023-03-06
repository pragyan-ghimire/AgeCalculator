package com.pragyan.agecalculator;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public final String TAG = MainActivity.class.getSimpleName();
    Button button;
    Button buttonAge;
    TextView ageText;
    int aYear, aMonth, aDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        buttonAge = findViewById(R.id.button_age);
        ageText = findViewById(R.id.textView_age);

        if (savedInstanceState != null) {
            ageText.setText(savedInstanceState.getString("ageText"));
            button.setText(savedInstanceState.getString("birthDate"));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog pickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String month_string = Integer.toString(month + 1);
                        String day_string = Integer.toString(day);
                        String year_string = Integer.toString(year);
                        String dateBirth = month_string + "/" + day_string + "/" + year_string;
                        button.setText(dateBirth);
//                        Toast.makeText(MainActivity.this, dateBirth, Toast.LENGTH_SHORT).show();
                        aYear = year;
                        aMonth = month + 1;
                        aDay = day;
                    }
                }, year, month, day);
                pickerDialog.show();
                ageText.setText("");
            }
        });


        buttonAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (aYear != 0) {
                    LocalDate startDate;
                    LocalDate endDate;
                    int ageDay, ageYear, ageMonth;

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        endDate = LocalDate.now();
                        startDate = LocalDate.of(aYear, aMonth, aDay);
                        Period period = Period.between(startDate, endDate);
                        ageYear = period.getYears();
                        ageMonth = period.getMonths();
                        ageDay = period.getDays();

                        Log.d(TAG, Integer.toString(aYear));
                        Log.d(TAG, Integer.toString(aMonth));
                        Log.d(TAG, Integer.toString(aDay));
                        if (startDate.isBefore(endDate)) {
                            String age = (ageYear + "years " + ageMonth + "months " + ageDay + "days");
                            ageText.setText(age);
                        } else {
                            Toast.makeText(MainActivity.this, "Please enter valid DOB.", Toast.LENGTH_SHORT).show();
                        }

                    }

                } else {
                    Toast.makeText(MainActivity.this, "Please select Date Of Birth", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("ageText", ageText.getText().toString());
        outState.putString("birthDate", button.getText().toString());
    }
}