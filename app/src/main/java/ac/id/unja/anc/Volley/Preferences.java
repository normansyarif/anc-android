package ac.id.unja.anc.Volley;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class Preferences {
    private static Preferences mInstance;
    private Context mContext;
    private SharedPreferences prefAuth, prefUser;

    private Preferences(){ }


    // ------ Return this class, so another class may access the method.


    public static Preferences getInstance(){
        if (mInstance == null) mInstance = new Preferences();
        return mInstance;
    }


    // ------ Initialize. Only at first Activity.


    public void Initialize(Context context){
        mContext = context;
        prefAuth = context.getSharedPreferences("auth", 0);
        prefUser = context.getSharedPreferences("user", 0);
    }


    // ----- Write using Editor


    public void write(SharedPreferences pref, String key, String val){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, val);
        editor.commit();
    }


    //  -------
    //  ------- One Preference One Write Method
    //  -------


    public void writeAuth(String key, String val){
        write(prefAuth, key, val);
    }

    public void writeUser(String key, String val) {
        write(prefUser, key, val);
    }


    //  -------
    // -------- Get Value
    //  -------


    public String getToken(){
        return prefAuth.getString("token", null);
    }

    public SharedPreferences getUser() {
        return prefUser;
    }


    //  -------
    // -------- Set Value
    //  -------


    public void setUser(JSONObject user) throws JSONException {
        Preferences prefUser = Preferences.getInstance();
        prefUser.writeUser("name", user.getString("name"));
        prefUser.writeUser("username", user.getString("username"));;
        prefUser.writeUser("tanggal_lahir", user.getString("tanggal_lahir"));
        prefUser.writeUser("tipe",  user.getString("tipe"));
        prefUser.writeUser("awal_hamil", user.getString("awal_hamil"));
    }

}
