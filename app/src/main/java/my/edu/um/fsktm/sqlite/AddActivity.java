package my.edu.um.fsktm.sqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import javax.xml.validation.Validator;

public class AddActivity extends AppCompatActivity {
    private Validator nonempty_validate;
    private EditText editTextPhone, editTextName, editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

    }

    public void saveRecord(View v){
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);

        String phone, name, email;

        phone = editTextPhone.getText().toString();
        if(phone.isEmpty()){
            editTextPhone.setError(getString(R.string.error_phone));
            return;
        }

        name = editTextName.getText().toString();
        if(name.isEmpty()){
            editTextName.setError(getString(R.string.error_name));
            return;
        }

        email = editTextEmail.getText().toString();
        if(email.isEmpty()){
            editTextEmail.setError(getString(R.string.error_email));
            return;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError(getString(R.string.error_invalid_email));
            return;
        }
        UserRecord userRecord = new UserRecord();
        userRecord.setPhone(phone);
        userRecord.setName(name);
        userRecord.setEmail(email);

        UserSQLHelper userDataSource = new UserSQLHelper(this);
        userDataSource.insertUser(userRecord);

        this.finish();
    }

}
