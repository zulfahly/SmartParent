package pnj.ti.b2013.smartparent.view.studentBalance;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pnj.ti.b2013.smartparent.R;
import pnj.ti.b2013.smartparent.model.Balance;
import pnj.ti.b2013.smartparent.model.Student;
import pnj.ti.b2013.smartparent.service.VolleyTaskService;
import pnj.ti.b2013.smartparent.view.BaseActivity;
import pnj.ti.b2013.smartparent.view.MainActivity;

public class BalanceActivity extends BaseActivity {

    String TAG = BalanceActivity.class.getSimpleName();

    TextView currentBalance;
    TextView balanceDateHistory;
    TextView topupBalanceHistory;
    Student student;
    Balance balance;
    ArrayList<Balance> balanceList;
    Button btnSendLimit;
    EditText valueLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        student = getIntent().getParcelableExtra("student");
        Log.e(TAG, "student balance" + student.debit);

        Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                initUI();
            }
        }, 1500);
    }

    private void initUI() {
        setToolbar("Saldo", true);

        btnSendLimit = (Button) findViewById(R.id.btnSubmitLimit);
        valueLimit = (EditText) findViewById(R.id.valueLimit);
        currentBalance = (TextView) findViewById(R.id.saldoValue);
        balanceDateHistory = (TextView) findViewById(R.id.infoSaldoDate);
        topupBalanceHistory = (TextView) findViewById(R.id.infoSaldoValue);

        currentBalance.setText(getString(R.string.rupiah) + student.debit);

        getBalance();

        btnSendLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLimit();
            }
        });
    }

    private void setLimit() {
        if (getTaskService() != null) {
            getTaskService().setLimit(student.NIS,valueLimit.getText().toString());
        } else {
            Toast.makeText(this, "Check Your Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void getBalance() {
        if (getTaskService() != null) {
            getTaskService().checkBalance(student.NIS);
            Log.e(TAG, "student balance NIS" + student.NIS);
        } else {
            Toast.makeText(this, "Check Your Connection", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void receive(int type, boolean success, Bundle extras) {
        if (success) {
            String data = extras.getString(VolleyTaskService.RESPONSE_DATA);
            switch (type) {
                case VolleyTaskService.REQ_TYPE_CHECK_BALANCE:
                    Type balanceType = new TypeToken<ArrayList<Balance>>() {
                    }.getType();
                    balanceList = new Gson().fromJson(data, balanceType);

                    if (balanceList!=null){
                        balanceDateHistory.setText(balanceList.get(0).tanggal);
                        topupBalanceHistory.setText(getString(R.string.sejumlah)+" "+getString(R.string.rupiah) +balanceList.get(0).jumlah_debit);
                    }
                    break;
                case VolleyTaskService.REQ_TYPE_SET_LIMIT_BALANCE:
                    extras = new Bundle();
                    extras.putParcelable("student", student);

                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtras(extras);
                    Toast.makeText(this,"Pengaturan Limit Sukses",Toast.LENGTH_LONG).show();
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
