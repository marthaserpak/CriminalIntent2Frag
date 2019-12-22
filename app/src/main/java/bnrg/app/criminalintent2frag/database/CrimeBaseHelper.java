package bnrg.app.criminalintent2frag.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import bnrg.app.criminalintent2frag.database.CrimeDbSchema.CrimeTable;

import static bnrg.app.criminalintent2frag.Utils.Pref.DATABASE_NAME;
import static bnrg.app.criminalintent2frag.Utils.Pref.VERSION;

public class CrimeBaseHelper extends SQLiteOpenHelper {

    public CrimeBaseHelper(Context context){
        super(context, DATABASE_NAME,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CrimeTable.NAME +
                "(" + "_id integer primary key autoincrement, " +
                CrimeTable.Cols.UUID + ", " +
                CrimeTable.Cols.TITLE + ", " +
                CrimeTable.Cols.DATE + ", " +
                CrimeTable.Cols.SOLVED + ", " +
                CrimeTable.Cols.SUSPECT + "," +
                CrimeTable.Cols.REQUIRES_POLICE + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
