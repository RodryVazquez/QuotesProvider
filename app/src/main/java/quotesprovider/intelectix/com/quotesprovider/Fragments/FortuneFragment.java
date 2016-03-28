package quotesprovider.intelectix.com.quotesprovider.Fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;

import quotesprovider.intelectix.com.quotesprovider.Helpers.ConnectionDetector;
import quotesprovider.intelectix.com.quotesprovider.Helpers.Preferences;
import quotesprovider.intelectix.com.quotesprovider.R;
import quotesprovider.intelectix.com.quotesprovider.app.AppController;


public class FortuneFragment extends Fragment {

    private TextView txtFortune;
    private static final String TAG = "FortuneFragment";

    public FortuneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fortune, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        txtFortune = (TextView) getActivity().findViewById(R.id.txtFortune);
        Preferences preferences = new Preferences(getActivity());
        if (preferences.isFirstTime()) {
            Toast.makeText(getActivity(), "Hi " + preferences.getUserName(), Toast.LENGTH_SHORT).show();
            preferences.setOld(true);
        } else {
            Toast.makeText(getActivity(), "Welcome back " + preferences.getUserName(), Toast.LENGTH_SHORT).show();
        }

        ConnectionDetector detector = new ConnectionDetector(getActivity());
        if (detector.isConnectingToInternet()) {
            getFortuneOnLine();
        } else {
            readFortuneFromFile();
        }
    }


    /**
     *
     */
    private void getFortuneOnLine() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Consultando informacion...");
        progressDialog.show();

        txtFortune.setText("Loading...");

        JsonObjectRequest request =
                new JsonObjectRequest(Request.Method.GET,
                        "http://jsonplaceholder.typicode.com/posts/1", "",
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                    String fortune;
                                    try {
                                        fortune = response.getString("body");
                                        Log.d(TAG, fortune);
                                    } catch (JSONException e) {
                                        Log.e(TAG, e.getMessage());
                                        fortune = "Error";
                                    }

                                    txtFortune.setText(fortune);
                                    writeToFile(fortune);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                txtFortune.setText("");
                                VolleyLog.e("Response", "Error" + error.getMessage());
                                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            }
                        });

        AppController.getInstance().addToRequestQueue(request);
    }

    /**
     * @param data
     */
    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter(getActivity().openFileOutput("Fortune.json", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
           Log.e(TAG,e.getMessage());
        }
    }

    /**
     *
     */
    private void readFortuneFromFile() {
        String fortune = " ";
        try {
            InputStream inputStream = getActivity().openFileInput("Fortune.json");
            if(inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                fortune = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG,e.getMessage());
        } catch (IOException e) {
            Log.e(TAG,e.getMessage());
        }
        txtFortune.setText(fortune);
    }
}
