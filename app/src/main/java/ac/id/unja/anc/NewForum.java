package ac.id.unja.anc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import ac.id.unja.anc.Volley.Preferences;
import ac.id.unja.anc.Volley.Routes;
import ac.id.unja.anc.Volley.VolleyAPI;
import ac.id.unja.anc.Volley.VolleyResponseListener;

public class NewForum extends AppCompatActivity {
    private VolleyAPI api = new VolleyAPI();
    private Routes routes = new Routes();
    ProgressDialog progress;
    private boolean btnAdd = true;
    Bitmap bitmap;
    String token;
    EditText forumTitle, forumContent;
    private final static int GALLERY_REQUEST_CODE = 123;
    ImageView ivfoto1, ivfoto2, ivfoto3;
    String foto1 = "", foto2 = "", foto3 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_forum);

        Toolbar toolbar = findViewById(R.id.main_toolbar);

        toolbar.setElevation(10);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Forum");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        token = Preferences.getInstance().getToken();
        forumTitle = findViewById(R.id.forumTitle);
        forumContent = findViewById(R.id.forumContent);
        btnListener();
        initLoading();
    }

    public void initLoading(){
        progress = new ProgressDialog(this);
        progress.setMessage("Loading...");
        progress.setCanceledOnTouchOutside(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_add_forum:
                if(btnAdd){
                    btnAdd = false;
                    progress.show();
                    String judul = forumTitle.getText().toString();
                    String konten = forumContent.getText().toString();

                    HashMap<String, String> data = new HashMap<>();
                    data.put("judul", judul);
                    data.put("konten", konten);
                    data.put("foto1", foto1);
                    data.put("foto2", foto2);
                    data.put("foto3", foto3);
                    data.put("token", token);

                    api.postDataVolley(data, routes.newForum, NewForum.this, new VolleyResponseListener() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject result = new JSONObject(response);
                                String status = result.getString("status");

                                if(status.equals("0")) {
                                    Toast.makeText(NewForum.this, "Terjadi Kesalahan. Coba lagi nanti.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent intent = new Intent(NewForum.this, ForumActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                api.handleError(e + response, NewForum.this);
                            }

                            btnAdd = true;
                            progress.dismiss();
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            btnAdd = true;
                            progress.dismiss();
                            api.handleError(error.toString(), NewForum.this);
                        }

                    });
                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void btnListener(){
        ivfoto1 = findViewById(R.id.foto1);
        ivfoto2 = findViewById(R.id.foto2);
        ivfoto3 = findViewById(R.id.foto3);

        Button btnImg = findViewById(R.id.btnImg);
        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                String[] mimeTypes = {"image/jpeg", "image/png"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                startActivityForResult(intent,GALLERY_REQUEST_CODE);
            }
        });
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
                    cursor.close();

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(foto1.equals("")) {
                        foto1 = Utils.getStringImage(bitmap);
                        ivfoto1.setImageURI(selectedImage);
                    } else if(foto2.equals("")){
                        foto2 = Utils.getStringImage(bitmap);
                        ivfoto2.setImageURI(selectedImage);
                    } else {
                        foto3 = Utils.getStringImage(bitmap);
                        ivfoto3.setImageURI(selectedImage);
                    }
                    break;
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_forum_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
