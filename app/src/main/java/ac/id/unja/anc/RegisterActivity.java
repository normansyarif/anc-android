package ac.id.unja.anc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ac.id.unja.anc.Volley.Preferences;
import ac.id.unja.anc.Volley.Routes;
import ac.id.unja.anc.Volley.VolleyAPI;

public class RegisterActivity extends AppCompatActivity {
    String token;
    EditText etFullName, etUsername, etPass, etPass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFullName = findViewById(R.id.et_full_name);
        etUsername = findViewById(R.id.et_username);
        etPass = findViewById(R.id.et_pass);
        etPass2 = findViewById(R.id.et_pass_2);
    }

    public void registerBtnClicked(View v) {
        String name = etFullName.getText().toString();
        String username = etUsername.getText().toString();
        String pass = etPass.getText().toString();
        String pass2 = etPass2.getText().toString();

        if(!pass.equals(pass2)) {
            Toast.makeText(this, "Password tidak cocok. Mohon coba lagi.", Toast.LENGTH_SHORT).show();
        }else{
            HashMap<String, String> user = new HashMap<>();
            user.put("name", name);
            user.put("username", username);
            user.put("password", pass);

            Intent intent = new Intent(RegisterActivity.this, UserTypeActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
    }

}
