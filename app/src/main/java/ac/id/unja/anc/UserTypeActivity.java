package ac.id.unja.anc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class UserTypeActivity extends AppCompatActivity {

    // Mock data
    int userId = 1;
    ///////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);
    }

    public void onIbuClicked(View v) {
        if(mockUpdateUserType(1)) {
            // Berhasil update user type, masuk ke activity pilih usia kehamilan
            Intent intent = new Intent(UserTypeActivity.this, PregnancyPeriodActivity.class);
            startActivity(intent);
            finish();
        }else{
            // Gagal update user type
            Toast.makeText(this, "Pesan error", Toast.LENGTH_LONG).show();
        }

    }

    public void onMahasiswaClicked(View v) {
        if(mockUpdateUserType(2)) {
            // Berhasil update user type, masuk ke activity welcome
            Intent intent = new Intent(UserTypeActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        }else{
            // Gagal update user type
            Toast.makeText(this, "Pesan error", Toast.LENGTH_LONG).show();
        }
    }


    // Mock methods
    public boolean mockUpdateUserType(int typeId) {
        // Type ID 1 = ibu hamil
        // Type ID 2 = mahasiswa
        Toast.makeText(this, "UPDATE" + "\ntype: " + typeId + " where user_id: " + userId, Toast.LENGTH_LONG).show();
        return true;
    }
}
