package pnj.ti.b2013.smartparent.view.canteen;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import pnj.ti.b2013.smartparent.R;
import pnj.ti.b2013.smartparent.model.Canteen;
import pnj.ti.b2013.smartparent.model.Student;
import pnj.ti.b2013.smartparent.service.VolleyTaskService;
import pnj.ti.b2013.smartparent.view.BaseActivity;

public class CanteenActivity extends BaseActivity {

    private static final String TAG = CanteenActivity.class.getSimpleName();
    private CanteenAdapter adapter;
    private Canteen canteen;
    private RecyclerView recyclerView;
    private RelativeLayout emptyView;
    private ArrayList<Canteen> canteenArrayList;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canteen);
        student = getIntent().getParcelableExtra("student");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                initUI();
            }
        }, 1500);

    }

    private void initUI() {
        setToolbar("Informasi Kantin",true);

        canteenArrayList = new ArrayList<>();
        adapter = new CanteenAdapter(getApplicationContext(),canteenArrayList);
        emptyView = (RelativeLayout) findViewById(R.id.emptyView);
        recyclerView = (RecyclerView) findViewById(R.id.recCanteen);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        emptyView.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.INVISIBLE);

        getData();

    }

    private void getData() {
        if (getTaskService() != null) {
            getTaskService().getTransaction(student.NIS);
        } else {
            Toast.makeText(this, "Check Your Connection", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void receive(int type, boolean success, Bundle extras) {
        if (success) {
            String data = extras.getString(VolleyTaskService.RESPONSE_DATA);
            switch (type) {
                case VolleyTaskService.REQ_TYPE_TRANSAKSI:
                    updateData(data);
                    Log.e(TAG, "Presence Data " + data);
                    break;
            }
        } else {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.failed))
                    .setMessage(extras.getString(VolleyTaskService.RESPONSE_MESSAGE))
                    .show();
        }
    }

    private void updateData(String data) {

        List<Canteen> canteenList = new Gson().fromJson(data, new TypeToken<List<Canteen>>() {
        }.getType());

        adapter.updateData(canteenList);

        this.canteenArrayList.clear();
        this.canteenArrayList.addAll(canteenList);
    }
}
