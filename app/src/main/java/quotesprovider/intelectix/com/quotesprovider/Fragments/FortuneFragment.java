package quotesprovider.intelectix.com.quotesprovider.Fragments;


import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import quotesprovider.intelectix.com.quotesprovider.Helpers.ConnectionDetector;
import quotesprovider.intelectix.com.quotesprovider.Helpers.Preferences;
import quotesprovider.intelectix.com.quotesprovider.R;


public class FortuneFragment extends Fragment {

    private TextView txtFortune;

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

        txtFortune = (TextView)getActivity().findViewById(R.id.txtFortune);
        Preferences preferences = new Preferences(getActivity());
        if(preferences.isFirstTime()){
            Toast.makeText(getActivity(),"Hi " + preferences.getUserName(),Toast.LENGTH_SHORT).show();
            preferences.setOld(true);
        }else{
            Toast.makeText(getActivity(),"Welcome back " + preferences.getUserName(),Toast.LENGTH_SHORT).show();
        }

        ConnectionDetector detector = new ConnectionDetector(getActivity());
        if(detector.isConnectingToInternet()){
            getFortuneOnLine();
        }else{
            readFortuneFromFile();
        }
    }


    /**
     *
     */
    private void getFortuneOnLine(){

    }

    /**
     *
     * @param data
     */
    private void writeToFile(String data){

    }

    /**
     *
     */
    private void readFortuneFromFile(){

    }
}
