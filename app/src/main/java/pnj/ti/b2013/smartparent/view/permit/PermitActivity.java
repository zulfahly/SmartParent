package pnj.ti.b2013.smartparent.view.permit;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import pnj.ti.b2013.smartparent.R;
import pnj.ti.b2013.smartparent.model.Student;
import pnj.ti.b2013.smartparent.service.VolleyTaskService;
import pnj.ti.b2013.smartparent.view.BaseActivity;
import pnj.ti.b2013.smartparent.view.MainActivity;

public class PermitActivity extends BaseActivity {

    private static final String CONSTANT_SAKIT = "S";
    private static final String CONSTANT_IZIN = "I";

    String TAG = PermitActivity.class.getSimpleName();

    EditText studentName;
    EditText className;
    EditText permitDetail;
    RadioGroup radioGroup;
    RadioButton rbIzin;
    RadioButton rbSakit;
    Button submit;

    String permitType;
    Student student;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permit);
        initUI();
    }

    private void initUI() {
        setToolbar("Perizinan",true);
        student = getIntent().getParcelableExtra("student");

        studentName = (EditText) findViewById(R.id.studentName);
        className = (EditText) findViewById(R.id.classOfStudent);
        permitDetail = (EditText) findViewById(R.id.permitDetail);
        radioGroup = (RadioGroup) findViewById(R.id.rgJenisIzin);
        rbIzin = (RadioButton) findViewById(R.id.rbIzin);
        rbSakit = (RadioButton) findViewById(R.id.rbSakit);
        submit = (Button) findViewById(R.id.submitBtn);


        studentName.setText(student.nama_siswa);
        className.setText(student.nama_kelas);
        studentName.setEnabled(false);
        className.setEnabled(false);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbIzin:
                        permitType = String.valueOf(CONSTANT_IZIN);
                        break;
                    case R.id.rbSakit:
                        permitType = String.valueOf(CONSTANT_SAKIT);
                        break;
                }

            }
        });
        rbSakit.setChecked(true);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postPermitData();
            }
        });

    }

    private void postPermitData() {
        if(validation()){
            if (getTaskService().isNetworkAvailable()){
                getTaskService().usePermit(studentName.getText().toString(),permitType,permitDetail.getText().toString());
            }else {
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.failed))
                        .setMessage(R.string.no_internet)
                        .show();
            }
        }

    }

    private boolean validation(){
        boolean valid = true;
        if(TextUtils.isEmpty(permitDetail.getText().toString())) {
            permitDetail.setError(getString(R.string.empty_form));
            permitDetail.requestFocus();
            valid = false;
        }
        return valid;
    }


    @Override
    public void receive(int type, boolean success, Bundle extras) {
        if (success) {
            String data = extras.getString(VolleyTaskService.RESPONSE_DATA);
            switch (type) {
                case VolleyTaskService.REQ_TYPE_ADD_IZIN:
                    Toast.makeText(getApplicationContext(),"SUCCESS",Toast.LENGTH_LONG).show();

                    extras = new Bundle();
                    extras.putParcelable("student",student);

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
