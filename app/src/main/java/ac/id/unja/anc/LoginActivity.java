package ac.id.unja.anc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import ac.id.unja.anc.Volley.Preferences;
import ac.id.unja.anc.Volley.Routes;
import ac.id.unja.anc.Volley.VolleyAPI;
import ac.id.unja.anc.Volley.VolleyResponseListener;

public class LoginActivity extends AppCompatActivity {
    private VolleyAPI api = new VolleyAPI();
    private Routes routes = new Routes();
    ProgressDialog progress;
    EditText etUsername, etPass;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Preferences.getInstance().Initialize(getApplicationContext());

        etUsername = findViewById(R.id.et_username);
        etPass = findViewById(R.id.et_pass);
        initLoading();
    }

    @Override
    protected void onResume() {
        super.onResume();

        token = Preferences.getInstance().getToken();


        if(!TextUtils.isEmpty(token)) {
            HashMap<String, String> user = new HashMap<>();
            user.put("token", token);
            progress.show();

            api.postDataVolley(user, routes.checkLogin, LoginActivity.this, new VolleyResponseListener() {

                @Override
                public void onResponse(String response) {
                    progress.dismiss();

                    try {
                        JSONObject result = new JSONObject(response);
                        String status = result.getString("status");
                        if (status.equals("1")) {
                            Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                            startActivity(intent);
                            finish();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        api.handleError(e + response, LoginActivity.this);
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    progress.dismiss();
                    api.handleError(error.toString(), LoginActivity.this);
                }

            });

        }
    }

    public void initLoading(){
        progress = new ProgressDialog(this);
        progress.setMessage("Loading...");
        progress.setCanceledOnTouchOutside(false);
    }

    public void loginBtnClicked(View v) {
        progress.show();
        HashMap<String, String> user = new HashMap<>();
        user.put("username", etUsername.getText().toString());
        user.put("password", etPass.getText().toString());

        api.postDataVolley(user, routes.login, LoginActivity.this, new VolleyResponseListener() {

            @Override
            public void onResponse(String response) {
                progress.dismiss();

                try {
                    JSONObject result = new JSONObject(response);
                    String token = result.getString("token");

                    if(token.equals("0")) {
                        Toast.makeText(LoginActivity.this, "Username atau Password tidak ditemukan", Toast.LENGTH_SHORT).show();
                    } else {
                        JSONObject user = new JSONObject(result.getString("user"));
                        Preferences.getInstance().writeAuth("token", token);
                        Preferences.getInstance().setUser(user);

                        Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    api.handleError(e + response, LoginActivity.this);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                api.handleError(error.toString(), LoginActivity.this);
            }

        });
    }

    public void registerLinkClicked(View v) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

}
