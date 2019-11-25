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

public class CrimeFragment extends Fragment {

    private Crime mCrime;
    private TextView mTitle, mDetails;
    private EditText mEnterTitle;
    private Button mDate;
    private CheckBox mSolved;
    private CheckBox mRequiresPolice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCrime = new Crime();

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_crime,
                container, false);

        mTitle = view.findViewById(R.id.tv_title);
        mEnterTitle = view.findViewById(R.id.et_title);
        mDate = view.findViewById(R.id.tv_date);
        mDetails = view.findViewById(R.id.tv_details);
        mSolved = view.findViewById(R.id.chb_solved);
        mRequiresPolice = view.findViewById(R.id.chb_requires_police);

        mEnterTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSolved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
