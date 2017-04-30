package pnj.ti.b2013.smartparent.view.student;

import android.app.ProgressDialog;
import android.content.Intent;
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
import pnj.ti.b2013.smartparent.interfaces.Mainlistener;
import pnj.ti.b2013.smartparent.model.Student;
import pnj.ti.b2013.smartparent.service.VolleyTaskService;
import pnj.ti.b2013.smartparent.view.BaseActivity;
import pnj.ti.b2013.smartparent.view.MainActivity;

public class SelectStudentActivity extends BaseActivity implements SelectStudentAdapter.StudentListener {

    String TAG = SelectStudentActivity.class.getSimpleName();

    private String username;
    private SelectStudentAdapter studentAdapter;
    public Student student;
    private List<Student> arrayListstudent;
    private RecyclerView studentRecycler;
    private RelativeLayout emptyView;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_student);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.downloading));
        pDialog.setCancelable(false);
        pDialog.show();
        username = getIntent().getStringExtra("username");
        Log.e(TAG,"Extras: "+username);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
               initUI();
            }
        }, 3000);


    }

    private void initUI(){
        setToolbarUseImage(false);
        pDialog.dismiss();
        arrayListstudent = new ArrayList<>();
        studentAdapter = new SelectStudentAdapter(getApplicationContext(),arrayListstudent, this);

        studentRecycler = (RecyclerView) findViewById(R.id.studentRecyclerView);
        emptyView = (RelativeLayout) findViewById(R.id.studentEmptyView);


        studentRecycler.setLayoutManager(new LinearLayoutManager(this));
        studentRecycler.setAdapter(studentAdapter);


        emptyView.setVisibility(studentAdapter.getItemCount() == 0 ? View.VISIBLE : View.INVISIBLE);
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        getStudentlist();
    }


    private void getStudentlist()
    {
        if (getTaskService() != null) {
            getTaskService().studentList(username);
        } else {
            Toast.makeText(this, "Check Your Connection", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void receive(int type, boolean success, Bundle extras) {
        if (success) {
            String data = extras.getString(VolleyTaskService.RESPONSE_DATA);
            switch (type) {
                case VolleyTaskService.REQ_TYPE_STUDENT_LIST:
                    updateData(data);
                    Log.e(TAG,"Student Data "+data);
                    break;
            }
        } else {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.failed))
                    .setMessage(extras.getString(VolleyTaskService.RESPONSE_MESSAGE))
                    .show();
        }

    }

    public void updateData(String data){
        List<Student> listStudent = new Gson().fromJson(data, new TypeToken<List<Student>>() {
        }.getType());
        Log.e(TAG,"DATA LIST"+data);

        studentAdapter.updateData(listStudent);

        this.arrayListstudent.clear();
        this.arrayListstudent.addAll(listStudent);

    }

    @Override
    public void onSelectStudent(Student item) {
        Bundle extras = new Bundle();
        extras.putParcelable("student", item);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(extras);

        startActivity(intent);
    }
}
