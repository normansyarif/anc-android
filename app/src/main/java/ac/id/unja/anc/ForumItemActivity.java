package ac.id.unja.anc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ac.id.unja.anc.Adapters.ResponseAdapter;
import ac.id.unja.anc.Models.Response;
import ac.id.unja.anc.Volley.Preferences;
import ac.id.unja.anc.Volley.Routes;
import ac.id.unja.anc.Volley.VolleyAPI;
import ac.id.unja.anc.Volley.VolleyResponseListener;

public class ForumItemActivity extends AppCompatActivity {
    private VolleyAPI api = new VolleyAPI();
    private Routes routes = new Routes();
    private ResponseAdapter adapter;
    private ArrayList<Response> arrayList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_item);
        setData();
    }

    public void setData(){
        arrayList = new ArrayList<>();

        String id = getIntent().getStringExtra("id");
        api.getDataVolley(routes.forum + id, ForumItemActivity.this, new VolleyResponseListener() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject result = new JSONObject(response);
                    String id = result.getString("id");
                    String user_name = result.getString("user_name");
                    String judul = result.getString("judul");
                    String konten = result.getString("konten");
                    String created_at = result.getString("created_at");
                    String response_count = result.getString("response_count");

                    TextView t_user_name = findViewById(R.id.user_name);
                    TextView t_created_at = findViewById(R.id.created_at);
                    TextView t_judul = findViewById(R.id.judul);
                    TextView t_konten = findViewById(R.id.konten);
                    TextView t_response_count = findViewById(R.id.response_count);

                    t_user_name.setText(user_name);
                    t_judul.setText(judul);
                    t_konten.setText(konten);
                    t_created_at.setText(created_at);
                    t_response_count.setText(response_count + " tanggapan");

                    String token = result.getString("token");
                    ImageView foto1 = findViewById(R.id.foto1);
                    ImageView foto2 = findViewById(R.id.foto2);
                    ImageView foto3 = findViewById(R.id.foto3);
                    Glide.with(ForumItemActivity.this).load(routes.imgForum + id + "/" + "1")
                            .thumbnail(Glide.with(ForumItemActivity.this).load(R.drawable.ic_broken_image))
                            .into(foto1);
                    Glide.with(ForumItemActivity.this).load(routes.imgForum + id + "/" + "2")
                            .thumbnail(Glide.with(ForumItemActivity.this).load(R.drawable.ic_broken_image))
                            .into(foto2);
                    Glide.with(ForumItemActivity.this).load(routes.imgForum + id + "/" + "3")
                            .thumbnail(Glide.with(ForumItemActivity.this).load(R.drawable.ic_broken_image))
                            .into(foto3);

                    ImageView img = findViewById(R.id.img);
                    Glide.with(ForumItemActivity.this).load(routes.imgProfile + token)
                            .thumbnail(Glide.with(ForumItemActivity.this).load(R.drawable.ic_broken_image))
                            .into(img);

                    String responses = result.getString("responses");

                    JSONArray data = new JSONArray(responses);
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        String r_token = item.getString("token");
                        String r_user_name = item.getString("user_name");
                        String r_response = item.getString("response");
                        String r_created_at = item.getString("created_at");

                        arrayList.add(new Response(r_token, r_user_name, r_response, r_created_at));
                    }

                    adapter = new ResponseAdapter(arrayList);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(ForumItemActivity.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView = findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    api.handleError(e + response, ForumItemActivity.this);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                api.handleError(error.toString(), ForumItemActivity.this);
            }
        });

    }

}
