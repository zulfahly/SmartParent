package pnj.ti.b2013.smartparent.view.student;

import android.os.Bundle;
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
import java.util.List;

import pnj.ti.b2013.smartparent.R;
import pnj.ti.b2013.smartparent.model.Student;
import pnj.ti.b2013.smartparent.service.VolleyTaskService;
import pnj.ti.b2013.smartparent.view.BaseActivity;

public class SelectStudentActivity extends BaseActivity {

    String TAG = SelectStudentActivity.class.getSimpleName();

    private String username;
    private SelectStudentAdapter studentAdapter;
    public Student student;
    private List<Student> arrayListstudent;
    private RecyclerView studentRecycler;
    private RelativeLayout emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_student);
        username = getIntent().getStringExtra("username");
        Log.e(TAG,"Extras: "+username);
        initUI();

    }

    private void initUI(){
        setToolbarUseImage(false);
        getStudentlist();

        studentAdapter = new SelectStudentAdapter(this, arrayListstudent);

//        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.sponsorSwipeRefresh);
        studentRecycler = (RecyclerView) findViewById(R.id.studentRecyclerView);
        emptyView = (RelativeLayout) findViewById(R.id.studentEmptyView);

//        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refreshSponsorList();
//            }
//        });

        studentRecycler.setLayoutManager(new LinearLayoutManager(this));
        studentRecycler.setAdapter(studentAdapter);

        emptyView.setVisibility(studentAdapter.getItemCount() == 0 ? View.VISIBLE : View.INVISIBLE);
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    private void getStudentlist()
    {
        if (getTaskService() != null) {
            getTaskService().studentList(username);
        } else {
            Toast.makeText(this, "TASK Service : " + getTaskService(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void receive(int type, boolean success, Bundle extras) {
        if (success) {
            String data = extras.getString(VolleyTaskService.RESPONSE_DATA);
            switch (type) {
                case VolleyTaskService.REQ_TYPE_STUDENT_LIST:
                    Toast.makeText(this,"Data student",Toast.LENGTH_LONG).show();
                    Log.e(TAG,"Student Data "+data);
                      Type studentList = new TypeToken<List<Student>>() {
                    }.getType();
                    arrayListstudent = new Gson().fromJson(data, studentList);
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
