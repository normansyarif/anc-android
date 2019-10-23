package ac.id.unja.anc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import ac.id.unja.anc.Volley.Preferences;
import ac.id.unja.anc.Volley.Routes;
import ac.id.unja.anc.Volley.VolleyAPI;
import ac.id.unja.anc.Volley.VolleyResponseListener;

public class UserTypeActivity extends AppCompatActivity {
    private VolleyAPI api = new VolleyAPI();
    private Routes routes = new Routes();
    HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);
        user = (HashMap<String, String>) getIntent().getSerializableExtra("user");
    }

    public void onIbuClicked(View v) {
        user.put("tipe", "1");

        Intent intent = new Intent(UserTypeActivity.this, PregnancyPeriodActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    public void onMahasiswaClicked(View v) {
        user.put("tipe", "2");

        api.postDataVolley(user, routes.register, UserTypeActivity.this, new VolleyResponseListener() {

            @Override
            public void onResponse(String response) {
                responseHandler(response);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                api.handleError(error.toString(), UserTypeActivity.this);
            }

        });
    }

    public void responseHandler(String response){
        try {
            JSONObject result = new JSONObject(response);
            String status = result.getString("status");

            if(status.equals("0")) {
                Toast.makeText(UserTypeActivity.this, "Terjadi Kesalahan. Mohon Coba beberapa saat lagi.", Toast.LENGTH_SHORT).show();
            } else if(status.equals("2")) {
                Toast.makeText(UserTypeActivity.this, "Username tidak tersedia", Toast.LENGTH_SHORT).show();
            } else {
                String token = result.getString("token");
                JSONObject user = new JSONObject(result.getString("user"));
                Preferences.getInstance().writeAuth("token", token);
                Preferences.getInstance().setUser(user);

                Intent intent = new Intent(UserTypeActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            api.handleError(e + response, UserTypeActivity.this);
        }
    }


}
