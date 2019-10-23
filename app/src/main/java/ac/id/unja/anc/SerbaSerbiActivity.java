package ac.id.unja.anc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ac.id.unja.anc.Volley.Preferences;
import ac.id.unja.anc.Volley.Routes;
import ac.id.unja.anc.Volley.VolleyAPI;
import ac.id.unja.anc.Volley.VolleyResponseListener;

public class SerbaSerbiActivity extends AppCompatActivity {
    private VolleyAPI api = new VolleyAPI();
    private Routes routes = new Routes();

    List<String> listId = new ArrayList<>();
    List<String> listArray = new ArrayList<>();
//    String[] listArray={"Merencanakan Kehamilan",
//            "Saat yang Baik untuk Hamil",
//            "Persiapan Kehamilan",
//            "Tanda-tanda Kehamilan",
//            "Keluhan yang Sering Timbul Selama Hamil dan Cara Mengatasi",
//            "Catatan Penting saat Hamil",
//            "Panduan Pergerakan Senam Hamil",
//            "Video USG"};

    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serba_serbi);

        api.getDataVolley(routes.serba, SerbaSerbiActivity.this, new VolleyResponseListener() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray result = new JSONArray(response);

                    for (int i = 0; i < result.length(); i++) {
                        JSONObject data = result.getJSONObject(i);
                        String id = data.getString("id");
                        String title = data.getString("title");

                        listId.add(id);
                        listArray.add(title);
                        setList();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    api.handleError(e + response, SerbaSerbiActivity.this);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                api.handleError(error.toString(), SerbaSerbiActivity.this);
            }

        });
    }

    public void setList(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listArray);
        listview = findViewById(R.id.listview);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(SerbaSerbiActivity.this, "ID : " + listId.get(position), Toast.LENGTH_SHORT).show();

//                Intent intent = new Intent(SerbaSerbiActivity.this, LocalWebviewActivity.class);
//                intent.putExtra("fileContent",  fileContent);
//                startActivity(intent);

            }
        });
    }
}
