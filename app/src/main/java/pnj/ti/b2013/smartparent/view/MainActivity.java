package pnj.ti.b2013.smartparent.view;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import pnj.ti.b2013.smartparent.R;
import pnj.ti.b2013.smartparent.model.Student;
import pnj.ti.b2013.smartparent.view.message.MessageActivity;
import pnj.ti.b2013.smartparent.view.notification.NotificationActivity;
import pnj.ti.b2013.smartparent.view.permit.PermitActivity;
import pnj.ti.b2013.smartparent.view.pickUpStudent.PickUpStudentActivity;
import pnj.ti.b2013.smartparent.view.presence.PresenceActivity;
import pnj.ti.b2013.smartparent.view.profile.ProfileActivity;
import pnj.ti.b2013.smartparent.view.studentBalance.BalanceActivity;

public class MainActivity extends BaseActivity {

    String TAG = MainActivity.class.getSimpleName();
    final static int REQUEST_PHONE_CALL = 1;

    TextView studentName;
    TextView className;
    TextView studentBalance;
    LinearLayout presensiBtn;
    LinearLayout penjemputanBtn;
    LinearLayout perizinanBtn;
    LinearLayout notifikasiBtn;
    LinearLayout uangsakuBtn;
    LinearLayout pesanBtn;
    LinearLayout profilBtn;
    LinearLayout phoneCall;

    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        student = getIntent().getParcelableExtra("student");
        Log.e(TAG, "extras student" + student);

        setToolbarUseImage(false);

        studentName = (TextView) findViewById(R.id.studentName);
        className = (TextView) findViewById(R.id.classOfStudent);
        studentBalance = (TextView) findViewById(R.id.studentBalance);
        presensiBtn = (LinearLayout) findViewById(R.id.linPrensensi);
        penjemputanBtn = (LinearLayout) findViewById(R.id.linPenjemputan);
        perizinanBtn = (LinearLayout) findViewById(R.id.linPerizinan);
        notifikasiBtn = (LinearLayout) findViewById(R.id.linNotifikasi);
        uangsakuBtn = (LinearLayout) findViewById(R.id.linUangsaku);
        pesanBtn = (LinearLayout) findViewById(R.id.linPesan);
        profilBtn = (LinearLayout) findViewById(R.id.linProfile);
        phoneCall = (LinearLayout) findViewById(R.id.linPhonecall);

        studentName.setText(student.nama_siswa);
        className.setText(getString(R.string.darul_abidin) + " " + getString(R.string.kelas) + " " + student.nama_kelas);
        studentBalance.setText(getString(R.string.rupiah) + student.debit);

        presensiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = new Bundle();
                extras.putParcelable("student", student);

                Intent intent = new Intent(MainActivity.this, PresenceActivity.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        penjemputanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PickUpStudentActivity.class);
                startActivity(intent);
            }
        });

        perizinanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = new Bundle();
                extras.putParcelable("student", student);

                Intent intent = new Intent(MainActivity.this, PermitActivity.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        notifikasiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });

        uangsakuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = new Bundle();
                extras.putParcelable("student", student);

                Intent intent = new Intent(MainActivity.this, BalanceActivity.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        pesanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MessageActivity.class);
                startActivity(intent);
            }
        });

        profilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = new Bundle();
                extras.putParcelable("student", student);

                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        phoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG,"CALL CLICKED");
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:02177802989"));
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                }
                else
                {
                    startActivity(intent);
                }



//                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return;
//                }
//
//                startActivity(callIntent);
            }
        });
    }

    @Override
    public void receive(int type, boolean success, Bundle extras) {

    }
}
