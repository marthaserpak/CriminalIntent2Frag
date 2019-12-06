package bnrg.app.criminalintent2frag;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

import static bnrg.app.criminalintent2frag.Preferences.Pref.ARG_TIME;
import static bnrg.app.criminalintent2frag.Preferences.Pref.EXTRA_TIME;

public class TimePickerFragment extends DialogFragment {

    //5.Чтобы передать время преступл. создаем метод NewInstance()
    static TimePickerFragment newInstanceTime(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, date);
        TimePickerFragment timeFragment = new TimePickerFragment();
        timeFragment.setArguments(args);
        return timeFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //7.Извлечение времени из аргуметов
        assert getArguments() != null;
        final Date date = (Date) getArguments().getSerializable(ARG_TIME);

        //8.Работа с календарем
        Date mDate = new Date();
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTime(mDate);
        final int hour = mCalendar.get(Calendar.HOUR);
        final int minute = mCalendar.get(Calendar.MINUTE);

        //4.1 Заполняем представление
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_time, null);

        //9.Находим виджет и назнач слушателя
        TimePicker timePicker = v.findViewById(R.id.dialog_time_picker);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mCalendar.set(Calendar.MINUTE, minute);
            }
        });

        //1.Создали само диалоговое окно, которое выводит текст и кнопку ОК
        return new AlertDialog.Builder(getActivity())
                .setView(v) //4.2 Включаем видж TimePick. в диалоговое окно
                //                                       при помощи метода setView()
                .setTitle(R.string.dialog_time_picker)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /* 12.Теперь использ новый метод sendResult(...).
                    Когда пользователь нажимает кнопку положительного ответа в диалоговом окне,
                         приложение должно получить дату из DatePicker
                         и отправить результат CrimeFragment . */
                        Date date = mCalendar.getTime();
                        sendResult(Activity.RESULT_OK, date);
                    }
                })
                .create();
    }
    /*11. Создаем закрытый метод, который создает интент,
          помещает в него дату как дополнение, а затем вызывает
            CrimeFragment.onActivityResult*/
    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, date);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

}