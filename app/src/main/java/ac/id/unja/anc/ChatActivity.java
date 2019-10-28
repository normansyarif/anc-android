package ac.id.unja.anc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import ac.id.unja.anc.Adapters.ChatAdapter;
import ac.id.unja.anc.Adapters.ChatItemAdapter;
import ac.id.unja.anc.Models.Chat;
import ac.id.unja.anc.Volley.Preferences;
import ac.id.unja.anc.Volley.Routes;
import ac.id.unja.anc.Volley.SocketInit;
import ac.id.unja.anc.Volley.VolleyAPI;
import ac.id.unja.anc.Volley.VolleyResponseListener;

public class ChatActivity extends AppCompatActivity {
    private VolleyAPI api = new VolleyAPI();
    private Routes routes = new Routes();
    private RecyclerView recyclerView;
    private ChatItemAdapter adapter;
    private ArrayList<Chat> arrayList;
    String thisID;
    ImageButton btnSend;
    ProgressDialog progress;
    ProgressBar progressBar;
    TextView textViewMsg;
    ChatAdapter chatAdapter;
    String token, msg, img = null;
    boolean sendImg = false;

    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap = null;
    private Uri filePath;
    private ImageView imagePreview;
    private SocketInit socketInit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatAdapter = new ChatAdapter(this, this);
        this.socketInit = new SocketInit();

        token = Preferences.getInstance().getToken();
        btnSend = findViewById(R.id.btnSend);
        progressBar = findViewById(R.id.progressBar);

        String nama = getIntent().getStringExtra("nama");
        if(nama == null) nama = "Chat Dokter";
        getSupportActionBar().setTitle(nama);
        sendMsg();
        initImgPreview();
        initLoading();
        socketListener();
    }

    public void initLoading(){
        progress = new ProgressDialog(this);
        progress.setMessage("Loading...");
        progress.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setMsg();
        getId();
    }

    // ---------- SCROLL TO BOTTOM


    public void scrollBottom(){
        final ScrollView scrollChat = findViewById(R.id.scrollChat);
        scrollChat.post(new Runnable() {
            @Override
            public void run() {
                scrollChat.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    // ---------- Get ID ONLY


    public void getId(){
        api.getDataVolley(routes.getId + token, ChatActivity.this, new VolleyResponseListener() {

            @Override
            public void onResponse(String response) {
                thisID = response;
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                api.handleError(error.toString(), ChatActivity.this);
            }

        });
    }


    // ---------- SEND MSG


    public void sendMsg(){
        ImageView btnAttach = findViewById(R.id.attach);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewMsg = findViewById(R.id.msgInput);
                msg = textViewMsg.getText().toString();
                if(TextUtils.isEmpty(msg)) {
                    Toast.makeText(ChatActivity.this, "Pesan Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                btnSend.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);


                HashMap<String, String> data = new HashMap<>();
                data.put("token", token);
                data.put("recipient_id", "1");
                data.put("chat", msg);
                if(sendImg) data.put("img", img);

                api.postDataVolley(data, routes.newChat, ChatActivity.this, new VolleyResponseListener() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject result = new JSONObject(response);
                            String id = result.getString("id");
                            String sender_id = result.getString("sender_id");
                            String status = result.getString("status");

                            if(status.equals("0")) {
                                btnSend.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(ChatActivity.this, "Terjadi Kesalahan. Coba lagi nanti.", Toast.LENGTH_SHORT).show();
                            } else {
                                socketInit.mSocket.emit("messagedetection", id, sender_id, "1", msg, getTime(), (sendImg) ? "1" : "0");

                                arrayList.add(new Chat(id, "0", msg, getTime(), "Terkirim", (sendImg) ? "1" : "0"));

                                sendImg = false;
                                bitmap = null;

                                btnSend.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            btnSend.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            api.handleError(e + response, ChatActivity.this);
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        btnSend.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        api.handleError(error.toString(), ChatActivity.this);
                    }

                });

                // ---- Reset

                imagePreview.setVisibility(View.GONE);
                textViewMsg.setText("");
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


    public void setMsg(){
        progress.show();
        api.getDataVolley(routes.chats + token, ChatActivity.this, new VolleyResponseListener() {

            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    arrayList = new ArrayList<>();
                    adapter = new ChatItemAdapter(arrayList, ChatActivity.this);

                    JSONArray data = new JSONArray(response);
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        String id = item.getString("id");
                        String sender_id = item.getString("sender_id");
                        String chat = item.getString("chat");
                        String created_at = item.getString("created_at");
                        String img = item.getString("img");

                        arrayList.add(new Chat(id, sender_id, chat, Utils.FormatTime(created_at), "Terkirim", img));
                    }

                    LinearLayoutManager layoutManager = new LinearLayoutManager(ChatActivity.this, LinearLayoutManager.VERTICAL, false);
                    layoutManager.setStackFromEnd(true);
                    recyclerView = findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                    scrollBottom();
                } catch (JSONException e) {
                    e.printStackTrace();
                    api.handleError(e + response, ChatActivity.this);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                api.handleError(error.toString(), ChatActivity.this);
            }

        });
    }

    public static String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(new Date());
    }


    // ------ Upload Img


    private void initImgPreview(){
        imagePreview = findViewById(R.id.imagePreview);
        imagePreview.setVisibility(View.GONE);
    }

    private void showFileChooser() {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                sendImg = true;
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                img = Utils.getStringImage(bitmap);
                imagePreview.setVisibility(View.VISIBLE);
                imagePreview.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // ---- Listener socket message received

    public void socketListener(){
        Emitter.Listener onNewMessage = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                ChatActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        // DO SOMETHING AFTER RECEIVED

                        JSONObject data = (JSONObject) args[0];
                        String id, sender_id, recipient, newMsg, time, img;
                        try {
                            id = data.getString("id");
                            sender_id = data.getString("sender_id");
                            recipient = data.getString("recipient_id");
                            newMsg = data.getString("msg");
                            time = data.getString("time");
                            img = data.getString("img");

                            if(recipient.equals(thisID)){
                                arrayList.add(new Chat(id, sender_id, newMsg, time, "", img));
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            Log.e("Received socket error", e.toString());
                        }
                    }
                });
            }
        };

        socketInit.mSocket.on("newmessage", onNewMessage);
    }

    // ---- Destroy

    @Override
    public void onDestroy() {
        super.onDestroy();
        socketInit.mSocket.disconnect();
    }
}
