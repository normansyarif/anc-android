package ac.id.unja.anc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import static ac.id.unja.anc.Utils.*;

public class InfoActivity extends AppCompatActivity {
    TextView tvPeriode;

    // Mock data
    long periode = dateToWeek("2019-09-12");
    ///////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        tvPeriode = findViewById(R.id.tv_periode);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Info Kehamilan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        CardView cardGizi = findViewById(R.id.card_gizi);
        CardView cardBesi = findViewById(R.id.card_besi);
        CardView cardNutrisi = findViewById(R.id.card_nutrisi);
        CardView cardKarakteristik = findViewById(R.id.card_karakteristik);

        tvPeriode.setText("Usia kehamilan " + periode + " minggu");

        cardGizi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, LocalWebviewActivity.class);
                intent.putExtra("fileContent",  "gizi/" + periode + ".html");
                startActivity(intent);
            }
        });

        cardBesi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, LocalWebviewActivity.class);
                intent.putExtra("fileContent",  "besi/" + periode + ".html");
                startActivity(intent);
            }
        });

        cardNutrisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, LocalWebviewActivity.class);
                intent.putExtra("fileContent",  "nutrisi/" + periode + ".html");
                startActivity(intent);
            }
        });

        cardKarakteristik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, LocalWebviewActivity.class);
                intent.putExtra("fileContent",  "karakteristik/" + periode + ".html");
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
