package bnrg.app.criminalintent2frag.Singletone;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import bnrg.app.criminalintent2frag.Activities.Crime;
import bnrg.app.criminalintent2frag.database.CrimeBaseHelper;

public class CrimeLab {

    private static CrimeLab sCrimeLab;
    private static List<Crime> mCrimes;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context){
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext)
                .getWritableDatabase();
        mCrimes = new ArrayList<>();
       /* for (int i = 0; i<17; i++) {
            Crime crime = new Crime();
            crime.setTitle("crime # " + i);
            crime.setSolved(i % 2 == 0);
            crime.setRequiresPolice(i % 5 == 0);
            mCrimes.add(crime);
        }*/
    }

    public void addCrime(Crime c) {
        mCrimes.add(c);
    }

    public static void deleteCrime(UUID crimeId){
        Crime crime = getCrime(crimeId);
        mCrimes.remove(crime);
    }

    public List<Crime> getCrimes(){
        return mCrimes;
    }

    public static Crime getCrime(UUID id){
        for (Crime crime : mCrimes) {
            if(crime.getId().equals(id)){
             return crime;
            }
        }
        return null;
    }

    public void updateCrime(Crime crime) {
    }
}
