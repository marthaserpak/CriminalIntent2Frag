package bnrg.app.criminalintent2frag.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import bnrg.app.criminalintent2frag.Activities.Crime;
import bnrg.app.criminalintent2frag.database.CrimeDbSchema.CrimeTable;

public class CrimeCursorWrapper extends CursorWrapper {

    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        //long time = getLong(getColumnIndex(CrimeTable.Cols.TIME));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));
        int isRequiresPolice = getInt(getColumnIndex(
                CrimeTable.Cols.REQUIRES_POLICE));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
       // crime.setTime(new Date(time));
        crime.setRequiresPolice(isRequiresPolice != 0);
        crime.setSolved(isSolved != 0);

        return crime;
    }
}
