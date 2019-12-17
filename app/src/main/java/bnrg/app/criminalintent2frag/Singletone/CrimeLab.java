package bnrg.app.criminalintent2frag.Singletone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import bnrg.app.criminalintent2frag.Activities.Crime;
import bnrg.app.criminalintent2frag.database.CrimeBaseHelper;
import bnrg.app.criminalintent2frag.database.CrimeDbSchema;
import bnrg.app.criminalintent2frag.database.CrimeDbSchema.CrimeTable;

public class CrimeLab {

    private static CrimeLab sCrimeLab;
    /*private static List<Crime> mCrimes;*/
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext)
                .getWritableDatabase();
        /*mCrimes = new ArrayList<>();*/
       /* for (int i = 0; i<17; i++) {
            Crime crime = new Crime();
            crime.setTitle("crime # " + i);
            crime.setSolved(i % 2 == 0);
            crime.setRequiresPolice(i % 5 == 0);
            mCrimes.add(crime);
        }*/
    }

    public void addCrime(Crime c) {
        /*mCrimes.add(c);*/
        ContentValues values = getContentValues(c);

        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    public static void deleteCrime(UUID crimeId) {
        Crime crime = getCrime(crimeId);
        /*mCrimes.remove(crime);*/
    }

    public List<Crime> getCrimes() {
        /*return mCrimes;*/
        return new ArrayList<>();
    }

    public static Crime getCrime(UUID id) {
        /*for (Crime crime : mCrimes) {
            if(crime.getId().equals(id)){
             return crime;
            }
        }*/
        return null;
    }

    public void updateCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);

        mDatabase.update(CrimeTable.NAME, values,
                CrimeTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    private Cursor queryCrimes(String whereClause,
                               String[] whereArgs) {
        return mDatabase.query(
                CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.TIME, crime.getTime().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        values.put(CrimeTable.Cols.REQUIRES_POLICE,
                crime.isRequiresPolice() ? 1 : 0);
        return values;
    }
}
