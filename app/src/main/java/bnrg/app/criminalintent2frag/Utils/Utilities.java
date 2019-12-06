package bnrg.app.criminalintent2frag.Utils;

import android.graphics.Color;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import bnrg.app.criminalintent2frag.Crime;

public class Utilities {

    public static void setItemColor(Crime mCrime, View itemView){
        if (mCrime.isSolved()) {
            itemView.setBackgroundColor(Color.parseColor("#FFDCEDC8"));
        } else {
            itemView.setBackgroundColor(Color.parseColor("#FFFCE4EC"));
        }
    }

    public static String getFormattedDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat( "EEE , MMM d, ''yy",
                Locale.getDefault());

        return formatter.format(date);
    }

    public static String getFormattedTime(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss a",
                Locale.getDefault());

        return  formatter.format(date);
    }
}
