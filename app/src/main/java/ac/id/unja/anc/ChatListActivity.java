package ac.id.unja.anc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import ac.id.unja.anc.Adapters.ChatListAdapter;
import ac.id.unja.anc.Models.ChatList;

public class ChatListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChatListAdapter adapter;
    private ArrayList<ChatList> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        setList();
    }

    void setList(){
        addData();
        adapter = new ChatListAdapter(arrayList, this);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    void addData(){
        arrayList = new ArrayList<>();
        arrayList.add(new ChatList("Dendiani Kemeriana", "Jadwal berobat kapan ya dok?", 2));
        arrayList.add(new ChatList("Widiana Saraswati", "Assalamualaikum dok", 1));
        arrayList.add(new ChatList("Norma Paradisa", "Baik terimakasih", 0));
        arrayList.add(new ChatList("Tami Siani", "Apa bisa ditunda 1 minggu?", 3));
        arrayList.add(new ChatList("Yulia Okarin", "Ok dok", 0));
        arrayList.add(new ChatList("Seliana Zaici", "Begitu ya", 0));
        arrayList.add(new ChatList("Nezuka Fitriani", "Apa bisa ditunda?", 0));
        arrayList.add(new ChatList("Kiana Karunia", "Baik terimakasih", 0));
        arrayList.add(new ChatList("Mei Tari", "Baik dok", 0));
        arrayList.add(new ChatList("Ayu Putri", "Terimakasih dok", 0));
        arrayList.add(new ChatList("Widya Ananda Sari", "Terimakasih..", 0));
        arrayList.add(new ChatList("Teriana Alawi", "Baik terimakasih", 0));
        arrayList.add(new ChatList("Aisyah Pratiwi", "Disitu ya oke", 0));
        arrayList.add(new ChatList("Ratna Sari", "Oke besok saya ke tempat praktek ya dok", 0));
        arrayList.add(new ChatList("Susanti", "Baik dok", 0));
    }
}
