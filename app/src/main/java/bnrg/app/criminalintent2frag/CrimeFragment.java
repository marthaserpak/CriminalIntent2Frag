package bnrg.app.criminalintent2frag;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.UUID;

import bnrg.app.criminalintent2frag.Preferences.Pref;

import static bnrg.app.criminalintent2frag.Preferences.Pref.ARG_CRIME_ID;
import static bnrg.app.criminalintent2frag.Preferences.Pref.START_POSITION;

public class CrimeFragment extends Fragment {

    private Crime mCrime;
    private List<Crime> mCrimes;


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

        UUID crimeId = (UUID) getArguments()
                .getSerializable(Pref.ARG_CRIME_ID);

        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_crime,
                container, false);

        TextView title = view.findViewById(R.id.tv_title);
        EditText enterTitle = view.findViewById(R.id.et_title);
        Button date = view.findViewById(R.id.tv_date);
        TextView details = view.findViewById(R.id.tv_details);
        CheckBox solved = view.findViewById(R.id.chb_solved);
        CheckBox requiresPolice = view.findViewById(R.id.chb_requires_police);
        Button firstCrime = view.findViewById(R.id.to_start_list);
        Button lastCrime = view.findViewById(R.id.to_end_list);
        Button save = view.findViewById(R.id.btn_save);

        solved.setChecked(mCrime.isSolved());
        requiresPolice.setChecked(mCrime.isRequiresPolice());

        title.setText(mCrime.getTitle());

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

        solved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

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

        //TODO: Util NullPointerException
        /*firstCrime.setOnClickListener(new View.OnClickListener() {
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
        });*/




        return view;
    }

}


