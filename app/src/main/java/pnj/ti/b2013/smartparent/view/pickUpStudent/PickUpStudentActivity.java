package pnj.ti.b2013.smartparent.view.pickUpStudent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pnj.ti.b2013.smartparent.R;
import pnj.ti.b2013.smartparent.model.Student;
import pnj.ti.b2013.smartparent.service.VolleyTaskService;
import pnj.ti.b2013.smartparent.view.BaseActivity;
import pnj.ti.b2013.smartparent.view.MainActivity;

public class PickUpStudentActivity extends BaseActivity {

    private static final String TAG = PickUpStudentActivity.class.getSimpleName() ;
    EditText namaPenjemput;
    EditText ktp;
    EditText relation;
    EditText phone;
    Button submit;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        student = getIntent().getParcelableExtra("student");

        setContentView(R.layout.activity_pick_up_student);
        initUI();
    }

    private void initUI() {
        setToolbar("Penjemputan",true);

        namaPenjemput = (EditText) findViewById(R.id.Name);
        ktp = (EditText) findViewById(R.id.ktpId);
        relation = (EditText) findViewById(R.id.relation);
        phone = (EditText) findViewById(R.id.telepon);
        submit = (Button) findViewById(R.id.submitBtn);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPickUp();
            }
        });

    }

    private void setPickUp() {
        if (getTaskService().isNetworkAvailable()){
            getTaskService().addPickup(namaPenjemput.getText().toString(),ktp.getText().toString(),
                                        phone.getText().toString(),student.NIS,relation.getText().toString());
        }
    }

    @Override
    public void receive(int type, boolean success, Bundle extras) {
        if (success) {
            switch (type) {
                case VolleyTaskService.REQ_TYPE_SEND_PICKUP:
                    extras = new Bundle();
                    extras.putParcelable("student", student);

                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtras(extras);
                    Toast.makeText(this,"Pengiriman data penjemput sukses",Toast.LENGTH_LONG).show();
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
