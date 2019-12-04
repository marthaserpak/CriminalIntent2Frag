package bnrg.app.criminalintent2frag;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.zip.Inflater;

import static bnrg.app.criminalintent2frag.Preferences.Pref.ARG_TIME;
import static bnrg.app.criminalintent2frag.Preferences.Pref.EXTRA_TIME;

public class TimePickerFragment extends DialogFragment {

    private TimePicker mTimePicker;
    //private Calendar mCalendar;

    Date time = (Date) getArguments().getSerializable(ARG_TIME);

    Calendar mCalendar = Calendar.getInstance();
    int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
    int minute = mCalendar.get(Calendar.MINUTE);

    public static TimePickerFragment newInstance(Date time) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, time);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_time, null);



        mTimePicker = (TimePicker) v.findViewById(R.id._dialog_time_picker);
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mTimePicker.getHour();
                mTimePicker.getMinute();
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.dialog_time_picker)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int hour = mTimePicker.getHour();
                        int minute = mTimePicker.getMinute();
                        sendResult(Activity.RESULT_OK, time);
                    }
                })
                .create();
    }

    private void sendResult(int resultCode, Date time) {
        if(getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, time);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(),resultCode, intent);
    }
}
