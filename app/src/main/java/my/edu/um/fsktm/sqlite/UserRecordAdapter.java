package my.edu.um.fsktm.sqlite;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class UserRecordAdapter extends ArrayAdapter<UserRecord> {
    public UserRecordAdapter(Activity context, int resource, List<UserRecord> list) {
        super(context, resource, list);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final UserRecord userRecord = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.user_record,
                            parent,
                            false);
        }

        TextView textViewPhone, textViewName, textViewEmail;

        textViewPhone = (TextView) convertView.findViewById(R.id.textViewPhone);
        textViewName = (TextView) convertView.findViewById(R.id.textViewName);
        textViewEmail = (TextView) convertView.findViewById(R.id.textViewEmail);
        Button buttonDelete = (Button) convertView.findViewById(R.id.deleteButton);
        Button buttonUpdate = (Button) convertView.findViewById(R.id.updateButton);

        textViewPhone.setText("Phone" + " : " + userRecord.getPhone());
        textViewName.setText(userRecord.getName());
        textViewEmail.setText("Email" + " : " + userRecord.getEmail());

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSQLHelper userDataSource = new UserSQLHelper(getContext());
                boolean deleteSuccess = userDataSource.deleteUser(userRecord.getName());
                if (deleteSuccess) {
                    Toast.makeText(getContext(), "Deleted " + userRecord.getName(), Toast.LENGTH_SHORT).show();
                    remove(userRecord);
                } else
                    Toast.makeText(getContext(), "Failed to delete " + userRecord.getName(), Toast.LENGTH_SHORT).show();

            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final UserSQLHelper userDataSource = new UserSQLHelper(getContext());


                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Editing " +userRecord.getName());

                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);

                // Set up the input
                final EditText phone = new EditText(getContext());
                phone.setHint(R.string.phone);
                final EditText email = new EditText(getContext());
                email.setHint(R.string.email);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                phone.setInputType(InputType.TYPE_CLASS_TEXT);
                layout.addView(phone);
                email.setInputType(InputType.TYPE_CLASS_TEXT);
                layout.addView(email);

                builder.setView(layout);



                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContentValues k = new ContentValues();
                        k.put(UserContract.User.COLUMN_PHONE,phone.getText().toString());
                        k.put(UserContract.User.COLUMN_EMAIL,email.getText().toString());
                        boolean updateSuccessful = userDataSource.updateUser(userRecord.getName(),k);
                        if (updateSuccessful) {
                            Toast.makeText(getContext(), "Updated " + userRecord.getName(), Toast.LENGTH_SHORT).show();
                            remove(userRecord);
                            userRecord.setPhone(phone.getText().toString());
                            userRecord.setEmail(email.getText().toString());
                            add(userRecord);
                        } else
                            Toast.makeText(getContext(), "Failed to update " + userRecord.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        return convertView;
    }
}
