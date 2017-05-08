package pnj.ti.b2013.smartparent.view.profile;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pnj.ti.b2013.smartparent.R;
import pnj.ti.b2013.smartparent.model.Profile;
import pnj.ti.b2013.smartparent.model.Student;
import pnj.ti.b2013.smartparent.service.VolleyTaskService;
import pnj.ti.b2013.smartparent.util.Preferences;
import pnj.ti.b2013.smartparent.view.BaseActivity;
import pnj.ti.b2013.smartparent.view.MainActivity;

public class ChangePassword extends BaseActivity {

    Profile profile;
    Student student;
    Button submit;
    EditText oldPassword;
    EditText newPassword;
    EditText repeatPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        profile = Preferences.getInstance(this).getProfile();
        student = getIntent().getParcelableExtra("student");

        initUI();
    }

    private void initUI() {
        setToolbar("Ganti Sandi",true);

        submit = (Button) findViewById(R.id.btnSubmit);
        oldPassword = (EditText) findViewById(R.id.oldPassword);
        newPassword = (EditText) findViewById(R.id.newPassword);
        repeatPassword = (EditText) findViewById(R.id.repeatPassword);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });

    }

    private void changePassword() {
        if (getTaskService().isNetworkAvailable()){
            getTaskService().editPassword(profile.username,oldPassword.getText().toString(),newPassword.getText().toString());
        }
    }

    @Override
    public void receive(int type, boolean success, Bundle extras) {
        if (success) {
            String data = extras.getString(VolleyTaskService.RESPONSE_DATA);
            switch (type) {
                case VolleyTaskService.REQ_TYPE_EDIT_PASSWORD:
                    Preferences.getInstance(this).storeString(Preferences.PROFILE, extras.getString(VolleyTaskService.RESPONSE_DATA));
                    Toast.makeText(this,"Ubah sandi Sukses",Toast.LENGTH_LONG).show();
                    extras.putParcelable("student", student);
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtras(extras);
                    startActivity(intent);
                    break;
            }
        } else {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.failed))
                    .setMessage(extras.getString(VolleyTaskService.RESPONSE_MESSAGE))
                    .show();
        }

    }
}
