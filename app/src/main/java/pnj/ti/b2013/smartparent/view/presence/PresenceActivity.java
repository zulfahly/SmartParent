package pnj.ti.b2013.smartparent.view.presence;

import android.app.ProgressDialog;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pnj.ti.b2013.smartparent.R;
import pnj.ti.b2013.smartparent.model.Presense;
import pnj.ti.b2013.smartparent.model.Student;
import pnj.ti.b2013.smartparent.service.VolleyTaskService;
import pnj.ti.b2013.smartparent.view.BaseActivity;

public class PresenceActivity extends BaseActivity {

    String TAG = PresenceActivity.class.getSimpleName();

    private PresenceAdapter adapter;
    private Student student;
    private RecyclerView presenceRecycler;
    private RelativeLayout emptyView;
    private ProgressDialog pDialog;
    private ArrayList<Presense> arrayListPresenses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presence);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.downloading));
        pDialog.setCancelable(false);
        pDialog.show();
        student = getIntent().getParcelableExtra("student");
        Log.e(TAG,"extras Student"+student.NIS);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                initUI();
            }
        }, 1500);
    }

    private void initUI() {
        setToolbar("Presensi", true);
        pDialog.dismiss();
        arrayListPresenses = new ArrayList<>();
        adapter = new PresenceAdapter(getApplicationContext(),arrayListPresenses);

        presenceRecycler = (RecyclerView) findViewById(R.id.presenceRecyclerView);
        emptyView = (RelativeLayout) findViewById(R.id.presenceEmptyView);


        presenceRecycler.setLayoutManager(new LinearLayoutManager(this));
        presenceRecycler.setAdapter(adapter);


        emptyView.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.INVISIBLE);
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        getPresencelist();

    }

    private void getPresencelist() {
        if (getTaskService() != null) {
            getTaskService().studentPrecenseList(student.NIS);
        } else {
            Toast.makeText(this, "Check Your Connection", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void receive(int type, boolean success, Bundle extras) {
        if (success) {
            String data = extras.getString(VolleyTaskService.RESPONSE_DATA);
            switch (type) {
                case VolleyTaskService.REQ_TYPE_STUDENT_PRESENCE:
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
        List<Presense> presenses = new Gson().fromJson(data, new TypeToken<List<Presense>>() {
        }.getType());

        adapter.updateData(presenses);

        this.arrayListPresenses.clear();
        this.arrayListPresenses.addAll(presenses);
    }


}
