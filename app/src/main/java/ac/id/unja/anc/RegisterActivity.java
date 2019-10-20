package ac.id.unja.anc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
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
        if(mockRegister()) {
            // Register berhasil, masuk ke activity user type
            Intent intent = new Intent(RegisterActivity.this, UserTypeActivity.class);
            startActivity(intent);
            finish();
        }else{
            // Register gagal
            Toast.makeText(this, "Register error", Toast.LENGTH_LONG).show();
        }
    }


    // Mock methods
    public boolean mockRegister() {
        String fullname = etFullName.getText().toString();
        String username = etUsername.getText().toString();
        String pass = etPass.getText().toString();
        String pass2 = etPass2.getText().toString();
        Toast.makeText(this, "POST" + "\nfullname: " + fullname + "\nusername: " + username + "\npass: " + pass + "\npass2: " + pass2, Toast.LENGTH_LONG).show();
        return true;
    }
    ////////////////
}
