package edu.grinnell.appdev.pradhanp.grinstagram;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by prabir on 5/4/15.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "uPFiftnojDDtpMGNFidaUnr9dUmGCpGbwIq7nV4n", "D7YCB3cVFJmMHgsxlX2G9ZOmPhiqdNGQ4Ix2VMnw");
    }

}
