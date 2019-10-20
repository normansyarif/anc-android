package ac.id.unja.anc;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static ac.id.unja.anc.Utils.*;

public class ProfileActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {
    final Calendar myCalendar = Calendar.getInstance();
    EditText edittext, edittext2, etfullname;
    ImageView profile;
    private final static int GALLERY_REQUEST_CODE = 12345;

    // Mock data
    int userId = 1;
    String fullname = "Hatsune Miku";
    String ttl = "2019-08-08";
    long periode = dateToWeek("2019-09-12");
    int profilePicture = R.drawable.miku;
    //////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etfullname = findViewById(R.id.et_fullname);
        edittext = findViewById(R.id.birthday);
        edittext2 = findViewById(R.id.pregnant_date);
        profile = findViewById(R.id.imageView1);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        toolbar.setElevation(10);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit profil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etfullname.setText(fullname);
        edittext.setText(ttl);
        edittext2.setText(String.valueOf(periode));
        profile.setImageResource(profilePicture);

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
        if(mockLogout()) {
            // Berhasil logout, hapus data lokal, masuk ke activity login
            destroyLocalData();

            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else{
            // Gagal logout
            Toast.makeText(this, "Gagal logout", Toast.LENGTH_SHORT).show();
        }
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
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST_CODE:
                    //data.getData return the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    //Get the column index of MediaStore.Images.Media.DATA
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //Gets the String value in the column
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    // Set the Image in ImageView after decoding the String
                    profile.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
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
                if(mockSaveData()) {
                    // Berhasil edit profil
                }else{
                    // Gagal edit profil
                    Toast.makeText(this, "Edit profil gagal", Toast.LENGTH_SHORT).show();
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

    // Mock methods
    public boolean mockSaveData() {
        String fullname = etfullname.getText().toString();
        String birthday = edittext.getText().toString();
        int hamil = Integer.parseInt(edittext2.getText().toString());
        Toast.makeText(this, "POST\nfullname: " + fullname + "\nbirthday: " + birthday + "\nawal_hamil: " + weekToDate(hamil), Toast.LENGTH_LONG).show();
        return true;
    }

    public boolean mockLogout() {
        Toast.makeText(this, "Logout userId: " + userId, Toast.LENGTH_SHORT).show();
        return true;
    }

    public boolean destroyLocalData() {
        // Hapus data lokal
        return true;
    }
    /////////////////////////////
}
