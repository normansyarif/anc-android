package ac.id.unja.anc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        LinearLayout actionBar = findViewById(R.id.custom_action_bar);
        actionBar.setElevation(10);
    }

    public void toMain(View view){
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void tujuanClicked(View v) {
        Intent intent = new Intent(WelcomeActivity.this, LocalWebviewActivity.class);
        intent.putExtra("fileContent",  "tujuan.html");
        startActivity(intent);
    }

    public void tempatClicked(View v) {
        Intent intent = new Intent(WelcomeActivity.this, LocalWebviewActivity.class);
        intent.putExtra("fileContent",  "tempat.html");
        startActivity(intent);
    }

    public void jadwalClicked(View v) {
        Intent intent = new Intent(WelcomeActivity.this, LocalWebviewActivity.class);
        intent.putExtra("fileContent",  "jadwal_kunjungan.html");
        startActivity(intent);
    }
}
