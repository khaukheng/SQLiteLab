package my.edu.um.fsktm.sqlite;

import android.view.View;
import android.widget.Toast;

public class UserRecord {
    private String phone;
    private String name;
    private String email;

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString(){
        return super.toString();
    }
}
