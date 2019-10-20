package ac.id.unja.anc;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import static ac.id.unja.anc.Utils.weekToDate;

public class PregnancyPeriodActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {
    Button openDialog;
    int periode = 0;

    // Mock data
    int userId = 1;
    //////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregnancy_period);

        openDialog = findViewById(R.id.open_dialog);
        openDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
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
        if(mockUpdatePeriode()) {
            // Berhasil update usia kehamilan, masuk ke activity welcome
            Intent intent = new Intent(PregnancyPeriodActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        }else{
            // Gagal update usia kehamilan
            Toast.makeText(this, "Update error", Toast.LENGTH_LONG).show();
        }
    }

    // Mock methods
    public boolean mockUpdatePeriode() {
        Toast.makeText(this, "UPDATE\nawal_hamil: " + weekToDate(periode) + " where userId: " + userId, Toast.LENGTH_LONG).show();
        return true;
    }
    ////////////////////
}
