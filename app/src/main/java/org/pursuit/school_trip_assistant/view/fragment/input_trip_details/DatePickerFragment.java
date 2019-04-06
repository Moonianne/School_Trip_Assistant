package org.pursuit.school_trip_assistant.view.fragment.input_trip_details;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;


public final class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private static final String[] MONTH = {"Jan", "Feb", "Mar", "Apr", "May"
            , "Jun", "Jul", "Aug", "Sep", "Nov", "Dec"};
    private static final String[] DAY = {"Sun", "Mon", "Tues", "Wed",
            "Thurs", "Fri", "Sat"};
    private static final String SHARED_PREFS = "ASSISTANT";
    private static final String DATE_PREFS = "DATE";

    private final Calendar calendar;
    private OnDatePickListener listener;

    public DatePickerFragment() {
        calendar = Calendar.getInstance();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date now = new Date();
        calendar.setTime(now);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        String date = getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK))
                + getMonth(month) + ", " + day + ", " + year;
        setDate(date);
        listener.onDatePick();
    }

    private void setDate(String date) {
        if (getActivity() == null)
            throw new NullPointerException("Invoked Method on Null Activity.");
        getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
                .edit()
                .putString(DATE_PREFS, date)
                .apply();
    }

    private String getDayOfWeek(int i) {
        return DAY[i - 1];
    }

    private String getMonth(int i) {
        return MONTH[i - 1];
    }

    public void setOnDatePickListener(OnDatePickListener listener) {
        this.listener = listener;
    }

    interface OnDatePickListener {
        void onDatePick();
    }
}
