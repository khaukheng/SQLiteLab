package my.edu.um.fsktm.sqlite;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView listViewRecords;
    UserSQLHelper userSQLHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewRecords = (ListView)findViewById(R.id.listViewRecords);
        listViewRecords.setOnItemClickListener(this);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.action_settings){
            return true;
        }

        if(id == R.id.action_favourite){
            Intent dbmanager = new Intent(MainActivity.this,AndroidDatabaseManager.class);
            startActivity(dbmanager);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        Toast.makeText(getApplication(), "Position: " + position, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onResume(){
        super.onResume();
        updateList();
    }

    private void updateList(){
        userSQLHelper = new UserSQLHelper(this);
        final List<UserRecord> values = userSQLHelper.getAllUsers();

        if(values.isEmpty()){
            Toast.makeText(getApplicationContext(),getString(R.string.no_record_message), Toast.LENGTH_SHORT).show();

        }

        UserRecordAdapter adapter = new UserRecordAdapter(this, R.layout.user_record, values);

        listViewRecords.setAdapter(adapter);
    }
}
