package pnj.ti.b2013.smartparent.view.pickUpStudent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import pnj.ti.b2013.smartparent.R;
import pnj.ti.b2013.smartparent.model.Student;
import pnj.ti.b2013.smartparent.view.BaseActivity;

public class PickUpStudentActivity extends BaseActivity {

    Spinner spinner;
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
        phone = (EditText) findViewById(R.id.phoneNumber);
        submit = (Button) findViewById(R.id.submitBtn);
        spinner = (Spinner) findViewById(R.id.spinner);

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
                                        relation.getText().toString(),student.NIS,relation.getText().toString());
        }
    }

    @Override
    public void receive(int type, boolean success, Bundle extras) {

    }
}
