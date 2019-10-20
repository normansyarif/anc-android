package ac.id.unja.anc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.IOException;

import ac.id.unja.anc.Adapters.ChatAdapter;

public class ChatActivity extends AppCompatActivity {
    ChatAdapter chatAdapter;

    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private Uri filePath;
    private ImageView imagePreview;
    private boolean sendImage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatAdapter = new ChatAdapter(this, this);
        getSupportActionBar().setTitle(getIntent().getStringExtra("nama"));
        sendMsg();
        initImgPreview();
    }

    public void sendMsg(){
        ImageButton btnSend = findViewById(R.id.btnSend);
        ImageView btnAttach = findViewById(R.id.attach);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String img = null;
                if(sendImage){
                    img = "OK";
                    sendImage = false;
                }
                chatAdapter.addUserChat(img, bitmap);
                imagePreview.setVisibility(View.GONE);
            }
        });

        btnAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });
    }

    // ------ Upload Img

    private void initImgPreview(){
        imagePreview = findViewById(R.id.imagePreview);
        imagePreview.setVisibility(View.GONE);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                sendImage = true;
                imagePreview.setVisibility(View.VISIBLE);
                imagePreview.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
