package bnrg.app.criminalintent2frag;

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

import java.util.List;
import java.util.UUID;

import bnrg.app.criminalintent2frag.Preferences.Pref;

import static bnrg.app.criminalintent2frag.Preferences.Pref.ARG_CRIME_ID;

public class CrimeFragment extends Fragment {

    private Crime mCrime;

    private TextView mTitle, mDetails;
    private EditText mEnterTitle;
    private Button mDate;
    private CheckBox mRequiresPolice;


    public static CrimeFragment newInstance(UUID crimeId) {

        Bundle args = new Bundle();
        args.putSerializable(Pref.ARG_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID crimeId = (UUID) getArguments().getSerializable(Pref.ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_crime,
                container, false);

        mTitle = view.findViewById(R.id.tv_title);

        mEnterTitle = view.findViewById(R.id.et_title);
        mDate = view.findViewById(R.id.tv_date);
        mDetails = view.findViewById(R.id.tv_details);
        CheckBox solved = view.findViewById(R.id.chb_solved);
        solved.setChecked(mCrime.isSolved());
        mRequiresPolice = view.findViewById(R.id.chb_requires_police);
        mRequiresPolice.setChecked(mCrime.isRequiresPolice());

        mTitle.setText(mCrime.getTitle());

        mEnterTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle("gggggggggggggggggggggggg");
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

        mRequiresPolice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setRequiresPolice(isChecked);
            }
        });

        return view;
    }




}


