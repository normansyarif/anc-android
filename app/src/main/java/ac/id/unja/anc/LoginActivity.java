package ac.id.unja.anc;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText etUsername, etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPass = findViewById(R.id.et_pass);
    }

    public void loginBtnClicked(View v) {
        if(mockLogin()) {
            // Login berhasil, masuk ke activity welcome
            updateLocalData();

            Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        }else{
            // Login gagal
            Toast.makeText(this, "Login error", Toast.LENGTH_LONG).show();
        }
    }

    public void registerLinkClicked(View v) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    // Mock methods
    public boolean mockLogin() {
        String username = etUsername.getText().toString();
        String pass = etPass.getText().toString();
        Toast.makeText(this, "POST\nusername: " + username + "\npass: " + pass, Toast.LENGTH_LONG).show();
        return true;
    }

    public void updateLocalData() {
        // Simpan data lokal
    }
    ////////////////
}
