package pnj.ti.b2013.smartparent.view.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import pnj.ti.b2013.smartparent.R;
import pnj.ti.b2013.smartparent.model.Profile;
import pnj.ti.b2013.smartparent.model.Student;
import pnj.ti.b2013.smartparent.util.Preferences;
import pnj.ti.b2013.smartparent.view.BaseActivity;

public class ProfileActivity extends BaseActivity {

    private EditText parentName;
    private EditText studentName;
    private EditText phoneNumber;
    private EditText address;
    private Student student;
    private Profile profile;

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

        studentName.setText(student.nama_siswa);
        studentName.setEnabled(false);
        parentName.setText(profile.nama_orangtua);
        parentName.setEnabled(false);
        phoneNumber.setText(profile.nomor_telepon);
        address.setText(profile.alamat);

    }

    @Override
    public void receive(int type, boolean success, Bundle extras) {

    }
}
