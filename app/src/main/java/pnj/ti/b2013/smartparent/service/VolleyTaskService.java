package pnj.ti.b2013.smartparent.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pnj.ti.b2013.smartparent.R;
import pnj.ti.b2013.smartparent.util.Preferences;

public class VolleyTaskService extends Service {

    public interface Callback {
        void receive(int type, boolean success, Bundle extras);
    }

    public class IGrowBinder extends Binder {
        public VolleyTaskService getService() {
            return VolleyTaskService.this;
        }
    }

    private static final int TIMEOUT = 60000; // 60 seconds timeout interval
    public static final String RESPONSE_TOKEN = "x-api-token";
    public static final String RESPONSE_MESSAGE = "message";
    public static final String RESPONSE_DATA = "data";

    public static final int REQ_TYPE_LOGIN = 1;
    public static final int REQ_TYPE_STUDENT_LIST = 2;
    public static final int REQ_TYPE_STUDENT_PRESENCE = 3;
    public static final int REQ_TYPE_ADD_IZIN = 4;
    public static final int REQ_TYPE_CHECK_BALANCE = 5;
    public static final int REQ_TYPE_PROFILE = 6;
    public static final int REQ_TYPE_EDIT_PROFILE = 7;
    public static final int REQ_TYPE_SEND_PICKUP = 8;
    public static final int REQ_TYPE_EDIT_PASSWORD = 9;
    public static final int REQ_TYPE_GET_PESAN = 10;
    public static final int REQ_TYPE_CREATE_PESAN = 11;
    public static final int REQ_TYPE_SET_LIMIT_BALANCE = 12;


    private static final String API_ENDPOINT = "http://api.dasmartschool.com";

    private static final String LOGIN_URL = "smartparent/login_ortu";
    private static final String STUDENTLIST_URL = "smartparent/get_student_list";
    private static final String DATA_PRESENSI_URL = "smartparent/get_presensi_siswa";
    private static final String ADD_IZIN_URL = "smartparent/add_izin";
    private static final String CHECK_BALANCE_URL = "smartparent/get_uang_saku";
    private static final String CHECK_PROFILE_URL = "smartparent/get_profile";
    private static final String EDIT_PROFILE_URL = "smartparent/edit_profile";
    private static final String ADD_PICKUP_URL = "smartparent/add_penjemputan";
    private static final String EDIT_PASSWORD_URL = "smartparent/edit_password";
    private static final String GET_PESAN_URL = "smartparent/get_pesan";
    private static final String CREATE_PESAN_URL = "smartparent/create_pesan";
    private static final String SET_BALANCE_LIMIT_URL = "smartparent/set_limit_saldo";

    // bound activity
    private Callback activityCallback;
    private RequestQueue requestQueue;
    private final IBinder binder = new IGrowBinder();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // it is performed when startService() in an activity is called
        return Service.BIND_AUTO_CREATE;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // it is performed when bindService() in an activity is called
        return binder;
    }

    private StringRequest composeStringRequest(int method, String url, final int type, @Nullable final Map<String, String> params, @Nullable final Map<String, String> headers) {
        Response.Listener<String> successListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("success listener: " + type, response);
                Bundle bundle = new Bundle();

                try {
                    JSONObject responseJson = new JSONObject(response);
                    bundle.putString(RESPONSE_MESSAGE, responseJson.getString(RESPONSE_MESSAGE));

                    if (responseJson.get(RESPONSE_DATA) != null) {
                        bundle.putString(RESPONSE_DATA, responseJson.get(RESPONSE_DATA).toString());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                activityCallback.receive(type, true, bundle);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Bundle bundle = new Bundle();

                try {
                    if (error.networkResponse != null) {
                        JSONObject responseJson = new JSONObject(new String(error.networkResponse.data, "utf-8"));
                        bundle.putString(RESPONSE_MESSAGE, responseJson.getString(RESPONSE_MESSAGE));

                    } else {
                        bundle.putString(RESPONSE_MESSAGE, getString(R.string.no_response));
                    }

                } catch (Exception err) {
                    bundle.putString(RESPONSE_MESSAGE, getString(R.string.internal_server_error));
                    err.printStackTrace();
                }

                activityCallback.receive(type, false, bundle);
            }
        };

        return new StringRequest(method, url, successListener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params == null) {
                    return new HashMap<>();

                } else {
                    return params;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (headers == null) {
                    return new HashMap<>();

                } else {
                    return headers;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Preferences.getInstance(getApplicationContext()).storeString(Preferences.TOKEN, response.headers.get(RESPONSE_TOKEN));
                Log.d("token", "set token: " + response.headers.get(RESPONSE_TOKEN));

                return super.parseNetworkResponse(response);
            }
        };
    }


    private void addToRequestQueue(int requestType, Request<String> request) {
        request.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        if (isNetworkAvailable()) {
            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);
            } else {
                requestQueue.add(request);
            }
        } else {
            noInternetCallBack(requestType);
        }
    }

    private void noInternetCallBack(int requestType) {
        Bundle bundle = new Bundle();
        bundle.putString(RESPONSE_MESSAGE, getString(R.string.no_internet));
        activityCallback.receive(requestType, false, bundle);
    }

    public boolean isNetworkAvailable() {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    private Map<String, String> getDefaultHeaders() {
        String token = Preferences.getInstance(getApplicationContext()).getString(Preferences.TOKEN);
        Log.d("token", "get token: " + token);

        if (token == null) {
            return null;

        } else {
            Map<String, String> headers = new HashMap<>();
            headers.put(RESPONSE_TOKEN, token);

            return headers;
        }
    }

    private String getURL(String url) {
        return API_ENDPOINT + "/" + url;
    }

    public void registerCallback(Callback activityCallback) {
        this.activityCallback = activityCallback;
    }

    public void login(final String username, final String password) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
//        params.put("gcm_token", fcmToken);

        StringRequest request = composeStringRequest(Request.Method.POST, getURL(LOGIN_URL), REQ_TYPE_LOGIN, params, null);
        addToRequestQueue(REQ_TYPE_LOGIN, request);
    }

    public void studentList(String username) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);

        StringRequest request = composeStringRequest(Request.Method.POST, getURL(STUDENTLIST_URL), REQ_TYPE_STUDENT_LIST, params, null);
        addToRequestQueue(REQ_TYPE_STUDENT_LIST, request);
    }

    public void studentPrecenseList(String NIS) {
        Map<String, String> params = new HashMap<>();
        params.put("nis", NIS);

        StringRequest request = composeStringRequest(Request.Method.POST, getURL(DATA_PRESENSI_URL), REQ_TYPE_STUDENT_PRESENCE, params, null);
        addToRequestQueue(REQ_TYPE_STUDENT_PRESENCE, request);
    }

    public void usePermit(final String namaSiswa, final String jenisIzin, String detail) {
        Map<String, String> params = new HashMap<>();
        params.put("nama_siswa", namaSiswa);
        params.put("jenis_izin", jenisIzin);
        params.put("keterangan", detail);

        StringRequest request = composeStringRequest(Request.Method.POST, getURL(ADD_IZIN_URL), REQ_TYPE_ADD_IZIN, params, null);
        addToRequestQueue(REQ_TYPE_ADD_IZIN, request);
    }

    public void checkBalance(final String NIS) {
        Map<String, String> params = new HashMap<>();
        params.put("nis", NIS);

        StringRequest request = composeStringRequest(Request.Method.POST, getURL(CHECK_BALANCE_URL), REQ_TYPE_CHECK_BALANCE, params, null);
        addToRequestQueue(REQ_TYPE_CHECK_BALANCE, request);
    }

    public void checkProfile(final String username) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);

        StringRequest request = composeStringRequest(Request.Method.POST, getURL(CHECK_PROFILE_URL), REQ_TYPE_PROFILE, params, null);
        addToRequestQueue(REQ_TYPE_PROFILE, request);
    }

    public void editProfile(final String username, String alamat, String telepon) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("alamat", alamat);
        params.put("nomor_telepon", telepon);

        StringRequest request = composeStringRequest(Request.Method.POST, getURL(EDIT_PROFILE_URL), REQ_TYPE_EDIT_PROFILE, params, null);
        addToRequestQueue(REQ_TYPE_EDIT_PROFILE, request);
    }

    public void addPickup(final String name, String ktp, String telepon, String nis, String relation) {
        Map<String, String> params = new HashMap<>();
        params.put("nama", name);
        params.put("ktp", ktp);
        params.put("nomor_hp", telepon);
        params.put("nis", nis);
        params.put("hubungan", relation);

        StringRequest request = composeStringRequest(Request.Method.POST, getURL(ADD_PICKUP_URL), REQ_TYPE_SEND_PICKUP, params, null);
        addToRequestQueue(REQ_TYPE_SEND_PICKUP, request);
    }

    public void editPassword(final String username, String oldPassword, String newPassword, String retypePassword) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password_lama", oldPassword);
        params.put("password_baru", newPassword);
        params.put("retype_password_baru", newPassword);

        StringRequest request = composeStringRequest(Request.Method.POST, getURL(EDIT_PASSWORD_URL), REQ_TYPE_EDIT_PASSWORD, params, null);
        addToRequestQueue(REQ_TYPE_EDIT_PASSWORD, request);
    }

    public void readMessage(String username, String kelas) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("id_kelas", kelas);
        StringRequest request = composeStringRequest(Request.Method.POST, getURL(GET_PESAN_URL), REQ_TYPE_GET_PESAN, params, getDefaultHeaders());
        addToRequestQueue(REQ_TYPE_GET_PESAN, request);
    }

    public void replyMessage(String username, String id_kelas, String content) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("id_kelas", id_kelas);
        params.put("content", content);

        StringRequest request = composeStringRequest(Request.Method.POST, getURL(CREATE_PESAN_URL), REQ_TYPE_CREATE_PESAN, params, getDefaultHeaders());
        addToRequestQueue(REQ_TYPE_CREATE_PESAN, request);
    }

    public void setLimit(String nis, String limit) {
        Map<String, String> params = new HashMap<>();
        params.put("nis", nis);
        params.put("limit", limit);

        StringRequest request = composeStringRequest(Request.Method.POST, getURL(SET_BALANCE_LIMIT_URL), REQ_TYPE_SET_LIMIT_BALANCE, params, getDefaultHeaders());
        addToRequestQueue(REQ_TYPE_SET_LIMIT_BALANCE, request);
    }

}