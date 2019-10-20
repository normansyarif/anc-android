package ac.id.unja.anc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NewForum extends AppCompatActivity {
    EditText forumTitle, forumContent;

    // Mock data
    int userId = 1;
    ////////////

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

        forumTitle = findViewById(R.id.forumTitle);
        forumContent = findViewById(R.id.forumContent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_add_forum:
                if(mockPostForum()) {
                    // Berhasil post, masuk ke activity forum item
                    Intent intent = new Intent(NewForum.this, ForumItemActivity.class);
                    startActivity(intent);
                }else{
                    // Gagal
                    Toast.makeText(this, "Gagal post", Toast.LENGTH_SHORT).show();
                }


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_forum_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Mock method
    public boolean mockPostForum() {
        String judul = forumTitle.getText().toString();
        String konten = forumContent.getText().toString();
        Toast.makeText(this, "POST\njudul: " + judul + " konten: " + konten + " where user_id: " + userId, Toast.LENGTH_SHORT).show();
        return true;
    }
}
