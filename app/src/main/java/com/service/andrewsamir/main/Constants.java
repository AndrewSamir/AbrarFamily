package com.service.andrewsamir.abrarfamily;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by andre on 01-May-17.
 */

public class Constants {

    public String firebaseRef = "fsol/" + FirebaseAuth.getInstance().getCurrentUser().getUid();

}
