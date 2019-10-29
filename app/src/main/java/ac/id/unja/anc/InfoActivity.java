package ac.id.unja.anc;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import ac.id.unja.anc.Volley.Preferences;

import static ac.id.unja.anc.Utils.*;

public class InfoActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {
    TextView tvPeriode;
    long usiaHamil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        final SharedPreferences user = Preferences.getInstance().getUser();

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

        String tipe = user.getString("tipe", "0");
        if(!tipe.equals("1")){
            usiaHamil = 0;
        }else if(tipe.equals("2")){
            usiaHamil = dateToWeek(user.getString("awal_hamil", null));
        }


        tvPeriode.setText("Usia kehamilan " + usiaHamil + " minggu");

        cardGizi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, LocalWebviewActivity.class);
                intent.putExtra("fileContent",  "gizi/" + usiaHamil + ".html");
                startActivity(intent);
            }
        });

        cardBesi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, LocalWebviewActivity.class);
                intent.putExtra("fileContent",  "besi/" + usiaHamil + ".html");
                startActivity(intent);
            }
        });

        cardNutrisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, LocalWebviewActivity.class);
                intent.putExtra("fileContent",  "nutrisi/" + usiaHamil + ".html");
                startActivity(intent);
            }
        });

        cardKarakteristik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, LocalWebviewActivity.class);
                intent.putExtra("fileContent",  "karakteristik/" + usiaHamil + ".html");
                startActivity(intent);
            }
        });

        tvPeriode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilih();
            }
        });

    }

    public void pilih() {
        final Dialog d = new Dialog(InfoActivity.this);
        d.setTitle("Pilih usia kehamilan");
        d.setContentView(R.layout.period_selector);
        Button b1 = d.findViewById(R.id.button1);
        Button b2 = d.findViewById(R.id.button2);
        final NumberPicker np = d.findViewById(R.id.numberPicker1);
        np.setMaxValue(37);
        np.setMinValue(0);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                int periode = np.getValue();
                tvPeriode.setText("Usia kehamilan " + periode + " minggu");
                d.dismiss();

                usiaHamil = periode;
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
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

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Log.i("value is",""+newVal);
    }
}
