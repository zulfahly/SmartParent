package pnj.ti.b2013.smartparent.view.pickUpStudent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pnj.ti.b2013.smartparent.R;
import pnj.ti.b2013.smartparent.view.BaseActivity;

public class PickUpStudentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up_student);
        initUI();
    }

    private void initUI() {
        setToolbar("Penjemputan",true);

    }

    @Override
    public void receive(int type, boolean success, Bundle extras) {

    }
}
