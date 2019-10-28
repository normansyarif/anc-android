package ac.id.unja.anc;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import ac.id.unja.anc.Volley.Preferences;
import ac.id.unja.anc.Volley.Routes;
import ac.id.unja.anc.Volley.VolleyAPI;
import ac.id.unja.anc.Volley.VolleyResponseListener;

import static ac.id.unja.anc.Utils.weekToDate;

public class PregnancyPeriodActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {
    private VolleyAPI api = new VolleyAPI();
    private Routes routes = new Routes();
    HashMap<String, String> user;
    ProgressDialog progress;

    Button openDialog;
    int periode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregnancy_period);

        initLoading();

        user = (HashMap<String, String>) getIntent().getSerializableExtra("user");
        openDialog = findViewById(R.id.open_dialog);
        openDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
    }

    public void initLoading(){
        progress = new ProgressDialog(this);
        progress.setMessage("Loading...");
        progress.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Log.i("value is",""+newVal);
    }

    public void show() {
        final Dialog d = new Dialog(PregnancyPeriodActivity.this);
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
                periode = np.getValue();
                openDialog.setText(np.getValue() + " minggu");
                d.dismiss();
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

    public void mulaiBtnClicked(View v) {
        progress.show();
        user.put("awal_hamil", weekToDate(periode));

        api.postDataVolley(user, routes.register, PregnancyPeriodActivity.this, new VolleyResponseListener() {

            @Override
            public void onResponse(String response) {
                responseHandler(response);
                progress.dismiss();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                api.handleError(error.toString(), PregnancyPeriodActivity.this);
                progress.dismiss();
            }

        });
    }

    public void responseHandler(String response){
        try {
            JSONObject result = new JSONObject(response);
            String status = result.getString("status");

            if(status.equals("0")) {
                Toast.makeText(PregnancyPeriodActivity.this, "Terjadi Kesalahan. Mohon Coba beberapa saat lagi.", Toast.LENGTH_SHORT).show();
            } else if(status.equals("2")) {
                Toast.makeText(PregnancyPeriodActivity.this, "Username tidak tersedia", Toast.LENGTH_SHORT).show();
            } else {
                String token = result.getString("token");
                JSONObject user = new JSONObject(result.getString("user"));
                Preferences.getInstance().writeAuth("token", token);
                Preferences.getInstance().setUser(user);

                Intent intent = new Intent(PregnancyPeriodActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            api.handleError(e + response, PregnancyPeriodActivity.this);
        }
    }
}
