package pnj.ti.b2013.smartparent.view;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pnj.ti.b2013.smartparent.R;
import pnj.ti.b2013.smartparent.service.VolleyTaskService;


/**
 * This abstract base class provides basic functionality to perform some tasks on service.
 * Additionally, it listens Android home item click and performs finish this activity.
 * <p/>
 * A class which extends this base activity will getString these benefits of service functionality
 * without service binding configuration on onResume() and onPause().
 * <p/>
 * Note: we use support action bar activity as extended class in this example. You could change
 * it depending on your needs.
 *
 * @author Alh, bambolz
 */
public abstract class BaseActivity extends AppCompatActivity implements VolleyTaskService.Callback {

    private VolleyTaskService volleyTaskService;

    /**
     * It is a channel between the activity and service.
     */
    protected ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            volleyTaskService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            VolleyTaskService.IGrowBinder binder = (VolleyTaskService.IGrowBinder) service;
            volleyTaskService = binder.getService();
            volleyTaskService.registerCallback(BaseActivity.this);
            onConnect();
        }
    };

    public void onConnect() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*
         * we do local binding here. Bind current activity to the service so the activity
         * can interact with service.
         * BIND_AUTO_CREATE means recreate the service if it is destroyed when
         * there is a bound activity.
         */
        bindService(new Intent(this, VolleyTaskService.class), serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(serviceConnection);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setToolbar(String title, boolean displayHomeAsUp) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        ImageView toolbarImage = (ImageView) toolbar.findViewById(R.id.toolbarImage);
        toolbarImage.setVisibility(View.GONE);

        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(title);
        toolbarTitle.setVisibility(View.VISIBLE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(displayHomeAsUp);
    }

    public void setToolbarUseImage(boolean displayHomeAsUp) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        ImageView toolbarImage = (ImageView) toolbar.findViewById(R.id.toolbarImage);
        toolbarImage.setVisibility(View.VISIBLE);

        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbarTitle);
        toolbarTitle.setVisibility(View.GONE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(displayHomeAsUp);
    }

    // getter methods

    /**
     * Get service which manages tasks. Often you call this method
     * from activity which extends this class.
     *
     * @return download service.
     */
    public VolleyTaskService getTaskService() {
        return volleyTaskService;
    }
}
