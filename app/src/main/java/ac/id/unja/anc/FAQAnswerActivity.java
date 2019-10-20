package ac.id.unja.anc;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

public class FAQAnswerActivity extends AppCompatActivity {

    TextView question, answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqanswer);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        toolbar.setElevation(10);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FAQ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        question = findViewById(R.id.question);
        answer = findViewById(R.id.answer);

        setText();
    }

    private void setText() {
        Intent intent = getIntent();
        String _question = intent.getStringExtra("question");
        String _answer = intent.getStringExtra("answer");

        question.setText(_question);
        answer.setText(_answer);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
