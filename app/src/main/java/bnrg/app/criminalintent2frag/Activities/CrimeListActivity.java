package bnrg.app.criminalintent2frag.Activities;

import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import bnrg.app.criminalintent2frag.Fragments.CrimeFragment;
import bnrg.app.criminalintent2frag.Fragments.CrimeListFragment;
import bnrg.app.criminalintent2frag.Abstracts.SingleFragmentActivity;
import bnrg.app.criminalintent2frag.Interface.Callbacks;
import bnrg.app.criminalintent2frag.R;
import bnrg.app.criminalintent2frag.Utils.Pref;

public class CrimeListActivity extends SingleFragmentActivity implements Callbacks {

    public static Intent newIntent(FragmentActivity activity,
                                   boolean requiresPolice,
                                   boolean isSolved,
                                   int position) {
        Intent intent = new Intent(activity, CrimeListActivity.class);
        intent.putExtra(Pref.EXTRA_REQUIRED_POLICE, requiresPolice);
        intent.putExtra(Pref.EXTRA_IS_SOLVED, isSolved);
        intent.putExtra(Pref.EXTRA_RW_POSITION, position);

        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    protected int getLayoutResId(){
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onCrimeSelected(Crime crime) {
        if(findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = CrimePagerActivity.newIntent(this,
                    crime.getId());
        } else {
            Fragment newDetail = CrimeFragment.newInstance(crime.getId());

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }
}
