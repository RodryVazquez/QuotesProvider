package quotesprovider.intelectix.com.quotesprovider.Helpers;


import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "";
    private static final String IS_FIRSTTIME = "";
    public static final String UserName = "name";


    public Preferences(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = preferences.edit();
    }

    /**
     * Indica si es la primera vez que se accede a la aplicacion
     * @return
     */
    public Boolean isFirstTime(){
        return preferences.getBoolean(IS_FIRSTTIME,true);
    }

    /**
     * Crea la preferencia que indica que el usuario accedio a ala aplicacion
     * @param b
     */
    public void setOld(boolean b){
        if(b){
            editor.putBoolean(IS_FIRSTTIME,false);
            editor.commit();
        }
    }

    /**
     * Obtiene el nombre del usuario
     * @return
     */
    public String getUserName(){
        return preferences.getString(UserName,"");
    }

    /**
     * Guarda al usuario
     * @param name
     */
    public void setUserName(String name){
        editor.putString(UserName,name);
        editor.commit();
    }
}
