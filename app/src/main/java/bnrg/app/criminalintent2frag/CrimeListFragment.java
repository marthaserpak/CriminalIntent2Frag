package bnrg.app.criminalintent2frag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CrimeListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Crime> mCrimes;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        createAdapter();

        return view;
    }

    private void createAdapter() {

        CrimeLab crimeLab = CrimeLab.get(getActivity());
        mCrimes = crimeLab.getCrimes();
        mAdapter = new CrimeAdapter(mCrimes);
        mRecyclerView.setAdapter(mAdapter);

    }


    public class CrimeHolder extends RecyclerView.ViewHolder {

        private Crime mCrime;
        private TextView title;
        private TextView date;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));

            title = itemView.findViewById(R.id.tv_title);
            date = itemView.findViewById(R.id.tv_date);
        }

        private void bind(Crime crime) {
            mCrime = crime;
            title.setText(crime.getTitle());
            date.setText(crime.getDate().toString());
        }
    }

    public class PoliceHolder extends RecyclerView.ViewHolder {

        private Crime mCrime;
        private TextView title;
        private TextView date;
        private Button police;

        public PoliceHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime_police, parent, false));

            title = itemView.findViewById(R.id.tv_title_police);
            date = itemView.findViewById(R.id.tv_date_police);
            police = itemView.findViewById(R.id.bt_call_police);
        }

        private void bind(Crime crime) {
            mCrime = crime;
            title.setText(crime.getTitle());
            date.setText(crime.getDate().toString());
            police.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Calling police..", Toast.LENGTH_LONG);
                }
            });
        }
    }


    private class CrimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final int NOT_CALL_POLICE = 0;
        private final int CALL_POLICE = 1;

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public int getItemViewType(int position) {

            Crime crime = mCrimes.get(position);

            int viewType = NOT_CALL_POLICE;
            if (crime.isRequiresPolice()) {
                viewType = CALL_POLICE;
            }
            return viewType;
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            if (viewType == CALL_POLICE) {
                return new PoliceHolder(inflater, parent);
            } else {
                return new CrimeHolder(inflater, parent);
            }

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            Crime crime = mCrimes.get(position);
            if (holder.getItemViewType() == CALL_POLICE) {
                PoliceHolder policeHolder = (PoliceHolder) holder;
                policeHolder.bind(crime);
            } else {
                CrimeHolder crimeHolder = (CrimeHolder) holder;
                crimeHolder.bind(crime);

            }
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
