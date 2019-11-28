package bnrg.app.criminalintent2frag;

import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import bnrg.app.criminalintent2frag.Preferences.Pref;

public class CrimeListActivity extends SingleFragmentActivity {

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
}
