package com.example.gotolockers;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mcsoft.timerangepickerdialog.RangeTimePickerDialog;

import java.util.Calendar;
import java.util.Locale;

public class LockerReservation extends AppCompatActivity implements RangeTimePickerDialog.ISelectedTime {

    EditText startET;
    EditText endET;

    RangeTimePickerDialog dialog;
    FragmentManager fragmentManager;
    int startHour = 0;
    int startMinute = 0;
    int endHour = 1;
    int endMinute = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locker_reservation);

        Intent intent = getIntent();
        int containerNum = intent.getIntExtra("Container Number", 1);
        int containerType = intent.getIntExtra("Container Type", 0);

        setTitle(getString(R.string.reservation_title));

        TextView contTV = findViewById(R.id.contNumTV);
        contTV.setText(getString(R.string.container_num, containerNum));
        TextView contTypeTV = findViewById(R.id.contTypeTV);
        contTypeTV.setText(getResources().getStringArray(R.array.container_type)[containerType]);

        fragmentManager = getFragmentManager();

        Calendar now = Calendar.getInstance();
        startHour = now.get(Calendar.HOUR_OF_DAY);
        startMinute = now.get(Calendar.MINUTE);
        endHour = startHour + 1;
        endMinute = startMinute;

        startET = findViewById(R.id.startTimeET);
        startET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configureTPD(RangeTimePickerDialog.InitialOpenedTab.START_CLOCK_TAB);
                dialog.show(fragmentManager, "");
            }
        });

        endET = findViewById(R.id.endTimeET);
        endET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configureTPD(RangeTimePickerDialog.InitialOpenedTab.END_CLOCK_TAB);
                dialog.show(fragmentManager, "");
            }
        });
    }

    private void configureTPD(RangeTimePickerDialog.InitialOpenedTab tab) {
        // Create an instance of the dialog fragment and show it
        dialog = new RangeTimePickerDialog();
        dialog.newInstance();
        dialog.setRadiusDialog(20); // Set radius of dialog (default is 50)
        dialog.setIs24HourView(true); // Indicates if the format should be 24 hours
        dialog.setColorBackgroundHeader(R.color.colorPrimaryDark); // Set Color of Background header dialog
        dialog.setColorBackgroundTimePickerHeader(R.color.colorPrimary);
        dialog.setColorTextButton(R.color.colorPrimary); // Set Text color of button

        dialog.setInitialOpenedTab(RangeTimePickerDialog.InitialOpenedTab.START_CLOCK_TAB);

        dialog.setInitialStartClock(startHour, startMinute);
        dialog.setInitialEndClock(endHour, endMinute);
    }

    @Override
    public void onSelectedTime(int hourStart, int minuteStart, int hourEnd, int minuteEnd) {
        startHour = hourStart;
        startMinute = minuteStart;
        endHour = hourEnd;
        endMinute = minuteEnd;

        String startHourString = String.format(Locale.getDefault(), "%02d", startHour);
        String startMinString = String.format(Locale.getDefault(), "%02d", startMinute);
        String endHourString = String.format(Locale.getDefault(), "%02d", endHour);
        String endMinuteString = String.format(Locale.getDefault(), "%02d", endMinute);

        startET.setText(getString(R.string.time, startHourString, startMinString));
        endET.setText(getString(R.string.time, endHourString, endMinuteString));
    }
}
