package ac.id.unja.anc;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import ac.id.unja.anc.Volley.Preferences;
import ac.id.unja.anc.Volley.Routes;
import ac.id.unja.anc.Volley.VolleyAPI;
import ac.id.unja.anc.Volley.VolleyResponseListener;

import static ac.id.unja.anc.Utils.*;

public class ProfileActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {
    private VolleyAPI api = new VolleyAPI();
    private Routes routes = new Routes();
    ProgressDialog progress;
    final Calendar myCalendar = Calendar.getInstance();
    Bitmap bitmap;
    String foto = "";
    EditText edittext, edittext2, etfullname;
    ImageView profile;
    boolean btnSaveActive = true;
    private final static int GALLERY_REQUEST_CODE = 12345;

    String token, fullname, ttl;
    long periode;
    int profilePicture = R.drawable.miku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        token = Preferences.getInstance().getToken();
        etfullname = findViewById(R.id.et_fullname);
        edittext = findViewById(R.id.birthday);
        edittext2 = findViewById(R.id.pregnant_date);
        profile = findViewById(R.id.imageView1);

        Glide.with(this).load(routes.imgProfile + token)
                .thumbnail(Glide.with(this).load(R.drawable.ic_broken_image))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(profile);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        toolbar.setElevation(10);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit profil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SharedPreferences user = Preferences.getInstance().getUser();
        etfullname.setText(user.getString("name", null));
        edittext.setText(user.getString("tanggal_lahir", null));
        profile.setImageResource(profilePicture);

        String tipe = user.getString("tipe", "0");
        if(tipe.equals("1")) {
            edittext2.setText(String.valueOf(dateToWeek(user.getString("awal_hamil", null))));
        }

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ProfileActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        edittext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
        initLoading();
    }

    public void initLoading(){
        progress = new ProgressDialog(this);
        progress.setMessage("Loading...");
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }

    public void show() {
        final Dialog d = new Dialog(ProfileActivity.this);
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
                edittext2.setText(String.valueOf(np.getValue()));
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

    public void onLogoutClicked(View v) {
            Preferences.getInstance().writeAuth("token", null);
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
    }

    public void onProfileClicked(View v) {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST_CODE:
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);

                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    cursor.close();

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        foto   = Utils.getStringImage(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    profile.setImageURI(selectedImage);
                    break;
            }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_save_profile:
                if(btnSaveActive){
                    btnSaveActive = false;
                    progress.show();
                    String fullname = etfullname.getText().toString();
                    String birthday = edittext.getText().toString();
                    int hamil = Integer.parseInt(edittext2.getText().toString());
                    String usiaHamil = weekToDate(hamil);

                    HashMap<String, String> user = new HashMap<>();
                    user.put("token", token);
                    user.put("name", fullname);
                    user.put("awal_hamil", usiaHamil);
                    user.put("tanggal_lahir", birthday);
                    user.put("foto", foto);

                    api.postDataVolley(user, routes.editProfile, ProfileActivity.this, new VolleyResponseListener() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject result = new JSONObject(response);
                                String status = result.getString("status");

                                if(status.equals("0")) {
                                    Toast.makeText(ProfileActivity.this, "Terjadi Kesalahan. Coba lagi nanti.", Toast.LENGTH_SHORT).show();
                                } else {
                                    JSONObject user = new JSONObject(result.getString("user"));
                                    Preferences.getInstance().setUser(user);
                                    Toast.makeText(ProfileActivity.this, "Berhasil mengubah data.", Toast.LENGTH_SHORT).show();
                                }

                                btnSaveActive = true;
                                progress.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                api.handleError(e + response, ProfileActivity.this);
                            }
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            api.handleError(error.toString(), ProfileActivity.this);
                            btnSaveActive = true;
                            progress.dismiss();
                        }

                    });
                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Log.i("value is",""+newVal);
    }

}
