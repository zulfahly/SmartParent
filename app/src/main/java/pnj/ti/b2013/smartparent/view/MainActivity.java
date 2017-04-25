package pnj.ti.b2013.smartparent.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pnj.ti.b2013.smartparent.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void receive(int type, boolean success, Bundle extras) {

    }
}
