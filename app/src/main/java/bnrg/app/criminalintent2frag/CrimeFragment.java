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
        Button firstCrime = view.findViewById(R.id.btn_to_first_crime);
        Button secondCrime = view.findViewById(R.id.btn_to_second_crime);

        solved.setChecked(mCrime.isSolved());
        requiresPolice.setChecked(mCrime.isRequiresPolice());

        title.setText(mCrime.getTitle());

        enterTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle("gggggg");
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

        return view;
    }

}


