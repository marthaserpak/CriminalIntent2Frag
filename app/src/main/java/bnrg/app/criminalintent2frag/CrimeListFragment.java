package bnrg.app.criminalintent2frag;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import bnrg.app.criminalintent2frag.Utils.Utilities;

import static bnrg.app.criminalintent2frag.Preferences.Pref.CALL_POLICE;
import static bnrg.app.criminalintent2frag.Preferences.Pref.NOT_CALL_POLICE;
import static bnrg.app.criminalintent2frag.Preferences.Pref.SAVED_SUBTITLE_VISIBLE;
import static java.time.format.FormatStyle.MEDIUM;


public class CrimeListFragment extends Fragment {

    private ViewPager mViewPager;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Crime> mCrimes;
    public static int mPos;
    private boolean mSubtitleVisible;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState
                    .getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(createAdapter());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_crime:
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                Intent intent = CrimePagerActivity
                        .newIntent(getActivity(), crime.getId());
                startActivity(intent);
                return true;

            case R.id.show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        String subtitle = getString(R.string.subtitle_format, crimeCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // сообщаем ФМ что он должен получать обратные вызовы меню
        setHasOptionsMenu(true);
    }

    private RecyclerView.Adapter createAdapter() {

        CrimeLab crimeLab = CrimeLab.get(getActivity());
        mCrimes = crimeLab.getCrimes();
        mAdapter = new CrimeAdapter(mCrimes);
        mRecyclerView.setAdapter(mAdapter);
        return mAdapter;
    }

    private void updateUI() {

        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();

        if (mAdapter != null) {
            //TODO:mAdapter.notifyItemChanged(pos);
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter = new CrimeAdapter(mCrimes);
        }

        updateSubtitle();
    }

    public class CrimeHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Crime mCrime;
        private TextView title;
        private TextView mDate;
        private ImageView mSolved;
        private Button mToStart;
        private Button mToEnd;


        CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));

            itemView.setOnClickListener(this);

            title = itemView.findViewById(R.id.tv_title);

            mDate = itemView.findViewById(R.id.tv_date);
            mSolved = itemView.findViewById(R.id.crime_solved);
            mToStart = itemView.findViewById(R.id.to_start);
            mToEnd = itemView.findViewById(R.id.to_end);
        }

        private void bind(final Crime crime) {
            mCrime = crime;

            Utilities.setItemColor(mCrime, itemView);

            title.setText(crime.getTitle());

            SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, ''yy" );
            mDate.setText(format.format(crime.getDate()));

            mSolved.setVisibility(crime.isSolved() ?
                    View.VISIBLE : View.GONE);

            mToStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRecyclerView.smoothScrollToPosition(0);
                }
            });

            mToEnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRecyclerView.smoothScrollToPosition(mCrimes.size());
                }
            });
        }

        @Override
        public void onClick(View v) {
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
        }
    }

    public class PoliceHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        private Crime mCrime;
        private TextView title;
        private TextView date;
        private Button police;
        private ImageView mSolvedImageView;
        private Button mToStartPolice;
        private Button mToEndPolice;


        PoliceHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime_police, parent, false));

            itemView.setOnClickListener(this);

            title = itemView.findViewById(R.id.tv_title_police);
            date = itemView.findViewById(R.id.tv_date_police);
            police = itemView.findViewById(R.id.bt_call_police);
            mSolvedImageView = itemView.findViewById(R.id.crime_solved_police);
            mToStartPolice = itemView.findViewById(R.id.to_start_police);
            mToEndPolice = itemView.findViewById(R.id.to_end_police);

        }

        private void bind(final Crime crime) {

            mCrime = crime;

            Utilities.setItemColor(mCrime, itemView);

            title.setText(crime.getTitle());

            SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, ''yy" );
            date.setText(format.format(crime.getDate()));

            police.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(police.getContext(),
                            crime.getId().toString(),
                            Toast.LENGTH_SHORT)
                            .show();
                }
            });

            mSolvedImageView.setVisibility(mCrime.isSolved() ?
                    View.VISIBLE : View.GONE);

            mToEndPolice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRecyclerView.smoothScrollToPosition(mCrimes.size());
                }
            });

            mToStartPolice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRecyclerView.smoothScrollToPosition(0);
                }
            });
        }

        @Override
        public void onClick(View v) {
            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
            /*CrimeAdapter.mPos = getAdapterPosition();
            Intent intent = CrimePagerActivity.newIntent(this.itemView.getContext(),
                    this.mToEndPolice.getContext());*/
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<Crime> mCrimes;
        public int mPos;

        CrimeAdapter(List<Crime> crimes) {
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

        @NonNull
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

            mPos = position;

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
