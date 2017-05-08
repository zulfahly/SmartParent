package pnj.ti.b2013.smartparent.view.profile;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import pnj.ti.b2013.smartparent.R;
import pnj.ti.b2013.smartparent.model.Profile;
import pnj.ti.b2013.smartparent.model.Student;
import pnj.ti.b2013.smartparent.service.VolleyTaskService;
import pnj.ti.b2013.smartparent.util.Preferences;
import pnj.ti.b2013.smartparent.view.BaseActivity;
import pnj.ti.b2013.smartparent.view.MainActivity;
import pnj.ti.b2013.smartparent.view.canteen.CanteenActivity;

public class ProfileActivity extends BaseActivity {

    private EditText parentName;
    private EditText studentName;
    private EditText phoneNumber;
    private EditText address;
    private Student student;
    private Profile profile;
    private Button save, changePassword, logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        student = getIntent().getParcelableExtra("student");
        profile = Preferences.getInstance(this).getProfile();

        initUI();
    }

    private void initUI() {
        setToolbar("Pengaturan Akun",true);

        parentName = (EditText) findViewById(R.id.parentName);
        studentName = (EditText) findViewById(R.id.studentName);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        address = (EditText) findViewById(R.id.address);
        save = (Button) findViewById(R.id.saveProfile);
        logout = (Button) findViewById(R.id.btnLogout);
        changePassword = (Button) findViewById(R.id.changePassword);

        studentName.setText(student.nama_siswa);
        studentName.setEnabled(false);
        parentName.setText(profile.nama_orangtua);
        parentName.setEnabled(false);
        phoneNumber.setText(profile.nomor_telepon);
        address.setText(profile.alamat);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProfile();
            }


        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = new Bundle();
                extras.putParcelable("student", student);

                Intent intent = new Intent(ProfileActivity.this, ChangePassword.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void saveProfile(){
        if (getTaskService().isNetworkAvailable()){
            getTaskService().editProfile(profile.username,address.getText().toString(),phoneNumber.getText().toString());
        }

    }

    @Override
    public void receive(int type, boolean success, Bundle extras) {
        if (success) {
            String data = extras.getString(VolleyTaskService.RESPONSE_DATA);
            switch (type) {
                case VolleyTaskService.REQ_TYPE_EDIT_PROFILE:
                    Preferences.getInstance(this).storeString(Preferences.PROFILE, extras.getString(VolleyTaskService.RESPONSE_DATA));
                    Toast.makeText(this,"Edit Profil Sukses",Toast.LENGTH_LONG).show();
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
