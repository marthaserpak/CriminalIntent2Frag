package bnrg.app.criminalintent2frag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TimeFormatException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import bnrg.app.criminalintent2frag.Preferences.Pref;
import bnrg.app.criminalintent2frag.Utils.Utilities;

import static bnrg.app.criminalintent2frag.Preferences.Pref.DIALOG_DATE;
import static bnrg.app.criminalintent2frag.Preferences.Pref.DIALOG_TIME;
import static bnrg.app.criminalintent2frag.Preferences.Pref.EXTRA_TIME;
import static bnrg.app.criminalintent2frag.Preferences.Pref.REQUEST_DATE;
import static bnrg.app.criminalintent2frag.Preferences.Pref.EXTRA_DATE;
import static bnrg.app.criminalintent2frag.Preferences.Pref.REQUEST_TIME;

public class CrimeFragment extends Fragment {

    private Crime mCrime;
    private List<Crime> mCrimes;
    private Button mDateButton;
    private Button mTimeButton;

    static CrimeFragment newInstance(UUID crimeId) {

        Bundle args = new Bundle();
        args.putSerializable(Pref.ARG_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        assert getArguments() != null;
        UUID crimeId = (UUID) getArguments()
                .getSerializable(Pref.ARG_CRIME_ID);

        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.delete_crime_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_crime) {
            CrimeLab crimeLab = CrimeLab.get(getActivity());
            crimeLab.deleteCrime(mCrime.getId());
            Intent intent = new Intent(getActivity(), CrimeListActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime,
                container, false);

        //EditText enterDescription = view.findViewById(R.id.et_details_description);
        EditText enterTitle = view.findViewById(R.id.et_title);
        CheckBox solved = view.findViewById(R.id.chb_solved);
        CheckBox requiresPolice = view.findViewById(R.id.chb_requires_police);
        Button firstCrime = view.findViewById(R.id.to_start_list);
        Button lastCrime = view.findViewById(R.id.to_end_list);
        Button save = view.findViewById(R.id.btn_save);
        mDateButton = view.findViewById(R.id.tv_date);
        mTimeButton = view.findViewById(R.id.btn_time);

        mDateButton.setText(Utilities.getFormattedDate(mCrime.getDate()));
        mTimeButton.setText(Utilities.getFormattedTime(mCrime.getDate()));
        enterTitle.setText(mCrime.getTitle());

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this,
                        REQUEST_DATE);
                assert manager != null;
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*           2.Добавляем экз DialogFrag во фрагмент   */
                FragmentManager manager = getFragmentManager();
                /*3.Созд конст для метки TimePickerFrag и
                    метод show(), который отображает диал. окно*/
                TimePickerFragment dialog = TimePickerFragment
                        .newInstanceTime(mCrime.getDate()); /*6.Меняем вызов констр на метод NewIns()*/
                /*10.Для создания связи между 2мя фрагментами устанавлив метод
                getTargetFragment(Fragment fragment, int requestCode) - метод получает целевой фраг
                              и код запроса; Создаем константу REQUEST_TIME
                 и назначаем CrimeFrag целев фрагментом экземплляра TimePickerFrag.*/
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
                assert manager != null;
                dialog.show(manager, DIALOG_TIME);
            }
        });

        enterTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        solved.setChecked(mCrime.isSolved());
        solved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        requiresPolice.setChecked(mCrime.isRequiresPolice());
        requiresPolice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setRequiresPolice(isChecked);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = CrimeListFragment.mPos;
                Intent intent = CrimeListActivity.newIntent(getActivity(),
                        mCrime.isRequiresPolice(),
                        mCrime.isSolved(),
                        position);
                startActivity(intent);
            }
        });

        firstCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int START_POSITION = 0;
                CrimePagerActivity.setViewPagerStart(START_POSITION);
            }
        });

        lastCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrimePagerActivity.setViewPagerStart(mCrimes.size());
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case REQUEST_DATE:
                if (resultCode != Activity.RESULT_OK) {
                    return;
                }
                Date date = (Date) data
                        .getSerializableExtra(EXTRA_DATE);
                mCrime.setDate(date);
                mDateButton.setText(Utilities.getFormattedDate(date));

            case REQUEST_TIME:
                if (resultCode != Activity.RESULT_OK) {
                    return;
                }
                if (requestCode == REQUEST_TIME) {
                    Date mDate = (Date) data
                            .getSerializableExtra(EXTRA_TIME);
                    mCrime.setTime(mDate);
                    mTimeButton.setText(Utilities.getFormattedTime(mDate));
                }
        }
    }
}


