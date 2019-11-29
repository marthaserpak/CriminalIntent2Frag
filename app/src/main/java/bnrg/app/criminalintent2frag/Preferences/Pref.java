package bnrg.app.criminalintent2frag.Preferences;

public interface Pref {

    //Flags
    int NOT_CALL_POLICE = 0;
    int CALL_POLICE = 1;
    int START_POSITION = 0;
    int REQUEST_DATE = 0;

    //EXTRA
    String EXTRA_CRIME_ID = "extra_crime_id";
    String EXTRA_REQUIRED_POLICE = "extra_required_police";
    String EXTRA_IS_SOLVED = "extra_is_solved";
    String EXTRA_RW_POSITION = "extra_rw_position";
    String DIALOG_DATE = "DialogDate";
    String EXTRA_DATE = "extra_date";

    //ARGS
    String ARG_CRIME_ID = "crime_id";
    String ARG_DATE = "date";

}
