package bnrg.app.criminalintent2frag.Utils;

import android.graphics.Color;
import android.view.View;

import bnrg.app.criminalintent2frag.Crime;

public class Utilities {

    public static void setItemColor(Crime mCrime, View itemView){
        if (mCrime.isSolved()) {
            itemView.setBackgroundColor(Color.parseColor("#FFDCEDC8"));
        } else {
            itemView.setBackgroundColor(Color.parseColor("#FFFCE4EC"));
        }
    }


}
