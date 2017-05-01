package pnj.ti.b2013.smartparent.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pnj.ti.b2013.smartparent.R;
import pnj.ti.b2013.smartparent.service.VolleyTaskService;
import pnj.ti.b2013.smartparent.util.Preferences;
import pnj.ti.b2013.smartparent.view.notification.NotificationActivity;
import pnj.ti.b2013.smartparent.view.pickUpStudent.PickUpStudentActivity;
import pnj.ti.b2013.smartparent.view.profile.ChangePassword;
import pnj.ti.b2013.smartparent.view.profile.ProfileActivity;
import pnj.ti.b2013.smartparent.view.student.SelectStudentActivity;

public class LoginActivity extends BaseActivity {
    final String TAG = LoginActivity.class.getSimpleName();


    EditText usernameField;
    EditText passwordField;
    Button loginButton;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(this);

        // wait for gcm
        progressDialog.setMessage(getString(R.string.play_service_waiting));
        progressDialog.setCancelable(false);
        progressDialog.show();

        initUI();

    }

    private void initUI() {
        // outlets
        usernameField = (EditText) findViewById(R.id.loginUsernameField);
        passwordField = (EditText) findViewById(R.id.loginPasswordField);
        loginButton = (Button) findViewById(R.id.loginButton);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
//                fcmToken = FirebaseInstanceId.getInstance().getToken();
                progressDialog.dismiss();
//                Log.e(TAG, "FCM TOKEN ON START " + fcmToken);
//                LocalBroadcastManager.getInstance(LoginActivity.this).registerReceiver(tokenReceiver, new IntentFilter("tokenReceiver"));
            }
        }, 3000);

        // listeners
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }


    private void login() {
        if (TextUtils.isEmpty(usernameField.getText().toString())) {
            usernameField.setError(getString(R.string.empty_username));
            usernameField.requestFocus();
            return;

        } else {
            usernameField.clearFocus();
        }

        if (TextUtils.isEmpty(passwordField.getText().toString())) {
            passwordField.setError(getString(R.string.empty_password));
            passwordField.requestFocus();
            return;

        } else {
            passwordField.clearFocus();
        }
        if (getTaskService() != null) {
            progressDialog.setMessage(getString(R.string.login_loading));
            progressDialog.show();
            Log.e(TAG, "FCM " + usernameField.getText().toString() + " " + passwordField.getText().toString());
            getTaskService().login(usernameField.getText().toString(), passwordField.getText().toString());
        } else {
            Toast.makeText(this, "TASK Service : " + getTaskService(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void receive(int type, boolean success, Bundle extras) {
       Log.e(TAG,"On Receive");
        if (success) {
            switch (type) {
                case VolleyTaskService.REQ_TYPE_LOGIN:
                    progressDialog.dismiss();
                    Preferences.getInstance(this).storeString(Preferences.PROFILE, extras.getString(VolleyTaskService.RESPONSE_DATA));

                    Intent intent = new Intent(this, SelectStudentActivity.class);
                    extras.putString("username",usernameField.getText().toString());

//                    if (getIntent() != null && getIntent().getExtras() != null) {
//                        intent.putExtras(getIntent().getExtras());
//                    }
                    intent.putExtras(extras);
                    Log.e(TAG,"Extras : login "+extras);
                    startActivity(intent);
                    finish();

                    break;

//                case VolleyTaskService.REQ_TYPE_FORGOT_PASSWORD:
//                    new AlertDialog.Builder(this)
//                            .setTitle(getString(R.string.success))
//                            .setMessage(extras.getString(VolleyTaskService.RESPONSE_MESSAGE))
//                            .show();
//                    break;
            }
        } else {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.failed))
                    .setMessage(extras.getString(VolleyTaskService.RESPONSE_MESSAGE))
                    .show();
        }
    }

//    private void forgotPassword() {
//        View forgotPassword = getLayoutInflater().inflate(R.layout.popup_forgot_password, null);
//        alertDialog = new AlertDialog.Builder(this).setView(forgotPassword).show();
//
//        final EditText usernameField = (EditText) forgotPassword.findViewById(R.id.forgotUsernameField);
//        Button resetButton = (Button) forgotPassword.findViewById(R.id.forgotResetButton);
//
//        resetButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//
//                progressDialog.setMessage(getString(R.string.loading));
//                progressDialog.setCancelable(false);
//                progressDialog.show();
//
//                // TODO: forgot password
//                // getTaskService().performForgotPass(usernameField.getText().toString());
//            }
//        });
// }



}
