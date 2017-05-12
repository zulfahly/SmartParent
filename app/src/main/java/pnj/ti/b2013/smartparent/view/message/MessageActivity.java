package pnj.ti.b2013.smartparent.view.message;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


import pnj.ti.b2013.smartparent.R;
import pnj.ti.b2013.smartparent.model.Message;
import pnj.ti.b2013.smartparent.model.Profile;
import pnj.ti.b2013.smartparent.model.Student;
import pnj.ti.b2013.smartparent.service.VolleyTaskService;
import pnj.ti.b2013.smartparent.util.Preferences;
import pnj.ti.b2013.smartparent.view.BaseActivity;

public class MessageActivity extends BaseActivity {

    private static final String TAG = MessageActivity.class.getSimpleName();
    ArrayList<Message> messageList;
    RecyclerView recyclerView;
    RelativeLayout emptyView;
    MessageAdapter messageAdapter;
    Profile profile;
    private LinearLayoutManager layoutManager;
    Message message;
    LinearLayout sendButton;
    Student student;
    EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        profile = Preferences.getInstance(this).getProfile();
        student = getIntent().getParcelableExtra("student");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                initUI();
            }
        }, 3000);
    }

    private void initUI() {
        setToolbar("Perpesanan", true);

        sendButton = (LinearLayout) findViewById(R.id.btnSend);
        content = (EditText) findViewById(R.id.contentBox);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(getApplicationContext(), messageList);

        recyclerView = (RecyclerView) findViewById(R.id.messageRecyclerView);
        emptyView = (RelativeLayout) findViewById(R.id.emptyView);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(messageAdapter);

        emptyView.setVisibility(messageAdapter.getItemCount() == 0 ? View.VISIBLE : View.INVISIBLE);

        getData();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });


    }

    private void sendMessage() {
        if (getTaskService() != null) {
            getTaskService().replyMessage(profile.username,student.id_kelas,content.getText().toString());
            content.setText("");

        } else {
            Toast.makeText(this, "Failed Send Message", Toast.LENGTH_LONG).show();
        }

    }

    private void getData() {
        if (getTaskService() != null) {
            getTaskService().readMessage(profile.username, student.id_kelas);
        } else {
            Toast.makeText(this, "Check Your Connection", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void receive(int type, boolean success, Bundle extras) {
        if (success) {
            String data = extras.getString(VolleyTaskService.RESPONSE_DATA);
            switch (type) {
                case VolleyTaskService.REQ_TYPE_GET_PESAN:
                    updateData(data);
                    Log.e(TAG, "PESAN  " + data);
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

        List<Message> messages = new Gson().fromJson(data, new TypeToken<List<Message>>() {
        }.getType());

        messageAdapter.updateData(messages);

        this.messageList.clear();
        this.messageList.addAll(messages);
        layoutManager.scrollToPosition(messageAdapter.getItemCount());
    }
}
