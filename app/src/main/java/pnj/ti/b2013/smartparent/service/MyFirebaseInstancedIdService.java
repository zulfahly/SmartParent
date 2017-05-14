package pnj.ti.b2013.smartparent.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import pnj.ti.b2013.smartparent.util.Preferences;

/**
 * Created by Zulfahly on 3/8/2017.
 **/

public class MyFirebaseInstancedIdService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstancedIdService.class.getSimpleName();


    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);

        sendRegistrationToServer(refreshedToken);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
       // Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
       // registrationComplete.putExtra("token", refreshedToken);
       // LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token);
    }

    private void storeRegIdInPref(String token) {
        Preferences.getInstance(this).storeString("regId",token);
    }


}
