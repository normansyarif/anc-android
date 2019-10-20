package ac.id.unja.anc;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    // Mock data //
    boolean dataLoginLocal = false;
    //--------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(!dataLoginLocal) {
            // Data login lokal tidak ada, masuk ke activity login
            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
            finish();
        }else{
            // Data login di lokal ada, coba login
            if(mockLogin()) {
                // Login berhasil, masuk ke activity welcome
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        startActivity(new Intent(SplashActivity.this,WelcomeActivity.class));
                        finish();
                    }
                },2000);
            }else{
                // Login gagal, masuk ke activity login, tampilkan error
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                        finish();
                    }
                },2000);
            }
        }
    }

    // Mock method
    private boolean mockLogin() {
        return false;
    }
    //---------------
}
