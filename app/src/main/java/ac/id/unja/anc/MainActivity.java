package ac.id.unja.anc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static ac.id.unja.anc.Utils.*;

public class MainActivity extends AppCompatActivity {

    // Mock data
    String fullname = "Hatsune Miku";
    long usiaHamil = dateToWeek("2019-09-12");
    int profileImage = R.drawable.miku;
    //////////////////////////

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
        ImageView imageView1 = findViewById(R.id.imageView1);
        TextView tvFullname = findViewById(R.id.name1);
        TextView tvUsia = findViewById(R.id.name2);

        imageView1.setImageResource(profileImage);
        tvFullname.setText(fullname);
        tvUsia.setText("Usia kehamilaln " + usiaHamil + " minggu");

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
    }

    private void openActivity(Class<?> cls) {
        Intent intent = new Intent(MainActivity.this, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
