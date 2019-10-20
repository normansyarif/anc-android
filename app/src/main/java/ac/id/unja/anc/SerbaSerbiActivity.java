package ac.id.unja.anc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SerbaSerbiActivity extends AppCompatActivity {

    String[] listArray={"Merencanakan Kehamilan",
            "Saat yang Baik untuk Hamil",
            "Persiapan Kehamilan",
            "Tanda-tanda Kehamilan",
            "Keluhan yang Sering Timbul Selama Hamil dan Cara Mengatasi",
            "Catatan Penting saat Hamil",
            "Panduan Pergerakan Senam Hamil",
            "Video USG"};

    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serba_serbi);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listArray);
        listview = findViewById(R.id.listview);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    default:
                        break;
                }

                Toast.makeText(SerbaSerbiActivity.this, position + "", Toast.LENGTH_SHORT).show();

//                Intent intent = new Intent(SerbaSerbiActivity.this, LocalWebviewActivity.class);
//                intent.putExtra("fileContent",  fileContent);
//                startActivity(intent);

            }
        });
    }
}
