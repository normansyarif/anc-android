package ac.id.unja.anc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ac.id.unja.anc.Volley.Preferences;
import ac.id.unja.anc.Volley.Routes;

import static ac.id.unja.anc.Utils.*;

public class MainActivity extends AppCompatActivity {

    private Routes routes = new Routes();

    String fullname, token;
    long usiaHamil;
    int profileImage = R.drawable.miku;

    TextView tvFullname, tvUsia;
    ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout actionBar = findViewById(R.id.custom_action_bar);
        actionBar.setElevation(10);

        CardView cardHome = findViewById(R.id.card_home);
        CardView cardConsult = findViewById(R.id.card_consult);
        CardView cardForum = findViewById(R.id.card_forum);
        CardView cardCondition = findViewById(R.id.card_condition);
        CardView cardFaq = findViewById(R.id.card_faq);
        CardView cardSerba = findViewById(R.id.card_misc);
        LinearLayout profile = findViewById(R.id.main_profile);
        imageView1 = findViewById(R.id.imageView1);
        tvFullname = findViewById(R.id.name1);
        tvUsia = findViewById(R.id.name2);

        cardConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ChatListActivity.class);
            }
        });

        cardForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ForumActivity.class);
            }
        });

        cardFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(FAQActivity.class);
            }
        });

        cardCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(InfoActivity.class);
            }
        });

        cardHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(WelcomeActivity.class);
            }
        });

        cardSerba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(SerbaSerbiActivity.class);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ProfileActivity.class);
            }
        });

        setData();
    }

    public void setData(){
        SharedPreferences user = Preferences.getInstance().getUser();
        fullname = user.getString("name", null);

        token = Preferences.getInstance().getToken();
        Glide.with(this).load(routes.imgProfile + token)
                .thumbnail(Glide.with(this).load(R.drawable.miku))
                .into(imageView1);

        tvFullname.setText(fullname);

        String tipe = user.getString("tipe", "0");
        if(tipe.equals("1")){
            usiaHamil = dateToWeek(user.getString("awal_hamil", null));
            tvUsia.setText("Usia kehamilaln " + usiaHamil + " minggu");
        }
    }

    private void openActivity(Class<?> cls) {
        Intent intent = new Intent(MainActivity.this, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }
}
