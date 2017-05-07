package pnj.ti.b2013.smartparent.view.canteen;

import android.os.Bundle;

import pnj.ti.b2013.smartparent.R;
import pnj.ti.b2013.smartparent.view.BaseActivity;

public class CanteenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canteen);

        initUI();
    }

    private void initUI() {
        setToolbar("Informasi Kantin",true);


    }

    @Override
    public void receive(int type, boolean success, Bundle extras) {

    }
}
