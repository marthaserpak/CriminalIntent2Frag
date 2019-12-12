package bnrg.app.criminalintent2frag.Activities;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import java.util.UUID;

import bnrg.app.criminalintent2frag.Fragments.CrimeFragment;
import bnrg.app.criminalintent2frag.Abstracts.SingleFragmentActivity;

import static bnrg.app.criminalintent2frag.Utils.Pref.EXTRA_CRIME_ID;


public class CrimeActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_CRIME_ID);

        return CrimeFragment.newInstance(crimeId);
    }
}
