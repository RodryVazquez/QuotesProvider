package quotesprovider.intelectix.com.quotesprovider.Helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {

    private Context context;

    /**
     *
     * @param context
     */
    public ConnectionDetector(Context context) {
        this.context = context;
    }

    /**
     *
     * @return
     */
    public boolean isConnectingToInternet(){
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return  activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
