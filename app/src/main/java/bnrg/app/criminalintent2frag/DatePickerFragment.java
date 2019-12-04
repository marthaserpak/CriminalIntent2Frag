package bnrg.app.criminalintent2frag;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


import bnrg.app.criminalintent2frag.Preferences.Pref;

import static bnrg.app.criminalintent2frag.Preferences.Pref.ARG_DATE;
import static bnrg.app.criminalintent2frag.Preferences.Pref.EXTRA_DATE;

public class DatePickerFragment extends DialogFragment {

    static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        assert getArguments() != null;
        Date date = (Date) getArguments().getSerializable(Pref.ARG_DATE);

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);

        final DatePicker datePicker = v.findViewById(R.id.dialog_date_picker);
        datePicker.init(year, month, day, null);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int year = datePicker.getYear();
                                int month = datePicker.getMonth();
                                int day = datePicker.getDayOfMonth();
                                Date date = new GregorianCalendar(year,
                                        month,
                                        day).getTime();
                                sendResult(Activity.RESULT_OK,date);
                            }
                        })
                .create();


    }

    private void sendResult(int resultCode, Date date) {

        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        getTargetFragment().onActivityResult(getTargetRequestCode(),
                resultCode, intent);
    }
}
