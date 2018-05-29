package com.knacky.events.presentation.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import com.knacky.events.presentation.MainActivity;

import java.util.Calendar;

/**
 * Created by knacky on 28.05.2018.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    static final int START_DATE = 1;
    static final int END_DATE = 2;
    static final int CREATE_DATE = 3;

    DateSelectedListener dateSelectedListener;
    CreateEventFragment createEventFragment;

    private int mChosenDate;

    int cur = 0;

    public interface DateSelectedListener{
        void onCreateEventDateSelected();
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        createEventFragment = new CreateEventFragment();


//        dateSelectedListener = (CreateEventFragment) this;

        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        Log.v("DatePicker", "onCreateDialog(), activity: " + getActivity().toString());
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mChosenDate = bundle.getInt("DATE", 1);
        }

        Log.i("DatePicker", "onCreateDialog, cur: " + String.valueOf(cur));

        switch (mChosenDate) {

            case START_DATE:
                cur = START_DATE;
                return new DatePickerDialog(getActivity(), this::onDateSet, year, month, day);

            case END_DATE:
                cur = END_DATE;
                return new DatePickerDialog(getActivity(), this::onDateSet, year, month, day);

            case CREATE_DATE:
                cur = CREATE_DATE;
                return new DatePickerDialog(getActivity(), createEventFragment.onDateSetListener, year, month, day);
//                return new DatePickerDialog(getActivity(), this::onDateSet, year, month, day);

        }

        return null;
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Log.i("DatePicker", "onDataSet, cur: " + String.valueOf(cur));

        if (cur == START_DATE) {
            // set selected date into textview
            Log.i("DatePicker", "Date Start : " + new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));
        } else if (cur == END_DATE) {
            Log.i("DatePicker", "Date End : " + new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));
        } else if(cur == CREATE_DATE){

/////////////////////////////////////////////////////
            Log.i("DatePicker", "Date Create : " + new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));
        }
    }
/////////////////////////////////////////////////////
}